package kenny.ml.markov.text.ngram;

public class NGram {
	
	private final String gram;
	
	public NGram(String gram) {
		this.gram = gram;
	}

	public String gram() {
		return gram;
	}

	@Override
	public String toString() { 
		return gram;
	}
	
	@Override
	public boolean equals(Object o) {
		if(this == o) {
			return true;
		}
		if(o instanceof NGram) {
			return gram.equals(((NGram) o).gram());
		}
		return false;
	}
	
	@Override
	public int hashCode() {
		return gram.hashCode();
	}

}
