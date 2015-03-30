package kenny.ml.markov.text.ngram;




public class BigramTokenizer implements ITokenizer {

	public NGram[] tokenize(String s) {
		UnigramTokenizer tokenizer = new UnigramTokenizer();
		NGram[] unigrams = tokenizer.tokenize(s);
		NGram[] bigrams = new NGram[unigrams.length / 2];
		if(bigrams.length == 0) {
			return bigrams;
		}
		for(int i = 0; i < unigrams.length - 1; i += 2) {
			bigrams[i / 2] = new NGram(unigrams[i].gram() + " " + unigrams[i + 1].gram());
		}
		if(unigrams.length % 2 != 0) {
			bigrams[bigrams.length - 1] = new NGram(unigrams[unigrams.length - 1].gram());
		}
		return bigrams;
	}
	
}
