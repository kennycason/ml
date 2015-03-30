package kenny.ml.cluster.feature.impl;


import kenny.ml.cluster.feature.Feature;

/**
 * Created by kenny on 2/16/14.
 */
public class RatingFeature extends Feature {

    private static final double MAX_VALUE = 5.0;

    private static final double MIN_VALUE = 1.0;

    public RatingFeature(double value) {
        super("RATING", value);
    }

    @Override
    public double normalize() {
        return getOriginalValue();
    }

    @Override
    public String toString() {
        return "RatingFeature{" +
                "value='" + getOriginalValue() + " / " + MAX_VALUE + '\'' +
                '}';
    }

}
