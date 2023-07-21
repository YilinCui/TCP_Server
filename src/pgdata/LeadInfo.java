package pgdata;

import Constant.Constant;
import ParseData.DataConvert;
import ParseData.DecodingPacket;
import ParseData.EncodingPacket;
import ParseData.PacketManager;

/**
 * Y Parameter CRC32 check
 */
public class LeadInfo extends BaseFakeDataGenerator{
    // Payload could contain CRC32, or doesn't.
    //private byte[] defaultProgramPayload = DataConvert.hexStringToByteArray("E5 B4 94 E7 9A 84 E9 BB 91 E5 BF 83 E5 8E 82 00 31 31 34 35 31 34 00 00 00 00 00 00 00 00 00 00 E6 9E 81 E4 B9 90 E5 87 80 E5 9C 9F 00 00 00 00 32 30 32 33 30 37 32 31 00 00 00 00 E5 B4 94 E7 9A 84 E9 BB 91 E5 BF 83 E5 8E 82 00 31 31 34 35 31 34 00 00 00 00 00 00 00 00 00 00 E6 9E 81 E4 B9 90 E5 87 80 E5 9C 9F 00 00 00 00 32 30 32 33 30 37 32 31 00 00 00 00 17 42 73 74");
    private byte[] Manufacturer;
    private byte[] ModelNumber;
    private byte[] SerialNumber;
    private byte[] ImplantedDate;
    private byte Magnet;
    private byte ImplantedPart;
    private byte[] Reserved = {0x00,0x00};
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
        if(bRetrunData!=null){
            System.arraycopy(bRetrunData, 3, Manufacturer, 0, 16);
            System.arraycopy(bRetrunData, 19, ModelNumber, 0, 16);
            System.arraycopy(bRetrunData, 35, Manufacturer, 0, 16);
            System.arraycopy(bRetrunData, 51, Manufacturer, 0, 8);
        }

    }



//    public byte[] getbRetrunData(){
//        // Read
//        if(mode==1){
//            // 自定义读取的数据值
//            // 因此我们要篡改返回的数据包的payload。
//
//            // 先找找这个值有无本地历史记录，有就直接读取
//            EncodingPacket encodingPacket = new EncodingPacket(packet, false, fileName);
//            bRetrunData = encodingPacket.getPacketData();
//
//            if(bRetrunData==null){
//                // 说明没有历史记录，直接返回默认值
//                return Constant.PATIENT_LEAD_INFO;
//            }
//
//        }
//        // Write
//        else if(mode==2){
//            PacketManager.serialize(packet, fileName);
//            EncodingPacket encodingPacket = new EncodingPacket(packet, true, fileName);
//            bRetrunData = encodingPacket.getPacketData();
//        }
//        return  bRetrunData;
//    }

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
