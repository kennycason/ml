package kenny.ml.markov.text.ngram;


public interface ITokenizer {
	
	public NGram[] tokenize(String s);

}
