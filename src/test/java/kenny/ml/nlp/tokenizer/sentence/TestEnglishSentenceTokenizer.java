package kenny.ml.nlp.tokenizer.sentence;

import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * Created by kenny on 6/9/14.
 */
public class TestEnglishSentenceTokenizer {

    private static final SentenceTokenizer SENTENCE_TOKENIZER = new EnglishSentenceTokenizer();

    @Test
    public void test() {
        List<Sentence> sentences = SENTENCE_TOKENIZER.tokenize("I am a frog. You are a toad");
        assertEquals(2, sentences.size());
        assertEquals("I am a frog .", sentences.get(0).toString());
        assertEquals("You are a toad", sentences.get(1).toString());
    }

}
