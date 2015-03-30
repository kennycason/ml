package kenny.ml.som.map;


import kenny.ml.som.vector.AbstractVector;

/**
 * represents a physical location in the Self Organizing Map
 * @author kenny
 *
 */
public abstract class AbstractMapLocation extends AbstractVector {

	protected AbstractMapLocation(int size) {
		super(AbstractVector.buildDefaultValues(size, 0));
	}
	
	
}
