package kenny.ml.word2vec;

import kenny.ml.data.dictionary.WordFrequency;

/**
 * Created by kenny on 3/27/16.
 */
public class BatchAndLabels {
    public WordFrequency[] batch;
    public WordFrequency[] labels;

    public BatchAndLabels(WordFrequency[] batch, WordFrequency[] labels) {
        this.batch = batch;
        this.labels = labels;
    }
}
