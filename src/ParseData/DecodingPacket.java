package ParseData;


import java.io.*;

/**
 * Decode the binary packet to Hex
 */
public class DecodingPacket implements ICDCommandDefinitions, Serializable {
    private static final long serialVersionUID = 1L;

    private byte[] receivedBuffer;
    private int size;
    private byte sequenceNumber;
    private byte commandID;
    private byte[] payload;
    private byte[] crc32;
    public DecodingPacket(byte[] receivedBuffer){
        this.receivedBuffer = receivedBuffer;
        parsePacket();
    }

    private void parsePacket(){
        size = receivedBuffer[0];

        // Check if the packet has at least 4 parts (length, sequence number, instruction id, crc32)
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

        int payloadSize = size - 4;
        payload = new byte[payloadSize];

        // payloads - the payloads are between sequence number and instruction id
        if(payloadSize>0){
            for (int i = 3; i < size - 1; i++) {
                payload[i-3] = receivedBuffer[i];
            }
        }

        // Instruction ID
        commandID = receivedBuffer[2];

        // crc32
        if (payloadSize > 3) { // Ensure we have at least 4 bytes
            crc32 = new byte[4];
            System.arraycopy(receivedBuffer, size - 5, crc32, 0, 4);
        }

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

    public byte[] getpayload() {
        return payload;
    }

    public byte[] getcrc32() {
        return crc32;
    }
    public String toString(){
        // Create the output string
        String output = "Command ID: " + DataConvert.byteToHex(commandID) + ", " +
                "Packet Length: " + size + ", " +
                "Sequence Number: " + DataConvert.byteToHex(sequenceNumber) + ", " +
                "payloads: " + DataConvert.bytesToHex(payload) + ", " + // remove trailing space
                "crc32: " + DataConvert.byteArrayToHexString(crc32);
        return output;
    }
}
