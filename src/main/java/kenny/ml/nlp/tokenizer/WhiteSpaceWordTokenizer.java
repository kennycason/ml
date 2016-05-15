package kenny.ml.nlp.tokenizer;


import org.eclipse.collections.impl.factory.Lists;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.apache.commons.lang3.StringUtils.isAnyBlank;

public class WhiteSpaceWordTokenizer implements WordTokenizer {

    @Override
    public List<String> tokenize(final String sentence) {
        if (isAnyBlank(sentence)) { return Collections.emptyList(); }
        return Lists.mutable.ofAll(
                Arrays.asList(
                    sentence.replaceAll("(?!\")\\p{Punct}", " ")    // remove punctuation
                            .replaceAll("!\\p{L}", " ")             // remove all non language characters
                            .split("\\s+")));
    }

}
