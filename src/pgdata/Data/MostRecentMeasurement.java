package pgdata.Data;

import Controller.RandomData;
import DataStructure.DynamicByteBuffer;
import pgdata.DeviceLog.BaseLog;

public class MostRecentMeasurement extends BaseLog {
    private static int deviceMode = 0;
    private int testCaseId = 0;
    private byte[] data = new byte[64]; // overall data
    private byte[] chargeLogData = new byte[16];
    private byte[] measurementData = new byte[10];
    private byte[] reserved = new byte[2];
    private byte[] times1 = new byte[20]; // rtc timer with dalta
    private byte[] LastSessionTime = new byte[4];
    private byte[] times2 = new byte[4];
    private byte[] BLEmeasurement = new byte[4];
    private byte[] MagnetMeasurement = new byte[4];

    public MostRecentMeasurement(int deviceMode, int testCaseId){
        packetHeader = new byte[]{(byte)0x4B, 0x36, 0x2A};
        this.deviceMode = deviceMode;
        this.testCaseId = testCaseId;
        initializeData();
    }

    private void initializeData(){
        LastSessionTime = RandomData.getTimePassedInSeconds(4,1,1);
    }

    public void buildPayload(DynamicByteBuffer buffer) {
        buffer.put(chargeLogData);
        buffer.put(measurementData);
        buffer.put(reserved);
        buffer.put(times1);
        buffer.put(LastSessionTime);
        buffer.put(times2);
        buffer.put(BLEmeasurement);
        buffer.put(MagnetMeasurement);
    }
}
