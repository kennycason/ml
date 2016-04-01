package kenny.ml.bayes;

import kenny.ml.nlp.tokenizer.NGramTokenizer;
import kenny.ml.nlp.tokenizer.WhiteSpaceWordTokenizer;
import kenny.ml.nlp.tokenizer.WordTokenizer;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class NaiveBayesianFilter implements BayesianFilter {
    private WordTokenizer wordTokenizer = new WhiteSpaceWordTokenizer();
    private List<Function<String, String>> tokenNormalizers = new ArrayList<>();
    private Set<String> stopWords = new HashSet<>();
    private final Map<String, Word> words = new HashMap<>();

    private int nGrams = 1;
    private boolean commulativeNGrams = false;

    private List<String> tokenize(String text, int nGrams) {
        List<String> tokens = wordTokenizer.tokenize(text);

        tokens.removeAll(stopWords);

        for(Function<String, String> filter : tokenNormalizers) {
            tokens = tokens.stream().map(filter).collect(Collectors.toList());
        }
        final NGramTokenizer nGramTokenizer = new NGramTokenizer();
        if (commulativeNGrams) {
            return nGramTokenizer.tokenizeCommulative(tokens, nGrams);
        }
        return nGramTokenizer.tokenize(tokens, nGrams);
    }

    @Override
    public void trainNegative(String text) {
        final List<String> tokens = tokenize(text, nGrams);
        int spamTotal = 0; // How many words total

        // For every word token
        for (String token : tokens) {
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
        for (Word word : words.values()) {
            word.calcNegativeProb(spamTotal);
        }
    }

    @Override
    public void trainPositive(String text) {
        final List<String> tokens = tokenize(text, nGrams);
        int positiveTotal = 0; // How many words total

        // For every word token
        for (String token : tokens) {
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
        for (Word word : words.values()) {
            word.calcPositiveProb(positiveTotal, 2.0f);
        }
    }

    /**
     * This method is derived from Paul Graham:
     * http://www.paulgraham.com/spam.html
     */
    @Override
    public float analyze(String text) {
        final List<String> tokens = tokenize(text, nGrams);
        final List<Word> interesting = getInterestingWords(tokens, 15);

        // Apply Bayes' rule (via Graham)
        float pposproduct = 1.0f;
        float pnegproduct = 1.0f;
        // For every word, multiply Spam probabilities ("Pneg") together
        // (As well as 1 - Pneg)
        for (Word word : interesting) {
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
    private List<Word> getInterestingWords(List<String> tokens, int limit) {
        final List<Word> interesting = new ArrayList<>();

        // For every word in the String to be analyzed

        for (String token : tokens) {
            Word word;

            // If the String is in our HashMap get the word out
            if (words.containsKey(token)) {
                word = words.get(token);
                // Otherwise, make a new word with a Bad probability of 0.5;
            } else {
                word = new Word(token);
                word.setPNegative(0.4f);
            }

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

    // For every word, calculate the probability of matching positive category
    @Override
    public void finalizeTraining() {
        for(Word word : words.values()) {
            word.finalizeProb();
        }
    }

    public void setWordTokenizer(WordTokenizer wordTokenizer) {
        wordTokenizer = wordTokenizer;
    }

    public void setTokenNormalizers(List<Function<String, String>> tokenNormalizers) {
        tokenNormalizers = tokenNormalizers;
    }

    public void setnGrams(final int nGrams) {
        this.nGrams = nGrams;
    }

    public void setCommulativeNGrams(boolean commulativeNGrams) {
        this.commulativeNGrams = commulativeNGrams;
    }

    public void setStopWords(Set<String> stopWords) {
        stopWords = stopWords;
    }

    @Override
    public String toString() {
        final StringBuilder stringBuilder = new StringBuilder();
        for(String key : words.keySet()) {
            Word word = words.get(key);
            if (word != null) {
                stringBuilder.append(key + " pBad: " + word.getPNegative() + " pGood: " + word.getPPositive());
            }
        }
        return stringBuilder.toString();
    }

}
