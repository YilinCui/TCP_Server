package pgdata.DeviceLog;

import DataStructure.DynamicByteBuffer;
import ParseData.DecodingPacket;
import ParseData.EncodingPacket;
import ParseData.PacketManager;

import java.util.Random;

public abstract class BaseDeviceLog {
    protected byte[] bRetrunData;
    protected byte[] packetHeader;
    protected byte[] payload;
    protected byte[] CRC32;
    protected DynamicByteBuffer buffer;

    public abstract byte[] getbRetrunData();

}
