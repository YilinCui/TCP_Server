package ParseData;


import java.io.*;
import java.util.Arrays;

/**
 * Decode the binary packet to Hex
 */
public class DecodingPacket implements ICDCommandDefinitions, Serializable {
    private static final long serialVersionUID = 1L;

    private byte[] receivedBuffer;
    private int size;
    private byte sequenceNumber = 0x00;
    private byte commandID = 0x00;
    private byte[] payload = new byte[0];
    private byte[] crc32 = new byte[0];
    public DecodingPacket(byte[] receivedBuffer){
        this.receivedBuffer = receivedBuffer;
        parsePacket();
    }

    private void parsePacket(){
        size = receivedBuffer[0] & 0xFF;
        // Instruction ID
        commandID = receivedBuffer[2];
        if(size==0){
            System.out.println("TestCase Configuration received!");
            System.out.println("receivedBuffer is " + Arrays.toString(receivedBuffer));
            sequenceNumber = receivedBuffer[1];
            System.out.println("sequenceNumber is " + sequenceNumber);
            return;
        }

        // Check if the packet has at least 4 parts (length, sequence number, instruction id, crc32)
        if (receivedBuffer.length < 4) {
            throw new IllegalArgumentException("Invalid packet , the packet length shouldn't be less than 4");
        }

        // Number of fields in the packet
        int fieldCount = receivedBuffer.length;

        if(fieldCount!=size){
            throw new IllegalArgumentException("Invalid packet , the packet format does not fit ICDCommand rule because the length of the byte[]!= claimed packet length \n" +
                    "The invalid packet is: " + DataConvert.bytesToHex(receivedBuffer));
        }

        // Sequence number
        sequenceNumber = receivedBuffer[1];

        // the size of payload should be either 0, or larger or equals to 4
        int payloadSize = size - 4==0?0:size-8;
        payload = new byte[payloadSize];

        // payloads - the payloads are between sequence number and instruction id
        if(payloadSize>0){
            for (int i = 3; i < size - 5; i++) {
                payload[i-3] = receivedBuffer[i];
            }
        }



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

    public void setSize(int size) {
        this.size = size;
    }

    public void setSequenceNumber(byte sequenceNumber) {
        this.sequenceNumber = sequenceNumber;
    }

    public void setCommandID(byte commandID) {
        this.commandID = commandID;
    }

    public void setPayload(byte[] payload) {
        this.payload = payload;
    }

    public void setCrc32(byte[] crc32) {
        this.crc32 = crc32;
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
                "Packet Length(int): " + size + ", " +
                "Sequence Number: " + DataConvert.byteToHex(sequenceNumber) + ", " +
                "payloads(Hex): " + DataConvert.bytesToHex(payload) + ", " +
                "crc32: " + DataConvert.byteArrayToHexString(crc32);
        return output;
    }
}
