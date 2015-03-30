package kenny.ml.som;


import kenny.ml.som.features.AbstractWeightVector;

public class SelfOrganizingMapConfig {
	
	public int dimX = 0;
			
	public int dimY = 0;

	public int dimZ = 0;
	
	public int dimU = 0;
	
	
	// initial radius of influence (start large)
	public int radius = 60;

	// how many neighbors are used in the calculations of the similarity map,
	// the greater the number the higher the quality
	public int simWeight = 3;
	
	public int weightVectorDimension = 1;

	public AbstractWeightVector[] samples;
	
}
