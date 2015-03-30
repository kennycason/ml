package kenny.ml.cluster.kmeans.initializer;


import kenny.ml.cluster.feature.Item;
import kenny.ml.cluster.kmeans.Centroid;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by kenny on 2/21/14.
 */
public class KMeansPlusPlusInitializer implements Initializer {

    /**
     * TODO
     * http://en.wikipedia.org/wiki/K-means++
     * 1. Choose one center uniformly at random from among the data points.
     * 2. For each data point x, compute D(x), the distance between x and the nearest center that has already been chosen.
     * 3. Choose one new data point at random as a new center, using a weighted probability distribution where a point x is chosen with probability proportional to D(x)2.
     * 4. Repeat Steps 2 and 3 until k centers have been chosen.
     */
    @Override
    public List<Centroid> initialize(List<Item> items, int k) {
        if(items.size() <= k) {
            return mapToCentroids(items);
        }
        // get two random points
        List<Integer> indices = new ArrayList<>();
        for(int i = 0; i < items.size(); i++) { indices.add(i); }
        Collections.shuffle(indices);
        Item item1 = items.get(indices.get(0));
        Item item2 = items.get(indices.get(1));


        return null;
    }


    private List<Centroid> mapToCentroids(List<Item> items) {
        List<Centroid> centroids = new LinkedList<>();
        for(Item item : items) {
            centroids.add(new Centroid(item));
        }
        return centroids;
    }

}
