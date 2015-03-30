package kenny.ml.nlp.tokenizer;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kenny on 6/11/14.
 */
public class NGramTokenizer {

    public List<String> tokenize(final String text, int n, final WordTokenizer wordTokenizer) {
        final List<String> tokensList = wordTokenizer.tokenize(text);
        final String[] tokens = tokensList.toArray(new String[tokensList.size()]);

        if(n <= 1) { return tokensList; }

        final List<String> ngrams = new ArrayList<String>();
        for (int i = 0; i < tokens.length - n + 1; i++)
            ngrams.add(concat(tokens, i, i + n));
        return ngrams;
    }

    public List<String> tokenize(List<String> tokensList, int n) {
        final String[] tokens = tokensList.toArray(new String[tokensList.size()]);

        if(n <= 1) { return tokensList; }
        final List<String> ngrams = new ArrayList<String>();
        for (int i = 0; i < tokens.length - n + 1; i++)
            ngrams.add(concat(tokens, i, i + n));
        return ngrams;
    }

    private static String concat(String[] tokens, int start, int end) {
        StringBuilder sb = new StringBuilder();
        for (int i = start; i < end; i++)
            sb.append((i > start ? " " : "") + tokens[i]);
        return sb.toString();
    }

}
