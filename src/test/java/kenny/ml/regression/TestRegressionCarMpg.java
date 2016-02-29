package kenny.ml.regression;

import kenny.ml.regression.linear.LinearModel;
import kenny.ml.regression.linear.LinearRegression;
import kenny.ml.regression.nonlinear.LocalRegression;
import kenny.ml.regression.nonlinear.LocalRegressionModel;
import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * Created by kenny
 */
public class TestRegressionCarMpg {

    private static final Logger LOGGER = Logger.getLogger(TestRegressionCarMpg.class);

    private static final LinearRegression LINEAR_REGRESSION = new LinearRegression();
    private static final LocalRegression LOCAL_REGRESSION = new LocalRegression();

    private static final double DELTA = 0.05;

    @Test
    public void linear() throws IOException {
        final double[][] data = loadPoints(4, 0); // weight, mpg

        final LinearModel linearModel = LINEAR_REGRESSION.regress(data);
        LOGGER.info(linearModel);

        assertEquals(-0.007, linearModel.getLine().getSlope(), DELTA);
        assertEquals(33.65, linearModel.predict(1649), DELTA);
        assertEquals(26.69, linearModel.predict(2556), DELTA);
        assertEquals(7.95, linearModel.predict(4997), DELTA);
    }

    @Test
    public void nonlinearLocalRegression() throws IOException {
        final double[][] data = loadPoints(4, 0); // weight, mpg

        final int dx = 100;
        final LocalRegressionModel localRegressionModel = LOCAL_REGRESSION.regress(data, dx);
        LOGGER.info(localRegressionModel);

        assertEquals(34.1, localRegressionModel.predict(1649), DELTA);
        assertEquals(26.33, localRegressionModel.predict(2556), DELTA);
        assertEquals(11.95, localRegressionModel.predict(4997), DELTA);

        final int dx2 = 500;
        final LocalRegressionModel localRegressionModel2 = LOCAL_REGRESSION.regress(data, dx2);
        LOGGER.info(localRegressionModel2);

        assertEquals(33.21, localRegressionModel2.predict(1649), DELTA);
        assertEquals(25.59, localRegressionModel2.predict(2556), DELTA);
        assertEquals(12.08, localRegressionModel2.predict(4997), DELTA);
    }

    private static final double[][] loadPoints(final int x, final int y) throws IOException {
        final List<double[]> points = new ArrayList<>();
        final List<String> lines = IOUtils.readLines(Thread.currentThread().getContextClassLoader().getResourceAsStream("kenny/ml/regression/auto-mpg.data"));
        for(String line : lines) {
            final String[] attributes = line.split("\t");
            if("?".equals(attributes[x])) { continue; } // skip unknown x
            if("?".equals(attributes[y])) { continue; } // skip unknown y
            // System.out.println(attributes[x] + ", " + attributes[y]);

            points.add(new double[] { Double.valueOf(attributes[x]), Double.valueOf(attributes[y])});
        }
        return points.toArray(new double[0][]);
    }

}
