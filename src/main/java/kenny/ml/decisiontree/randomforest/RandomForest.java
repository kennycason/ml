package kenny.ml.decisiontree.randomforest;

import kenny.ml.decisiontree.DecisionTree;
import kenny.ml.decisiontree.FeatureSet;
import kenny.ml.decisiontree.Tree;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

/**
 * Created by kenny
 */
public class RandomForest {

    private static final Random RANDOM = new Random();

    private static final DecisionTree DECISION_TREE = new DecisionTree();

    public double featureSampleRate = 0.5;

    public double labelSampleRate = 0.5;

    public int numTrees = 10;

    public Forest train(final String target, final List<FeatureSet> features) {
        if(features.isEmpty()) { throw new IllegalArgumentException("There must be at least one feature."); }

        final Forest forest = new Forest();
        forest.trees.addAll(buildTrees(target, features));

        return forest;
    }

    private List<Tree> buildTrees(final String target, final List<FeatureSet> features) {
        final List<Tree> trees = new ArrayList<>(numTrees);

        for(int i = 0; i < numTrees; i++) {
            trees.add(DECISION_TREE.train(target, sampleLabels(sampleFeatures(features), target)));
        }

        return trees;
    }

    /*
     * select a random portion of features
     * first method called, need to clone
     */
    private List<FeatureSet> sampleFeatures(final List<FeatureSet> features) {
        final List<FeatureSet> sample = new ArrayList<>();
        for(FeatureSet featureSet : features) {
            if(RANDOM.nextDouble() >= featureSampleRate) {
                sample.add(clone(featureSet));
            }
        }
        return sample;
    }

    /*
     * select a random portion of labels
     * method functions on feature clones, no need to clone
     */
    private List<FeatureSet> sampleLabels(final List<FeatureSet> features, final String target) {
        for(String label : extractLabels(features)) {
            if(target.equals(label)) { continue; }

            if (RANDOM.nextDouble() >= labelSampleRate) {
                for(FeatureSet feature : features) {
                    // must have at least one feature and the target
                    if(feature.features.size() <= 2) { return features; }

                    feature.features.remove(label);
                }
            }
        }
        return features;
    }

    private static List<String> extractLabels(final List<FeatureSet> features) {
        final Set<String> labels = new HashSet<>();
        for (FeatureSet feature : features) {
            labels.addAll(feature.getLabels());
        }
        return new ArrayList<>(labels);
    }

    private static FeatureSet clone(FeatureSet featureSet) {
        final FeatureSet clone = new FeatureSet();
        clone.features.putAll(featureSet.features);
        return clone;
    }

}
