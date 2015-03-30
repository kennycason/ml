package kenny.ml.nlp.tokenizer.sentence;

import ch.lambdaj.Lambda;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by kenny on 6/9/14.
 */
public class Sentence {

    private List<String> words = new LinkedList<>();

    public Sentence(List<String> words) {
        this.words = words;
    }

    public List<String> getWords() {
        return words;
    }

    @Override
    public String toString() {
        return Lambda.join(words, " ");
    }

}
