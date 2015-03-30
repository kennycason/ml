package kenny.ml.nlp.fleschkincaid.lexer;


import kenny.ml.nlp.fleschkincaid.lexer.syllable.SyllableLexer;
import kenny.ml.nlp.fleschkincaid.lexer.word.Word;
import org.junit.Test;

/**
 * Created by kenny on 3/12/14.
 */
public class TestSyllableLexer {

    @Test
    public void countTest() {
        count("kawazaki");
        count("am");
        count("going");
        count("away");
        count("syllable");
        count("arrangement");
        count("strange");
    }

    private void count(String word) {
        System.out.println("counting syllables: " + word);
        SyllableLexer syllableLexer = new SyllableLexer();
        System.out.println(syllableLexer.count(new Word(word)));
    }

}
