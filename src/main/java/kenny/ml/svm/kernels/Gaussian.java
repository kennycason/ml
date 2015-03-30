package kenny.ml.svm.kernels;

import kenny.ml.svm.FeatureSpace;

public class Gaussian implements IKernel {

	/**
	 * Gaussian (RBF) kernel: k(x,z) = (-0.5/sigma^2)*||x-z||^2
	 * 
	 * @param x  first vector
	 * @param z   second vector
	 * @param sigma parameter (standard deviation)
	 * @return Gaussian kernel value
	 */
	@Override
	public double k(FeatureSpace x, FeatureSpace z, double ... params) {
		if(params.length < 1) {
			throw new IllegalArgumentException("Must contain one parameter: sigma");
		}
		double sigma = params[0];
		return Math.exp(-sigma * x.distanceSquared(z));
		//return (-0.5 / sigma * sigma) * x.distanceSquared(z);
	}
	
}

