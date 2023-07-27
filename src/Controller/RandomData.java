package Controller;

import java.nio.charset.StandardCharsets;
import java.util.Random;

/**
 * Generate Random Data
 * All the methods in this class should be public static
 */
public class RandomData {
    /**
     * Generate a byte array, and fill in the array with random bytes.
     * @param length
     * @return byte array
     */
    public static byte[] generateRandomBytes(int length) {
        byte[] array = new byte[length];
        Random random = new Random();
        for (int i = 0; i < length; i++) {
            // 生成0到127之间的随机字节
            array[i] = (byte) random.nextInt(128);
        }
        return array;
    }
    // Generate a random byte between [-128,127]
    public static byte generateRandomByte() {
        Random random = new Random();
        byte[] bytes = new byte[1];
        random.nextBytes(bytes);
        return bytes[0];
    }
    // Generate a random byte no larger than length
    public static byte generateRandomByte(int length) {
        Random rand = new Random();
        int limit = Math.min(length, 127);
        return (byte) rand.nextInt(limit + 1);
    }

    public static int generateRandomInt(int length) {
        Random random = new Random();
        return random.nextInt(length); // 这里的16是生成随机数的上限，不包括这个数本身。
    }

}
