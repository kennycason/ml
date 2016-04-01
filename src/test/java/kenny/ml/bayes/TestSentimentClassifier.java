package kenny.ml.bayes;

import kenny.ml.data.imdb.ImdbData;
import kenny.ml.data.imdb.ImdbLoader;
import kenny.ml.utils.ResourceLoader;
import org.eclipse.collections.api.RichIterable;
import org.junit.Test;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;

/**
 * Created by kenny on 4/1/16.
 */
public class TestSentimentClassifier {
    @Test
    public void test() throws IOException {
        final ImdbData imdbData = ImdbLoader.load("/Users/kenny/Downloads/aclImdb/");
        System.out.println("Train: Loaded " + imdbData.train.positive.size() + " positive reviews and " + imdbData.train.negative.size() + " negative reviews");
        System.out.println("Test: Loaded " + imdbData.test.positive.size() + " positive reviews and " + imdbData.test.negative.size() + " negative reviews");

        final long start = System.currentTimeMillis();
        // Create a new SpamFilter Object
        final NaiveBayesianFilter filter = new NaiveBayesianFilter();
        filter.setCommulativeNGrams(true);
        filter.setnGrams(3);
        filter.setStopWords(new HashSet<>(ResourceLoader.toLines("kenny/ml/bayes/SPAM_WORDS.txt")));
        filter.setTokenNormalizers(Arrays.asList(String::toLowerCase));

        // Train spam with a file of spam e-mails
        filter.trainNegative(imdbData.train.negative.makeString(" "));
        // Train spam with a file of regular e-mails
        filter.trainPositive(imdbData.train.positive.makeString(" "));
        // We are finished adding words so finalize the results
        filter.finalizeTraining();
        System.out.println((System.currentTimeMillis() - start) + "ms");

        final float threshold = 0.05f;
        System.out.println("Training Data Results");
        evaluate(filter, imdbData.train.positive, 1.0f, threshold);
        evaluate(filter, imdbData.train.negative, 0.0f, threshold);

        System.out.println("Test Data Results");
        evaluate(filter, imdbData.test.positive, 1.0f, threshold);
        evaluate(filter, imdbData.test.negative, 0.0f, threshold);
    }

    private static void evaluate(final NaiveBayesianFilter filter, final RichIterable<String> samples, final float expected, final float threshold) {
        int correct = 0;
        int wrong = 0;
        for (final String sample : samples) {
            final float ppos = filter.analyze(sample);
            if (Math.abs(ppos - expected) <= threshold) {
                correct++;
            } else  if (Math.abs(ppos - expected) >= 1.0 - threshold) {
                wrong++;
            }
        }

        System.out.println("correct: " + correct + ", wrong: " + wrong + ", undecided: " + (samples.size() - correct - wrong));
        System.out.println(correct + "/" + samples.size() + " - " + (correct / (float) samples.size() * 100.0f) + "% accuracy");
    }
}
