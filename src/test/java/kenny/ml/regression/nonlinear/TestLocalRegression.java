package kenny.ml.regression.nonlinear;

import kenny.ml.regression.linear.LinearRegression;
import kenny.ml.regression.linear.LinearModel;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by kenny
 */
public class TestLocalRegression {

    private static final LinearRegression LINEAR_REGRESSION = new LinearRegression();
    private static final LocalRegression LOCAL_REGRESSION = new LocalRegression();

    private static final double DELTA = 0.005;

    @Test
    public void line() {
        final double[][] grades = new double[][] {
                {-1, -1},
                {0, 0},
                {1, 1}
        };

        final int dx = 1;
        final LocalRegressionModel localRegressionModel = LOCAL_REGRESSION.regress(grades, dx);
        assertEquals(2, localRegressionModel.localRegressions.size());
        assertEquals(-1.0, localRegressionModel.min, DELTA);
        assertEquals(1.0, localRegressionModel.max, DELTA);
        assertEquals(1.0, localRegressionModel.coefficientOfDetermination, DELTA);
        // extrapolate
        assertEquals(-5.0,localRegressionModel.predict(-5.0), DELTA);
        assertEquals(5.0,localRegressionModel.predict(5.0), DELTA);
    }


    @Test
    public void grades() {
        final double[][] grades = new double[][] {
                {95, 85},
                {85, 95},
                {80, 70},
                {70, 65},
                {60, 70}
        };

        final LinearModel linearModel = LINEAR_REGRESSION.regress(grades);
        assertEquals(0.64, linearModel.getLine().getSlope(), DELTA);
        assertEquals(26.78, linearModel.getLine().getyIntercept(), DELTA);
        assertEquals(0.48, linearModel.coefficientOfDetermination, DELTA);

        final int dx = 15;
        final LocalRegressionModel localRegressionModel = LOCAL_REGRESSION.regress(grades, dx);
        assertEquals(2, localRegressionModel.localRegressions.size());
        assertEquals(0.093, localRegressionModel.coefficientOfDetermination, DELTA);

        assertEquals(82.14,localRegressionModel.predict(85), DELTA);
        // slopes are obviously off due to local errors
        assertEquals(0.0, localRegressionModel.localRegressions.get(0).getLine().getSlope(), DELTA);
        assertEquals(0.71, localRegressionModel.localRegressions.get(1).getLine().getSlope(), DELTA);
    }

}
