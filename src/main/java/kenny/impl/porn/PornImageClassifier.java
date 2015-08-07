package kenny.impl.porn;

import kenny.impl.image.ImageCategoryClassifier;
import kenny.ml.data.image.Image;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kenny on 4/5/15.
 */
public class PornImageClassifier {

    private ImageCategoryClassifier imageCategoryClassifier = new ImageCategoryClassifier();

    public PornImageClassifier() {}

    public void init() {
        imageCategoryClassifier.init(getPornImages(), getNonPornImages());
    }

    public float classify(final Image image) {
        return this.imageCategoryClassifier.classify(image);
    }

    private List<Image> getPornImages() {
        final List<Image> images = new ArrayList<>();
        for(int i = 1; i <= 15/*72*/; i++) {
            images.add(new Image("kenny/ml/nlp/porn/images/porn/i" + i + ".png"));
        }
        return images;
    }

    private List<Image> getNonPornImages() {
        final List<Image> images = new ArrayList<>();
        for(int i = 1; i <= 15/*72*/; i++) {
            images.add(new Image("kenny/ml/nlp/porn/images/not_porn/i" + i + ".png"));
        }
        return images;
    }
}
