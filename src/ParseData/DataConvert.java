package ParseData;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Locale;
import java.util.zip.CRC32;

/**
 * Utility Class used to do data conversion
 */
public class DataConvert {
    private static final char[] HEX_ARRAY = "0123456789ABCDEF".toCharArray();

    private static int[] CRC8_TABLE = {
            0x00, 0x31, 0x62, 0x53, 0xc4, 0xf5, 0xa6, 0x97, 0xb9, 0x88, 0xdb, 0xea, 0x7d, 0x4c, 0x1f, 0x2e,
            0x43, 0x72, 0x21, 0x10, 0x87, 0xb6, 0xe5, 0xd4, 0xfa, 0xcb, 0x98, 0xa9, 0x3e, 0x0f, 0x5c, 0x6d,
            0x86, 0xb7, 0xe4, 0xd5, 0x42, 0x73, 0x20, 0x11, 0x3f, 0x0e, 0x5d, 0x6c, 0xfb, 0xca, 0x99, 0xa8,
            0xc5, 0xf4, 0xa7, 0x96, 0x01, 0x30, 0x63, 0x52, 0x7c, 0x4d, 0x1e, 0x2f, 0xb8, 0x89, 0xda, 0xeb,
            0x3d, 0x0c, 0x5f, 0x6e, 0xf9, 0xc8, 0x9b, 0xaa, 0x84, 0xb5, 0xe6, 0xd7, 0x40, 0x71, 0x22, 0x13,
            0x7e, 0x4f, 0x1c, 0x2d, 0xba, 0x8b, 0xd8, 0xe9, 0xc7, 0xf6, 0xa5, 0x94, 0x03, 0x32, 0x61, 0x50,
            0xbb, 0x8a, 0xd9, 0xe8, 0x7f, 0x4e, 0x1d, 0x2c, 0x02, 0x33, 0x60, 0x51, 0xc6, 0xf7, 0xa4, 0x95,
            0xf8, 0xc9, 0x9a, 0xab, 0x3c, 0x0d, 0x5e, 0x6f, 0x41, 0x70, 0x23, 0x12, 0x85, 0xb4, 0xe7, 0xd6,
            0x7a, 0x4b, 0x18, 0x29, 0xbe, 0x8f, 0xdc, 0xed, 0xc3, 0xf2, 0xa1, 0x90, 0x07, 0x36, 0x65, 0x54,
            0x39, 0x08, 0x5b, 0x6a, 0xfd, 0xcc, 0x9f, 0xae, 0x80, 0xb1, 0xe2, 0xd3, 0x44, 0x75, 0x26, 0x17,
            0xfc, 0xcd, 0x9e, 0xaf, 0x38, 0x09, 0x5a, 0x6b, 0x45, 0x74, 0x27, 0x16, 0x81, 0xb0, 0xe3, 0xd2,
            0xbf, 0x8e, 0xdd, 0xec, 0x7b, 0x4a, 0x19, 0x28, 0x06, 0x37, 0x64, 0x55, 0xc2, 0xf3, 0xa0, 0x91,
            0x47, 0x76, 0x25, 0x14, 0x83, 0xb2, 0xe1, 0xd0, 0xfe, 0xcf, 0x9c, 0xad, 0x3a, 0x0b, 0x58, 0x69,
            0x04, 0x35, 0x66, 0x57, 0xc0, 0xf1, 0xa2, 0x93, 0xbd, 0x8c, 0xdf, 0xee, 0x79, 0x48, 0x1b, 0x2a,
            0xc1, 0xf0, 0xa3, 0x92, 0x05, 0x34, 0x67, 0x56, 0x78, 0x49, 0x1a, 0x2b, 0xbc, 0x8d, 0xde, 0xef,
            0x82, 0xb3, 0xe0, 0xd1, 0x46, 0x77, 0x24, 0x15, 0x3b, 0x0a, 0x59, 0x68, 0xff, 0xce, 0x9d, 0xac
    };
    /**
     * Convert incoming byte[] buffer into Hex Strng
     * @param bytes
     * @return Hex String
     */
    public static String bytesToHex(byte[] bytes) {
        char[] hexChars = new char[bytes.length * 3];
        for (int j = 0; j < bytes.length; j++) {
            int v = bytes[j] & 0xFF;
            hexChars[j * 3] = HEX_ARRAY[v >>> 4];
            hexChars[j * 3 + 1] = HEX_ARRAY[v & 0x0F];
            if (j < bytes.length - 1) {
                hexChars[j * 3 + 2] = ' '; // add space between bytes
            }
        }
        return new String(hexChars).trim(); // remove trailing space
    }

    public static String byteArrayToHexString(byte[] bytes){
        if(bytes==null){
            return "";
        }
        StringBuilder sb = new StringBuilder();
        for (byte b : bytes) {
            sb.append(String.format("%02X ", b));
        }
        String result = sb.toString().trim(); // Remove the trailing space
        return result;
    }

    public static String hexToString(int hexNumber) {
        // 这会移除 "0x" 前缀并将数值转换为字符串
        return Integer.toHexString(hexNumber).toUpperCase();
    }

    public static String byteDataToHexString( byte[] data) {
        StringBuilder sb = new StringBuilder();
        sb.append("0x");
        for (byte b : data) {
            sb.append(String.format(Locale.US, "%02X ", b));
        }
        return sb.toString();
    }

