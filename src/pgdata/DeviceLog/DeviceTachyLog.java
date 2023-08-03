package pgdata.DeviceLog;

import Controller.RandomData;
import DataStructure.DynamicByteBuffer;
import ParseData.DataConvert;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.ArrayList;
import java.util.List;

public class DeviceTachyLog extends BaseLog {
    private byte size;
    private byte index;
    private byte r1;
    private byte r2;
    int deviceMode = 0;
    int testCaseId = 0;

    private byte[] payload;
    private byte[] CRC32;

    public DeviceTachyLog(){
        packetHeader = new byte[]{(byte) 0x84, 0x5E, 0x10};
    }

    public DeviceTachyLog(int deviceMode, int testCaseId){
        this();
        this.deviceMode = deviceMode;
        this.testCaseId = testCaseId;
    }

    private class TachyLog{
        private byte[] timestamp;
        private byte recordReason; // only 5 reasons
        private byte recordMode; // 3 modes in total

        public TachyLog(int deviceMode, int testCaseId){

            timestamp = RandomData.getTimePassedInSeconds();
            recordMode = RandomData.generateRandomByte(2); // 3 modes in total
            recordReason = RandomData.generateRandomByte(4); //5 reasons

            if(deviceMode==0){
                // default

            }else if(deviceMode==1){
                timestamp = new byte[4];
                if(testCaseId==0){

                }else if(testCaseId==1){
                    recordMode = 0x00; // 3 modes in total
                    recordReason = 0x00; //5 reasons
                }else if(testCaseId==2){
                    timestamp = new byte[4];
                    recordReason = 0x00; //5 reasons
                }else if(testCaseId==3){
                    timestamp = new byte[4];
                    recordMode = 0x00; // 3 modes in total
                    recordReason = RandomData.generateRandomByte(4); //5 reasons
                }
            }

        }
        public TachyLog(){}

        public byte[] getTachyLog(){
            DynamicByteBuffer buffer = new DynamicByteBuffer();
            buffer.put(timestamp);
            buffer.put(recordMode);
            buffer.put(recordReason);
            return buffer.toArray();
        }
        public byte[] getTimestamp(){
            return timestamp;
        }
    }

    public void buildPayload(DynamicByteBuffer buffer){
        size = RandomData.generateRandomByte();
        index = RandomData.generateRandomByte();
        r1 = RandomData.generateRandomByte();
        r2 = RandomData.generateRandomByte();
        if(deviceMode==0){

        }else if(deviceMode==1){
            size = 0x00;
            index = 0x00;
            r1 = 0x00;
            r2 = 0x00;
        }
        buffer.put(size);
        buffer.put(index);
        buffer.put(r1);
        buffer.put(r2);

        List<TachyLog> list = new ArrayList<>();

        for(int i = 0;i<20;i++){
            DeviceTachyLog.TachyLog log = new DeviceTachyLog.TachyLog(deviceMode, testCaseId);
            buffer.put(log.getTachyLog());
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
        for (TachyLog log : list) {
            byte[] logBytes = log.getTachyLog();
            System.out.println("The Timestamp is: " + DataConvert.getFormattedTimestampFromBytes(log.getTimestamp()) + " The mode is " + logBytes[4] + " and the reason is " + logBytes[5]);
        }
    }
}
