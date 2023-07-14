package ParseData;


import java.io.*;

/**
 * Decode the binary packet to Hex
 */
public class DecodingPacket implements ICDCommandDefinitions, Serializable {
    private static final long serialVersionUID = 1L;
    private static final String FILE_NAME = "DecodingPacket.ser";
    private byte[] receivedBuffer;
    private int size;
    private byte sequenceNumber;
    private byte commandID;
    private byte[] parameter;
    private byte crc;
    public DecodingPacket(byte[] receivedBuffer){
        this.receivedBuffer = receivedBuffer;
        parsePacket();
    }

    private void parsePacket(){
        size = receivedBuffer[0];

        // Check if the packet has at least 4 parts (length, sequence number, instruction id, CRC)
        if (size < 4) {
            throw new IllegalArgumentException("Invalid packet , the packet length shouldn't be less than 4");
        }

        // Number of fields in the packet
        int fieldCount = receivedBuffer.length;

        if(fieldCount!=size){
            throw new IllegalArgumentException("Invalid packet , the packet format does not fit ICDCommand rule \n" +
                    "The invalid packet is: " + DataConvert.bytesToHex(receivedBuffer));
        }

        // Sequence number
        sequenceNumber = receivedBuffer[1];

        int parameterSize = size - 4;
        parameter = new byte[parameterSize];

        // Parameters - the parameters are between sequence number and instruction id
        if(parameterSize>0){
            for (int i = 3; i < size - 1; i++) {
                parameter[i-3] = receivedBuffer[i];
            }
        }

        // Instruction ID
        commandID = receivedBuffer[2];

        // CRC
        crc = receivedBuffer[size - 1];
    }

    public void serialize() {
        try {
            FileOutputStream fileOut = new FileOutputStream(FILE_NAME);
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(this);
            out.close();
            fileOut.close();
        } catch (Exception i) {
            i.printStackTrace();
        }
    }
    public DecodingPacket deserialize() {
        DecodingPacket packet = null;
        try {
            FileInputStream fileIn = new FileInputStream(FILE_NAME);
            ObjectInputStream in = new ObjectInputStream(fileIn);
            packet = (DecodingPacket) in.readObject();
            in.close();
            fileIn.close();
        } catch (IOException i) {
            i.printStackTrace();
            return null;
        } catch (ClassNotFoundException c) {
            System.out.println("DecodingPacket class not found");
            c.printStackTrace();
            return null;
        }
        return packet;
    }


    public int getSize() {
        return size;
    }

    public byte getSequenceNumber() {
        return sequenceNumber;
    }

    public byte getCommandID() {
        return commandID;
    }

    public byte[] getParameter() {
        return parameter;
    }

    public byte getCrc() {
        return crc;
    }
    public String toString(){
        // Create the output string
        String output = "Packet Length: " + size + ", " +
                "Sequence Number: " + DataConvert.byteToHex(sequenceNumber) + ", " +
                "Parameters: " + DataConvert.bytesToHex(parameter) + ", " + // remove trailing space
                "Command ID: " + DataConvert.byteToHex(commandID) + ", " +
                "CRC: " + DataConvert.byteToHex(crc);
        return output;
    }
}
