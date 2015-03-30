package kenny.ml.svm.lib.vector;

public class VectorND extends AbstractVector {

	public VectorND(double ... values) {
		super(values);
	}

	public VectorND unit() {
		VectorND v = new VectorND();
		double m = magnitude();
		for(int i = 0; i < size(); i++) {
			v.set(i, get(i) / m);
		}
		return v;
	}
	
	public VectorND clone() {
		return new VectorND(val);
	}
	
}
