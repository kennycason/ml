package kenny.ml.svm.smo;

import kenny.ml.svm.KernelParams;
import kenny.ml.svm.Model;
import kenny.ml.svm.problem.Problem;

public class Simple extends AbstractSequentialMinimalOptimization {
	
	private int maxPass = 30;

	public Simple() {
		this(10);
	}
	
	public Simple(int maxPass) {
		this.maxPass = maxPass;
	}	
	
	/**
	 * Probabilistic (random, simple) SMO
	 * 
	 * @param train  Training set
	 * @param p Kernel parameters
	 */
	@Override
	public void smo(Problem train, KernelParams p) {
		int pass = 0;
		int alpha_change = 0;
		int i, j;
		double eta;
		// Initialize:
		model = new Model();
		model.alpha = new double[train.l];
		model.b = 0;
		model.params = p;
		model.x = train.x;
		model.y = train.y;
		model.l = train.l;
		model.n = train.n;
		// LinearLibSVMFormatRunner iteration:
		while (pass < maxPass) {
			alpha_change = 0;
			for (i = 0; i < train.l; i++) {
				Ei = testOne(train.x[i]) - train.y[i];
				if ((train.y[i] * Ei < -tol && model.alpha[i] < model.params.getC()) 
						|| (train.y[i] * Ei > tol && model.alpha[i] > 0)) {
					j = (int) Math.floor(Math.random() * (train.l - 1));
					j = (j < i) ? j : (j + 1);
					Ej = testOne(train.x[j]) - train.y[j];
					ai_old = model.alpha[i];
					aj_old = model.alpha[j];
					L = computeL(train.y[i], train.y[j]);
					H = computeH(train.y[i], train.y[j]);
					if (L == H) // next i
						continue;
					double kij = kernel(i, j);
					double kii = kernel(i, i);
					double kjj = kernel(j, j);
					eta = 2 * kij - kii - kjj;
					if (eta >= 0) // next i
						continue;
					model.alpha[j] = aj_old - (train.y[j] * (Ei - Ej)) / eta;
					if (model.alpha[j] > H) {
						model.alpha[j] = H;
					} else if (model.alpha[j] < L) {
						model.alpha[j] = L;
					}
					if (Math.abs(model.alpha[j] - aj_old) < tol2) // next i
						continue;
					model.alpha[i] = ai_old + train.y[i] * train.y[j]
							* (aj_old - model.alpha[j]);
					computeBias(model.alpha[i], model.alpha[j], train.y[i],
							train.y[j], kii, kjj, kij);
					alpha_change++;
				}
			}
			if (alpha_change == 0) {
				pass++;
			} else {
				pass = 0;
			}
			if (alpha_change > 0) {
				System.out.print(".");
			} else {
				System.out.print("*");
			}
		}
		System.out.println();
	}

	
	public int getMaxPass() {
		return maxPass;
	}

	public void setMaxPass(int p) {
		maxPass = p;
	}

}
