package pgdata.Data;

import Controller.RandomData;
import DataStructure.DynamicByteBuffer;
import ParseData.DataConvert;
import ParseData.DecodingPacket;
import pgdata.DeviceLog.BaseLog;

public class DeviceCounter extends BaseLog {
    private int deviceMode;
    private int testCaseId;
    public DeviceCounter(int deviceMode, int testCaseId) {
        packetHeader = new byte[]{(byte) 0x84, 0x04, 0x2F};
        this.deviceMode = deviceMode;
        this.testCaseId = testCaseId;
    }

    public void buildPayload(DynamicByteBuffer buffer) {
        buffer.put(RandomData.generateRandomBytes(124));
    }
}