package pgdata.Episodes;

import Controller.RandomData;
import DataStructure.DynamicByteBuffer;
import ParseData.DataConvert;
import pgdata.DeviceLog.BaseLog;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

// ATP: 0x1x;0x2x;0x3x
// Shock: 0x0X;0x4x
public class SingleEpisode extends BaseLog {
    private boolean t = true, f = false;
    // part I : payload length = 100
    private byte[] startTime;
    private byte[] endTime;
    private byte[] episodeNumber = new byte[2];
    private byte vf_initial_duration;
    private byte vf_re_duration;
    private byte vf_post_shock_duration;
    private byte fvt_initial_duration;
    private byte fvt_re_duration;
    private byte fvt_post_shock_duration;
    private byte vt_initial_duration;
    private byte vt_re_duration;
    private byte vt_post_shock_duration;
    private byte Segments_number;
    private byte Gain_value;
    private byte Episode_type;
    private byte Shock_impedance_val;
    private byte Tachy_detect_zone;
    private byte vf_tachy_rate;
    private byte fvt_tachy_rate;
    private byte vt_tachy_rate;
    private byte EPISODE_FORMAT_VER;
    private byte[] svt_params;
    private byte[] reserved = new byte[24];
    // part of Tachy_treat in Packet1, the rest of it is in packet 2
    private byte[] tachy_treat;

    // part II:packet payload length = 100;
    private byte[] estimatedImpedance;
    private byte[] segmentsTimestamp;
    private byte[] nearSegments;
    private byte[] farSegments;
    private byte[] CRC32;
    // 最后3个补位
    private byte[] supplement = new byte[3];
    private byte[] packetHeader1 = new byte[]{0x6C, 0x2A, 0x64};
    private byte[] packetHeader2 = new byte[]{0x6C, 0x2D, 0x64};
    private int deviceMode;
    private int testCaseId;

    public SingleEpisode(int episodeIndex, int deviceMode, int testCaseId) {
        this.deviceMode = deviceMode;
        this.testCaseId = testCaseId;
        episodeNumber[0] = (byte) episodeIndex;
    }

    private List<Byte> episodeTypeList = new ArrayList<>() {{
        add((byte) 0x10);
        add((byte) 0x20);
        add((byte) 0x30);
        add((byte) 0x40);
        add((byte) 0x80);
        add((byte) 0xC0);
        add((byte) 0x00);
    }};

    @Override
    public byte[] getbReturnData() {
        return null;
    }


