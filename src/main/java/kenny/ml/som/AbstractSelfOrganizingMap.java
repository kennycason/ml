package kenny.ml.som;


import kenny.ml.som.features.AbstractWeightVector;
import kenny.ml.som.map.AbstractMapLocation;

import java.util.Random;

public abstract class AbstractSelfOrganizingMap {
	
	protected SelfOrganizingMapConfig config;
	
	protected Random r = new Random();
	
	protected AbstractSelfOrganizingMap(SelfOrganizingMapConfig config) {
		this.config = config;
	}
	
	public abstract AbstractMapLocation calculateBestMatchingUnit(AbstractWeightVector weight);

	public abstract void scaleNeighbors(AbstractMapLocation loc, AbstractWeightVector actual, double t2);

	public abstract AbstractWeightVector getRandomSample();
	
	public abstract AbstractWeightVector getSample(int i);

	public abstract double[] processInput(AbstractWeightVector input);
	
	public int getSampleSize() {
		return config.samples.length;
	}
	
	public double sigmoid(double x) {
		return (1.0 / (1 + Math.exp(-x)));
	}
	
	public double tanh(double x) {
		return Math.tanh(x);
	}

	public SelfOrganizingMapConfig getConfig() {
		return config;
	}

}
