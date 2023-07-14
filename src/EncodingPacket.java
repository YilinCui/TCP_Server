public class EncodingPacket implements ICDCommandDefinitions {
    private int size = 0;
    private String sequenceNumber;
    private String instructionID;
    private String parameter;
    private String crc;
    private byte[] data;

    public EncodingPacket(DecodingPacket decodingPacket){
        this.sequenceNumber = decodingPacket.getSequenceNumber();
        this.instructionID = decodingPacket.getInstructionID();
        this.parameter = decodingPacket.getParameter();
        this.crc = decodingPacket.getCrc();
        initializePacket();
    }

    private void initializePacket(){
        if(instructionID.equals(DataConvert.hexToString(ICD_CMD_BLE_IN_SESSION))){
            size = 8;
            data = new byte[size];
            data[0] = (byte)size;
            data[1] = (byte)Integer.parseInt(sequenceNumber,16);
            data[2] = (byte)Integer.parseInt(instructionID,16);
            data[size-1] = (byte)Integer.parseInt(crc,16);
            if(size>4){
                String[] parts = parameter.split(" ");
                for(int i = 3;i<size-1;i++){
                    data[i] = (byte)Integer.parseInt(parts[i-3],16);
                }
            }

        }
    }

    public byte[] getPacketData(){
        return data;
    }

}
