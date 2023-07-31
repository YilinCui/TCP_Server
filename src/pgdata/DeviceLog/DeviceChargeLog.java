package pgdata.DeviceLog;

import Controller.RandomData;
import DataStructure.DynamicByteBuffer;
import ParseData.DataConvert;

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
            status = RandomData.generateRandomBytes(2);
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
    }

    private class ChargeLog2{
        private byte[] totalChargeDuration;
        private byte numberOfEntries;
        private byte[] reserved = new byte[3];
        public ChargeLog2(){
            totalChargeDuration = RandomData.generateRandomBytes(4);
            numberOfEntries = RandomData.generateRandomByte();
        }

        public byte[] getChargeLog2(){
            DynamicByteBuffer buffer = new DynamicByteBuffer();
            buffer.put(totalChargeDuration);
            buffer.put(numberOfEntries);
            buffer.put(reserved);
            return buffer.toArray();
        }
    }

    @Override
    public byte[] getbReturnData() {

        return null;
    }

    public byte[] getbReturnData(int chargeLog){
        DynamicByteBuffer buffer;
        if(chargeLog==1){
            // for packet1, it contains packetHeader1 + 7 entries of ChargeLog + 4 bytes of CRC32
            DynamicByteBuffer dataBuffer = new DynamicByteBuffer();
            buffer = new DynamicByteBuffer();
            for(int i = 0;i<7;i++){
                DeviceChargeLog.ChargeLog log = new DeviceChargeLog.ChargeLog();
                buffer.put(log.getChargeLog());
            }

            payload1 = null;

            dataBuffer.put(buffer.toArray());
            // 将ByteBuffer转化为byte数组
            payload1 = dataBuffer.toArray();

            byte[] parameterCRC32 = DataConvert.calculateCRC32(payload1, 0, payload1.length);
            dataBuffer.put(parameterCRC32);

            dataBuffer.put(0, packetHeader1);

            bRetrunData = dataBuffer.toArray();
            return bRetrunData;
        }else if(chargeLog==2){
            // for packet2, it contains
            // 1. packetHeader2 and 7+7 entries of ChargeLog
            // 2. 8 bytes of supplement
            // 3. CRC32 for the (14 entries of Charge Log and 8 bytes of supplement)
            // 4. CRC32 for (payload of packet2 + 8 bytes of supplement + CRC32 from step3)
            DynamicByteBuffer dataBuffer = new DynamicByteBuffer();
            buffer = new DynamicByteBuffer();
            for(int i = 0;i<7;i++){
                DeviceChargeLog.ChargeLog log = new DeviceChargeLog.ChargeLog();
                buffer.put(log.getChargeLog());
            }

            payload2 = buffer.toArray();
            dataBuffer.put(payload1);
            dataBuffer.put(payload2);

            DeviceChargeLog.ChargeLog2 log2 = new DeviceChargeLog.ChargeLog2();
            dataBuffer.put(log2.getChargeLog2());

            payload = dataBuffer.toArray();
            byte[] payloadCRC32 = DataConvert.calculateCRC32(payload, 0, payload.length);

            DynamicByteBuffer returnBuffer = new DynamicByteBuffer();

            returnBuffer.put(payload2);
            returnBuffer.put(log2.getChargeLog2());
            returnBuffer.put(payloadCRC32);

            byte[] packetBody = returnBuffer.toArray();
            byte[] StructureCRC32 = DataConvert.calculateCRC32(packetBody, 0, packetBody.length);
            returnBuffer.put(StructureCRC32);
            returnBuffer.put(0,packetHeader2);

            bRetrunData = returnBuffer.toArray();

            return bRetrunData;
        }
        System.out.println("Invalid chargeLogCount!");
        return null;
    }
}
