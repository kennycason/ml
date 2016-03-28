package kenny.ml.word2vec;

/**
 * Created by kenny on 3/27/16.
 */
public class Parameters {
    public String fileName;
    public int batchSize = 8;
    public int numSkips = 2;
    public int skipWindow = 1;
    public int vocabularySize = 50_000;

    public Parameters setBatchSize(int batchSize) {
        this.batchSize = batchSize;
        return this;
    }

    public Parameters setFileName(String fileName) {
        this.fileName = fileName;
        return this;
    }

    public Parameters setNumSkips(int numSkips) {
        this.numSkips = numSkips;
        return this;
    }

    public Parameters setSkipWindow(int skipWindow) {
        this.skipWindow = skipWindow;
        return this;
    }

    public Parameters setVocabularySize(int vocabularySize) {
        this.vocabularySize = vocabularySize;
        return this;
    }

}
