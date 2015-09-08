package kenny.ml.decisiontree.data;

import org.apache.commons.lang3.mutable.MutableDouble;
import org.apache.commons.lang3.tuple.Pair;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by kenny
 */
public class DecisionTree {

    public DecisionTree() {
    }
    public Tree train(final String target, final List<Feature> features) {
        if(features.isEmpty()) { throw new IllegalArgumentException("There must be at least one feature."); }

        assertTargetIsBinary(target, features);
        return train(target, features, extractLabels(features));
    }

    private Tree train(final String target, final List<Feature> features, final List<String> labels) {
        final List<String> sortedLabels = stripEntropyFromList(calculateGains(target, labels, features));

        final Tree root = new Tree();
        root.label = sortedLabels.get(0);
        return train(target, root, sortedLabels, features);
    }

    private Tree train(final String target, final Tree root, final List<String> sortedLabels, final List<Feature> features) {
        if (sortedLabels.isEmpty()) { return root; }
        if (root.isLeaf()) { return root; }

        final String maxGainLabel = sortedLabels.remove(0);
        final Map<Enum, Map<Enum, MutableDouble>> jointCounts = jointFeatureCounts(maxGainLabel, target, features);

        for (Map.Entry<Enum, Map<Enum, MutableDouble>> jointCount : jointCounts.entrySet()) {
            final Enum labelValue = jointCount.getKey();

            // handle entropy zero cases, since enums may not show up in data size could be 1, handle these as trivial cases
            if (jointCount.getValue().size() == 1) {
                final Enum zeroEntropyValue = jointCount.getValue().keySet().iterator().next();
                final Tree leaf = new Tree();
                leaf.label = target;
                leaf.value = zeroEntropyValue;

                root.children.put(labelValue, leaf);
                continue;
            }
            // partition data and recursively apply algorithm
            root.children.put(labelValue, train(target, partitionFeatures(root.label, labelValue, features), sortedLabels));
        }

        return root;
    }

    private List<Feature> partitionFeatures(final String label, final Enum labelValue, final List<Feature> features) {
        final List<Feature> partitionedFeatures = new ArrayList<>();
        for(Feature feature : features) {
            if(feature.get(label) == labelValue) {
                partitionedFeatures.add(feature);
            }
        }
        return partitionedFeatures;
    }

    private static void assertTargetIsBinary(final String target, final List<Feature> features) {
        final Enum value = features.get(0).get(target);
        if(value.getClass().getEnumConstants().length != 2) {
            throw new IllegalArgumentException("Enum value must be a binary valued enum. i.e. consist of exactly two states.");
        }
    }

    /*
     * Return a list of information gains for each of the labels.
     * Sort from highest gain to lowest.
     */
    private static List<Pair<String, Double>> calculateGains(final String target, final List<String> labels, final List<Feature> features) {
        System.out.println("calculate information gains");
        final List<Pair<String, Double>> gains = new ArrayList<>();

        final double targetEntropy = entropy(probabilities(target, features));
        System.out.println("target entropy for [" + target + "] = " + targetEntropy);

        for(String label : labels) {
            if(label.equals(target)) { continue; }

            final double gain = targetEntropy - jointEntropy(label, target, features);
            System.out.println("information gain for [" + label + "/" + target + "] = " + gain);

            gains.add(Pair.of(label, gain));
        }
        Collections.sort(gains, GAIN_COMPARATOR);
        return gains;
    }

    private static Comparator<Pair<String, Double>> GAIN_COMPARATOR = (o1, o2) -> -Double.compare(o1.getValue(), o2.getValue());

    private static double entropy(final Map<Enum, MutableDouble> probabilities) {
        double entropy = 0.0;
        for (MutableDouble probability : probabilities.values()) {
            if(probability.doubleValue() == 0.0) { continue; }
            entropy += -probability.doubleValue() * log(probability.doubleValue(), 2);
        }
        return entropy;
    }

    private static double jointEntropy(final String label, final String target, final List<Feature> features) {
        final Map<Enum, MutableDouble> featureValueCounts = featureCounts(label, features);
        final double labelCounts = sum(featureValueCounts);

        final Map<Enum, Map<Enum, MutableDouble>> jointCounts = jointFeatureCounts(label, target, features);

        double entropy = 0.0;
        for(Map.Entry<Enum, MutableDouble> entry : featureValueCounts.entrySet()) {
            final Map<Enum, MutableDouble> featureTargetCounts = jointCounts.get(entry.getKey());
            if (featureTargetCounts.isEmpty()) { continue; } // zero entropy
            entropy += (entry.getValue().doubleValue() / labelCounts) * entropy(probabilities(featureTargetCounts));
        }
        return entropy;
    }

    private static Map<Enum, MutableDouble> probabilities(final String label, final List<Feature> features) {
        return probabilities(featureCounts(label, features));
    }

    private static Map<Enum, MutableDouble> probabilities(final Map<Enum, MutableDouble> featureValueCounts) {
        final double size = sum(featureValueCounts);

        final Map<Enum, MutableDouble> featureProbabilities = new HashMap<>();
        for (Map.Entry<Enum, MutableDouble> entry : featureValueCounts.entrySet()) {
            featureProbabilities.put(entry.getKey(), new MutableDouble());
            featureProbabilities.get(entry.getKey()).setValue(entry.getValue().doubleValue() / size);
        }
        return featureProbabilities;
    }

    private static Map<Enum, MutableDouble> featureCounts(final String label, final List<Feature> features) {
        final Map<Enum, MutableDouble> counts = new HashMap<>();
        for (Feature feature : features) {
            final Enum value = feature.get(label);
            if(!counts.containsKey(value)) {
                counts.put(value, new MutableDouble());
            }
            counts.get(value).increment();
        }
        return counts;
    }

    private static Map<Enum, Map<Enum, MutableDouble>> jointFeatureCounts(final String label, final String target, final List<Feature> features) {
        final Map<Enum, Map<Enum, MutableDouble>> jointCounts = new HashMap<>();
        for (Feature feature : features) {
            final Enum value = feature.get(label);
            if (!jointCounts.containsKey(value)) {
                jointCounts.put(value, new HashMap<>());
            }
            final Enum targetValue = feature.get(target);
            if (!jointCounts.get(value).containsKey(targetValue)) {
                jointCounts.get(value).put(targetValue, new MutableDouble());
            }
            jointCounts.get(value).get(targetValue).increment();
        }
        return jointCounts;
    }

    private static List<String> stripEntropyFromList(final List<Pair<String, Double>> labelEntropyPairs) {
        final List<String> labels = new ArrayList<>();
        for (Pair<String, Double> labelEntropyPair : labelEntropyPairs) {
            labels.add(labelEntropyPair.getKey());
        }
        return labels;
    }

    private static double sum(final Map<Enum, ? extends Number> map) {
        final MutableDouble size = new MutableDouble();
        map.values().forEach(count -> size.add(count.doubleValue()));
        return size.doubleValue();
    }

    /*
     * exhaustive search, consider constructing decision tree with this knowledge
     */
    private static List<String> extractLabels(final List<Feature> features) {
        final Set<String> labels = new HashSet<>();
        for (Feature feature : features) {
            labels.addAll(feature.getLabels());
        }
        return new ArrayList<>(labels);
    }

    private static double log(double x, int base) {
        return Math.log(x) / Math.log(base);
    }

}