    public byte[][] getbLongReturnData() {
        // 创建一个ArrayList来存放byte[]
        ArrayList<byte[]> list = new ArrayList<>();
        DynamicByteBuffer buffer1 = new DynamicByteBuffer();
        DynamicByteBuffer buffer2 = new DynamicByteBuffer();
        DynamicByteBuffer payloadBuffer = new DynamicByteBuffer();

        byte[] time = RandomData.getTimePassedInSeconds();
        startTime = time;
        endTime = RandomData.addSecondsToTime(time, 20);

        if (deviceMode == 2) {

        } else if (deviceMode == 3) {
            switch (testCaseId) {
                case 0 -> {
                    time = RandomData.getTimePassedInSeconds(0, 0, 0);
                    startTime = time;
                    endTime = RandomData.addSecondsToTime(time, 20);
                }
                case 1 -> {
                    time = RandomData.getTimePassedInSeconds(1, 1, 1);
                    startTime = time;
                    endTime = RandomData.addSecondsToTime(time, 30);
                }
                case 2 -> {
                    time = RandomData.getTimePassedInSeconds(2, 2, 2);
                    startTime = time;
                    endTime = RandomData.addSecondsToTime(time, 40);
                }
                case 3 -> {
                    time = RandomData.getTimePassedInSeconds(3, 3, 3);
                    startTime = time;
                    endTime = RandomData.addSecondsToTime(time, 50);
                }
                case 4 -> {
                    time = RandomData.getTimePassedInSeconds(4, 4, 4);
                    startTime = time;
                    endTime = RandomData.addSecondsToTime(time, 121);
                }
            }

        }

        vf_initial_duration = RandomData.generateRandomByte();
        vf_re_duration = RandomData.generateRandomByte();
        vf_post_shock_duration = RandomData.generateRandomByte();
        fvt_initial_duration = RandomData.generateRandomByte();
        fvt_re_duration = RandomData.generateRandomByte();
        fvt_post_shock_duration = RandomData.generateRandomByte();
        vt_initial_duration = RandomData.generateRandomByte();
        vt_re_duration = RandomData.generateRandomByte();
        vt_post_shock_duration = RandomData.generateRandomByte();

        Segments_number = RandomData.generateRandomByte(10);
        if (deviceMode == 4) {
            Segments_number = 0x00;
        }
        Gain_value = RandomData.generateRandomByte();
        Episode_type = RandomData.getRandomIntegerFromList(episodeTypeList);
        Shock_impedance_val = RandomData.generateRandomByte();

        Tachy_detect_zone = 0x00;
        vf_tachy_rate = (byte) 0x00;
        fvt_tachy_rate = (byte) 0x90;
        vt_tachy_rate = (byte) 0xFF;

        svt_params = new byte[24];
        svt_params[6] = 0x10;
        svt_params[8] = 0x20;
        svt_params[11] = 0x01;
        svt_params[18] = 0x01;
        svt_params[20] = 0x02;
//        svt_params = RandomData.generateRandomBytes(24);

        if (deviceMode == 2) {
            switch (testCaseId) {
                case 0 -> {
                    Tachy_detect_zone = 0x00;
                    vf_tachy_rate = (byte) 0x70;
                }
                case 1 -> {
                    Tachy_detect_zone = 0x01;
                    vf_tachy_rate = (byte) 0x80;
                    fvt_tachy_rate = (byte) 0x80;
                }
                case 2 -> {
                    Tachy_detect_zone = 0x02;
                    vf_tachy_rate = (byte) 0xA0;
                    fvt_tachy_rate = (byte) 0xA0;
                    vt_tachy_rate = (byte) 0xA0;
                }
                case 3 -> {
                    svt_params = RandomData.generateRandomBytes(24);
                    Tachy_detect_zone = 0x00;
                    vf_tachy_rate = (byte) 0x00;
                }
                case 4 -> {
                    Tachy_detect_zone = 0x02;
                    svt_params = new byte[24];
                    svt_params[6] = 0x10;
                    svt_params[8] = 0x20;
                    svt_params[11] = 0x01;
                }
                case 5 -> {
                    Tachy_detect_zone = 0x02;
                    svt_params = new byte[24];
                    svt_params[6] = 0x10;
                    svt_params[8] = 0x20;
                    svt_params[11] = 0x01;
                    svt_params[18] = 0x01;
                    svt_params[20] = 0x02;
                    svt_params[23] = 0x08;
                }
                case 6 -> {
                    Tachy_detect_zone = 0x02;
                    svt_params = new byte[24];
                    svt_params[6] = 0x10;
                    svt_params[8] = 0x20;
                    svt_params[18] = 0x01;
                    svt_params[20] = 0x02;
                    svt_params[23] = 0x08;
                }
            }
        }

        EPISODE_FORMAT_VER = RandomData.generateRandomByte(0);

        tachy_treat = new byte[40];
        tachy_treat[0] = RandomData.getRandomByte((byte) 0xC, (byte) 0xF, (byte) 1, (byte) 3); // Commanded Therapy
        tachy_treat[1] = 0x10;
        // ATP: 0x1x;0x2x;0x3x;0x5X;0x6X;
        // Shock: 0x0X;0x4x;0x8X;0xCX;
        // If not Shock, then must be ATP
        estimatedImpedance = new byte[20];

        if (deviceMode == 0) {
            // default random
        } else if (deviceMode == 1) {
            Episode_type = episodeTypeList.get(testCaseId % episodeTypeList.size());
        } else if (deviceMode == 2) {
            tachy_treat = RandomData.generateTherapy(testCaseId);
        } else if (deviceMode == 4) {
            tachy_treat = new byte[40];
            switch (testCaseId) {
                case 0 -> {
                    tachy_treat[0] = RandomData.getRandomByte((byte) 0, (byte) 3, (byte) 1, (byte) 9); // VF
                }
                case 1 -> {
                    tachy_treat[0] = RandomData.getRandomByte((byte) 4, (byte) 7, (byte) 1, (byte) 9); // FVT
                }
                case 2 -> {
                    tachy_treat[0] = RandomData.getRandomByte((byte) 8, (byte) 11, (byte) 1, (byte) 9); // VT
                }
                case 3 -> {
                    tachy_treat[0] = RandomData.getRandomByte((byte) 0xC, (byte) 0xF, (byte) 1, (byte) 9); // Commanded Therapy
                }
                default -> {
                }
            }
        } else if (deviceMode == 5) {
            tachy_treat = new byte[40];
            switch (testCaseId) {
                case 0 -> {
                    tachy_treat[0] = RandomData.generateByte(1, 1, 0, 0); // Commanded Shock
                }
                case 1 -> {
                    do {
                        tachy_treat[0] = RandomData.generateByte(1, 1, -1, -1); // Commanded ATP
                    } while ((tachy_treat[0] & 0b11000000) == 0xC0);
                }
                case 2 -> {
                    do {
                        tachy_treat[0] = RandomData.generateByte(-1, -1, 0, 0); // Shock
                    } while ((tachy_treat[0] & 0b11000000) == 0b11000000);

                }
                case 3 -> {
                    do {
                        tachy_treat[0] = RandomData.generateByte(-1, -1, -1, -1); // ATP
                    } while (((tachy_treat[0] & 0b11000000) == 0b11000000) || ((tachy_treat[0] & 0b00110000) == 0));
                }
                default -> {
                    break;
                }
            }
        } else if (deviceMode == 6) {
            tachy_treat = new byte[40];
            switch (testCaseId) {
                case 0 -> {
                    tachy_treat[0] = RandomData.generateByte(-1, -1, 0, 1); // Burst
                }
                case 1 -> {
                    tachy_treat[0] = RandomData.generateByte(-1, -1, 1, 0); // Ramp
                }
                case 2 -> {
                    tachy_treat[0] = RandomData.generateByte(-1, -1, 1, 1); // Ramp+

                }
                default -> {
                    break;
                }
            }
        }else if (deviceMode == 7) {
            tachy_treat = new byte[40];
            tachy_treat[0] = (byte) (0b11000000 | testCaseId);
        }else if (deviceMode == 8) {
            tachy_treat = new byte[40];
            tachy_treat[0] = (byte) (0b11000000);
            switch (testCaseId){
                case 0->{
                    tachy_treat[1] = (byte) 0xFF;
                    estimatedImpedance[0] = (byte) 0xFF;
                    estimatedImpedance[1] = (byte) 0xFF;
                    tachy_treat[2] = 0x10; //0.06s
                }
                case 1->{
                    tachy_treat[1] = (byte) 0xFE;
                    estimatedImpedance[0] = (byte) 0xFD;
                    estimatedImpedance[1] = (byte) 0xFF;
                    tachy_treat[2] = 0x01; // 0.00s;
                }
                case 2->{
                    tachy_treat[1] = (byte) 0xFD;
                    estimatedImpedance[0] = 0x10;
                    tachy_treat[2] = 0x20; //0.13s
                }
                case 3->{
                    tachy_treat[1] = 0x10;
                    estimatedImpedance[1] = 0x01; //256/20 = 12.8
                    tachy_treat[3] = 0x01; // 1.02s
                }
                case 4->{
                    tachy_treat[1] = (byte) 0xFC; // 16.13 s
                    estimatedImpedance[0] = (byte) 0xFE;
                    estimatedImpedance[1] = (byte) 0xFF; // 3276.7 Ω
                    tachy_treat[2] = (byte) 0xFF;
                    tachy_treat[3] = (byte) 0xFF; // 262.14 s
                }
            }
        }else if (deviceMode == 9) {
            tachy_treat = new byte[40];
            tachy_treat[0] = 0b01010000;
            tachy_treat[1] = (byte) testCaseId;
        }

        buffer1.put(startTime);
        buffer1.put(endTime);
        buffer1.put(episodeNumber);
        buffer1.put(vf_initial_duration);
        buffer1.put(vf_re_duration);
        buffer1.put(vf_post_shock_duration);
        buffer1.put(fvt_initial_duration);
        buffer1.put(fvt_re_duration);
        buffer1.put(fvt_post_shock_duration);
        buffer1.put(vt_initial_duration);
        buffer1.put(vt_re_duration);
        buffer1.put(vt_post_shock_duration);
        buffer1.put(Segments_number);
        buffer1.put(Gain_value);
        buffer1.put(Episode_type);
        buffer1.put(Shock_impedance_val);
        buffer1.put(Tachy_detect_zone);
        buffer1.put(vf_tachy_rate);
        buffer1.put(fvt_tachy_rate);
        buffer1.put(vt_tachy_rate);
        buffer1.put(EPISODE_FORMAT_VER);
        buffer1.put(svt_params);
        buffer1.put(reserved);
        // packet1 has a total length of 108
        // 77, still needs 24 from tachy_treat. 102-104 bytes are supplement(00 00 00)
        // Last 4 bytes are CRC32 105-108

        // Gotta to tear Tachy_treat packet apart

        byte[] tachy_treat_part1 = new byte[24];
        byte[] tachy_treat_part2 = new byte[16];
        System.arraycopy(tachy_treat, 0, tachy_treat_part1, 0, tachy_treat_part1.length);
        System.arraycopy(tachy_treat, 24, tachy_treat_part2, 0, tachy_treat_part2.length);

        buffer1.put(tachy_treat_part1);
        // payloadCRC32 calculation does not include supplement!!!!
        payloadBuffer.put(buffer1.toArray());

        buffer1.put(supplement);
        buffer1.put(0, (byte) 0x00);

        DynamicByteBuffer packetBuffer1 = new DynamicByteBuffer();
        packetBuffer1.put(packetHeader1);
        packetBuffer1.put(DataConvert.addCRC32(buffer1.toArray()));

        list.add(packetBuffer1.toArray());
        // End of Constructing packet1


//        segmentsTimestamp = RandomData.generateRandomBytes(20);
        segmentsTimestamp = new byte[20];
        nearSegments = new byte[20];
        farSegments = new byte[20];

        buffer2.put(tachy_treat_part2); // 17
        buffer2.put(estimatedImpedance); // 37
        buffer2.put(segmentsTimestamp); //57
        buffer2.put(nearSegments); // 77
        buffer2.put(farSegments); //97
        payloadBuffer.put(buffer2.toArray());
        buffer2.put(0, (byte) 0x01); // 1

        byte[] payloadCRC32 = DataConvert.calculateCRC32(payloadBuffer.toArray(), 0, payloadBuffer.toArray().length);
        buffer2.put(payloadCRC32); //101
        buffer2.put(supplement); //104
        byte[] packetBodyWithCRC = DataConvert.addCRC32(buffer2.toArray()); // 108

        DynamicByteBuffer packetBuffer2 = new DynamicByteBuffer();
        packetBuffer2.put(packetHeader2);
        packetBuffer2.put(packetBodyWithCRC);

        list.add(packetBuffer2.toArray());
        // End of Constructing packet2

        byte[][] array = new byte[list.size()][];
        for (int i = 0; i < list.size(); i++) {
            array[i] = list.get(i);
        }

        return array;
    }
}
