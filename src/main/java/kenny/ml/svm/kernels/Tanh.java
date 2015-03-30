package kenny.ml.svm.kernels;

import kenny.ml.svm.FeatureSpace;

public class Tanh implements IKernel {
	
	/**
	 * Tanh (sigmoid) kernel: k(x,z) = tanh(a*<x,z>+b)
	 * @param x first vector
	 * @param z  second vector
	 * @param a coefficient of <x,y>
	 * @param b  bias
	 * @return tanh kernel value
	 */
	@Override
	public double k(FeatureSpace x, FeatureSpace z, double... params) {
		if(params.length < 2) {
			throw new IllegalArgumentException("Must contain two parameters: a, b");
		}
		double a = params[0];
		double b = params[1];
		return Math.tanh(a * x.dot(z) + b);
	}

}
