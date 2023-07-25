package pgdata.DeviceLog;

import Controller.RandomData;
import DataStructure.DynamicByteBuffer;

public class DeviceChargeLog extends BaseDeviceLog{



    private class ChargeLog{
        private byte[] duration;
        private byte[] timestamp;
        private byte[] status;
        private byte[] disStart;
        private byte[] disEnd;
        private byte[] impedance;
        public ChargeLog(){
            duration = RandomData.generateRandomBytes(4);
            timestamp = RandomData.generateRandomBytes(4);
            status = RandomData.generateRandomBytes(2);
            disStart = RandomData.generateRandomBytes(2);
            disEnd = RandomData.generateRandomBytes(2);
            impedance = RandomData.generateRandomBytes(2);
        }

        public byte[] getChargeLog(){
            DynamicByteBuffer buffer = new DynamicByteBuffer();
            buffer.put(DeviceId);
            buffer.put(faultId);
            buffer.put(timestamp);
            buffer.put(operataionIdLog);
            return buffer.toArray();
        }
    }

    @Override
    public byte[] getbRetrunData() {
        return new byte[0];
    }
}
