package pgdata.Episodes;

import Controller.RandomData;
import DataStructure.DynamicByteBuffer;
import ParseData.DataConvert;
import pgdata.DeviceLog.BaseLog;

public class TherapyOverview extends BaseLog {
    private int deviceMode;
    private int testCaseId;

    private byte[] lifeChargeDuration;
    private byte[] ATP_VT_life_counts;
    private byte[] ATP_FVT_life_counts;
    private byte[] M3_life_duration;
    private byte[] shock_VT_life_counts;
    private byte[] shock_FVT_life_counts;
    private byte[] shock_VF_life_counts;
    private byte[] charge_life_counts;
    private byte[] tachy_svt_life_counts;
    private byte[] tachy_ns_life_counts;
    private byte[] tachy_vt_life_counts;
    private byte[] tachy_fvt_life_counts;
    private byte[] tachy_vf_life_counts;
    private byte[] commanded_episode_life_counts;
    private byte[] shock_episode_life_counts;
    private byte[] shock_aborted_episode_life_counts;
    private byte[] atp_episode_life_counts;
    private byte[] reserved1;
    private byte[] BLE_life_duration;
    private byte[] reserved2;
    private byte[] session_charge_duration;
    private byte[] ATP_VT_session_counts;
    private byte[] ATP_FVT_session_counts;
    private byte[] M3_session_duration;
    private byte[] shock_VT_session_counts;
    private byte[] shock_FVT_session_counts;
    private byte[] shock_VF_session_counts;
    private byte[] charge_session_counts;
    private byte[] tachy_svt_session_counts;
    private byte[] tachy_ns_session_counts;
    private byte[] tachy_vt_session_counts;
    private byte[] tachy_fvt_session_counts;
    private byte[] tachy_vf_session_counts;
    private byte[] commanded_episode_session_counts;
    private byte[] shock_episode_session_counts;
    private byte[] shock_aborted_episode_session_counts;
    private byte[] atp_episode_session_counts;
    private byte[] reserved3;
    private byte[] BLE_session_duration;
    private byte[] reserved4;
    private byte[] last_session_timeStamp;
    private byte[] reserved5;
    public TherapyOverview(int deviceMode, int testCaseId){
        this.deviceMode = deviceMode;
        this.testCaseId = testCaseId;
        packetHeader = new byte[]{(byte) 0x84, 0x39, 0x2B};
        initialOverview();
    }
    private void initialOverview(){
        lifeChargeDuration = RandomData.generateRandomBytes(4);
        ATP_VT_life_counts = RandomData.generateRandomBytes(2);
        ATP_FVT_life_counts = RandomData.generateRandomBytes(2);
        M3_life_duration = RandomData.generateRandomBytes(4);
        shock_VT_life_counts = RandomData.generateRandomBytes(2);
        shock_FVT_life_counts = RandomData.generateRandomBytes(2);
        shock_VF_life_counts = RandomData.generateRandomBytes(2);
        charge_life_counts = RandomData.generateRandomBytes(2);
        tachy_svt_life_counts = RandomData.generateRandomBytes(2);
        tachy_ns_life_counts = RandomData.generateRandomBytes(2);
        tachy_vt_life_counts = RandomData.generateRandomBytes(2);
        tachy_fvt_life_counts = RandomData.generateRandomBytes(2);
        tachy_vf_life_counts = RandomData.generateRandomBytes(2);
        commanded_episode_life_counts = RandomData.generateRandomBytes(2);
        shock_episode_life_counts = RandomData.generateRandomBytes(2);
        shock_aborted_episode_life_counts = RandomData.generateRandomBytes(2);
        atp_episode_life_counts = RandomData.generateRandomBytes(2);
        reserved1 = new byte[2];
        BLE_life_duration = RandomData.generateRandomBytes(4);
        reserved2 = new byte[12];
        session_charge_duration  = RandomData.generateRandomBytes(4);
        ATP_VT_session_counts = RandomData.generateRandomBytes(2);
        ATP_FVT_session_counts = RandomData.generateRandomBytes(2);
        M3_session_duration = RandomData.generateRandomBytes(4);
        shock_VT_session_counts = RandomData.generateRandomBytes(2);
        shock_FVT_session_counts = RandomData.generateRandomBytes(2);
        shock_VF_session_counts = RandomData.generateRandomBytes(2);
        charge_session_counts = RandomData.generateRandomBytes(2);
        tachy_svt_session_counts = RandomData.generateRandomBytes(2);
        tachy_ns_session_counts = RandomData.generateRandomBytes(2);
        tachy_vt_session_counts = RandomData.generateRandomBytes(2);
        tachy_fvt_session_counts = RandomData.generateRandomBytes(2);
        tachy_vf_session_counts = RandomData.generateRandomBytes(2);
        commanded_episode_session_counts = RandomData.generateRandomBytes(2);
        shock_episode_session_counts = RandomData.generateRandomBytes(2);
        shock_aborted_episode_session_counts = RandomData.generateRandomBytes(2);
        atp_episode_session_counts = RandomData.generateRandomBytes(2);
        reserved3 = new byte[2];
        BLE_session_duration = RandomData.generateRandomBytes(4);
        reserved4 = new byte[12];
        last_session_timeStamp  = RandomData.generateRandomBytes(4);
        reserved5 = new byte[8];
        if(deviceMode==0){
            lifeChargeDuration = RandomData.generateByteArray(4, testCaseId*1000);
            ATP_VT_life_counts = RandomData.generateByteArray(2, testCaseId*1000);
            ATP_FVT_life_counts = RandomData.generateByteArray(2, testCaseId*1000);
            M3_life_duration = RandomData.generateByteArray(4, testCaseId*1000);
            shock_VT_life_counts = RandomData.generateByteArray(2, testCaseId*1000);
            shock_FVT_life_counts = RandomData.generateByteArray(2, testCaseId*1000);
            shock_VF_life_counts = RandomData.generateByteArray(2, testCaseId*1000);
            charge_life_counts = RandomData.generateByteArray(2, testCaseId*1000);
            tachy_svt_life_counts = RandomData.generateByteArray(2, testCaseId*1000);
            tachy_ns_life_counts = RandomData.generateByteArray(2, testCaseId*1000);
            tachy_vt_life_counts = RandomData.generateByteArray(2, testCaseId*1000);
            tachy_fvt_life_counts = RandomData.generateByteArray(2, testCaseId*1000);
            tachy_vf_life_counts = RandomData.generateByteArray(2, testCaseId*1000);
            commanded_episode_life_counts = RandomData.generateByteArray(2, testCaseId*1000);
            shock_episode_life_counts = RandomData.generateByteArray(2, testCaseId*1000);
            shock_aborted_episode_life_counts = RandomData.generateByteArray(2, testCaseId*1000);
            atp_episode_life_counts = RandomData.generateByteArray(2, testCaseId*1000);
            reserved1 = new byte[2];
            BLE_life_duration = RandomData.generateByteArray(4, testCaseId*1000);
            reserved2 = new byte[12];
            session_charge_duration  = RandomData.generateByteArray(4, testCaseId*1000);
            ATP_VT_session_counts = RandomData.generateByteArray(2, testCaseId*1000);
            ATP_FVT_session_counts = RandomData.generateByteArray(2, testCaseId*1000);
            M3_session_duration = RandomData.generateByteArray(4, testCaseId*1000);
            shock_VT_session_counts = RandomData.generateByteArray(2, testCaseId*1000);
            shock_FVT_session_counts = RandomData.generateByteArray(2, testCaseId*1000);
            shock_VF_session_counts = RandomData.generateByteArray(2, testCaseId*1000);
            charge_session_counts = RandomData.generateByteArray(2, testCaseId*1000);
            tachy_svt_session_counts = RandomData.generateByteArray(2, testCaseId*1000);
            tachy_ns_session_counts = RandomData.generateByteArray(2, testCaseId*1000);
            tachy_vt_session_counts = RandomData.generateByteArray(2, testCaseId*1000);
            tachy_fvt_session_counts = RandomData.generateByteArray(2, testCaseId*1000);
            tachy_vf_session_counts = RandomData.generateByteArray(2, testCaseId*1000);
            commanded_episode_session_counts = RandomData.generateByteArray(2, testCaseId*1000);
            shock_episode_session_counts = RandomData.generateByteArray(2, testCaseId*1000);
            shock_aborted_episode_session_counts = RandomData.generateByteArray(2, testCaseId*1000);
            atp_episode_session_counts = RandomData.generateByteArray(2, testCaseId*1000);
            reserved3 = new byte[2];
            BLE_session_duration = RandomData.generateByteArray(4, testCaseId*1000);
            reserved4 = new byte[12];
            last_session_timeStamp  = RandomData.getTimePassedInSeconds(testCaseId,testCaseId,testCaseId);
            reserved5 = new byte[8];
        }
    }

