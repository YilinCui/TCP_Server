import java.util.zip.CRC32;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class Main {
    public static void main(String[] args) {
        byte[] data = new byte[]{0x0C, 0x5D, (byte) 0xBF, 0x76, 0x00, (byte) 0x8B, 0x00, 0x00, 0x00, 0x00, 0x00};

        int payloadStart = 3; // Assuming that payload starts from 4th byte (index 3)

        CRC32 crc = new CRC32();
        crc.update(data, payloadStart, data.length - payloadStart);

        long checksum = crc.getValue();

        // Convert to byte array in big endian order
        ByteBuffer bb = ByteBuffer.allocate(Integer.BYTES);
        bb.order(ByteOrder.LITTLE_ENDIAN);  // Use big endian byte order
        bb.putInt((int) checksum);
        byte[] result = bb.array();

        // Convert to hex string and print
        StringBuilder sb = new StringBuilder();
        for (byte b : result) {
            sb.append(String.format("%02X ", b));
        }

        System.out.println("The CRC32 checksum in big endian order is: " + sb.toString());
    }
}
