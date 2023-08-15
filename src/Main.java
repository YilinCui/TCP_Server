import Controller.RandomData;
import ParseData.DataConvert;

import java.util.zip.CRC32;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;


public class Main {
    public static void main(String[] args) {


        String hexData = "00 C9 40 DD 04 DD 74 C8 04 02 00 8A CA E4 79 98 90 8A 7A C6 01 01 04 54 9C A7 1A D0 92 45 21 4A 33 65 45 51 65 34 74 1D 4F 0D 52 0C 68 61 3F 7B 6F 10 7C 62 72 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 15 27 29 27 07 10 2F 3F 45 05 70 59 56 6A 74 45 20 72 0B 30 00 5C 63 09 00 00 00 C7 D4 1B 53";
        System.out.println("数据长度: " + getDataLength(hexData));

        String data = "AC 7A D2 04";
        byte[] arr = DataConvert.hexStringToByteArray(data);
        System.out.println(DataConvert.getFormattedTimestampFromBytes(arr));
        byte bb = 0x01;
        System.out.println(bb);


    }

    public static int getDataLength (String data){
        // 我们用空格来分割这个字符串，得到的数组的长度就是数据的长度
        String[] hexValues = data.split(" ");
        return hexValues.length;
    }




}
