import ParseData.DataConvert;

import java.util.zip.CRC32;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class Main {
    public static void main(String[] args) {

        byte[] data = DataConvert.hexStringToByteArray("80 44 0C 31 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 32 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 33 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 32 30 32 33 30 37 31 34 00 00 00 00 31 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 32 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 33 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 32 30 32 33 30 37 31 34 00 00 00 00 9E BE A0 CD 1C DF 44 21");

        System.out.println(DataConvert.byteArrayToHexString(data));
    }
}
