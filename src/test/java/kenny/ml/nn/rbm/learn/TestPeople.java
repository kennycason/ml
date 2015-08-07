package kenny.ml.nn.rbm.learn;


import kenny.ml.data.image.Image;
import kenny.ml.data.image.decode.Matrix24BitImageDecoder;
import kenny.ml.data.image.encode.Matrix24BitImageEncoder;
import kenny.ml.nn.rbm.RBM;
import kenny.ml.nn.rbm.deep.DeepRBM;
import kenny.ml.nn.rbm.deep.LayerParameters;
import kenny.ml.nn.rbm.factory.RandomRBMFactory;
import kenny.ml.nn.rbm.math.Matrix;
import org.apache.log4j.Logger;
import org.junit.Test;

/**
 * Created by kenny on 5/12/14.
 */
public class TestPeople {

    private static final Logger LOGGER = Logger.getLogger(TestPeople.class);

    private static final RandomRBMFactory RBM_FACTORY = new RandomRBMFactory();


    @Test
    public void woman24Bit() {
        final Image womanImage = new Image("/kenny/ml/nn/rbm/people/woman.jpg");
        final Matrix womanMatrix = new Matrix24BitImageEncoder().encode(womanImage);

        final RBM rbm = RBM_FACTORY.build(womanMatrix.columns(), 10);
        final ContrastiveDivergence contrastiveDivergence = new ContrastiveDivergence(new LearningParameters().setEpochs(100));

        contrastiveDivergence.learn(rbm, womanMatrix);

        final Matrix hidden = contrastiveDivergence.runVisible(rbm, womanMatrix);
        final Matrix visual = contrastiveDivergence.runHidden(rbm, hidden);
        final Image outImage = new Matrix24BitImageDecoder(womanImage.height()).decode(visual);
        outImage.save("/tmp/woman_24bit.bmp");
    }

    @Test
    public void woman24LargeDeepBit() {
        final Image womanImage = new Image("/kenny/ml/nn/rbm/people/woman.jpg");
        final Matrix womanMatrix = new Matrix24BitImageEncoder().encode(womanImage);
        // 200 * 360 * 24 = 1728000

//        final LayerParameters[] layerParameters = new LayerParameters[] {
//                new LayerParameters().setNumRBMS(1000).setVisibleUnitsPerRBM(1728).setHiddenUnitsPerRBM(75),        // 75000 out
//                new LayerParameters().setNumRBMS(500).setVisibleUnitsPerRBM(150).setHiddenUnitsPerRBM(25),     // 12500 out
//                new LayerParameters().setNumRBMS(100).setVisibleUnitsPerRBM(125).setHiddenUnitsPerRBM(10),     // 1000 out
//                new LayerParameters().setNumRBMS(10).setVisibleUnitsPerRBM(100).setHiddenUnitsPerRBM(5),     // 50 out
//                new LayerParameters().setNumRBMS(1).setVisibleUnitsPerRBM(50).setHiddenUnitsPerRBM(5),    // 5 out
//        };
        final LayerParameters[] layerParameters = new LayerParameters[] {
                new LayerParameters().setNumRBMS(100).setVisibleUnitsPerRBM(17280).setHiddenUnitsPerRBM(75),        // 7500 out
                new LayerParameters().setNumRBMS(1).setVisibleUnitsPerRBM(7500).setHiddenUnitsPerRBM(25), // 25
        };

        DeepRBM deepRBM = new DeepRBM(layerParameters, RBM_FACTORY);
        final DeepContrastiveDivergence contrastiveDivergence = new DeepContrastiveDivergence(new LearningParameters().setEpochs(50));

        contrastiveDivergence.learn(deepRBM, womanMatrix);

        final Matrix hidden = contrastiveDivergence.runVisible(deepRBM, womanMatrix);
        final Matrix visual = contrastiveDivergence.runHidden(deepRBM, hidden);
        final Image outImage = new Matrix24BitImageDecoder(womanImage.height()).decode(visual);
        outImage.save("/tmp/woman_24bit_deep2.bmp");

    }

}
