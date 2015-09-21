package kenny.ml.regression.linear;

/**
 * Created by kenny
 */
public class LinearModel {
    public final double slope;
    public final double yIntercept;
    public final double coefficientOfDetermination;

    public LinearModel(final double slope, final double yIntercept, final double coefficientOfDetermination) {
        this.slope = slope;
        this.yIntercept = yIntercept;
        this.coefficientOfDetermination = coefficientOfDetermination;
    }

    public double distance(final double x, final double y) {
        return Math.abs(y - f(x));
    }

    public boolean isAbove(final double x, final double y) {
        return y > f(x);
    }

    public double predict(final double x) {
        return f(x);
    }

    private double f(final double x) {
        return slope * x + yIntercept;
    }

    @Override
    public String toString() {
        return "Model{" +
                "slope=" + slope +
                ", yIntercept=" + yIntercept +
                ", coefficientOfDetermination=" + coefficientOfDetermination +
                '}';
    }

}
