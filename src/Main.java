import ParseData.DataConvert;

import java.util.zip.CRC32;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class Main {
    public static void main(String[] args) {


        String hexData = "2F 3E 3B 19 67 6E 25 D8 90 D0 26 3A 96 C3 44 79 19 3C 11 49 08 F3 8A 4D 5C 57 50 18 42 48 C2 3C E8 BF F0 44 1B 98 05 35 1B 53 6F 01 83 6B 58 DD B6 F9 51 20 10 2D 0E 95 7C 43 24 FC 73 5D 07 53 40 40 0E B4 28 CB 48 DE 00 F8 33 04 57 52 6A E1 12 E4 20 34 3C DD 24 74 22 19 85 01 23 59 04 C5 9D 4B 16 04 15 66 0B A7 33 E7 7F 37 D3 BC 02 21 39 49 9A 56 C8 BB C2 40 4A 6D 37 0E 1C DF 44 21";
        System.out.println("数据长度: " + getDataLength(hexData));


    }

    public static int getDataLength (String data){
        // 我们用空格来分割这个字符串，得到的数组的长度就是数据的长度
        String[] hexValues = data.split(" ");
        return hexValues.length;
    }
}
