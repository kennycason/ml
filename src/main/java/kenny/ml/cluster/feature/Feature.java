package kenny.ml.cluster.feature;

/**
 * Created by kenny on 2/13/14.
 */
public abstract class Feature implements Normalizable {

    private final String name;

    private final double value;

    private final double originalValue;

    public Feature(String name, double value) {
        this.name = name;
        this.originalValue = value;
        this.value = normalize();
    }

    public String getName() {
        return name;
    }

    /**
     * getItem normalized value
     */
    public double getValue() {
        return value;
    }

    public double getOriginalValue() {
        return originalValue;
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }

    @Override
    public String toString() {
        return "Feature{" +
                "name='" + name + '\'' +
                ", value=" + value +
                '}';
    }

}
