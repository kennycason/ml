package kenny.impl.porn;

import kenny.ml.data.image.Image;
import org.junit.Test;

/**
 * Created by kenny on 4/11/15.
 */
public class TestPornImageSkinDetectionClassifier {

    @Test
    public void test() {
        final PornImageSkinDetectionClassifier pornImageClassifier = new PornImageSkinDetectionClassifier();

        System.out.println("\nPORN\n*******************");
        for(int i = 2; i < 15; i++) {
            System.out.println(pornImageClassifier.classify(new Image("kenny/impl/porn/images/porn/i" + i + ".png")));
        }
        System.out.println("\nNOT PORN\n*******************");
        for(int i = 2; i < 15; i++) {
            System.out.println(pornImageClassifier.classify(new Image("kenny/impl/porn/images/not_porn/i" + i + ".png")));
        }
    }

}
