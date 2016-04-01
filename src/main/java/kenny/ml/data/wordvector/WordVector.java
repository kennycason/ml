package kenny.ml.data.wordvector;

/**
 * Created by kenny on 3/28/16.
 */
public class WordVector {
    public final String word;
    public final double[] vector;

    public WordVector(final String word, final double[] vector) {
        this.word = word;
        this.vector = vector;
    }

}
