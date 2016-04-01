package kenny.ml.nlp.tokenizer.sentence;

import ch.lambdaj.Lambda;
import ch.lambdaj.function.convert.Converter;
import edu.stanford.nlp.ling.HasWord;
import edu.stanford.nlp.process.DocumentPreprocessor;
import org.eclipse.collections.impl.list.mutable.FastList;

import java.io.StringReader;
import java.util.List;

/**
 * Created by kenny on 6/9/14.
 */
public class EnglishSentenceTokenizer implements SentenceTokenizer {

    @Override
    public List<Sentence> tokenize(final String text) {
        final StringReader stringReader = new StringReader(text);
        final DocumentPreprocessor dp = new DocumentPreprocessor(stringReader);

        final List<Sentence> sentences = new FastList<>();
        for (List<HasWord> hasWords : dp) {
            final Sentence sentence = new Sentence(Lambda.convert(hasWords, HAS_WORD_TO_STRING_CONVERTER));
            sentences.add(sentence);
        }
        return sentences;
    }

    private static final Converter<HasWord, String> HAS_WORD_TO_STRING_CONVERTER = HasWord::word;

}
