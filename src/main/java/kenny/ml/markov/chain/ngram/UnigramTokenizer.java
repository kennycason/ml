package kenny.ml.markov.chain.ngram;

import java.util.StringTokenizer;

public class UnigramTokenizer implements ITokenizer {

	public NGram[] tokenize(String s) {
		StringTokenizer tokenizer = new StringTokenizer(clean(s), " ");
		int numTokens = tokenizer.countTokens();
		NGram[] unigrams = new NGram[numTokens];
		
		int i = 0;
		while(tokenizer.hasMoreTokens()) {
			unigrams[i] = new NGram(tokenizer.nextToken());
			i++;
		}
		return unigrams;
	}
	
	private String clean(String s) {
		return s.replace("/\"/", "");
	}
	
}
