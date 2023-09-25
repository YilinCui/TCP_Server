package pgdata.Data;

public class DeviceStatus {
    private int deviceStatus = 0;
    public static final int STATUS_ATP_ACTIVE         = 0x00000001;
    public static final int STATUS_CHARGING_ON        = 0x00000002;
    public static final int STATUS_TEMP_BRADY         = 0x00000004;
    public static final int STATUS_STAT_PACE          = 0x00000008;
    public static final int STATUS_POSTSHOCK_BRADY    = 0x00000010;
    public static final int STATUS_EP_MANUAL_BURST    = 0x00000020;
    public static final int STATUS_EP_PES_INDUCTION   = 0x00000040;
    public static final int STATUS_EP_SHOCK_ON_T      = 0x00000080;
    public static final int STATUS_BATTERY_EOS        = 0x00000100;
    public static final int STATUS_BATTERY_RRT        = 0x00000200;
    public static final int STATUS_ELECTROCAUTERY_MODE= 0x00000400;
    public static final int STATUS_MRI_MODE           = 0x00000800;
    public static final int STATUS_STORAGE            = 0x00001000;
    public static final int STATUS_SAFETY             = 0x00002000;
    public static final int STATUS_MAGNET_INHIBIT     = 0x00004000;
    public static final int STATUS_TACHY_OFF          = 0x00008000;
    public static final int STATUS_TACHY_MONITOR      = 0x00010000;
    public static final int STATUS_COMMANDED_SHOCK    = 0x00020000;
    public static final int STATUS_CAP_REFORM         = 0x00040000;
    public static final int STATUS_MEASURE_IMPEDANCE  = 0x00080000;
    public static final int STATUS_MEASURE_BATTERY    = 0x00100000;
    public static final int STATUS_STAT_SHOCK         = 0x00200000;
    public static final int STATUS_TACHY_EPISODE      = 0x00400000;
    public static final int STATUS_MORPH_ACQUISITION  = 0x00800000;
    public static final int STATUS_MANUAL_THRESHOLD   = 0x01000000;
    public static final int STATUS_MEASURE_INTRINSIC  = 0x02000000;
    public static final int STATUS_HV_DUMP            = 0x04000000;

    public static final int DEFAULT = 0;
    public static final int STORAGE = 1;
    public static final int ELECTROCAUTERY = 2;
    public static final int MRI = 3;
    public static final int STATPACE = 4;
    public static final int TEMPBRADY = 5;
    public static final int CHARGING = 6;
    public static final int EPISODE = 7;
    public static final int SAFETY = 8;

    public boolean isATPActive() {
        return (deviceStatus & STATUS_ATP_ACTIVE) != 0;
    }

    public boolean isSTATPace(){
        return ((deviceStatus &STATUS_STAT_PACE )!= 0);
    }

    public boolean isShockOnT() {
        return (deviceStatus & STATUS_EP_SHOCK_ON_T) != 0;
    }

    public boolean isPESInduction() {
        return (deviceStatus & STATUS_EP_PES_INDUCTION) != 0;
    }

    public boolean isManualBurstPacing() {
        return (deviceStatus & STATUS_EP_MANUAL_BURST) != 0;
    }

    public boolean isCommandedShock() {
        return (deviceStatus & STATUS_COMMANDED_SHOCK) != 0;
    }

    public boolean isDeviceStorageMode(){
        return ((deviceStatus &STATUS_STORAGE )!= 0);
    }

    public boolean isDeviceTempBradyMode(){
        return ((deviceStatus &STATUS_TEMP_BRADY )!= 0);
    }

    public boolean isMRIMode(){
        return ((deviceStatus &STATUS_MRI_MODE )!= 0);
    }

    public boolean isElectrocauteryMode(){
        return ((deviceStatus &STATUS_ELECTROCAUTERY_MODE )!= 0);
    }

    public boolean isMagnetInhibited() {
        return ((deviceStatus & STATUS_MAGNET_INHIBIT) != 0);
    }

    public boolean isInTachyEpisode() {
        return ((deviceStatus & STATUS_TACHY_EPISODE) != 0);
    }

    public boolean isBatteryRRT() {
        return ((deviceStatus & STATUS_BATTERY_RRT) != 0);
    }

    public boolean isBatteryEOS() {
        return ((deviceStatus & STATUS_BATTERY_EOS) != 0);
    }

    public boolean isCharging() {
        return ((deviceStatus & STATUS_CHARGING_ON) != 0);
    }

    public boolean isSafetyCore() {
        return ((deviceStatus & STATUS_SAFETY) != 0);
    }

    public boolean isEPTest() {
        return isShockOnT() || isManualBurstPacing() || isPESInduction();
    }
}
