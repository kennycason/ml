package kenny.ml.cluster.hierarchical;


import kenny.ml.cluster.feature.Item;

import java.util.Set;

/**
 * Created by kenny on 2/20/14.
 */
public interface Cluster {

    String getName();

    Item getItem();

    Set<Cluster> getParents();

}
