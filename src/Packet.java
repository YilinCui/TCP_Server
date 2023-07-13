public class Packet {
    private String ICDCommand;
    private int size;
    private String sequenceNumber;
    private String instructionID;
    private String parameter;
    private String crc;

    public Packet(String ICDCommand){
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

        // CRC
        crc = parts[fieldCount - 1];


    }

    public String toString(){
        // Create the output string
        String output = "Packet Length: " + size + ", " +
                "Sequence Number: " + sequenceNumber + ", " +
                "Parameters: " + parameter.trim() + ", " + // remove trailing space
                "Instruction ID: " + instructionID + ", " +
                "CRC: " + crc;
        return output;
    }
}
