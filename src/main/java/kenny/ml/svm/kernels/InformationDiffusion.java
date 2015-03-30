package kenny.ml.svm.kernels;

import kenny.ml.svm.FeatureSpace;

public class InformationDiffusion implements IKernel {

	/**
	 * Information Diffusion kernel: k(x,z) = (4πt)^((|V|-1)/2) * e^(-(1/t)*arccos^2(SUM(sqrt(x_i, v_i, 1, |V|)))
	 * 		V = Vocabulary
	 * @param x first vector
	 * @param z second vector
	 * @param t 
	 * @return linear kernel value
	 */
	@Override
	public double k(FeatureSpace x, FeatureSpace z, double... params) {
		if(params.length < 1) {
			throw new IllegalArgumentException("Must contain one parameter: t");
		}
		double t = params[0];
		
		double V = z.size() > x.size() ? z.size() : x.size(); // TODO, better understand what |V| means and if this implementation is correct
		double sum = 0;
		for(int i = 1; i <= V; i++) {
			sum += Math.sqrt(x.get(i) * z.get(i));
		}
		double acos = Math.acos(sum);
		return Math.pow(4 * Math.PI * t, (V - 1) / 2) * Math.exp((-1 / 2) * acos * acos);
	}

}
