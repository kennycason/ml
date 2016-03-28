package kenny.ml.data.dictionary;

/**
 * Created by kenny on 3/27/16.
 */
public class WordFrequency {
    public final String word;
    public final int frequency;

    public WordFrequency(String word, int frequency) {
        this.word = word;
        this.frequency = frequency;
    }

    @Override
    public String toString() {
        return "WordFrequency{" +
                "frequency=" + frequency +
                ", word='" + word + '\'' +
                '}';
    }
}
