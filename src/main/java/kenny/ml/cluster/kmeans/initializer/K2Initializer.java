package kenny.ml.cluster.kmeans.initializer;


import kenny.ml.cluster.distance.DistanceFunction;
import kenny.ml.cluster.feature.Item;
import kenny.ml.cluster.kmeans.Centroid;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by kenny on 2/21/14.
 */
public class K2Initializer implements Initializer {

    private DistanceFunction distanceFunction;

    public K2Initializer(DistanceFunction distanceFunction) {
        this.distanceFunction = distanceFunction;
    }

    /**
     * for k = 2, lets find the two furthest apart items
     */
    @Override
    public List<Centroid> initialize(List<Item> items, int k) {
        Item max1 = null;
        Item max2 = null;
        double max = Double.MIN_NORMAL;
        for(Item item1 : items) {
            for(Item item2 : items) {
                if(item1.equals(item2)) { continue; }
                double distance = this.distanceFunction.distance(item1, item2);
                if(distance > max) {
                    max = distance;
                    max1 = item1;
                    max2 = item2;
                }
            }
        }
        items.remove(max1);
        items.remove(max2);
        return new LinkedList<>(Arrays.asList(new Centroid(max1), new Centroid(max2)));
    }

}
