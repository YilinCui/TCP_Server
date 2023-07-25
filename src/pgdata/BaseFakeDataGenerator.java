package pgdata;

import ParseData.DecodingPacket;


public abstract class BaseFakeDataGenerator {
    protected String fileName;
    protected byte[] bRetrunData;

    protected DecodingPacket packet;
    protected byte[] packetHeader = new byte[3];

    public BaseFakeDataGenerator(){

    }

    public void process(DecodingPacket packet){
        this.packet = packet;
        //note = packet.getpayload();
        // Customize your content
        // payload = ...;
        //packet.setPayload(note);
    }

    public abstract byte[] getbRetrunData();

}