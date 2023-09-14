package pgdata.DeviceLog;

import DataStructure.DynamicByteBuffer;
import ParseData.DataConvert;

public abstract class BaseLog {
    protected byte[] bRetrunData;
    protected byte[][] bLongRetrunData;
    protected byte[] packetHeader;
    protected byte[] payload;
    protected byte[] CRC32;

    public byte[] getbReturnData(){
        DynamicByteBuffer buffer = new DynamicByteBuffer();

        buildPayload(buffer);  // 调用子类的实现

        payload = buffer.toArray();

        byte[] parameterCRC32 = DataConvert.calculateCRC32(payload, 0, payload.length);
        buffer.put(parameterCRC32);

//        byte[] packetBody = buffer.toArray();
//        byte[] CRC32 = DataConvert.calculateCRC32(packetBody, 0, packetBody.length);
//
//        buffer.put(CRC32);
//        buffer.put(0, packetHeader);
//
//        bRetrunData = buffer.toArray();
//        return bRetrunData;

        if(packetHeader[2]!=0x0E){
            byte[] packetBody = buffer.toArray();
            byte[] CRC32 = DataConvert.calculateCRC32(packetBody, 0, packetBody.length);

            buffer.put(CRC32);

        }
        buffer.put(0, packetHeader);

        bRetrunData = buffer.toArray();
        return bRetrunData;
    }

    public byte[][] getbLongReturnData(){
        return bLongRetrunData;
    }

    public void buildPayload(DynamicByteBuffer buffer){}
}
