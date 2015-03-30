package kenny.ml.cluster.math;


import kenny.ml.cluster.feature.Item;
import kenny.ml.cluster.feature.impl.RatingFeature;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by kenny on 2/20/14.
 */
public class StatsTest {

    private static double DELTA = 0.0;

    @Test
    public void averageTest() {
        Item item1 = new Item("A");
        item1.addFeature(new RatingFeature(5.0));
        Item item2 = new Item("B");
        item2.addFeature(new RatingFeature(1.0));

        Item averaged = Stats.average(item1, item2);
        assertEquals(3.0, averaged.getFeature("RATING").getValue(), DELTA);
    }

}
