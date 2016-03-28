package kenny.ml.nlp.tokenizer;

import org.apache.commons.io.IOUtils;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by kenny on 3/27/16.
 */
public class TestLazyDelimeterBackedTokenizer {

    @Test
    public void basic() {
        final LazyDelimeterBackedTokenizer tokenizer = new LazyDelimeterBackedTokenizer(' ',  IOUtils.toInputStream("i'm some text"));
        assertEquals("i'm", tokenizer.next());
        assertEquals("some", tokenizer.next());
        assertEquals("text", tokenizer.next());
        assertEquals(null, tokenizer.next());
    }
}
