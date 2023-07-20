package pgdata;
import ParseData.*;

/**
 * payload example: 00 01 17 1F 0B 00 00 00 00 00 C8 00 64 02 00 00 03 0A 0D 0E 09 01 1C 25 69 E3 5C BB
 * Last 4 bytes are parameter CRC32
 */
public class BradyParameter {
    private byte[] bBradyDataStore;
    private byte BradyMode;
    private byte[] BradyModeOption = {0x00, 0x01, 0x02, 0x03, 0x04}; // OFF, OOO, VOO, VVI, VVIR. OOO has been abandoned.
    private byte BradyState; // 0x01 in BradyMode. 0x03 in Tachy Therapy Post Shocking
    private byte PacingRate;
    private byte[] PacingRateOption = {0x00, 0x01, 0x02, 0x03, 0x04, 0x05, 0x06, 0x07, 0x08, 0x09, 0x0A, 0x0B, 0x0C, 0x0D,
                                       0x0E, 0x0F, 0x10, 0x11, 0x12, 0x13, 0x14, 0x15, 0x16, 0x17, 0x18}; // 30bpm-150bpm
    private byte PacingAmplitude;
    private byte[] PacingAmplitudeOption = {0x04, 0x05, 0x06, 0x07, 0x08, 0x09, 0x0A, 0x0B, 0x0C, 0x0D, 0x0E, 0x0F, 0x10,
                                            0x11, 0x12, 0x13, 0x14, 0x15, 0x16, 0x17, 0x18, 0x19, 0x1A, 0x1B, 0x1C, 0x1D,
                                            0x1E, 0x1F, 0x20, 0x21, 0x22, 0x23, 0x24, 0x25, 0x26, 0x27, 0x28, 0x29, 0x2A}; //0.5V -> 7.5V
    private byte PacingWidth;
    private byte[] PacingWidthOption = {0x00, 0x01, 0x02, 0x03, 0x04, 0x05, 0x06, 0x07, 0x08, 0x09, 0x0A, 0x0B, 0x0C, 0x0D, 0x0E, 0x0F}; // 0.1ms -> 1.5ms
    private byte NoiseResponse;
    private byte[] NoiseResponseOption = {0x00, 0x01}; // VOO, Inhibit Pacing
    private byte PacingDurationAfterShock; // 0x00 in BradyMode, other value in Tachy Therapy
    private byte[] PacingDurationAfterShockDuration = {0x01, 0x02, 0x03, 0x04, 0x05, 0x06, 0x07, 0x08, 0x09, 0x0A, 0x0B};
    private byte PacingPauseAfterShockTime;
    private byte[] PacingPauseAfterShockTimeOption = {0x01, 0x02, 0x03, 0x04, 0x05};
    private byte ManualThresholdTestMode;
    private byte[] ManualThresholdTestModeOption = {0x00, 0x01};
    private byte ManualThresholdTestStepsPerPeriod;
    private byte VRPPaced; // Paced Refractory
    private byte[] VRPPacedOption = {};
    public BradyParameter(){
        // initialize all the brady data parameters


        // initialize the data store
    }

    public void setBradyParamter(byte[] bCommandIn){
        // parse individual data

        // update the data store
    }


    public byte[] readBradyParameter(){


        return bBradyDataStore;
    }


    public void dump(){
        // save to file
    }
}
