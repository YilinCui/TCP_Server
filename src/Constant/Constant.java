package Constant;

import ParseData.DataConvert;

/**
 * Constant/Default Values
 * I used to convert Hex String to byte[], and assign the byte array to Constant directly
 * However, I used GPT for the data transition, which often came with data loss.
 * Thus, I am currently using Hex->Byte[] conversion method.
 */
public class Constant {
    public static final String BRADY_PARAMETER_POSTSHOCK = "BradyParameterPostShock.per";
    public static final String BRADY_PARAMETER_NORM = "BradyParameterNormal.per";
    public static final String TACHY_SVT_DETECTION = "TachySVTDetection.per";
    public static final String TACHY_THERAPY_PARAMETER = "TachyTherapyParameter.per";
    public static final String TACHY_MODE_PARAMETER = "TachyModeParameter.per";
    public static final String TACHY_LOG = "TachyLog.per";
    public static final String TACHY_MODE_ONOFF = "TachyModeOnOff.per";
    public static final String LEAD_INFO = "LeadInfo.per";
    public static final String CLINICIAN_NOTE = "ClinicianNote.per";
    public static final String PATIENT_INFO1 = "PatientInfo1.per";
    public static final String PATIENT_INFO2 = "PatientInfo2.per";
    public static final byte[] FIRMWARE_VERSION = new byte[]{
            0x1C, 0x48, 0x27, 0x01, 0x00, 0x12, 0x01, 0x12, 0x00, 0x12, 0x00, 0x12, 0x00,
            0x20, 0x20, 0x20, 0x20, 0x31, 0x2E, 0x30, 0x2E, 0x31, 0x39, 0x1A, 0x2B, 0x3C,
            0x03, 0x5E, (byte) 0xA0, (byte) 0xCA, (byte) 0xEF
    };
    public static final byte[] DEVICE_SERIAL_NUMBER = new byte[]{
            0x34, 0x52, 0x0D, 0x17, 0x03, 0x01, 0x03, 0x00, 0x00, 0x00, 0x00, 0x35, 0x44,
            0x32, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,
            0x00, 0x30, 0x35, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,
            0x00, 0x00, 0x00, 0x00, 0x01, 0x01, 0x00, 0x00, (byte) 0xA9, (byte) 0xBD, (byte) 0xEE, 0x47, 0x1C,
            (byte) 0xDF, 0x44, 0x21
    };
    public static final byte[] PATIENT_LEAD_INFO = DataConvert.hexStringToByteArray("83 4A 0C E5 B4 94 E7 9A 84 E9 BB 91 E5 BF 83 E5 8E 82 00 31 31 34 35 31 34 00 00 00 00 00 00 00 00 00 00 E6 9E 81 E4 B9 90 E5 87 80 E5 9C 9F 00 00 00 00 32 30 32 33 30 37 32 31 00 00 00 00 E5 B4 94 E7 9A 84 E9 BB 91 E5 BF 83 E5 8E 82 00 31 31 34 35 31 34 00 00 00 00 00 00 00 00 00 00 E6 9E 81 E4 B9 90 E5 87 80 E5 9C 9F 00 00 00 00 32 30 32 33 30 37 32 31 00 00 00 00 17 42 73 74 1C DF 44 21");
    public static final byte[] READ_PATIENT_INFO1 = DataConvert.hexStringToByteArray("7B 2F 69 00 E5 81 A5 E5 BA B7 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 77 68 69 74 65 20 68 6F 75 73 65 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 31 39 39 38 30 35 31 36 32 30 30 30 30 31 30 31 00 00 00 F4 89 63 9E");
    public static final byte[] READ_PATIENT_INFO2 = DataConvert.hexStringToByteArray("7B 30 69 01 E5 B4 94 E9 80 B8 E6 9E 97 00 00 00 00 00 00 00 39 31 31 00 00 00 00 00 00 00 00 00 00 00 00 00 74 72 75 6D 70 00 00 00 00 00 00 00 00 00 00 00 31 31 30 00 00 00 00 00 00 00 00 00 00 00 00 00 E7 99 BD E5 AE AB 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 BF 00 5A 00 00 00 00 00 00 00 55 B9 AB B4 00 00 00 2C 65 E2 03");
    public static final byte[] READ_CLINICIAN_NOTE = DataConvert.hexStringToByteArray("7F C6 0F 54 68 69 73 20 69 73 20 64 65 66 61 75 6C 74 20 6E 6F 74 65 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 93 34 33 29");
    public static final byte[] READ_ALERT_PARAMETER = DataConvert.hexStringToByteArray("1C 56 02 FA 00 C4 09 1E 00 96 00 28 0A 0A 00 00 00 00 00 00 00 00 00 DB D0 1D B2 1C DF 44 21");
    public static final byte[] SET_MAGNET_MODE = DataConvert.hexStringToByteArray("08 59 6E 01 00 00 00 79 B8 F8 99");
    public static final byte[] READ_GLOBAL_CONSTANTS = DataConvert.hexStringToByteArray("84 5D 24 7D 07 00 00 E6 6A 31 45 A4 01 FC 03 DC 05 9E 07 6D 0B 3C 0F 00 00 00 00 40 50 0B 10 07 00 00 00 A7 41 05 11 0A 2E 0B 0C 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 46 BC FC D8 1C DF 44 21");
    public static final byte[] SAFTY_CORE_PARAMETERS = DataConvert.hexStringToByteArray("50 63 87 03 01 08 22 03 00 00 00 00 00 C8 00 96 00 00 00 00 00 00 00 07 02 00 00 BD 0B 68 E9 03 03 0A 27 0E 00 03 00 00 00 C8 00 96 00 00 00 00 00 00 00 07 02 00 00 3E 4D 5A FB 0C 8E 01 00 0C 8E 01 00 B4 00 00 00 00 00 00 00 0F 74 43 7A 1C DF 44 21");
    public static final byte[] READ_DAILY_MEASUREMENT_HEADER = DataConvert.hexStringToByteArray("08 04 0E 16 00 00 00 5F D7 36 54");
    public static final byte[] READ_EPESODE_HEADER = DataConvert.hexStringToByteArray("14 07 0B 20 FF FF FF FF 00 1C 79 03 EE 36 37 8D 00 00 00 B1 EC 3B B1");
    public static final byte[] READ_SINGLE_EPISODE_PART1 = DataConvert.hexStringToByteArray("6C 3C 64 00 50 7A D2 04 6D 7A D2 04 00 00 00 00 00 03 03 00 03 03 00 07 04 30 01 02 C8 AA 8C 01 00 00 00 00 00 00 00 00 00 02 02 00 00 00 00 00 00 00 00 00 00 02 02 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 90 27 82 06 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 D7 E1 00 B5");
    public static final byte[] READ_SINGLE_EPISODE_PART2 = DataConvert.hexStringToByteArray("6C 3F 64 01 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 73 13 55 18 F5 19 D7 1E B9 23 C8 2A AA 2F 00 00 00 00 00 00 00 00 01 00 02 00 03 00 04 00 05 00 06 00 00 00 00 00 00 00 0A 00 0B 00 0C 00 0D 00 0E 00 0F 00 10 00 00 00 00 00 00 00 41 2B B5 FC 00 00 00 63 0D 8D 1D");
    public static final byte[] READ_MOST_RECENT_MEASUREMENT = DataConvert.hexStringToByteArray("48 36 2A 05 00 00 00 BA 8C C2 04 1C 10 FF 0F FF 0F 00 00 B6 0A 3C 01 EE FE EE FE FF 7F 00 00 C0 9B 1E 05 C0 9B 1E 05 C7 96 C2 04 C7 96 C2 04 B2 75 1E 05 1B 99 1E 05 B2 75 1E 05 B4 01 00 00 04 00 00 00 A4 9C B6 9F 1C DF 44 21");
    public static final byte[] READ_GLOBAL_COUNTERS = DataConvert.hexStringToByteArray("84 39 2B 7A 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 16 00 18 00 00 00 6D 03 00 00 00 00 0C 00 00 00 0C 00 01 00 00 00 00 00 D8 8F 01 00 00 00 00 00 00 00 00 00 00 00 00 00 7A 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 16 00 18 00 00 00 6D 03 00 00 00 00 0C 00 00 00 0C 00 01 00 00 00 00 00 D8 8F 01 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 A5 77 A9 B7 1C DF 44 21");
    public static final byte[] READ_BRADY_COUNTERS = DataConvert.hexStringToByteArray("84 3F 2F FF 3A 02 00 05 44 04 00 00 00 00 00 34 04 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 FF 3A 02 00 05 44 04 00 00 00 00 00 34 04 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 FE 00 00 00 FB C0 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 5A 8A 9F 4D 1C DF 44 21");
    public static final byte[] RETRIEVE_RATE_HISTOGRAM = DataConvert.hexStringToByteArray("84 44 1B 00 00 00 00 00 00 00 00 0A 00 00 00 11 00 00 00 12 00 00 00 7B 00 00 00 69 1C 00 00 20 54 00 00 6A 5C 00 00 EB 0B 00 00 62 0C 00 00 3D 0C 00 00 B7 0C 00 00 A3 0E 00 00 26 0B 00 00 5A 22 01 00 00 00 00 00 7F 3A 00 00 05 E4 00 00 BA 1F 00 00 92 09 00 00 9A 00 00 00 4B 01 00 00 72 00 00 00 C7 02 00 00 9B 02 00 00 E2 49 00 00 77 43 00 00 C6 00 00 00 10 66 02 00 4D 00 00 00 00 00 00 00 B7 92 6F AB");
    public static final byte[] RETRIEVE_RATE_HISTOGRAM_LIFETIME = DataConvert.hexStringToByteArray("84 4A 1C 00 00 00 00 00 00 00 00 0A 00 00 00 11 00 00 00 12 00 00 00 7B 00 00 00 69 1C 00 00 20 54 00 00 6A 5C 00 00 EB 0B 00 00 62 0C 00 00 3D 0C 00 00 B7 0C 00 00 A3 0E 00 00 26 0B 00 00 5A 22 01 00 00 00 00 00 7F 3A 00 00 05 E4 00 00 BA 1F 00 00 92 09 00 00 9A 00 00 00 4B 01 00 00 72 00 00 00 C7 02 00 00 9B 02 00 00 E2 49 00 00 77 43 00 00 C6 00 00 00 10 66 02 00 4D 00 00 00 00 00 00 00 B7 92 6F AB");
    public static final byte[] READ_PACE_THRESHOLD_LOG = DataConvert.hexStringToByteArray("24 50 29 FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF 1D 23 E3 80 1C DF 44 21");
    public static final byte[] READ_CAP_REFORM_LOG = DataConvert.hexStringToByteArray("6C 53 20 5C 32 0D 05 00 00 01 00 AB 37 0D 05 00 00 01 00 B2 75 1E 05 00 00 01 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 03 00 00 00 23 69 81 54 1C DF 44 21");
    public static final byte[] READ_DEVICE_RESET_LOG = DataConvert.hexStringToByteArray("84 59 11 0A 03 14 99 B2 75 1E 05 A3 3D A1 5B 34 09 54 54 B2 75 1E 05 EC C0 66 C6 7D D9 00 40 B2 75 1E 05 02 61 60 00 00 00 54 5C 9F 6C C0 04 EB 28 2E C6 7D D9 00 01 9F 6C C0 04 01 61 00 00 00 00 14 01 C0 9B 1E 05 A4 3D A9 5F CB 09 54 5E EB 07 C3 04 EC C0 26 C6 7D D9 00 01 EB 07 C3 04 02 61 60 00 00 00 54 5C E7 FA C6 04 EC C0 66 C6 7D D9 00 01 E7 FA C6 04 02 61 60 00 00 00 02 00 09 29 81 F9 1C DF 44 21");
    public static final byte[] READ_TACHY_LOG = DataConvert.hexStringToByteArray("84 5E 10 01 01 00 00 42 59 A4 04 02 04 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 36 EF 30 50 1C DF 44 21");
    public static final byte[] READ_DEVICE_FAULT_LOG = DataConvert.hexStringToByteArray("80 63 14 EA F8 A1 04 0E 00 1F 00 EA F8 A1 04 0E 00 1E 00 EA F8 A1 04 0E 00 2F 00 D8 20 A3 04 14 00 19 00 D8 20 A3 04 14 00 11 00 DC A5 AC 04 01 00 08 00 3B 97 C2 04 01 00 1D 00 67 B3 C2 04 01 00 24 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 B8 78 A6 71 1C DF 44 21");
    public static final byte[] READ_CHARGE_LOG1 = DataConvert.hexStringToByteArray("74 05 74 00 00 00 00 BC 8C C2 04 10 A0 00 00 00 00 04 0E 00 00 00 00 DE 8C C2 04 10 90 00 00 06 00 04 0E 05 00 00 00 19 40 C2 04 1C 10 FF 0F FF 0F 00 00 00 00 00 00 1B 40 C2 04 10 A0 00 00 00 00 04 0E 05 00 00 00 70 6C C2 04 1C 10 FF 0F FF 0F 00 00 00 00 00 00 71 6C C2 04 10 A0 00 00 00 00 04 0E 05 00 00 00 C4 70 C2 04 1C 10 FF 0F FF 0F 00 00 89 28 7B E5");
    public static final byte[] READ_CHARGE_LOG2 = DataConvert.hexStringToByteArray("80 09 74 00 00 00 00 C5 70 C2 04 10 A0 00 00 06 00 04 0E 00 00 00 00 F3 70 C2 04 10 90 00 00 00 00 04 0E 05 00 00 00 72 8C C2 04 1C 10 FF 0F FF 0F 00 00 00 00 00 00 74 8C C2 04 10 A0 00 00 00 00 04 0E 05 00 00 00 9E 8C C2 04 1C 10 FF 0F FF 0F 00 00 00 00 00 00 9F 8C C2 04 10 A0 00 00 00 00 BF 0D 05 00 00 00 BA 8C C2 04 1C 10 FF 0F FF 0F 00 00 7F 00 00 00 3A 00 00 00 4C F9 11 7B CA 07 BD 22");
    public static final byte[] READ_BATTERY_LOG = DataConvert.hexStringToByteArray("34 0F 30 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 1C 66 24 83 1C DF 44 21");
    public static final byte[] READ_BATTERY_DETAIL = DataConvert.hexStringToByteArray("84 12 88 A6 B0 04 00 8A D4 20 00 57 BE 02 00 C0 5F 02 00 51 05 00 00 00 00 00 00 E8 12 00 00 66 BB 01 00 64 69 50 00 22 00 00 00 76 01 00 00 00 96 1D 02 00 08 63 19 73 13 36 00 00 00 00 02 00 00 00 00 00 00 00 00 17 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 D7 08 00 00 2E 99 1E 05 1A 02 5C 11 1C DF 44 21");
    public static final byte[] READ_ALARM_LOG = DataConvert.hexStringToByteArray("58 23 2E 00 00 00 00 00 00 00 01 00 00 00 00 00 00 00 01 00 00 00 00 00 00 00 01 D8 20 A3 04 0B 00 03 11 D8 20 A3 04 0B 00 04 11 00 00 00 00 00 00 00 01 00 00 00 00 00 00 00 01 00 00 00 00 00 00 00 01 00 00 00 00 00 00 00 01 00 00 00 00 00 00 00 01 B2 B4 58 F7 1C DF 44 21");
    public static final byte[] READ_M2_RTC_TIMER = DataConvert.hexStringToByteArray("10 39 19 2D 31 01 00 B2 75 1E 05 57 32 73 83 1C DF 44 21");
    public static final byte[] PROGRAM_RTC_DELTA = DataConvert.hexStringToByteArray("10 36 6D 67 95 02 00 79 4C C9 04 F7 BD D6 3D 1C DF 44 21");
    public static final byte[] READ_BRADY_PARAMETERS_POSTSHOCK = DataConvert.hexStringToByteArray("20 2F 4A 03 03 0A 27 0E 00 03 00 00 00 FA 00 78 00 00 00 03 1E 08 02 07 02 14 00 F9 FB 15 11 1C DF 44 21");
    public static final byte[] READ_BRADY_PARAMETERS_NORM = DataConvert.hexStringToByteArray("20 33 4A 03 01 02 22 03 00 03 00 00 00 FA 00 78 00 00 00 03 1E 08 02 07 02 14 00 98 8F B2 7E 1C DF 44 21");
    public static final String DEVICE_RESET_LOG = "DeviceResetLog.per";
    public static final byte[] READ_BLE_EPISODE = DataConvert.hexStringToByteArray("08 00 A3 1A 00 00 00 E7 68 E0 1E");
}
