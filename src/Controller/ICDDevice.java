package Controller;

import Constant.Constant;
import ParseData.*;
import pgdata.*;

import java.io.File;

public class ICDDevice implements ICDCommandDefinitions, FilesHandler {
    private byte[] byteArray;
    private String folderName;

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
            System.out.println("Received from client: " + packet);
        }
        // Initialize variable
        EncodingPacket encodingPacket = null;
        byteArray = null;
        String fileName;

        // Switch cases
        switch (iCommandId) {
            case ICD_CMD_BLE_IN_SESSION:
                // Android sends you a keep alive signal.
                // No response needed.
                //System.out.println(DataConvert.bytesToHex(receivedBytes));
                break;
            case ICD_CMD_READ_BRADY_PARAM:
                // Construct return packet based on incoming packet's sequenceNumber, commandID, crc
                fileName = folderName + Constant.BRADY_PARAMETERS;
                encodingPacket = new EncodingPacket(packet,false, fileName);
                byteArray = encodingPacket.getPacketData();

                System.out.println("Sent to client: " + DataConvert.byteDataToHexString(byteArray));

                break;

            case ICD_CMD_SET_BRADY_PARAM:
                fileName = folderName + Constant.BRADY_PARAMETERS;
                PacketManager.serialize(packet, fileName);
                encodingPacket = new EncodingPacket(packet,true, fileName);
                byteArray = encodingPacket.getPacketData();

                System.out.println("Sent to client: " + DataConvert.byteDataToHexString(byteArray));

                break;
            default:
                break;
            // Handle other commands
        }
    }

    public byte[] getResponse(){

       return byteArray;
    }

}
