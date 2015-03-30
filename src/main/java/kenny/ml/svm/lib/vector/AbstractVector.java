package kenny.ml.svm.lib.vector;

import java.text.DecimalFormat;
import java.util.Random;

public abstract class AbstractVector implements IVector {
	
	private static Random r = new Random();
	
	protected double[] val;
	
	protected AbstractVector(double ... values) {
		this.val = values;
	}
	
	public double get(int i) {
		if(i < size()) {
			return val[i];
		}
		return 0.0;
	}
	
	public void set(int i, double value) {
		if(i < size()) {
			val[i] = value;
		}
	}
	
	public void setAll(double ... values) {
		for(int i = 0; i < size() && i < values.length; i++) {
			set(i, values[i]);
		}
	}
	
	public double[] get() {
		return val;
	}
	
	public double magnitude() {
		double magnitude = 0;
		for(int i = 0; i < size(); i++) {
			magnitude += get(i) * get(i);
		}
		magnitude = Math.sqrt(magnitude);
		return magnitude;
	}
	
	public double dot(IVector vector) {
		double dot = 0;
		for(int i = 0; i < size(); i++) {
			dot += get(i) * vector.get(i);
		}
		return dot;
	}
	
	public double distanceSquared(IVector vector) {
		double distanceSquared = 0;
		for(int i = 0; i < size(); i++) {
			distanceSquared += (get(i) - vector.get(i)) * (get(i) - vector.get(i));
		}
		return distanceSquared;
	}
		
	public double distance(IVector vector) {
		return Math.sqrt(distanceSquared(vector));
	}
	
	public int size() {
		return val.length;
	}
	
	public String toString() {
		DecimalFormat dFormat = new DecimalFormat("0.00");
		StringBuilder sb = new StringBuilder();
		sb.append("(");
		for(int i = 0; i < size(); i++) {
			sb.append(dFormat.format(get(i)));
			if(i < size() - 1) {
				sb.append(", ");
			}
		}
		sb.append(")");
		return sb.toString();
	}
	
	public static double[] buildRandomValues(int size) {
		double[] values = new double[size];
		for(int i = 0; i < size; i++) {
			values[i] = r.nextDouble();
		}
		return values;
	}
	
	public static double[] buildDefaultValues(int size, double val) {
		double[] values = new double[size];
		for(int i = 0; i < size; i++) {
			values[i] = val;
		}
		return values;
	}

}
