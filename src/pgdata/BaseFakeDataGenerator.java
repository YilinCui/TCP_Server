package pgdata;

import ParseData.DecodingPacket;
import ParseData.EncodingPacket;
import ParseData.PacketManager;

import java.util.Random;

public abstract class BaseFakeDataGenerator {
    protected String fileName;
    protected byte[] bRetrunData;

    protected DecodingPacket packet;
    protected byte[] packetHeader = new byte[3];

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
