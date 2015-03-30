package kenny.ml.data.image.encode;


import kenny.ml.data.image.Image;
import kenny.ml.nn.rbm.math.Matrix;

/**
 * Created by kenny on 5/20/14.
 */
public interface MatrixImageEncoder {

    Matrix encode(final Image image);

}
