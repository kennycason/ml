package kenny.ml.som.map;


public class MapLocation1D extends AbstractMapLocation {
	
	public MapLocation1D(int x) {
		super(1);
		setAll(x);
	}
	
	public void x(int x) {
		set(0, x);
	}
	
	public int x() {
		return (int)get(0);
	}
	
	public MapLocation1D unit() {
		MapLocation1D v = new MapLocation1D(0);
		double m = magnitude();
		v.x((int)(x() / m));
		return v;
	}
	
}
