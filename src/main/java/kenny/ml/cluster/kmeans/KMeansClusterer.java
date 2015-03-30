package kenny.ml.cluster.kmeans;


import kenny.ml.cluster.distance.DistanceFunction;
import kenny.ml.cluster.distance.EuclideanDistanceFunction;
import kenny.ml.cluster.feature.Item;
import kenny.ml.cluster.kmeans.initializer.Initializer;
import kenny.ml.cluster.kmeans.initializer.K2Initializer;
import kenny.ml.cluster.kmeans.initializer.RandomInitializer;

import java.util.List;

/**
 * Created by kenny on 2/21/14.
 */
public class KMeansClusterer {

    private Initializer initializer;

    private DistanceFunction distanceFunction;

    private int maxIterations;

    private int k;

    public KMeansClusterer(int k) {
        this.initializer = new RandomInitializer();
        this.distanceFunction = new EuclideanDistanceFunction();
        this.maxIterations = 10;
        this.k = k;
        if(k == 1) { throw new IllegalArgumentException("K must be >= 2!"); }
    }

    public List<Centroid> cluster(List<Item> items) {
        List<Centroid> centroids;
        if(k == 2) {
            centroids = new K2Initializer(this.distanceFunction).initialize(items, k);
        } else {
            centroids = this.initializer.initialize(items, k);
        }
        for(int iteration = 0; iteration < maxIterations; iteration++) {
            assignItemsToCentroid(centroids, items);
        }
        return centroids;
    }

    private void assignItemsToCentroid(List<Centroid> centroids, List<Item> items) {
        for(Item item : items) {
            Centroid nearest = null;
            double min = Double.MAX_VALUE;
            for(Centroid centroid : centroids) {
                double distance = this.distanceFunction.distance(item, centroid.center());
                if(distance < min) {
                    min = distance;
                    nearest = centroid;
                }
            }
            nearest.add(item);
            // remove from others
            for(Centroid centroid : centroids) {
                if(centroid.equals(nearest)) { continue; }
                centroid.remove(item);
            }
        }
    }

    public void setDistanceFunction(DistanceFunction distanceFunction) {
        this.distanceFunction = distanceFunction;
    }

    public void setMaxIterations(int maxIterations) {
        this.maxIterations = maxIterations;
    }

    public void setInitializer(Initializer initializer) {
        this.initializer = initializer;
    }

}
