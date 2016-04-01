package kenny.ml.utils;

import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.util.List;

/**
 * Created by kenny on 4/1/16.
 */
public class ResourceLoader {

    public static String toString(final String resource) {
        try {
            return IOUtils.toString(Thread.currentThread().getContextClassLoader().getResourceAsStream(resource));
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static List<String> toLines(final String resource) {
        try {
            return IOUtils.readLines(Thread.currentThread().getContextClassLoader().getResourceAsStream(resource));
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
