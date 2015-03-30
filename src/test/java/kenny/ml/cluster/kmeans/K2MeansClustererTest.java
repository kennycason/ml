package kenny.ml.cluster.kmeans;


import ch.lambdaj.Lambda;
import kenny.ml.cluster.SampleCorpus;
import org.junit.Test;

import java.util.List;

import static ch.lambdaj.Lambda.on;

/**
 * Created by kenny on 2/21/14.
 */
public class K2MeansClustererTest {

    @Test
    public void clusterSampleData() {
        KMeansClusterer clusterer = new K2MeansClusterer();
        clusterer.setMaxIterations(1);

        System.out.println("Iterations = 1");
        List<Centroid> clusters = clusterer.cluster(SampleCorpus.kMeansData());
        System.out.println(Lambda.join(clusters, "\n"));
        System.out.println("New centers:");
        System.out.println(Lambda.join(Lambda.extract(clusters, on(Centroid.class).center()), "\n"));


        clusterer.setMaxIterations(3);
        System.out.println("\n\nIterations = 3");
        clusters = clusterer.cluster(SampleCorpus.kMeansData());
        System.out.println(Lambda.join(clusters, "\n"));
        System.out.println("New centers:");
        System.out.println(Lambda.join(Lambda.extract(clusters, on(Centroid.class).center()), "\n"));


        clusterer.setMaxIterations(10);
        System.out.println("\n\nIterations = 10");
        clusters = clusterer.cluster(SampleCorpus.kMeansData());
        System.out.println(Lambda.join(clusters, "\n"));
        System.out.println("New centers:");
        System.out.println(Lambda.join(Lambda.extract(clusters, on(Centroid.class).center()), "\n"));
    }


    @Test
    public void clusterColors() {
        KMeansClusterer clusterer = new K2MeansClusterer();
        clusterer.setMaxIterations(1);

        System.out.println("Iterations = 1");
        List<Centroid> clusters = clusterer.cluster(SampleCorpus.buildColors());
        System.out.println(Lambda.join(clusters, "\n"));
        System.out.println("New centers:");
        System.out.println(Lambda.join(Lambda.extract(clusters, on(Centroid.class).center()), "\n"));


        clusterer.setMaxIterations(3);
        System.out.println("\n\nIterations = 3");
        clusters = clusterer.cluster(SampleCorpus.buildColors());
        System.out.println(Lambda.join(clusters, "\n"));
        System.out.println("New centers:");
        System.out.println(Lambda.join(Lambda.extract(clusters, on(Centroid.class).center()), "\n"));


        clusterer.setMaxIterations(10);
        System.out.println("\n\nIterations = 10");
        clusters = clusterer.cluster(SampleCorpus.buildColors());
        System.out.println(Lambda.join(clusters, "\n"));
        System.out.println("New centers:");
        System.out.println(Lambda.join(Lambda.extract(clusters, on(Centroid.class).center()), "\n"));
    }

}
