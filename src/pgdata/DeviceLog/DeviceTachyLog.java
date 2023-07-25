package pgdata.DeviceLog;

import DataStructure.DynamicByteBuffer;

public class DeviceTachyLog extends BaseDeviceLog{

    private byte[] packetHeader = {(byte) 0x84, 0x59, 0x11};
    private byte[] payload;
    private byte[] CRC32;
    private DynamicByteBuffer buffer;

    @Override
    public byte[] getbRetrunData() {
        return new byte[0];
    }
}
