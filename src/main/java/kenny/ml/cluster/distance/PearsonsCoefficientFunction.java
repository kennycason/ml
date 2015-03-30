package kenny.ml.cluster.distance;


import kenny.ml.cluster.feature.Category;
import kenny.ml.cluster.feature.Item;
import kenny.ml.cluster.math.Normalize;

import java.util.Set;

/**
 * Created by kenny on 2/16/14.
 */
public class PearsonsCoefficientFunction implements DistanceFunction {

    /**
     * Only allow pearson's coefficient on one feature/item
     */
    private String feature;

    public PearsonsCoefficientFunction(String feature) {
        this.feature = feature;
    }

    @Override
    public double distance(Item item1, Item item2) {
        System.out.println("Not Implemented");
        return 0.0;
    }

    @Override
    public double distance(Category category1, Category category2) {
        Set<String> intersection = category1.intersection(category2);
        if(intersection.size() == 0) {
            return 0.0;
        }
        double sum1 = 0;
        double sum2 = 0;
        double sum1Sq = 0;
        double sum2Sq = 0;
        double pSum = 0;
        for(String item : intersection) {
            // sum up all preferences
            sum1 += category1.getItem(item).getFeature(feature).getValue();
            sum2 += category2.getItem(item).getFeature(feature).getValue();

            // sum up squares
            sum1Sq += Math.pow(category1.getItem(item).getFeature(feature).getValue(), 2);
            sum2Sq += Math.pow(category2.getItem(item).getFeature(feature).getValue(), 2);

            // sum up products
            pSum += category1.getItem(item).getFeature(feature).getValue() * category2.getItem(item).getFeature(feature).getValue();
        }

        // calculate Pearson's score
        double num = pSum - (sum1 * sum2 / (double)intersection.size());
        double den = Math.sqrt(
                (sum1Sq - Math.pow(sum1, 2) / intersection.size()) *
                        (sum2Sq - Math.pow(sum2, 2) / intersection.size()));
        if(den == 0) { return 0; }
        //return num / den;
        return 1 - Normalize.linear(num / den, -1, 1); // return similary coefficient as distance metric
    }

    @Override
    public String toString() {
        return "PearsonsCoefficientFunction{" +
                "feature='" + feature + '\'' +
                '}';
    }
}
