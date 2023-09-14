package pgdata.Data;

import Controller.RandomData;
import DataStructure.DynamicByteBuffer;
import ParseData.DataConvert;
import ParseData.DecodingPacket;
import pgdata.DeviceLog.BaseLog;

public class DailyMeasurement extends BaseLog {
    private int deviceMode;
    private int testCaseId;
    private int days;

    public DailyMeasurement(DecodingPacket packet, int deviceMode, int testCaseId) {
        days = packet.getpayload()[2];
        packetHeader = new byte[]{(byte) (days * 16 + 4), 0x04, 0x58};
        this.deviceMode = deviceMode;
        this.testCaseId = testCaseId;
    }

    private class Measurement {
        private byte[] meansTime = new byte[4];
        private byte[] paceLeadImpedance = new byte[2];
        private byte[] shockLeadImpedance = new byte[2];
        private byte[] batteryValue = new byte[2];
        private byte[] RV = new byte[2];
        private byte vSensed;
        private byte batteryLevel;
        private byte reserved = 0x00;
        private byte crc8;

        public Measurement(int deviceMode, int testCaseId) {
            meansTime = RandomData.getTimePassedInSeconds();
            paceLeadImpedance = RandomData.createByteArray(2, 200, 3000);
            shockLeadImpedance = RandomData.createByteArray(2, 40,200);
            batteryValue = RandomData.generateRandomBytes(2);
            RV = new byte[]{RandomData.generateRandomByte(), 0x1F};
            vSensed = RandomData.generateRandomByte();
            batteryLevel = RandomData.generateRandomByte();
        }

        public byte[] getMeasurement() {
            DynamicByteBuffer buffer = new DynamicByteBuffer();
            buffer.put(meansTime);
            buffer.put(paceLeadImpedance);
            buffer.put(shockLeadImpedance);
            buffer.put(batteryValue);
            buffer.put(RV);
            buffer.put(vSensed);
            buffer.put(batteryLevel);
            buffer.put(reserved);
            byte crc8 = DataConvert.calcCRC8(buffer.toArray());
            buffer.put(crc8);
            return buffer.toArray();
        }
    }

    public void buildPayload(DynamicByteBuffer buffer) {
        for (int i = 0; i < days; i++) {
            Measurement daily = new Measurement(deviceMode, testCaseId);
            buffer.put(daily.getMeasurement());
        }

    }
}