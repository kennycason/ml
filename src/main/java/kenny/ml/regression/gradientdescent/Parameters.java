package kenny.ml.regression.gradientdescent;

import kenny.ml.math.Line;

/**
 * Created by kenny on 2/29/16.
 */
public class Parameters {
    private int iterations = 1000;
    private double learningRate = 0.001;
    private Line startLine = new Line(0, 0);
    private double[][] points;

    private Parameters() {}

    public static Parameters with(final double[][] points) {
        final Parameters parameters = new Parameters();
        parameters.points = points;
        return parameters;
    }

    public Parameters setIterations(final int iterations) {
        this.iterations = iterations;
        return this;
    }

    public Parameters setLearningRate(final double learningRate) {
        this.learningRate = learningRate;
        return this;
    }

    public Parameters setStartLine(final Line startLine) {
        this.startLine = startLine;
        return this;
    }

    public int getIterations() {
        return iterations;
    }

    public double getLearningRate() {
        return learningRate;
    }

    public Line getStartLine() {
        return startLine;
    }

    public double[][] getPoints() {
        return points;
    }
}
