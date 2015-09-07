package kenny.ml.markov.chain.ngram;


import java.util.Arrays;

public class Sentence {

	private NGram[] ngrams;
	
	public Sentence(NGram[] ngrams) {
        this.ngrams = new NGram[ngrams.length];
		System.arraycopy(ngrams, 0, this.ngrams, 0, ngrams.length);
	}
	
	public NGram[] grams() {
		return ngrams;
	}

	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		for(NGram ngram : ngrams) {
			sb.append(ngram + " ");
		}
		return sb.toString();
	}

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Sentence)) return false;

        Sentence sentence = (Sentence) o;

        if (!Arrays.equals(ngrams, sentence.ngrams)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(ngrams);
    }
}
