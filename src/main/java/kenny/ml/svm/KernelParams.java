package kenny.ml.svm;

import kenny.ml.svm.kernels.IKernel;
import kenny.ml.svm.kernels.Linear;

/**
 * Class for the kernel parameters
 */
public class KernelParams {
	
	/** Regularization parameter */
	private double C = 1;

	public IKernel kernel = new Linear();
	
	private double[] params;

	public KernelParams(IKernel kernel, double ... params) {
		this.kernel = kernel;
		this.params = params;
	}

	public double getParam(int i) {
		return params[i];
	}
	
	public double[] getParams() {
		return params;
	}
	
	public KernelParams() {
		this(new Linear(), 1, 1, 1);
	}
	
	public void setC(double C) {
		this.C = C;
	}
	
	public double getC() {
		return C;
	}
	
}
