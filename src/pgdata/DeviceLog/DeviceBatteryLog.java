package pgdata.DeviceLog;

import Controller.RandomData;
import DataStructure.DynamicByteBuffer;
import ParseData.DataConvert;

public class DeviceBatteryLog extends BaseDeviceLog{

    private class BatteryLog{
        private byte[] timestamp;
        private byte oldStatus;
        private byte newStatus;
        private byte Reserved1 = 0x00;
        private byte Reserved2 = 0x00;
        public BatteryLog(){
            timestamp = RandomData.generateRandomBytes(4);
            oldStatus = RandomData.generateRandomByte();
            newStatus = RandomData.generateRandomByte();
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

    @Override
    public byte[] getbRetrunData() {
        DynamicByteBuffer dataBuffer = new DynamicByteBuffer();
        buffer = new DynamicByteBuffer();
        for(int i = 0;i<5;i++){
            BatteryLog log = new BatteryLog();
            buffer.put(log.getBatteryLog());
        }

        payload = null;

        dataBuffer.put(0,buffer.toArray());
        // 将ByteBuffer转化为byte数组
        payload = dataBuffer.toArray();

        byte[] parameterCRC32 = DataConvert.calculateCRC32(payload, 0, payload.length);
        dataBuffer.put(parameterCRC32);

        byte[] packetBody = dataBuffer.toArray();
        byte[] CRC32 = DataConvert.calculateCRC32(packetBody, 0, packetBody.length);

        dataBuffer.put(CRC32);
        dataBuffer.put(0, packetHeader);

        bRetrunData = dataBuffer.toArray();

        return bRetrunData;
    }
}
