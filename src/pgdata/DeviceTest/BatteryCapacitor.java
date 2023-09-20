package pgdata.DeviceTest;

import Controller.RandomData;
import DataStructure.DynamicByteBuffer;
import pgdata.DeviceLog.BaseLog;
import pgdata.DeviceLog.DeviceBatteryLog;

import java.util.ArrayList;
import java.util.List;

public class BatteryCapacitor extends BaseLog {
    private int deviceMode;
    private int testCaseId;
    public BatteryCapacitor(int deviceMode, int testCaseId){
        packetHeader = new byte[]{0x6B, 0x53, 0x20};
        this.deviceMode = deviceMode;
        this.testCaseId = testCaseId;
    }

    private class CapacitorEntry {
        private byte[] timestamp = new byte[4];
        private byte[] chargeDuration = new byte[2];
        private byte[] chargeStatus = new byte[2];

        public CapacitorEntry(int deviceMode, int testCaseId){
            timestamp = RandomData.getTimePassedInSeconds();
            chargeDuration = RandomData.generateRandomBytes(2);
            chargeStatus = new byte[]{0x00, 0x00};
            if(deviceMode==1){
                switch (testCaseId){
                    case 0->{
                        timestamp = RandomData.getTimePassedInSeconds(0,0,0);
                        chargeDuration = new byte[2];
                        chargeStatus = new byte[]{(byte) 0x00, RandomData.generateRandomByte()};
                    }
                    case 1->{
                        timestamp = RandomData.getTimePassedInSeconds(1,1,1);
                        chargeDuration = new byte[]{0x11, 0x00};
                        chargeStatus = new byte[]{(byte) 0x80, RandomData.generateRandomByte()};
                    }
                    case 2->{
                        timestamp = RandomData.getTimePassedInSeconds(2,2,2);
                        chargeDuration = new byte[]{0x22, 0x00};
                        chargeStatus = new byte[]{(byte) 0x82, RandomData.generateRandomByte()};
                    }
                    case 3->{
                        timestamp = RandomData.getTimePassedInSeconds(3,3,3);
                        chargeDuration = new byte[]{0x33, 0x11};
                        chargeStatus = new byte[]{(byte) 0x83, RandomData.generateRandomByte()};
                    }
                    case 4->{
                        timestamp = RandomData.getTimePassedInSeconds(4,4,4);
                        chargeDuration = new byte[]{0x44, 0x22};
                        chargeStatus = new byte[]{(byte) 0x83, RandomData.generateRandomByte()};
                    }
                    case 5->{
                        timestamp = RandomData.getTimePassedInSeconds(1,2,3);
                        chargeDuration = new byte[]{0x22, 0x00};
                        chargeStatus = new byte[]{(byte) 0x80, RandomData.generateRandomByte()};
                    }
                    case 6->{
                        timestamp = RandomData.getTimePassedInSeconds(3,2,1);
                        chargeDuration = new byte[]{0x33, 0x11};
                        chargeStatus = new byte[]{(byte) 0x00, RandomData.generateRandomByte()};
                    }
                }
            }
        }

        public byte[] getCapacitorEntry(){
            DynamicByteBuffer buffer = new DynamicByteBuffer();
            buffer.put(timestamp);
            buffer.put(chargeDuration);
            buffer.put(chargeStatus);
            return buffer.toArray();
        }

    }
    public void buildPayload(DynamicByteBuffer buffer){
        for(int i = 0; i < 12; i++) {
            CapacitorEntry entry = new CapacitorEntry(deviceMode, testCaseId);
            buffer.put(entry.getCapacitorEntry());
        }
    }
}
