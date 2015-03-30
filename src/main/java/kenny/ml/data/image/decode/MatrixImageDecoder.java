package kenny.ml.data.image.decode;


import kenny.ml.data.image.Image;
import kenny.ml.nn.rbm.math.Matrix;

public interface MatrixImageDecoder {

    Image decode(final Matrix matrix);

}
