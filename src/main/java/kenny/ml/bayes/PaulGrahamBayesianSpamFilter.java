package kenny.ml.bayes;


/**
 * right now this doesn't do much of the optimizations mentioned in PG's essay, but will soon.
 */
public class PaulGrahamBayesianSpamFilter extends NaiveBayesianFilter {

	private float spamThreshold = 0.8f;

	/**
	 * pSpam is negative, so probability of spam is 1.0 - pPositive
	 */
	@Override
	public float analyze(String content) {
		float pPositive = super.analyze(content);
		return 1.0f - pPositive;
	}
	
	public boolean isSpam(float pSpam) {
        return pSpam > spamThreshold;
    }
	
	public void setSpamThreshold(float spamThreshold) {
		this.spamThreshold = spamThreshold;
	}
	
}
