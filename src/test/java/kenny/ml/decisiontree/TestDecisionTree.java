package kenny.ml.decisiontree;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * Created by kenny
 */
public class TestDecisionTree {

    private static final String OUTLOOK = "Outlook";
    private enum Outlook { RAINY, OVERCAST, SUNNY }

    private static final String TEMP = "Temp";
    private enum Temp { HOT, MILD, COOL }

    private static final String HUMIDITY = "Humidity";
    private enum Humidity { HIGH, NORMAL }

    private static final String WINDY = "Windy";
    private enum Windy { TRUE, FALSE }

    private static final String PLAY_GOLF = "Play Golf";
    private enum PlayGolf { YES, NO }

    @Test
    public void test() {
        final DecisionTree decisionTree = new DecisionTree();
        final Tree tree = decisionTree.train(PLAY_GOLF, loadFeatures());
        System.out.println(tree);

        assertEquals(PlayGolf.NO.toString(), tree.walk(new FeatureSet()
                .set(PLAY_GOLF, PlayGolf.NO.toString())
                .set(OUTLOOK, Outlook.RAINY.toString())
                .set(TEMP, Temp.HOT.toString())
                .set(HUMIDITY, Humidity.HIGH.toString())
                .set(WINDY, Windy.FALSE.toString())));

        assertEquals(PlayGolf.YES.toString(), tree.walk(new FeatureSet()
                .set(PLAY_GOLF, PlayGolf.YES.toString())
                .set(OUTLOOK, Outlook.OVERCAST.toString())
                .set(TEMP, Temp.HOT.toString())
                .set(HUMIDITY, Humidity.HIGH.toString())
                .set(WINDY, Windy.FALSE.toString())));

        assertEquals(PlayGolf.YES.toString(), tree.walk(new FeatureSet()
                .set(PLAY_GOLF, PlayGolf.YES.toString())
                .set(OUTLOOK, Outlook.OVERCAST.toString())
                .set(TEMP, Temp.HOT.toString())
                .set(HUMIDITY, Humidity.HIGH.toString())
                .set(WINDY, Windy.FALSE.toString())));

        assertEquals(PlayGolf.YES.toString(), tree.walk(new FeatureSet()
                .set(PLAY_GOLF, PlayGolf.YES.toString())
                .set(OUTLOOK, Outlook.SUNNY.toString())
                .set(TEMP, Temp.MILD.toString())
                .set(HUMIDITY, Humidity.HIGH.toString())
                .set(WINDY, Windy.FALSE.toString())));

        assertEquals(PlayGolf.YES.toString(), tree.walk(new FeatureSet()
                .set(PLAY_GOLF, PlayGolf.YES.toString())
                .set(OUTLOOK, Outlook.SUNNY.toString())
                .set(TEMP, Temp.COOL.toString())
                .set(HUMIDITY, Humidity.NORMAL.toString())
                .set(WINDY, Windy.FALSE.toString())));

        assertEquals(PlayGolf.NO.toString(), tree.walk(new FeatureSet()
                .set(PLAY_GOLF, PlayGolf.NO.toString())
                .set(OUTLOOK, Outlook.SUNNY.toString())
                .set(TEMP, Temp.COOL.toString())
                .set(HUMIDITY, Humidity.NORMAL.toString())
                .set(WINDY, Windy.TRUE.toString())));

        assertEquals(PlayGolf.YES.toString(), tree.walk(new FeatureSet()
                .set(PLAY_GOLF, PlayGolf.YES.toString())
                .set(OUTLOOK, Outlook.OVERCAST.toString())
                .set(TEMP, Temp.COOL.toString())
                .set(HUMIDITY, Humidity.NORMAL.toString())
                .set(WINDY, Windy.TRUE.toString())));

        assertEquals(PlayGolf.NO.toString(), tree.walk(new FeatureSet()
                .set(PLAY_GOLF, PlayGolf.NO.toString())
                .set(OUTLOOK, Outlook.RAINY.toString())
                .set(TEMP, Temp.MILD.toString())
                .set(HUMIDITY, Humidity.HIGH.toString())
                .set(WINDY, Windy.FALSE.toString())));

        assertEquals(PlayGolf.YES.toString(), tree.walk(new FeatureSet()
                .set(PLAY_GOLF, PlayGolf.YES.toString())
                .set(OUTLOOK, Outlook.RAINY.toString())
                .set(TEMP, Temp.COOL.toString())
                .set(HUMIDITY, Humidity.NORMAL.toString())
                .set(WINDY, Windy.FALSE.toString())));

        assertEquals(PlayGolf.YES.toString(), tree.walk(new FeatureSet()
                .set(PLAY_GOLF, PlayGolf.YES.toString())
                .set(OUTLOOK, Outlook.SUNNY.toString())
                .set(TEMP, Temp.MILD.toString())
                .set(HUMIDITY, Humidity.NORMAL.toString())
                .set(WINDY, Windy.FALSE.toString())));

        assertEquals(PlayGolf.YES.toString(), tree.walk(new FeatureSet()
                .set(PLAY_GOLF, PlayGolf.YES.toString())
                .set(OUTLOOK, Outlook.RAINY.toString())
                .set(TEMP, Temp.MILD.toString())
                .set(HUMIDITY, Humidity.NORMAL.toString())
                .set(WINDY, Windy.TRUE.toString())));

        assertEquals(PlayGolf.YES.toString(), tree.walk(new FeatureSet()
                .set(PLAY_GOLF, PlayGolf.YES.toString())
                .set(OUTLOOK, Outlook.OVERCAST.toString())
                .set(TEMP, Temp.MILD.toString())
                .set(HUMIDITY, Humidity.HIGH.toString())
                .set(WINDY, Windy.TRUE.toString())));

        assertEquals(PlayGolf.YES.toString(), tree.walk(new FeatureSet()
                .set(PLAY_GOLF, PlayGolf.YES.toString())
                .set(OUTLOOK, Outlook.OVERCAST.toString())
                .set(TEMP, Temp.HOT.toString())
                .set(HUMIDITY, Humidity.NORMAL.toString())
                .set(WINDY, Windy.FALSE.toString())));

        assertEquals(PlayGolf.NO.toString(), tree.walk(new FeatureSet()
                .set(PLAY_GOLF, PlayGolf.NO.toString())
                .set(OUTLOOK, Outlook.SUNNY.toString())
                .set(TEMP, Temp.MILD.toString())
                .set(HUMIDITY, Humidity.HIGH.toString())
                .set(WINDY, Windy.TRUE.toString())));
    }

