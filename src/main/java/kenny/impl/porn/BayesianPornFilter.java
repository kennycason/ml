package kenny.impl.porn;

import kenny.ml.bayes.BayesianFilter;
import kenny.ml.bayes.NaiveBayesianFilter;
import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * Created by kenny on 4/2/15.
 */
public class BayesianPornFilter {

    private static final Logger LOGGER = Logger.getLogger(BayesianPornFilter.class);

    private static final float PORN_THRESHOLD = 0.8f;

    private final BayesianFilter bayesianFilter = new NaiveBayesianFilter();

    public BayesianPornFilter() {
        init();
    }

    public boolean isPorn(String content) {
        return getPornProbability(content) > PORN_THRESHOLD;
    }

    public float getPornProbability(String content) {
        float pPositive = bayesianFilter.analyze(content);
        return 1.0f - pPositive;
    }

    private void init() {
        try {
            for(String line : loadPornText()) {
                bayesianFilter.trainNegative(line);
            }
            for(String line : loadGoodText()) {
                bayesianFilter.trainPositive(line);
            }

            bayesianFilter.finalizeTraining();
        }
        catch (IOException e) {
            LOGGER.error(e.getMessage(), e);
        }
    }

    private List<String> loadPornText() throws IOException {
        return IOUtils.readLines(getInputStream("kenny/ml/nlp/porn/bayes/porn300.txt"));
    }

    private List<String> loadGoodText() throws IOException {
        return IOUtils.readLines(getInputStream("kenny/ml/nlp/porn/bayes/not_porn.txt"));
    }

    private InputStream getInputStream(String resource) {
        return Thread.currentThread().getContextClassLoader().getResourceAsStream(resource);
    }

}
