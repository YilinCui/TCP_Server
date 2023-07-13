import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Arrays;

public class TCPServer {

    private ServerSocket serverSocket;
    private Socket clientSocket;
    private InputStream inputStream;

    public TCPServer(int port) throws IOException {
        serverSocket = new ServerSocket(port);
    }

    public void start() throws IOException {
        System.out.println("Server is listening on port " + serverSocket.getLocalPort());

        while (true) {
            clientSocket = serverSocket.accept();
            System.out.println("New client connected");

            inputStream = clientSocket.getInputStream();
            byte[] buffer = new byte[1024];
            int bytes;
            while ((bytes = inputStream.read(buffer)) != -1) {
                byte[] receivedBytes = Arrays.copyOf(buffer, bytes);
                Packet packet = new Packet(bytesToHex(receivedBytes));
                System.out.println("Received from client: " + packet);
                // parse the incoming data
            }

            inputStream.close();
            clientSocket.close();
            System.out.println("Client disconnected");
        }
    }

    private static final char[] HEX_ARRAY = "0123456789ABCDEF".toCharArray();

    public static String bytesToHex(byte[] bytes) {
        char[] hexChars = new char[bytes.length * 3];
        for (int j = 0; j < bytes.length; j++) {
            int v = bytes[j] & 0xFF;
            hexChars[j * 3] = HEX_ARRAY[v >>> 4];
            hexChars[j * 3 + 1] = HEX_ARRAY[v & 0x0F];
            if (j < bytes.length - 1) {
                hexChars[j * 3 + 2] = ' '; // add space between bytes
            }
        }
        return new String(hexChars).trim(); // remove trailing space
    }


    public static void main(String[] args) throws IOException {
        TCPServer server = new TCPServer(8888);
        server.start();
    }

}
