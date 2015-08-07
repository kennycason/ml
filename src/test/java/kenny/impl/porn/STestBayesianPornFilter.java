package kenny.impl.porn;

import ch.lambdaj.Lambda;
import ch.lambdaj.function.convert.Converter;
import kenny.impl.porn.url.UrlTextExtractor;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.junit.Test;

import java.io.IOException;
import java.net.URL;
import java.util.List;

/**
 * Created by kenny on 4/2/15.
 */
public class STestBayesianPornFilter {

    private static Logger LOGGER = Logger.getLogger(STestBayesianPornFilter.class);

    @Test
    public void pornTest() throws IOException {
        LOGGER.info("Training Bayesian Filter");
        final BayesianPornFilter bayesianPornFilter = new BayesianPornFilter();
        LOGGER.info("Finished Training Bayesian Filter");

        int pornSites = 0;
        int urlsAnalyzed = 0;
        for(String url : pornUrls()) {
            try {
                final String text = getTextFromUrl(url);
                if(StringUtils.isAnyBlank(text) || text.length() < 100) {
                    LOGGER.info("Skipping blank url: " + url);
                    continue;
                }
                final float pPorn = bayesianPornFilter.getPornProbability(text);
                final boolean isPorn = bayesianPornFilter.isPorn(text);
                if(isPorn) { pornSites++; }
                urlsAnalyzed++;
                LOGGER.info(url + "\t\t" + isPorn + "\t" + pPorn + "\ttext length: " + text.length());
            } catch (IOException e) {
                LOGGER.info("Skipping url: " + url);
            }
        }
        LOGGER.info(((double)pornSites / urlsAnalyzed * 100.0) + "% of urls are porn");
    }

    private List<String> pornUrls() throws IOException {
        final List<String> domains = IOUtils.readLines(Thread.currentThread().getContextClassLoader().getResourceAsStream("kenny/ml/nlp/porn/domains_top300.txt"));
        return Lambda.convert(domains, DOMAIN_TO_URL_CONVERTER);
    }

    private String getTextFromUrl(final String url) throws IOException {
        final UrlTextExtractor urlTextExtractor = new UrlTextExtractor();
        return urlTextExtractor.read(new URL(url));
    }

    private static final Converter<String, String> DOMAIN_TO_URL_CONVERTER = new Converter<String, String>() {
        @Override
        public String convert(String domain) {
            if(domain.startsWith("http") || domain.startsWith("www")) { return domain; }
            return "http://" + domain;
        }
    };

}
