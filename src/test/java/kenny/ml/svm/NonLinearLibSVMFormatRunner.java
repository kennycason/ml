package kenny.ml.svm;

import kenny.ml.svm.kernels.Gaussian;
import kenny.ml.svm.problem.IProblemLoader;
import kenny.ml.svm.problem.LibSVMProblemLoader;
import kenny.ml.svm.problem.Problem;
import org.junit.Test;

import java.io.IOException;

public class NonLinearLibSVMFormatRunner {
	
	@Test
	public void runTest() throws IOException {
		SupportVectorMachine svm = new SupportVectorMachine();

		IProblemLoader loader = new LibSVMProblemLoader();
		
		Problem train = loader.load("kenny/ml/svm/libsvm/nonlinear_train.libsvm");
		Problem test = loader.load("kenny/ml/svm/libsvm/nonlinear_test.libsvm");

		System.out.println("Loaded.");
		System.out.println("Training...");
		
		KernelParams kp = new KernelParams(new Gaussian(), 0.03);
		kp.setC(Math.pow(2, 0));
		svm.train(train, kp);
		
		System.out.println("Testing...");
		int[] pred = svm.test(test, true);

		EvalMeasures e = new EvalMeasures(test, pred, 2);
		System.out.println("Accuracy=" + e.accuracy());

		System.out.println("Done.");
	}
	
}
