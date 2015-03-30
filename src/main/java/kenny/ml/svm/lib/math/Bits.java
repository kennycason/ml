package kenny.ml.svm.lib.math;


import kenny.ml.svm.lib.math.rounding.IRoundingStrategy;
import kenny.ml.svm.lib.math.rounding.ThresholdRoundingStrategy;

public class Bits {

    private final int bits;

    private long value;

    private IRoundingStrategy roundingStrategy = new ThresholdRoundingStrategy(0.5);

    public Bits(int bits, long value) {
        this.value = value;
        this.bits = bits;
    }

    public Bits(double[] arr) {
        bits = arr.length;
        value = 0;
        for(int i = arr.length - 1; i >= 0; i--) {
            value <<= 1;
            value += ((long)roundingStrategy.round(arr[i]));
        }
    }

    public int getBits() {
        return bits;
    }

    public double value() {
        return value;
    }

    public boolean isBit(int bit) {
        int mask = (int) 1 << bit;
        return (value & mask) == mask;
    }

    public int getBit(int bit) {
        if(isBit(bit)) {
            return 1;
        }
        return 0;
    }

    public double[] toDoubleArray() {
        double[] bits = new double[getBits()];
        for(int i = 0; i < getBits(); i++) {
            bits[i] = getBit(i);
        }
        return bits;
    }

    public boolean[] toBooleanArray() {
        boolean[] bits = new boolean[getBits()];
        for(int i = 0; i < getBits(); i++) {
            bits[i] = isBit(i);
        }
        return bits;
    }

    public int[] toIntArray() {
        int[] bits = new int[getBits()];
        for(int i = 0; i < getBits(); i++) {
            bits[i] = getBit(i);
        }
        return bits;
    }

    public String toString() {
        StringBuffer sb = new StringBuffer();
        for(int i = 0; i < getBits(); i++) {
            sb.append(getBit(i));
        }
        return sb.reverse().toString();
    }

}
