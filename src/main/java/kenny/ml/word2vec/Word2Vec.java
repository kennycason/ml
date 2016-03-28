package kenny.ml.word2vec;

import cern.colt.matrix.tdouble.DoubleFactory2D;
import cern.colt.matrix.tdouble.DoubleMatrix2D;
import kenny.ml.data.dictionary.FrequencyAwareWordDictionary;
import kenny.ml.data.dictionary.WordFrequency;
import kenny.ml.math.Randomly;
import kenny.ml.nlp.tokenizer.LazyDelimeterBackedTokenizer;
import org.eclipse.collections.api.list.MutableList;
import org.eclipse.collections.api.set.MutableSet;
import org.eclipse.collections.impl.factory.Sets;
import org.eclipse.collections.impl.list.mutable.FastList;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

/**
 * Created by kenny on 3/27/16.
 *
 * Java version of Tensor Flow's implementation.
 * https://github.com/tensorflow/tensorflow/blob/r0.7/tensorflow/examples/tutorials/word2vec/word2vec_basic.py#L66
 */
public class Word2Vec {

    // step 4, build and train a skip-gram model.
    public void train(final Parameters parameters) throws FileNotFoundException {
        final FrequencyAwareWordDictionary dictionary = readData(parameters);
        final MutableList<WordFrequency> words = dictionary.sortByFrequencyDescending();

        final int batchSize = 128;
        final int embeddingSize = 128;  // dimension of the embedding vector.
        final int skipWindow = 1;       // how many words to consider left and right.
        final int numSkips = 2;         // how many times to reuse an input to generate a label.

        // we pick a random validation set to sample nearest neighbors. Here we limit the
        // validation samples to the words that have a low numeric ID, which by
        // construction are also the most frequent.
        final int validSize = 16;     // random set of words to evaluate similarity on.
        final int validWindow = 100;  // only pick dev samples in the head of the distribution.
        final MutableList<WordFrequency> validExamples = Randomly.sample(words.subList(0, validWindow), validSize);
        final int numSampled = 64;    // number of negative examples to sample.

        final DoubleMatrix2D embeddings = DoubleFactory2D.dense
                                            .make(parameters.vocabularySize, embeddingSize)
                                            .assign(v -> Randomly.nextDouble(-1.0, 1.0));

        // TODO resume at L150
    }

    // step 3, generate a training batch for the skip-gram model.
    public BatchAndLabels generateBatch(final Parameters parameters, final FrequencyAwareWordDictionary dictionary) {
        validateParameters(parameters);
        final MutableList<WordFrequency> words = dictionary.sortByFrequencyDescending();

        final WordFrequency[] batch = new WordFrequency[parameters.batchSize];
        final WordFrequency[] labels = new WordFrequency[parameters.batchSize];
        final int span = 2 * parameters.skipWindow + 1;
        final MutableList<WordFrequency> buffer = new FastList<>(span);
        int dataIndex = 0;
        for (int i = 0; i < span; i++) {
            buffer.add(words.get(dataIndex));
            dataIndex = (dataIndex + 1) % dictionary.size();
        }
        for (int i = 0; i < parameters.batchSize / parameters.numSkips; i++) {
            int target = parameters.skipWindow;
            final MutableSet<Integer> previouslySelectedTargets = Sets.mutable.empty();
            for (int j = 0; j < parameters.numSkips; j++) {
                while (previouslySelectedTargets.contains(target)) {
                    target = Randomly.nextInt(span);
                }
                previouslySelectedTargets.add(target);
                batch[i * parameters.numSkips + j] = buffer.get(parameters.skipWindow);
                labels[i * parameters.numSkips + j] = buffer.get(target);
            }
            buffer.add(words.get(dataIndex));
            dataIndex = (dataIndex + 1) % dictionary.size();
        }
        return new BatchAndLabels(batch, labels);
    }

    // step 1 & 2, load and build the dictionary.
    private FrequencyAwareWordDictionary readData(final Parameters parameters) throws FileNotFoundException {
        final LazyDelimeterBackedTokenizer tokenizer = new LazyDelimeterBackedTokenizer(' ', new FileInputStream(new File(parameters.fileName)));
        final FrequencyAwareWordDictionary dictionary = new FrequencyAwareWordDictionary();
        for (final String token : tokenizer) {
            dictionary.add(token);
        }
        // dictionary.sortByFrequencyAscending().each(System.out::println);
        return dictionary;
    }

    private void validateParameters(final Parameters parameters) {
        if (parameters.batchSize % parameters.numSkips != 0) {
            throw new IllegalArgumentException("Batch size % skip count must equal zero");
        }
        if (parameters.numSkips <= 2 * parameters.skipWindow) {
            throw new IllegalArgumentException("numSkips must be less than 2 * skipWindow");
        }
    }

}
