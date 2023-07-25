package Controller;

import Constant.Constant;
import ParseData.*;
import pgdata.*;
import pgdata.DeviceLog.*;

import java.io.File;

/**
 * Every Device connected to TCP Server would instantiate an ICDDevice
 * packet will be delivered as following logic TCP Server -> ICDDevice -> Modules -> process -> return to ICDDevice -> TCP Server -> TCP Client
 * Send packet to seperate Class to handle if we wish to fake the data.
 * Otherwise, use ICDCommand method directly for simple I/O
 */

public class ICDDevice implements ICDCommandDefinitions, FilesHandler {
    private int chargeLogCnt = 1;
    private int patienInfoIndex = 1;
    private int BradyParameterIndex = 1;
    private byte[] bResponseArray;
    private String folderName;
    private EncodingPacket encodingPacket;
    public BradyParameter bp_Local;
    public TachyDetection td_Local;
    public TachyATPTherapy atp_Local;
    public TachyShockTherapy shock_Local;
    public DeviceResetLog resetlog_Local;
    public DeviceFaultLog faultlog_Local;
    public DeviceTachyLog tachylog_local;
    public DeviceChargeLog chargeLog_local;
    public DeviceBatteryLog batteryLog_local;

    public LeadInfo li_Local;
    public PatientInformation pi_Local;
    public ClinicianNote cn_Local;

    private int dataMode = 1;

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
    public ICDDevice(int clientID){
        folderName = "src" + File.separator + "LocalData" + File.separator + "Device" + clientID + File.separator;
        FilesHandler.creatFolder(folderName);
        initializeDevice();
    }

    private void initializeDevice(){
        bp_Local = new BradyParameter();
        td_Local = new TachyDetection();
        atp_Local = new TachyATPTherapy();
        shock_Local = new TachyShockTherapy();

        // Device Log Randomized Generating.
        resetlog_Local = new DeviceResetLog();
        tachylog_local = new DeviceTachyLog();
        faultlog_Local = new DeviceFaultLog();
        chargeLog_local = new DeviceChargeLog();
        batteryLog_local = new DeviceBatteryLog();

        li_Local = new LeadInfo(folderName);
        pi_Local = new PatientInformation();
        cn_Local = new ClinicianNote(folderName);

    }

    // export/save to local XML document

