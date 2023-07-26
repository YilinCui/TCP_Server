package pgdata.Episodes;

import Controller.RandomData;
import pgdata.DeviceLog.BaseData;

public class SingleEpisode extends BaseData {
    // part I : payload length = 81
    private byte[] startTime;
    private byte[] endTime;
    private byte[] episodeNumber;
    private byte[] tachyTreat;
    private byte[] treatmentDeliveredCounter;
    private byte[] estimatedImpedance;

    // part II
    private byte segmentsNumber;
    private byte gainValue;
    private byte EpisodeType;
    private byte shockImpedanceValue;
    private byte tachyDetectZone;
    private byte vfTachyRate;
    private byte fvtTachyRate;
    private byte vtTachyRate;
    private byte[] segmentsTimestamp;
    private byte[] nearSegments;
    private byte[] farSegments;
    private byte[] reserved1 = new byte[4];
    private byte[] reserved2 = new byte[4];
    private byte[] EpisodeCRC32;
    private byte[] CRC32;

    @Override
    public byte[] getbReturnData() {
        return null;
    }

    public byte[] getbReturnData(byte segment){
        if(segment==0x00){
            startTime = RandomData.generateRandomBytes(4);
            endTime = RandomData.generateRandomBytes(4);
            episodeNumber = RandomData.generateRandomBytes(4);
            tachyTreat = RandomData.generateRandomBytes(40);
            treatmentDeliveredCounter = RandomData.generateRandomBytes(10);
            estimatedImpedance = RandomData.generateRandomBytes(20);

        }
        return bRetrunData;
    }
}
