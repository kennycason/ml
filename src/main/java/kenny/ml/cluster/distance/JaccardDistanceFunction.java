package kenny.ml.cluster.distance;


import kenny.ml.cluster.feature.Category;
import kenny.ml.cluster.feature.Item;

import java.util.Set;

/**
 * Created by kenny on 2/16/14.
 */
public class JaccardDistanceFunction implements DistanceFunction {

    private final double positiveThreshold;

    public JaccardDistanceFunction(double positiveThreshold) {
        this.positiveThreshold = positiveThreshold;
    }

    /**
     * J(A,B) = |A ∩ B| / |A ∪ B|
     * dJ(A,B) = 1 - J(A,B)
     *
     * M11 represents the total number of attributes where A and B both have a value of 1.
     * M01 represents the total number of attributes where the attribute of A is 0 and the attribute of B is 1.
     * M10 represents the total number of attributes where the attribute of A is 1 and the attribute of B is 0.
     * M00 represents the total number of attributes where A and B both have a value of 0.
     * dJ = (M01 + M10) / (M01 + M10 + M11)
     */
    @Override
    public double distance(Item item1, Item item2) {
        Set<String> intersection = item1.intersection(item2);

        double m11 = 0;
        double m01 = 0;
        double m10 = 0;
        double m00 = 0; // unused
        for(String feature : intersection) {
            boolean a = item1.getFeature(feature).getValue() >= positiveThreshold ? true : false;
            boolean b = item2.getFeature(feature).getValue() >= positiveThreshold ? true : false;
            if(a & b) {
                m11++;
            } else if(!a & b) {
                m01++;
            } else if(a & !b) {
                m10++;
            } else {
                m00++;
            }
        }
        if(m01 + m10 + m11 == 0) {
            return 0;
        }
        return (m01 + m10) / (m01 + m10 + m11);
    }

    @Override
    public double distance(Category category1, Category category2) {
        System.out.println("Not Implemented");
        return 0.0;
    }

    @Override
    public String toString() {
        return "JaccardDistanceFunction{" +
                "positiveThreshold=" + positiveThreshold +
                '}';
    }
}
