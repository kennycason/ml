package kenny.ml.data.imdb;

import org.apache.commons.io.IOUtils;
import org.eclipse.collections.api.RichIterable;
import org.eclipse.collections.api.list.MutableList;
import org.eclipse.collections.impl.list.mutable.FastList;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * Created by kenny on 3/28/16.
 */
public class ImdbLoader {

    public static ImdbData load(final String directory) throws IOException {
        final ImdbData imdbData = new ImdbData();
        imdbData.train.positive.addAllIterable(loadAllFiles(directory + "train/pos/"));
        imdbData.train.negative.addAllIterable(loadAllFiles(directory + "train/neg/"));
        imdbData.test.positive.addAllIterable(loadAllFiles(directory + "test/pos/"));
        imdbData.test.negative.addAllIterable(loadAllFiles(directory + "test/neg/"));

        return imdbData;
    }

    private static RichIterable<String> loadAllFiles(final String directory) throws IOException {
        System.out.println("Loading files in directory: " + directory);
        final MutableList<String> fileContents = new FastList<>();
        Files.walk(Paths.get(directory)).forEach(filePath -> {
            if (Files.isRegularFile(filePath)) {
                try {
                    fileContents.add(IOUtils.toString(filePath.toUri()));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        return fileContents;
    }
}
