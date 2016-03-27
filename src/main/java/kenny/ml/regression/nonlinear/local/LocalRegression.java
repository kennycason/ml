package kenny.ml.regression.nonlinear.local;

import kenny.ml.regression.linear.LinearRegression;
import kenny.ml.regression.linear.LinearModel;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by kenny
 *
 * Apply Linear Regression at various intervals to generate local derivatives of the
 * modeled function.
 * Works better with dense data sets.
 */
public class LocalRegression {

    private static final Logger LOGGER = Logger.getLogger(LocalRegression.class);

    private static final LinearRegression LINEAR_REGRESSION = new LinearRegression();

    public LocalRegressionModel regress(final double[][] points, final int dx) {
        final List<double[]> sortedPoints = sortX(points);
        final double[] minMax = findMinMax(points);

        final List<LinearModel> localRegressions = new ArrayList<>();
        final List<double[]> localWindow = new ArrayList<>();
        double max = minMax[0] + dx;

        for(int i = 0; i < sortedPoints.size(); i++) {
            final double[] point = sortedPoints.get(i);

            localWindow.add(point);
            if(point[0] >= max) {
                localRegressions.add(localRegress(localWindow));
                localWindow.clear();

                if(max < minMax[1]) {
                    localWindow.add(point); // inclusively add if there are still more points
                }
                max += dx;
            }
        }
        if(localWindow.size() > 1) {
            localRegressions.add(localRegress(localWindow));
        }
        if(localWindow.size() == 1) {
            LOGGER.warn("Specified dx leaves a trailing point that is not excluded in the model. (" + localWindow.get(0)[0] + "," + localWindow.get(0)[1] + ")");
        }

        return new LocalRegressionModel(localRegressions, minMax[0], minMax[1], dx);
    }

    private LinearModel localRegress(final List<double[]> localWindow) {
        if(localWindow.size() == 1) {
            LOGGER.warn("Local regression window size is 1, try increasing dx");
        }
        final double[][] points = localWindow.toArray(new double[0][]);
        return LINEAR_REGRESSION.regress(points);
    }

    private static List<double[]> sortX(final double[][] points) {
        final List<double[]> sorted = new ArrayList<>();
        for(double[] point : points) {
            sorted.add(point);
        }
        Collections.sort(sorted, (lhs, rhs) -> Double.compare(lhs[0], rhs[0]));
        return sorted;
    }

    private static double[] findMinMax(final double[][] points) {
        double min = Double.MAX_VALUE;
        double max = Double.MIN_VALUE;
        for(double[] point : points) {
            if(point[0] < min) { min = point[0]; }
            if(point[0] > max) { max = point[0]; }
        }
        return new double[] { min, max };
    }

}
