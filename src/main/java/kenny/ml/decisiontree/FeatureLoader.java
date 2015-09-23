package kenny.ml.decisiontree;

import ch.lambdaj.Lambda;
import org.apache.commons.io.IOUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

/**
 * Created by kenny
 */
public class FeatureLoader {

    public static void write(final List<FeatureSet> features, final String file) throws IOException {
        try(final FileWriter fileWriter = new FileWriter(new File(file))) {
            if(features.isEmpty()) { return; }

            // write header
            final FeatureSet first = features.get(0);
            final List<String> labels = new ArrayList<>(first.getLabels());
            Collections.sort(labels);
            IOUtils.write(Lambda.join(labels, ",") + "\n", fileWriter);

            // write rows
            for(final FeatureSet feature : features) {
                final List<String> values = new ArrayList<>();
                labels.forEach(l -> values.add(feature.get(l)));

                IOUtils.write(Lambda.join(values, ",") + "\n", fileWriter);
            }
        }
    }

    public static List<FeatureSet> read(final String file) throws IOException {
        return read(file, Collections.emptySet());
    }

    public static List<FeatureSet> read(final String file, final Set<String> whiteList) throws IOException {
        final List<String> lines = IOUtils.readLines(new FileInputStream(new File(file)));
        final String header = lines.remove(0);
        final String[] labels = header.split(",");

        final List<FeatureSet> features = new ArrayList<>();
        for(String line : lines) {
            final String[] values = line.split(",");
            final FeatureSet feature = new FeatureSet();
            for(int i = 0; i < labels.length; i++) {
                if(whiteList.isEmpty() || whiteList.contains(labels[i])) {
                    feature.set(labels[i], values[i]);
                }
            }
            features.add(feature);
        }
        return features;
    }

}
