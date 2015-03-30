package kenny.ml.cluster.math;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by kenny on 2/16/14.
 */
public class NormalizeTest {

    private static final double DELTA = 0.001;

    @Test
    public void linear() {
        assertEquals(0.0, Normalize.linear(0, 0, 10), DELTA);
        assertEquals(0.5, Normalize.linear(5, 0, 10), DELTA);
        assertEquals(1.0, Normalize.linear(10, 0, 10), DELTA);
    }

    @Test
    public void standard() {
        assertEquals(-2.5, Normalize.standard(0, 5, 2), DELTA);
        assertEquals(0.0, Normalize.standard(5, 5, 2), DELTA);
        assertEquals(2.5, Normalize.standard(10, 5, 2), DELTA);
    }

    @Test
    public void poisson() {
        assertEquals(0.0000, Normalize.poisson(0, 0.005), DELTA);
        assertEquals(0.3934, Normalize.poisson(100, 0.005), DELTA);
        assertEquals(0.6321, Normalize.poisson(200, 0.005), DELTA);
        assertEquals(0.7768, Normalize.poisson(300, 0.005), DELTA);
        assertEquals(0.8646, Normalize.poisson(400, 0.005), DELTA);

        assertEquals(0.0000, Normalize.poisson(0, 0.1), DELTA);
        assertEquals(0.3934, Normalize.poisson(5, 0.1), DELTA);
        assertEquals(0.6321, Normalize.poisson(10, 0.1), DELTA);
        assertEquals(0.7768, Normalize.poisson(15, 0.1), DELTA);
        assertEquals(0.9502, Normalize.poisson(30, 0.1), DELTA);
        assertEquals(0.9932, Normalize.poisson(50, 0.1), DELTA);

        assertEquals(0.0000, Normalize.poisson(0, 0.4), DELTA);
        assertEquals(0.8646, Normalize.poisson(5, 0.4), DELTA);
        assertEquals(0.9816, Normalize.poisson(10, 0.4), DELTA);
        assertEquals(0.9975, Normalize.poisson(15, 0.4), DELTA);
        assertEquals(0.9999, Normalize.poisson(50, 0.4), DELTA);
    }

}
