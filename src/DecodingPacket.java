/**
 * Decode the binary packet to Hex
 */
public class DecodingPacket implements ICDCommandDefinitions{
    private String ICDCommand;
    private int size;
    private String sequenceNumber;
    private String instructionID;
    private String parameter;
    private String crc;
    private String tag="";
    public DecodingPacket(String ICDCommand){
        this.ICDCommand = ICDCommand;
        parsePacket();
    }

    private void parsePacket(){
        String[] parts = ICDCommand.split(" "); // split the packet into parts
        size = parts.length;

        // Check if the packet has at least 4 parts (length, sequence number, instruction id, CRC)
        if (parts.length < 4) {
            throw new IllegalArgumentException("Invalid packet , the packet length shouldn't be less than 4");
        }

        // Number of fields in the packet
        int fieldCount = Integer.parseInt(parts[0], 16);

        if(fieldCount!=size){
            throw new IllegalArgumentException("Invalid packet , the packet format does not fit ICDCommand rule");
        }

        // Sequence number
        sequenceNumber = parts[1];

        // Parameters - the parameters are between sequence number and instruction id
        parameter = "";
        for (int i = 2; i < fieldCount - 2; i++) {
            parameter += parts[i] + " ";
        }

        // Instruction ID
        instructionID = parts[2];
        if(instructionID.equals(DataConvert.hexToString(ICD_CMD_BLE_IN_SESSION))){
            tag = "ack";
        }

        // CRC
        crc = parts[fieldCount - 1];
    }

    public int getSize() {
        return size;
    }

    public String getSequenceNumber() {
        return sequenceNumber;
    }

    public String getInstructionID() {
        return instructionID;
    }

    public String getParameter() {
        return parameter.trim();
    }

    public String getCrc() {
        return crc;
    }
    public String getTag() {
        return tag;
    }

    public String toString(){
        if(instructionID.equals(DataConvert.hexToString(ICD_CMD_BLE_IN_SESSION))){
//            return "Are you still there?";
            return "";
        }
        // Create the output string
        String output = "Packet Length: " + size + ", " +
                "Sequence Number: " + sequenceNumber + ", " +
                "Parameters: " + parameter.trim() + ", " + // remove trailing space
                "Instruction ID: " + instructionID + ", " +
                "CRC: " + crc;

        return output;
    }
}
