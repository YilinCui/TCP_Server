package Controller;

import ParseData.DataConvert;
import ParseData.DecodingPacket;
import ParseData.ICDCommandDefinitions;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Arrays;
import java.util.HashSet;

/**
 * Entrance of the program
 */
public class TCPServer implements ICDCommandDefinitions, FilesHandler {
    private ServerSocket serverSocket;
    private Socket clientSocket;
    private String folderPath = "src" + File.separator + "LocalData";

    public TCPServer(int port) throws IOException {
        serverSocket = new ServerSocket(port);
        FilesHandler.creatFolder(folderPath);
    }

    public void start() throws IOException {
        System.out.println("Server is listening on port " + serverSocket.getLocalPort());

        while (true) {
            clientSocket = serverSocket.accept();
            System.out.println("New client connected");

            // Create a new thread and pass the client socket to it
            Thread clientThread = new Thread(new ClientHandler(clientSocket));
            clientThread.start();
        }

    }

    private class ClientHandler implements Runnable {
        private Socket clientSocket;
        private int clientID;
        // a set of commandId including the command return more than 1 packet.
        private HashSet<Integer> commandSet = new HashSet<>() {{
            add(0x64);
            add(0x65);
            add(0x67);
        }};

        private static int globalClientID = 0;

        public ClientHandler(Socket clientSocket) {
            this.clientSocket = clientSocket;
            synchronized (ClientHandler.class) { // 同步clientID分配，以防止在多线程环境中出现问题
                this.clientID = globalClientID++;
            }
        }

        @Override
        public void run() {
            try {
                ICDDevice myDevice = new ICDDevice(clientID++);
                InputStream inputStream = clientSocket.getInputStream();
                OutputStream outputStream = clientSocket.getOutputStream();

                while (true) {
                    byte[] headerBuffer = new byte[3];
                    int bytesRead = inputStream.read(headerBuffer, 0, 3);  // Read the 3-byte header

                    if (bytesRead < 3) {
                        // Flush the InputStream and continue
                        inputStream.skip(inputStream.available());
                        break;
                    }
                    byte[] receivedBytes;
                    int totalPacketLength = headerBuffer[0] & 0xFF;  // Total length of the packet

                    // Check if it's a special packet with length 4
                    if (totalPacketLength == 0) {
                        byte[] specialPacket = new byte[4];
                        bytesRead = inputStream.read(specialPacket, 3, 1); // Read the remaining byte
                        if (bytesRead < 1) {
                            // Flush the InputStream and continue
                            inputStream.skip(inputStream.available());
                            break;
                        }
                        System.arraycopy(headerBuffer, 0, specialPacket, 0, 3);
                        receivedBytes = specialPacket;
                    } else {
                        int payloadLength = totalPacketLength - 3;

                        byte[] payloadBuffer = new byte[payloadLength];
                        bytesRead = inputStream.read(payloadBuffer, 0, payloadLength);  // Read the exact length of the payload

                        if (bytesRead < payloadLength) {
                            // Flush the InputStream and continue
                            inputStream.skip(inputStream.available());
                            break;
                        }

                        receivedBytes = new byte[totalPacketLength];
                        System.arraycopy(headerBuffer, 0, receivedBytes, 0, 3);
                        System.arraycopy(payloadBuffer, 0, receivedBytes, 3, payloadLength);
                    }


                    // Typically, TCP client and Server's message logic should be 1 to 1
                    // which means client sent 1 packet and server should also return 1 packet
                    // However, there are some commands might return more than one packet
                    // Thus, a command HashSet is introduced to handle 1 to Many relationship.
                    try {
                        myDevice.process(receivedBytes);
                        int iCommandId = receivedBytes[2];
                        if (!commandSet.contains(iCommandId)) {
                            // 1 to 1 relationship
                            byte[] TCPServerResponse = myDevice.getResponse();
                            if (TCPServerResponse != null) {
                                outputStream.write(TCPServerResponse);
                                System.out.println("Sent to client: " + DataConvert.byteDataToHexString(TCPServerResponse) + "\n");
                            }
                        } else {
                            // 1 to many relationship
                            byte[][] TCPServerResponse = myDevice.getLongResponse();
                            if (TCPServerResponse != null) {
                                for (byte[] response : TCPServerResponse) {
                                    outputStream.write(response);
                                    // flush the packet to handle TCP packet stickiness
                                    outputStream.flush();
                                    System.out.println("Sent to client: " + DataConvert.byteDataToHexString(response) + "\n");
                                }
                            }
                        }

                    } catch (IllegalArgumentException e) {
                        System.out.println(e.getMessage());
                    }
                }

                inputStream.close();
                outputStream.close();
                clientSocket.close();
                System.out.println("Client disconnected");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}