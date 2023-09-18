package pgdata.Data;

import Controller.RandomData;
import DataStructure.DynamicByteBuffer;
import ParseData.DataConvert;
import ParseData.DecodingPacket;
import pgdata.DeviceLog.BaseLog;

public class DailyMeasurement extends BaseLog {
    private static int deviceMode = 0;
    private int testCaseId = 0;
    private int days;

    public DailyMeasurement(DecodingPacket packet, int deviceMode, int testCaseId) {
        days = packet.getpayload()[2];
        packetHeader = new byte[]{(byte) (days * 16 + 4), 0x04, 0x58};
        this.deviceMode++;
        this.deviceMode %= 4;
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
            meansTime = RandomData.getTimePassedInSeconds(false);
            switch (deviceMode) {
                case 0 -> {
                    paceLeadImpedance = RandomData.createByteArray(2, 200, 3000);
                    shockLeadImpedance = RandomData.createByteArray(2, 40, 200);
                    batteryValue = RandomData.createByteArray(2, 2000, 3500);
                    RV = new byte[]{(byte) (testCaseId * 40 & 0xFF), 0x1F};
                    vSensed = RandomData.generateRandomByte();
                    batteryLevel = RandomData.generateRandomByte();
                }
                case 1 -> {
                    testCaseId = days - testCaseId;
                    paceLeadImpedance = RandomData.generateByteArray(2, testCaseId * 300);
                    shockLeadImpedance = RandomData.generateByteArray(2, testCaseId * 30);
                    batteryValue = RandomData.generateByteArray(2, 2000 + testCaseId * 200);
                    RV = new byte[]{(byte) (testCaseId * 40 & 0xFF), 0x1F};
                    vSensed = (byte) (testCaseId * 40 & 0xFF);
                    batteryLevel = (byte) (testCaseId * 10 & 0xFF);
                }
                case 2 -> {
                    paceLeadImpedance = RandomData.generateByteArray(2, testCaseId * 300);
                    shockLeadImpedance = RandomData.generateByteArray(2, testCaseId * 30);
                    batteryValue = RandomData.generateByteArray(2, 2000 + testCaseId * 200);
                    RV = new byte[]{(byte) (testCaseId * 40 & 0xFF), 0x1F};
                    vSensed = (byte) (testCaseId * 40 & 0xFF);
                    batteryLevel = (byte) (testCaseId * 10 & 0xFF);
                }
                default -> {
                    paceLeadImpedance = RandomData.generateByteArray(2, 500);
                    shockLeadImpedance = RandomData.generateByteArray(2, 100);
                    batteryValue = RandomData.generateByteArray(2, 2500);
                    RV = new byte[]{(byte) (0x33), 0x1F};
                    vSensed = (byte) (0x33);
                    batteryLevel = 0x33;
                }
            }

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
        for (int i = 1; i <= days; i++) {
            Measurement daily = new Measurement(deviceMode, i);
            buffer.put(daily.getMeasurement());
        }

    }
}