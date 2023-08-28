package pgdata.DeviceLog;

import Controller.RandomData;
import DataStructure.DynamicByteBuffer;
import ParseData.DataConvert;

/**
 * All the device logs are generated by Device itself
 * The logs have nothing to do with programmer itself
 */
public class DeviceFaultLog extends BaseLog {
    private int deviceMode;
    private int testCaseId;
    public DeviceFaultLog(){
        packetHeader = new byte[]{(byte) 0x80, 0x63, 0x14};
    }
    public DeviceFaultLog(int deviceMode, int testCaseId){
        this();
        this.deviceMode = deviceMode;
        this.testCaseId = testCaseId;
    }

    private class FaultLog{
        private byte[] timestamp;
        private byte[] faultCount;
        private byte faultId;
        private byte status;
        public FaultLog(int deviceMode, int testCaseId){
            timestamp = RandomData.getTimePassedInSeconds();
            faultCount = RandomData.generateRandomBytes(2);
            faultId = RandomData.generateRandomByte(34);
            status = RandomData.generateRandomByte();
            if(deviceMode==0){
                // default
            }else if (deviceMode==1){
                timestamp = new byte[4];
                switch (testCaseId){
                    case 0:{
                        // ListView should be Empty
                        faultCount = new byte[2];
                        faultId = 0x00;
                        status = 0x00;
                        break;
                    }
                    case 1:{
                        // ListView should have contents
                        break;
                    }
                    case 2:{
                        // ListView should be Empty
                        faultId = 0x00;
                        break;
                    }
                    case 3:{
                        // ListView should be Empty
                        faultCount = new byte[2];
                        break;
                    }
                    case 4:{
                        // ListView should have contents
                        status = 0x00;
                        break;

                    }
                }
            }else if (deviceMode==2){
                timestamp = new byte[4];
                faultId = (byte) testCaseId;
            }
        }

        public byte[] getFaultLog(){
            DynamicByteBuffer buffer = new DynamicByteBuffer();
            buffer.put(timestamp);
            buffer.put(faultCount);
            buffer.put(faultId);
            buffer.put(status);
            return buffer.toArray();
        }
    }

    public void buildPayload(DynamicByteBuffer buffer){
        for(int i = 0;i<15;i++){
            FaultLog log = new FaultLog(deviceMode, testCaseId);
            buffer.put(log.getFaultLog());
        }
    }


}
