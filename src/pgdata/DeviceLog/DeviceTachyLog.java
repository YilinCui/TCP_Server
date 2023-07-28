package pgdata.DeviceLog;

import Controller.RandomData;
import DataStructure.DynamicByteBuffer;
import ParseData.DataConvert;

public class DeviceTachyLog extends BaseLog {

    private byte[] packetHeader = {(byte)0x84, 0x5E, 0x10};
    private byte[] payload;
    private byte[] CRC32;

    private class TachyLog{
        private byte recordTime;
        private byte recordIndex;
        private byte[] timestamp;
        private byte recordReason;
        private byte recordMode;
        public TachyLog(){
            recordTime = RandomData.generateRandomByte();
            recordIndex = RandomData.generateRandomByte();
            timestamp = RandomData.generateRandomBytes(4);
            recordReason = RandomData.generateRandomByte();
            recordMode = RandomData.generateRandomByte();
        }

        public byte[] getTachyLog(){
            DynamicByteBuffer buffer = new DynamicByteBuffer();
            buffer.put(recordTime);
            buffer.put(recordIndex);
            buffer.put(timestamp);
            buffer.put(recordReason);
            buffer.put(recordMode);

            byte[] payload = buffer.toArray();

            byte[] CRC32 = DataConvert.calculateCRC32(payload, 0, payload.length);

            buffer.put(CRC32);

            return buffer.toArray();
        }
    }

    @Override
    public byte[] getbReturnData() {
        DynamicByteBuffer dataBuffer = new DynamicByteBuffer();
        DynamicByteBuffer buffer = new DynamicByteBuffer();
        for(int i = 0;i<10;i++){
            DeviceTachyLog.TachyLog log = new DeviceTachyLog.TachyLog();
            buffer.put(log.getTachyLog());
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
