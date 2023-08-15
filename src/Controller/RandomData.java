package Controller;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.charset.StandardCharsets;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Generate Random Data
 * All the methods in this class should be public static
 */
public class RandomData {
    /**
     * Generate a byte array with size = length, and fill in the array with random bytes.
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
    public static byte generateRandomByte(int max) {
        Random rand = new Random();
        int limit = Math.min(max, 127);
        return (byte) rand.nextInt(limit + 1);
    }

    public static int generateRandomInt(int length) {
        Random random = new Random();
        return random.nextInt(length); // 这里的16是生成随机数的上限，不包括这个数本身。
    }

    public static byte[] getTimePassedInSeconds() {
        // Get the current date and the date one month ago.
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime oneMonthAgo = now.minusMonths(1);

        // Generate a random date between one month ago and now.
        long secondsOneMonthAgo = oneMonthAgo.toEpochSecond(ZoneOffset.UTC);
        long secondsNow = now.toEpochSecond(ZoneOffset.UTC);
        long randomEpochSecond = ThreadLocalRandom.current().nextLong(secondsOneMonthAgo, secondsNow);

        // Calculate the number of seconds from 2021/1/1 to the random date.
        LocalDateTime startingDate = LocalDateTime.of(2021, 1, 1, 0, 0);
        long startingEpochSecond = startingDate.toEpochSecond(ZoneOffset.UTC);
        long secondsPassed = randomEpochSecond - startingEpochSecond;

        // Convert the number of seconds to a 4-byte array with Little-Endian order.
        ByteBuffer byteBuffer = ByteBuffer.allocate(4);
        byteBuffer.order(java.nio.ByteOrder.LITTLE_ENDIAN);
        byteBuffer.putInt((int) secondsPassed);
        return byteBuffer.array();
    }

    public static byte[] addSecondsToTime(byte[] time, int secondsToAdd) {
        // Convert the 4-byte array to an integer using Little-Endian order.
        ByteBuffer byteBuffer = ByteBuffer.wrap(time);
        byteBuffer.order(java.nio.ByteOrder.LITTLE_ENDIAN);
        int secondsPassed = byteBuffer.getInt();

        // Add the specified number of seconds.
        secondsPassed += secondsToAdd;

        // Convert the modified number of seconds back to a 4-byte array.
        byteBuffer = ByteBuffer.allocate(4);
        byteBuffer.order(java.nio.ByteOrder.LITTLE_ENDIAN);
        byteBuffer.putInt(secondsPassed);
        return byteBuffer.array();
    }

    // Method to calculate a timestamp from a 4-byte array and return it as a formatted string



    // Method to get a random byte from the given array
    public static byte getRandomByteFromArray(byte[] array) {
        // Check if array is empty
        if (array == null || array.length == 0) {
            throw new IllegalArgumentException("Array is empty.");
        }

        // Create a Random object
        Random rand = new Random();

        // Get a random index from 0 to array length - 1
        int randomIndex = rand.nextInt(array.length);

        // Return the byte at the random index
        return array[randomIndex];
    }

    public static byte[] getRandomArrayFrom2DArray(byte[][] arrays) {
        // 检查数组是否为空
        if (arrays == null || arrays.length == 0) {
            throw new IllegalArgumentException("Array is empty.");
        }

        // 创建一个Random对象
        Random rand = new Random();

        // 从0到数组长度-1中获取一个随机索引
        int randomIndex = rand.nextInt(arrays.length);

        // 返回随机索引处的字节数组
        return arrays[randomIndex];
    }

    public static byte[] generateRandomLittleEndianBytes(int max) {
        Random rand = new Random();

        // Generate a random integer in the range [1, 127]
        int randomNumber = rand.nextInt(max) + 1;

        // Convert the integer to bytes in little endian order
        ByteBuffer bb = ByteBuffer.allocate(4);
        bb.order(ByteOrder.LITTLE_ENDIAN);
        bb.putInt(randomNumber);

        return bb.array();
    }

    public static byte[] generateBytesinRange() {
        // Create a Random object
        Random rand = new Random();

        // Define the range for each part
        int[] firstRange = {1, 2, 3, 6};  // Values for the first X
        int secondRange = 13;  // Range for the second X, from 0 to C (12 in decimal)
        int[] thirdRange = {1,2,3,4,5,8,9,10, 11};   // Range for the third X, from 1 to B (11 in decimal)
        int fourthRange = 4;   // Range for the fourth X, from 0 to 3

        // Generate random numbers within the specified range
        int firstX = firstRange[rand.nextInt(firstRange.length)];
        int secondX = rand.nextInt(secondRange);
        int thirdX = thirdRange[rand.nextInt(thirdRange.length)];
        int fourthX = rand.nextInt(fourthRange);

        // Combine the two parts of each byte
        byte firstByte = (byte) ((firstX << 4) | secondX);
        byte secondByte = (byte) ((thirdX << 4) | fourthX);

        // Return the byte array
        return new byte[] {firstByte, secondByte};
    }

}
