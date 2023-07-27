import ParseData.DataConvert;

import java.util.zip.CRC32;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class Main {
    public static void main(String[] args) {


        String hexData = "01 61 1C 17 27 31 6D 05 7D 4F 24 62 47 7A 26 75 7F 77 3C 2C 3B 49 7E 67 11 08 06 69 4D 6B 13 01 0E 1F 0A 28 32 1D 5C 1D 59 0D 42 5E 3E 66 53 37 1B 05 64 75 26 55 6B 64 44 72 62 5F 38 01 0B 42 0B 66 7F 13 32 2C 6C 38 53 0E 27 3C 76 43 39 25 5C 7D 09 75 56 56 39 7E 4E 09 04 36 76 51 18 4A 0C D3 BF 22 01 00 00 00 F7 13 A6 0B";
        System.out.println("数据长度: " + getDataLength(hexData));


    }

    public static int getDataLength (String data){
        // 我们用空格来分割这个字符串，得到的数组的长度就是数据的长度
        String[] hexValues = data.split(" ");
        return hexValues.length;
    }
}
