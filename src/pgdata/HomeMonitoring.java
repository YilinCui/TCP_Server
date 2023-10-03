package pgdata;

import Controller.RandomData;
import DataStructure.DynamicByteBuffer;
import pgdata.DeviceLog.BaseLog;

public class HomeMonitoring extends BaseLog {
    private int deviceMode;
    private int testCaseId;
    private byte uploadTime;
    private byte uploadFreq;
    private byte uploadLoadOn;
    private byte[] reserved = new byte[9];
    public HomeMonitoring(int deviceMode, int testCaseId){
        packetHeader = new byte[]{0x17, 0x53, 0x05};
        this.deviceMode = deviceMode;
        this.testCaseId = testCaseId;
        uploadTime = RandomData.generateRandomByte(23);
        uploadFreq = RandomData.generateRandomByte(2);
        uploadLoadOn = RandomData.generateRandomByte(1);
    }


    public void buildPayload(DynamicByteBuffer buffer){
        buffer.put(uploadTime);
        buffer.put(uploadFreq);
        buffer.put(uploadLoadOn);
        buffer.put(reserved);
    }
}