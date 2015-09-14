package kenny.ml.svm;

import java.util.HashMap;
import java.util.Map;

public class CategoryMap<T> {
	Map<T, Integer> oldnew;
	Map<Integer, T> newold;
	int lastindex;

	public CategoryMap() {
		oldnew = new HashMap<>();
		newold = new HashMap<>();
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
