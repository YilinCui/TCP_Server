package pgdata.Data;

import Controller.RandomData;
import DataStructure.DynamicByteBuffer;
import pgdata.DeviceLog.BaseLog;
import pgdata.DeviceTest.BatteryCapacitor;

public class RateHistogramLast extends BaseLog {
    private static int deviceMode = 0;
    private int testCaseId = 0;
    public RateHistogramLast(int deviceMode, int testCaseId){
        packetHeader = new byte[]{(byte) 0x8B, 0x44, 0x1B};
//        this.deviceMode = deviceMode;
//        this.testCaseId = testCaseId;
        this.deviceMode++;
        this.deviceMode %= 4;
    }
    private class PacedData {
        private byte[] data = new byte[2];

        public PacedData(int deviceMode, int testCaseId){
            data = RandomData.generateByteArray(4, 5+testCaseId*10);
        }

        public byte[] getData(){
            return data;
        }
    }

    private class SensedData {
        private byte[] data = new byte[2];
        public SensedData(int deviceMode, int testCaseId){
            data = RandomData.generateByteArray(4, testCaseId*10);
        }

        public byte[] getData(){
            return data;
        }

    }

    public void buildPayload(DynamicByteBuffer buffer){
        if(deviceMode==0){
            for(int i=0;i<32;i++){
                byte[] data = RandomData.generateByteArray(4,10);
                buffer.put(data);
            }
        }else if(deviceMode==1){
            for(int i = 0; i < 16; i++) {
                SensedData entry = new SensedData(deviceMode, 16-i);
                buffer.put(entry.getData());
            }
            for(int i = 0; i < 16; i++) {
                PacedData entry = new PacedData(deviceMode, 16-i);
                buffer.put(entry.getData());
            }
        }else if(deviceMode==2){
            for(int i = 0; i < 16; i++) {
                SensedData entry = new SensedData(deviceMode, i);
                buffer.put(entry.getData());
            }
            for(int i = 0; i < 16; i++) {
                PacedData entry = new PacedData(deviceMode, i);
                buffer.put(entry.getData());
            }
        }else{
            for(int i=0;i<32;i++){
                byte[] data = RandomData.generateRandomBytes(4);
                buffer.put(data);
            }
        }
    }
}
