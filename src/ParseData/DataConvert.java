package ParseData;

import java.util.Locale;

public class DataConvert {
    private static final char[] HEX_ARRAY = "0123456789ABCDEF".toCharArray();

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

}
