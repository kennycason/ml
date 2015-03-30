package kenny.ml.som.vector;

public interface IVector extends Cloneable {

	double get(int i);
	
	void set(int i, double value);
	
	void setAll(double... values);
	
	double[] get();
	
	double magnitude();
	
	double dot(IVector vector);
	
	double distanceSquared(IVector vector);
	
	double distance(IVector vector);
	
	IVector unit();
	
	int size();
}
