package kenny.ml.nlp.tokenizer;


import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class WhiteSpaceWordTokenizer implements WordTokenizer {

	@Override
	public List<String> tokenize(String sentence) {
        if(StringUtils.isAnyBlank(sentence)) { return Collections.emptyList(); }
        return Arrays.asList(
                sentence.replaceAll("(?!\")\\p{Punct}", " ")    // remove punctuation
                        .replaceAll("!\\p{L}", " ")             // remove all non language characters
                        .split("\\s+")
        );
	}

}
