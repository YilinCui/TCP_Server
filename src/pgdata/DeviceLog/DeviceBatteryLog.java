package pgdata.DeviceLog;

import Controller.RandomData;
import DataStructure.DynamicByteBuffer;
import ParseData.DataConvert;

public class DeviceBatteryLog extends BaseLog {
    private byte index;
    private byte[] reserved = new byte[3];
    private class BatteryLog{
        private byte[] timestamp;
        private byte oldStatus;
        private byte newStatus;
        private byte Reserved1 = 0x00;
        private byte Reserved2 = 0x00;
        public BatteryLog(){
            timestamp = RandomData.getTimePassedInSeconds();
            oldStatus = RandomData.generateRandomByte(6);
            newStatus = RandomData.generateRandomByte(6);
            packetHeader = new byte[]{0x34, 0x0F, 0x30};
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
    }

    public void buildPayload(DynamicByteBuffer buffer){
        for(int i = 0;i<5;i++){
            BatteryLog log = new BatteryLog();
            buffer.put(log.getBatteryLog());
        }
        index = RandomData.generateRandomByte(20);
        buffer.put(index);
        buffer.put(reserved);
    }

}
