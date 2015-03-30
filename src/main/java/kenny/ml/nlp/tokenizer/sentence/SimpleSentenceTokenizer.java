package kenny.ml.nlp.tokenizer.sentence;


import kenny.ml.nlp.tokenizer.WhiteSpaceWordTokenizer;
import kenny.ml.nlp.tokenizer.WordTokenizer;

import java.text.BreakIterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;

/**
 * Created by kenny on 7/10/14.
 */
public class SimpleSentenceTokenizer  implements SentenceTokenizer {

    private final Locale locale;

    private final WordTokenizer wordTokenizer;

    public SimpleSentenceTokenizer() {
        this(Locale.US, new WhiteSpaceWordTokenizer());
    }

    public SimpleSentenceTokenizer(final Locale locale, final WordTokenizer wordTokenizer) {
        this.locale = locale;
        this.wordTokenizer = wordTokenizer;
    }

    public List<Sentence> tokenize(String text) {
        List<Sentence> sentences = new LinkedList<>();
        BreakIterator border = BreakIterator.getSentenceInstance(locale);
        border.setText(text);
        int start = border.first();
        //iterate, creating sentences out of all the Strings between the given boundaries
        for (int end = border.next(); end != BreakIterator.DONE; start = end, end = border.next()) {
            sentences.add(new Sentence(this.wordTokenizer.tokenize(text.substring(start,end))));
        }

        return sentences;
    }

}
