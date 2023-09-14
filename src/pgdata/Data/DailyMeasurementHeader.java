package pgdata.Data;

import Controller.RandomData;
import DataStructure.DynamicByteBuffer;
import pgdata.DeviceLog.BaseLog;

public class DailyMeasurementHeader extends BaseLog {
    private int deviceMode;
    private int testCaseId;
    public DailyMeasurementHeader(int deviceMode, int testCaseId){
        packetHeader = new byte[]{(byte)0x08, 0x04, 0x0E};
        this.deviceMode = deviceMode;
        this.testCaseId = testCaseId;
    }
    public void buildPayload(DynamicByteBuffer buffer){
        byte[] days = new byte[]{0x07,0x00};
        byte[] reserved = new byte[2];
        buffer.put(days);
        buffer.put(reserved);
    }
}