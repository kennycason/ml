package kenny.ml.markov.text;


import kenny.ml.markov.text.ngram.ITokenizer;
import kenny.ml.markov.text.ngram.NGram;
import org.apache.commons.io.IOUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

public class MarkovChains {

	private static final int MAX_SIZE = 15;

	private Map<NGram, List<NGram>> stats;

	private List<NGram> starts;

	private Set<NGram> terminals;

	private Set<Sentence> all;

	private ITokenizer tokenizer;

	private String file;

	private Random rand;

	public MarkovChains(String file, ITokenizer tokenizer) {
		stats = new HashMap<>();
		starts = new LinkedList<>();
		terminals = new HashSet<>();
		all = new HashSet<>();

		rand = new Random();
		this.file = file;
		this.tokenizer = tokenizer;
		load();
	}

	public List<Sentence> generate(int num, int minSize) {
		List<Sentence> sentences = new LinkedList<>();
		while(sentences.size() < num) {
			List<NGram> ngrams = new LinkedList<>();
			NGram ngram = choose(starts);
			ngrams.add(ngram);
			for(int j = 0; j < MAX_SIZE || j >= minSize; j++) {
				ngram = choose(stats.get(ngram));
				if(ngram == null) { break; }
				ngrams.add(ngram);
				if(terminals.contains(ngram) && j <= minSize) { break; }
			}

			Sentence sentence = new Sentence(ngrams.toArray(new NGram[ngrams.size()]));
			if(!all.contains(sentence)) {
				sentences.add(sentence);
			}
		}
		return sentences;
	}

	private NGram choose(List<NGram> words) {
		if(words == null) {
			return null;
		}
		return words.get(rand.nextInt(words.size()));
	}

	private void load() {
		// load sentences
		try {
			// load/build sentences
            final List<String> lines = IOUtils.readLines(Thread.currentThread().getContextClassLoader().getResourceAsStream(file));
			for(String line : lines) {
				Sentence sentence = new Sentence(tokenizer.tokenize(line));
				all.add(sentence);
			}
		} catch(Exception e) {
			e.printStackTrace();
		}

		// build stats
        // this approach, while less memory efficient than storing "word" -> [word frequencies] pairs it stores each
        // occurrence of a word that follows. so if the word "foo" is followed by "bar" 3 times and "frog" 1 time
        // it will be stored as "bar" -> ["bar" "bar" "bar" "frog"], thus a random drawing from the bag of words will
        // take into account frequencies.
		for(Sentence sentence : all) {
			if(sentence.grams().length == 0) { continue; }

			starts.add(sentence.grams()[0]);
			terminals.add(sentence.grams()[sentence.grams().length - 1]);

			NGram[] ngrams = sentence.grams();
			for(int i = 0; i < ngrams.length - 1; i++) {
				if(!stats.containsKey(ngrams[i])) {
					stats.put(ngrams[i], new ArrayList<NGram>());
				} 
				stats.get(ngrams[i]).add(ngrams[i + 1]);
			}
		}
	}

}
