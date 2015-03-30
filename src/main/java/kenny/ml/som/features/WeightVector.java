package kenny.ml.som.features;


public class WeightVector extends AbstractWeightVector {

	public WeightVector (double ... values) {
		super(values);
	}
	
	public WeightVector add(double m) {
		WeightVector ret = new WeightVector(new double[size()]);
		for(int i = 0; i < size(); i++) {
			ret.set(i, get(i) + m);
		}
		return ret;
	}
	
	public WeightVector sub(double m) {
		WeightVector ret = new WeightVector(new double[size()]);
		for(int i = 0; i < size(); i++) {
			ret.set(i, get(i) - m);
		}
		return ret;
	}	
	
	public WeightVector mult(double m) {
		WeightVector ret = new WeightVector(new double[size()]);
		for(int i = 0; i < size(); i++) {
			ret.set(i, get(i) * m);
		}
		return ret;
	}
	
	public WeightVector div(double m) {
		WeightVector ret = new WeightVector(new double[size()]);
		if(m != 0.0) {
			for(int i = 0; i < size(); i++) {
				ret.set(i, get(i) / m);
			}
		} else {
			for(int i = 0; i < size(); i++) {
				ret.set(i, 0);
			}
		}
		return ret;
	}
	
	public WeightVector add(AbstractWeightVector m) {
		WeightVector ret = new WeightVector(new double[size()]);
		int maxSize = max(size(), m.size());
		for(int i = 0; i < maxSize; i++) {
			ret.set(i, get(i) + ((WeightVector)m).get(i));
		}
		return ret;
	}
	
	public WeightVector sub(AbstractWeightVector m) {
		WeightVector ret = new WeightVector(new double[size()]);
		int maxSize = max(size(), m.size());
		for(int i = 0; i < maxSize; i++) {
			ret.set(i, get(i) - ((WeightVector)m).get(i));
		}
		return ret;
	}	
	
	public WeightVector mult(AbstractWeightVector m) {
		WeightVector ret = new WeightVector(new double[size()]);
		int maxSize = max(size(), m.size());
		for(int i = 0; i < maxSize; i++) {
			ret.set(i, get(i) * ((WeightVector)m).get(i));
		}
		return ret;
	}
	
	public WeightVector div(AbstractWeightVector m) {
		WeightVector ret = new WeightVector(new double[size()]);
		boolean nonZero = true;
		int maxSize = max(size(), m.size());
		for(int i = 0; i < maxSize; i++) {
			nonZero &= (((WeightVector)m).get(i) != 0);
		}
		if(nonZero) {
			for(int i = 0; i < size(); i++) {
				ret.set(i, get(i) / ((WeightVector)m).get(i));
			}
		} else {
			for(int i = 0; i < size(); i++) {
				ret.set(i, 0);
			}
		}
		return ret;
	}
	
	public WeightVector unit() {
		WeightVector v = new WeightVector();
		double m = magnitude();
		for(int i = 0; i < size(); i++) {
			v.set(i, get(i) / m);
		}
		return v;
	}
	
	private int max(int a, int b) {
		return a > b ? a : b;
	}
	

}