    public void buildPayload(DynamicByteBuffer buffer){
        buffer.put(lifeChargeDuration);
        buffer.put(ATP_VT_life_counts);
        buffer.put(ATP_FVT_life_counts);
        buffer.put(M3_life_duration);
        buffer.put(shock_VT_life_counts);
        buffer.put(shock_FVT_life_counts);
        buffer.put(shock_VF_life_counts);
        buffer.put(charge_life_counts);
        buffer.put(tachy_svt_life_counts);
        buffer.put(tachy_ns_life_counts);
        buffer.put(tachy_vt_life_counts);
        buffer.put(tachy_fvt_life_counts);
        buffer.put(tachy_vf_life_counts);
        buffer.put(commanded_episode_life_counts);
        buffer.put(shock_episode_life_counts);
        buffer.put(shock_aborted_episode_life_counts);
        buffer.put(atp_episode_life_counts);
        buffer.put(reserved1);
        buffer.put(BLE_life_duration);
        buffer.put(reserved2);
        buffer.put(session_charge_duration);
        buffer.put(ATP_VT_session_counts);
        buffer.put(ATP_FVT_session_counts);
        buffer.put(M3_session_duration);
        buffer.put(shock_VT_session_counts);
        buffer.put(shock_FVT_session_counts);
        buffer.put(shock_VF_session_counts);
        buffer.put(charge_session_counts);
        buffer.put(tachy_svt_session_counts);
        buffer.put(tachy_ns_session_counts);
        buffer.put(tachy_vt_session_counts);
        buffer.put(tachy_fvt_session_counts);
        buffer.put(tachy_vf_session_counts);
        buffer.put(commanded_episode_session_counts);
        buffer.put(shock_episode_session_counts);
        buffer.put(shock_aborted_episode_session_counts);
        buffer.put(atp_episode_session_counts);
        buffer.put(reserved3);
        buffer.put(BLE_session_duration);
        buffer.put(reserved4);
        buffer.put(last_session_timeStamp);
        buffer.put(reserved5);
    }
}
