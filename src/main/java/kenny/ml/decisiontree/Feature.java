package kenny.ml.decisiontree;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Created by kenny
 */
public class Feature {

    public final Map<String, String> features = new HashMap<>();

    public String get(final String label) {
        return features.get(label);
    }

    public Set<String> getLabels() {
        return features.keySet();
    }

    public Feature set(final String label, final String value) {
        features.put(label, value);
        return this;
    }

}
