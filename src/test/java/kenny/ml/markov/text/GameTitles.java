package kenny.ml.markov.text;


import kenny.ml.markov.text.ngram.BigramTokenizer;
import kenny.ml.markov.text.ngram.UnigramTokenizer;

/**
 * full list of games: http://www.gamerevolution.com/game/all/all/long_name/asc
 * @author kenny
 *
 */
public class GameTitles {

	public static void main(String[] args) {		
		System.out.println("Unigram:\n--------------------");
		MarkovChains mc = new MarkovChains("kenny/ml/markov/text/game_titles.txt", new UnigramTokenizer());
		for(Sentence gen : mc.generate(30, 5)) {
			System.out.println(gen);
		}
		
		System.out.println("Bigram:\n--------------------");
		mc = new MarkovChains("kenny/ml/markov/text/game_titles.txt", new BigramTokenizer());

		for(Sentence gen : mc.generate(50, 5)) {
			System.out.println(gen);
		}
	
	}
	
}
