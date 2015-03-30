package kenny.ml.som.map;

public class MapLocation3D extends AbstractMapLocation {
	
	public MapLocation3D(int x, int y, int z) {
		super(3);
		setAll(x, y, z);
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
	
	public MapLocation3D unit() {
		MapLocation3D v = new MapLocation3D(0, 0, 0);
		double m = magnitude();
		v.x((int)(x() / m));
		v.y((int)(y() / m));
		v.z((int)(z() / m));
		return v;
	}
	
}
