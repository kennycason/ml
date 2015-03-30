package kenny.ml.som.save;


import kenny.ml.som.SelfOrganizingMap1D;
import kenny.ml.som.SelfOrganizingMap2D;
import kenny.ml.som.SelfOrganizingMap3D;
import kenny.ml.som.SelfOrganizingMap4D;
import kenny.ml.som.SelfOrganizingMapConfig;

import java.io.FileWriter;
import java.io.IOException;

public class State {

	private FileWriter fw = null;

	private final String fileName;

	public State(String fileName) {
		this.fileName = fileName;
		try {
			fw = new FileWriter(fileName);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void save(SelfOrganizingMap1D som) {
		try {
			SelfOrganizingMapConfig config = som.getConfig();
			// first write the config
			writeConfig(config);

			// write the weights
			for (int x = 0; x < config.dimX; x++) {
				for (int j = 0; j < config.weightVectorDimension; j++) {
					fw.write(som.getWeightVectors()[x].get(j) + " ");
				}
				fw.write("\n");
			}
			fw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("SOM File Save: " + fileName);
	}

	public void save(SelfOrganizingMap2D som) {
		try {
			SelfOrganizingMapConfig config = som.getConfig();
			// first write the config
			writeConfig(config);

			// write the weights
			for (int y = 0; y < config.dimY; y++) {
				for (int x = 0; x < config.dimX; x++) {
					for (int j = 0; j < config.weightVectorDimension; j++) {
						fw.write(som.getWeightVectors()[x][y].get(j) + " ");
					}
					fw.write("\n");
				}
			}
			fw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("SOM File Save: " + fileName);
	}

	public void save(SelfOrganizingMap3D som) {
		try {
			SelfOrganizingMapConfig config = som.getConfig();
			// first write the config
			writeConfig(config);

			// write the weights
			for (int z = 0; z < config.dimZ; z++) {
				for (int y = 0; y < config.dimY; y++) {
					for (int x = 0; x < config.dimX; x++) {
						for (int j = 0; j < config.weightVectorDimension; j++) {
							fw.write(som.getWeightVectors()[x][y][z].get(j)
									+ " ");
						}
						fw.write("\n");
					}
				}
			}
			fw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("SOM File Save: " + fileName);
	}

	public void save(SelfOrganizingMap4D som) {
		try {
			SelfOrganizingMapConfig config = som.getConfig();
			// first write the config
			writeConfig(config);

			// write the weights
			for (int u = 0; u < config.dimU; u++) {
				for (int z = 0; z < config.dimZ; z++) {
					for (int y = 0; y < config.dimY; y++) {
						for (int x = 0; x < config.dimX; x++) {
							for (int j = 0; j < config.weightVectorDimension; j++) {
								fw.write(som.getWeightVectors()[x][y][z][u]
										.get(j) + " ");
							}
							fw.write("\n");
						}
					}
				}
			}
			fw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("SOM File Save: " + fileName);
	}

	private void writeConfig(SelfOrganizingMapConfig config) throws IOException {
		fw.write("dimX=" + config.dimX + " " + "dimY=" + config.dimY + " "
				+ "dimZ=" + config.dimZ + " " + "dimU=" + config.dimU + " ");
		fw.write("radius=" + config.radius + " " + "simWeight="
				+ config.simWeight + " ");
		fw.write("weightVectorDimension=" + config.weightVectorDimension + " "
				+ "samples=" + config.samples.length + "\n");
		for (int i = 0; i < config.samples.length; i++) {
			for (int j = 0; j < config.weightVectorDimension; j++) {
				fw.write(config.samples[i].get(j) + " ");
			}
			fw.write("\n");
		}
	}

}
