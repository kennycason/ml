package kenny.ml.svm;

import java.util.HashMap;

public class CategoryMap<T> {
	HashMap<T, Integer> oldnew;
	HashMap<Integer, T> newold;
	int lastindex;

	public CategoryMap() {
		oldnew = new HashMap<T, Integer>();
		newold = new HashMap<Integer, T>();
		lastindex = -1;
	}

	public int size() {
		return oldnew.size();
	}

	public boolean isEmpty() {
		return oldnew.isEmpty();
	}

	public void addCategory(T cat) {
		if (!oldnew.containsKey(cat)) {
			lastindex++;
			oldnew.put(cat, lastindex);
			newold.put(lastindex, cat);
		}
	}

	public T getOldCategoryOf(int cat) {
		return newold.get(cat);
	}

	public int getNewCategoryOf(T cat) {
		return oldnew.get(cat);
	}
}
