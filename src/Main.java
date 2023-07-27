import ParseData.DataConvert;

import java.util.zip.CRC32;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class Main {
    public static void main(String[] args) {


        String hexData = "6C 4F 64 01 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 73 13 55 18 F5 19 D7 1E B9 23 C8 2A AA 2F 00 00 00 00 00 00 00 00 01 00 02 00 03 00 04 00 05 00 06 00 00 00 00 00 00 00 0A 00 0B 00 0C 00 0D 00 0E 00 0F 00 10 00 00 00 00 00 00 00 41 2B B5 FC 00 00 00 63 0D 8D 1D";
        System.out.println("数据长度: " + getDataLength(hexData));


    }

    public static int getDataLength (String data){
        // 我们用空格来分割这个字符串，得到的数组的长度就是数据的长度
        String[] hexValues = data.split(" ");
        return hexValues.length;
    }
}
