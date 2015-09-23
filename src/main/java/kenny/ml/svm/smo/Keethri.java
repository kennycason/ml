package kenny.ml.svm.smo;

import kenny.ml.svm.KernelParams;
import kenny.ml.svm.Model;
import kenny.ml.svm.problem.Problem;

import java.util.ArrayList;


/**
 * TODO - need to finish
 * @author kenny
 *
 */
@Deprecated
public class Keethri extends AbstractSequentialMinimalOptimization {


	/**
	 * SMO type algoritm by Keerthi et al. (2001)
	 * 
	 * @param train  Training set
	 * @param p  Parameters
	 */
	@Override
	public void smo(Problem train, KernelParams p) {
		int alpha_change = 0;
		int examineAll = 1;
		int i;
		// Initialize:
		model = new Model();
		model.alpha = new double[train.trainingSize];
		model.b = 0;
		model.params = p;
		model.x = train.x;
		model.y = train.y;
		model.trainingSize = train.trainingSize;
		model.featureSize = train.featureSize;
		// Initialize the sets Ii:
		I0 = new ArrayList<Integer>();
		I1 = new ArrayList<Integer>();
		I2 = new ArrayList<Integer>();
		I3 = new ArrayList<Integer>();
		I4 = new ArrayList<Integer>();
		for (i = 0; i < train.trainingSize; i++)
			addtoSet(i, model.alpha[i], model.y[i]);
		// LinearLibSVMFormatRunner iteration:
		System.out.println();
		while (alpha_change > 0 || examineAll == 1) {
			alpha_change = 0;
			if (examineAll == 1) {
				for (i = 0; i < train.trainingSize; i++) {

				}
			} else {

			}
			if (examineAll == 1) {
				examineAll = 0;
			} else if (alpha_change == 0) {
				examineAll = 1;
			}
		}
	}
	
	@SuppressWarnings("unused")
	private int takeStep(int i, int j) {
		double kii, kjj, kij;
		double ai, aj;
		double gamma;
		double s;
		if (i == j) {
			return 0;
		}
		Ei = testOne(model.x[i]) - model.y[i];
		Ej = testOne(model.x[j]) - model.y[j];
		ai_old = model.alpha[i];
		aj_old = model.alpha[j];
		L = computeL(model.y[i], model.y[j]);
		H = computeH(model.y[i], model.y[j]);
		if (L == H) {
			return 0;
		}
		kij = kernel(i, j);
		kii = kernel(i, i);
		kjj = kernel(j, j);
		s = model.y[i] * model.y[j];
		gamma = ai_old + s * aj_old;
		eta = 2 * kij - kii - kjj;
		if (eta < 0) {
			aj = aj_old - (model.y[j] * (Ei - Ej)) / eta;
			if (aj > H) {
				aj = H;
			} else if (aj < L) {
				aj = L;
			}
		} else {
			double vi = 0, vj = 0;
			vi = testOne(model.x[i]) - model.y[i] * ai_old * kii
					- model.y[j] * aj_old * kij;
			vj = testOne(model.x[j]) - model.y[i] * ai_old * kij
					- model.y[j] * aj_old * kjj;
			double Lobj = (1 - s) * L - kii * (-gamma * s * L + 0.5 * L * L)
					- 0.5 * kjj * L * L + +kij * L * L + model.y[i] * s * L
					* vi - model.y[j] * L * vj;
			double Hobj = (1 - s) * H - kii * (-gamma * s * H + 0.5 * H * H)
					- 0.5 * kjj * H * H + +kij * H * H + model.y[i] * s * H
					* vi - model.y[j] * H * vj;
			
			if (Lobj > Hobj + tol4) {
				aj = L;
			} else if (Lobj < Hobj - tol4) {
				aj = H;
		    } else {
				aj = aj_old;
			}
		}
			
		if (Math.abs(aj - aj_old) < tol4 * (aj + aj_old + tol4)) {
			return 0;
		}
		ai = ai_old + s * (aj_old - aj);
		computeBias(ai, aj, model.y[i], model.y[j], kii, kjj, kij);
		model.alpha[i] = ai;
		model.alpha[j] = aj;
		updateISets(i, j);
		computeUpLow();
		return 1;
	}
	
	private void updateISets(int i, int j) {
		I0.remove((Integer) i);
		I0.remove((Integer) j);
		I1.remove((Integer) i);
		I1.remove((Integer) j);
		I2.remove((Integer) i);
		I2.remove((Integer) j);
		I3.remove((Integer) i);
		I3.remove((Integer) j);
		addtoSet(i, model.alpha[i], model.y[i]);
		addtoSet(j, model.alpha[j], model.y[j]);
	}

	private void addtoSet(int i, double a, int y) {
		if (a > 0 && a < model.params.getC()) {
			I0.add(i);
		} else if (y == 1 && a == 0) {
			I1.add(i);
		} else if (y == -1 && a == model.params.getC()) {
			I2.add(i);
		} else if (y == 1 && a == model.params.getC()) {
			I3.add(i);
		} else {
			// if (y == -1 && a == 0)
			I4.add(i);
		}
	}
	
	private void computeUpLow() {
		boolean firstup = true;
		boolean firstlow = true;
		double s = 0;
		for (int i = 0; i < model.alpha.length; i++) {
			if ((model.alpha[i] > 0 && model.alpha[i] < model.params.getC())
					|| (model.alpha[i] == 0 && model.y[i] == 1)
					|| (model.alpha[i] == model.params.getC() && model.y[i] == -1)) {
				// Iup
				s = testOne(model.x[i]) - model.y[i];
				if (firstup) {
					bup = s;
					iup = i;
					firstup = false;
				} else {
					if (s > bup) {
						bup = s;
						iup = i;
					}
				}
			} else if ((model.alpha[i] > 0 && model.alpha[i] < model.params.getC())
					|| (model.alpha[i] == 0 && model.y[i] == -1)
					|| (model.alpha[i] == model.params.getC() && model.y[i] == 1)) {
				// Ilow
				s = testOne(model.x[i]) - model.y[i];
				if (firstlow) {
					blow = s;
					ilow = i;
					firstlow = false;
				} else {
					if (s < blow) {
						blow = s;
						ilow = i;
					}
				}
			}
		}
	}


}
