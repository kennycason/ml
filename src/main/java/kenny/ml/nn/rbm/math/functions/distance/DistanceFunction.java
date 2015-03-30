package kenny.ml.nn.rbm.math.functions.distance;


import kenny.ml.nn.rbm.math.Matrix;

/**
 * kenny
 *
 * Distance is analogous to the 1 - normalized(similarityScore)
 */
public interface DistanceFunction {
    double distance(Matrix item1, Matrix item2);
}
