
package kenny.ml.bayes;

import org.apache.log4j.Logger;

public class Word {

    private static final Logger LOGGER = Logger.getLogger(Word.class);

	private String word; // The String itself

	private int countNegative; // The total times it appears in "negative" messages

	private int countPositive; // The total times it appears in "positive" messages

	private float rNegative; // negative count / total negative words

	private float rPositive; // positive count / total positive words

	private float pNeg; // probability this word is negative
	
	private float pPositive; // probability this word is positive

	// Create a word, initialize all vars to 0
	public Word(String s) {
		word = s;
		countNegative = 0;
		countPositive = 0;
		rNegative = 0.0f;
		rPositive = 0.0f;
		pNeg = 0.0f;
		pPositive = 0.0f;
	}

	// Increment negative counter
	public void countNegative() {
		countNegative++;
	}

	// Increment positive counter
	public void countPositive() {
		countPositive++;
	}

	public void calcNegativeProb(int total) {
		calcNegativeProb(total, 1.0f);
	}

	// Computer how often this word is negative
	public void calcNegativeProb(int total, float biasNegative) {
		if (total > 0) {
			rNegative = biasNegative * countNegative / (float) total;
		}
	}
	
	public void calcPositiveProb(int total) {
		calcPositiveProb(total, 1.0f);
	}

	/**
	 *  Computer how often this word is positive
	 *  recommend: bias by 2.0f to prevent false positives
	 * @param total
	 * @param biasPositive
	 */
	public void calcPositiveProb(int total, float biasPositive) {
		if (total > 0) {
			rPositive = biasPositive * countPositive / (float) total; 
		}
	}

	// Implement bayes rules to computer how likely this word is "negative"
	public void finalizeProb() {
		if (rPositive + rNegative > 0) {
			pNeg = rNegative / (rNegative + rPositive);
			pPositive = rPositive / (rNegative + rPositive);
		}
		if (pNeg < 0.01f) {
			pNeg = 0.01f;
		} else if (pNeg > 0.99f) {
			pNeg = 0.99f;
		}
		if(pPositive < 0.01f) {
			pPositive = 0.01f;
		} else if(pPositive > 0.99f) {
			pPositive = 0.99f;
		}
        if(LOGGER.isTraceEnabled()) {
		    LOGGER.trace("word: " + word + " pNegative: " + pNeg + " pPositive: " + pPositive);
        }
	}

	// The "interesting" rating for a word is
	// How different from 0.5 it is
	public float interesting() {
		return Math.abs(0.5f - pNeg);
	}

	public float getRPositive() {
		return rPositive;
	}

	public float getRNegative() {
		return rNegative;
	}

	public float getPNegative() {
		return pNeg;
	}
	
	public void setPNegative(float f) {
		pNeg = f;
	}

	public float getPPositive() {
		return pPositive;
	}
	
	public void setPPositive(float f) {
		pPositive = f;
	}
	
	public String getWord() {
		return word;
	}

}
