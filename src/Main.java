import Controller.RandomData;
import ParseData.DataConvert;

import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.zip.CRC32;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;


public class Main {
    public static final int EPOCH_TIME_20210101= 1609459200;
    public static void main(String[] args) {
        String hexData = "6F 2D 64 01 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 1C 56 EB B0 00 00 00 C6 67 7E E3";
        System.out.println("数据长度: " + getDataLength(hexData));

//        String data = "AC 7A D2 04";
//        byte[] arr = DataConvert.hexStringToByteArray(data);
//        System.out.println(DataConvert.getFormattedTimestampFromBytes(arr));
//        byte bb = 0x01;
//        System.out.println(bb);

//        byte[] timestamp = new byte[]{0x53, (byte) 0xBA, 0x03, 0x05};
//        System.out.println("timeStamp is: " + parseTimeFromBytes(timestamp));

    }

    public static int getDataLength (String data){
        // 我们用空格来分割这个字符串，得到的数组的长度就是数据的长度
        String[] hexValues = data.split(" ");
        return hexValues.length;
    }

    public static String parseTimeFromBytes(byte[] timeStamp) {
        ByteBuffer byteBuffer = ByteBuffer.wrap(timeStamp);
        byteBuffer.order(java.nio.ByteOrder.LITTLE_ENDIAN);
        int time = byteBuffer.getInt();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss").withZone(ZoneId.systemDefault());

        long epochTime = EPOCH_TIME_20210101 + (time & 0xFFFFFFFFL);
        Instant datePG = Instant.ofEpochSecond(epochTime);
        try {
            return formatter.format(datePG);
        } catch (DateTimeParseException e) {
            return "Error";
        }
    }

}
