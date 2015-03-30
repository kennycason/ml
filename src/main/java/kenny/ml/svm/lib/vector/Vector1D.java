package kenny.ml.svm.lib.vector;

public class Vector1D extends AbstractVector {
	
	public Vector1D() {
		this(0.0);
	}
	
	public Vector1D(double x) {
		super(0.0);
	}
	
	public double x() {
		return get(0);
	}
	
	public void x(double x) {
		set(0, x);
	}

	public Vector1D unit() {
		Vector1D v = new Vector1D();
		double m = magnitude();
		v.x(x() / m);
		return v;
	}
	
	public Vector1D clone() {
		return new Vector1D(x());
	}
	
}
