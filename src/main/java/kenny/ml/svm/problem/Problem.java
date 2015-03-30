package kenny.ml.svm.problem;

import kenny.ml.svm.CategoryMap;
import kenny.ml.svm.FeatureSpace;

/**
 * Class representing an optimization problem (a data setting); "bias" excluded
 * 
 * 
 */
public class Problem {

	/** The number of training data */
	public int l;

	/** The number of features (including the bias feature if bias >= 0) */
	public int n;

	/** Array containing the target values */
	public int[] y;

	/** Map of categories to allow various ID's to identify classes with. */
	public CategoryMap<Integer> catmap;

	/** Array of feature spaces */
	public FeatureSpace[] x;

	public Problem() {
		l = 0;
		n = 0;
		catmap = new CategoryMap<Integer>();
	}
	

}
