package kenny.ml.data.dictionary;

import org.eclipse.collections.api.list.MutableList;
import org.eclipse.collections.api.map.MutableMap;
import org.eclipse.collections.impl.factory.Lists;
import org.eclipse.collections.impl.factory.Maps;

/**
 * Created by kenny on 3/27/16.
 */
public class FrequencyAwareWordDictionary {

    private final MutableMap<String, Integer> dictionary = Maps.mutable.empty();

    public boolean has(final String word) {
        return dictionary.containsKey(word);
    }

    public int getFrequency(final String word) {
        if (!has(word)) {
            return 0;
        }
        return dictionary.get(word);
    }

    public void add(final String word) {
        if (!has(word)) {
            dictionary.put(word, 0);
        }
        dictionary.put(word, dictionary.get(word) + 1);
    }

    public int size() {
        return dictionary.size();
    }

    public void rejectRareWords(final int minFrequency) {
        Lists.mutable
                .ofAll(dictionary.entrySet())
                .reject(entry -> entry.getValue() > minFrequency)
                .each(entry -> dictionary.removeKey(entry.getKey()));
    }

    public MutableList<WordFrequency> sortByFrequencyDescending() {
        final MutableList<WordFrequency> list = getAsWordFrequencies();
        list.sort((lhs, rhs) -> -Integer.compare(lhs.frequency, rhs.frequency));
        return list;
    }

    public MutableList<WordFrequency> sortByFrequencyAscending() {
        final MutableList<WordFrequency> list = getAsWordFrequencies();
        list.sort((lhs, rhs) -> Integer.compare(lhs.frequency, rhs.frequency));
        return list;
    }

    private MutableList<WordFrequency> getAsWordFrequencies() {
        return Lists.mutable
                .ofAll(dictionary.entrySet())
                .collect(entry -> new WordFrequency(entry.getKey(), entry.getValue()));
    }

}
