package kenny.ml.bayes;

import org.apache.commons.io.IOUtils;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.assertEquals;

public class TestNaiveBayesianFilter {

	@Test
	public void test() throws IOException {
		NaiveBayesianFilter filter = new NaiveBayesianFilter();
		// Train spam with a file of spam e-mails
		filter.trainNegative(getText("email/spam.txt"));
		// Train spam with a file of regular e-mails
		filter.trainPositive(getText("email/good.txt"));

		// We are finished adding words so finalize the results
		filter.finalizeTraining();

		// Read in a text file
		// good
		// Ask the filter to analyze it
		float ppos = filter.analyze(getText("email/mail1.txt"));
		assertEquals(1.0, ppos, 0.05);

		// bad
		ppos = filter.analyze(getText("email/mail2.txt"));
		assertEquals(0.0, ppos, 0.05);

		ppos = filter.analyze(getText("email/mail3.txt"));
		assertEquals(0.0, ppos, 0.05);

	}

    private String getText(final String resource) throws IOException {
        return IOUtils.toString(
            Thread.currentThread().getContextClassLoader().getResourceAsStream("kenny/ml/bayes/" + resource)
        );
    }

}
