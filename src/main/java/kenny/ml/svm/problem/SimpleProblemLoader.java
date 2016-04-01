package kenny.ml.svm.problem;

import kenny.ml.svm.FeatureSpace;
import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class SimpleProblemLoader implements ProblemLoader {

    private static final Logger LOGGER = Logger.getLogger(SimpleProblemLoader.class);

    /**
     * Loads a binary problem from file, i.e. having 2 classes.
     */
    @Override
    public Problem load(String fileName) throws IOException {
        LOGGER.debug("Loading problem: " + fileName);
        Problem problem = new Problem();
        List<Integer> classes = new ArrayList<>();
        List<FeatureSpace> examples = new ArrayList<>();

        final StringBuilder stringBuilder = new StringBuilder();
        final List<String> rows = IOUtils.readLines(Thread.currentThread().getContextClassLoader().getResourceAsStream(fileName));
        for(String row : rows) {
            String[] elems = row.split(" ");
            // Category:
            Integer cat = Integer.parseInt(elems[0]);
            stringBuilder.append(cat + ": ");
            problem.categories.add(cat);
            if (problem.categories.size() > 2) {
                throw new IllegalArgumentException("only 2 classes allowed!");
            }
            classes.add(problem.categories.getNewCategoryOf(cat));
            // Index/value pairs:
            examples.add(parseRow(elems, stringBuilder));
        }
        problem.x = new FeatureSpace[examples.size()];
        problem.y = new int[examples.size()];
        for (int i = 0; i < examples.size(); i++) {
            problem.x[i] = examples.get(i);
            problem.y[i] = 2 * classes.get(i) - 1; // 0,1 => -1,1
        }
        problem.trainingSize = examples.size();
        LOGGER.debug(stringBuilder);
        return problem;
    }

    /**

     * @param row The already split row on spaces.
     * @return The corresponding FeatureSpace.
     */
    private FeatureSpace parseRow(String[] row, final StringBuilder stringBuilder) {
        FeatureSpace example = new FeatureSpace();
        for (int i = 1; i < row.length; i++) {
            stringBuilder.append(row[i]).append(' ');
            double value = Double.parseDouble(row[i]);
            example.set(i - 1, value);
        }
        stringBuilder.append('\n');
        return example;
    }
}
