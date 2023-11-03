package pgdata.DeviceLog;

import Controller.RandomData;
import DataStructure.DynamicByteBuffer;
import ParseData.DataConvert;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.*;
import java.util.stream.Collectors;

public class DeviceBatteryLog extends BaseLog {
    // index points to the next Battery Log to be read
    private byte index;
    private byte[] reserved = new byte[3];
    private int deviceMode = 0;
    private int testCaseId = 0;

    private class BatteryLog{
        private byte[] timestamp = new byte[4];
        private byte oldStatus = 0x00;
        private byte newStatus = 0x00;
        private byte Reserved1 = 0x00;
        private byte Reserved2 = 0x00;
        // 0x01 : BOL
        // 0x02 : RRT
        // 0x03 : EOS
        // 0x04 : EOS
        // Other: NA
        private byte[] batteryStatus = new byte[]{0x00, 0x01, 0x02, 0x03, 0x04};
        public BatteryLog(int deviceMode, int testCaseId){
            timestamp = RandomData.getTimePassedInSeconds(0,0,0);
            oldStatus = batteryStatus[deviceMode%batteryStatus.length];
            newStatus = batteryStatus[testCaseId%batteryStatus.length];
        }

        public byte[] getBatteryLog(){
            DynamicByteBuffer buffer = new DynamicByteBuffer();
            buffer.put(timestamp);
            buffer.put(oldStatus);
            buffer.put(newStatus);
            buffer.put(Reserved1);
            buffer.put(Reserved2);
            return buffer.toArray();
        }

        public byte[] getTimestamp(){
            return timestamp;
        }
    }

    public DeviceBatteryLog(int deviceMode, int testCaseId){
        packetHeader = new byte[]{0x37, 0x0F, 0x30};
        this.deviceMode = deviceMode;
        this.testCaseId = testCaseId;

    }

    public void buildPayload(DynamicByteBuffer buffer){
        List<BatteryLog> list = new ArrayList<>();
        for(int i = 0; i < 5; i++) {
            BatteryLog log = new BatteryLog(deviceMode, testCaseId);
            buffer.put(log.getBatteryLog());
            list.add(log);
        }
        // List here is used for debug purpose.
        // It will print out BatteryLog in timestamp order
        // delete the code below won't affect the functionalities.
        list.sort((b1, b2) -> {
            byte[] timestamp1 = b1.getTimestamp();
            byte[] timestamp2 = b2.getTimestamp();

            // Assuming timestamps are integers represented as byte arrays
            int time1 = ByteBuffer.wrap(timestamp1).order(ByteOrder.LITTLE_ENDIAN).getInt();
            int time2 = ByteBuffer.wrap(timestamp2).order(ByteOrder.LITTLE_ENDIAN).getInt();

            return time2 - time1;
        });

        // Print out sorted log entries
        for (BatteryLog log : list) {
            byte[] logBytes = log.getBatteryLog();
            System.out.println("The Timestamp is: " + DataConvert.getFormattedTimestampFromBytes(log.getTimestamp()) + " The old status is " + logBytes[4] + " and the new status is " + logBytes[5]);
        }

        //index = RandomData.generateRandomByte(20);
        // Programmer treats index as redundancy byte
        // Thus it's set to 0x00.
        index = 0x00;
        buffer.put(index);
        buffer.put(reserved);
    }

}
