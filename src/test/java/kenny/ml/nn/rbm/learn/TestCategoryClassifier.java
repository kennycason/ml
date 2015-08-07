package kenny.ml.nn.rbm.learn;

import kenny.ml.data.image.Image;
import kenny.ml.data.image.decode.Matrix24BitImageDecoder;
import kenny.ml.data.image.decode.MatrixImageDecoder;
import kenny.ml.data.image.encode.Matrix24BitImageEncoder;
import kenny.ml.data.image.encode.MatrixImageEncoder;
import kenny.ml.nn.rbm.deep.DeepRBM;
import kenny.ml.nn.rbm.deep.LayerParameters;
import kenny.ml.nn.rbm.factory.RandomRBMFactory;
import kenny.ml.nn.rbm.math.DenseMatrix;
import kenny.ml.nn.rbm.math.Matrix;
import org.apache.log4j.Logger;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

/**
 * Created by kenny on 3/30/15.
 */
public class TestCategoryClassifier {

    private static final Logger LOGGER = Logger.getLogger(TestPeople.class);
    private static final RandomRBMFactory RBM_FACTORY = new RandomRBMFactory();

    @Test
    public void woman24LargeDeepBit() {
        final List<String> trainingImages = Arrays.asList(
                "/kenny/ml/nn/rbm/categories/faces/0.png",
//                "/kenny/ml/nn/rbm/categories/faces/1.png",
//                "/kenny/ml/nn/rbm/categories/faces/8.png",
//                "/kenny/ml/nn/rbm/categories/faces/9.png",
//                "/kenny/ml/nn/rbm/categories/faces/16.png",
//                "/kenny/ml/nn/rbm/categories/faces/17.png",
//                "/kenny/ml/nn/rbm/categories/houses/0.png",
                "/kenny/ml/nn/rbm/categories/houses/1.png"
//                "/kenny/ml/nn/rbm/categories/houses/8.png",
//                "/kenny/ml/nn/rbm/categories/houses/9.png",
//                "/kenny/ml/nn/rbm/categories/houses/16.png",
//                "/kenny/ml/nn/rbm/categories/houses/17.png"
        );

        final int rows = 66;
        final int columns = 67;

        final MatrixImageEncoder encoder = new Matrix24BitImageEncoder();
        final MatrixImageDecoder decoder = new Matrix24BitImageDecoder(rows);

        final Matrix trainingMatrix = loadDataSet(trainingImages, encoder);

        // 35376
        final LayerParameters[] layerParameters = new LayerParameters[] {
                new LayerParameters().setNumRBMS(1).setVisibleUnitsPerRBM(66 * 67 * 24).setHiddenUnitsPerRBM(1000),
                new LayerParameters().setNumRBMS(1).setVisibleUnitsPerRBM(1000).setHiddenUnitsPerRBM(100)
        };
//        final LayerParameters[] layerParameters = new LayerParameters[] {
//                new LayerParameters().setNumRBMS(3 * 3).setVisibleUnitsPerRBM(11792).setHiddenUnitsPerRBM(50),
//                new LayerParameters().setNumRBMS(1).setVisibleUnitsPerRBM(450).setHiddenUnitsPerRBM(25)
//        };
//        final LayerParameters[] layerParameters = new LayerParameters[] {
//                new LayerParameters().setNumRBMS(1).setVisibleUnitsPerRBM(35376 * 3).setHiddenUnitsPerRBM(10)
//        };

        DeepRBM deepRBM = new DeepRBM(layerParameters, RBM_FACTORY);
        final DeepContrastiveDivergence contrastiveDivergence = new DeepContrastiveDivergence(
                new LearningParameters().setEpochs(500).setLearningRate(0.1))
         ;

        contrastiveDivergence.learn(deepRBM, trainingMatrix);

        final Matrix hidden = contrastiveDivergence.runVisible(deepRBM, DenseMatrix.make(trainingMatrix.row(0)));
        final Matrix visual = contrastiveDivergence.runHidden(deepRBM, hidden);
        LOGGER.info(hidden);
        final Image outImage = decoder.decode(visual);
        outImage.save("/tmp/face.bmp");

        final Matrix hidden2 = contrastiveDivergence.runVisible(deepRBM, DenseMatrix.make(trainingMatrix.row(1)));
        final Matrix visual2 = contrastiveDivergence.runHidden(deepRBM, hidden2);
        LOGGER.info(hidden2);
        final Image outImage2 = decoder.decode(visual2);
        outImage2.save("/tmp/house.bmp");

        final Image testImage = new Image("/kenny/ml/nn/rbm/categories/house/32.png");
        final Matrix testMatrix = encoder.encode(testImage);
        final Matrix hidden3 = contrastiveDivergence.runVisible(deepRBM, testMatrix);
        final Matrix visual3 = contrastiveDivergence.runHidden(deepRBM, hidden3);
        LOGGER.info(hidden3);
        final Image outImage4 = decoder.decode(visual3);
        outImage4.save("/tmp/house.bmp");

    }

    private Matrix loadDataSet(final List<String> imageFiles, final MatrixImageEncoder encoder) {
        int i = 0;
        final Matrix[] allPokemonData = new Matrix[imageFiles.size()];
        for(String imageFile : imageFiles) {
            final Image pokemonImage = new Image(imageFile);
            final Matrix pokemonData = encoder.encode(pokemonImage);
            allPokemonData[i] = pokemonData;

            i++;
        }
        return DenseMatrix.make(Matrix.concatRows(allPokemonData));
    }
}
