package kenny.ml.regression.gradientdescent;

import kenny.ml.math.Line;

/**
 * Created by kenny on 2/28/16.
 */
public class LinearGradientDescent {

    public LinearModel regress(final Parameters parameters) {
        Line currentLine = parameters.getStartLine();
        for (int i = 0; i < parameters.getIterations(); i++) {
            currentLine = stepGradient(currentLine, parameters);
        }
        return new LinearModel(currentLine, computeTotalError(parameters.getPoints(), currentLine));
    }

    // perform gradient descent
    // since y = mx + b has two parameters that we are optimizing (m and b),
    // take the partial derivative with respect to each variable. we will use this to
    // walk the gradient with respect to both variables.
    // specifically walk the gradient of the error function
    private Line stepGradient(final Line currentLine, final Parameters parameters) {
        final double learningRate = parameters.getLearningRate();
        final double[][] points = parameters.getPoints();
        final double n = points.length;
        // set a start slope/y-intercept, arbitrary line
        final double currentSlope = currentLine.getSlope();
        final double currentYIntercept = currentLine.getyIntercept();
        // the current gradient
        double slopeGradient = 0;
        double yInterceptGradient = 0;

        for (final double[] point : points) {
            slopeGradient += computeErrorDerivativeRespectToSlope(point, currentSlope, currentYIntercept, n);
            yInterceptGradient += computeErrorDerivativeRespectToYIntercept(point, currentSlope, currentYIntercept, n);
        }
        final double regressedSlope = currentSlope - (learningRate * slopeGradient);
        final double regressedYIntercept = currentYIntercept - (learningRate * yInterceptGradient);
        return new Line(regressedSlope, regressedYIntercept);
    }

    // y = mx + b
    // total error = 1/N ∑(y - (mx + b))^2
    // compute error of supplied line against the known y value.
    public double computeTotalError(final double[][] points, final Line line) {
        double totalError = 0;
        for (final double[] point : points) {
            totalError += computeError(point, line);
        }
        return totalError / points.length;
    }

    private double computeError(final double[] point, final Line line) {
        final double slope = line.getSlope();
        final double yIntercept = line.getyIntercept();
        final double x = point[0];
        final double y = point[1];
        return Math.pow(y - (slope * x + yIntercept), 2);
    }

    // 1/N ∑(y - (mx + b))^2
    // ∂/∂m   1/N ∑(y - (mx + b))^2
    //      = 1/N ∑(y - (mx - b))(y - (mx - b))
    //      = 1/N ∑(y - mx - b)(y - mx - b)
    //      = 1/N ∑(y^2 - ymx - yb - ymx + m^2x^2 + bmx - yb + bmx + b^2)
    //      = 1/N ∑(y^2 -2ymx - 2yb + m^2x^2 + 2bmx + b^2)

    // now start taking the derivative with respect to m
    //      = 1/N ∑(- 2yx + 2mx^2 + 2bx)
    //      = 1/N ∑(- 2x (y - mx - b))
    // ∂/∂m = 2/N ∑(- x (y - mx - b)
    private double computeErrorDerivativeRespectToSlope(final double[] point,
                                                        final double slope,
                                                        final double yIntercept,
                                                        final double n) {
        final double x = point[0];
        final double y = point[1];
        return (2 / n) * -x * (y - slope * x - yIntercept);
    }

    // now start taking the derivative with respect to y-intercept
    // ∂/∂b = 1/N ∑(- 2y + 2mx + 2b)
    //      = 2/N ∑(-y + mx + b)
    //      = 2/N ∑-(y - mx - b)
    private double computeErrorDerivativeRespectToYIntercept(final double[] point,
                                                             final double slope,
                                                             final double yIntercept,
                                                             final double n) {
        final double x = point[0];
        final double y = point[1];
        return (2 / n) * -(y - slope * x - yIntercept);
    }
}
