package kenny.ml.decisiontree;

import kenny.ml.decisiontree.data.DecisionTree;
import kenny.ml.decisiontree.data.Feature;
import kenny.ml.decisiontree.data.Tree;
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

        assertEquals(PlayGolf.NO, tree.walk(new Feature()
                .set(PLAY_GOLF, PlayGolf.NO)
                .set(OUTLOOK, Outlook.RAINY)
                .set(TEMP, Temp.HOT)
                .set(HUMIDITY, Humidity.HIGH)
                .set(WINDY, Windy.FALSE)));

        assertEquals(PlayGolf.YES, tree.walk(new Feature()
                .set(PLAY_GOLF, PlayGolf.YES)
                .set(OUTLOOK, Outlook.OVERCAST)
                .set(TEMP, Temp.HOT)
                .set(HUMIDITY, Humidity.HIGH)
                .set(WINDY, Windy.FALSE)));

        assertEquals(PlayGolf.YES, tree.walk(new Feature()
                .set(PLAY_GOLF, PlayGolf.YES)
                .set(OUTLOOK, Outlook.OVERCAST)
                .set(TEMP, Temp.HOT)
                .set(HUMIDITY, Humidity.HIGH)
                .set(WINDY, Windy.FALSE)));

        assertEquals(PlayGolf.YES, tree.walk(new Feature()
                .set(PLAY_GOLF, PlayGolf.YES)
                .set(OUTLOOK, Outlook.SUNNY)
                .set(TEMP, Temp.MILD)
                .set(HUMIDITY, Humidity.HIGH)
                .set(WINDY, Windy.FALSE)));

        assertEquals(PlayGolf.YES, tree.walk(new Feature()
                .set(PLAY_GOLF, PlayGolf.YES)
                .set(OUTLOOK, Outlook.SUNNY)
                .set(TEMP, Temp.COOL)
                .set(HUMIDITY, Humidity.NORMAL)
                .set(WINDY, Windy.FALSE)));

        assertEquals(PlayGolf.NO, tree.walk(new Feature()
                .set(PLAY_GOLF, PlayGolf.NO)
                .set(OUTLOOK, Outlook.SUNNY)
                .set(TEMP, Temp.COOL)
                .set(HUMIDITY, Humidity.NORMAL)
                .set(WINDY, Windy.TRUE)));

        assertEquals(PlayGolf.YES, tree.walk(new Feature()
                .set(PLAY_GOLF, PlayGolf.YES)
                .set(OUTLOOK, Outlook.OVERCAST)
                .set(TEMP, Temp.COOL)
                .set(HUMIDITY, Humidity.NORMAL)
                .set(WINDY, Windy.TRUE)));

        assertEquals(PlayGolf.NO, tree.walk(new Feature()
                .set(PLAY_GOLF, PlayGolf.NO)
                .set(OUTLOOK, Outlook.RAINY)
                .set(TEMP, Temp.MILD)
                .set(HUMIDITY, Humidity.HIGH)
                .set(WINDY, Windy.FALSE)));

        assertEquals(PlayGolf.YES, tree.walk(new Feature()
                .set(PLAY_GOLF, PlayGolf.YES)
                .set(OUTLOOK, Outlook.RAINY)
                .set(TEMP, Temp.COOL)
                .set(HUMIDITY, Humidity.NORMAL)
                .set(WINDY, Windy.FALSE)));

        assertEquals(PlayGolf.YES, tree.walk(new Feature()
                .set(PLAY_GOLF, PlayGolf.YES)
                .set(OUTLOOK, Outlook.SUNNY)
                .set(TEMP, Temp.MILD)
                .set(HUMIDITY, Humidity.NORMAL)
                .set(WINDY, Windy.FALSE)));

        assertEquals(PlayGolf.YES, tree.walk(new Feature()
                .set(PLAY_GOLF, PlayGolf.YES)
                .set(OUTLOOK, Outlook.RAINY)
                .set(TEMP, Temp.MILD)
                .set(HUMIDITY, Humidity.NORMAL)
                .set(WINDY, Windy.TRUE)));

        assertEquals(PlayGolf.YES, tree.walk(new Feature()
                .set(PLAY_GOLF, PlayGolf.YES)
                .set(OUTLOOK, Outlook.OVERCAST)
                .set(TEMP, Temp.MILD)
                .set(HUMIDITY, Humidity.HIGH)
                .set(WINDY, Windy.TRUE)));

        assertEquals(PlayGolf.YES, tree.walk(new Feature()
                .set(PLAY_GOLF, PlayGolf.YES)
                .set(OUTLOOK, Outlook.OVERCAST)
                .set(TEMP, Temp.HOT)
                .set(HUMIDITY, Humidity.NORMAL)
                .set(WINDY, Windy.FALSE)));

        assertEquals(PlayGolf.NO, tree.walk(new Feature()
                .set(PLAY_GOLF, PlayGolf.NO)
                .set(OUTLOOK, Outlook.SUNNY)
                .set(TEMP, Temp.MILD)
                .set(HUMIDITY, Humidity.HIGH)
                .set(WINDY, Windy.TRUE)));
    }

    private static List<Feature> loadFeatures() {
        final List<Feature> features = new ArrayList<>();

        features.add(new Feature()
            .set(PLAY_GOLF, PlayGolf.NO)
            .set(OUTLOOK, Outlook.RAINY)
            .set(TEMP, Temp.HOT)
            .set(HUMIDITY, Humidity.HIGH)
            .set(WINDY, Windy.FALSE));

        features.add(new Feature()
                .set(PLAY_GOLF, PlayGolf.NO)
                .set(OUTLOOK, Outlook.RAINY)
                .set(TEMP, Temp.HOT)
                .set(HUMIDITY, Humidity.HIGH)
                .set(WINDY, Windy.TRUE));

        features.add(new Feature()
                .set(PLAY_GOLF, PlayGolf.YES)
                .set(OUTLOOK, Outlook.OVERCAST)
                .set(TEMP, Temp.HOT)
                .set(HUMIDITY, Humidity.HIGH)
                .set(WINDY, Windy.FALSE));

        features.add(new Feature()
                .set(PLAY_GOLF, PlayGolf.YES)
                .set(OUTLOOK, Outlook.SUNNY)
                .set(TEMP, Temp.MILD)
                .set(HUMIDITY, Humidity.HIGH)
                .set(WINDY, Windy.FALSE));

        features.add(new Feature()
                .set(PLAY_GOLF, PlayGolf.YES)
                .set(OUTLOOK, Outlook.SUNNY)
                .set(TEMP, Temp.COOL)
                .set(HUMIDITY, Humidity.NORMAL)
                .set(WINDY, Windy.FALSE));

        features.add(new Feature()
                .set(PLAY_GOLF, PlayGolf.NO)
                .set(OUTLOOK, Outlook.SUNNY)
                .set(TEMP, Temp.COOL)
                .set(HUMIDITY, Humidity.NORMAL)
                .set(WINDY, Windy.TRUE));

        features.add(new Feature()
                .set(PLAY_GOLF, PlayGolf.YES)
                .set(OUTLOOK, Outlook.OVERCAST)
                .set(TEMP, Temp.COOL)
                .set(HUMIDITY, Humidity.NORMAL)
                .set(WINDY, Windy.TRUE));

        features.add(new Feature()
                .set(PLAY_GOLF, PlayGolf.NO)
                .set(OUTLOOK, Outlook.RAINY)
                .set(TEMP, Temp.MILD)
                .set(HUMIDITY, Humidity.HIGH)
                .set(WINDY, Windy.FALSE));

        features.add(new Feature()
                .set(PLAY_GOLF, PlayGolf.YES)
                .set(OUTLOOK, Outlook.RAINY)
                .set(TEMP, Temp.COOL)
                .set(HUMIDITY, Humidity.NORMAL)
                .set(WINDY, Windy.FALSE));

        features.add(new Feature()
                .set(PLAY_GOLF, PlayGolf.YES)
                .set(OUTLOOK, Outlook.SUNNY)
                .set(TEMP, Temp.MILD)
                .set(HUMIDITY, Humidity.NORMAL)
                .set(WINDY, Windy.FALSE));

        features.add(new Feature()
                .set(PLAY_GOLF, PlayGolf.YES)
                .set(OUTLOOK, Outlook.RAINY)
                .set(TEMP, Temp.MILD)
                .set(HUMIDITY, Humidity.NORMAL)
                .set(WINDY, Windy.TRUE));

        features.add(new Feature()
                .set(PLAY_GOLF, PlayGolf.YES)
                .set(OUTLOOK, Outlook.OVERCAST)
                .set(TEMP, Temp.MILD)
                .set(HUMIDITY, Humidity.HIGH)
                .set(WINDY, Windy.TRUE));

        features.add(new Feature()
                .set(PLAY_GOLF, PlayGolf.YES)
                .set(OUTLOOK, Outlook.OVERCAST)
                .set(TEMP, Temp.HOT)
                .set(HUMIDITY, Humidity.NORMAL)
                .set(WINDY, Windy.FALSE));

        features.add(new Feature()
                .set(PLAY_GOLF, PlayGolf.NO)
                .set(OUTLOOK, Outlook.SUNNY)
                .set(TEMP, Temp.MILD)
                .set(HUMIDITY, Humidity.HIGH)
                .set(WINDY, Windy.TRUE));

        return features;
    }

}
