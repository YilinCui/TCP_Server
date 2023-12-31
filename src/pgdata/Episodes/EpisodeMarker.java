package pgdata.Episodes;

import Controller.RandomData;
import DataStructure.DynamicByteBuffer;
import ParseData.DataConvert;
import pgdata.DeviceLog.BaseLog;

import java.util.ArrayList;

public class EpisodeMarker extends BaseLog {
    private byte markerIndex = 0x00;
    private byte[] markerData;

    public EpisodeMarker(){
        packetHeader = new byte[]{(byte) 0x2B, 0x56, 0x67};
    }

    @Override
    public byte[] getbReturnData() {
        return new byte[0];
    }

    public byte[][] getbLongReturnData(){
        ArrayList<byte[]> list = new ArrayList<>();
        for(int i=0;i<5;i++){
            DynamicByteBuffer buffer = new DynamicByteBuffer();
            buffer.put(markerIndex++);
            //markerData = RandomData.generateRandomBytes(32);
            markerData = new byte[32];
            buffer.put(markerData);
            byte[] supplement = new byte[3];
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
