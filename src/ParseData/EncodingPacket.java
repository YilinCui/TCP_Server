package ParseData;

import ParseData.DecodingPacket;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.nio.ByteBuffer;

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
    private byte[] parameter;
    private byte crc;
    private byte[] data;
    boolean needACK;

    public EncodingPacket(DecodingPacket decodingPacket, boolean needACK, String file_name) {
        this.needACK = needACK;
        this.commandID = decodingPacket.getCommandID();
        if(!needACK){
            this.sequenceNumber = decodingPacket.getSequenceNumber();

            this.crc = decodingPacket.getCrc();

            this.file_name = file_name;
            // By default, each retrieve operation should have corresponding serialized packet.
            try{
                DecodingPacket packet = PacketManager.deserialize(file_name);
                this.parameter = packet.getParameter();
                constructPacket();
            }catch (NullPointerException c) {
                System.out.println("Can't find the file you looking for!" + file_name);
//                c.printStackTrace();
            }
        }

    }

    /**
     * If it's ACK signal, just send back hardcoding byte[] to bypass ACK check.
     * Otherwise, send back the encoding packet.
     * @return Packet data in the format of byte[]
     */
    public byte[] getPacketData() {
        if (needACK){
            switch(commandID){
                case 0x48:
                    return new byte[] {
                            0x0C, 0x60, (byte)0xBF, commandID, 0x00, 0x74, 0x00, 0x00,
                            0x00, 0x00, 0x00, (byte)0xE6, 0x49, (byte)0xE9, 0x0F
                    };
                case 0x76:
                    return new byte[] {
                            0x0C, 0x60, (byte)0xBF, commandID, 0x00, 0x74, 0x00, 0x00,
                            0x00, 0x00, 0x00, (byte)0xE6, 0x49, (byte)0xE9, 0x0F
                    };
            }

        }
        return data;
    }

    public void constructPacket() {
        // 首先计算出新的byte数组的大小
        int totalLength = 4 + parameter.length;  // 这里4是因为有4个byte变量

        // 初始化ByteBuffer
        ByteBuffer buffer = ByteBuffer.allocate(totalLength);

        // 将变量按照顺序添加到ByteBuffer中
        buffer.put((byte) totalLength);
        buffer.put(sequenceNumber);
        buffer.put(commandID);
        buffer.put(parameter);
        buffer.put(crc);

        // 将ByteBuffer转化为byte数组
        data = buffer.array();
    }

}
