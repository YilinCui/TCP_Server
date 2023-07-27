package pgdata.Episodes;

import Controller.RandomData;
import DataStructure.DynamicByteBuffer;
import ParseData.DataConvert;
import pgdata.DeviceLog.BaseData;

import java.util.ArrayList;

public class SingleEpisode extends BaseData {
    // part I : payload length = 100
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
    private byte[] packetHeader1 = new byte[]{0x6C, 0x2A, 0x64};
    private byte[] packetHeader2 = new byte[]{0x6C, 0x2D, 0x64};

    public SingleEpisode(byte[] episodeNumber) {
        this.episodeNumber = new byte[2];
        System.arraycopy(episodeNumber, 0, this.episodeNumber, 0, 2);

    }

    @Override
    public byte[] getbReturnData() {
        return null;
    }

    public byte[][] getbLongReturnData() {
        // 创建一个ArrayList来存放byte[]
        ArrayList<byte[]> list = new ArrayList<>();
        DynamicByteBuffer buffer1 = new DynamicByteBuffer();
        DynamicByteBuffer buffer2 = new DynamicByteBuffer();

        startTime = RandomData.generateRandomBytes(4);
        endTime = RandomData.generateRandomBytes(4);
        //episodeNumber = RandomData.generateRandomBytes(4);
        tachyTreat = RandomData.generateRandomBytes(40);
        treatmentDeliveredCounter = RandomData.generateRandomBytes(10);
        estimatedImpedance = RandomData.generateRandomBytes(20);

        buffer1.put((byte) 0x00);
        buffer1.put(startTime);
        buffer1.put(endTime);
        buffer1.put(episodeNumber);
        buffer1.put(tachyTreat);
        buffer1.put(treatmentDeliveredCounter);
        buffer1.put(estimatedImpedance);

        byte[] payload1 = buffer1.toArray();
        byte[] payload1CRC32 = DataConvert.calculateCRC32(payload1, 0, payload1.length);
        buffer1.put(payload1CRC32);
        buffer1.put(0, packetHeader1);

        byte[] packet1 = buffer1.toArray();
        list.add(packet1);

        segmentsNumber = 0x00;
        gainValue = RandomData.generateRandomByte();
        EpisodeType = RandomData.generateRandomByte();
        shockImpedanceValue = RandomData.generateRandomByte();
        tachyDetectZone = RandomData.generateRandomByte();
        vfTachyRate = RandomData.generateRandomByte();
        fvtTachyRate = RandomData.generateRandomByte();
        vtTachyRate = RandomData.generateRandomByte();

        segmentsTimestamp = RandomData.generateRandomBytes(20);
        nearSegments = RandomData.generateRandomBytes(20);
        farSegments = RandomData.generateRandomBytes(20);

        buffer2.put((byte) 0x01);
        buffer2.put(segmentsNumber);
        buffer2.put(gainValue);
        buffer2.put(EpisodeType);
        buffer2.put(shockImpedanceValue);
        buffer2.put(tachyDetectZone);
        buffer2.put(vfTachyRate);
        buffer2.put(fvtTachyRate);
        buffer2.put(vtTachyRate);
        buffer2.put(segmentsTimestamp);
        buffer2.put(nearSegments);
        buffer2.put(farSegments);
        buffer2.put(reserved1);
        buffer2.put(reserved2);

        byte[] payload2 = buffer2.toArray();
        byte[] payload2CRC32 = DataConvert.calculateCRC32(payload2, 0, payload2.length);
        buffer2.put(payload2CRC32);

        byte[] packet2Body = buffer2.toArray();
        byte[] CRC32 = DataConvert.calculateCRC32(packet2Body, 0, packet2Body.length);
        buffer2.put(CRC32);
        buffer2.put(0, packetHeader2);

        byte[] packet2 = buffer2.toArray();
        list.add(packet2);

        byte[][] array = new byte[list.size()][];
        for (int i = 0; i < list.size(); i++) {
            array[i] = list.get(i);
        }

        return array;
    }
}
