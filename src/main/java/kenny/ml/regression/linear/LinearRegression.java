package kenny.ml.regression.linear;

/**
 * Created by kenny
 */
public class LinearRegression {

    /*
     * generate a linear model of the form y = b0 + x * b1
     *
     * The line minimizes the sum of squared differences between observed values (y)
     * and predicted values
     *
     * b1 = Σ[(xi - E(x))(yi - E(y))] / Σ[(xi - E(x))^2]
     * b1 = r * (σy / σx)
     * b0 = E(y) - b1 * E(x)
     *
     * Coefficient of determination (accuracy)
     * R2 = ( 1/n * Σ[(xi - E(x)) * (yi - E(y)] ) / (σx * σy))^2
     */
    public Model regress(final double[][] points) {
        final double meanX = mean(points, 0);
        final double meanY = mean(points, 1);
        final double sumDeltaSquaredX = sumDeltaSquared(points, meanX, 0);
        final double sumDeltaMeansCorrelation = sumDeltaMeansMultiplied(points, meanX, meanY);

        // calculate slope and y-intercept
        final double b1 = sumDeltaMeansCorrelation / sumDeltaSquaredX;
        final double b0 = meanY - b1 * meanX;

        // calculate error
        final double standardDeviationX = standardDeviation(points, meanX, 0);
        final double standardDeviationY = standardDeviation(points, meanY, 1);
        final double coefficientOfDetermination = Math.pow(((1.0 / points.length) * sumDeltaMeansCorrelation) / (standardDeviationX * standardDeviationY), 2);

        return new Model(b1, b0, coefficientOfDetermination);
    }

    private static double standardDeviation(final double[][] points, final double mean, final int index) {
        final double variance = sumDeltaSquared(points, mean, index) / points.length;
        return Math.sqrt(variance);
    }

    private static double sumDeltaSquared(final double points[][], final double mean, final int index) {
        double sumDeltaSquares = 0.0;
        for(double[] point : points) {
            sumDeltaSquares += (point[index] - mean) * (point[index] - mean);
        }
        return sumDeltaSquares;
    }

    private static double mean(final double[][] points, int index) {
        double sum = 0.0;
        for(double[] point : points) {
            sum += point[index];
        }
        return sum / points.length;
    }

    private static double sumDeltaMeansMultiplied(final double[][] points, final double meanX, final double meanY) {
        double sum = 0.0;
        for(double[] point : points) {
            sum += (point[0] - meanX) * (point[1] - meanY);
        }
        return sum;
    }

}
