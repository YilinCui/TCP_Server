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
    private int testCaseId = 0;

    private class BatteryLog{
        private byte[] timestamp;
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
        public BatteryLog(){
            //timestamp = RandomData.getTimePassedInSeconds();
            timestamp = new byte[]{0x00,0x00,0x00,0x00};
            while (oldStatus == newStatus){
                oldStatus = RandomData.getRandomByteFromArray(batteryStatus);
                newStatus = RandomData.getRandomByteFromArray(batteryStatus);
            }
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

    public DeviceBatteryLog(){

    }

    public DeviceBatteryLog(int testCaseId){
        this.testCaseId = testCaseId;
    }

    public void buildPayload(DynamicByteBuffer buffer){
        packetHeader = new byte[]{0x34, 0x0F, 0x30};
        //HashSet<BatteryLog> set = new HashSet<>();
        for(int i = 0;i<5;i++){
            BatteryLog log = new BatteryLog();
            buffer.put(log.getBatteryLog());
            //set.add(log);
        }

//        // Converting and sorting HashSet in one line
//        List<BatteryLog> list = set.stream()
//                .sorted((b1, b2) -> {
//                    byte[] timestamp1 = b1.getTimestamp();
//                    byte[] timestamp2 = b2.getTimestamp();
//
//                    // Assuming timestamps are integers represented as byte arrays
//                    int time1 = ByteBuffer.wrap(timestamp1).order(ByteOrder.LITTLE_ENDIAN).getInt();
//                    int time2 = ByteBuffer.wrap(timestamp2).order(ByteOrder.LITTLE_ENDIAN).getInt();
//
//                    return time2 - time1;
//                })
//                .collect(Collectors.toList());
//
//
//        // Print out sorted log entries
//        for (BatteryLog log : list) {
//            byte[] logBytes = log.getBatteryLog();
//            System.out.println("The Timestamp is: " + DataConvert.getFormattedTimestampFromBytes(log.getTimestamp()) + " The old status is " + logBytes[4] + " and the new status is " + logBytes[5]);
//        }

        //index = RandomData.generateRandomByte(20);
        index = 0x00; // Programmer treats index as redundancy byte
        buffer.put(index);
        buffer.put(reserved);
    }

}
