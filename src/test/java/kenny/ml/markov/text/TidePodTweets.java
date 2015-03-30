package kenny.ml.markov.text;


import kenny.ml.markov.text.ngram.BigramTokenizer;
import kenny.ml.markov.text.ngram.UnigramTokenizer;

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
