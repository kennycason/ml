package kenny.ml.markov.chain.text;


import kenny.ml.markov.chain.ngram.BigramTokenizer;
import kenny.ml.markov.chain.MarkovChains;
import kenny.ml.markov.chain.ngram.Sentence;
import kenny.ml.markov.chain.ngram.UnigramTokenizer;

public class TidePodTweets {

	public static void main(String[] args) {
		System.out.println("Unigram:\n--------------------");
		MarkovChains mc = new MarkovChains("kenny/ml/markov/text/tide_pods.txt", new UnigramTokenizer());
		for(Sentence gen : mc.generate(30, 10)) {
			System.out.println(gen);
		}
		
		System.out.println("Bigram:\n--------------------");
		mc = new MarkovChains("kenny/ml/markov/text/tide_pods.txt", new BigramTokenizer());
		for(Sentence gen : mc.generate(30, 10)) {
			System.out.println(gen);
		}
	
	}
	
}
