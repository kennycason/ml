package kenny.ml.nlp.tokenizer;

import java.util.List;

public interface WordTokenizer {
	
	List<String> tokenize(String sentence);

}
