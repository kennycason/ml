package kenny.ml.svm.kernels;

import kenny.ml.svm.FeatureSpace;

public class Linear implements IKernel {

	/**
	 * Linear kernel: k(x,z) = <x,z>
	 * @param x first vector
	 * @param z second vector
	 * @return linear kernel value
	 */
	@Override
	public double k(FeatureSpace x, FeatureSpace z, double ... params) {
		return x.dot(z);
	}
	
}
