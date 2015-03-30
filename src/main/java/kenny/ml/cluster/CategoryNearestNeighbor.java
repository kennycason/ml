package kenny.ml.cluster;


import ch.lambdaj.Lambda;
import kenny.ml.cluster.distance.DistanceFunction;
import kenny.ml.cluster.feature.Category;

import java.util.Collections;
import java.util.List;

/**
 * Created by kenny on 2/16/14.
 */
public class CategoryNearestNeighbor {

    private DistanceFunction distanceFunction;

    private List<Category> categories;

    public CategoryNearestNeighbor(List<Category> categories) {
        this.categories = categories;
    }

    public List<Category> topMatches(Category baseCategory, int n) {
        Lambda.forEach(this.categories).setScore(1.0);

        for(Category category : this.categories) {
            if(baseCategory.equals(category)) { continue; }
            category.setScore(this.distanceFunction.distance(baseCategory, category));
        }

        Collections.sort(this.categories);
        if(n > this.categories.size()) {
            n = this.categories.size();
        }
        return this.categories.subList(0, n);
    }

    public void setDistanceFunction(DistanceFunction distanceFunction) {
        this.distanceFunction = distanceFunction;
    }

}
