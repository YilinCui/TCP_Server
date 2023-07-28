package pgdata.DeviceLog;

import Controller.RandomData;
import DataStructure.DynamicByteBuffer;
import ParseData.DataConvert;

public class DeviceTachyLog extends BaseLog {
    private byte size;
    private byte index;
    private byte r1;
    private byte r2;

    private byte[] packetHeader = {(byte)0x84, 0x5E, 0x10};
    private byte[] payload;
    private byte[] CRC32;

    private class TachyLog{
        private byte[] timestamp;
        private byte recordReason; // only 5 reasons
        private byte recordMode;
        public TachyLog(){
            timestamp = RandomData.getTimePassedInSeconds();
            recordMode = RandomData.generateRandomByte(2); // 3 modes in total
            recordReason = RandomData.generateRandomByte(4); //5 reasons

        }

        public byte[] getTachyLog(){
            DynamicByteBuffer buffer = new DynamicByteBuffer();
            buffer.put(timestamp);
            buffer.put(recordMode);
            buffer.put(recordReason);


            return buffer.toArray();
        }
    }

    @Override
    public byte[] getbReturnData() {
        DynamicByteBuffer dataBuffer = new DynamicByteBuffer();

        size = RandomData.generateRandomByte();
        index = RandomData.generateRandomByte();
        r1 = RandomData.generateRandomByte();
        r2 = RandomData.generateRandomByte();
        dataBuffer.put(size);
        dataBuffer.put(index);
        dataBuffer.put(r1);
        dataBuffer.put(r2);

        for(int i = 0;i<20;i++){
            DeviceTachyLog.TachyLog log = new DeviceTachyLog.TachyLog();
            dataBuffer.put(log.getTachyLog());
        }

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
