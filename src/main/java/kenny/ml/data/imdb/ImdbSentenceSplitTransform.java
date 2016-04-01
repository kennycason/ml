package kenny.ml.data.imdb;

import kenny.ml.nlp.tokenizer.sentence.EnglishSentenceTokenizer;
import kenny.ml.nlp.tokenizer.sentence.SentenceTokenizer;
import org.eclipse.collections.api.RichIterable;
import org.eclipse.collections.api.list.MutableList;
import org.eclipse.collections.impl.list.mutable.FastList;

/**
 * Created by kenny on 3/28/16.
 */
public class ImdbSentenceSplitTransform {
    private static final SentenceTokenizer SENTENCE_TOKENIZER = new EnglishSentenceTokenizer();

    public static ImdbData transform(final ImdbData data) {
        final ImdbData imdbData = new ImdbData();
        imdbData.train.positive.addAllIterable(tokenizeSentences(data.train.positive));
        imdbData.train.negative.addAllIterable(tokenizeSentences(data.train.negative));
        imdbData.test.positive.addAllIterable(tokenizeSentences(data.test.positive));
        imdbData.test.negative.addAllIterable(tokenizeSentences(data.test.negative));

        return imdbData;
    }

    private static RichIterable<String> tokenizeSentences(final MutableList<String> paragraphs) {
        final MutableList<String> sentences = new FastList<>();
        paragraphs.each(paragraph -> {
            SENTENCE_TOKENIZER.tokenize(paragraph)
                              .stream().forEach(sentence1 -> sentences.add(sentence1.toString()));
        });
        return sentences;
    }


}
