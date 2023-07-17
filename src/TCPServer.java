import ParseData.DataConvert;
import ParseData.DecodingPacket;
import ParseData.EncodingPacket;
import ParseData.ICDCommandDefinitions;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Arrays;

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

        public ClientHandler(Socket clientSocket) {
            this.clientSocket = clientSocket;
        }

        @Override
        public void run() {
            try {
                ICDDevice myDevice = new ICDDevice();
                InputStream inputStream = clientSocket.getInputStream();
                OutputStream outputStream = clientSocket.getOutputStream();

                byte[] buffer = new byte[1024];
                int bytes;
                while ((bytes = inputStream.read(buffer)) != -1) {
                    byte[] receivedBytes = Arrays.copyOf(buffer, bytes);
                    DecodingPacket packet = new DecodingPacket(receivedBytes);

                    // if command id is valid
                    myDevice.process(packet);
                    byte[] response = myDevice.getResponse();
                    if(response!=null)
                    outputStream.write(myDevice.getResponse());
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
        TCPServer server = new TCPServer(8888);
        server.start();

    }

}