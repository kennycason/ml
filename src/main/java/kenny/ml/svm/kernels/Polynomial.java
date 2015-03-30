package kenny.ml.svm.kernels;

import kenny.ml.svm.FeatureSpace;

public class Polynomial implements IKernel {

	/**
	 * Polynomial kernel: k(x,z) = (a*<x,z>+b)^c
	 * @param x first vector
	 * @param z second vector
	 * @param a coefficient of <x,z>
	 * @param b bias
	 * @param c power
	 * @return polynomial kernel value
	 */
	@Override
	public double k(FeatureSpace x, FeatureSpace z, double ... params) {
		if(params.length < 3) {
			throw new IllegalArgumentException("Must contain three parameters: a, b, and c");
		}
		double a = params[0]; // gamma
		double b = params[1]; // coef0
		double c = params[2]; // degree
		double dotXZ = x.dot(z);
		if (c == 1.0) {
			return a * dotXZ + b;
		}
		return Math.pow(a * dotXZ + b, c);
	}
	
}
