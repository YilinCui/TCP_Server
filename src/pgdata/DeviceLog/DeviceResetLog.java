package pgdata.DeviceLog;

import Controller.RandomData;
import DataStructure.DynamicByteBuffer;
import ParseData.DataConvert;

/**
 * All the device logs are generated by Device itself
 * The logs have nothing to do with programmer itself
 */
public class DeviceResetLog extends BaseLog {
    private byte actual_number;
    private byte write_index;
    private byte[] m1_reset_count;
    public DeviceResetLog(){
        packetHeader = new byte[]{(byte) 0x84, 0x59, 0x11};
    }

    private class ResetLog{
        private byte[] DeviceIdList = new byte[]{0x00, 0x54, 0x14, 0x50}; // M1, M2, M3, BLE
        private byte DeviceId;
        private byte resetReason;
        private byte[] timestamp;
        private byte[] operataionIdLog;
        public ResetLog(){
            DeviceId = DeviceIdList[RandomData.generateRandomByte(3)];
            resetReason = RandomData.generateRandomByte();
            timestamp = RandomData.getTimePassedInSeconds(); // length 4
            operataionIdLog = RandomData.generateRandomBytes(6);
        }

        public byte[] getResetLog(){
            DynamicByteBuffer buffer = new DynamicByteBuffer();
            buffer.put(DeviceId);
            buffer.put(resetReason);
            buffer.put(timestamp);
            buffer.put(operataionIdLog);
            return buffer.toArray();
        }
    }

    public byte[] getbReturnData() {
        DynamicByteBuffer dataBuffer = new DynamicByteBuffer();

        actual_number = RandomData.generateRandomByte();
        write_index = RandomData.generateRandomByte();
        m1_reset_count = RandomData.generateRandomBytes(2);

        dataBuffer.put(actual_number);
        dataBuffer.put(write_index);

        for(int i = 0;i<10;i++){
            DeviceResetLog.ResetLog log = new DeviceResetLog.ResetLog();
            dataBuffer.put(log.getResetLog());
        }

        dataBuffer.put(m1_reset_count);
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
