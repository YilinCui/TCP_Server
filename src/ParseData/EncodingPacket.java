package ParseData;

import ParseData.DecodingPacket;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.nio.ByteBuffer;

public class EncodingPacket implements ICDCommandDefinitions {
    private static final long serialVersionUID = 1L;
    private static final String FILE_NAME = "DecodingPacket.ser";
    private byte sequenceNumber;
    private byte commandID;
    private byte[] parameter;
    private byte crc;
    private byte[] data;
    boolean needACK;

    public EncodingPacket(DecodingPacket decodingPacket, boolean needACK) {
        this.sequenceNumber = decodingPacket.getSequenceNumber();
        this.commandID = decodingPacket.getCommandID();
        this.crc = decodingPacket.getCrc();

        DecodingPacket packet = decodingPacket.deserialize();
        this.parameter = packet.getParameter();

        this.needACK = needACK;

        constructPacket();
    }

    public byte[] getPacketData() {
        if (needACK){
            return new byte[] {
                    0x0C, 0x60, (byte)0xBF, 0x48, 0x00, 0x74, 0x00, 0x00,
                    0x00, 0x00, 0x00, (byte)0xE6, 0x49, (byte)0xE9, 0x0F
            };
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
