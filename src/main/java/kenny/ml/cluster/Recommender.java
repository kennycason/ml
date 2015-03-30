package kenny.ml.cluster;


import ch.lambdaj.Lambda;
import kenny.ml.cluster.distance.DistanceFunction;
import kenny.ml.cluster.feature.Category;
import kenny.ml.cluster.feature.Feature;
import kenny.ml.cluster.feature.Item;

import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Created by kenny on 2/17/14.
 */
public class Recommender {

    /**
     * which distance function to use to cluster
     */
    private DistanceFunction distanceFunction;

    /**
     * the categories to recommend against
     */
    private List<Category> categories;

    public Recommender(List<Category> categories) {
        this.categories = categories;
    }

    /**
     * gets item recommendations for a category by using a weighted
     * average of every other category's scores
     *
     */
    public List<Item> recommend(Category baseCategory, int n) {
        Lambda.forEach(this.categories).setScore(-1.0);

        Map<Item, Double> itemTotals = new HashMap<>();
        Map<String, Double> similaritySums = new HashMap<>();
        for(Category category : this.categories) {
            if(category.equals(baseCategory)) { continue; }

            double similarity = 1 - this.distanceFunction.distance(baseCategory, category);
            // throw out categories that are not similar at all
            if(similarity <= 0.0) { continue; }

            for(String itemName : category.getItemNames()) {
                // only score items that baseCategory does not already have
                // (otherwise what's the point of recommending)
                if(baseCategory.getItemNames().contains(itemName)) { continue; }

                // similarity * score
                Item item = category.getItem(itemName);
                // average all feature values (TODO investigate if this is ok)
                double value = 0; // category.getItem(itemName).getFeature(this.feature).getValue();
                for(Feature feature : category.getItem(itemName)) {
                    value += feature.getValue();
                }
                value /= category.getItem(itemName).getFeatures().size();

                if(itemTotals.get(item) == null) {
                    itemTotals.put(item, 0.0);
                }
                itemTotals.put(item, itemTotals.get(item) + value * similarity);

                // sum of similarities
                if(similaritySums.get(itemName) == null) {
                    similaritySums.put(itemName, 0.0);
                }
                similaritySums.put(itemName, similaritySums.get(itemName) + similarity);
            }
        }

        // create normalized list
        for(Map.Entry<Item, Double> itemTotal : itemTotals.entrySet()) {
            Item item = itemTotal.getKey();
            item.setScore(itemTotal.getValue() / similaritySums.get(item.getName()));
        }

        List<Item> recommendedItems = new LinkedList<>(itemTotals.keySet());

        Collections.sort(recommendedItems, Collections.reverseOrder());
        if(n > recommendedItems.size()) {
            n = recommendedItems.size();
        }
        return recommendedItems.subList(0, n);
    }

    public void setDistanceFunction(DistanceFunction distanceFunction) {
        this.distanceFunction = distanceFunction;
    }

}
