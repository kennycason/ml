package kenny.ml.cluster;


import ch.lambdaj.Lambda;
import kenny.ml.cluster.distance.PearsonsCoefficientFunction;
import kenny.ml.cluster.feature.Category;
import org.junit.Test;

import java.util.List;

/**
 * Created by kenny on 2/16/14.
 */
public class MovieRecommenderTest {

    private static final PearsonsCoefficientFunction DISTANCE_FUNCTION = new PearsonsCoefficientFunction("RATING");

    private static final List<Category> MOVIE_CRITICS = SampleCorpus.buildMoviedCritics();

    private static final Category TOBY = buidToby();

    /**
     * If using Pearson's coefficient  as a normalized distance metric, instead of similarity
     * FeatureSet{name=The Night Listener, score=3.3207995980457397, features=RatingFeature{value=3.0 / 5.0}}
     * FeatureSet{name=Lady in the Water, score=2.856947184505091, features=RatingFeature{value=2.5 / 5.0}}
     * FeatureSet{name=Just My Luck, score=2.4442086481691603, features=RatingFeature{value=3.0 / 5.0}}
     *
     * If using Pearson's coefficient as a similarity metric, instead of distance
     * Recommend for: Toby
     * FeatureSet{name=The Night Listener, score=3.3477895267131017, features=RatingFeature{value=3.0 / 5.0}}
     * FeatureSet{name=Lady in the Water, score=2.8325499182641614, features=RatingFeature{value=2.5 / 5.0}}
     * FeatureSet{name=Just My Luck, score=2.530980703765565, features=RatingFeature{value=3.0 / 5.0}}
     */
    @Test
    public void recommendMovies() {
        Recommender recommender = new Recommender(MOVIE_CRITICS);
        recommender.setDistanceFunction(DISTANCE_FUNCTION);

        System.out.println("Recommend for: " + TOBY.getName());
        System.out.println(Lambda.join(recommender.recommend(TOBY, 3), "\n"));
        System.out.println();

    }

    private static Category buidToby() {
        for(Category category : MOVIE_CRITICS) {
            if(category.getName().equals("Toby")) {
                return category;
            }
        }
        return null;
    }

}
