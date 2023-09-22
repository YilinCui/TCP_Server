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
        String hexData = "4B 36 2A 05 00 00 00 BA 8C C2 04 1C 10 FF 0F FF 0F 00 00 B6 0A 3C 01 EE FE EE FE FF 7F 00 00 C0 9B 1E 05 C0 9B 1E 05 C7 96 C2 04 C7 96 C2 04 B2 75 1E 05 1B 99 1E 05 B2 75 1E 05 B4 01 00 00 04 00 00 00 A4 9C B6 9F 1C DF 44 21";
        System.out.println("数据长度: " + getDataLength(hexData));

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
