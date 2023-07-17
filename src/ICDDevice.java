import ParseData.*;
import pgdata.*;

public class ICDDevice implements ICDCommandDefinitions {
    private byte[] byteArray;

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
    public void initializeDevice(){

    }

    // export/save to local XML document


    public void process(DecodingPacket packet){
        int iCommandId = packet.getCommandID() & 0xFF;

        if (iCommandId != ICD_CMD_BLE_IN_SESSION) {
            System.out.println("Received from client: " + packet);
        }

        EncodingPacket encodingPacket = null;
        byteArray = null;

        switch (iCommandId) {
            case ICD_CMD_BLE_IN_SESSION:
                // Android sends you a keep alive signal.
                // No response needed.
                //System.out.println(DataConvert.bytesToHex(receivedBytes));
                break;
            case ICD_CMD_READ_BRADY_PARAM:
                // Construct return packet based on incoming packet's sequenceNumber, commandID, crc
                encodingPacket = new EncodingPacket(packet,false);
                byteArray = encodingPacket.getPacketData();

                System.out.println("Sent to client: " + DataConvert.byteDataToHexString(byteArray));

                break;

            case ICD_CMD_SET_BRADY_PARAM:
                PacketManager.serialize(packet);
                encodingPacket = new EncodingPacket(packet,true);
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
