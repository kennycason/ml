package kenny.ml.som.map;

public class MapLocation4D extends AbstractMapLocation {
	
	public MapLocation4D(int x, int y, int z, int u) {
		super(4);
		setAll(x, y, z, u);
	}
	
	public void x(int x) {
		set(0, x);
	}
	
	public int x() {
		return (int)get(0);
	}
	
	public void y(int y) {
		set(1, y);
	}
	
	public int y() {
		return (int)get(1);
	}
	
	public void z(int z) {
		set(2, z);
	}
	
	public int z() {
		return (int)get(2);
	}
	
	public void u(int u) {
		set(3, u);
	}
	
	public int u() {
		return (int)get(3);
	}

	public MapLocation4D unit() {
		MapLocation4D v = new MapLocation4D(0, 0, 0, 0);
		double m = magnitude();
		v.x((int)(x() / m));
		v.y((int)(y() / m));
		v.z((int)(z() / m));
		v.u((int)(u() / m));
		return v;
	}
	
}
