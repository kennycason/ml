package kenny.ml.data.wordvector;

import org.eclipse.collections.api.list.MutableList;
import org.eclipse.collections.api.map.MutableMap;
import org.eclipse.collections.impl.list.mutable.FastList;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

/**
 * Created by kenny on 3/28/16.
 *
 * load a word2vec text formatted file
 *
 * [word] [d1, d2, ..., dn]
 *
 */
public class Word2VecTextLoader {

    public static MutableMap<String, double[]> loadAsMap(final String fileName, final int dimensions, final int words) throws IOException {
        return load(fileName, dimensions, words)
                .toMap(wv -> wv.word, wv -> wv.vector);
    }

    public static MutableList<WordVector> load(final String fileName, final int dimensions, final int words) throws IOException {
        final MutableList<WordVector> wordVectors = new FastList<>(words);

        try (Stream<String> stream = Files.lines(Paths.get(fileName))) {
            stream.limit(words)
                  .forEach(line -> {
                      if (line.startsWith("30")) { return; }
                      wordVectors.add(parseWordVector(line, dimensions));
                  });
        }
        return wordVectors;
    }

    private static WordVector parseWordVector(final String line, final int dimensions) {
        final String[] parts = line.split(" ");
        final String word = parts[0];
        final double[] vector = new double[dimensions];
        for (int i = 0; i < dimensions; i++) {
            vector[i] = Double.parseDouble(parts[i + 1]);
        }
        return new WordVector(word, vector);
    }

}
