package kenny.ml.decisiontree;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Created by kenny
 */
public class Feature {

    public final Map<String, Enum> features = new HashMap<>();

    public Enum get(final String label) {
        return features.get(label);
    }

    public Set<String> getLabels() {
        return features.keySet();
    }

    public Feature set(final String label, final Enum value) {
        features.put(label, value);
        return this;
    }

}