    /**
     * Process the message sent from Android APP
     * @param receivedBytes byte array sent from Android app(TCP client)
     */
    public void process(byte[] receivedBytes){
        // Reset/Initialize parameters to null/default state;
        encodingPacket = null;
        bResponseArray = null;
        String fileName = null;
        int BradyState = 1;

        // if receivedByte is the indicator of testCase marker
        if(receivedBytes.length == 1){
            dataMode = receivedBytes[0];
            System.out.println("TestCase initialize message received");
            return;
        }
        DecodingPacket packet = new DecodingPacket(receivedBytes);
        int iCommandId = packet.getCommandID() & 0xFF;

        if (iCommandId != ICD_CMD_BLE_IN_SESSION) {
            System.out.println("Received: " + packet);
        }
        // Initialize variable

        // Switch cases
        switch (iCommandId) {
            case ICD_CMD_READ_ALERT_PARAM: //0x02 Read Alert Parameter

                bResponseArray = Constant.READ_ALERT_PARAMETER;

                break;

            case ICD_CMD_READ_TACHY_DETECT_PARAM: //0x04 Read Tachy Mode Parameter
                fileName = folderName + Constant.TACHY_MODE_PARAMETER;
                IOCommand(fileName, 1, packet);

                break;

            case ICD_CMD_READ_EPISODE_HEADER: //0x0B Read Episode Header

                //bResponseArray = Constant.READ_EPESODE_HEADER;

                break;

            case ICD_CMD_READ_PATIENT_LEADS_INFO: //0x0C Read Patient Lead Info
//                if(dataMode==1){
//                    fileName = folderName + Constant.LEAD_INFO;
//                    IOCommand(fileName, 1, packet);
//
//                    if(bResponseArray==null){
//                        bResponseArray = Constant.PATIENT_LEAD_INFO;
//                    }
//                }else if(dataMode==2){
//                    li_Local.process(packet);
//                    li_Local.setManufacturer(RandomData.generateRandomBytes(16));
//                    //li_Local.setManufacturer("自定义");
//                    bResponseArray = li_Local.getbRetrunData();
//                }

                li_Local.process(packet);
                li_Local.setManufacturer(RandomData.generateRandomBytes(16));
                //li_Local.setManufacturer("自定义");
                bResponseArray = li_Local.getbRetrunData();

                break;

            case ICD_CMD_READ_SERIAL_MODEL_NUM: //0x0D Read Device Serial Number

                bResponseArray = Constant.DEVICE_SERIAL_NUMBER;

                break;

            case ICD_CMD_READ_DAILY_MMT_HEADER: //0x0E Read Daily Measurement Header

                bResponseArray = Constant.READ_DAILY_MEASUREMENT_HEADER;

                break;

            case ICD_CMD_READ_CLINICIAN_NOTE: //0x0F Read Clinician Note
                fileName = folderName + Constant.CLINICIAN_NOTE;
                IOCommand(fileName, 1, packet);

                if(bResponseArray==null){
                    bResponseArray = Constant.READ_CLINICIAN_NOTE;
                }

                break;

            case ICD_CMD_READ_TACHY_LOG: //0x10 Read Tachy Log

                bResponseArray = tachylog_local.getbRetrunData();

                //bResponseArray = Constant.READ_TACHY_LOG;

                break;

            case ICD_CMD_READ_DEVICE_RESET_LOG: //0x11 Read Device Reset Log

                // Random Generated Data
                bResponseArray = resetlog_Local.getbRetrunData();

                break;

            case ICD_CMD_CLEAR_RESET_LOG: //0x13 Clear Device Reset Log

                fileName = folderName + Constant.DEVICE_RESET_LOG;
                IOCommand(fileName, 3, packet);

                break;

            case ICD_CMD_READ_DEVICE_FAULT_LOG: //0x14 Read Device Fault Log

                // Random Generated Data
                bResponseArray = faultlog_Local.getbRetrunData();


                //bResponseArray = Constant.READ_DEVICE_FAULT_LOG;

                break;

            case ICD_CMD_READ_TACHY_MODE: //0x15 Read Tachy Mode OnOff
                fileName = folderName + Constant.TACHY_MODE_ONOFF;
                IOCommand(fileName, 1, packet);

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

                bResponseArray = Constant.READ_GLOBAL_CONSTANTS;

                break;

            case ICD_CMD_READ_TACHY_SVT_PARAM: //0x26 Read Tachy SVT Parameters
                fileName = folderName + Constant.TACHY_SVT_DETECTION;
                IOCommand(fileName, 1, packet);

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

                bResponseArray = batteryLog_local.getbRetrunData();

                //bResponseArray = Constant.READ_BATTERY_LOG;

                break;

            case ICD_CMD_SET_BRADY_PARAM: // 0x48 Program Brady Parameters
                BradyState = packet.getpayload()[1] & 0xFF; // Get BradyState code : 0->9
                switch (BradyState){
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

                if(bResponseArray==null){
                    bResponseArray = Constant.READ_BRADY_PARAMETERS_NORM;
                }

                break;

            case ICD_CMD_READ_BRADY_PARAM: // 0x4A Read Brady Parameters
                if(dataMode==1){
                    BradyState = packet.getpayload()[0] & 0xFF;
                    switch (BradyState){
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

                    if(bResponseArray==null){
                        bResponseArray = Constant.READ_BRADY_PARAMETERS_NORM;
                    }
                }else if(dataMode == 2){
                    bResponseArray = Constant.READ_BRADY_PARAMETERS_NORM;
                }

                break;

            case ICD_CMD_SET_TACHY_DETECT_PARAM : //0x52 Set Tachy Mode Parameters
                fileName = folderName + Constant.TACHY_MODE_PARAMETER;
                IOCommand(fileName, 2, packet);

                break;

            case ICD_CMD_SET_TACHY_THERAPY_PARAM : //0x53 Set Tachy Therapy Parameters
                fileName = folderName + Constant.TACHY_THERAPY_PARAMETER;
                IOCommand(fileName, 2, packet);

                break;

            case ICD_CMD_READ_TACHY_THERAPY_PARAM : //0x54 Read Tachy Therapy Parameters
                fileName = folderName + Constant.TACHY_THERAPY_PARAMETER;
                IOCommand(fileName, 1, packet);

                break;

            case ICD_CMD_READ_SINGLE_EPISODE: //0x64 Read Single Episode

                bResponseArray = Constant.READ_SINGLE_EPISODE;

                break;

            case ICD_CMD_SET_PATIENT_INFO: //0x68 Set Patient Info
                if(patienInfoIndex==1){
                    fileName = folderName + Constant.PATIENT_INFO1;
                    IOCommand(fileName, 2, packet);
                    patienInfoIndex = 2;
                }else if(patienInfoIndex==2){
                    fileName = folderName + Constant.PATIENT_INFO2;
                    IOCommand(fileName, 2, packet);
                    patienInfoIndex = 1;
                }

                break;

            case ICD_CMD_READ_PATIENT_INFO: //0x69 Read Patient Info
                if(patienInfoIndex==1){
                    fileName = folderName + Constant.PATIENT_INFO1;
                    IOCommand(fileName, 1, packet);
                    patienInfoIndex = 2;
                    if(bResponseArray==null){
                        bResponseArray = Constant.READ_PATIENT_INFO1;
                    }

                }else if(patienInfoIndex==2){
                    fileName = folderName + Constant.PATIENT_INFO2;
                    IOCommand(fileName, 1, packet);
                    patienInfoIndex = 1;
                    if(bResponseArray==null){
                        bResponseArray = Constant.READ_PATIENT_INFO2;
                    }
                }

                break;

            case ICD_CMD_SET_PATIENT_LEADS_INFO: //0x6A Set Lead Info

                fileName = folderName + Constant.LEAD_INFO;
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

            case ICD_CMD_READ_CHARGE_LOG: //0x74 Read Charge Log
                bResponseArray = chargeLog_local.getbReturnData(packet.getpayload()[0]);
//                if(chargeLogCnt==1){
//                    bResponseArray = chargeLog_local.getbReturnData(chargeLogCnt);
//                    chargeLogCnt=2;
//                }else if(chargeLogCnt==2){
//                    bResponseArray = chargeLog_local.getbReturnData(chargeLogCnt);
//                    chargeLogCnt = 1;
//                }


                break;

            case ICD_CMD_SET_TACHY_MODE : //0x75 Set Tachy Mode OnOFF
                fileName = folderName + Constant.TACHY_MODE_ONOFF;
                IOCommand(fileName, 2, packet);

                break;

            case ICD_CMD_SET_TACHY_SVT_PARAM : //0x76 Set Tachy SVT Parameters
                fileName = folderName + Constant.TACHY_SVT_DETECTION;
                IOCommand(fileName, 2, packet);

                break;



            case ICD_CMD_BLE_IN_SESSION: // 0x80
                // Android sends you a keep alive signal.
                // No response needed.
                //System.out.println(DataConvert.bytesToHex(receivedBytes));
                break;

            case ICD_CMD_SAFETY_CORE_PARAM: // 0x87 Safty Core Parameter

                bResponseArray = Constant.SAFTY_CORE_PARAMETERS;

                break;

            case ICD_CMD_READ_BATTERY_DETAIL: // 0x88 Read Battery Detail

                bResponseArray = Constant.READ_BATTERY_DETAIL;

                break;

            default:
                break;
            // Handle other commands
        }
    }

    public byte[] getResponse(){

       return bResponseArray;
    }

    /**
     * Files I/O and serialization
     * If we don't wish to fake the data, use this method.
     * @param fileName name of the serialized packet. eg. packet.per
     * @param mode mode 1 == Read; mode 2 == Write
     */
    private void IOCommand(String fileName, int mode, DecodingPacket packet){
        // Read
        if(mode==1){
            encodingPacket = new EncodingPacket(packet,false, fileName);
            bResponseArray = encodingPacket.getPacketData();
        }
        // Write
        else if(mode==2){
            PacketManager.serialize(packet, fileName);
            encodingPacket = new EncodingPacket(packet,true, fileName);
            bResponseArray = encodingPacket.getPacketData();
        }
        else if(mode==3){
            FilesHandler.deleteFile(fileName);
            encodingPacket = new EncodingPacket(packet,true, fileName);
            bResponseArray = encodingPacket.getPacketData();
        }
    }



}
