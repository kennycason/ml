package kenny.ml.som.map;

public class MapLocation2D extends AbstractMapLocation {
	
	public MapLocation2D(int x, int y) {
		super(2);
		setAll(x, y);
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
	
	public MapLocation2D unit() {
		MapLocation2D v = new MapLocation2D(0, 0);
		double m = magnitude();
		v.x((int)(x() / m));
		v.y((int)(y() / m));
		return v;
	}
	
}
