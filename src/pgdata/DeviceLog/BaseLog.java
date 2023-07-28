package pgdata.DeviceLog;

import DataStructure.DynamicByteBuffer;

public abstract class BaseLog {
    protected byte[] bRetrunData;
    protected byte[][] bLongRetrunData;
    protected byte[] packetHeader;
    protected byte[] payload;
    protected byte[] CRC32;

    public byte[] getbReturnData(){
        return new byte[0];
    }

    public byte[][] getbLongReturnData(){
        return bLongRetrunData;
    }
}
