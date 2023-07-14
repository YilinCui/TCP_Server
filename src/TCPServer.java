import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Arrays;

public class TCPServer implements ICDCommandDefinitions{

    private ServerSocket serverSocket;
    private Socket clientSocket;
    private InputStream inputStream;
    private OutputStream outputStream;

    private ICDDevice myDevice;

    public TCPServer(int port) throws IOException {
        serverSocket = new ServerSocket(port);
    }

    public void start() throws IOException {
        System.out.println("Server is listening on port " + serverSocket.getLocalPort());

        while (true) {
            clientSocket = serverSocket.accept();
            System.out.println("New client connected");

            inputStream = clientSocket.getInputStream();
            outputStream = clientSocket.getOutputStream();

            byte[] buffer = new byte[1024];
            int bytes;
            while ((bytes = inputStream.read(buffer)) != -1) {
                byte[] receivedBytes = Arrays.copyOf(buffer, bytes);
                int iCommandId = receivedBytes[2];
                // if commadn id is valid
                myDevice.process(receivedBytes);

                switch (iCommandId){
                    case ICD_CMD_READ_BRADY_PARAM:

                }
                DecodingPacket packet = new DecodingPacket(DataConvert.bytesToHex(receivedBytes));

                if(packet.getInstructionID().equals(DataConvert.hexToString(ICD_CMD_READ_BRADY_PARAM))){
                    byte sequenceNumber = (byte) Integer.parseInt(packet.getSequenceNumber(), 16);
                    byte instructionID = (byte) Integer.parseInt(packet.getInstructionID(), 16);
                    byte crc = (byte) Integer.parseInt(packet.getCrc(), 16);

                    byte[] byteArray = new byte[]{
                            (byte) 0x20, sequenceNumber, instructionID, (byte) 0x04,
                            (byte) 0x01, (byte) 0x15, (byte) 0x1E, (byte) 0x00,
                            (byte) 0x01, (byte) 0x00, (byte) 0x00, (byte) 0x00,
                            (byte) 0x00, (byte) 0xD2, (byte) 0x00, (byte) 0x78,
                            (byte) 0x0B, (byte) 0x00, (byte) 0x00, (byte) 0x03,
                            (byte) 0x1E, (byte) 0x0B, (byte) 0x05, (byte) 0x00,
                            (byte) 0x02, (byte) 0x04, (byte) 0x30, (byte) 0xB7,
                            (byte) 0xB3, (byte) 0x0A, (byte) 0xE2, (byte) 0x1C,
                            (byte) 0xDF, (byte) 0x44, crc
                    };
                    //0x20 2C 4A 04 01 15 1E 00 01 00 00 00 00 D2 00 78 0B 00 00 03 1E 0B 05 00 02 04 30 B7 B3 0A E2 1C DF 44 21
                    System.out.println("客户端应该看到:" + DataConvert.byteDataToHexString(byteArray));
                    outputStream.write(byteArray);
                }

                if(!packet.getTag().equals("ack")){
                    System.out.println("Received from client: " + packet);

//                    int sequenceNumber = Integer.parseInt(packet.getSequenceNumber(), 16); // 将十六进制的字符串转换为int
//                    byte b = (byte)sequenceNumber; // 将int转换为byte
//
//                    int crc = Integer.parseInt(packet.getCrc(), 16); // 将十六进制的字符串转换为int
//                    byte c = (byte)crc; // 将int转换为byte
//                    byte[] toSendBytes = new byte[]{0x08, b, (byte) 0x80, 0x03, 0x00, 0x00, 0x00, (byte) 0xF2, 0x70, (byte) 0xF1, c};
//
//                    System.out.println("垃圾数据:" + DataConvert.byteDataToHexString(toSendBytes));
//                    outputStream.write(toSendBytes);
                }
            }

            inputStream.close();
            clientSocket.close();
            outputStream.close();
            System.out.println("Client disconnected");
        }
    }
    public static void main(String[] args) throws IOException {
        TCPServer server = new TCPServer(8888);
        server.start();

    }


}
