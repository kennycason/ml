package kenny.ml.word2vec.rbm;

import kenny.ml.data.dictionary.FrequencyAwareWordDictionary;
import kenny.ml.nlp.tokenizer.LazyDelimeterBackedTokenizer;
import kenny.ml.word2vec.skipgram.Parameters;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

/**
 * Created by kenny on 3/27/16.
 */
public class RBMWord2Vec {

    private FrequencyAwareWordDictionary readData(final Parameters parameters) throws FileNotFoundException {
        final LazyDelimeterBackedTokenizer tokenizer = new LazyDelimeterBackedTokenizer(' ', new FileInputStream(new File(parameters.fileName)));
        final FrequencyAwareWordDictionary dictionary = new FrequencyAwareWordDictionary();
        for (final String token : tokenizer) {
            dictionary.add(token);
        }
        // dictionary.sortByFrequencyAscending().each(System.out::println);
        return dictionary;
    }
}
