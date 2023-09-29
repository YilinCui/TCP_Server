package pgdata.DeviceTest;

import Controller.RandomData;
import DataStructure.DynamicByteBuffer;
import pgdata.DeviceLog.BaseLog;

public class BatteryVoltage extends BaseLog {
    private int deviceMode;
    private int testCaseId;
    private byte[] result = new byte[2];
    private byte[] timeStamp = new byte[4];
    private byte[] reserved = new byte[2];
    public BatteryVoltage(int deviceMode, int testCaseId){
        packetHeader = new byte[]{0x13, 0x53, 0x51};
        this.deviceMode = deviceMode;
        this.testCaseId = testCaseId;
        result = RandomData.generateByteArray(2, testCaseId*111);
        timeStamp = RandomData.getTimePassedInSeconds(testCaseId%9,testCaseId%9,testCaseId%9);
    }


    public void buildPayload(DynamicByteBuffer buffer){
        buffer.put(result);
        buffer.put(timeStamp);
        buffer.put(reserved);
    }
}