package kenny.ml.decisiontree;

import ch.lambdaj.Lambda;
import com.datarank.api.DataRank;
import com.datarank.api.request.filters.FeatureMode;
import com.datarank.api.request.filters.Gender;
import com.datarank.api.request.filters.Limit;
import com.datarank.api.response.containers.Comment;
import com.datarank.api.response.envelopes.CommentFeaturesEnvelope;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static ch.lambdaj.Lambda.on;

/**
 * Created by kenny
 */
public class DataRankTopicFeatureExtractor {

    public static List<FeatureSet> downloadComments(final DataRank dataRank, final String slug, final int max) {
        System.out.println("Loading features for [" + slug + "]");

        final List<Comment> comments = dataRank.comments(slug, Gender.MALE_OR_FEMALE, new Limit(max)).getBody().getComments();
        System.out.println("Loaded [" + comments.size() + "] features");
        final List<FeatureSet> features = buildFeatures(comments);

        System.out.println("Finished loading a total of [" + features.size() + "] features");
        return features;
    }

    public static CommentFeaturesEnvelope downloadFeatures(final DataRank dataRank, final String slug, final int max, final FeatureMode featureMode) {
        System.out.println("Loading features for [" + slug + "]");

        final CommentFeaturesEnvelope commentFeaturesEnvelope = dataRank.commentsFeatures(slug, new Limit(max), featureMode).getBody();
        System.out.println("Loaded [" + commentFeaturesEnvelope.getFeatureNames().size() + "] features for " + commentFeaturesEnvelope.getFeatures().size() + " comments.");

        return commentFeaturesEnvelope;
    }

    private static List<FeatureSet> buildFeatures(final List<Comment> comments) {
        final List<FeatureSet> features = new ArrayList<>();
        comments.forEach(c -> features.add(buildFeature(c)));
        return features;
    }

    private static FeatureSet buildFeature(final Comment comment) {
        final FeatureSet featureSet = new FeatureSet();
        final Set<Long> themeIds = new HashSet<>(Lambda.extract(comment.getThemes(), on(Comment.Theme.class).getId()));
        featureSet.set("GENDER", comment.getUser().getGender());
        featureSet.set("AGE", comment.getUser().getAge());
        featureSet.set("SENTIMENT", comment.getSentiment());
        featureSet.set("DATASOURCE", comment.getDatasource().getHuman());
        featureSet.set("DATASOURCE_TYPE", comment.getDatasource().getType());

        //feature.set("HAS_BIO", String.valueOf(isNotBlank(comment.getUser().getBio()))); // don't use has-bio as there is a minor bug
        featureSet.set("THEME_PACKAGING", String.valueOf(themeIds.contains(1L)));
        featureSet.set("THEME_PRICE", String.valueOf(themeIds.contains(2L)));
        featureSet.set("THEME_SWITCHING", String.valueOf(themeIds.contains(4L)));
        featureSet.set("THEME_AVAILABILITY", String.valueOf(themeIds.contains(65L)));
        featureSet.set("THEME_PERFORMANCE", String.valueOf(themeIds.contains(75L)));
        featureSet.set("THEME_NPS", String.valueOf(themeIds.contains(303L)));
        featureSet.set("THEME_PURCHASE_INTENT", String.valueOf(themeIds.contains(304L)));
        featureSet.set("THEME_SHOPPER_EXPERIENCE", String.valueOf(themeIds.contains(974L)));

        // test features for control experiment
        featureSet.set("IS_AMAZON", String.valueOf(comment.getDatasource().getHuman().equals("Amazon")));

        return featureSet;
    }

}
