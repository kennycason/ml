package kenny.ml.cluster.feature;

/**
 * Created by kenny on 2/16/14.
 */
public class GeneralFeature extends Feature {

    /**
     * value should be between 0 and 1
     * @param name
     * @param value
     */
    public GeneralFeature(String name, double value) {
        super(name, value);
    }

    @Override
    public double normalize() {
        return getOriginalValue();
    }

    @Override
    public String toString() {
        return "GeneralFeature{" +
                "name='" + getName() + '\'' +
                ", value='" + getOriginalValue() +
                '}';
    }

}
