package pgdata.DeviceLog;

import Controller.RandomData;
import DataStructure.DynamicByteBuffer;
import ParseData.DataConvert;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.*;

public class DeviceChargeLog extends BaseLog {
    private byte[] packetHeader1 = new byte[]{0x74, 0x05, 0x74};
    private byte[] packetHeader2 = new byte[]{(byte) 0x80, 0x09, 0x74};
    private byte[] payload1 = null;
    private byte[] payload2 = null;

    private class ChargeLog{
        private byte[] duration;
        private byte[] timestamp;
        private byte[] status;
        private byte[] disStart;
        private byte[] disEnd;
        private byte[] impedance;
        public ChargeLog(){
            duration = RandomData.generateRandomBytes(4);
            timestamp = RandomData.getTimePassedInSeconds();
            status = generateBytesinRange();
            //status = RandomData.generateRandomBytes(2);
            disStart = RandomData.generateRandomBytes(2);
            disEnd = RandomData.generateRandomBytes(2);
            impedance = RandomData.generateRandomBytes(2);
        }

        public byte[] getChargeLog(){
            DynamicByteBuffer buffer = new DynamicByteBuffer();
            buffer.put(duration);
            buffer.put(timestamp);
            buffer.put(status);
            buffer.put(disStart);
            buffer.put(disEnd);
            buffer.put(impedance);
            return buffer.toArray();
        }

        public byte[] getTimestamp(){
            return timestamp;
        }

        public byte[] getStatus(){
            return status;
        }

    }

    @Override
    public byte[] getbReturnData() {

        return null;
    }

    public byte[] getbReturnData(int chargeLog){
        DynamicByteBuffer buffer;
        List<ChargeLog> list = new ArrayList<>();
        bRetrunData = null;
        if(chargeLog==1){
            // for packet1, it contains packetHeader1 + 7 entries of ChargeLog + 4 bytes of CRC32
            DynamicByteBuffer dataBuffer = new DynamicByteBuffer();


            buffer = new DynamicByteBuffer();
            for(int i = 0;i<7;i++){
                DeviceChargeLog.ChargeLog log = new DeviceChargeLog.ChargeLog();
                buffer.put(log.getChargeLog());
                list.add(log);
            }

            dataBuffer.put(buffer.toArray());
            // 将ByteBuffer转化为byte数组
            payload1 = dataBuffer.toArray();

            byte[] parameterCRC32 = DataConvert.calculateCRC32(payload1, 0, payload1.length);
            dataBuffer.put(parameterCRC32);

            dataBuffer.put(0, packetHeader1);

            bRetrunData = dataBuffer.toArray();
        }else if(chargeLog==2){
            // for packet2, it contains
            // 1. packetHeader2 and 7 entries of ChargeLog
            // 2. 7 bytes of supplement
            // 3. 1 byte of charge_log_index
            // 4. CRC32 for the (14 entries of Charge Log and 7 bytes of supplement + 1 byte of charge_log_index)
            // 5. CRC32 for payload of packet2 and CRC32 in step 4.
            DynamicByteBuffer dataBuffer = new DynamicByteBuffer();
            buffer = new DynamicByteBuffer();
            for(int i = 0;i<7;i++){
                DeviceChargeLog.ChargeLog log = new DeviceChargeLog.ChargeLog();
                buffer.put(log.getChargeLog());
                list.add(log);
            }

            byte[] reserved = new byte[8];

            payload2 = buffer.toArray();
            dataBuffer.put(payload1);
            dataBuffer.put(payload2);
            dataBuffer.put(reserved);

            payload = dataBuffer.toArray();
            byte[] payloadCRC32 = DataConvert.calculateCRC32(payload, 0, payload.length);

            DynamicByteBuffer returnBuffer = new DynamicByteBuffer();

            returnBuffer.put(payload2);
            returnBuffer.put(reserved);
            returnBuffer.put(payloadCRC32);

            byte[] packetBody = returnBuffer.toArray();
            byte[] StructureCRC32 = DataConvert.calculateCRC32(packetBody, 0, packetBody.length);
            returnBuffer.put(StructureCRC32);
            returnBuffer.put(0,packetHeader2);

            bRetrunData = returnBuffer.toArray();

        }

        // Sort list based on the timestamps
        Collections.sort(list, new Comparator<ChargeLog>(){
            public int compare(ChargeLog b1, ChargeLog b2) {
                byte[] timestamp1 = b1.getTimestamp();
                byte[] timestamp2 = b2.getTimestamp();

                // Assuming timestamps are integers represented as byte arrays
                int time1 = ByteBuffer.wrap(timestamp1).order(ByteOrder.LITTLE_ENDIAN).getInt();
                int time2 = ByteBuffer.wrap(timestamp2).order(ByteOrder.LITTLE_ENDIAN).getInt();

                return time2 - time1;
            }
        });

        // Print out sorted log entries
        for (ChargeLog log : list) {

            System.out.println("The Timestamp is: " + DataConvert.getFormattedTimestampFromBytes(log.getTimestamp()) + " The status is " + DataConvert.byteDataToHexString(log.getStatus()));
        }

        if(bRetrunData!=null)
        return bRetrunData;

        else{
            System.out.println("Invalid chargeLogCount!");
            return null;
        }
    }

    public static byte[] generateBytesinRange() {
        // Create a Random object
        Random rand = new Random();

        // Define the range for each part
        int[] firstRange = {1, 2, 3, 6};  // Values for the first X
        int secondRange = 13;  // Range for the second X, from 0 to C (12 in decimal)
        int thirdRange = 12;   // Range for the third X, from 0 to B (11 in decimal)
        int fourthRange = 4;   // Range for the fourth X, from 0 to 3

        // Generate random numbers within the specified range
        int firstX = firstRange[rand.nextInt(firstRange.length)];
        int secondX = rand.nextInt(secondRange);
        int thirdX = rand.nextInt(thirdRange);
        int fourthX = rand.nextInt(fourthRange);

        // Combine the two parts of each byte
        byte firstByte = (byte) ((firstX << 4) | secondX);
        byte secondByte = (byte) ((thirdX << 4) | fourthX);

        // Return the byte array
        return new byte[] {firstByte, secondByte};
    }

}
