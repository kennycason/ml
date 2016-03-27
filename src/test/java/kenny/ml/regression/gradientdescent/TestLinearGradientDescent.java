package kenny.ml.regression.gradientdescent;

import kenny.ml.math.Line;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by kenny
 */
public class TestLinearGradientDescent {
    private static final LinearGradientDescent LINEAR_GRADIENT_DESCENT = new LinearGradientDescent();
    private static final double LEARNING_RATE = 0.001;
    private static final int ITERATIONS = 5000;
    private static final double LINE_DELTA = 0.002;
    private static final double GRADES_DELTA = 0.2;

    @Test
    public void line() {
        final double[][] points = new double[][] {
                {-1, -1},
                {0, 0},
                {1, 1}
        };

        final LinearModel linearModel = LINEAR_GRADIENT_DESCENT.regress(
                                                Parameters.with(points)
                                                          .setLearningRate(LEARNING_RATE)
                                                          .setIterations(ITERATIONS));

        assertEquals(1.0, linearModel.getLine().getSlope(), LINE_DELTA);
        assertEquals(0.0, linearModel.getLine().getyIntercept(), LINE_DELTA);
        assertEquals(0.0, linearModel.error, LINE_DELTA);
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

        final LinearModel linearModel = LINEAR_GRADIENT_DESCENT.regress(
                                               Parameters.with(grades)
                                                         .setStartLine(new Line(5, 25))
                                                         .setLearningRate(LEARNING_RATE / 1000)
                                                         .setIterations(ITERATIONS * 10000));
        // the expected values are known exactly
        assertEquals(0.64, linearModel.getLine().getSlope(), GRADES_DELTA);
        assertEquals(26.78, linearModel.getLine().getyIntercept(), GRADES_DELTA);
        assertEquals(65.48, linearModel.error, GRADES_DELTA);
    }

}
