package pgdata.Data;

import Controller.RandomData;
import DataStructure.DynamicByteBuffer;
import pgdata.DeviceLog.BaseLog;
import pgdata.DeviceTest.BatteryCapacitor;

public class RateHistogramLast extends BaseLog {
    private int deviceMode;
    private int testCaseId;
    public RateHistogramLast(int deviceMode, int testCaseId){
        packetHeader = new byte[]{(byte) 0x84, 0x44, 0x1B};
        this.deviceMode = deviceMode;
        this.testCaseId = testCaseId;
    }
    private class PacedData {
        private byte[] data = new byte[2];
        public PacedData(int deviceMode, int testCaseId){
            data = RandomData.generateRandomBytes(4);
        }

        public byte[] getData(){
            return data;
        }
    }

    private class SensedData {
        private byte[] data = new byte[2];
        public SensedData(int deviceMode, int testCaseId){
            data = RandomData.generateRandomBytes(4);
        }

        public byte[] getData(){
            return data;
        }

    }

    public void buildPayload(DynamicByteBuffer buffer){
        for(int i = 0; i < 16; i++) {
            SensedData entry = new SensedData(deviceMode, testCaseId);
            buffer.put(entry.getData());
        }
        for(int i = 0; i < 16; i++) {
            PacedData entry = new PacedData(deviceMode, testCaseId);
            buffer.put(entry.getData());
        }
    }
}
