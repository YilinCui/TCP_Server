package pgdata.Data;

import Controller.RandomData;
import DataStructure.DynamicByteBuffer;
import ParseData.DataConvert;

import java.util.Arrays;

public class ECGData{
    // Packet length is : 26/30/34
    private static int sequence = 0;
    private byte[] data;
    private byte[] deviceStatus = new byte[4];
    private int deviceMode = 2;
    public ECGData(){
        switch (deviceMode){
            case 0->{
                data = RandomData.generateRandomBytes(26);
            }
            case 1->{
                data = RandomData.generateRandomBytes(30);
            }
            case 2->{
                data = RandomData.generateRandomBytes(34);
            }
            case 3->{
                data = RandomData.generateRandomBytes(40);
            }
        }

    }

    public ECGData(int deviceMode){
        this();
        this.deviceMode = deviceMode;
    }

    public byte[] getECG(){
        data[25] = (byte)sequence++;
        if(Integer.MAX_VALUE-sequence<10000){
            sequence = 0;
        }
        data[24] = (byte)0x00; // no marker
        switch (deviceMode){
            case 0->{
                int base = sequence%40;
                if((sequence/40)%2==0){
                    base = 40-sequence%40;
                }
                byte[] mockData = RandomData.generateByteArray(2, base*20);

                for (int i=0;i<12;i++){
                    System.arraycopy(mockData,0,data,i*2,2);
                }
            }
            case 1->{
            }
            case 2->{
                int base = sequence%40;
                if((sequence/40)%2==0){
                    base = 40-sequence%40;
                }
                byte[] mockData = RandomData.generateByteArray(2, base*20);

                for (int i=0;i<12;i++){
                    System.arraycopy(mockData,0,data,i*2,2);
                }

                byte[] timeStamp = RandomData.getCurrentTimeInSeconds();
                System.arraycopy(timeStamp,0,data,30,4);
            }
            case 3->{

            }
        }
        byte header = 0x23;
        DynamicByteBuffer buffer = new DynamicByteBuffer();
        buffer.put(header);
        buffer.put(data);

        return buffer.toArray();
    }

    public String getStatus(){
        if(data[24] == (byte) 0xFF){
            return DataConvert.bytesToHex(deviceStatus);
        }
        return null;
    }
}
