import Controller.ECGServer;
import Controller.FilesHandler;
import Controller.TCPServer;

import java.io.File;
import java.io.IOException;

public class StartServer implements FilesHandler {
    public static void main(String[] args) throws IOException {
        FilesHandler.deleteDirectoryRecursively("src" + File.separator + "LocalData" + File.separator);
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
