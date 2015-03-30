package kenny.ml.nlp.tokenizer.sentence;

import java.util.List;

/**
 * Created by kenny on 6/9/14.
 */
public interface SentenceTokenizer {
    List<Sentence> tokenize(String text);
}
