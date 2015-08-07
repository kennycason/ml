package kenny.impl.porn.site;

import kenny.impl.porn.url.UrlTextExtractor;
import org.apache.commons.io.IOUtils;

import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.List;

/**
 * Created by kenny on 4/1/15.
 */
public class PornSiteTextCorpusGenerator {
    public static void main(final String[] args) throws IOException {
        new PornSiteTextCorpusGenerator().generator();
    }

    private void generator() throws IOException {
        final FileWriter fileWriter = new FileWriter("/tmp/porn300.txt");
        final UrlTextExtractor urlTextExtractor = new UrlTextExtractor();
        final List<String> domains = IOUtils.readLines(Thread.currentThread().getContextClassLoader().getResourceAsStream("kenny/ml/nlp/porn/domains_top300.txt"));
        for(String domain : domains) {
            try {
                final String text = urlTextExtractor.read(new URL("http://" + domain));
                fileWriter.write(text + "\n");
                System.out.println(text);
            } catch(IOException e) {
                System.out.println("failed to read " + domain);
            }
        }
    }
}
