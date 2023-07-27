package pgdata.Episodes;

import Controller.RandomData;
import DataStructure.DynamicByteBuffer;
import ParseData.DataConvert;
import pgdata.DeviceLog.BaseData;

public class EpisodeHeader extends BaseData {
    private byte maxEpisodeCount = 0x20;
    private byte[] EpisodeHeader;
    private byte latestEpisode;
    private byte[] EpisodeCount;
    private byte[] supplement = new byte[3];
    private byte[] CRC32;

    public EpisodeHeader(){
        packetHeader = new byte[]{0x14, 0x07, 0x0B};
    }


    public byte[] getbReturnData(){
        EpisodeHeader = RandomData.generateRandomBytes(5);
        latestEpisode = RandomData.generateRandomByte();
        EpisodeCount = new byte[]{0x20,0x00};

        DynamicByteBuffer buffer = new DynamicByteBuffer();
        buffer.put(EpisodeHeader);
        buffer.put(latestEpisode);
        buffer.put(EpisodeCount);

        byte[] payload = buffer.toArray();
        byte[] payloadCRC32 = DataConvert.calculateCRC32(payload, 0, payload.length);

        buffer.put(0, maxEpisodeCount);
        buffer.put(payloadCRC32);
        buffer.put(supplement);

        byte[] packetBody = buffer.toArray();
        byte[] CRC32 = DataConvert.calculateCRC32(packetBody, 0, packetBody.length);

        buffer.put(CRC32);
        buffer.put(0,packetHeader);

        bRetrunData = buffer.toArray();
        return bRetrunData;
    }
}
