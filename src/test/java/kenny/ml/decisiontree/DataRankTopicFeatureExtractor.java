package kenny.ml.decisiontree;

import ch.lambdaj.Lambda;
import com.datarank.api.DataRank;
import com.datarank.api.request.filters.Gender;
import com.datarank.api.request.filters.Limit;
import com.datarank.api.request.filters.Page;
import com.datarank.api.response.containers.Comment;
import org.apache.commons.io.IOUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static ch.lambdaj.Lambda.on;

/**
 * Created by kenny
 */
public class DataRankTopicFeatureExtractor {

    public static List<Feature> download(final DataRank dataRank, final String slug, final int max) {
        System.out.println("Loading features for [" + slug + "]");
        final List<Feature> features = new ArrayList<>();
        int page = 1;
        for(;;) {
            final List<Comment> comments = dataRank.comments(slug, Gender.MALE_OR_FEMALE, new Page(page++), new Limit(500)).getBody().getComments();
            System.out.println("Loaded [" + comments.size() + "] features");
            if(comments.isEmpty()) { break; }
            features.addAll(buildFeatures(comments));
            if(features.size() >= max) { break; }
            try {
                Thread.sleep(500L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println("Finished loading a total of [" + features.size() + "] features");
        return features;
    }

    public static void write(final List<Feature> features, final String file) throws IOException {
        try(final FileWriter fileWriter = new FileWriter(new File(file))) {
            if(features.isEmpty()) { return; }

            // write header
            final Feature first = features.get(0);
            final List<String> labels = new ArrayList<>(first.getLabels());
            Collections.sort(labels);
            IOUtils.write(Lambda.join(labels, ",") + "\n", fileWriter);

            // write rows
            for(final Feature feature : features) {
                final List<String> values = new ArrayList<>();
                labels.forEach(l -> values.add(feature.get(l)));

                IOUtils.write(Lambda.join(values, ",") + "\n", fileWriter);
            }
        }
    }

    public static List<Feature> read(final String file) throws IOException {
        return read(file, Collections.emptySet());
    }

    public static List<Feature> read(final String file, final Set<String> whiteList) throws IOException {
        final List<String> lines = IOUtils.readLines(new FileInputStream(new File(file)));
        final String header = lines.remove(0);
        final String[] labels = header.split(",");

        final List<Feature> features = new ArrayList<>();
        for(String line : lines) {
            final String[] values = line.split(",");
            final Feature feature = new Feature();
            for(int i = 0; i < labels.length; i++) {
                if(whiteList.isEmpty() || whiteList.contains(labels[i])) {
                    feature.set(labels[i], values[i]);
                }
            }
            features.add(feature);
        }
        return features;
    }

    private static List<Feature> buildFeatures(final List<Comment> comments) {
        final List<Feature> features = new ArrayList<>();
        comments.forEach(c -> features.add(buildFeature(c)));
        return features;
    }

    private static Feature buildFeature(final Comment comment) {
        final Feature feature = new Feature();
        final Set<Long> themeIds = new HashSet<>(Lambda.extract(comment.getThemes(), on(Comment.Theme.class).getId()));
        feature.set("GENDER", comment.getUser().getGender());
        feature.set("AGE", comment.getUser().getAge());
        feature.set("SENTIMENT", comment.getSentiment());
        feature.set("DATASOURCE", comment.getDatasource().getHuman());
        feature.set("DATASOURCE_TYPE", comment.getDatasource().getType());

        //feature.set("HAS_BIO", String.valueOf(isNotBlank(comment.getUser().getBio()))); // don't use has-bio as there is a minor bug
        feature.set("THEME_PACKAGING", String.valueOf(themeIds.contains(1L)));
        feature.set("THEME_PRICE", String.valueOf(themeIds.contains(2L)));
        feature.set("THEME_SWITCHING", String.valueOf(themeIds.contains(4L)));
        feature.set("THEME_AVAILABILITY", String.valueOf(themeIds.contains(65L)));
        feature.set("THEME_PERFORMANCE", String.valueOf(themeIds.contains(75L)));
        feature.set("THEME_NPS", String.valueOf(themeIds.contains(303L)));
        feature.set("THEME_PURCHASE_INTENT", String.valueOf(themeIds.contains(304L)));
        feature.set("THEME_SHOPPER_EXPERIENCE", String.valueOf(themeIds.contains(974L)));

        // test features for control experiment
        feature.set("IS_AMAZON", String.valueOf(comment.getDatasource().getHuman().equals("Amazon")));

        return feature;
    }

}
