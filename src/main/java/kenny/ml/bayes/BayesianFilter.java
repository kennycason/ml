package kenny.ml.bayes;

public interface BayesianFilter {

	void finalizeTraining();

    void trainPositive(String content);

    void trainNegative(String content);

	float analyze(String content);
	
}
