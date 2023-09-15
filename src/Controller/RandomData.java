package Controller;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.charset.StandardCharsets;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.List;
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
        if (max < 0 || max > 255) {
            throw new IllegalArgumentException("Max must be between 0 and 255.");
        }

        Random rand = new Random();
        int randomInt = rand.nextInt(max + 1); // 0到max之间
        return (byte) randomInt; // 转换为字节
    }

    public static int getRandomNumberInRange(int min, int max) {
        if (min >= max) {
            throw new IllegalArgumentException("最大值必须大于最小值");
        }

        Random r = new Random();
        return r.nextInt((max - min) + 1) + min;
    }


    public static byte[] getTimePassedInSeconds() {
        // 使用系统默认的时区
        ZoneId zoneId = ZoneId.systemDefault();

        // 获取当前日期和一个小时之前的日期
        ZonedDateTime now = ZonedDateTime.now(zoneId);
        ZonedDateTime oneHourAgo = now.minusHours(1);

        // 转换为epoch秒
        long secondsOneHourAgo = oneHourAgo.toEpochSecond();
        long secondsNow = now.toEpochSecond();
        long randomEpochSecond = ThreadLocalRandom.current().nextLong(secondsOneHourAgo, secondsNow);

        // 这应该是你在uint32ToTimeString中使用的相同的EPOCH_TIME_20210101
        long startingEpochSecond = 1609459200/* 在这里插入EPOCH_TIME_20210101的值 */;

        // 从2021/1/1计算到随机日期的秒数
        long secondsPassed = randomEpochSecond - startingEpochSecond;

        // 将秒数转换为一个4字节的数组（小端序）
        ByteBuffer byteBuffer = ByteBuffer.allocate(4);
        byteBuffer.order(java.nio.ByteOrder.LITTLE_ENDIAN);
        byteBuffer.putInt((int) secondsPassed);
        return byteBuffer.array();
    }

    public static byte[] getTimePassedInSeconds(boolean useOneHourWindow) {
        // Use system's default timezone
        ZoneId zoneId = ZoneId.systemDefault();

        // Get the current date and an hour ago date
        ZonedDateTime now = ZonedDateTime.now(zoneId);
        long secondsNow = now.toEpochSecond();

        long randomEpochSecond;

        // Choose calculation logic based on the boolean parameter
        if (useOneHourWindow) {
            ZonedDateTime oneHourAgo = now.minusHours(1);
            long secondsOneHourAgo = oneHourAgo.toEpochSecond();
            randomEpochSecond = ThreadLocalRandom.current().nextLong(secondsOneHourAgo, secondsNow);
        } else {
            // Here you put the EPOCH_TIME_20210101 value, which is 1609459200
            long startingEpochSecond = 1609459200;
            randomEpochSecond = ThreadLocalRandom.current().nextLong(startingEpochSecond, secondsNow);
        }

        // Calculate seconds passed since 2021/1/1
        long startingEpochSecond = 1609459200; // Insert EPOCH_TIME_20210101 value here
        long secondsPassed = randomEpochSecond - startingEpochSecond;

        // Convert the seconds into a 4-byte array (little-endian)
        ByteBuffer byteBuffer = ByteBuffer.allocate(4);
        byteBuffer.order(java.nio.ByteOrder.LITTLE_ENDIAN);
        byteBuffer.putInt((int) secondsPassed);
        return byteBuffer.array();
    }

    public static byte[] getTimePassedInSeconds(int years, int months, int days) {
        ZoneId zoneId = ZoneId.systemDefault();

        // Base date is 2021/01/01
        ZonedDateTime baseTime = ZonedDateTime.of(2021, 1, 1, 0, 0, 0, 0, zoneId);

        // Add years, months, and days to the base date
        ZonedDateTime specificTime = baseTime.plusYears(years).plusMonths(months).plusDays(days);

        long specificEpochSecond = specificTime.toEpochSecond();

        // This should be the same EPOCH_TIME_20210101 that you use in uint32ToTimeString
        long startingEpochSecond = 1609459200L;

        // Calculate the number of seconds from 2021/1/1 to the specific date
        long secondsPassed = specificEpochSecond - startingEpochSecond;

        // Convert the number of seconds to a 4-byte array (little-endian)
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

    public static byte getRandomIntegerFromList(List<Byte> list) {
        // 检查列表是否为空或为null
        if (list == null || list.isEmpty()) {
            throw new IllegalArgumentException("List must not be null or empty");
        }

        // 创建Random对象用于生成随机索引
        Random random = new Random();

        // 从列表的范围内随机选择一个索引
        int randomIndex = random.nextInt(list.size());

        // 返回对应索引处的整数
        return list.get(randomIndex);
    }

    public static byte[] generateTherapy(int testCaseId) {
        byte[] therapies = new byte[40];
        Random rand = new Random();

        if (testCaseId == 0) {
            return therapies;
        }

        if (testCaseId == 1) {
            for (int i = 0; i < 40; i += 4) {
                therapies[i] = generateNonShock(rand);
            }
            return therapies;
        }

        if (testCaseId == 2) {
            for (int i = 0; i < 40; i += 4) {
                therapies[i] = generateShock(rand);
            }
            return therapies;
        }

        if (testCaseId == 3) {
            for (int i = 0; i < 40; i += 4) {
                if (i < 20) {
                    therapies[i] = generateNonShock(rand);
                } else {
                    therapies[i] = generateShock(rand);
                }
            }
            return therapies;
        }

        return therapies;
    }

    // Generate a random Shock byte
    public static byte generateShock(Random rand) {
        int base = rand.nextInt(4) * 0x40; // Randomly choose between 0x00, 0x40, 0x80, 0xC0
        int offset = rand.nextInt(16); // Randomly choose between 0x0 to 0xF
        return (byte) (base + offset);
    }

    // Generate a random non-Shock byte
    public static byte generateNonShock(Random rand) {
        byte result;
        do {
            result = (byte) rand.nextInt(256); // Generate a random byte between 0x00 and 0xFF
        } while ((result & 0xF0) == 0x00 || (result & 0xF0) == 0x40 || (result & 0xF0) == 0x80 || (result & 0xF0) == 0xC0);
        return result;
    }

    public static byte getRandomByte(byte min1, byte max1, byte min2, byte max2) {
        int rand1 = (int)min1 + (int)(Math.random() * ((int)max1 - (int)min1 + 1));
        int rand2 = (int)min2 + (int)(Math.random() * ((int)max2 - (int)min2 + 1));

        // Convert to unsigned integers for bit manipulation
        int unsignedRand1 = rand1 & 0xFF;
        int unsignedRand2 = rand2 & 0xFF;

        return (byte) ((unsignedRand1 << 4) | unsignedRand2);
    }

    public static byte generateByte(int bit3, int bit2, int bit1, int bit0) {
        // Initialize to 0
        byte result = 0b00000000;
        Random rand = new Random();

        // Set or randomize the bits based on the int values
        result |= handleBit(bit3, 7);
        result |= handleBit(bit2, 6);
        result |= handleBit(bit1, 5);
        result |= handleBit(bit0, 4);

        // Generate 4 random bits for the least significant bits
        byte randomBits = (byte) (rand.nextInt(16)); // Generate a random number between 0 and 15 (inclusive)

        // Combine the random bits with the existing result
        result |= randomBits;

        return result;
    }

    private static int handleBit(int bitValue, int position) {
        Random rand = new Random();
        if (bitValue == 1) {
            return 1 << position;
        } else if (bitValue == -1) {
            return rand.nextInt(2) << position;
        }
        return 0;
    }

    public static byte[] createByteArray(int length, int minValue, int maxValue) {
        if (length <= 0 || length > 4) {
            throw new IllegalArgumentException("The length should be between 1 and 4.");
        }

        if (minValue < 0 || maxValue < 0 || maxValue < minValue) {
            throw new IllegalArgumentException("minValue and maxValue should be non-negative, and maxValue should be greater than or equal to minValue.");
        }

        byte[] result = new byte[length];
        Random random = new Random();

        // 生成一个在 minValue 和 maxValue 之间的随机整数
        int randomValue = random.nextInt((maxValue - minValue) + 1) + minValue;

        // 使用位操作来填充数组，这里假设系统是小端序（Least Significant Byte first）
        for (int i = 0; i < length; i++) {
            result[i] = (byte) (randomValue & 0xFF);  // 取最低8位
            randomValue >>= 8;  // 右移8位，准备取下一个 byte
        }

        return result;
    }


}
