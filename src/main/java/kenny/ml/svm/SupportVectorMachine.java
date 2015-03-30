package kenny.ml.svm;

import kenny.ml.svm.problem.Problem;
import kenny.ml.svm.smo.AbstractSequentialMinimalOptimization;
import kenny.ml.svm.smo.Platts;

/**
 * LinearLibSVMFormatRunner class containing the SVM's train and test methods

 */
public class SupportVectorMachine {
	
	/**
	 * A Sequential Minimal Optimizer
	 */
	private AbstractSequentialMinimalOptimization smo;

	public SupportVectorMachine() {
		this(new Platts());
	}
	
	public SupportVectorMachine(AbstractSequentialMinimalOptimization smo) {
		this.smo = smo;
	}
	

	/**
	 * Training the SVM with specified kernel parameters and algorithm
	 * 
	 * @param train Training set
	 * @param p Kernel parameters
	 */
	public void train(Problem train, KernelParams p) {
		smo.smo(train, p);
	}

	
	/**
	 * Test a whole data set
	 * 
	 * @param test The test data
	 * @return An array of -1 and 1's
	 */
	public int[] test(Problem test, boolean print) {
		if (test == null) {
			return null;
		}
		int[] pred = new int[test.l];
		for (int i = 0; i < test.l; i++) {
			pred[i] = (testOne(test.x[i]) < 0 ? -1 : 1);
			
			if(print) { 
				System.out.print(pred[i] + ": ");
				for(int j = 0; j < test.x[i].size(); j++) {
					System.out.print(test.x[i].get(j) + " ");
				}
				System.out.println();
			}
		}
		return pred;
	}
	
	/**
	 * Test one example
	 * 
	 * @param x The test example
	 * @return Class of x: -1 or 1
	 */
	public double testOne(FeatureSpace x) {
		return smo.testOne(x);
	}

}
