package kenny.ml.randomforest;

import kenny.ml.decisiontree.Feature;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kenny
 */
public class TestRandomForest {

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

    private int correct = 0;
    private int incorrect = 0;

    @Test
    public void test() {
        final RandomForest randomForest = new RandomForest();
//        // these settings will function like a single decision tree
//        randomForest.labelSampleRate = 1.0;
//        randomForest.featureSampleRate = 0.0;
//        randomForest.numTrees = 1;
        randomForest.labelSampleRate = 0.9;
        randomForest.featureSampleRate = 0.5;
        randomForest.numTrees = 100;

        final Forest forest = randomForest.train(PLAY_GOLF, loadFeatures());

        System.out.println(forest);

        tally(PlayGolf.NO, forest.walk(new Feature()
                .set(PLAY_GOLF, PlayGolf.NO)
                .set(OUTLOOK, Outlook.RAINY)
                .set(TEMP, Temp.HOT)
                .set(HUMIDITY, Humidity.HIGH)
                .set(WINDY, Windy.FALSE)));

        tally(PlayGolf.YES, forest.walk(new Feature()
                .set(PLAY_GOLF, PlayGolf.YES)
                .set(OUTLOOK, Outlook.OVERCAST)
                .set(TEMP, Temp.HOT)
                .set(HUMIDITY, Humidity.HIGH)
                .set(WINDY, Windy.FALSE)));

        tally(PlayGolf.YES, forest.walk(new Feature()
                .set(PLAY_GOLF, PlayGolf.YES)
                .set(OUTLOOK, Outlook.OVERCAST)
                .set(TEMP, Temp.HOT)
                .set(HUMIDITY, Humidity.HIGH)
                .set(WINDY, Windy.FALSE)));

        tally(PlayGolf.YES, forest.walk(new Feature()
                .set(PLAY_GOLF, PlayGolf.YES)
                .set(OUTLOOK, Outlook.SUNNY)
                .set(TEMP, Temp.MILD)
                .set(HUMIDITY, Humidity.HIGH)
                .set(WINDY, Windy.FALSE)));

        tally(PlayGolf.YES, forest.walk(new Feature()
                .set(PLAY_GOLF, PlayGolf.YES)
                .set(OUTLOOK, Outlook.SUNNY)
                .set(TEMP, Temp.COOL)
                .set(HUMIDITY, Humidity.NORMAL)
                .set(WINDY, Windy.FALSE)));

        tally(PlayGolf.NO, forest.walk(new Feature()
                .set(PLAY_GOLF, PlayGolf.NO)
                .set(OUTLOOK, Outlook.SUNNY)
                .set(TEMP, Temp.COOL)
                .set(HUMIDITY, Humidity.NORMAL)
                .set(WINDY, Windy.TRUE)));

        tally(PlayGolf.YES, forest.walk(new Feature()
                .set(PLAY_GOLF, PlayGolf.YES)
                .set(OUTLOOK, Outlook.OVERCAST)
                .set(TEMP, Temp.COOL)
                .set(HUMIDITY, Humidity.NORMAL)
                .set(WINDY, Windy.TRUE)));

        tally(PlayGolf.NO, forest.walk(new Feature()
                .set(PLAY_GOLF, PlayGolf.NO)
                .set(OUTLOOK, Outlook.RAINY)
                .set(TEMP, Temp.MILD)
                .set(HUMIDITY, Humidity.HIGH)
                .set(WINDY, Windy.FALSE)));

        tally(PlayGolf.YES, forest.walk(new Feature()
                .set(PLAY_GOLF, PlayGolf.YES)
                .set(OUTLOOK, Outlook.RAINY)
                .set(TEMP, Temp.COOL)
                .set(HUMIDITY, Humidity.NORMAL)
                .set(WINDY, Windy.FALSE)));

        tally(PlayGolf.YES, forest.walk(new Feature()
                .set(PLAY_GOLF, PlayGolf.YES)
                .set(OUTLOOK, Outlook.SUNNY)
                .set(TEMP, Temp.MILD)
                .set(HUMIDITY, Humidity.NORMAL)
                .set(WINDY, Windy.FALSE)));

        tally(PlayGolf.YES, forest.walk(new Feature()
                .set(PLAY_GOLF, PlayGolf.YES)
                .set(OUTLOOK, Outlook.RAINY)
                .set(TEMP, Temp.MILD)
                .set(HUMIDITY, Humidity.NORMAL)
                .set(WINDY, Windy.TRUE)));

        tally(PlayGolf.YES, forest.walk(new Feature()
                .set(PLAY_GOLF, PlayGolf.YES)
                .set(OUTLOOK, Outlook.OVERCAST)
                .set(TEMP, Temp.MILD)
                .set(HUMIDITY, Humidity.HIGH)
                .set(WINDY, Windy.TRUE)));

        tally(PlayGolf.YES, forest.walk(new Feature()
                .set(PLAY_GOLF, PlayGolf.YES)
                .set(OUTLOOK, Outlook.OVERCAST)
                .set(TEMP, Temp.HOT)
                .set(HUMIDITY, Humidity.NORMAL)
                .set(WINDY, Windy.FALSE)));

        tally(PlayGolf.NO, forest.walk(new Feature()
                .set(PLAY_GOLF, PlayGolf.NO)
                .set(OUTLOOK, Outlook.SUNNY)
                .set(TEMP, Temp.MILD)
                .set(HUMIDITY, Humidity.HIGH)
                .set(WINDY, Windy.TRUE)));

        System.out.println((((double) correct) / (correct + incorrect) * 100.0) + "% correct");
    }

    private final void tally(Enum expected, Enum actual) {
        if (expected == actual) { correct++; }
        else { incorrect++; }
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
