package pgdata.DeviceTest;

import Controller.RandomData;
import DataStructure.DynamicByteBuffer;
import pgdata.DeviceLog.BaseLog;

public class IntrinsicMeasure extends BaseLog {
    private int deviceMode;
    private int testCaseId;
    private byte[] result = new byte[2];
    private byte[] timeStamp = new byte[4];
    private byte[] reserved = new byte[2];
    public IntrinsicMeasure(int deviceMode, int testCaseId){
        packetHeader = new byte[]{0x13, 0x53, 0x22};
        this.deviceMode = deviceMode;
        this.testCaseId = testCaseId;
        result = new byte[]{0x00, 0x11};
        timeStamp = RandomData.getTimePassedInSeconds(testCaseId%9,testCaseId%9,testCaseId%9);
    }


    public void buildPayload(DynamicByteBuffer buffer){
        buffer.put(result);
        buffer.put(timeStamp);
        buffer.put(reserved);
    }
}