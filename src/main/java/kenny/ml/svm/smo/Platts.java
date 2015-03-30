package kenny.ml.svm.smo;

import kenny.ml.svm.KernelParams;
import kenny.ml.svm.Model;
import kenny.ml.svm.problem.Problem;

public class Platts extends AbstractSequentialMinimalOptimization {

	/**
	 * LinearLibSVMFormatRunner method for Platt's SMO
	 * 
	 * @param train Training data set
	 * @param p Kernel parameters
	 */
	@Override
	public void smo(Problem train, KernelParams p) {
		int numChanged = 0;
		int examineAll = 1;
		// Initialize:
		model = new Model();
		model.alpha = new double[train.l];
		model.b = 0;
		model.params = p;
		model.x = train.x;
		model.y = train.y;
		model.l = train.l;
		model.n = train.n;
		// Initialize error cache E:
		E = new double[model.l];
		for (int i = 0; i < model.l; i++)
			E[i] = testOne(model.x[i]) - model.y[i];
		while (numChanged > 0 || examineAll == 1) {
			numChanged = 0;
			if (examineAll == 1) {
				for (int i = 0; i < model.l; i++) {
					numChanged += psmoExamineExample(i);
				}
			} else {
				for (int i = 0; i < model.l; i++) {
					if (model.alpha[i] > 0 && model.alpha[i] < 0) {
						numChanged += psmoExamineExample(i);
					}
				}
			}
			if (examineAll == 1)
				examineAll = 0;
			else if (numChanged == 0)
				examineAll = 1;
			System.out.print(".");
		}
		System.out.println();
	}
	
	/**
	 * ExamineExample method for Platt's SMO
	 * 
	 * @param j Second index
	 * @return 0/1
	 */
	private int psmoExamineExample(int j) {
		int i = 0;
		int randpos;
		int yj = model.y[j];
		aj_old = model.alpha[j];
		Ej = E[j];
		double rj = Ej * yj;
		if ((rj < -tol && aj_old < model.params.getC()) || (rj > tol && aj_old > 0)) {
			boolean exists = false;
			for (int k = 0; k < model.l; k++)
				if (model.alpha[k] > 0 && model.alpha[k] < model.params.getC()) {
					exists = true;
					break;
				}
			if (exists) {
				// second choice heuristics:
				int maxind = 0;
				double maxval = Math.abs(E[0] - Ej);
				for (int k = 1; k < model.l; k++)
					if (Math.abs(E[k] - Ej) > maxval) {
						maxval = Math.abs(E[k] - Ej);
						maxind = k;
					}
				if (psmoTakeStep(maxind, j) == 1)
					return 1;
			}
			// loop over non-zero & non-C alpha, starting at a random point:
			randpos = (int) Math.floor(Math.random() * model.l);
			for (int k = 0; k < model.alpha.length; k++) {
				i = (randpos + k) % model.l;
				if (model.alpha[i] > 0 && model.alpha[i] < model.params.getC()) {
					if (psmoTakeStep(i, j) == 1) {
						return 1;
					}
				}
			}
			// loop over all i, starting at a random point
			randpos = (int) Math.floor(Math.random() * model.l);
			for (int k = 0; k < model.alpha.length; k++) {
				i = (randpos + k) % model.l;
				if (psmoTakeStep(i, j) == 1) {
					return 1;
				}
			}
		}
		return 0;
	}

	
	/**
	 * TakeStep method for Platt's SMO
	 * 
	 * @param i First index
	 * @param j Second index
	 * @return 0/1
	 */
	private int psmoTakeStep(int i, int j) {
		double ai, aj;
		if (i == j) {
			return 0;
		}
		b_old = model.b;
		ai_old = model.alpha[i];
		// aj_old = model.alpha[j];
		int yi = model.y[i];
		int yj = model.y[j];
		Ei = E[i];
		// Ej = E[j];
		double s = yi * yj;
		L = computeL(yi, yj);
		H = computeH(yi, yj);
		if (L == H)
			return 0;
		double kii = kernel(i, i);
		double kjj = kernel(j, j);
		double kij = kernel(i, j);
		eta = 2 * kij - kii - kjj;
		if (eta < 0) {
			aj = aj_old - yj * (Ei - Ej) / eta;
			if (aj < L) {
				aj = L;
			} else if (aj > H) {
				aj = H;
			}
		} else {
			double vi, vj;
			vi = testOne(model.x[i]) - yi * ai_old * kii - yj * aj_old * kij;
			vj = testOne(model.x[j]) - yi * ai_old * kij - yj * aj_old * kjj;
			double Lobj = smoObj(L, yi, yj, kij, kii, kjj, vi, vj);
			double Hobj = smoObj(H, yi, yj, kij, kii, kjj, vi, vj);
			if (Lobj > Hobj + tol) {
				aj = L;
			} else if (Lobj < Hobj - tol) {
				aj = H;
			} else {
				aj = aj_old;
			}
		}
		if (aj < tol2) {
			aj = 0;
		} else if (aj > model.params.getC() - tol2) {
			aj = model.params.getC();
		}
		if (Math.abs(aj - aj_old) < tol * (aj + aj_old + tol)) {
			return 0;
		}
		ai = ai_old + s * (aj_old - aj);
		computeBias(ai, aj, yi, yj, kii, kjj, kij);
		model.alpha[i] = ai;
		model.alpha[j] = aj;
		for (int k = 0; k < model.l; k++) {
			double kik = kernel(model.x[i], model.x[k]);
			double kjk = kernel(model.x[j], model.x[k]);
			E[k] += (ai - ai_old) * yi * kik + (aj - aj_old) * yj * kjk - b_old
					+ model.b;
		}
		return 1;
	}
}
