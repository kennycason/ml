package kenny.ml.svm;

import kenny.ml.svm.problem.Problem;

public class EvalMeasures {

	Problem p;
	int[] predicted;
	int catnum;
	int computed;

	public EvalMeasures(Problem p, int[] predicted, int catnum) {
		if (predicted.length != p.trainingSize) {
			System.err.println("Length error!");
			return;
		}
		this.p = p;
		this.predicted = predicted;
		this.catnum = catnum;
		computed = 0;
	}

	public double accuracy() {
		int ret = 0;
		for (int i = 0; i < p.trainingSize; i++) {
			if (p.y[i] == predicted[i]) {
				ret++;
			}
		}
		return (double) ret / p.trainingSize;
	}
	
}
