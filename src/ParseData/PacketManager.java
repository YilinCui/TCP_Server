package ParseData;

import java.io.*;

public class PacketManager {
    private static final String FILE_NAME = "DecodingPacket.ser";

    public PacketManager(){

    }

    public static void serialize(DecodingPacket packet) {
        try {
            FileOutputStream fileOut = new FileOutputStream(FILE_NAME);
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(packet);
            out.close();
            fileOut.close();
        } catch (Exception i) {
            i.printStackTrace();
        }
    }
    public static DecodingPacket deserialize() {
        DecodingPacket packet = null;
        try {
            FileInputStream fileIn = new FileInputStream(FILE_NAME);
            ObjectInputStream in = new ObjectInputStream(fileIn);
            packet = (DecodingPacket) in.readObject();
            in.close();
            fileIn.close();
        } catch (IOException i) {
            i.printStackTrace();
            return null;
        } catch (ClassNotFoundException c) {
            System.out.println("DecodingPacket class not found");
            c.printStackTrace();
            return null;
        }
        return packet;
    }
}
