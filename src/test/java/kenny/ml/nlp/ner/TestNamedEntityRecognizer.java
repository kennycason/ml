package kenny.ml.nlp.ner;

import opennlp.tools.util.Span;
import org.eclipse.collections.api.list.ListIterable;
import org.junit.Test;

/**
 * Created by kenny on 5/9/16.
 */
public class TestNamedEntityRecognizer {
    private final NamedEntityRecognizer namedEntityRecognizer = new NamedEntityRecognizer();

    @Test
    public void simpleTests() {
        final ListIterable<Span> spans = namedEntityRecognizer.tokenize(new String[] {"I", "am", "Kenny"});
        final ListIterable<Span> spans2 = namedEntityRecognizer.tokenize(new String[] {"I", "am", "traveling", "Europe", "tomorrow"});
        final ListIterable<Span> spans3 = namedEntityRecognizer.tokenize(new String[] {"Nintendo", "of", "America", "used", "to", "be", "my", "favorite", "company"});

        System.out.println("tests");
    }

}
