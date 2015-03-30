package kenny.ml.svm.lib.math;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class MathUtils {
	
	public static double percent(double a, double b) {
		return  (a -b) / b * 100; //100.0 - (a/b * 100.0);
	}
	
	public static double mean(double[] m) {
	    double sum = 0;
	    for (int i = 0; i < m.length; i++) {
	        sum += m[i];
	    }
	    return sum / m.length;
	}
	
	public static double mean(List<Double> m) {
	    double sum = 0;
	    for (Double d : m) {
	        sum += d;
	    }
	    return sum / m.size();
	}
	
	// the array double[] m MUST BE SORTED
	public static double median(double[] m) {
		Arrays.sort(m);
	    int middle = m.length/2;
	    if (m.length%2 == 1) {
	        return m[middle];
	    } else {
	        return (m[middle-1] + m[middle]) / 2.0;
	    }
	}
	
	// the array double[] m MUST BE SORTED
	public static double median(List<Double> m) {
		//Arrays.sort(m);
		Collections.sort(m);
	    int middle = m.size()/2;
	    if (m.size()%2 == 1) {
	        return m.get(middle);
	    } else {
	        return (m.get(middle - 1) + m.get(middle)) / 2.0;
	    }
	}

}
