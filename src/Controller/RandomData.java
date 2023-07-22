package Controller;

import java.nio.charset.StandardCharsets;
import java.util.Random;

/**
 * Generate Random Data
 * All the methods in this class should be public static
 */
public class RandomData {
    public static byte[] generateRandomBytes(int length) {
        byte[] array = new byte[length];
        Random random = new Random();
        for (int i = 0; i < length; i++) {
            // 生成0到127之间的随机字节
            array[i] = (byte) random.nextInt(128);
        }
        return array;
    }

}
