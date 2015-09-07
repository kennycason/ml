package kenny.ml.math;

import no.uib.cipr.matrix.DenseVector;
import no.uib.cipr.matrix.Vector;
import no.uib.cipr.matrix.VectorEntry;

import java.util.Arrays;

/**
 * Created by kenny
 */
public class Vectors {

    public static double correlation(final Vector vector, final Vector vector2) {
        final double stdDev = standardDeviation(vector);
        final double stdDev2 = standardDeviation(vector2);
        if(stdDev == 0 || stdDev2 == 0) {
            return 0; // if no variation, correlation is zero
        }
        return covariance(vector, vector2) / stdDev / stdDev2;
    }

    public static double covariance(final Vector vector, final Vector vector2) {
        if(vector.size() != vector2.size()) {
            throw new IllegalArgumentException("Vectors must both be the same length");
        }
        return meanDeviation(vector).dot(meanDeviation(vector2)) / (vector.size() - 1);
    }

    public static double standardDeviation(final Vector vector) {
        return Math.sqrt(variance(vector));
    }

    public static double variance(final Vector vector) {
        return sumOfSquares(meanDeviation(vector)) / (vector.size() - 1);
    }

    public static double mean(final Vector vector) {
        if(vector.size() == 0) { return 0; }
        double total = 0;
        for(VectorEntry point : vector) {
            total += point.get();
        }
        return total / vector.size();
    }

    public static Vector meanDeviation(final Vector vector) {
        final double mean = mean(vector);
        final Vector deviations = new DenseVector(vector.size());
        int i = 0;
        for(VectorEntry point : vector) {
            deviations.set(i++, point.get() - mean);
        }
        return deviations;
    }

    public static double sumOfSquares(final Vector vector) {
        double sumOfSquares = 0;
        for(VectorEntry point : vector) {
            sumOfSquares += point.get() * point.get();
        }
        return sumOfSquares;
    }

    public static double min(final Vector vector) {
        double min = Double.MAX_VALUE;
        for(VectorEntry point : vector) {
            if(point.get() < min) {
                min = point.get();
            }
        }
        return min;
    }

    public static double max(final Vector vector) {
        double max = Double.MIN_VALUE;
        for(VectorEntry point : vector) {
            if(point.get() > max) {
                max = point.get();
            }
        }
        return max;
    }

    public static double quantile(final Vector vector, final double percentile) {
        if(percentile < 0 || percentile > 100) {
            throw new IllegalArgumentException("Percentile must be in the range [0,100].");
        }
        final int percentileIndex = (int) (percentile * vector.size());
        return sort(vector).get(percentileIndex);
    }

    public static double quantileRange(final Vector vector, final double lower, final double upper) {
        if(lower > upper) {
            throw new IllegalArgumentException("Lower percentile must be less than upper.");
        }
        return quantile(vector, upper) - quantile(vector, lower);
    }

    public static Vector sort(final Vector vector) {
        final double[] array = toArray(vector);
        Arrays.sort(array);
        return new DenseVector(array);
    }

    public static double[] toArray(final Vector vector) {
        final double[] array = new double[vector.size()];
        int i = 0;
        for(VectorEntry point : vector) {
            array[i++] = point.get();
        }
        return array;
    }

}
