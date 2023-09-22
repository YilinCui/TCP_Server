import Controller.ECGServer;
import Controller.TCPServer;

import java.io.IOException;

public class StartServer {
    public static void main(String[] args) throws IOException {
        Thread serverThread = new Thread(() -> {
            try {
                new TCPServer(8888).start();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        serverThread.start();


        Thread ECGThread = new Thread(() -> {
            try {
                new ECGServer(8889).start();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        ECGThread.start();
    }
}
