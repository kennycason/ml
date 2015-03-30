package kenny.ml.som;


import kenny.ml.som.features.AbstractWeightVector;
import kenny.ml.som.features.WeightVector;
import kenny.ml.som.map.AbstractMapLocation;
import kenny.ml.som.map.MapLocation1D;
import kenny.ml.som.vector.AbstractVector;

public class SelfOrganizingMap1D extends AbstractSelfOrganizingMap {

	
	private WeightVector[] weights;

	public SelfOrganizingMap1D(SelfOrganizingMapConfig config) {
		super(config);
		this.init();
	}

	private void init() {
		weights = new WeightVector[config.dimX];

			for (int x = 0; x < config.dimX; x++) {	
				weights[x] = new WeightVector(AbstractVector.buildDefaultValues(config.weightVectorDimension, 0.0));
			}
	
	}

	public MapLocation1D calculateBestMatchingUnit(AbstractWeightVector weight) {
		MapLocation1D[] matches = new MapLocation1D[config.dimX];
		int numMatches = 0;
		double shortestDistance = Double.MAX_VALUE;

			for (int x = 0; x < config.dimX; x++) {
				double d = weights[x].distance(weight);
				if (d < shortestDistance) {
					matches[0] = new MapLocation1D(x);
					shortestDistance = d;
					numMatches = 1;
				} else if (d == shortestDistance) {
					matches[numMatches++] = new MapLocation1D(x);
				}
			}

		return matches[Math.abs(r.nextInt()) % numMatches];
	}

	/**
	 * use a Gaussian function for every point with value above zero as a
	 * neighbor NOTE: other possible options include concentric squares,
	 * hexagons, etc
	 */
	public void scaleNeighbors(AbstractMapLocation locIn, AbstractWeightVector actualIn, double t2) {
		MapLocation1D loc = (MapLocation1D) locIn;
		WeightVector actual = (WeightVector) actualIn;
		
		int R2 = (int) Math.round(((double) (config.radius) * (1.0 - t2)) / 2.0);
		
		WeightVector outer = new WeightVector(AbstractVector.buildDefaultValues(config.weightVectorDimension, R2));
		WeightVector center = new WeightVector(AbstractVector.buildDefaultValues(config.weightVectorDimension, 0.0));
		
		double distNormalized = center.distance(outer);
		
			for (int x = -R2; x < R2; x++) {
				if (x + loc.x() >= 0 && x + loc.x() < config.dimX) {
					// Get distance from center point and normalize it
					//for(int i = 0; i < outer.size(); i++) { 
						
					//}
					outer.set(0, x);
					double distance = outer.distance(center);
					
					distance /= distNormalized;
					
					// Get how much to scale it by
					double t = Math.exp(-1.0 * (Math.pow(distance, 2.0)) / 0.15);

					// Amount a neuron can learn decreases with time
					// The 4 is chosen and the +1 is to avoid divide by 0'sconfig.weightVectorDimension
					t /= (t2 * 4.0 + 1.0);
					

					// Scale it with the parametric equation
					weights[loc.x() + x] = (actual.mult(t)).add(weights[loc.x() + x].mult(1.0 - t));
				}
			}


	}
	
	public WeightVector getRandomSample() {
		return (WeightVector) config.samples[Math.abs(r.nextInt()) % config.samples.length];
	}
	
	public WeightVector getSample(int i ) {
		return (WeightVector) config.samples[i];
	}
	
	
	/**
	 * returns an array of distances between the input and the sample data
	 * @param input
	 * @return
	 */
	public double[] processInput(AbstractWeightVector input) {
		double[] processed = new double[config.samples.length];
		for(int i = 0; i < config.samples.length; i++) {
			MapLocation1D loc = calculateBestMatchingUnit(input);
			processed[i] = config.samples[i].distance(weights[loc.x()]);
		}
		return processed;
	}
	
	public WeightVector[] getWeightVectors() {
		return weights;
	}

	public String toString() {
		StringBuilder sb = new StringBuilder();
			for (int x = 0; x < config.dimX; x++) {
				sb.append(weights[x]);
			}

		return sb.toString();
	}
}
