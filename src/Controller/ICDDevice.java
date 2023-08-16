package Controller;

import Constant.Constant;
import ParseData.*;
import pgdata.*;
import pgdata.DeviceLog.*;
import pgdata.Episodes.EpisodeHeader;
import pgdata.Episodes.EpisodeMarker;
import pgdata.Episodes.EpisodeSegment;
import pgdata.Episodes.SingleEpisode;

import java.io.File;

/**
 * Every Device connected to TCP Server would instantiate an ICDDevice
 * packet will be delivered as following logic TCP Server -> ICDDevice -> Modules -> process -> return to ICDDevice -> TCP Server -> TCP Client
 * Send packet to seperate Class to handle if we wish to fake the data.
 * Otherwise, use ICDCommand method directly for simple I/O
 */

public class ICDDevice implements ICDCommandDefinitions, FilesHandler {
    /*
Non-testing mode (deviceMode = 0): In this mode, device data, such as parameter data that can be programmed, is processed through local serialization and deserialization IO. If the local data does not exist, the default value is returned. Non-programmable data all return random values (like Device Log, Episode, etc.)

Espresso testing script mode 1 (deviceMode = 1): Fuzz testing + Full random: After Espresso gets the data, it parses it locally and then compares it with the actual UI data. All returned data are completely randomly without any rules.

Espresso testing script mode 2 (deviceMode = 2): Fuzz testing + Semi-random: After Espresso gets the data, it parses it locally and then compares it with the actual UI data. All returned data are randomly within a reasonable range.

Espresso testing script mode 3 (deviceMode = 3): Regression testing. Predefined data and expected test results: Espresso does not need to parse the data locally. Each Testcase will predefine the expected data and results. Espresso should directly compare whether the result is correct or not.

Espresso testing script mode 4 (deviceMode = 4): Integration testing. The interaction of multiple modules. For example, pop-up testing.

Stress/Performance testing (deviceMode = 5): Test the programmer's performance when faced with a large amount of garbage/illegal data.
DeviceMode 11: Storage Mode

     */
    private int deviceMode = 1;
    private int testCaseId = 1;

    private int chargeLogCnt = 1;
    private int patienInfoIndex = 1;
    private int BradyParameterIndex = 1;
    private byte[] bResponseArray;
    private byte[][] bLongResponseArray;
    private String folderName;
    private EncodingPacket encodingPacket;
    public BradyParameter bp_Local;
    public DeviceResetLog resetlog_Local;
    public DeviceFaultLog faultlog_Local;
    public DeviceTachyLog tachylog_local;
    public DeviceChargeLog chargeLog_local;
    public DeviceBatteryLog batteryLog_local;

    public EpisodeHeader episodeHeader_local;

    public LeadInfo li_Local;
    // There are several device modes of the virtual device
    // 0: Normal Mode. All of the device data are randomized.
    // 1: Espresso Testing Mode. How to return the data is determined by the configuration paramters from Espresso.


    // runtime status
    // DeviceStatus

    // Event

    // ECG realtime

    // Device Mode
    // Storage/Normal (MRI, Electrocautery ... )

    // Error conditions
    // Fault conditions

    // Episode History

    // OTA

    // constructor
    // load from local XML document
    // initialize with specific value
    public ICDDevice(int clientID) {
        folderName = "src" + File.separator + "LocalData" + File.separator + "Device" + clientID + File.separator;
        FilesHandler.creatFolder(folderName);
        initializeDevice();
    }

    private void initializeDevice() {
        bp_Local = new BradyParameter();

        // Device Log Randomized Generating.
        resetlog_Local = new DeviceResetLog();
        tachylog_local = new DeviceTachyLog();
        faultlog_Local = new DeviceFaultLog();
        chargeLog_local = new DeviceChargeLog();
        batteryLog_local = new DeviceBatteryLog();

        li_Local = new LeadInfo(folderName);

        episodeHeader_local = new EpisodeHeader();
        SingleEpisode.episodeIndex = 0;
    }

    // export/save to local XML document

