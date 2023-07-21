package pgdata;

import ParseData.DecodingPacket;
import ParseData.EncodingPacket;
import ParseData.PacketManager;

public abstract class BaseFakeDataGenerator {
    protected String fileName;
    protected byte[] bRetrunData;

    protected DecodingPacket packet;

    public BaseFakeDataGenerator(){

    }

    protected void initialize(){

    }

    public void process(DecodingPacket packet){
        this.packet = packet;
        //note = packet.getpayload();
        // Customize your content
        // payload = ...;
        //packet.setPayload(note);
    }

}
