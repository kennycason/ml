package kenny.ml.data.image.encode;


import kenny.ml.data.image.Image;
import kenny.ml.nn.rbm.math.DenseMatrix;
import kenny.ml.nn.rbm.math.Matrix;

import java.awt.image.BufferedImage;

/**
 * Created by kenny on 5/20/14.
 */
public class MatrixGrayscaleImageEncoder implements MatrixImageEncoder {

    private static final int BITS = 8;

    private static final int HIGH_BIT_FLAG = 0b1000_0000_1000_0000_1000_0000;

    @Override
    public Matrix encode(final Image image) {
        double[] matrix = new double[image.width() * BITS * image.height()];
        final BufferedImage bi = image.data();
        for(int y = 0; y < image.height(); y++) {
            for(int x = 0; x < image.width(); x++) {
                read(matrix, bi, x, y);
            }
        }
        return DenseMatrix.make(new double[][]{matrix});
    }

    private void read(final double[] matrix, final BufferedImage bi, final int x, final int y) {
        int flag = HIGH_BIT_FLAG;
        int offset = 0;
        while(offset < 8) {
            final int rgb = bi.getRGB(x, y) & 0xFFFFFF;
            boolean set = (rgb & flag) == flag;
            int index = (y * bi.getWidth() * BITS) + ((x * BITS) + offset);
            matrix[index] = set ? 1.0 : 0.0;
            offset++;
            flag >>= 1;
        }
    }


}
