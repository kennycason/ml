package kenny.ml.regression.linear;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by kenny
 */
public class TestLinearRegression {

    private static final LinearRegression LINEAR_REGRESSION = new LinearRegression();

    private static final double DELTA = 0.005;

    @Test
    public void line() {
        final double[][] grades = new double[][] {
                {-1, -1},
                {0, 0},
                {1, 1}
        };

        final LinearModel linearModel = LINEAR_REGRESSION.regress(grades);
        assertEquals(1.0, linearModel.slope, DELTA);
        assertEquals(0.0, linearModel.yIntercept, DELTA);
        assertEquals(1.0, linearModel.coefficientOfDetermination, DELTA);
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
        assertEquals(0.64, linearModel.slope, DELTA);
        assertEquals(26.78, linearModel.yIntercept, DELTA);
        assertEquals(0.48, linearModel.coefficientOfDetermination, DELTA);
    }

}
