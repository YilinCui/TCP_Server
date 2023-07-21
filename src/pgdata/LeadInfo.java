package pgdata;

import Constant.Constant;
import ParseData.DataConvert;
import ParseData.DecodingPacket;
import ParseData.EncodingPacket;
import ParseData.PacketManager;

import javax.xml.crypto.Data;
import java.nio.ByteBuffer;

/**
 * Y Parameter CRC32 check
 */
public class LeadInfo extends BaseFakeDataGenerator{
    // Payload could contain CRC32, or doesn't.
    //private byte[] defaultProgramPayload = DataConvert.hexStringToByteArray("E5 B4 94 E7 9A 84 E9 BB 91 E5 BF 83 E5 8E 82 00 31 31 34 35 31 34 00 00 00 00 00 00 00 00 00 00 E6 9E 81 E4 B9 90 E5 87 80 E5 9C 9F 00 00 00 00 32 30 32 33 30 37 32 31 00 00 00 00 E5 B4 94 E7 9A 84 E9 BB 91 E5 BF 83 E5 8E 82 00 31 31 34 35 31 34 00 00 00 00 00 00 00 00 00 00 E6 9E 81 E4 B9 90 E5 87 80 E5 9C 9F 00 00 00 00 32 30 32 33 30 37 32 31 00 00 00 00 17 42 73 74");
    private byte[] Manufacturer = new byte[16];
    private byte[] ModelNumber = new byte[16];
    private byte[] SerialNumber = new byte[16];
    private byte[] ImplantedDate = new byte[8];
    private byte Magnet;
    private byte ImplantedPart;
    private byte[] Reserved = {0x00,0x00};
    private byte[] payload;
    private byte[] CRC32;
    public LeadInfo(String folderName){
        fileName = folderName + Constant.LEAD_INFO;
        initialize();
    }

    protected void initialize(){
        Magnet = 0x00;
        ImplantedPart = 0x00;
    }

    public void process(DecodingPacket packet){
        super.process(packet);
        EncodingPacket encodingPacket = new EncodingPacket(packet, false, fileName);
        bRetrunData = encodingPacket.getPacketData();
        System.out.println(DataConvert.bytesToHex(bRetrunData));
        if(bRetrunData!=null){
            System.arraycopy(bRetrunData, 0, packetHeader, 0, 3);
            System.arraycopy(bRetrunData, 3, Manufacturer, 0, 16);
            System.arraycopy(bRetrunData, 19, ModelNumber, 0, 16);
            System.arraycopy(bRetrunData, 35, SerialNumber, 0, 16);
            System.arraycopy(bRetrunData, 51, ImplantedDate, 0, 8);
        }

    }



    public byte[] getbRetrunData(){
        // Construct Payload. Need to recalculate CRC32
        // 长度计算方式见文档
        int totalLength = (Manufacturer.length + ModelNumber.length + SerialNumber.length + ImplantedDate.length + 4)*2;  // length of packet payload(not including CRC32)

        // 初始化ByteBuffer
        ByteBuffer buffer = ByteBuffer.allocate(totalLength);

        // 将变量按照顺序添加到ByteBuffer中
        buffer.put(Manufacturer);
        buffer.put(ModelNumber);
        buffer.put(SerialNumber);
        buffer.put(ImplantedDate);
        buffer.put(ImplantedPart);
        buffer.put(Reserved);
        // idk why they duplicate these
        buffer.put(Manufacturer);
        buffer.put(ModelNumber);
        buffer.put(SerialNumber);
        buffer.put(ImplantedDate);
        buffer.put(ImplantedPart);
        buffer.put(Reserved);

        // 将ByteBuffer转化为byte数组
        payload = buffer.array();

        byte[] parameterCRC32 = DataConvert.calculateCRC32(payload, 0, payload.length);

        ByteBuffer buffer2 = ByteBuffer.allocate(payload.length + parameterCRC32.length);
        buffer2.put(payload);
        buffer2.put(parameterCRC32);

        byte[] packetBody = buffer2.array();
        byte[] CRC32 = DataConvert.calculateCRC32(packetBody, 0, packetBody.length);

        ByteBuffer buffer3 = ByteBuffer.allocate(3 + packetBody.length + CRC32.length);
        buffer3.put(packetHeader);
        buffer3.put(packetBody);
        buffer3.put(CRC32);

        bRetrunData = buffer3.array();

        return bRetrunData;
    }

    public byte[] getManufacturer() {
        return Manufacturer;
    }

    public void setManufacturer(byte[] manufacturer) {
        Manufacturer = manufacturer;
    }

    public byte[] getModelNumber() {
        return ModelNumber;
    }

    public void setModelNumber(byte[] modelNumber) {
        ModelNumber = modelNumber;
    }

    public byte[] getSerialNumber() {
        return SerialNumber;
    }

    public void setSerialNumber(byte[] serialNumber) {
        SerialNumber = serialNumber;
    }

    public byte[] getImplantedDate() {
        return ImplantedDate;
    }

    public void setImplantedDate(byte[] implantedDate) {
        ImplantedDate = implantedDate;
    }

    public byte getMagnet() {
        return Magnet;
    }

    public void setMagnet(byte magnet) {
        Magnet = magnet;
    }

    public byte getImplantedPart() {
        return ImplantedPart;
    }

    public void setImplantedPart(byte implantedPart) {
        ImplantedPart = implantedPart;
    }

    public byte[] getCRC32() {
        return CRC32;
    }

    public void setCRC32(byte[] CRC32) {
        this.CRC32 = CRC32;
    }
}
