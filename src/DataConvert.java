import java.util.Locale;

public class DataConvert {
    private static final char[] HEX_ARRAY = "0123456789ABCDEF".toCharArray();
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
}
