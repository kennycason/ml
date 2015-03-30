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
public class RandomInitializer implements Initializer {

    @Override
    public List<Centroid> initialize(List<Item> items, int k) {
        if(items.size() <= k) {
            return mapToCentroids(items);
        }
        // get two random points
        List<Integer> indices = new ArrayList<>();
        for(int i = 0; i < items.size(); i++) { indices.add(i); }
        Collections.shuffle(indices);

        List<Centroid> centroids = new LinkedList<>();
        List<Item> toRemove = new LinkedList<>();
        for(int i = 0; i < k; i++) {
            Item randomItem = items.get(indices.get(i));
            centroids.add(new Centroid(randomItem));
            toRemove.add(randomItem);
        }
        // now remove from items because they now form the centroids
        for(Item remove : toRemove) {
            items.remove(remove);
        }
        return centroids;
    }

    private List<Centroid> mapToCentroids(List<Item> items) {
        List<Centroid> centroids = new LinkedList<>();
        for(Item item : items) {
            centroids.add(new Centroid(item));
        }
        return centroids;
    }

}
