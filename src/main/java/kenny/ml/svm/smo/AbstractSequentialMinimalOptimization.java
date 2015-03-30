package kenny.ml.svm.smo;

import kenny.ml.svm.FeatureSpace;
import kenny.ml.svm.KernelParams;
import kenny.ml.svm.Model;
import kenny.ml.svm.kernels.Linear;
import kenny.ml.svm.problem.Problem;

import java.util.List;


public abstract class AbstractSequentialMinimalOptimization {
	
	
	/** Trained/loaded model */
	protected Model model;
	
	/** Tolerance */
	protected double tol = 10e-3;
	/** Tolerance */
	protected double tol2 = 10e-5;

	protected double tol3 = 10e-3;
	
	protected double tol4 = 10e-5;

	
	/**
	 *  some global variables of the SMO algorithm 
	 */
	protected double Ei, Ej;
	
	protected double ai_old, aj_old, b_old;
	
	protected double L, H;
	
	protected double maxup, minlow;
	
	protected double bup, blow;
	
	protected int iup, ilow;
	
	protected double eta;
	
	protected List<Integer> I0, I1, I2, I3, I4;
	
	protected double[] E;
	
	protected AbstractSequentialMinimalOptimization() {
	}
	
	public abstract void smo(Problem train, KernelParams p);
	
	/**
	 * computes L
	 * 
	 * @param yi
	 * @param yj
	 * @return Returns L
	 */
	protected double computeL(int yi, int yj) {
		double L = 0;
		if (yi != yj) {
			L = Math.max(0, -ai_old + aj_old);
		} else {
			L = Math.max(0, ai_old + aj_old - model.params.getC());
		}
		return L;
	}

	/**
	 * computes H
	 * 
	 * @param yi
	 * @param yj
	 * @return Returns H
	 */
	protected double computeH(int yi, int yj) {
		double H = 0;
		if (yi != yj) {
			H = Math.min(model.params.getC(), - ai_old + aj_old + model.params.getC());
		} else {
			H = Math.min(model.params.getC(), ai_old + aj_old);
		}
		return H;
	}
	
	protected double smoObj(double aj, int yi, int yj, double kij, double kii,
			double kjj, double vi, double vj) {
		double s = yi * yj;
		double gamma = ai_old + s * aj_old;
		return (gamma + (1 - s) * aj - 0.5 * kii * (gamma - s * aj)
				* (gamma - s * aj) - 0.5 * kjj * aj * aj + -s * kij
				* (gamma - s * aj) * aj - yi * (gamma - s * aj) * vi - yj * aj
				* vj);
	}

	/**
	 * computes the bias and stores in model.b
	 * 
	 * @param ai
	 * @param aj
	 * @param yi
	 * @param yj
	 * @param kii
	 * @param kjj
	 * @param kij
	 */
	protected void computeBias(double ai, double aj, int yi, int yj, double kii, double kjj, double kij) {
		double b1 = model.b - Ei - yi * (ai - ai_old) * kii - yj
				* (aj - aj_old) * kij;
		double b2 = model.b - Ej - yi * (ai - ai_old) * kij - yj
				* (aj - aj_old) * kjj;
		if (0 < ai && ai < model.params.getC()) {
			model.b = b1;
		} else if (0 < aj && aj < model.params.getC()) {
			model.b = b2;	
		} else {
			model.b = (b1 + b2) / 2;
		}
	}

	
	/**
	 * Test one example
	 * testOne
	 * @param x The test example
	 * @return class of x: -1 or 1
	 */
	public double testOne(FeatureSpace x) {
		double f = 0;
		for (int i = 0; i < model.l; i++) {
			f += model.alpha[i] * model.y[i] * kernel(x, model.x[i]);
		}
		return f + model.b;
	}
	
	/**
	 * Based on the kernel parameters/settings of the model, calculates the
	 * kernel value between two points
	 * 
	 * @param x First point/vector
	 * @param z Second point/vector
	 * @return Kernel value between x and z
	 */
	protected double kernel(FeatureSpace x, FeatureSpace z) {
		if (model.params == null) {
			model.params = new KernelParams(new Linear(), 1, 1, 1);
		}
		return model.params.kernel.k(x, z, model.params.getParams());

	}

	/**
	 * Based on the kernel parameters/settings of the model, calculates the
	 * kernel value between two points
	 * 
	 * @param i Index of the first vector in model.x
	 * @param j Index of the second vector in model.x
	 * @return Kernel value between model.x[i] and model.x[j]
	 */
	protected double kernel(int i, int j) {
		if (model.params == null) {
			System.err.println("No Kernel Params were specified!, using default!");
			model.params = new KernelParams(new Linear(), 1, 1, 1);
		}
		return model.params.kernel.k(model.x[i], model.x[j], model.params.getParams());
	}

	public double getTolerance() {
		return tol;
	}

	public void setTolerance(double tol) {
		this.tol = tol;
	}

	public double getTolerance2() {
		return tol2;
	}

	public void setTolerance2(double tol2) {
		this.tol2 = tol2;
	}

	public void setParameters(double tol, double tol2) {
		this.tol = tol;
		this.tol2 = tol2;
	}
	
	public Model getModel() {
		return model;
	}

}
