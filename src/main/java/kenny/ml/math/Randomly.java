package kenny.ml.math;

import org.eclipse.collections.api.list.ListIterable;
import org.eclipse.collections.api.list.MutableList;
import org.eclipse.collections.api.set.MutableSet;
import org.eclipse.collections.impl.factory.Lists;
import org.eclipse.collections.impl.factory.Sets;

import java.util.concurrent.ThreadLocalRandom;

/**
 * Created by kenny on 3/27/16.
 */
public class Randomly {
    private Randomly() {}

    public static double nextDouble() {
        return ThreadLocalRandom.current().nextDouble();
    }

    public static double nextDouble(final double min, final double max) {
        return ThreadLocalRandom.current().nextDouble(min, max);
    }

    public static double nextGaussian() {
        return ThreadLocalRandom.current().nextGaussian();
    }

    public static long nextLong() {
        return ThreadLocalRandom.current().nextLong();
    }

    public static int nextInt(final int bound) {
        return ThreadLocalRandom.current().nextInt(bound);
    }

    public static <T> ListIterable<T> sample(final ListIterable<T> list, final int sampleSize) {
        if (sampleSize > list.size()) {
            throw new IllegalArgumentException("Sample size is larger than list size");
        }
        if (sampleSize == list.size()) { return list; }
        final MutableSet<Integer> placedIndices = Sets.mutable.empty();
        final MutableList<T> sample = Lists.mutable.empty();
        while (sample.size() < sampleSize) {
            final int i = nextInt(list.size());
            if (placedIndices.contains(i)) { continue; }
            sample.add(list.get(i));
        }
        return sample;
    }

}
