package kenny.impl.porn;

import kenny.ml.data.image.Image;
import kenny.ml.utils.ImageUtils;

import static kenny.ml.utils.ImageUtils.toYcBcR;

/**
 * Created by kenny on 4/11/15.
 *
 * http://www.naun.org/multimedia/NAUN/computers/20-462.pdf
 */
public class PornImageSkinDetectionClassifier {

    public double classify(final Image image) {
        final Image resized = ImageUtils.resize(image, 150, 150);
        int count = 0;
        for(int y = 0; y < resized.height(); y++) {
            for(int x = 0; x < resized.width(); x++) {
                if(judge(toYcBcR(resized.get(x, y)))) {
                    count++;
                }
            }
        }
        return count / (double) (resized.width() * resized.height());
    }

    private boolean judge(final int[] ycbcr) {
        int cb = ycbcr[1];
        int cr = ycbcr[2];
        return cb >= 80 && cb <= 120
                && cr >= 133 && cr <= 173;
    }

}
