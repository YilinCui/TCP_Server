package Controller;

import Constant.Constant;
import ParseData.*;
import pgdata.*;

import java.io.File;

public class ICDDevice implements ICDCommandDefinitions, FilesHandler {
    private int chargeLogCnt = 1;
    private byte[] byteArray;
    private String folderName;
    private EncodingPacket encodingPacket;

    public BradyParameter bp_Local;

    public TachyDetection td_Local;

    public TachyATPTherapy atp_Local;

    public TachyShockTherapy shock_Local;

    public DeviceResetLog resetlog_Local;

    public DeviceFaultLog faultlog_Local;


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
    }


    // export/save to local XML document


    public void process(DecodingPacket packet){
        int iCommandId = packet.getCommandID() & 0xFF;

        if (iCommandId != ICD_CMD_BLE_IN_SESSION) {
            System.out.println("Received: " + packet);
        }
        // Initialize variable
        encodingPacket = null;
        byteArray = null;
        String fileName = null;
        // Switch cases
        switch (iCommandId) {
            case ICD_CMD_READ_ALERT_PARAM: //0x02 Read Alert Parameter

                byteArray = Constant.READ_ALERT_PARAMETER;

                break;

            case ICD_CMD_READ_TACHY_DETECT_PARAM: //0x04 Read Tachy Mode Parameter
                fileName = folderName + Constant.TACHY_MODE_PARAMETER;
                IOCommand(fileName, 1, packet);

                break;

            case ICD_CMD_READ_EPISODE_HEADER: //0x0B Read Episode Header

                byteArray = Constant.READ_EPESODE_HEADER;

                break;

            case ICD_CMD_READ_PATIENT_LEADS_INFO: //0x0C Read Patient Lead Info

                byteArray = Constant.PATIENT_LEAD_INFO;

                break;

            case ICD_CMD_READ_SERIAL_MODEL_NUM: //0x0D Read Device Serial Number

                byteArray = Constant.DEVICE_SERIAL_NUMBER;

                break;

            case ICD_CMD_READ_DAILY_MMT_HEADER: //0x0E Read Daily Measurement Header

                byteArray = Constant.READ_DAILY_MEASUREMENT_HEADER;

                break;

            case ICD_CMD_READ_CLINICIAN_NOTE: //0x0F Read Clinician Note

                byteArray = Constant.READ_CLINICIAN_NOTE;

                break;

            case ICD_CMD_READ_TACHY_LOG: //0x10 Read Tachy Log

                byteArray = Constant.READ_TACHY_LOG;

                break;

            case ICD_CMD_READ_DEVICE_RESET_LOG: //0x11 Read Device Reset Log

                byteArray = Constant.READ_DEVICE_RESET_LOG;

                break;

            case ICD_CMD_READ_DEVICE_FAULT_LOG: //0x14 Read Device Fault Log

                byteArray = Constant.READ_DEVICE_FAULT_LOG;

                break;

            case ICD_CMD_READ_TACHY_MODE: //0x15 Read Tachy Mode OnOff
                fileName = folderName + Constant.TACHY_MODE_ONOFF;
                IOCommand(fileName, 1, packet);

                break;

            case ICD_CMD_RETRIEVE_PG_TIMER_AND_RTC_DELTA: //0x19 Retrieve M2 Timer and RTC Timer

                byteArray = Constant.READ_M2_RTC_TIMER;

                break;

            case ICD_CMD_RETRIEVE_RATE_HISTOGRAM: //0x1B RETRIEVE RATE HISTOGRAM

                byteArray = Constant.RETRIEVE_RATE_HISTOGRAM;

                break;

            case ICD_CMD_RETRIEVE_RATE_HISTOGRAM_LIFETIME: //0x1C RETRIEVE RATE HISTOGRAM LIFETIME

                byteArray = Constant.RETRIEVE_RATE_HISTOGRAM_LIFETIME;

                break;

            case ICD_CMD_READ_CAPACITOR_REFORM: //0x20 Read Cap Reform Log

                byteArray = Constant.READ_CAP_REFORM_LOG;

                break;

            case ICD_CMD_READ_GLOBAL_CONSTANTS: //0x24 Read Global Constants

                byteArray = Constant.READ_GLOBAL_CONSTANTS;

                break;

            case ICD_CMD_READ_TACHY_SVT_PARAM: //0x26 Read Tachy SVT Parameters
                fileName = folderName + Constant.TACHY_SVT_DETECTION;
                IOCommand(fileName, 1, packet);

                break;

            case ICD_CMD_FIRMWARE_VERSION: //0x27 Read FW Revision

                byteArray = Constant.FIRMWARE_VERSION;

                break;

            case ICD_CMD_READ_PACE_THRESHOLD_LOG: //0x29 Read Pace Threshold Log

                byteArray = Constant.READ_PACE_THRESHOLD_LOG;

                break;

            case ICD_CMD_READ_LAST_MEASUREMENTS: //0x2A Read Most Recent Measurement

                byteArray = Constant.READ_MOST_RECENT_MEASUREMENT;

                break;

            case ICD_CMD_READ_GLOBAL_COUNTERS: //0x2B Read Global COUNTERS

                byteArray = Constant.READ_GLOBAL_COUNTERS;

                break;

            case ICD_CMD_READ_ALARM_LOG: //0x2E Read Alarm Log

                byteArray = Constant.READ_ALARM_LOG;

                break;

            case ICD_CMD_READ_BRADY_COUNTERS: //0x2F Read Brady COUNTERS

                byteArray = Constant.READ_BRADY_COUNTERS;

                break;

            case ICD_CMD_READ_BATTERY_LOG: //0x30 Read Battery Log

                byteArray = Constant.READ_BATTERY_LOG;

                break;

            case ICD_CMD_SET_BRADY_PARAM: // 0x48 Program Brady Parameters
                fileName = folderName + Constant.BRADY_PARAMETER;
                IOCommand(fileName, 2, packet);

                break;

            case ICD_CMD_READ_BRADY_PARAM: // 0x4A Read Brady Parameters
                fileName = folderName + Constant.BRADY_PARAMETER;
                IOCommand(fileName, 1, packet);

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

                byteArray = Constant.READ_SINGLE_EPISODE;

                break;

            case ICD_CMD_READ_PATIENT_INFO: //0x69 Read Patient Info

                byteArray = Constant.READ_PATIENT_INFO;

                break;

            case ICD_CMD_SET_MAGNET_MODE: //0x6E Set Magnet Mode

                byteArray = Constant.SET_MAGNET_MODE;

                break;

            case ICD_CMD_READ_CHARGE_LOG: //0x74 Read Charge Log
                if(chargeLogCnt==1){
                    byteArray = Constant.READ_CHARGE_LOG1;
                    chargeLogCnt++;
                }else byteArray = Constant.READ_CHARGE_LOG2;


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

                byteArray = Constant.SAFTY_CORE_PARAMETERS;

                break;

            case ICD_CMD_READ_BATTERY_DETAIL: // 0x88 Read Battery Detail

                byteArray = Constant.READ_BATTERY_DETAIL;

                break;

            default:
                break;
            // Handle other commands
        }
    }

    public byte[] getResponse(){

       return byteArray;
    }

    /**
     * Files I/O and serialization
     * @param fileName name of the serialized packet. eg. packet.per
     * @param mode mode 1 == Read; mode 2 == Write
     */
    private void IOCommand(String fileName, int mode, DecodingPacket packet){
        // Read
        if(mode==1){
            encodingPacket = new EncodingPacket(packet,false, fileName);
            byteArray = encodingPacket.getPacketData();
        }
        // Write
        else if(mode==2){
            PacketManager.serialize(packet, fileName);
            encodingPacket = new EncodingPacket(packet,true, fileName);
            byteArray = encodingPacket.getPacketData();
        }
    }
}
