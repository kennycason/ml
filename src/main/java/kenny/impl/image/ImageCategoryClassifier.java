package kenny.impl.image;

import ch.lambdaj.Lambda;
import kenny.ml.bayes.BayesianFilter;
import kenny.ml.bayes.NaiveBayesianFilter;
import kenny.ml.data.image.Image;
import kenny.ml.data.image.encode.Matrix8BitImageEncoder;
import kenny.ml.data.image.encode.MatrixImageEncoder;
import kenny.ml.nn.rbm.RBM;
import kenny.ml.nn.rbm.factory.RBMFactory;
import kenny.ml.nn.rbm.factory.RandomRBMFactory;
import kenny.ml.nn.rbm.learn.ContrastiveDivergence;
import kenny.ml.nn.rbm.learn.LearningParameters;
import kenny.ml.nn.rbm.math.Matrix;
import kenny.ml.utils.ImageUtils;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by kenny on 4/5/15.
 */
public class ImageCategoryClassifier {
    private static final Logger LOGGER = Logger.getLogger(ImageCategoryClassifier.class);
    private static final RBMFactory RBM_FACTORY = new RandomRBMFactory();
    private static final int IMAGE_WIDTH = 25;
    private static final int IMAGE_HEIGHT = 25;

    private final MatrixImageEncoder imageEncoder = new Matrix8BitImageEncoder();
    private final ContrastiveDivergence contrastiveDivergence = new ContrastiveDivergence(new LearningParameters().setEpochs(100).setLearningRate(0.05));
    private RBM imageRbm;

    private BayesianFilter imageFeatureBayesianFilter;

    public ImageCategoryClassifier() {}

    public void init(final Collection<Image> categoryAImages, final Collection<Image> categoryBImages) {
        LOGGER.info("Training RBM against images");

        final List<Matrix> categoryAMatrices = convertImagesToMatrices(categoryAImages);
        final List<Matrix> categoryBMatrices = convertImagesToMatrices(categoryBImages);

        final List<Matrix> allImages = new ArrayList<>();
        allImages.addAll(categoryAMatrices);
        allImages.addAll(categoryBMatrices);

        this.imageRbm = RBM_FACTORY.build(IMAGE_WIDTH * IMAGE_HEIGHT * 8, 1000);
        LOGGER.info("Begin Training RBM Network");
        this.contrastiveDivergence.learn(imageRbm, allImages);

        LOGGER.info("Train BayesianFilter");
        this.imageFeatureBayesianFilter = new NaiveBayesianFilter();
        final List<String> categoryAFeatures = convertImageDataToFeatures(categoryAMatrices);
        final List<String> categoryBFeatures = convertImageDataToFeatures(categoryBMatrices);
        this.imageFeatureBayesianFilter.trainPositive(Lambda.join(categoryAFeatures, "\n"));
        this.imageFeatureBayesianFilter.trainNegative(Lambda.join(categoryBFeatures, "\n"));
        this.imageFeatureBayesianFilter.finalizeTraining();
    }

    public float classify(final Image image) {
        final Matrix hidden = this.contrastiveDivergence.runVisible(this.imageRbm, imageEncoder.encode(ImageUtils.resize(image, IMAGE_WIDTH, IMAGE_HEIGHT)));

        return this.imageFeatureBayesianFilter.analyze(convertFeatureToText(hidden));
    }

    private String convertFeatureToText(final Matrix featureMatrix) {
        final StringBuilder stringBuilder = new StringBuilder();
        for(int i = 0; i < featureMatrix.columns(); i++) {
//            if(featureMatrix.get(0, i) > 0.7) {
//                stringBuilder.append("f" + i + "h ");
//            } else if(featureMatrix.get(0, i) < 0.3) {
//                stringBuilder.append("f" + i + "l ");
//            } else {
//                stringBuilder.append("f" + i + "m ");
//            }
            if(featureMatrix.get(0, i) > 0.9) {
                stringBuilder.append("f" + i + " ");
            }
        }
        LOGGER.info("Feature: " + stringBuilder.toString());
        return stringBuilder.toString();
    }

    private List<String> convertImageDataToFeatures(final Collection<Matrix> imageMatrices) {
        final List<String> features = new ArrayList<String>();
        for(Matrix imageMatrix : imageMatrices) {
            final Matrix hidden = this.contrastiveDivergence.runVisible(this.imageRbm, imageMatrix);

            features.add(convertFeatureToText(hidden));
        }
        return features;
    }

    private List<Matrix> convertImagesToMatrices(final Collection<Image> images) {
        final List<Matrix> imagesMatrices = new ArrayList<>();
        for(Image image : images) {
            imagesMatrices.add(
                    imageEncoder.encode(
                            ImageUtils.resize(image, IMAGE_WIDTH, IMAGE_HEIGHT)));
        }
        return imagesMatrices;
    }

}
