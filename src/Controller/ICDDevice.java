package Controller;

import Constant.Constant;
import ParseData.*;
import pgdata.*;

import java.io.File;

public class ICDDevice implements ICDCommandDefinitions, FilesHandler {
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
            case ICD_CMD_READ_TACHY_DETECT_PARAM: //0x04 Read Tachy Mode Parameter
                fileName = folderName + Constant.TACHY_MODE_PARAMETER;
                IOCommand(fileName, 1, packet);

                break;

            case ICD_CMD_READ_TACHY_MODE: //0x15 Read Tachy Mode OnOff
                fileName = folderName + Constant.TACHY_MODE_ONOFF;
                IOCommand(fileName, 1, packet);

                break;

            case ICD_CMD_READ_TACHY_SVT_PARAM: //0x26 Read Tachy SVT Parameters
                fileName = folderName + Constant.TACHY_SVT_DETECTION;
                IOCommand(fileName, 1, packet);

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
