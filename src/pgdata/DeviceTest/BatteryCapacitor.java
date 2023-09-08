package pgdata.DeviceTest;

import Controller.RandomData;
import DataStructure.DynamicByteBuffer;
import pgdata.DeviceLog.BaseLog;
import pgdata.DeviceLog.DeviceBatteryLog;

import java.util.ArrayList;
import java.util.List;

public class BatteryCapacitor extends BaseLog {
    public BatteryCapacitor(){
        packetHeader = new byte[]{0x6C, 0x53, 0x20};
    }

    private class CapacitorEntry {
        private byte[] timestamp = new byte[4];
        private byte[] chargeDuration = new byte[2];
        private byte[] chargeStatus = new byte[2];

        public CapacitorEntry(){
            timestamp = RandomData.getTimePassedInSeconds();
            chargeDuration = RandomData.generateRandomBytes(2);
            chargeStatus = RandomData.generateRandomBytes(2);
//            chargeStatus = new byte[]{(byte) 0x80, 0x00};
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
            CapacitorEntry entry = new CapacitorEntry();
            buffer.put(entry.getCapacitorEntry());
        }
    }
}
