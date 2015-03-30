package kenny.ml.cluster;


import ch.lambdaj.Lambda;
import kenny.ml.cluster.distance.DistanceFunction;
import kenny.ml.cluster.distance.PearsonsCoefficientFunction;
import kenny.ml.cluster.feature.Category;
import kenny.ml.cluster.feature.Item;
import kenny.ml.cluster.feature.impl.RatingFeature;
import org.junit.Test;

import java.util.List;

/**
 * Created by kenny on 2/16/14.
 */
public class MovieCriticNearestNeighborTest {

    private static final PearsonsCoefficientFunction DISTANCE_FUNCTION = new PearsonsCoefficientFunction("RATING");

    private static final List<Category> MOVIE_CRITICS = SampleCorpus.buildMoviedCritics();

    private static final Category TOBY = buidToby();

    private static final Category KENNY = buildKenny();

    @Test
    public void similarCritics() {
         similarCritics(KENNY, DISTANCE_FUNCTION, 3);
         similarCritics(TOBY, DISTANCE_FUNCTION, 3);
    }

    private void similarCritics(Category person, DistanceFunction distanceFunction, int n) {
        CategoryNearestNeighbor nearestNeighbor = new CategoryNearestNeighbor(MOVIE_CRITICS);
        nearestNeighbor.setDistanceFunction(distanceFunction);

        List<Category> sortedPeople = nearestNeighbor.topMatches(person, n);
        System.out.println("Top " + n + " for: " + person);
        System.out.println("Using: " + distanceFunction);
        System.out.println(Lambda.join(sortedPeople, "\n"));
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

    private static Category buildKenny() {
        Category kenny = new Category("kenny");
        kenny.addItem(new Item("Lady in the Water", new RatingFeature(2.0)));
        kenny.addItem(new Item("Snakes on a plane", new RatingFeature(1.5)));
        kenny.addItem(new Item("Just My Luck", new RatingFeature(1.5)));
        kenny.addItem(new Item("Superman Returns", new RatingFeature(5.0)));
        kenny.addItem(new Item("You, Me and Dupree", new RatingFeature(1.0)));
        kenny.addItem(new Item("The Night Listener", new RatingFeature(5.0)));

        return kenny;
    }

}
