package pgdata;

import Constant.Constant;
import ParseData.DecodingPacket;
import ParseData.EncodingPacket;
import ParseData.PacketManager;

/**
 * X Parameter CRC32 check.
 */
public class ClinicianNote extends BaseFakeDataGenerator{

    public ClinicianNote(String folderName){
        fileName = folderName + Constant.CLINICIAN_NOTE;
        initialize();
    }


    public void process(DecodingPacket packet){
        super.process(packet);

    }

    public byte[] getbRetrunData(){
        // Read


        if(bRetrunData==null){
            return Constant.READ_CLINICIAN_NOTE;
        }
        return  bRetrunData;
    }

}
