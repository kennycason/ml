package kenny.ml.cluster;


import ch.lambdaj.Lambda;
import kenny.ml.cluster.distance.DistanceFunction;
import kenny.ml.cluster.feature.Item;

import java.util.Collections;
import java.util.List;

/**
 * Created by kenny on 2/16/14.
 */
public class ItemNearestNeighbor {

    private DistanceFunction distanceFunction;

    private List<Item> items;

    public ItemNearestNeighbor(List<Item> items) {
        this.items = items;
    }

    public List<Item> search(Item baseItem, int n) {
        Lambda.forEach(this.items).setScore(1.0);

        for(Item item : this.items) {
            if(baseItem.equals(item)) { continue; }
            item.setScore(this.distanceFunction.distance(baseItem, item));
        }

        Collections.sort(this.items);
        if(n > this.items.size()) {
            n = this.items.size();
        }
        return this.items.subList(0, n);
    }

    public void setDistanceFunction(DistanceFunction distanceFunction) {
        this.distanceFunction = distanceFunction;
    }

}
