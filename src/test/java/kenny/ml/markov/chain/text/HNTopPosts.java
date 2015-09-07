package kenny.ml.markov.chain.text;


import kenny.ml.markov.chain.ngram.BigramTokenizer;
import kenny.ml.markov.chain.MarkovChains;
import kenny.ml.markov.chain.ngram.Sentence;
import kenny.ml.markov.chain.ngram.UnigramTokenizer;

/**
 * Data from: http://pastebin.com/raw.php?i=nqpsnTtW (Grant Slatton)
 * @author kenny
 *
 */
public class HNTopPosts {

	public static void main(String[] args) {
		System.out.println("Unigram:\n--------------------");
		MarkovChains mc = new MarkovChains("kenny/ml/markov/text/hn_top.txt", new UnigramTokenizer());
		for(Sentence gen : mc.generate(30, 5)) {
			System.out.println(gen);
		}
		
		System.out.println("Bigram:\n--------------------");
		mc = new MarkovChains("kenny/ml/markov/text/hn_top.txt", new BigramTokenizer());
		for(Sentence gen : mc.generate(30, 5)) {
			System.out.println(gen);
		}
		
	}
	
}
