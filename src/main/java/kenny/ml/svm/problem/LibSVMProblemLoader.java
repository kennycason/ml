package kenny.ml.svm.problem;

import kenny.ml.svm.FeatureSpace;
import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class LibSVMProblemLoader implements ProblemLoader {

    /**
     * Loads a binary problem from file, i.e. having 2 classes.
     */
    @Override
    public Problem load(String fileName) throws IOException {
        System.out.println("Loading problem: " + fileName);
        Problem problem = new Problem();

        ArrayList<Integer> classes = new ArrayList<>();
        ArrayList<FeatureSpace> examples = new ArrayList<>();

        final List<String> rows = IOUtils.readLines(Thread.currentThread().getContextClassLoader().getResourceAsStream(fileName));
        for(String row : rows) {
            String[] elems = row.split(" ");

            if(elems[0].startsWith("+")) { // allow +1
                elems[0] = elems[0].substring(1);
            }
            // Category:
            Integer cat = (int) Double.parseDouble(elems[0]);
            System.out.print(cat + " ");
            problem.categories.add(cat);
            if (problem.categories.size() > 2) {
                throw new IllegalArgumentException("only 2 classes allowed!");
            }
            classes.add(problem.categories.getNewCategoryOf(cat));
            // Index/value pairs:
            examples.add(parseRow(elems));
        }
        problem.x = new FeatureSpace[examples.size()];
        problem.y = new int[examples.size()];
        for (int i = 0; i < examples.size(); i++) {
            problem.x[i] = examples.get(i);
            problem.y[i] = 2 * classes.get(i) - 1; // 0,1 => -1,1
        }
        problem.trainingSize = examples.size();

        return problem;
    }

    /**

     * @param row The already split row on spaces.
     * @return The corresponding FeatureSpace.
     */
    private FeatureSpace parseRow(String[] row) {
        FeatureSpace example = new FeatureSpace();

        for (int i = 1; i < row.length; i++) {
            //	if(row[i].length() > 0) { // in case double space used
            String[] vals = row[i].split(":");
            System.out.print(vals[0] + ":" + vals[1] + " ");
            int index = Integer.parseInt(vals[0]) - 1;
            double value = Double.parseDouble(vals[1]);

            example.set(index, value);
            //	}
        }
        System.out.println();
        return example;
    }
}
