package pgdata;

import Constant.Constant;
import ParseData.DecodingPacket;
import ParseData.EncodingPacket;
import ParseData.PacketManager;

public class ClinicianNote {
    private String fileName;
    private byte[] bRetrunData;
    private int mode;
    private DecodingPacket packet;

    public ClinicianNote(String folderName){
        fileName = folderName + Constant.CLINICIAN_NOTE;
        initialize();
    }

    private void initialize(){

    }

    public void process(DecodingPacket packet, int mode){
        this.mode = mode;
        this.packet = packet;
        //note = packet.getpayload();
        // Customize your content
        // payload = ...;
        //packet.setPayload(note);


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
