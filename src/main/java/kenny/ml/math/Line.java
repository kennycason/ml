package kenny.ml.math;

/**
 * Created by kenny on 2/28/16.
 */
public class Line {
    private final double slope;
    private final double yIntercept;

    public Line(final double slope, final double yIntercept) {
        this.slope = slope;
        this.yIntercept = yIntercept;
    }

    public double f(final double x) {
        return slope * x + yIntercept;
    }

    public double getSlope() {
        return slope;
    }

    public double getyIntercept() {
        return yIntercept;
    }

    @Override
    public String toString() {
        return "Line{" +
                "slope=" + slope +
                ", yIntercept=" + yIntercept +
                '}';
    }
}
