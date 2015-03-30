package kenny.ml.cluster.kmeans;

import kenny.ml.cluster.feature.Item;

import java.util.List;

/**
 * Created by kenny on 2/21/14.
 */
public class K2MeansClusterer extends KMeansClusterer {

    public K2MeansClusterer() {
        super(2);
    }

    public List<Centroid> cluster(List<Item> items) {
        return super.cluster(items);
    }

}
