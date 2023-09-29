package pgdata.DeviceTest;

import Controller.RandomData;
import DataStructure.DynamicByteBuffer;
import pgdata.DeviceLog.BaseLog;

public class PaceImpedance extends BaseLog {
    private int deviceMode;
    private int testCaseId;
    private byte[] impedance = new byte[2];
    private byte[] timeStamp = new byte[4];
    private byte[] actual = new byte[32];

    public PaceImpedance(int deviceMode, int testCaseId) {
        packetHeader = new byte[]{0x31, 0x53, 0x4F};
        this.deviceMode = deviceMode;
        this.testCaseId = testCaseId;
        impedance = new byte[]{0x00, 0x11};
        timeStamp = RandomData.getTimePassedInSeconds(testCaseId % 9, testCaseId % 9, testCaseId % 9);
        actual = RandomData.generateRandomBytes(32);
    }

    public void buildPayload(DynamicByteBuffer buffer) {
        buffer.put(impedance);
        buffer.put(timeStamp);
        buffer.put(actual);
    }
}