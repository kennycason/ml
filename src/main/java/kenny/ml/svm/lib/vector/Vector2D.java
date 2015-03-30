package kenny.ml.svm.lib.vector;

public class Vector2D extends AbstractVector {
	
	public Vector2D() {
		this(0, 0);
	}
	
	public Vector2D(double x, double y) {
		super(x, y);
	}
	
	public double x() {
		return get(0);
	}
	
	public void x(double x) {
		set(0, x);
	}	
	
	public double y() {
		return get(1);
	}
	
	public void y(double y) {
		set(1, y);
	}	
	
	public Vector2D unit() {
		Vector2D v = new Vector2D();
		double m = magnitude();
		v.x(x() / m);
		v.y(y() / m);
		return v;
	}
	
	public Vector2D clone() {
		return new Vector2D(x(), y());
	}
	
}
