package kenny.ml.data.dictionary;

import org.eclipse.collections.api.RichIterable;

import java.io.*;

/**
 * Created by kenny on 3/27/16.
 */
public class WordFrequencyFileWriter {
    public static void write(final String fileName, final RichIterable<WordFrequency> wordFrequencies) throws IOException {
        final FileWriter fileWriter = new FileWriter(fileName);
        for (final WordFrequency wordFrequency : wordFrequencies) {
            fileWriter.write(wordFrequency.frequency + " " + wordFrequency.word + '\n');
        }
        fileWriter.flush();
        fileWriter.close();
    }
}
