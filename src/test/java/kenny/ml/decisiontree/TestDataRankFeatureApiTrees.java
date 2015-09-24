package kenny.ml.decisiontree;

import com.datarank.api.DataRank;
import com.datarank.api.config.DataRankConfiguration;
import com.datarank.api.request.filters.FeatureMode;
import com.datarank.api.request.filters.Limit;
import com.datarank.api.response.envelopes.CommentFeaturesEnvelope;
import kenny.ml.decisiontree.randomforest.Forest;
import kenny.ml.decisiontree.randomforest.RandomForest;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.junit.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by kenny
 */
public class TestDataRankFeatureApiTrees {

    private final String target = "THEME PURCHASE INTENT";

    @Test
    public void testRoot() throws IOException {
        // initialize datarank SDK
        final File apiKeyFile = new File(System.getProperty("user.home") + "/.datarank-api-key");
        final DataRank dataRank = new DataRank(new DataRankConfiguration(IOUtils.toString(new FileInputStream(apiKeyFile)).trim()));
        // perform api call to return 1000 categorical features
        final CommentFeaturesEnvelope commentFeatures = dataRank.commentsFeatures("tide-pods", new Limit(1000), FeatureMode.CATEGORY).getBody();
        // perform lightweight transforms to build decision tree features
        final List<FeatureSet> features = buidDecisionTreeFeatures(commentFeatures);

        trainDecisionTree(features);
        trainRandomForest(features);
    }

    private void trainDecisionTree(final List<FeatureSet> features) {
        final DecisionTree decisionTree = new DecisionTree();
        final Tree tree = decisionTree.train(target, features);

        // measure accuracy of learned train data
        int correct = 0;
        for(FeatureSet featureSet : features) {
            final String vote = tree.walk(featureSet);
            if(StringUtils.equals(vote, featureSet.get(target))) {
                correct++;
            }
        }
        System.out.println("Decision Tree: " + ((double) correct / features.size() * 100.0) + "% correct");
    }

    private void trainRandomForest(final List<FeatureSet> features) {
        final RandomForest randomForest = new RandomForest();
        randomForest.numTrees = 25;
        final Forest forest = randomForest.train(target, features);

        // measure accuracy of learned train data
        int correct = 0;
        for(FeatureSet featureSet : features) {
            final String vote = forest.walk(featureSet);
            if(StringUtils.equals(vote, featureSet.get(target))) {
                correct++;
            }
        }
        System.out.println("Random Forest: " + ((double) correct / features.size() * 100.0) + "% correct");
    }

    private List<FeatureSet> buidDecisionTreeFeatures(final CommentFeaturesEnvelope commentFeaturesEnvelope) {
        final List<FeatureSet> formattedFeatures = new ArrayList<>();
        for(List<String> features : commentFeaturesEnvelope.getFeatures()) {
            final FeatureSet featureSet = new FeatureSet();
            for(int i = 0; i < features.size(); i++) {
                featureSet.set(commentFeaturesEnvelope.getFeatureNames().get(i), features.get(i));
            }
            formattedFeatures.add(featureSet);
        }
        return formattedFeatures;
    }

}
