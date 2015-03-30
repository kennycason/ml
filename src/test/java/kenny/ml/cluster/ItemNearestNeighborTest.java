package kenny.ml.cluster;


import ch.lambdaj.Lambda;
import kenny.ml.cluster.distance.CosineDistanceFunction;
import kenny.ml.cluster.distance.DiscreteDistanceFunction;
import kenny.ml.cluster.distance.DistanceFunction;
import kenny.ml.cluster.distance.EuclideanDistanceFunction;
import kenny.ml.cluster.distance.JaccardDistanceFunction;
import kenny.ml.cluster.feature.Item;
import kenny.ml.cluster.feature.impl.person.HeightFeature;
import kenny.ml.cluster.feature.impl.person.WaistFeature;
import kenny.ml.cluster.feature.impl.person.WeightFeature;
import org.junit.Test;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

/**
 * Created by kenny on 2/13/14.
 */
public class ItemNearestNeighborTest {

    private static final Item KENNY = buildKenny();

    private static final Item PERSON = buildRandomPerson();

    @Test
    public void euclideanDistance() {
        System.out.println("Euclidean Distance Test");
        double score = new EuclideanDistanceFunction().distance(KENNY, PERSON);
        System.out.println(KENNY);
        System.out.println(PERSON);
        System.out.println(score);

        score = new EuclideanDistanceFunction().distance(KENNY, KENNY);
        System.out.println("kenny vs kenny: " + score);
    }

    @Test
    public void massScoreTest() {
        List<Item> people = buildRandomPeople(100000);

        massScoreTest(KENNY, people, new DiscreteDistanceFunction(), 10);
        massScoreTest(KENNY, people, new EuclideanDistanceFunction(), 10);
        massScoreTest(KENNY, people, new CosineDistanceFunction(), 10);
        massScoreTest(KENNY, people, new JaccardDistanceFunction(0.3), 10);
    }

    private void massScoreTest(Item person, List<Item> people, DistanceFunction distanceFunction, int n) {
        ItemNearestNeighbor nearestNeighbor = new ItemNearestNeighbor(people);
        nearestNeighbor.setDistanceFunction(distanceFunction);

        List<Item> sortedPeople = nearestNeighbor.search(person, n);
        System.out.println("Top " + n + " for: " + person);
        System.out.println("Using: " + distanceFunction);
        System.out.println(Lambda.join(sortedPeople, "\n"));
        System.out.println("\n");
    }

    private static Item buildKenny() {
        Item kenny = new Item("kenny");
        kenny.addFeature(new HeightFeature(73));
        kenny.addFeature(new WeightFeature(213));
        kenny.addFeature(new WaistFeature(34));
        //kenny.addFeature(new NeckFeature(15.5));
        //kenny.addFeature(new ChestFeature(43));

        return kenny;
    }

    private static Item buildRandomPerson() {
        return buildRandomPeople(1).get(0);
    }

    private static List<Item> buildRandomPeople(int number) {
        List<Item> persons = new LinkedList<>();

        Random random = new Random();
        for(int i = 0; i < number; i++) {
            Item person = new Item("PERSON_" + i);
            person.addFeature(new HeightFeature(58 + random.nextInt(26)));
            person.addFeature(new WeightFeature(100 + random.nextInt(200)));
            //person.addFeature(new NeckFeature(13 + random.nextInt(6)));
            //person.addFeature(new ChestFeature(35 + random.nextInt(20)));
            person.addFeature(new WaistFeature(28 + random.nextInt(14)));

            persons.add(person);
        }
        return persons;
    }

}
