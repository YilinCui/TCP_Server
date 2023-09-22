package Controller;

import ParseData.DataConvert;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashSet;

public class ECGServer {
    private ServerSocket serverSocket;
    private Socket clientSocket;

    public ECGServer(int port) throws IOException {
        serverSocket = new ServerSocket(port);
    }

    public void start() throws IOException {
        System.out.println("ECG Server is listening on port " + serverSocket.getLocalPort());

        while (true) {
            clientSocket = serverSocket.accept();
            System.out.println("ECG Channel Created");

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
                OutputStream outputStream = clientSocket.getOutputStream();
                InputStream inputStream = clientSocket.getInputStream();

                byte[] buffer = new byte[1024];
                int bytesRead;

                while (true) {
                    byte[] data = new byte[34];
                    outputStream.write(data);
                    outputStream.flush();
                    System.out.println("ECG data flushed to Client: " + DataConvert.byteDataToHexString(data));
                    Thread.sleep(24);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
