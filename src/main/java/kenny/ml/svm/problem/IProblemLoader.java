package kenny.ml.svm.problem;

import java.io.IOException;

public interface IProblemLoader {
	
	Problem load(String fileName) throws IOException;

}
