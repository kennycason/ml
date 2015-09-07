package kenny.ml.nn.bep;

import org.junit.Test;

public class TestBEPNeuralNetwork {

	@Test
	public void and() {
		NeuralNetworkConfig config = new NeuralNetworkConfig();
		config.bias = true;
		config.numCenterLayers = 1;
		config.numCenterNodes = 10;
		config.numInputNodes = 2;
		config.numOutputNodes = 1;
		config.learningRate = 0.8;
		NeuralNetwork nn = new NeuralNetwork(config);
		
		int numTrainData = 4;
		double[][] trainData = new double[numTrainData][nn.getInputs().length];
		double[][] teacherSignals = new double[numTrainData][nn.getOutputs().length];
		
		trainData[0] = new double[]{0, 0};
		trainData[1] = new double[]{0, 1};
		trainData[2] = new double[]{1, 0};
		trainData[3] = new double[]{1, 1};

		teacherSignals[0] = new double[]{0};
		teacherSignals[1] = new double[]{0};
		teacherSignals[2] = new double[]{0};
		teacherSignals[3] = new double[]{1};

		train(nn, trainData, teacherSignals);
		printResults(nn, trainData, teacherSignals);
	}

	@Test
	public void or() {
		NeuralNetworkConfig config = new NeuralNetworkConfig();
		config.bias = true;
		config.numCenterLayers = 3;
		config.numCenterNodes = 4;
		config.numInputNodes = 2;
		config.numOutputNodes = 1;
		config.learningRate = 0.05;
		NeuralNetwork nn = new NeuralNetwork(config);

		int numTrainData = 4;
		double[][] trainData = new double[numTrainData][nn.getInputs().length];
		double[][] teacherSignals = new double[numTrainData][nn.getOutputs().length];

		trainData[0] = new double[]{0, 0};
		trainData[1] = new double[]{0, 1};
		trainData[2] = new double[]{1, 0};
		trainData[3] = new double[]{1, 1};

		teacherSignals[0] = new double[]{0};
		teacherSignals[1] = new double[]{1};
		teacherSignals[2] = new double[]{1};
		teacherSignals[3] = new double[]{1};

		train(nn, trainData, teacherSignals);
		printResults(nn, trainData, teacherSignals);
	}

	@Test
	public void highLow() {
		NeuralNetworkConfig config = new NeuralNetworkConfig();
		config.bias = true;
		config.numCenterLayers = 2;
		config.numCenterNodes = 4;
		config.numInputNodes = 3;
		config.numOutputNodes = 1;
		config.learningRate = 0.5;
		NeuralNetwork nn = new NeuralNetwork(config);

		int numTrainData = 8;
		double[][] trainData = new double[numTrainData][nn.getInputs().length];
		double[][] teacherSignals = new double[numTrainData][nn.getOutputs().length];

		trainData[0] = new double[]{0,0,0};
		trainData[1] = new double[]{0,0,1};
		trainData[2] = new double[]{0,1,0};
		trainData[3] = new double[]{0,1,1};
		trainData[4] = new double[]{1,0,0};
		trainData[5] = new double[]{1,0,1};
		trainData[6] = new double[]{1,1,0};
		trainData[7] = new double[]{1,1,1};

		teacherSignals[0] = new double[]{0};
		teacherSignals[1] = new double[]{0};
		teacherSignals[2] = new double[]{0};
		teacherSignals[3] = new double[]{0};
		teacherSignals[4] = new double[]{1};
		teacherSignals[5] = new double[]{1};
		teacherSignals[6] = new double[]{1};
		teacherSignals[7] = new double[]{1};

		train(nn, trainData, teacherSignals);
		printResults(nn, trainData, teacherSignals);
	}

	private void train(final NeuralNetwork nn, final double[][] trainData, final double[][] teacherSignals) {
		final int numTrainData = trainData.length;
		double maxError = 0.001;
		double error = Double.MAX_VALUE;
		int count = 0;
		System.out.println("Begin trainings");
		while(error > maxError) {
			error = 0;
			for(int i = 0; i < numTrainData; i++) {
				for(int j = 0; j < nn.getInputHeight(); j++) {
					nn.setInput(j, trainData[i][j]);
					nn.setInput(j, trainData[i][j]);
				}
				for(int j = 0; j < nn.getInputHeight(); j++) {
					nn.setTeacherSignal(j, teacherSignals[i][j]);
				}
				nn.feedForward();
				error += nn.calculateError();
				nn.backPropagate();
				nn.clearAllValues();
			}
			count++;
			error /= numTrainData;
			if(count % 100 == 0) {
				System.out.println("[" + count + "] error = " + error);
			}
		}
	}

	private void printResults(final NeuralNetwork nn, final double[][] trainData, final double[][] teacherSignals) {
		final int numTrainData = trainData.length;
		// print results
		for(int i = 0; i < numTrainData; i++) {
			nn.clearAllValues();
			System.out.print("[");
			for(int j = 0; j < nn.getInputs().length; j++) {
				nn.setInput(j, trainData[i][j]);
				System.out.print(" " + trainData[i][j]);
			}
			System.out.print("] -> [");
			nn.feedForward();
			for(int j = 0; j < nn.getOutputs().length; j++) {
				nn.setTeacherSignal(j, teacherSignals[i][j]);
				System.out.print(nn.getOutput(j));
			}
			System.out.println("]");
		}
	}

}
