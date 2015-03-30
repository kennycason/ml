package kenny.ml.svm;


import kenny.ml.svm.kernels.Gaussian;
import kenny.ml.svm.kernels.IKernel;
import kenny.ml.svm.kernels.Linear;
import kenny.ml.svm.kernels.Polynomial;
import kenny.ml.svm.kernels.Tanh;
import kenny.ml.svm.lib.Arguments;
import kenny.ml.svm.problem.IProblemLoader;
import kenny.ml.svm.problem.LibSVMProblemLoader;
import kenny.ml.svm.problem.Problem;
import kenny.ml.svm.problem.SimpleProblemLoader;
import kenny.ml.svm.smo.AbstractSequentialMinimalOptimization;
import kenny.ml.svm.smo.Platts;
import kenny.ml.svm.smo.Simple;

import java.io.IOException;

public class Main {
	public static void main(String[] args) throws IOException {
		Arguments arg = Arguments.getInstance();
		arg.parse(args);

		if(arg.get("train") == null) {
			System.err.println("must specify train data file!");
			System.exit(0);
		}
		if(arg.get("test") == null) {
			arg.set("test", arg.get("train"));
		}
		
		IProblemLoader loader;
		if("libsvm".equalsIgnoreCase(arg.get("format"))) {
			loader = new LibSVMProblemLoader();
		} else {
			loader = new SimpleProblemLoader();
		}
		
		IKernel k = null;
		if("linear".equalsIgnoreCase(arg.get("kernel"))) {
			k = new Linear();
		} else if("polynomial".equalsIgnoreCase(arg.get("kernel"))) {
			k = new Polynomial();
		} else if("tanh".equalsIgnoreCase(arg.get("kernel"))) {
			k = new Tanh();
		} else if("gaussian".equalsIgnoreCase(arg.get("kernel"))) {
			k = new Gaussian();
		} else {
			k = new Gaussian();
		}
		
		AbstractSequentialMinimalOptimization smo;
		if("simple".equalsIgnoreCase(arg.get("smo"))) {
			smo = new Simple();
		} else if("platts".equalsIgnoreCase(arg.get("smo"))) {
			smo = new Platts();
		} else {
			smo = new Simple();
		} 
		
		double a = 1;
		double b = 1;
		double c = 1;
		if(arg.get("a") == null) {
			a = arg.getDouble("a");
		}
		if(arg.get("b") == null) {
			b = arg.getDouble("b");
		}	
		if(arg.get("c") == null) {
			c = arg.getDouble("c");
		}
		
		SupportVectorMachine svm = new SupportVectorMachine(smo);
		
		Problem train = loader.load(arg.get("train"));
		Problem test = loader.load(arg.get("test"));
		
		System.out.println("Loaded.");
		System.out.println("Training...");
		
		
		
		KernelParams kp = new KernelParams(k, a, b, c);
		kp.setC(Math.pow(2, 0));
		
		svm.train(train, kp);
		System.out.println("Testing...");
		int[] pred = svm.test(test, true);

		EvalMeasures e = new EvalMeasures(test, pred, 2);
		System.out.println("Accuracy=" + e.accuracy());

		System.out.println("Done.");
	}
}
