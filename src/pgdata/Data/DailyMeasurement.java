package pgdata.Data;

import Controller.RandomData;
import DataStructure.DynamicByteBuffer;
import ParseData.DecodingPacket;
import pgdata.DeviceLog.BaseLog;

public class DailyMeasurement extends BaseLog {
    private int deviceMode;
    private int testCaseId;
    private int days;
    public DailyMeasurement(DecodingPacket packet, int deviceMode, int testCaseId){
        days = packet.getpayload()[2];
        packetHeader = new byte[]{(byte)(days*16+4), 0x04, 0x58};
        this.deviceMode = deviceMode;
        this.testCaseId = testCaseId;
    }

    private class Measurement{
        private byte[] meansTime = new byte[4];
        private byte[] paceLeadImpedance = new byte[2];
        private byte[] shockLeadImpedance = new byte[2];
        private byte[] batteryValue = new byte[2];
        private byte afBurden;
        private byte reserved = 0x00;
        private byte[] intrinsicMeasurement = new byte[2];
        private byte[] reserved2 = new byte[2];

        public Measurement(int deviceMode, int testCaseId){
            meansTime = RandomData.getTimePassedInSeconds();
            paceLeadImpedance = RandomData.generateRandomBytes(2);
            shockLeadImpedance = RandomData.generateRandomBytes(2);
            batteryValue = RandomData.generateRandomBytes(2);
            afBurden = RandomData.generateRandomByte();
            intrinsicMeasurement = RandomData.generateRandomBytes(2);
        }
        public byte[] getMeasurement(){
            DynamicByteBuffer buffer = new DynamicByteBuffer();
            buffer.put(meansTime);
            buffer.put(paceLeadImpedance);
            buffer.put(shockLeadImpedance);
            buffer.put(batteryValue);
            buffer.put(afBurden);
            buffer.put(reserved);
            buffer.put(intrinsicMeasurement);
            buffer.put(reserved2);
            return buffer.toArray();
        }
    }

    public void buildPayload(DynamicByteBuffer buffer){
        for(int i=0;i<days;i++){
            Measurement daily = new Measurement(deviceMode, testCaseId);
            buffer.put(daily.getMeasurement());
        }

    }
}