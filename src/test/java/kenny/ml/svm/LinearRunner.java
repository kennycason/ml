package kenny.ml.svm;

import kenny.ml.svm.kernels.Polynomial;
import kenny.ml.svm.problem.ProblemLoader;
import kenny.ml.svm.problem.Problem;
import kenny.ml.svm.problem.SimpleProblemLoader;
import org.junit.Test;

import java.io.IOException;

public class LinearRunner {
	
	@Test
	public void runTest() throws IOException {
		SupportVectorMachine svm = new SupportVectorMachine();

		ProblemLoader loader = new SimpleProblemLoader();
		
		Problem train = loader.load("kenny/ml/svm/linear_train.svm");
		Problem test = loader.load("kenny/ml/svm/linear_test.svm");
		
		System.out.println("Loaded.");
		System.out.println("Training...");
		KernelParams kp = new KernelParams(new Polynomial(), 10, 1, 1);
		svm.train(train, kp);
		System.out.println("Testing...");
		int[] pred = svm.test(test, true);

		EvalMeasures e = new EvalMeasures(test, pred, 2);
		System.out.println("Accuracy=" + e.accuracy());

		System.out.println("Done.");
	}
}
