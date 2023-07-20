package pgdata;

import Constant.Constant;
import ParseData.DecodingPacket;
import ParseData.EncodingPacket;
import ParseData.PacketManager;

public class LeadInfo {
    private String fileName;
    private byte[] bRetrunData;
    private int mode;
    private DecodingPacket packet;

    public LeadInfo(String folderName){
        //fileName = folderName + Constant.DEVICE;
        initialize();
    }

    private void initialize(){

    }

    public byte[] getbRetrunData(){
        // Read
        if(mode==1){
            EncodingPacket encodingPacket = new EncodingPacket(packet, false, fileName);
            bRetrunData = encodingPacket.getPacketData();
        }
        // Write
        else if(mode==2){
            PacketManager.serialize(packet, fileName);
            EncodingPacket encodingPacket = new EncodingPacket(packet, true, fileName);
            bRetrunData = encodingPacket.getPacketData();
        }
        if(bRetrunData==null){
            return Constant.READ_CLINICIAN_NOTE;
        }
        return  bRetrunData;
    }
}
