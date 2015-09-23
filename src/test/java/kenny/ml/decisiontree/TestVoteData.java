package kenny.ml.decisiontree;

import kenny.ml.decisiontree.randomforest.Forest;
import kenny.ml.decisiontree.randomforest.RandomForest;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by kenny
 */
public class TestVoteData {

    private static final String[] FEATURES = new String[] {
            "handicapped infants",
            "water project cost sharing",
            "adoption of the budget resolution",
            "physician fee freeze",
            "el salvador aid",
            "religious groups in schools",
            "anti satellite test ban",
            "aid to nicaraguan contras",
            "mx missile",
            "immigration",
            "synfuels corporation cutback",
            "education spending",
            "superfund right to sue",
            "crime",
            "duty free exports",
            "export administration act south africa",
            "party"
    };

    private static final String TARGET = "party";

    @Test
    public void partyTest() throws IOException {
        final List<FeatureSet> train = loadFeatures("kenny/ml/decisiontree/vote.data");
        final List<FeatureSet> test = loadFeatures("kenny/ml/decisiontree/vote.test");

        testDecisionTree(train, test);
        testRandomForest(train, test);
    }

    private void testDecisionTree(final List<FeatureSet> train, final List<FeatureSet> test) {
        final DecisionTree decisionTree = new DecisionTree();
        final Tree tree = decisionTree.train(TARGET, train);

        int correct = 0;
        for(FeatureSet featureSet : test) {
            final String vote = tree.walk(featureSet);
            if(StringUtils.equals(vote, featureSet.get(TARGET))) {
                correct++;
            }
        }
        System.out.println("Decision Tree: " + ((double) correct / test.size() * 100.0) + "% correct");
    }

    private void testRandomForest(final List<FeatureSet> train, final List<FeatureSet> test) {
        final RandomForest randomForest = new RandomForest();
        randomForest.numTrees = 25;
        final Forest forest = randomForest.train(TARGET, train);

        int correct = 0;
        for(FeatureSet featureSet : test) {
            final String vote = forest.walk(featureSet);
            if(StringUtils.equals(vote, featureSet.get(TARGET))) {
                correct++;
            }
        }
        System.out.println("Random Forest: " + ((double) correct / test.size() * 100.0) + "% correct");
    }

    private List<FeatureSet> loadFeatures(final String resource) throws IOException {
        final List<FeatureSet> features = new ArrayList<>();
        final List<String> lines = IOUtils.readLines(Thread.currentThread().getContextClassLoader().getResourceAsStream(resource));
        for(String line : lines) {
            final FeatureSet feature = new FeatureSet();
            for (int i = 0; i < FEATURES.length; i++) {
                final String[] attributes = line.split(",");
                feature.set(FEATURES[i], attributes[i]);
            }
            features.add(feature);
        }
        return features;
    }

}
