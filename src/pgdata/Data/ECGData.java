package pgdata.Data;

import Controller.RandomData;
import ParseData.DataConvert;

public class ECGData{
    // Packet length is : 26/30/34
    private static int sequence = 0;
    private byte[] data;
    private byte[] deviceStatus = new byte[4];
    private int deviceMode = 0;
    public ECGData(){
        data = RandomData.generateRandomBytes(34);
    }

    public ECGData(int deviceMode){
        this();
        this.deviceMode = deviceMode;
    }

    public byte[] getECG(){

        data[25] = (byte)sequence++;
        if(sequence%40==0){
            data[24] = (byte) 0xFF; // Device Status on
        }else{
            data[24] = (byte) 0xEF; // Device Status off
        }
//        byte[] deviceStatus = DataConvert.intToByteArray(0x00000400,4);
        deviceStatus = RandomData.generateRandomBytes(4);

        System.arraycopy(deviceStatus,0,data,26,4);
        return data;
    }

    public String getStatus(){
        if(data[24] == (byte) 0xFF){
            return DataConvert.bytesToHex(deviceStatus);
        }
        return null;
    }
}
