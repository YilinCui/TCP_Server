import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.nio.ByteBuffer;


public class Main {
    public static final int EPOCH_TIME_20210101= 1609459200;
    public static void main(String[] args) {
        String hexData = "00 00 00 00 00 05 04 05 1E 00 00 00 00 00 02 02 04 05 6E 00 00 00 0A 01 08 0A AA 00 00 01 A0 00 01 00 41 00 00 00 B8 0B 78 00 1E 00 00 00 02 00 68 00 00 00";
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
