package pgdata.DeviceLog;

import Constant.Constant;
import Controller.RandomData;
import DataStructure.DynamicByteBuffer;
import ParseData.DataConvert;

import javax.xml.crypto.Data;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.*;

public class DeviceChargeLog extends BaseLog {
    private byte[] packetHeader1 = new byte[]{0x77, 0x05, 0x74};
    private byte[] packetHeader2 = new byte[]{(byte) 0x83, 0x09, 0x74};
    private byte[] payload1 = null;
    private byte[] payload2 = null;

    private Map<Integer, String> hvOperations;
    private Map<Integer, String> hvStatuses;
    private Map<Integer, String> m3Statuses;
    private static final String STR_INVALID = "INVALID";
    private static final String STR_NA = "NA";
    // New Lists to hold Integer values
    private List<Integer> hvOperationsList;
    private List<Integer> hvStatusesList;
    private List<Integer> m3StatusesList;

    public DeviceChargeLog() {
        hvOperations = new HashMap<>();
        hvOperations.put(0x1000, "CMD SHOCK");
        hvOperations.put(0x2000, "SHOCK ON T");
        hvOperations.put(0x3000, "HV TEST");
        hvOperations.put(0x4000, "CAP REFORM");
        hvOperations.put(0x5000, "HV TEST SYNC");
        hvOperations.put(0x8000, "STAT SHOCK");
        hvOperations.put(0x9000, "DUMP");
        hvOperations.put(0xA000, "DISCHARGE");
        hvOperations.put(0xB000, "DIVERT");

        hvStatuses = new HashMap<>();
        hvStatuses.put(0x0010, "SUCCESS");
        hvStatuses.put(0x0020, "CHG TIMEOUT");
        hvStatuses.put(0x0030, "DISCHG ERR");
        hvStatuses.put(0x0060, "ABORTED");

        m3Statuses = new HashMap<>();
        m3Statuses.put(0x0000, STR_NA);
        m3Statuses.put(0x0100, "M1 SENT");
        m3Statuses.put(0x0200, "M3 RETURNED");
        m3Statuses.put(0x0300, "M1 SENT M3 RET");

        // Initialize and populate the new Lists
        hvOperationsList = new ArrayList<>(hvOperations.keySet());
        hvStatusesList = new ArrayList<>(hvStatuses.keySet());
        m3StatusesList = new ArrayList<>(m3Statuses.keySet());
    }

    private class ChargeLog {
        private byte[] duration;
        private byte[] timestamp;
        private byte[] status;
        private byte[] disStart;
        private byte[] disEnd;
        private byte[] impedance;

        public ChargeLog(int deviceMode, int testCaseId) {
            // 4 bytes
            duration = RandomData.generateRandomLittleEndianBytes(300);
            // 4 bytes
            timestamp = RandomData.getTimePassedInSeconds();
            // 2 bytes
            status = RandomData.generateBytesinRange();
            // 2 bytes
            disStart = RandomData.generateRandomBytes(2);
            // 2 bytes
            disEnd = RandomData.generateRandomBytes(2);
            // 2 bytes
            impedance = RandomData.generateRandomBytes(2);

            if (deviceMode == 0) {

            } else if (deviceMode == 1) {
                switch (testCaseId) {
                    case 0: {
                        timestamp = new byte[4];
                        duration = new byte[4];
                        status = new byte[2];
                        break;
                    }
                    case 1: {
                        // Randomized test
                        break;
                    }
                    case 2: {
                        duration = new byte[4];
                        break;
                    }
                    case 3: {
                        status = new byte[2];
                        break;
                    }
                    case 4: {
                        timestamp = new byte[4];
                        break;
                    }
                }
            } else if (deviceMode == 2) {
                status = DataConvert.intToTwoBytes(hvOperationsList.get(testCaseId));
            } else if (deviceMode == 3) {
                status = DataConvert.intToTwoBytes(hvStatusesList.get(testCaseId));
            }else if (deviceMode == 4) {
                status = DataConvert.intToTwoBytes(m3StatusesList.get(testCaseId));
            }
        }

