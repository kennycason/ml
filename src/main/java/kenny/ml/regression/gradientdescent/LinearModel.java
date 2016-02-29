package kenny.ml.regression.gradientdescent;

import kenny.ml.math.Line;

/**
 * Created by kenny
 */
public class LinearModel {
    public final Line line;
    public final double error;

    public LinearModel(final Line line, final double error) {
        this.line = line;
        this.error = error;
    }

    public Line getLine() {
        return line;
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
        return line.f(x);
    }

    public double getError() {
        return error;
    }

    @Override
    public String toString() {
        return "LinearModel{" +
                "line=" + line +
                ", error=" + error +
                '}';
    }

}
