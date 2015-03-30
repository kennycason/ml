package kenny.ml.cluster.feature.impl.person;


import kenny.ml.cluster.feature.Feature;
import kenny.ml.cluster.math.Normalize;

/**
 * Created by kenny on 2/13/14.
 */
public class WeightFeature extends Feature {

    private static final double MAX_WEIGHT = 450;

    private static final double AVG_WEIGHT = 177.9;

    private static final double STAND_DEV = 7.5; // random number

    public WeightFeature(double value) {
        super("WEIGHT", value);
    }

    @Override
    public double normalize() {
        return Normalize.poisson(getOriginalValue(), 0.01);
    }

    @Override
    public String toString() {
        return "WeightFeature{" +
                "value=" + getOriginalValue() + " lb" +
                '}';
    }

}
