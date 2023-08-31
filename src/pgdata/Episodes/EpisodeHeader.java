package pgdata.Episodes;

import Controller.RandomData;
import DataStructure.DynamicByteBuffer;
import ParseData.DataConvert;
import pgdata.DeviceLog.BaseLog;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class EpisodeHeader extends BaseLog {
    private byte maxEpisodeCount = 0x20;
    private byte[] EpisodeHeader = new byte[5];
    private byte latestEpisode;
    private byte[] EpisodeCount;
    private byte[] supplement = new byte[3];
    private byte[] CRC32;
    private int episodesNum;
    private int testCaseId;
    private int deviceMode;
    private List<byte[][]> episodeList = new ArrayList<>();

    public EpisodeHeader(int deviceMode, int testCaseId){
        initialEpisode(deviceMode, testCaseId);
    }

    private void initialEpisode(int deviceMode, int testCaseId){
        this.testCaseId = testCaseId;
        this.deviceMode = deviceMode;
        //episodesNum = RandomData.getRandomNumberInRange(0,32);
        episodesNum = 0x0A;
        packetHeader = new byte[]{0x14, 0x07, 0x0B};

        setEpisodeHeader(episodesNum);
        creatEpisode(episodesNum);

        latestEpisode = 0x00;
        EpisodeCount = new byte[]{(byte) episodesNum,0x00};

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
    }

    // 设置Episode的数量
    public void setEpisodeHeader(int episodes) {
        if (episodes < 0 || episodes > 32) {
            throw new IllegalArgumentException("Episodes must be between 0 and 32");
        }

        // 清除旧数据
        for (int i = 0; i < EpisodeHeader.length; i++) {
            EpisodeHeader[i] = 0;
        }

        // 设置新的episode数量
        for (int i = 0; i < episodes; i++) {
            // 定位到正确的byte和bit位置
            int byteIndex = i / 8;
            int bitIndex = i % 8;

            // 设置对应的bit为1
            EpisodeHeader[byteIndex] |= (1 << bitIndex);
        }
    }

    private void creatEpisode(int episodesNum){
        for(int i=0;i<episodesNum;i++){
            SingleEpisode episode = new SingleEpisode(i,deviceMode,testCaseId);
            episodeList.add(episode.getbLongReturnData());
        }
    }

    public List<byte[][]> getEpisodeList(){
        return episodeList;
    }

    public byte[] getbReturnData(){
        return bRetrunData;
    }
}
