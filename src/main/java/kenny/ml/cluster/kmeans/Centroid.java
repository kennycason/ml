package kenny.ml.cluster.kmeans;


import ch.lambdaj.Lambda;
import kenny.ml.cluster.feature.GeneralFeature;
import kenny.ml.cluster.feature.Item;

import java.util.HashSet;
import java.util.Set;

import static ch.lambdaj.Lambda.on;

/**
 * Created by kenny on 2/21/14.
 */
public class Centroid {

    private Set<Item> items;

    private Item center; // the original item clustered around

    private Item meanItem; // mean of the items

    public Centroid(Item item) {
        this.center = item;
        this.items = new HashSet<>();
        calculateMeanItem();
    }

    public void add(Item item) {
        this.items.add(item);
        calculateMeanItem();
    }

    public void remove(Item item) {
        this.items.remove(item);
        calculateMeanItem();
    }

    private void calculateMeanItem() {
        meanItem = new Item(getName());
        Set<String> featureNames = this.center.getFeatures();
        for(String featureName : featureNames) {
            double sum = center.getFeature(featureName).getValue();
            for(Item item : items) {
                sum += item.getFeature(featureName).getValue();
            }
            meanItem.addFeature(new GeneralFeature(featureName, sum / (items.size() + 1)));
        }
    }

    public String getName() {
        return "{" + center.getName() + ", " + Lambda.join(Lambda.extract(items, on(Item.class).getName())) + "}";
    }

    public Item center() {
        return meanItem;
    }

    public int size() {
        return items.size() + 1; // add 1 to include original item in centroid
    }

    @Override
    public String toString() {
        return "{" + center.getName() + ", " + Lambda.join(items) + " }";
    }

}
