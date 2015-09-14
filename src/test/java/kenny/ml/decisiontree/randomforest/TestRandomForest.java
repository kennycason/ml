package kenny.ml.decisiontree.randomforest;

import kenny.ml.decisiontree.Feature;
import org.apache.commons.lang3.StringUtils;
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

        tally(PlayGolf.NO.toString(), forest.walk(new Feature()
                .set(PLAY_GOLF, PlayGolf.NO.toString())
                .set(OUTLOOK, Outlook.RAINY.toString())
                .set(TEMP, Temp.HOT.toString())
                .set(HUMIDITY, Humidity.HIGH.toString())
                .set(WINDY, Windy.FALSE.toString())));

        tally(PlayGolf.YES.toString().toString(), forest.walk(new Feature()
                .set(PLAY_GOLF, PlayGolf.YES.toString().toString())
                .set(OUTLOOK, Outlook.OVERCAST.toString())
                .set(TEMP, Temp.HOT.toString())
                .set(HUMIDITY, Humidity.HIGH.toString())
                .set(WINDY, Windy.FALSE.toString())));

        tally(PlayGolf.YES.toString().toString(), forest.walk(new Feature()
                .set(PLAY_GOLF, PlayGolf.YES.toString().toString())
                .set(OUTLOOK, Outlook.OVERCAST.toString())
                .set(TEMP, Temp.HOT.toString())
                .set(HUMIDITY, Humidity.HIGH.toString())
                .set(WINDY, Windy.FALSE.toString())));

        tally(PlayGolf.YES.toString().toString(), forest.walk(new Feature()
                .set(PLAY_GOLF, PlayGolf.YES.toString().toString())
                .set(OUTLOOK, Outlook.SUNNY.toString())
                .set(TEMP, Temp.MILD.toString())
                .set(HUMIDITY, Humidity.HIGH.toString())
                .set(WINDY, Windy.FALSE.toString())));

        tally(PlayGolf.YES.toString().toString(), forest.walk(new Feature()
                .set(PLAY_GOLF, PlayGolf.YES.toString().toString())
                .set(OUTLOOK, Outlook.SUNNY.toString())
                .set(TEMP, Temp.COOL.toString())
                .set(HUMIDITY, Humidity.NORMAL.toString())
                .set(WINDY, Windy.FALSE.toString())));

        tally(PlayGolf.NO.toString(), forest.walk(new Feature()
                .set(PLAY_GOLF, PlayGolf.NO.toString())
                .set(OUTLOOK, Outlook.SUNNY.toString())
                .set(TEMP, Temp.COOL.toString())
                .set(HUMIDITY, Humidity.NORMAL.toString())
                .set(WINDY, Windy.TRUE.toString())));

        tally(PlayGolf.YES.toString().toString(), forest.walk(new Feature()
                .set(PLAY_GOLF, PlayGolf.YES.toString().toString())
                .set(OUTLOOK, Outlook.OVERCAST.toString())
                .set(TEMP, Temp.COOL.toString())
                .set(HUMIDITY, Humidity.NORMAL.toString())
                .set(WINDY, Windy.TRUE.toString())));

        tally(PlayGolf.NO.toString(), forest.walk(new Feature()
                .set(PLAY_GOLF, PlayGolf.NO.toString())
                .set(OUTLOOK, Outlook.RAINY.toString())
                .set(TEMP, Temp.MILD.toString())
                .set(HUMIDITY, Humidity.HIGH.toString())
                .set(WINDY, Windy.FALSE.toString())));

        tally(PlayGolf.YES.toString().toString(), forest.walk(new Feature()
                .set(PLAY_GOLF, PlayGolf.YES.toString().toString())
                .set(OUTLOOK, Outlook.RAINY.toString())
                .set(TEMP, Temp.COOL.toString())
                .set(HUMIDITY, Humidity.NORMAL.toString())
                .set(WINDY, Windy.FALSE.toString())));

        tally(PlayGolf.YES.toString().toString(), forest.walk(new Feature()
                .set(PLAY_GOLF, PlayGolf.YES.toString().toString())
                .set(OUTLOOK, Outlook.SUNNY.toString())
                .set(TEMP, Temp.MILD.toString())
                .set(HUMIDITY, Humidity.NORMAL.toString())
                .set(WINDY, Windy.FALSE.toString())));

        tally(PlayGolf.YES.toString().toString(), forest.walk(new Feature()
                .set(PLAY_GOLF, PlayGolf.YES.toString().toString())
                .set(OUTLOOK, Outlook.RAINY.toString())
                .set(TEMP, Temp.MILD.toString())
                .set(HUMIDITY, Humidity.NORMAL.toString())
                .set(WINDY, Windy.TRUE.toString())));

        tally(PlayGolf.YES.toString().toString(), forest.walk(new Feature()
                .set(PLAY_GOLF, PlayGolf.YES.toString().toString())
                .set(OUTLOOK, Outlook.OVERCAST.toString())
                .set(TEMP, Temp.MILD.toString())
                .set(HUMIDITY, Humidity.HIGH.toString())
                .set(WINDY, Windy.TRUE.toString())));

        tally(PlayGolf.YES.toString().toString(), forest.walk(new Feature()
                .set(PLAY_GOLF, PlayGolf.YES.toString().toString())
                .set(OUTLOOK, Outlook.OVERCAST.toString())
                .set(TEMP, Temp.HOT.toString())
                .set(HUMIDITY, Humidity.NORMAL.toString())
                .set(WINDY, Windy.FALSE.toString())));

        tally(PlayGolf.NO.toString(), forest.walk(new Feature()
                .set(PLAY_GOLF, PlayGolf.NO.toString())
                .set(OUTLOOK, Outlook.SUNNY.toString())
                .set(TEMP, Temp.MILD.toString())
                .set(HUMIDITY, Humidity.HIGH.toString())
                .set(WINDY, Windy.TRUE.toString())));

        System.out.println((((double) correct) / (correct + incorrect) * 100.0) + "% correct");
    }

    private final void tally(String expected, String actual) {
        if (StringUtils.equals(expected, actual)) { correct++; }
        else { incorrect++; }
    }

    private static List<Feature> loadFeatures() {
        final List<Feature> features = new ArrayList<>();

        features.add(new Feature()
            .set(PLAY_GOLF, PlayGolf.NO.toString())
            .set(OUTLOOK, Outlook.RAINY.toString())
            .set(TEMP, Temp.HOT.toString())
            .set(HUMIDITY, Humidity.HIGH.toString())
            .set(WINDY, Windy.FALSE.toString()));

        features.add(new Feature()
                .set(PLAY_GOLF, PlayGolf.NO.toString())
                .set(OUTLOOK, Outlook.RAINY.toString())
                .set(TEMP, Temp.HOT.toString())
                .set(HUMIDITY, Humidity.HIGH.toString())
                .set(WINDY, Windy.TRUE.toString()));

        features.add(new Feature()
                .set(PLAY_GOLF, PlayGolf.YES.toString().toString())
                .set(OUTLOOK, Outlook.OVERCAST.toString())
                .set(TEMP, Temp.HOT.toString())
                .set(HUMIDITY, Humidity.HIGH.toString())
                .set(WINDY, Windy.FALSE.toString()));

        features.add(new Feature()
                .set(PLAY_GOLF, PlayGolf.YES.toString().toString())
                .set(OUTLOOK, Outlook.SUNNY.toString())
                .set(TEMP, Temp.MILD.toString())
                .set(HUMIDITY, Humidity.HIGH.toString())
                .set(WINDY, Windy.FALSE.toString()));

        features.add(new Feature()
                .set(PLAY_GOLF, PlayGolf.YES.toString().toString())
                .set(OUTLOOK, Outlook.SUNNY.toString())
                .set(TEMP, Temp.COOL.toString())
                .set(HUMIDITY, Humidity.NORMAL.toString())
                .set(WINDY, Windy.FALSE.toString()));

        features.add(new Feature()
                .set(PLAY_GOLF, PlayGolf.NO.toString())
                .set(OUTLOOK, Outlook.SUNNY.toString())
                .set(TEMP, Temp.COOL.toString())
                .set(HUMIDITY, Humidity.NORMAL.toString())
                .set(WINDY, Windy.TRUE.toString()));

        features.add(new Feature()
                .set(PLAY_GOLF, PlayGolf.YES.toString().toString())
                .set(OUTLOOK, Outlook.OVERCAST.toString())
                .set(TEMP, Temp.COOL.toString())
                .set(HUMIDITY, Humidity.NORMAL.toString())
                .set(WINDY, Windy.TRUE.toString()));

        features.add(new Feature()
                .set(PLAY_GOLF, PlayGolf.NO.toString())
                .set(OUTLOOK, Outlook.RAINY.toString())
                .set(TEMP, Temp.MILD.toString())
                .set(HUMIDITY, Humidity.HIGH.toString())
                .set(WINDY, Windy.FALSE.toString()));

        features.add(new Feature()
                .set(PLAY_GOLF, PlayGolf.YES.toString().toString())
                .set(OUTLOOK, Outlook.RAINY.toString())
                .set(TEMP, Temp.COOL.toString())
                .set(HUMIDITY, Humidity.NORMAL.toString())
                .set(WINDY, Windy.FALSE.toString()));

        features.add(new Feature()
                .set(PLAY_GOLF, PlayGolf.YES.toString().toString())
                .set(OUTLOOK, Outlook.SUNNY.toString())
                .set(TEMP, Temp.MILD.toString())
                .set(HUMIDITY, Humidity.NORMAL.toString())
                .set(WINDY, Windy.FALSE.toString()));

        features.add(new Feature()
                .set(PLAY_GOLF, PlayGolf.YES.toString().toString())
                .set(OUTLOOK, Outlook.RAINY.toString())
                .set(TEMP, Temp.MILD.toString())
                .set(HUMIDITY, Humidity.NORMAL.toString())
                .set(WINDY, Windy.TRUE.toString()));

        features.add(new Feature()
                .set(PLAY_GOLF, PlayGolf.YES.toString().toString())
                .set(OUTLOOK, Outlook.OVERCAST.toString())
                .set(TEMP, Temp.MILD.toString())
                .set(HUMIDITY, Humidity.HIGH.toString())
                .set(WINDY, Windy.TRUE.toString()));

        features.add(new Feature()
                .set(PLAY_GOLF, PlayGolf.YES.toString().toString())
                .set(OUTLOOK, Outlook.OVERCAST.toString())
                .set(TEMP, Temp.HOT.toString())
                .set(HUMIDITY, Humidity.NORMAL.toString())
                .set(WINDY, Windy.FALSE.toString()));

        features.add(new Feature()
                .set(PLAY_GOLF, PlayGolf.NO.toString())
                .set(OUTLOOK, Outlook.SUNNY.toString())
                .set(TEMP, Temp.MILD.toString())
                .set(HUMIDITY, Humidity.HIGH.toString())
                .set(WINDY, Windy.TRUE.toString()));

        return features;
    }

}
