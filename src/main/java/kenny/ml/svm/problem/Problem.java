package kenny.ml.svm.problem;

import kenny.ml.svm.Categories;
import kenny.ml.svm.FeatureSpace;

/**
 * Class representing an optimization problem (a data setting); "bias" excluded
 * 
 * 
 */
public class Problem {

	/** The number of training data */
	public int trainingSize;

	/** The number of features (including the bias feature if bias >= 0) */
	public int featureSize;

	/** Array containing the target values */
	public int[] y;

	/** Map of categories to allow various ID's to identify classes with. */
	public Categories<Integer> categories;

	/** Array of feature spaces */
	public FeatureSpace[] x;

	public Problem() {
		trainingSize = 0;
		featureSize = 0;
		categories = new Categories<>();
	}
	

}
