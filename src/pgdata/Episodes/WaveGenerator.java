package pgdata.Episodes;

public interface WaveGenerator {
    public static byte[] generateSquareWave() {
        byte[] bytes = new byte[125];

        for (int i = 0; i < 125; i++) {
            if (i % 25 < 12) {
                bytes[i] = (byte) 255;
            } else {
                bytes[i] = 0;
            }
        }

        return bytes;
    }

    public static byte[] generateSinWave() {
        byte[] bytes = new byte[125];
        double amplitude = 1127.0; // 振幅，可根据需求调整
        double frequency = 1.0; // 频率为1Hz
        double angularFrequency = 2 * Math.PI * frequency; // 角频率

        for (int i = 0; i < 125; i++) {
            double time = i / (double) 125; // 当前采样点的时间，将i除以125，确保整个周期在125个采样点内完成
            double sinValue = amplitude * Math.sin(angularFrequency * time); // 计算正弦值
            bytes[i] = (byte) (sinValue + 128); // 将正弦值转换为byte范围并存储
        }

        return bytes;
    }

    public static byte[] generateTriangleWave() {
        byte[] bytes = new byte[125];
        int period = 25;

        for (int i = 0; i < 125; i++) {
            int t = i % period;
            double value = 2.0 * Math.abs(t - period / 2.0) / period;
            bytes[i] = (byte) (255 * value);
        }

        return bytes;
    }

    public static byte[] generateSawtoothWave() {
        byte[] bytes = new byte[125];
        int period = 25;

        for (int i = 0; i < 125; i++) {
            int t = i % period;
            double value = (double) t / period;
            bytes[i] = (byte) (255 * value);
        }

        return bytes;
    }


}
