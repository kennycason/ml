package kenny.ml.svm.kernels;

import kenny.ml.svm.FeatureSpace;

public class StringKernel implements IKernel {

	// private String ;	// alphabet
	
//	public StringKernel(String ) {
		//this.= ;
//	}
	
	/**
	 * The most common discrete kernel is the string kernel, which is used to compute
	 * similarity between different strings
	 * @param x first vector
	 * @param z second vector
	 * 
	 */
	@Override
	public double k(FeatureSpace x, FeatureSpace z, double... params) {
		return 0;
	}

}
