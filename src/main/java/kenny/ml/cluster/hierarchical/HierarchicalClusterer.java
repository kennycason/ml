package kenny.ml.cluster.hierarchical;


import kenny.ml.cluster.distance.DistanceFunction;
import kenny.ml.cluster.distance.EuclideanDistanceFunction;
import kenny.ml.cluster.feature.Item;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by kenny on 2/20/14.
 */
public class HierarchicalClusterer {

    private DistanceFunction distanceFunction;

    public HierarchicalClusterer() {
        distanceFunction = new EuclideanDistanceFunction();
    }

    public Cluster cluster(List<Item> items) {
        List<Cluster> clusters = buildInitialClusters(items); // todo use sets/hashmaps instead of list
        if(clusters.size() == 1) { return clusters.get(0); }

        do {
            Cluster toCluster = null;
            Cluster closestCluster = null;
            Iterator<Cluster> clusterIterator = clusters.iterator();
            while(clusterIterator.hasNext()) {
                toCluster = clusterIterator.next();
                closestCluster = findClosestCluster(toCluster, clusters);
            }
            clusterIterator.remove();
            BinaryCluster binaryCluster = new BinaryCluster(toCluster, closestCluster);
            clusters.add(binaryCluster);
            clusters.remove(closestCluster);

        } while(clusters.size() > 1);

        return clusters.get(0);
    }

    private Cluster findClosestCluster(Cluster targetCluster, List<Cluster> clusters) {
        Cluster closest = null;
        double minDistance = Double.MAX_VALUE;
        for(Cluster cluster : clusters) {
            if(cluster.equals(targetCluster)) { continue; }
            double distance = distanceFunction.distance(cluster.getItem(), targetCluster.getItem());
            if(distance < minDistance) {
                minDistance = distance;
                closest = cluster;
            }
        }
        if(closest == null) { throw new RuntimeException("Failed to find closest cluster"); }
        return closest;
    }

    private List<Cluster> buildInitialClusters(List<Item> items) {
        List<Cluster> clusters = new LinkedList<>();
        for(Item item : items) {
            clusters.add(new SingleCluster(item));
        }

        return clusters;
    }

    public void setDistanceFunction(DistanceFunction distanceFunction) {
        this.distanceFunction = distanceFunction;
    }

}
