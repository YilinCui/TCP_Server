package pgdata;

import Constant.Constant;
import DataStructure.DynamicByteBuffer;
import ParseData.DataConvert;
import ParseData.DecodingPacket;
import ParseData.EncodingPacket;
/**
 * This module needs Parameter CRC32 check
 */
public class LeadInfo {
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

    private String fileName;
    private byte[] bRetrunData;
    private byte[] packetHeader = new byte[]{(byte) 0x83, 0x04, 0x0C};
    public LeadInfo(String folderName){
        fileName = folderName + Constant.LEAD_INFO;
        initialize();
    }

    protected void initialize(){
        Magnet = 0x00;
        ImplantedPart = 0x00;
    }

    public void process(DecodingPacket packet){

        EncodingPacket encodingPacket = new EncodingPacket(packet, false, fileName);
        bRetrunData = encodingPacket.getPacketData();

        if(bRetrunData!=null){
            //System.out.println(DataConvert.bytesToHex(bRetrunData));
            //System.arraycopy(bRetrunData, 0, packetHeader, 0, 3);
            System.arraycopy(bRetrunData, 3, Manufacturer, 0, 16);
            System.arraycopy(bRetrunData, 19, ModelNumber, 0, 16);
            System.arraycopy(bRetrunData, 35, SerialNumber, 0, 16);
            System.arraycopy(bRetrunData, 51, ImplantedDate, 0, 8);
        }

    }

    public byte[] getbRetrunData(){
        if(bRetrunData==null)return null;
        // Construct Payload. Need to recalculate CRC32

        // 初始化ByteBuffer
        DynamicByteBuffer buffer = new DynamicByteBuffer();

        // 将变量按照顺序添加到ByteBuffer中
        buffer.put(Manufacturer);
        buffer.put(ModelNumber);
        buffer.put(SerialNumber);
        buffer.put(ImplantedDate);
        buffer.put(Magnet);
        buffer.put(ImplantedPart);
        buffer.put(Reserved);
        // idk why they duplicate these
        buffer.put(Manufacturer);
        buffer.put(ModelNumber);
        buffer.put(SerialNumber);
        buffer.put(ImplantedDate);
        buffer.put(Magnet);
        buffer.put(ImplantedPart);
        buffer.put(Reserved);

        // 将ByteBuffer转化为byte数组
        payload = buffer.toArray();

        byte[] parameterCRC32 = DataConvert.calculateCRC32(payload, 0, payload.length);
        buffer.put(parameterCRC32);

        byte[] packetBody = buffer.toArray();
        byte[] CRC32 = DataConvert.calculateCRC32(packetBody, 0, packetBody.length);

        buffer.put(CRC32);
        buffer.put(0, packetHeader);

        bRetrunData = buffer.toArray();

        return bRetrunData;
    }

    public byte[] getManufacturer() {
        return Manufacturer;
    }

    public void setManufacturer(byte[] manufacturer) {
        Manufacturer = manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        Manufacturer = DataConvert.stringToByteArray(manufacturer, Manufacturer.length);
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
