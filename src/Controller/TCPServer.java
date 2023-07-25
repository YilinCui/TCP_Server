package Controller;

import ParseData.DataConvert;
import ParseData.DecodingPacket;
import ParseData.ICDCommandDefinitions;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Arrays;

/**
 * Entrance of the program
 */
public class TCPServer implements ICDCommandDefinitions {

    private ServerSocket serverSocket;
    private Socket clientSocket;

    public TCPServer(int port) throws IOException {
        serverSocket = new ServerSocket(port);
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
        private static int clientID;

        public ClientHandler(Socket clientSocket) {
            this.clientSocket = clientSocket;
        }

        @Override
        public void run() {
            try {
                ICDDevice myDevice = new ICDDevice(clientID++);
                InputStream inputStream = clientSocket.getInputStream();
                OutputStream outputStream = clientSocket.getOutputStream();

                byte[] buffer = new byte[1024];
                int bytes;
                while ((bytes = inputStream.read(buffer)) != -1) {
                    byte[] receivedBytes = Arrays.copyOf(buffer, bytes);

                    try{
                        myDevice.process(receivedBytes);
                        byte[] TCPServerResponse = myDevice.getResponse();
                        if(TCPServerResponse!=null){
                            outputStream.write(TCPServerResponse);
                            System.out.println("Sent to client: " + DataConvert.byteDataToHexString(TCPServerResponse) + "\n");
                        }
                    }catch (IllegalArgumentException e){
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

    public static void main(String[] args) throws IOException {
        new TCPServer(8888).start();
    }

}