package kenny.ml.svm.kernels;

import kenny.ml.svm.FeatureSpace;

public interface IKernel {
	
	double k(FeatureSpace x, FeatureSpace z, double... params);

}
