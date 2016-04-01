package kenny.ml.sentiment.svm;

import kenny.ml.data.imdb.ImdbData;
import kenny.ml.data.imdb.ImdbLoader;
import kenny.ml.data.imdb.ImdbSentenceSplitTransform;
import kenny.ml.data.wordvector.Word2VecTextLoader;
import kenny.ml.nlp.tokenizer.WhiteSpaceWordTokenizer;
import kenny.ml.nlp.tokenizer.WordTokenizer;
import kenny.ml.svm.EvalMeasures;
import kenny.ml.svm.FeatureSpace;
import kenny.ml.svm.KernelParams;
import kenny.ml.svm.SupportVectorMachine;
import kenny.ml.svm.kernels.Gaussian;
import kenny.ml.svm.problem.Problem;
import org.eclipse.collections.api.RichIterable;
import org.eclipse.collections.api.map.MutableMap;

import java.io.IOException;
import java.util.List;

/**
 * Created by kenny on 3/28/16.
 */
public class TestSentimentClassifier {
    private static final int WORD_VECTOR_DIMENSIONS = 300;
    private static final WordTokenizer WORD_TOKENIZER = new WhiteSpaceWordTokenizer();
    private final SupportVectorMachine svm = new SupportVectorMachine();

    private MutableMap<String, double[]> wordVectors;

    public static void main(final String[] args) throws IOException {
        new TestSentimentClassifier().load();
    }

    public void load() throws IOException {
        System.out.println("Loading Word Vectors");
        wordVectors = Word2VecTextLoader.loadAsMap("/Users/kenny/Downloads/GoogleNews-vectors-negative300.txt", WORD_VECTOR_DIMENSIONS, 500_000);
        System.out.println("Loaded Word Vectors");

//        final MutableList<String> positiveReviews = new FastList<>();
//        final MutableList<String> negativeReviews = new FastList<>();
//        loadReviews(positiveReviews, negativeReviews);
        final ImdbData originalImdbData = ImdbLoader.load("/Users/kenny/Downloads/aclImdb/");
        final ImdbData sentenceTokenizedImdbData = ImdbSentenceSplitTransform.transform(originalImdbData);
        System.out.println("Train: Loaded " + sentenceTokenizedImdbData.train.positive.size() + " positive reviews and " + sentenceTokenizedImdbData.train.negative.size() + " negative reviews");
        System.out.println("Test: Loaded " + originalImdbData.test.positive.size() + " positive reviews and " + originalImdbData.test.negative.size() + " negative reviews");

        // only take first N data for training
        final RichIterable<String> positive = sentenceTokenizedImdbData.train.positive.take(500);
        final RichIterable<String> negative = sentenceTokenizedImdbData.train.negative.take(500);
        sentenceTokenizedImdbData.train.positive.clear();
        sentenceTokenizedImdbData.train.positive.addAllIterable(positive);
        sentenceTokenizedImdbData.train.negative.clear();
        sentenceTokenizedImdbData.train.negative.addAllIterable(negative);

        final Problem train = loadProblem(1, sentenceTokenizedImdbData.train.positive, -1, sentenceTokenizedImdbData.train.negative);
        final Problem test = loadProblem(1, originalImdbData.test.positive, -1, originalImdbData.test.negative);

        System.out.println("Loaded.");
        System.out.println("Training...");

        final KernelParams kp = new KernelParams(new Gaussian(), Math.pow(2, -3));
        kp.setC(Math.pow(2, -8));

        // σ=0.03,c=1,words=50k,reviews=100 = 66
        // σ=2^-4,c=2^-2,words=500k,reviews=100 = .6660268714011516
        // σ=2^-4,c=2^-2,words=500k,reviews=1000 = .815
        svm.train(train, kp);

        System.out.println("Testing against training data...");
        final int[] prediction = svm.test(train, false);
        final EvalMeasures evalMeasures = new EvalMeasures(train, prediction, 2);
        System.out.println("Accuracy=" + evalMeasures.accuracy());

        System.out.println("Testing against test data...");
        final int[] predictionTest = svm.test(test, false);
        final EvalMeasures evalMeasuresTest = new EvalMeasures(test, predictionTest, 2);
        System.out.println("Accuracy=" + evalMeasuresTest.accuracy());

        System.out.println("Done.");
    }

//    private void loadReviews(final MutableList<String> positiveReviews, final MutableList<String> negativeReviews) throws IOException {
//        final List<String> lines = IOUtils.readLines(Thread.currentThread().getContextClassLoader().getResourceAsStream("kenny/ml/nlp/ecommerce_reviews_1000.csv"));
//        lines.stream()
//             .forEach(line -> {
//                 final String[] parts = line.split(",", 2);
//                 final String paragraph = parts[1].toLowerCase().replace("\"", "");
//                 final List<Sentence> sentences = SENTENCE_TOKENIZER.tokenize(paragraph);
//
//                 if (parts[0].charAt(0) == '5' || parts[0].charAt(0) == '4') {
//                     positiveReviews.addAll(sentences.stream().map(Sentence::toString).collect(Collectors.toList()));
//                 }
//                 else if (parts[0].charAt(0) == '2' || parts[0].charAt(0) == '1') {
//                     negativeReviews.addAll(sentences.stream().map(Sentence::toString).collect(Collectors.toList()));
//                 }
//             });
//    }

    private Problem loadProblem(final int class1, final List<String> class1Text, final int class2, final List<String> class2Text) {
        final Problem problem = new Problem();
        problem.categories.add(class1);
        problem.categories.add(class2);
        problem.trainingSize = class1Text.size() + class2Text.size();
        problem.y = new int[class1Text.size() + class2Text.size()];
        problem.x = new FeatureSpace[class1Text.size() + class2Text.size()];

        for (int i = 0; i < class1Text.size(); i++) {
            problem.y[i] = class1;
            problem.x[i] = new FeatureSpace(averageWordVectors(class1Text.get(i)));
        }
        for (int i = 0; i < class2Text.size(); i++) {
            problem.y[class1Text.size() + i] = class2;
            problem.x[class1Text.size() + i] = new FeatureSpace(averageWordVectors(class2Text.get(i)));
        }
        return problem;
    }

    private double[] averageWordVectors(final String sentence) {
        final double[] combinedVector = new double[WORD_VECTOR_DIMENSIONS];
        final List<String> words = WORD_TOKENIZER.tokenize(sentence);
        for (final String word : words) {
            if (!wordVectors.containsKey(word)) { continue; }
            addAssign(combinedVector, wordVectors.get(word));
        }
        divideAssign(combinedVector, words.size());
        return combinedVector;
    }


    private void addAssign(final double[] left, final double[] right) {
        for (int i = 0; i < left.length; i++) {
            left[i] += right[i];
        }
    }

    private void divideAssign(final double[] left, final int n) {
        for (int i = 0; i < left.length; i++) {
            left[i] /= n;
        }
    }
}
