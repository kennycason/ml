package kenny.ml.regression.nonlinear;

import ch.lambdaj.Lambda;
import kenny.ml.regression.linear.LinearModel;
import org.apache.log4j.Logger;

import java.util.List;

/**
 * Created by kenny
 */
public class LocalRegressionModel {

    private static final Logger LOGGER = Logger.getLogger(LocalRegressionModel.class);

    public final List<LinearModel> localRegressions;
    public final double min;
    public final double max;
    public final double dx;
    public final double coefficientOfDetermination;

    public LocalRegressionModel(final List<LinearModel> localRegressions, final double min, final double max, final double dx) {
        this.localRegressions = localRegressions;
        this.min = min;
        this.max = max;
        this.dx = dx;
        this.coefficientOfDetermination = averageCoefficientOfDetermination(localRegressions);
    }

    private static double averageCoefficientOfDetermination(final List<LinearModel> localRegressions) {
        double coefficientOfDetermination = 0.0;
        for(LinearModel model : localRegressions) {
            coefficientOfDetermination += model.coefficientOfDetermination;
        }
        return coefficientOfDetermination / localRegressions.size();
    }

    public double distance(final double x, final double y) {
        return getModel(x).distance(x, y);
    }

    public boolean isAbove(final double x, final double y) {
        return getModel(x).isAbove(x, y);
    }

    public double predict(final double x) {
        return getModel(x).predict(x);
    }

    private LinearModel getModel(final double x) {
        if(x < min) {
            LOGGER.warn("x is outside of the model's x-range, extrapolating");
            return localRegressions.get(0);
        }
        if (x > max) {
            LOGGER.warn("x is outside of the model's x-range, extrapolating");
            return localRegressions.get(localRegressions.size() - 1);
        }
        return localRegressions.get((int) ((x - min) / dx));
    }

    @Override
    public String toString() {
        return "LocalRegressionModel{" +
                "localRegressions=" + Lambda.join(localRegressions, "\n") +
                ", min=" + min +
                ", max=" + max +
                ", dx=" + dx +
                ", coefficientOfDetermination=" + coefficientOfDetermination +
                '}';
    }
}
