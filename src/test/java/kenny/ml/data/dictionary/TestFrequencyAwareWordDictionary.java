package kenny.ml.data.dictionary;

import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

/**
 * Created by kenny on 3/27/16.
 */
public class TestFrequencyAwareWordDictionary {

    @Test
    public void basic() {
        final FrequencyAwareWordDictionary dictionary = new FrequencyAwareWordDictionary();
        dictionary.add("frog");
        dictionary.add("dog");
        dictionary.add("dog");
        dictionary.add("cat");
        dictionary.add("cat");
        dictionary.add("cat");

        assertEquals(3, dictionary.size());
        final List<WordFrequency> wordFrequencies = dictionary.sortByFrequencyDescending();

        assertEquals("cat", wordFrequencies.get(0).word);
        assertEquals(3, wordFrequencies.get(0).frequency);

        assertEquals("dog", wordFrequencies.get(1).word);
        assertEquals(2, wordFrequencies.get(1).frequency);

        assertEquals("frog", wordFrequencies.get(2).word);
        assertEquals(1, wordFrequencies.get(2).frequency);

        dictionary.rejectRareWords(1);
        assertEquals(2, dictionary.size());
        assertFalse(dictionary.has("frog"));
    }
}
