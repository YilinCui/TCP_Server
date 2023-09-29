package pgdata.DeviceTest;

import Controller.RandomData;
import DataStructure.DynamicByteBuffer;
import pgdata.DeviceLog.BaseLog;

public class BatteryDetail extends BaseLog {
    private int deviceMode;
    private int testCaseId;
    private byte[] data;
    public BatteryDetail(int deviceMode, int testCaseId){
        packetHeader = new byte[]{(byte) 0x87, 0x12, (byte) 0x88};
        this.deviceMode = deviceMode;
        this.testCaseId = testCaseId;
        data = RandomData.generateRandomBytes(124);
    }


    public void buildPayload(DynamicByteBuffer buffer){
        buffer.put(data);
    }
}