    private static List<FeatureSet> loadFeatures() {
        final List<FeatureSet> features = new ArrayList<>();

        features.add(new FeatureSet()
            .set(PLAY_GOLF, PlayGolf.NO.toString())
            .set(OUTLOOK, Outlook.RAINY.toString())
            .set(TEMP, Temp.HOT.toString())
            .set(HUMIDITY, Humidity.HIGH.toString())
            .set(WINDY, Windy.FALSE.toString()));

        features.add(new FeatureSet()
                .set(PLAY_GOLF, PlayGolf.NO.toString())
                .set(OUTLOOK, Outlook.RAINY.toString())
                .set(TEMP, Temp.HOT.toString())
                .set(HUMIDITY, Humidity.HIGH.toString())
                .set(WINDY, Windy.TRUE.toString()));

        features.add(new FeatureSet()
                .set(PLAY_GOLF, PlayGolf.YES.toString())
                .set(OUTLOOK, Outlook.OVERCAST.toString())
                .set(TEMP, Temp.HOT.toString())
                .set(HUMIDITY, Humidity.HIGH.toString())
                .set(WINDY, Windy.FALSE.toString()));

        features.add(new FeatureSet()
                .set(PLAY_GOLF, PlayGolf.YES.toString())
                .set(OUTLOOK, Outlook.SUNNY.toString())
                .set(TEMP, Temp.MILD.toString())
                .set(HUMIDITY, Humidity.HIGH.toString())
                .set(WINDY, Windy.FALSE.toString()));

        features.add(new FeatureSet()
                .set(PLAY_GOLF, PlayGolf.YES.toString())
                .set(OUTLOOK, Outlook.SUNNY.toString())
                .set(TEMP, Temp.COOL.toString())
                .set(HUMIDITY, Humidity.NORMAL.toString())
                .set(WINDY, Windy.FALSE.toString()));

        features.add(new FeatureSet()
                .set(PLAY_GOLF, PlayGolf.NO.toString())
                .set(OUTLOOK, Outlook.SUNNY.toString())
                .set(TEMP, Temp.COOL.toString())
                .set(HUMIDITY, Humidity.NORMAL.toString())
                .set(WINDY, Windy.TRUE.toString()));

        features.add(new FeatureSet()
                .set(PLAY_GOLF, PlayGolf.YES.toString())
                .set(OUTLOOK, Outlook.OVERCAST.toString())
                .set(TEMP, Temp.COOL.toString())
                .set(HUMIDITY, Humidity.NORMAL.toString())
                .set(WINDY, Windy.TRUE.toString()));

        features.add(new FeatureSet()
                .set(PLAY_GOLF, PlayGolf.NO.toString())
                .set(OUTLOOK, Outlook.RAINY.toString())
                .set(TEMP, Temp.MILD.toString())
                .set(HUMIDITY, Humidity.HIGH.toString())
                .set(WINDY, Windy.FALSE.toString()));

        features.add(new FeatureSet()
                .set(PLAY_GOLF, PlayGolf.YES.toString())
                .set(OUTLOOK, Outlook.RAINY.toString())
                .set(TEMP, Temp.COOL.toString())
                .set(HUMIDITY, Humidity.NORMAL.toString())
                .set(WINDY, Windy.FALSE.toString()));

        features.add(new FeatureSet()
                .set(PLAY_GOLF, PlayGolf.YES.toString())
                .set(OUTLOOK, Outlook.SUNNY.toString())
                .set(TEMP, Temp.MILD.toString())
                .set(HUMIDITY, Humidity.NORMAL.toString())
                .set(WINDY, Windy.FALSE.toString()));

        features.add(new FeatureSet()
                .set(PLAY_GOLF, PlayGolf.YES.toString())
                .set(OUTLOOK, Outlook.RAINY.toString())
                .set(TEMP, Temp.MILD.toString())
                .set(HUMIDITY, Humidity.NORMAL.toString())
                .set(WINDY, Windy.TRUE.toString()));

        features.add(new FeatureSet()
                .set(PLAY_GOLF, PlayGolf.YES.toString())
                .set(OUTLOOK, Outlook.OVERCAST.toString())
                .set(TEMP, Temp.MILD.toString())
                .set(HUMIDITY, Humidity.HIGH.toString())
                .set(WINDY, Windy.TRUE.toString()));

        features.add(new FeatureSet()
                .set(PLAY_GOLF, PlayGolf.YES.toString())
                .set(OUTLOOK, Outlook.OVERCAST.toString())
                .set(TEMP, Temp.HOT.toString())
                .set(HUMIDITY, Humidity.NORMAL.toString())
                .set(WINDY, Windy.FALSE.toString()));

        features.add(new FeatureSet()
                .set(PLAY_GOLF, PlayGolf.NO.toString())
                .set(OUTLOOK, Outlook.SUNNY.toString())
                .set(TEMP, Temp.MILD.toString())
                .set(HUMIDITY, Humidity.HIGH.toString())
                .set(WINDY, Windy.TRUE.toString()));

        return features;
    }

}
