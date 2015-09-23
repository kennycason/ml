package kenny.ml.decisiontree;

import com.datarank.api.DataRank;
import com.datarank.api.config.DataRankConfiguration;
import kenny.ml.decisiontree.randomforest.Forest;
import kenny.ml.decisiontree.randomforest.RandomForest;
import kenny.ml.svm.EvalMeasures;
import kenny.ml.svm.KernelParams;
import kenny.ml.svm.SupportVectorMachine;
import kenny.ml.svm.kernels.Gaussian;
import kenny.ml.svm.kernels.IKernel;
import kenny.ml.svm.problem.Problem;
import kenny.ml.svm.problem.SimpleProblemLoader;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.junit.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by kenny
 */
public class TestDataRankTopicDecisionTreeAndForestAndSVM {

    private Set<String> whiteList = new HashSet<>(Arrays.asList(
       "GENDER", "AGE", "SENTIMENT", "DATASOURCE", "DATASOURCE_TYPE", /*"HAS_BIO", */
            "THEME_PACKAGING", "THEME_PRICE", "THEME_SWITCHING", "THEME_AVAILABILITY",
             "THEME_PERFORMANCE", /*"THEME_NPS",*/
            "THEME_PURCHASE_INTENT", "IS_AMAZON"
    ));

    private final String target = "THEME_PURCHASE_INTENT";  // "IS_AMAZON"; is amazon has a 1-to-1 mapping to datasource so models will learn 100% accuracy.

    public void loadFeatures() throws IOException {
        final File apiKeyFile = new File(System.getProperty("user.home") + "/.datarank-api-key");
        final DataRank dataRank = new DataRank(new DataRankConfiguration(IOUtils.toString(new FileInputStream(apiKeyFile)).trim()));
        final List<FeatureSet> features = DataRankTopicFeatureExtractor.downloadComments(dataRank, "tide-pods", 1000);
        FeatureLoader.write(features, "/tmp/tide-pods.csv");
    }

    @Test
    public void testBoth() throws IOException {
        loadFeatures();
        testDecisionTree();
        testRandomForest();
        testSvm();
    }

    private void testDecisionTree() throws IOException {
        final List<FeatureSet> features = FeatureLoader.read("/tmp/tide-pods.csv", whiteList);

        final DecisionTree decisionTree = new DecisionTree();
        final Tree tree = decisionTree.train(target, features);

        int correct = 0;
        for(FeatureSet featureSet : features) {
            final String vote = tree.walk(featureSet);
            if(StringUtils.equals(vote, featureSet.get(target))) {
                correct++;
            }
        }
        System.out.println("Decision Tree: " + ((double) correct / features.size() * 100.0) + "% correct");
    }

    private void testRandomForest() throws IOException {
        final List<FeatureSet> features = FeatureLoader.read("/tmp/tide-pods.csv", whiteList);

        final RandomForest randomForest = new RandomForest();
        randomForest.numTrees = 25;
        final Forest forest = randomForest.train(target, features);

        int correct = 0;
        for(FeatureSet featureSet : features) {
            final String vote = forest.walk(featureSet);
            if(StringUtils.equals(vote, featureSet.get(target))) {
                correct++;
            }
        }
        System.out.println("Random Forest: " + ((double) correct / features.size() * 100.0) + "% correct");
    }

    private void testSvm() throws IOException {
        final SupportVectorMachine supportVectorMachine = new SupportVectorMachine();

        final IKernel kernel = new Gaussian();
        final KernelParams kernelParams = new KernelParams(kernel, 0.023, 2, 7);
        kernelParams.setC(Math.pow(2, 0));

        final Problem problem = new SimpleProblemLoader().load("kenny/ml/svm/tide-pods.svm.small.csv");
        final Problem test = new SimpleProblemLoader().load("kenny/ml/svm/tide-pods.svm.csv");
        supportVectorMachine.train(problem, kernelParams);
        final int[] predictions = supportVectorMachine.test(test, false);

        final EvalMeasures evalMeasures = new EvalMeasures(test, predictions, 2);
        System.out.println("Support Vector Machine: " + (evalMeasures.accuracy() * 100.0) + "%");
    }

}
