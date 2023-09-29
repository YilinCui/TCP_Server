package pgdata.DeviceTest;

import Controller.RandomData;
import DataStructure.DynamicByteBuffer;
import ParseData.DataConvert;
import pgdata.Data.DailyMeasurement;
import pgdata.DeviceLog.BaseLog;

public class PaceThreshold extends BaseLog {
    private int deviceMode = 0;
    private int testCaseId = 0;
    public PaceThreshold(int deviceMode, int testCaseId){
        this.deviceMode = deviceMode;
        this.testCaseId = testCaseId;
        packetHeader = new byte[]{0x27, 0x50, 0x29};
    }

    private class TestLog{
        private byte[] timeStamp = new byte[4];
        private byte ampIdx;
        private byte pwIdx;
        private byte typeIdx;
        private byte reserved;
        public TestLog(){
            timeStamp = RandomData.getTimePassedInSeconds();
            ampIdx = RandomData.generateRandomByte(); // 0.1-7.5
            pwIdx = RandomData.generateRandomByte(); // 0.1-1.5
            typeIdx = RandomData.generateRandomByte(); // Amplitude & Pulse Width
            if(testCaseId==0){
                timeStamp = RandomData.getTimePassedInSeconds(0,0,0);
                ampIdx = (byte) 0xFF;
                pwIdx = (byte) 0xFF; // 0.1-1.5
                typeIdx = 0x02; // Amplitude & Pulse Width
            }else{
                timeStamp = RandomData.getTimePassedInSeconds(testCaseId%9,testCaseId%9,testCaseId%9);
                ampIdx = (byte) testCaseId;
                pwIdx = (byte) testCaseId; // 0.1-1.5
                typeIdx = (byte) (testCaseId%2==0?0x00:0x01); // Amplitude & Pulse Width
            }
        }

        public byte[] getTestLog() {
            DynamicByteBuffer buffer = new DynamicByteBuffer();
            buffer.put(timeStamp);
            buffer.put(ampIdx);
            buffer.put(pwIdx);
            buffer.put(typeIdx);
            buffer.put(reserved);
            return buffer.toArray();
        }
    }

    public void buildPayload(DynamicByteBuffer buffer) {
        for (int i = 0; i < 3; i++) {
            TestLog log = new TestLog();
            buffer.put(log.getTestLog());
        }
        buffer.put(RandomData.generateRandomBytes(4));
    }
}