        public byte[] getChargeLog() {
            DynamicByteBuffer buffer = new DynamicByteBuffer();
            buffer.put(duration);
            buffer.put(timestamp);
            buffer.put(status);
            buffer.put(disStart);
            buffer.put(disEnd);
            buffer.put(impedance);
            return buffer.toArray();
        }

        public byte[] getTimestamp() {
            return timestamp;
        }

        public byte[] getStatus() {
            return status;
        }

    }

    @Override
    public byte[] getbReturnData() {
        return null;
    }

    public byte[] getbReturnData(int deviceMode, int testCaseId, int chargeLog) {
        DynamicByteBuffer buffer;
        List<ChargeLog> list = new ArrayList<>();
        bRetrunData = null;
        if (chargeLog == 1) {
            // for packet1, it contains packetHeader1 + 7 entries of ChargeLog + 4 bytes of CRC32
            DynamicByteBuffer dataBuffer = new DynamicByteBuffer();

            buffer = new DynamicByteBuffer();
            for (int i = 0; i < 7; i++) {
                DeviceChargeLog.ChargeLog log = new DeviceChargeLog.ChargeLog(deviceMode, testCaseId);
                buffer.put(log.getChargeLog());
                list.add(log);
            }

            dataBuffer.put(buffer.toArray());
            // 将ByteBuffer转化为byte数组
            payload1 = dataBuffer.toArray();

            byte[] parameterCRC32 = DataConvert.calculateCRC32(payload1, 0, payload1.length);
            dataBuffer.put(parameterCRC32);

            dataBuffer.put(0, packetHeader1);

            bRetrunData = dataBuffer.toArray();
        } else if (chargeLog == 2) {
            // for packet2, it contains
            // 1. packetHeader2 and 7 entries of ChargeLog
            // 2. 7 bytes of supplement
            // 3. 1 byte of charge_log_index
            // 4. CRC32 for the (14 entries of Charge Log and 7 bytes of supplement + 1 byte of charge_log_index)
            // 5. CRC32 for payload of packet2 and CRC32 in step 4.
            DynamicByteBuffer dataBuffer = new DynamicByteBuffer();
            buffer = new DynamicByteBuffer();
            for (int i = 0; i < 7; i++) {
                DeviceChargeLog.ChargeLog log = new DeviceChargeLog.ChargeLog(deviceMode, testCaseId);
                buffer.put(log.getChargeLog());
                list.add(log);
            }

            byte[] reserved = new byte[8];

            payload2 = buffer.toArray();
            dataBuffer.put(payload1);
            dataBuffer.put(payload2);
            dataBuffer.put(reserved);

            payload = dataBuffer.toArray();
            byte[] payloadCRC32 = DataConvert.calculateCRC32(payload, 0, payload.length);

            DynamicByteBuffer returnBuffer = new DynamicByteBuffer();

            returnBuffer.put(payload2);
            returnBuffer.put(reserved);
            returnBuffer.put(payloadCRC32);

            byte[] packetBody = returnBuffer.toArray();
            byte[] StructureCRC32 = DataConvert.calculateCRC32(packetBody, 0, packetBody.length);
            returnBuffer.put(StructureCRC32);
            returnBuffer.put(0, packetHeader2);

            bRetrunData = returnBuffer.toArray();
        }

        // Print out sorted log entries
        for (ChargeLog log : list) {

            System.out.println("The Timestamp is: " + DataConvert.getFormattedTimestampFromBytes(log.getTimestamp()) + " The status is " + DataConvert.byteDataToHexString(log.getStatus()));
        }

        if (bRetrunData != null)
            return bRetrunData;

        else {
            System.out.println("Invalid chargeLogCount!");
            return null;
        }
    }
}
