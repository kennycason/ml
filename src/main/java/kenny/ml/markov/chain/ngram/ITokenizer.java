package kenny.ml.markov.chain.ngram;


public interface ITokenizer {
	
	public NGram[] tokenize(String s);

}
