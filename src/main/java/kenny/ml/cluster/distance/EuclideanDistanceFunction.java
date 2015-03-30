package kenny.ml.cluster.distance;


import kenny.ml.cluster.feature.Category;
import kenny.ml.cluster.feature.Item;

import java.util.Set;

/**
 * Created by kenny on 2/16/14.
 */
public class EuclideanDistanceFunction implements DistanceFunction {

    @Override
    public double distance(Item item1, Item item2) {
        Set<String> intersection = item1.intersection(item2);

        double sumSq = 0;
        for(String feature : intersection) {
            sumSq += Math.pow(item1.getFeature(feature).getValue() - item2.getFeature(feature).getValue(), 2);
        }
        return Math.sqrt(sumSq);
    }

    @Override
    public double distance(Category category1, Category category2) {
        System.out.println("Not Implemented");
        return 0.0;
    }

    @Override
    public String toString() {
        return "EuclideanDistanceFunction";
    }

}