    /**
     * Convert Byte to 0x00 String
     * @param num
     * @return 0x00 Hex String
     */
    public static String byteToHex(byte num) {
        char[] hexDigits = new char[2];
        hexDigits[0] = Character.forDigit((num >> 4) & 0xF, 16);
        hexDigits[1] = Character.forDigit((num & 0xF), 16);

        return "0x" + new String(hexDigits).toUpperCase();
    }
    public static byte[] hexStringToByteArray(String s) {
        s = s.replace(" ", "").trim();
        int len = s.length();
        byte[] data = new byte[len / 2];
        for (int i = 0; i < len; i += 2) {
            data[i / 2] = (byte) ((Character.digit(s.charAt(i), 16) << 4)
                    + Character.digit(s.charAt(i+1), 16));
        }
        return data;
    }

    public static byte[] calculateCRC32(byte[] data, int start, int length) {
        CRC32 crc = new CRC32();
        crc.update(data, start, length);

        long checksum = crc.getValue();

        // Convert to byte array in little endian order
        ByteBuffer bb = ByteBuffer.allocate(Integer.BYTES);
        bb.order(ByteOrder.LITTLE_ENDIAN);  // Use little endian byte order
        bb.putInt((int) checksum);

        return bb.array();
    }

    public static byte[] addCRC32(byte[] data) {
        byte[] crc1 = calculateCRC32(data, 0, data.length);
        byte[] dataWithCrc = ByteBuffer.allocate(data.length + crc1.length)
                .put(data).put(crc1).array();
        return dataWithCrc;
    }

    public static byte[] addTwoCRC32(byte[] data) {
        byte[] crc1 = calculateCRC32(data, 0, data.length);
        byte[] dataWithCrc1 = ByteBuffer.allocate(data.length + crc1.length)
                .put(data).put(crc1).array();
        byte[] crc2 = calculateCRC32(dataWithCrc1, 0, dataWithCrc1.length);
        byte[] dataWithTwoCrc = ByteBuffer.allocate(dataWithCrc1.length + crc2.length)
                .put(dataWithCrc1).put(crc2).array();
        return dataWithTwoCrc;
    }

    public static byte[] stringToByteArray(String input, int length) {
        byte[] bytes = new byte[length];
        byte[] inputBytes = input.getBytes(StandardCharsets.UTF_8);

        // 如果输入字符串转换为的byte[]长度超过length，那么只拷贝前length个字节
        if (inputBytes.length > length) {
            System.arraycopy(inputBytes, 0, bytes, 0, length);
        }
        // 如果输入字符串转换为的byte[]长度小于或等于length，那么将整个inputBytes拷贝到bytes，并将剩余位置填充为0
        else {
            System.arraycopy(inputBytes, 0, bytes, 0, inputBytes.length);
            Arrays.fill(bytes, inputBytes.length, length, (byte) 0);
        }

        return bytes;
    }

    public static String getFormattedTimestampFromBytes(byte[] byteArray) {
        if (byteArray == null || byteArray.length != 4) {
            throw new IllegalArgumentException("Array is null or its length is not 4.");
        }

        // Create a ZoneId for UTC-8
        ZoneId zoneId = ZoneId.of("America/Los_Angeles");

        ByteBuffer byteBuffer = ByteBuffer.wrap(byteArray);
        byteBuffer.order(java.nio.ByteOrder.LITTLE_ENDIAN);
        int secondsPassed = byteBuffer.getInt();

        ZonedDateTime startingDate = ZonedDateTime.of(2021, 1, 1, 0, 0, 0, 0, zoneId);
        ZonedDateTime targetDate = startingDate.plusSeconds(secondsPassed);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return targetDate.format(formatter);
    }

    public static byte[] intToTwoBytes(int value) {
        byte[] result = new byte[2];

        // Using bit-wise AND and bit-shift to isolate each byte
        result[1] = (byte) ((value >> 8) & 0xFF);  // High byte (Most Significant Byte)
        result[0] = (byte) (value & 0xFF);        // Low byte (Least Significant Byte)

        return result;
    }


    public static byte calcCRC8(byte[] data) {
        int crc = 0xFF;
        if(data != null) {
            for (int i = 0; i < data.length; i++) {
                crc = CRC8_TABLE[crc ^ (data[i] & 0xFF)];
            }
        }
        return (byte) (crc & 0xFF);
    }
    public static byte[] intToByteArray(int data, int length) {
        if (length <= 0 || length > 4) {
            throw new IllegalArgumentException("Length must be between 1 and 4.");
        }

        // Allocate a ByteBuffer with a capacity of 4 bytes
        ByteBuffer buffer = ByteBuffer.allocate(4);
        buffer.order(ByteOrder.LITTLE_ENDIAN);

        // Insert the integer
        buffer.putInt(data);

        // Extract the array
        byte[] tempArray = buffer.array();

        // Create the result array
        byte[] result = new byte[length];

        // Copy the required bytes from tempArray to result
        System.arraycopy(tempArray, 4 - length, result, 0, length);

        return result;
    }

    public static int byteArrayToInt(byte[] byteArray) {
        if (byteArray.length > 4) {
            throw new IllegalArgumentException("Byte array length must not exceed 4.");
        }

        // 创建一个ByteBuffer并设置为小端序
        ByteBuffer buffer = ByteBuffer.allocate(4).order(java.nio.ByteOrder.LITTLE_ENDIAN);

        // 在转换之前填充0（如果必要）
        for (int i = 0; i < 4 - byteArray.length; i++) {
            buffer.put((byte) 0);
        }

        // 填充输入的byteArray
        buffer.put(byteArray);

        // 重置buffer的position
        buffer.rewind();

        // 将buffer转换为一个int并返回
        return buffer.getInt();
    }
}