    /**
     * Process the message sent from Android APP
     *
     * @param receivedBytes byte array sent from Android app(TCP client)
     */
    public void process(byte[] receivedBytes) {
        // Reset/Initialize parameters to null/default state;
        encodingPacket = null;
        bResponseArray = null;
        bLongResponseArray = null;
        String fileName = null;
        int BradyState = 1;

        DecodingPacket packet = new DecodingPacket(receivedBytes);
        int iCommandId = packet.getCommandID() & 0xFF;
        int iSequenceNumber = packet.getSequenceNumber() & 0xFF;

        if (iCommandId != ICD_CMD_BLE_IN_SESSION) {
            System.out.println("Received: " + packet);
        }
        // Initialize variable

        // Switch cases
        switch (iCommandId) {
            case 0x00:
                // 0x00 is used to set up the device mode
                // commandId refers to the testing configurations

                deviceMode = iSequenceNumber;
                testCaseId = receivedBytes[3] & 0xFF;
                System.out.println("Testing configurations parameters received! deviceMode is: " + deviceMode + ", testCaseId is: " + testCaseId);

            case ICD_CMD_EXIT_STORAGE: //0x01 Exit the storage mode
                deviceMode = 1;
                IOCommand("", 3, packet);

                break;

            case ICD_CMD_READ_ALERT_PARAM: //0x02 Read Alert Parameter
                fileName = folderName + Constant.ALERTS;
                IOCommand(fileName, 1, packet);

                if(bResponseArray==null)
                bResponseArray = Constant.READ_ALERT_PARAMETER;

                break;

            case ICD_CMD_READ_TACHY_DETECT_PARAM: //0x04 Read Tachy Mode Parameter
                fileName = folderName + Constant.TACHY_MODE_PARAMETER;
                IOCommand(fileName, 1, packet);

                if(bResponseArray==null){
                    bResponseArray = Constant.READ_TACHY_MODE_PARAMETER;
                }
                break;

            case ICD_CMD_BACK_TO_STORAGE_MODE: //0x08 Enter Storage Mode
                fileName = "";
                IOCommand(fileName, 3, packet);

                deviceMode = 11;
                break;

            case ICD_CMD_READ_EPISODE_HEADER: //0x0B Read Episode Header

                bResponseArray = episodeHeader_local.getbReturnData();

                //bResponseArray = Constant.READ_EPESODE_HEADER;

                break;

            case ICD_CMD_READ_PATIENT_LEADS_INFO: //0x0C Read Patient Lead Info

                li_Local.process(packet);

                bResponseArray = li_Local.getbRetrunData();
                if (bResponseArray == null){
                    bResponseArray = Constant.PATIENT_LEAD_INFO;
                }
                break;

            case ICD_CMD_READ_SERIAL_MODEL_NUM: //0x0D Read Device Serial Number
                fileName = folderName + Constant.SERIAL_NUMBER;
                IOCommand(fileName, 1, packet);

                if(bResponseArray==null)
                bResponseArray = Constant.DEVICE_SERIAL_NUMBER;

                break;

            case ICD_CMD_READ_DAILY_MMT_HEADER: //0x0E Read Daily Measurement Header

                bResponseArray = Constant.READ_DAILY_MEASUREMENT_HEADER;

                break;

            case ICD_CMD_READ_CLINICIAN_NOTE: //0x0F Read Clinician Note
                fileName = folderName + Constant.CLINICIAN_NOTE;
                IOCommand(fileName, 1, packet);

                if (bResponseArray == null) {
                    bResponseArray = Constant.READ_CLINICIAN_NOTE;
                }

                break;

            case ICD_CMD_READ_TACHY_LOG: //0x10 Read Tachy Log

                // use sequenceNumber as testCase indicator
                DeviceTachyLog tachyLog = new DeviceTachyLog(deviceMode, testCaseId);
                //DeviceTachyLog tachyLog = new DeviceTachyLog(1, 0);
                bResponseArray = tachyLog.getbReturnData();

                //bResponseArray = tachylog_local.getbReturnData();

                //bResponseArray = Constant.READ_TACHY_LOG;

                break;

            case ICD_CMD_READ_DEVICE_RESET_LOG: //0x11 Read Device Reset Log

                DeviceResetLog resetLog = new DeviceResetLog(deviceMode, testCaseId);
                bResponseArray = resetLog.getbReturnData();

                break;

            case ICD_CMD_CLEAR_RESET_LOG: //0x13 Clear Device Reset Log

                fileName = folderName + Constant.DEVICE_RESET_LOG;
                IOCommand(fileName, 3, packet);

                break;

            case ICD_CMD_READ_DEVICE_FAULT_LOG: //0x14 Read Device Fault Log

                DeviceFaultLog faultLog = new DeviceFaultLog(deviceMode, testCaseId);
                bResponseArray = faultLog.getbReturnData();


                //bResponseArray = Constant.READ_DEVICE_FAULT_LOG;

                break;

            case ICD_CMD_READ_TACHY_MODE: //0x15 Read Tachy Mode OnOff
                fileName = folderName + Constant.TACHY_MODE_ONOFF;
                IOCommand(fileName, 1, packet);

                if(bResponseArray==null){
                    bResponseArray = Constant.READ_TACHY_MODE_ONOFF;
                }

                break;

            case ICD_CMD_RETRIEVE_PG_TIMER_AND_RTC_DELTA: //0x19 Retrieve M2 Timer and RTC Timer

                bResponseArray = Constant.READ_M2_RTC_TIMER;

                break;

            case ICD_CMD_RETRIEVE_RATE_HISTOGRAM: //0x1B RETRIEVE RATE HISTOGRAM

                bResponseArray = Constant.RETRIEVE_RATE_HISTOGRAM;

                break;

            case ICD_CMD_RETRIEVE_RATE_HISTOGRAM_LIFETIME: //0x1C RETRIEVE RATE HISTOGRAM LIFETIME

                bResponseArray = Constant.RETRIEVE_RATE_HISTOGRAM_LIFETIME;

                break;

            case ICD_CMD_READ_CAPACITOR_REFORM: //0x20 Read Cap Reform Log

                bResponseArray = Constant.READ_CAP_REFORM_LOG;

                break;

            case ICD_CMD_READ_GLOBAL_CONSTANTS: //0x24 Read Global Constants

                fileName = folderName + Constant.GLOBAL_CONSTANT;
                IOCommand(fileName, 1, packet);

                if(bResponseArray==null)
                bResponseArray = Constant.READ_GLOBAL_CONSTANTS;

                break;

            case ICD_CMD_READ_TACHY_SVT_PARAM: //0x26 Read Tachy SVT Parameters
                fileName = folderName + Constant.TACHY_SVT_DETECTION;
                IOCommand(fileName, 1, packet);

                if(bResponseArray==null){
                    bResponseArray = Constant.READ_TACHY_SVT_DETECTION;
                }
                break;

            case ICD_CMD_FIRMWARE_VERSION: //0x27 Read FW Revision

                bResponseArray = Constant.FIRMWARE_VERSION;

                break;

            case ICD_CMD_READ_PACE_THRESHOLD_LOG: //0x29 Read Pace Threshold Log

                bResponseArray = Constant.READ_PACE_THRESHOLD_LOG;

                break;

            case ICD_CMD_READ_LAST_MEASUREMENTS: //0x2A Read Most Recent Measurement

                bResponseArray = Constant.READ_MOST_RECENT_MEASUREMENT;

                break;

            case ICD_CMD_READ_GLOBAL_COUNTERS: //0x2B Read Global COUNTERS

                bResponseArray = Constant.READ_GLOBAL_COUNTERS;

                break;

            case ICD_CMD_READ_ALARM_LOG: //0x2E Read Alarm Log

                bResponseArray = Constant.READ_ALARM_LOG;

                break;

            case ICD_CMD_READ_BRADY_COUNTERS: //0x2F Read Brady COUNTERS

                bResponseArray = Constant.READ_BRADY_COUNTERS;

                break;

            case ICD_CMD_READ_BATTERY_LOG: //0x30 Read Battery Log


                // use sequenceNumber as testCase indicator
                DeviceBatteryLog batteryLog = new DeviceBatteryLog(deviceMode, testCaseId);
                //DeviceBatteryLog batteryLog = new DeviceBatteryLog(1, 2);
                bResponseArray = batteryLog.getbReturnData();


                //bResponseArray = Constant.READ_BATTERY_LOG;

                break;

            case ICD_CMD_SET_BRADY_PARAM: // 0x48 Program Brady Parameters
                BradyState = packet.getpayload()[1] & 0xFF; // Get BradyState code : 0->9
                switch (BradyState) {
                    case 1:
                        fileName = folderName + Constant.BRADY_PARAMETER_NORM;
                        IOCommand(fileName, 2, packet);
                        break;
                    case 3:
                        fileName = folderName + Constant.BRADY_PARAMETER_POSTSHOCK;
                        IOCommand(fileName, 2, packet);
                        break;
                    default:
                        break;
                }

                if (bResponseArray == null) {
                    bResponseArray = Constant.READ_BRADY_PARAMETERS_NORM;
                }

                break;

            case ICD_CMD_READ_BRADY_PARAM: // 0x4A Read Brady Parameters

                BradyState = packet.getpayload()[0] & 0xFF;
                switch (BradyState) {
                    case 1:
                        fileName = folderName + Constant.BRADY_PARAMETER_NORM;
                        IOCommand(fileName, 1, packet);
                        break;
                    case 3:
                        fileName = folderName + Constant.BRADY_PARAMETER_POSTSHOCK;
                        IOCommand(fileName, 1, packet);
                        break;
                    default:

                        break;
                }

                if (bResponseArray == null) {
                    bResponseArray = Constant.READ_BRADY_PARAMETERS_NORM;
                }

                break;

            case ICD_CMD_SET_ALERT_PARAM: // 0x4B Write Alert parameters
                fileName = folderName + Constant.ALERTS;
                IOCommand(fileName, 2, packet);

                break;

            case ICD_CMD_CHARGE_CAPACITOR: // 0x4D Charge the capacitor
                fileName = folderName + "ChargeCapacitor.per";
                IOCommand(fileName, 3, packet);

                break;

            case ICD_CMD_SET_TACHY_DETECT_PARAM: //0x52 Set Tachy Mode Parameters
                fileName = folderName + Constant.TACHY_MODE_PARAMETER;
                IOCommand(fileName, 2, packet);

                break;

            case ICD_CMD_SET_TACHY_THERAPY_PARAM: //0x53 Set Tachy Therapy Parameters
                fileName = folderName + Constant.TACHY_THERAPY_PARAMETER;
                IOCommand(fileName, 2, packet);

                break;

            case ICD_CMD_READ_TACHY_THERAPY_PARAM: //0x54 Read Tachy Therapy Parameters
                fileName = folderName + Constant.TACHY_THERAPY_PARAMETER;
                IOCommand(fileName, 1, packet);

                if(bResponseArray==null){
                    bResponseArray = Constant.READ_TACHY_THERAPY_PARAMETER;
                }

                break;

            case ICD_CMD_READ_SINGLE_EPISODE: //0x64 Read Single Episode

                SingleEpisode episode = new SingleEpisode();
                bLongResponseArray = episode.getbLongReturnData();

                break;

            case ICD_CMD_READ_SINGLE_SEGMENT: //0x65 Read single episode segment

                EpisodeSegment segment = new EpisodeSegment();
                bLongResponseArray = segment.getbLongReturnData();

                break;

            case ICD_CMD_READ_SEGMENT_MARKERS: //0x67 Read segment marker

                EpisodeMarker marker = new EpisodeMarker();
                bLongResponseArray = marker.getbLongReturnData();

                break;

            case ICD_CMD_SET_PATIENT_INFO: //0x68 Set Patient Info
                if (patienInfoIndex == 1) {
                    fileName = folderName + Constant.PATIENT_INFO1;
                    IOCommand(fileName, 2, packet);
                    patienInfoIndex = 2;
                } else if (patienInfoIndex == 2) {
                    fileName = folderName + Constant.PATIENT_INFO2;
                    IOCommand(fileName, 2, packet);
                    patienInfoIndex = 1;
                }

                break;

            case ICD_CMD_READ_PATIENT_INFO: //0x69 Read Patient Info
                if (patienInfoIndex == 1) {
                    fileName = folderName + Constant.PATIENT_INFO1;
                    IOCommand(fileName, 1, packet);
                    patienInfoIndex = 2;
                    if (bResponseArray == null) {
                        bResponseArray = Constant.READ_PATIENT_INFO1;
                    }

                } else if (patienInfoIndex == 2) {
                    fileName = folderName + Constant.PATIENT_INFO2;
                    IOCommand(fileName, 1, packet);
                    patienInfoIndex = 1;
                    if (bResponseArray == null) {
                        bResponseArray = Constant.READ_PATIENT_INFO2;
                    }
                }

                break;

            case ICD_CMD_SET_PATIENT_LEADS_INFO: //0x6A Set Lead Info

                fileName = folderName + Constant.LEAD_INFO;
                IOCommand(fileName, 2, packet);

                break;

            case ICD_CMD_SET_SERIAL_MODEL_NUM: //0x6B Set SERIAL_MODEL_NUM

                fileName = folderName + Constant.SERIAL_NUMBER;
                IOCommand(fileName, 2, packet);

                break;

            case ICD_CMD_SET_CLINICIAN_NOTE: //0x6C Set Clinician Note

                fileName = folderName + Constant.CLINICIAN_NOTE;
                IOCommand(fileName, 2, packet);

                break;
            case ICD_CMD_PROGRAM_RTC_DELTA: //0x6D Program RTC Delta

                bResponseArray = Constant.PROGRAM_RTC_DELTA;

                break;


            case ICD_CMD_SET_MAGNET_MODE: //0x6E Set Magnet Mode

                bResponseArray = Constant.SET_MAGNET_MODE;

                break;

            case ICD_CMD_SET_GLOBAL_CONSTANTS: //0x73 Set GLOBAL_CONSTANTS

                fileName = folderName + Constant.GLOBAL_CONSTANT;
                IOCommand(fileName, 2, packet);

                break;

            case ICD_CMD_READ_CHARGE_LOG: //0x74 Read Charge Log
                bResponseArray = chargeLog_local.getbReturnData(deviceMode, testCaseId, packet.getpayload()[0]);
//                if(chargeLogCnt==1){
//                    bResponseArray = chargeLog_local.getbReturnData(chargeLogCnt);
//                    chargeLogCnt=2;
//                }else if(chargeLogCnt==2){
//                    bResponseArray = chargeLog_local.getbReturnData(chargeLogCnt);
//                    chargeLogCnt = 1;
//                }


                break;

            case ICD_CMD_SET_TACHY_MODE: //0x75 Set Tachy Mode OnOFF
                fileName = folderName + Constant.TACHY_MODE_ONOFF;
                IOCommand(fileName, 2, packet);

                break;

            case ICD_CMD_SET_TACHY_SVT_PARAM: //0x76 Set Tachy SVT Parameters
                fileName = folderName + Constant.TACHY_SVT_DETECTION;
                IOCommand(fileName, 2, packet);

                break;


            case ICD_CMD_BLE_IN_SESSION: // 0x80
                // Android sends you a keep alive signal.
                // No response needed.
                //System.out.println(DataConvert.bytesToHex(receivedBytes));
                if(deviceMode==11){
                    // if in Storage Mode
                    bResponseArray = Constant.STORAGE_MODE_STATUS;
                }

                break;

            case ICD_CMD_SAFETY_CORE_PARAM: // 0x87 Safty Core Parameter
                if(packet.getpayload().length==4){ // Retrieve
                    fileName = folderName + Constant.SAFETYCORE_BRADY;
                    IOCommand(fileName, 1, packet);

                }else{ // Program
                    byte[] payload = packet.getpayload();
                    byte[] newPayload = new byte[payload.length-4];
                    System.arraycopy(payload, 1, newPayload, 0, payload.length-4);
                    packet.setPayload(newPayload);
                    byte[] CRC32 = new byte[4];
                    CRC32 = DataConvert.calculateCRC32(newPayload,0,newPayload.length);
                    packet.setCrc32(CRC32);

                    fileName = folderName + Constant.SAFETYCORE_BRADY;
                    IOCommand(fileName, 2, packet);
                }

                if(bResponseArray==null){
                    bResponseArray = Constant.SAFTY_CORE_PARAMETERS;
                }

                break;

            case ICD_CMD_READ_BATTERY_DETAIL: // 0x88 Read Battery Detail

                bResponseArray = Constant.READ_BATTERY_DETAIL;

                break;

            case ICD_CMD_SET_BATTERY_ESTIMATE_CONSTANTS: // 0x89 BATTERY_ESTIMATE_CONSTANTS

                fileName = folderName + Constant.BATTERY_ESTIMATE_CONSTANTS;
                IOCommand(fileName, 2, packet);

                break;

            case ICD_CMD_BLE_WRITE_TX_POWER: // 0xB3 WRITE_TX_POWER

                fileName = folderName + Constant.TX_POWER;
                IOCommand(fileName, 2, packet);

                break;

            case ICD_CMD_BLE_READ_TX_POWER: // 0xB2 READ_TX_POWER

                fileName = folderName + Constant.TX_POWER;
                IOCommand(fileName, 1, packet);

                break;

            default:
                break;
            // Handle other commands
        }
    }

