package kenny.ml.svm.lib.math.rounding;

public class ThresholdRoundingStrategy implements IRoundingStrategy {

	private double threshold = 0.5;
	
	public ThresholdRoundingStrategy(double threshold) {
		this.threshold = threshold;
	}
	
	public double round(double val) {
		return val >= threshold ? Math.ceil(val) : (long) val;
	}
	
}
