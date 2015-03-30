package kenny.ml.svm;


import kenny.ml.svm.lib.vector.SparseVector;

public class FeatureSpace extends SparseVector {

	public FeatureSpace(int size, double ... values) {
		super(size);
		for(int i = 0; i < size && i < values.length; i++) {
			set(i, values[i]);
		}
	}

	public FeatureSpace unit() {
		FeatureSpace v = new FeatureSpace(size(), get());
		double m = magnitude();
		for(int i = 0; i < size(); i++) {
			v.set(i, get(i) / m);
		}
		return v;
	}
	
}
