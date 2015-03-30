package kenny.ml.cluster.math;

/**
 * Created by kenny on 2/13/14.
 */
public class Normalize {

    private Normalize() {}

    public static double linear(double x, double min, double max) {
        return (x - min) / (max - min);
    }

    public static double standard(double x, double average, double standardDeviation) {
        return (x - average) / (standardDeviation);
    }

    public static double poisson(double x, double lambda) {
        return 1 - Math.exp(-lambda * x);
    }

}
