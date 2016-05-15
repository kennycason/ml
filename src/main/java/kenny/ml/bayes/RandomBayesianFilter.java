package kenny.ml.bayes;

import kenny.ml.math.Randomly;
import org.eclipse.collections.api.list.ListIterable;
import org.eclipse.collections.api.list.MutableList;
import org.eclipse.collections.impl.factory.Lists;

import java.util.Arrays;

/**
 * Created by kenny on 4/23/16.
 */
public class RandomBayesianFilter implements BayesianFilter {
    private final MutableList<BayesianFilter> bayesianFilters = Lists.mutable.empty();

    public RandomBayesianFilter(final int numberFilters, final ListIterable<String> positive, final ListIterable<String> negative) {
        for (int i = 0; i < numberFilters; i++) {
            bayesianFilters.add(buildFilter(positive, negative));
        }
    }

    private static BayesianFilter buildFilter(final ListIterable<String> positive, final ListIterable<String> negative) {
        final NaiveBayesianFilter filter = new NaiveBayesianFilter();
        filter.setCumulativeNGrams(false);
        filter.setnGrams(2);
        filter.setInterestingNGrams(25);
//        filter.setnGrams(Randomly.nextInt(3) + 1);
//        filter.setInterestingNGrams(Randomly.nextInt(25) + 5);
        filter.setTokenNormalizers(Arrays.asList(String::toLowerCase));

//        final double percent = (Randomly.nextInt(50) + 25) / 100.0;
//        final int positiveSampleSize = (int) (percent * positive.size());
//        final int negativeSampleSize = (int) (percent * negative.size());
        final int positiveSampleSize = (int) (.2 * positive.size());
        final int negativeSampleSize = (int) (.2 * negative.size());

        filter.trainPositive(Randomly.sample(positive, positiveSampleSize).makeString(" "));
        filter.trainNegative(Randomly.sample(negative, negativeSampleSize).makeString(" "));
        // We are finished adding words so finalize the results
        filter.finalizeTraining();

        return filter;
    }

    @Override
    public float analyze(final String text) {
        float ppos = 0.0f;
        for (final BayesianFilter bayesianFilter : bayesianFilters) {
            ppos += bayesianFilter.analyze(text);
        }
        return ppos / bayesianFilters.size();
    }

    @Override
    public void finalizeTraining() {
        throw new UnsupportedOperationException();
    }

    @Override
    public void trainNegative(final String content) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void trainPositive(final String content) {
        throw new UnsupportedOperationException();
    }
}
