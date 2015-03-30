package kenny.ml.cluster.kmeans.initializer;


import kenny.ml.cluster.feature.Item;
import kenny.ml.cluster.kmeans.Centroid;

import java.util.List;

/**
 * Created by kenny on 2/21/14.
 */
public interface Initializer {

    List<Centroid> initialize(List<Item> items, int k);

}
