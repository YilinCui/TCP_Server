package ParseData;

import DataStructure.DynamicByteBuffer;
import ParseData.DecodingPacket;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.zip.CRC32;

/**
 * To encoding a packet, you need 2 things
 * 1. The packet header(CRC, sequence Number, size, commandID) which fetched from the packet belongs to TCP client.
 * 2. The packet parameters. This needs the path name to locate the corresponding serialized packet.
 * 简单来说，返回包应包含client packet的packet header等关键信息。而packet body，也就是parameter应该根据要获取的参数信息从本地序列化的包中获取
 */
public class EncodingPacket implements ICDCommandDefinitions {
    private static final long serialVersionUID = 1L;
    private String file_name;
    private byte sequenceNumber;
    private byte commandID;
    private byte[] payload;
    private byte crc32[];
    private byte[] data;
    private byte[] ACK;
    boolean needACK;

    public EncodingPacket(DecodingPacket decodingPacket, boolean needACK, String file_name) {
        this.needACK = needACK;
        this.commandID = decodingPacket.getCommandID();
        this.sequenceNumber = decodingPacket.getSequenceNumber();
        if(!needACK){
            this.file_name = file_name;
            // By default, each retrieve operation should have corresponding serialized packet.
            try{
                DecodingPacket packet = PacketManager.deserialize(file_name);
                this.payload = packet.getpayload();
                this.crc32 = packet.getcrc32();
                constructPacket();
            }catch (NullPointerException c) {
                System.out.println("Can't find the file you looking for!" + file_name + ". Sending back default value...");
                data = null;
            }
        }else{
            ConstructACK();
        }

    }



    /**
     * If it's ACK signal, just send back hardcoding byte[] to bypass ACK check.
     * Otherwise, send back the encoding packet.
     * @return Packet data in the format of byte[]
     */
    public byte[] getPacketData() {
        if (needACK){
            return ACK;
        }
        return data;
    }

    public void constructPacket() {
        // 首先计算出新的byte数组的大小
        int totalLength = 3 + payload.length + crc32.length;  // 3->size, sequenceID, CommandID
//        int totalLength = payload.length + crc32.length;  // 3->size, sequenceID, CommandID
        // 初始化ByteBuffer
        DynamicByteBuffer buffer = new DynamicByteBuffer();

        // 将变量按照顺序添加到ByteBuffer中
        buffer.put((byte) totalLength);
        buffer.put(sequenceNumber);
        buffer.put(commandID);
        buffer.put(payload);
        buffer.put(crc32);

        // 将ByteBuffer转化为byte数组
        data = buffer.toArray();
    }

    private void ConstructACK() {
        ByteBuffer buffer = ByteBuffer.allocate(11);
        buffer.put((byte) 0x0F);  // Starts with 0x0C
        buffer.put(sequenceNumber);  // Followed by parameter
        buffer.put((byte) 0xBF);  // Then 0xBF
        buffer.put(commandID);  // Then commandID
        buffer.put((byte) 0x00);  // Then 0x00 means success
        buffer.put(sequenceNumber);  // Then sequenceNumber
        buffer.put(new byte[]{0x00, 0x00, 0x00, 0x00, 0x00});  // Ends with five 0x00s

        // Now you can use the buffer for your purposes.
        // For example, you can get the array of bytes:
        byte[] array = buffer.array();
        byte[] crc32 = DataConvert.calculateCRC32(array, 3, array.length - 3);
        ACK = new byte[array.length + crc32.length];
        System.arraycopy(array, 0, ACK, 0, array.length);
        System.arraycopy(crc32, 0, ACK, array.length, crc32.length);

    }



}
