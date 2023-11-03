package pgdata.DeviceLog;

import Controller.RandomData;
import DataStructure.DynamicByteBuffer;
import ParseData.DataConvert;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * All the device logs are generated by Device itself
 * The logs have nothing to do with programmer itself
 */
public class DeviceResetLog extends BaseLog {
    private byte actual_number;
    private byte write_index;
    private byte[] m1_reset_count;
    private int deviceMode;
    private int testCaseId;
    HashMap<Integer, String> rstcResetTypes = new HashMap<>();
    HashMap<Integer, String> exceptionResetTypes = new HashMap<>();
    HashMap<Integer, String> bleResetTypes = new HashMap<>();
    HashMap<Integer, String> deviceIds = new HashMap<>();
    private static final int DEVICE_ID_M1 = 0;
    private static final int DEVICE_ID_M2 = 0x54;
    private static final int DEVICE_ID_M3 = 0x14;
    private static final int DEVICE_ID_BLE = 0x50;
    private List<Integer> rstcResetTypesList;
    private List<Integer> exceptionResetTypesList;
    private List<Integer> bleResetTypesList;
    public DeviceResetLog(){
        packetHeader = new byte[]{(byte) 0x87, 0x59, 0x11};
        rstcResetTypes.put(0x0, "NONE"); // NO RESET
        rstcResetTypes.put(0x01, "PWR"); // POWER ON
        rstcResetTypes.put(0x02, "BODVDD"); // BODVDD
        rstcResetTypes.put(0x04, "BODCORE");// BODCORE
        rstcResetTypes.put(0x10, "EXT"); // EXTERNAL
        rstcResetTypes.put(0x20, "WDT"); // WDT
        rstcResetTypes.put(0x40, "SYS"); // SYSTEM

        // ARM exception resets
        exceptionResetTypes.put(0x28, "DEBUG MONITOR"); // DEBUG MONITOR
        exceptionResetTypes.put(0x80, "HARD FAULT"); // HARD FAULT
        exceptionResetTypes.put(0x88, "MEM MGMT"); // MEMORY MANAGMENT
        exceptionResetTypes.put(0xA0, "BUS FAULT"); // BUS FAULT
        exceptionResetTypes.put(0xA8, "USAGE FAULT"); // USAGE FAULT

        bleResetTypes.put(0x0, "PWR");
        bleResetTypes.put(0x01, "EXT");
        bleResetTypes.put(0x02, "WDT");
        bleResetTypes.put(0x04, "SOFT_RESET");
        bleResetTypes.put(0x08, "CPU_LOOKUP");
        bleResetTypes.put(0x10, "DETECT_GPIO");
        bleResetTypes.put(0x20, "ANADETECT_LPCOMP");
        bleResetTypes.put(0x40, "DEBUG_INTERFACE");
        bleResetTypes.put(0x80, "NFC_DETECT");

        deviceIds.put(DEVICE_ID_M1, "M1");
        deviceIds.put(DEVICE_ID_M2, "M2");
        deviceIds.put(DEVICE_ID_M3, "M3");
        deviceIds.put(DEVICE_ID_BLE, "BLE");

        // Initialize and populate the new Lists
        rstcResetTypesList = new ArrayList<>(rstcResetTypes.keySet());
        exceptionResetTypesList = new ArrayList<>(exceptionResetTypes.keySet());
        bleResetTypesList = new ArrayList<>(bleResetTypes.keySet());
    }

    public DeviceResetLog(int deviceMode, int testCaseId){
        this();
        this.deviceMode = deviceMode;
        this.testCaseId = testCaseId;
    }
    /*
    If every byte in the ResetLog is 0x00, it will not be displayed on ResetLog UI.
     */
    private class ResetLog{
        private byte[] DeviceIdList = new byte[]{0x00, 0x54, 0x14, 0x50}; // M1, M2, M3, BLE
        private byte deviceId;
        private byte resetReason;
        private byte[] timestamp;
        private byte[] operataionIdLog;

        public ResetLog(int deviceMode, int testCaseId){
            deviceId = DeviceIdList[RandomData.generateRandomByte(3)];
            resetReason = RandomData.generateRandomByte();
            timestamp = RandomData.getTimePassedInSeconds(); // length 4
            operataionIdLog = RandomData.generateRandomBytes(6);
            if(deviceMode==0){
                // default
            }else if(deviceMode==1){
                switch (testCaseId){
                    case 0:{
                        deviceId = 0x00;
                        resetReason = 0x00;
                        operataionIdLog = new byte[6];
                        timestamp = new byte[4];
                        break;
                    }
                    case 1:{
                        break;
                    }
                    case 2:{
                        deviceId = 0x00;
                        resetReason = 0x00;
                        break;
                    }
                    case 3:{
                        operataionIdLog = new byte[6];
                        break;
                    }
                }
            }else if(deviceMode==2){
                resetReason = 0x00;
                switch (testCaseId){
                    case 0:{
                        deviceId = DeviceIdList[3];
                        operataionIdLog = new byte[]{0x11,0x11,0x11,0x11,0x11,0x11};
                        break;
                    }
                    case 1:{
                        deviceId = DeviceIdList[0];
                        operataionIdLog = new byte[]{0x22,0x22,0x22,0x22,0x22,0x22};
                        break;
                    }
                    case 2:{
                        deviceId = DeviceIdList[1];
                        operataionIdLog = new byte[]{0x33,0x33,0x33,0x33,0x33,0x33};
                        break;
                    }
                    case 3:{
                        deviceId = DeviceIdList[2];
                        operataionIdLog = new byte[]{0x44,0x44,0x44,0x44,0x44,0x44};
                        break;
                    }
                    case 4:{
                        deviceId = DeviceIdList[3];
                        operataionIdLog = new byte[]{0x44,0x44,0x44,0x44,0x44,0x44};
                        resetReason = 0x40;
                        break;
                    }
                    case 5:{
                        deviceId = DeviceIdList[3];
                        operataionIdLog = new byte[]{0x44,0x44,0x44,0x44,0x44,0x44};
                        resetReason = 0x01;
                        break;
                    }
                }
            }else if(deviceMode==3){
                deviceId = DeviceIdList[3];
                int reason = bleResetTypesList.get(testCaseId);
                resetReason = (byte)reason;
            }
            else if(deviceMode==4){
                deviceId = DeviceIdList[RandomData.generateRandomByte(2)];
                int reason = rstcResetTypesList.get(testCaseId);
                resetReason = (byte)reason;
            }else if(deviceMode==5){
                deviceId = DeviceIdList[RandomData.generateRandomByte(2)];
                int reason = exceptionResetTypesList.get(testCaseId);
                resetReason = (byte)reason;
            }
        }

        public byte[] getResetLog(){
            DynamicByteBuffer buffer = new DynamicByteBuffer();
            buffer.put(deviceId);
            buffer.put(resetReason);
            buffer.put(timestamp);
            buffer.put(operataionIdLog);
            return buffer.toArray();
        }
    }

    public void buildPayload(DynamicByteBuffer buffer){
        actual_number = RandomData.generateRandomByte();
        write_index = RandomData.generateRandomByte();
        m1_reset_count = RandomData.generateRandomBytes(2);

        buffer.put(actual_number);
        buffer.put(write_index);

        for(int i = 0;i<10;i++){
            DeviceResetLog.ResetLog log = new DeviceResetLog.ResetLog(deviceMode, testCaseId);
            buffer.put(log.getResetLog());
        }

        buffer.put(m1_reset_count);
    }

}
