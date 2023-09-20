package pgdata.Data;

import Controller.RandomData;
import DataStructure.DynamicByteBuffer;
import ParseData.DataConvert;
import ParseData.DecodingPacket;
import pgdata.DeviceLog.BaseLog;

public class DeviceCounter extends BaseLog {
    private int deviceMode;
    private int testCaseId;

    private byte[] rvSenseCounters1 = new byte[4];
    private byte[] rvPaceCounters1 = new byte[4];
    private byte[] afCount1 = new byte[4];
    private byte[] hysteresisCount1 = new byte[4];
    private byte[] reserved1 = new byte[32];

    private byte[] rvSenseCounters2 = new byte[4];
    private byte[] rvPaceCounters2 = new byte[4];
    private byte[] afCount2 = new byte[4];
    private byte[] hysteresisCount2 = new byte[4];
    private byte[] reserved2 = new byte[32];

    private byte[] dailyRvSenseCounters = new byte[4];
    private byte[] dailyRvPaceCounters = new byte[4];
    private byte[] reserved3 = new byte[20];
    public DeviceCounter(int deviceMode, int testCaseId) {
        packetHeader = new byte[]{(byte) 0x87, 0x04, 0x2F};
        this.deviceMode = deviceMode;
        this.testCaseId = testCaseId;
        initialCounter();
    }

    private void initialCounter(){
        System.out.println("DeviceCounter: testcaseId is: " + testCaseId);
        rvSenseCounters1 = RandomData.generateByteArray(4,testCaseId*1111);
        rvPaceCounters1 = RandomData.generateByteArray(4,testCaseId*1111);
        afCount1 = RandomData.generateByteArray(4,testCaseId*1111); // lifeTime
        hysteresisCount1 = RandomData.generateByteArray(4,testCaseId*1111);

        rvSenseCounters2 = RandomData.generateByteArray(4,testCaseId*1111);
        rvPaceCounters2 = RandomData.generateByteArray(4,testCaseId*1111);
        afCount2 = RandomData.generateByteArray(4,testCaseId*1111);
        hysteresisCount2 = RandomData.generateByteArray(4,testCaseId*1111);

        dailyRvSenseCounters = RandomData.generateByteArray(4,testCaseId*1111);
        dailyRvPaceCounters = RandomData.generateByteArray(4,testCaseId*1111);
    }


    public void buildPayload(DynamicByteBuffer buffer) {
        buffer.put(rvSenseCounters1);
        buffer.put(rvPaceCounters1);
        buffer.put(afCount1);
        buffer.put(hysteresisCount1);
        buffer.put(reserved1);

        buffer.put(rvSenseCounters2);
        buffer.put(rvPaceCounters2);
        buffer.put(afCount2);
        buffer.put(hysteresisCount2);
        buffer.put(reserved2);

        buffer.put(dailyRvSenseCounters);
        buffer.put(dailyRvPaceCounters);
        buffer.put(reserved3);
    }
}