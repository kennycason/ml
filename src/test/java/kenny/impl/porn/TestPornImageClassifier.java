package kenny.impl.porn;

import kenny.ml.data.image.Image;
import org.junit.Test;

/**
 * Created by kenny on 4/5/15.
 */
public class TestPornImageClassifier {

    @Test
    public void test() {
        final PornImageClassifier pornImageClassifier = new PornImageClassifier();
        pornImageClassifier.init();

        System.out.println("\nPORN\n*******************");
        for(int i = 2; i < 15; i++) {
            System.out.println(pornImageClassifier.classify(new Image("kenny/ml/nlp/porn/images/porn/i" + i + ".png")));
        }
        System.out.println("\nNOT PORN\n*******************");
        for(int i = 2; i < 15; i++) {
            System.out.println(pornImageClassifier.classify(new Image("kenny/ml/nlp/porn/images/not_porn/i" + i + ".png")));
        }
    }
}
