package pgdata.Episodes;

import Controller.RandomData;
import DataStructure.DynamicByteBuffer;
import ParseData.DataConvert;
import pgdata.DeviceLog.BaseLog;
import java.util.Random;
import java.util.ArrayList;
import java.util.Arrays;

public class EpisodeSegment extends BaseLog implements WaveGenerator{
    private byte segmentIndex = 0x00;
    private byte[] segmentData;

    public EpisodeSegment(){
        packetHeader = new byte[]{(byte) 0x84, 0x30, 0x65};
    }

    @Override
    public byte[] getbReturnData() {
        return new byte[0];
    }

    public byte[][] getbLongReturnData(){
        ArrayList<byte[]> list = new ArrayList<>();
        Random random = new Random();
// 随机选择波形
        int waveType = random.nextInt(4);
        switch (waveType) {
            case 0:
                segmentData = WaveGenerator.generateSinWave();
                break;
            case 1:
                segmentData = WaveGenerator.generateSquareWave();
                break;
            case 2:
                segmentData = WaveGenerator.generateTriangleWave();
                break;
            case 3:
                segmentData = WaveGenerator.generateSawtoothWave();
                break;
        }
        for(int i=0;i<5;i++){
            DynamicByteBuffer buffer = new DynamicByteBuffer();
            buffer.put(segmentIndex++);
           // segmentData = RandomData.generateRandomBytes(125);
            Arrays.fill(segmentData, (byte) 0x7F);
            buffer.put(segmentData);
            byte[] supplement = new byte[2];
            buffer.put(supplement);
            byte[] CRC32 = DataConvert.calculateCRC32(buffer.toArray(),0,buffer.toArray().length);
            buffer.put(CRC32);
            buffer.put(0, packetHeader);

            list.add(buffer.toArray());
        }

        byte[][] array = new byte[list.size()][];
        for (int i = 0; i < list.size(); i++) {
            array[i] = list.get(i);
        }

        return array;
    }
}
