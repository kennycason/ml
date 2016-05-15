package kenny.ml.nlp.tokenizer;

import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.process.CoreLabelTokenFactory;
import edu.stanford.nlp.process.PTBTokenizer;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by kenny on 5/23/14.
 */
public class EnglishWordTokenizer implements WordTokenizer {

    @Override
    public List<String> tokenize(final String sentence) {
        final PTBTokenizer<CoreLabel> ptbt = new PTBTokenizer(new StringReader(sentence), new CoreLabelTokenFactory(), "");
        final List<String> words = new ArrayList<>();
        for(final CoreLabel word : ptbt.tokenize()) {
            words.add(word.word());
        }
        return words;
    }

}
