package kenny.ml.bayes;

import kenny.ml.nlp.tokenizer.NGramTokenizer;
import kenny.ml.nlp.tokenizer.WhiteSpaceWordTokenizer;
import kenny.ml.nlp.tokenizer.WordTokenizer;

import java.util.*;
import java.util.Map.Entry;
import java.util.function.Function;
import java.util.stream.Collectors;

public class NaiveBayesianFilter implements BayesianFilter {
    private final Map<String, Word> words = new HashMap<>();

    private WordTokenizer wordTokenizer = new WhiteSpaceWordTokenizer();
    private List<Function<String, String>> tokenNormalizers = new ArrayList<>();
    private Set<String> stopWords = new HashSet<>();
    private int nGrams = 1;
    private boolean cumulativeNGrams;
    private int interestingNGrams = 15;

    private List<String> tokenize(final String text, final int nGrams) {
        List<String> tokens = wordTokenizer.tokenize(text);

        tokens.removeAll(stopWords);

        for(final Function<String, String> filter : tokenNormalizers) {
            tokens = tokens.stream().map(filter).collect(Collectors.toList());
        }
        final NGramTokenizer nGramTokenizer = new NGramTokenizer();
        if (cumulativeNGrams) {
            return nGramTokenizer.tokenizeCumulative(tokens, nGrams);
        }
        return nGramTokenizer.tokenize(tokens, nGrams);
    }

    @Override
    public void trainNegative(final String text) {
        final List<String> tokens = tokenize(text, nGrams);
        int spamTotal = 0; // How many words total

        // For every word token
        for (final String token : tokens) {
            spamTotal++;
            // If it exists in the HashMap already
            // Increment the count
            if (words.containsKey(token)) {
                final Word word = words.get(token);
                word.countNegative();
                // Otherwise it's a new word so add it
            } else {
                final Word word = new Word(token);
                word.countNegative();
                words.put(token, word);
            }
        }

        // Go through all the words and divide
        // by total words
        for (final Word word : words.values()) {
            word.calcNegativeProb(spamTotal);
        }
    }

    @Override
    public void trainPositive(final String text) {
        final List<String> tokens = tokenize(text, nGrams);
        int positiveTotal = 0; // How many words total

        // For every word token
        for (final String token : tokens) {
            positiveTotal++;
            // If it exists in the HashMap already
            // Increment the count
            if (words.containsKey(token)) {
                final Word word = words.get(token);
                word.countPositive();
                // Otherwise it's a new word so add it
            } else {
                final Word word = new Word(token);
                word.countPositive();
                words.put(token, word);
            }

        }

        // Go through all the words and divide
        // by total words
        for (final Word word : words.values()) {
            word.calcPositiveProb(positiveTotal, 2.0f);
        }
    }

    /**
     * This method is derived from Paul Graham:
     * http://www.paulgraham.com/spam.html
     */
    @Override
    public float analyze(final String text) {
        final List<String> tokens = tokenize(text, nGrams);
        final List<Word> interesting = getInterestingWords(tokens, interestingNGrams);

        // Apply Bayes' rule 
        float pposproduct = 1.0f;
        float pnegproduct = 1.0f;
        // For every word, multiply Spam probabilities ("Pneg") together
        // (As well as 1 - Pneg)
        for (final Word word : interesting) {
            pposproduct *= word.getPNegative();
            pnegproduct *= (1.0f - word.getPNegative());
        }
        interesting.clear();

        // Apply formula
        final float pPos = pnegproduct / (pposproduct + pnegproduct);
        return pPos;
    }

    /**
     * Create an arraylist of <limit> most "interesting" words
      * Words are most interesting based on how different their BAD
     * probability is from 0.5
     * @param tokens
     * @param limit
     * @return
     */
    private List<Word> getInterestingWords(final List<String> tokens, final int limit) {
        final List<Word> interesting = new ArrayList<>();

        // For every word in the String to be analyzed
        for (final String token : tokens) {
            final Word word = getWord(token);

            // If this list is empty, then add this word in!
            if (interesting.isEmpty()) {
                interesting.add(word);
                // Otherwise, add it in sorted order by interesting level
            } else {
                for (int j = 0; j < interesting.size(); j++) {
                    // For every word in the list already
                    final Word interestingWord = interesting.get(j);
                    // If it's the same word, don't bother
                    if (word.getWord().equals(interestingWord.getWord())) {
                        break;
                        // If it's more interesting stick it in the list
                    } else if (word.interesting() > interestingWord.interesting()) {
                        interesting.add(j, word);
                        break;
                        // If we get to the end, just tack it on there
                    } else if (j == interesting.size() - 1) {
                        interesting.add(word);
                    }
                }
            }
            // If the list is bigger than the limit, delete entries
            // at the end (the more "interesting" ones are at the
            // start of the list
            while (interesting.size() > limit) {
                interesting.remove(interesting.size() - 1);
            }

        }
        return interesting;
    }
    
    private Word getWord(final String token) {
        if (words.containsKey(token)) {
            return words.get(token);
        }
        final Word word = new Word(token);
        word.setPNegative(0.4f);
        return word;   
    }

    // For every word, calculate the probability of matching positive category
    @Override
    public void finalizeTraining() {
        words.values()
             .forEach(Word::finalizeProb);
    }

    public void setWordTokenizer(final WordTokenizer wordTokenizer) {
        this.wordTokenizer = wordTokenizer;
    }

    public void setTokenNormalizers(final List<Function<String, String>> tokenNormalizers) {
        this.tokenNormalizers = tokenNormalizers;
    }

    public void setnGrams(final int nGrams) {
        this.nGrams = nGrams;
    }

    public void setCumulativeNGrams(final boolean cumulativeNGrams) {
        this.cumulativeNGrams = cumulativeNGrams;
    }

    public void setStopWords(final Set<String> stopWords) {
        this.stopWords = stopWords;
    }

    public void setInterestingNGrams(int interestingNGrams) {
        this.interestingNGrams = interestingNGrams;
    }

    @Override
    public String toString() {
        final StringBuilder stringBuilder = new StringBuilder();
        for (final Entry<String, Word> stringWordEntry : words.entrySet()) {
            final Word word = stringWordEntry.getValue();
            if (word != null) {
                stringBuilder.append(stringWordEntry.getKey() + " pBad: " + word.getPNegative() + " pGood: " + word.getPPositive());
            }
        }
        return stringBuilder.toString();
    }

}
