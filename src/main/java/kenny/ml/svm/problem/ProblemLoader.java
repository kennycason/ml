package kenny.ml.svm.problem;

import java.io.IOException;

public interface ProblemLoader {
	
	Problem load(String fileName) throws IOException;

}
