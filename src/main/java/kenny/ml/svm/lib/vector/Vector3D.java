package kenny.ml.svm.lib.vector;

public class Vector3D extends AbstractVector {
	
	public Vector3D() {
		this(0, 0, 0);
	}
	
	public Vector3D(double x, double y, double z) {
		super(x, y, z);
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
	
	public double z() {
		return get(2);
	}
	
	public void z(double z) {
		set(2, z);
	}	
	
	public Vector3D unit() {
		Vector3D v = new Vector3D();
		double m = magnitude();
		v.x(x() / m);
		v.y(y() / m);
		v.z(z() / m);
		return v;
	}
	
	public Vector3D clone() {
		return new Vector3D(x(), y(), z());
	}
	
}
