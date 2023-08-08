package pgdata.DeviceLog;

import Constant.Constant;
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
        public ChargeLog(){}

        public ChargeLog(int deviceMode, int testCaseId){
            // 4 bytes
            duration = RandomData.generateRandomLittleEndianBytes(300);
            // 4 bytes
            timestamp = RandomData.getTimePassedInSeconds();
            // 2 bytes
            status = RandomData.generateBytesinRange();
            // 2 bytes
            disStart = RandomData.generateRandomBytes(2);
            // 2 bytes
            disEnd = RandomData.generateRandomBytes(2);
            // 2 bytes
            impedance = RandomData.generateRandomBytes(2);

            if(deviceMode==0){

            }else if(deviceMode==1){
                switch (testCaseId){
                    case 0:{
                        timestamp = new byte[4];
                        duration = new byte[4];
                        status = new byte[2];
                        break;
                    }
                    case 1:{
                        // Randomized test
                        break;
                    }
                    case 2:{
                        duration = new byte[4];
                        break;
                    }
                    case 3:{
                        status = new byte[2];
                        break;
                    }
                    case 4:{
                        timestamp = new byte[4];
                        break;
                    }
                }
            }
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

    public byte[] getbReturnData(int deviceMode, int testCaseId, int chargeLog){
        DynamicByteBuffer buffer;
        List<ChargeLog> list = new ArrayList<>();
        bRetrunData = null;
        if(chargeLog==1){
            // for packet1, it contains packetHeader1 + 7 entries of ChargeLog + 4 bytes of CRC32
            DynamicByteBuffer dataBuffer = new DynamicByteBuffer();

            buffer = new DynamicByteBuffer();
            for(int i = 0;i<7;i++){
                DeviceChargeLog.ChargeLog log = new DeviceChargeLog.ChargeLog(deviceMode, testCaseId);
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
                DeviceChargeLog.ChargeLog log = new DeviceChargeLog.ChargeLog(deviceMode, testCaseId);
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



}
