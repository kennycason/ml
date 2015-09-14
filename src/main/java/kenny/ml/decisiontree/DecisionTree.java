package kenny.ml.decisiontree;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.mutable.MutableDouble;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.log4j.Logger;

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

    private static final Logger LOGGER = Logger.getLogger(DecisionTree.class);

    public DecisionTree() {}

    public Tree train(final String target, final List<Feature> features) {
        if(features.isEmpty()) { throw new IllegalArgumentException("There must be at least one feature."); }

        assertTargetIsBinary(target, features);
        return train(target, features, extractLabels(features));
    }

    private Tree train(final String target, final List<Feature> features, final List<String> labels) {
        final List<Pair<String, Double>> gains = calculateGains(target, labels, features);

        final Tree root = new Tree();
        root.label = gains.get(0).getKey();
        return train(target, root, gains, features);
    }

    private Tree train(final String target, final Tree root, List<Pair<String, Double>> gains, final List<Feature> features) {
        if (root.isLeaf()) { return root; }

        // if there is no optimum path, pick the first
        if(allGainsAreZero(gains)) {
            final Tree leaf = new Tree();
            leaf.label = target;
            leaf.value = features.get(0).get(target);

            root.children.put(features.get(0).get(root.label), leaf);
            return root;
        }

        final List<String> sortedLabels = stripEntropyFromList(gains);

        final String maxGainLabel = sortedLabels.get(0);
        final Map<String, Map<String, MutableDouble>> jointCounts = jointFeatureCounts(maxGainLabel, target, features);

        for (Map.Entry<String, Map<String, MutableDouble>> jointCount : jointCounts.entrySet()) {
            final String labelValue = jointCount.getKey();

            // handle entropy zero cases, since Strings may not show up in data size could be 1, handle these as trivial cases
            if (jointCount.getValue().size() == 1) {
                final String zeroEntropyValue = jointCount.getValue().keySet().iterator().next();
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

    /*
     * handle special case where there is no optimum path.
     *
     * e.g.
     * {Play Golf=YES, Temp=MILD, Outlook=SUNNY}
     * {Play Golf=NO, Temp=MILD, Outlook=SUNNY}
     */
    private boolean allGainsAreZero(final List<Pair<String, Double>> gains) {
        for(Pair<String, Double> gain : gains) {
            if(gain.getValue() != 0.0) { return false; }
        }
        return true;
    }

    private List<Feature> partitionFeatures(final String label, final String labelValue, final List<Feature> features) {
        final List<Feature> partitionedFeatures = new ArrayList<>();
        for(Feature feature : features) {
            if(StringUtils.equals(feature.get(label), labelValue)) {
                partitionedFeatures.add(feature);
            }
        }
        return partitionedFeatures;
    }

    private static void assertTargetIsBinary(final String target, final List<Feature> features) {
        final Set<String> values = new HashSet<>();
        features.forEach(f -> values.add(f.get(target)));
        if(values.size() != 2) {
            throw new IllegalArgumentException("Target value must be a binary valued String. i.e. consist of exactly two states.");
        }
    }

    /*
     * Return a list of information gains for each of the labels.
     * Sort from highest gain to lowest.
     */
    private static List<Pair<String, Double>> calculateGains(final String target, final List<String> labels, final List<Feature> features) {
        LOGGER.debug("calculate information gains");
        final List<Pair<String, Double>> gains = new ArrayList<>();

        final double targetEntropy = entropy(probabilities(target, features));
        LOGGER.debug("target entropy for [" + target + "] = " + targetEntropy);

        for(String label : labels) {
            if(label.equals(target)) { continue; }

            final double gain = targetEntropy - jointEntropy(label, target, features);
            LOGGER.debug("information gain for [" + label + "/" + target + "] = " + gain);

            gains.add(Pair.of(label, gain));
        }
        Collections.sort(gains, GAIN_COMPARATOR);
        return gains;
    }

    private static Comparator<Pair<String, Double>> GAIN_COMPARATOR = (o1, o2) -> -Double.compare(o1.getValue(), o2.getValue());

    private static double entropy(final Map<String, MutableDouble> probabilities) {
        double entropy = 0.0;
        for (MutableDouble probability : probabilities.values()) {
            if(probability.doubleValue() == 0.0) { continue; }

            entropy += -probability.doubleValue() * log(probability.doubleValue(), 2);
        }
        return entropy;
    }

    private static double jointEntropy(final String label, final String target, final List<Feature> features) {
        final Map<String, MutableDouble> featureValueCounts = featureCounts(label, features);
        final double labelCounts = sum(featureValueCounts);

        final Map<String, Map<String, MutableDouble>> jointCounts = jointFeatureCounts(label, target, features);

        double entropy = 0.0;
        for(Map.Entry<String, MutableDouble> entry : featureValueCounts.entrySet()) {
            final Map<String, MutableDouble> featureTargetCounts = jointCounts.get(entry.getKey());
            if (featureTargetCounts.isEmpty()) { continue; } // zero entropy

            entropy += (entry.getValue().doubleValue() / labelCounts) * entropy(probabilities(featureTargetCounts));
        }
        return entropy;
    }

    private static Map<String, MutableDouble> probabilities(final String label, final List<Feature> features) {
        return probabilities(featureCounts(label, features));
    }

    private static Map<String, MutableDouble> probabilities(final Map<String, MutableDouble> featureValueCounts) {
        final double size = sum(featureValueCounts);

        final Map<String, MutableDouble> featureProbabilities = new HashMap<>();
        for (Map.Entry<String, MutableDouble> entry : featureValueCounts.entrySet()) {
            featureProbabilities.put(entry.getKey(), new MutableDouble());
            featureProbabilities.get(entry.getKey()).setValue(entry.getValue().doubleValue() / size);
        }
        return featureProbabilities;
    }

    private static Map<String, MutableDouble> featureCounts(final String label, final List<Feature> features) {
        final Map<String, MutableDouble> counts = new HashMap<>();
        for (Feature feature : features) {
            final String value = feature.get(label);
            if(!counts.containsKey(value)) {
                counts.put(value, new MutableDouble());
            }
            counts.get(value).increment();
        }
        return counts;
    }

    private static Map<String, Map<String, MutableDouble>> jointFeatureCounts(final String label, final String target, final List<Feature> features) {
        final Map<String, Map<String, MutableDouble>> jointCounts = new HashMap<>();
        for (Feature feature : features) {
            final String value = feature.get(label);
            if (!jointCounts.containsKey(value)) {
                jointCounts.put(value, new HashMap<>());
            }
            final String targetValue = feature.get(target);
            if (!jointCounts.get(value).containsKey(targetValue)) {
                jointCounts.get(value).put(targetValue, new MutableDouble());
            }
            jointCounts.get(value).get(targetValue).increment();
        }
        return jointCounts;
    }

    private static List<String> stripEntropyFromList(final List<Pair<String, Double>> labelEntropyPairs) {
        final List<String> labels = new ArrayList<>();
        labelEntropyPairs.forEach(p -> labels.add(p.getKey()));
        return labels;
    }

    private static double sum(final Map<String, ? extends Number> map) {
        final MutableDouble size = new MutableDouble();
        map.values().forEach(count -> size.add(count.doubleValue()));
        return size.doubleValue();
    }

    /*
     * exhaustive search, consider constructing decision tree with this knowledge
     */
    private static List<String> extractLabels(final List<Feature> features) {
        final Set<String> labels = new HashSet<>();
        features.forEach(f -> labels.addAll(f.getLabels()));
        return new ArrayList<>(labels);
    }

    private static double log(double x, int base) {
        return Math.log(x) / Math.log(base);
    }

}
