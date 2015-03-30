package kenny.ml.cluster.distance;


import kenny.ml.cluster.feature.Category;
import kenny.ml.cluster.feature.Item;
import kenny.ml.cluster.math.Normalize;

import java.util.Set;

/**
 * Created by kenny on 2/16/14.
 */
public class CosineDistanceFunction implements DistanceFunction {

    @Override
    public double distance(Item item1, Item item2) {
        Set<String> intersection = item1.intersection(item2);

        double sumProd = 0;
        for(String feature : intersection) {
            sumProd += item1.getFeature(feature).getValue() * item2.getFeature(feature).getValue();
        }

        double sum1sq = 0;
        for(String feature : intersection) {
            sum1sq += item1.getFeature(feature).getValue() * item1.getFeature(feature).getValue();
        }
        sum1sq = Math.sqrt(sum1sq);

        double sum2sq = 0;
        for(String feature : intersection) {
            sum2sq += item2.getFeature(feature).getValue() * item2.getFeature(feature).getValue();
        }
        sum2sq = Math.sqrt(sum2sq);

        if(sum1sq * sum2sq == 0) {
            return 1.0;
        }
        double cosineSimilarity = sumProd / (sum1sq * sum2sq); // -1 opposite, 0 indifferent, 1 exactly the same
        return 1 - Normalize.linear(cosineSimilarity, -1, 1); // scale to 0 same 1 opposites
    }

    @Override
    public double distance(Category category1, Category category2) {
        System.out.println("Not Implemented");
        return 0.0;
    }

    @Override
    public String toString() {
        return "CosineDistanceFunction";
    }

}
