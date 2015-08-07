package kenny.impl.porn.url;

import org.jsoup.Jsoup;

import java.io.IOException;
import java.net.URL;

/**
 * Created by kenny on 4/1/15.
 */
public class UrlTextExtractor {

    public String read(final URL url) throws IOException {
        return Jsoup.parse(url, 10000).text();
    }
}
