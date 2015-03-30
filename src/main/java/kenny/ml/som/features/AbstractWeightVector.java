package kenny.ml.som.features;


import kenny.ml.som.vector.AbstractVector;

/**
 * each dimension in the Weight Vector represents a feature to be organized
 * @author kenny
 *
 */
public abstract class AbstractWeightVector extends AbstractVector {
	
	protected AbstractWeightVector(double ... values) {
		super(values);
	}

	public  abstract AbstractWeightVector add(double m);
	
	public  abstract AbstractWeightVector sub(double m);
	
	public  abstract AbstractWeightVector mult(double m);
	
	public  abstract AbstractWeightVector div(double m);
	
	public  abstract AbstractWeightVector add(AbstractWeightVector m);
	
	public  abstract AbstractWeightVector sub(AbstractWeightVector m);
	
	public  abstract AbstractWeightVector mult(AbstractWeightVector m);
	
	public abstract AbstractWeightVector div(AbstractWeightVector m);
	
}