    /**
     * return a byte array as response if the array is not too long
     *
     * @return byte[]
     */
    public byte[] getResponse() {

        return bResponseArray;
    }

    /**
     * return a byte[][] if the response is too long and needs to send it separately
     *
     * @return byte[][]
     */
    public byte[][] getLongResponse() {
        return bLongResponseArray;
    }

    /**
     * Files I/O and serialization
     * If we don't wish to fake the data, use this method.
     *
     * @param fileName name of the serialized packet. eg. packet.per
     * @param mode     mode 1 == Read; mode 2 == Write
     */
    private void IOCommand(String fileName, int mode, DecodingPacket packet) {
        switch (mode){
            // Read
            case 1:{
                encodingPacket = new EncodingPacket(packet, false, fileName);
                bResponseArray = encodingPacket.getPacketData();
                break;
            }
            // Write
            case 2:{
                PacketManager.serialize(packet, fileName);
                encodingPacket = new EncodingPacket(packet, true, fileName);
                bResponseArray = encodingPacket.getPacketData();
                break;
            }
            // Programmer wants the device to do something.
            // Device only needs to return ACK.
            case 3:{
                encodingPacket = new EncodingPacket(packet, true, fileName);
                bResponseArray = encodingPacket.getPacketData();
                break;
            }
        }


    }


}
