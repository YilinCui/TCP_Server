import Controller.RandomData;
import ParseData.DataConvert;

import java.util.zip.CRC32;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;


public class Main {
    public static void main(String[] args) {
        String hexData = "6C 2A 64 00 A1 F7 EE 04 B5 F7 EE 04 00 00 08 81 6A BD A8 8B 19 A8 35 07 00";
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
