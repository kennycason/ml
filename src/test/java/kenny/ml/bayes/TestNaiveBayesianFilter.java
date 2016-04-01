package kenny.ml.bayes;

import kenny.ml.utils.ResourceLoader;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class TestNaiveBayesianFilter {

    @Test
    public void test() {
        // Create a new SpamFilter Object
        final NaiveBayesianFilter filter = new NaiveBayesianFilter();
        // Train spam with a file of spam e-mails
        filter.trainNegative(ResourceLoader.toString("kenny/ml/bayes/email/spam.txt"));
        // Train spam with a file of regular e-mails
        filter.trainPositive(ResourceLoader.toString("kenny/ml/bayes/email/good.txt"));
        // We are finished adding words so finalize the results
        filter.finalizeTraining();

        // Read in a text file
        // good
        // Ask the filter to analyze it
        final float ppos = filter.analyze(ResourceLoader.toString("kenny/ml/bayes/email/mail1.txt"));
        assertEquals(1.0, ppos, 0.05); // should be near zero, i.e. not spam

        // bad
        final float ppos2 = filter.analyze(ResourceLoader.toString("kenny/ml/bayes/email/mail2.txt"));
        assertEquals(0.0, ppos2, 0.05);

        final float ppos3 = filter.analyze(ResourceLoader.toString("kenny/ml/bayes/email/mail3.txt"));
        assertEquals(0.0, ppos3, 0.05);
    }


}
