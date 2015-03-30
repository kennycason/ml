package kenny.ml.cluster.distance;


import kenny.ml.cluster.feature.Category;
import kenny.ml.cluster.feature.Item;

import java.util.Set;

/**
 * Created by kenny on 2/16/14.
 */
public class DiscreteDistanceFunction implements DistanceFunction {

    @Override
    public double distance(Item item1, Item item2) {
        Set<String> intersection = item1.intersection(item2);
        if(intersection.size() == 0) {
            return 0.0;
        }

        for(String feature : intersection) {
            if(item1.getFeature(feature) != item2.getFeature(feature)) {
                return 1.0;
            }
        }
        return 0.0;
    }

    @Override
    public double distance(Category category1, Category category2) {
        System.out.println("Not Implemented");
        return 0.0;
    }

    @Override
    public String toString() {
        return "DiscreteDistanceFunction";
    }

}
