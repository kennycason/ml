package kenny.ml.cluster;


import kenny.ml.cluster.feature.Category;
import kenny.ml.cluster.feature.GeneralFeature;
import kenny.ml.cluster.feature.Item;
import kenny.ml.cluster.feature.impl.RatingFeature;

import java.awt.*;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Created by kenny on 2/16/14.
 */
public class SampleCorpus {

    public static List<Category> buildMoviedCritics() {
        List<Category> categories = new LinkedList<>();
        Category lisa = new Category("Lisa Rose");
        lisa.addItem(new Item("Lady in the Water", new RatingFeature(2.5)));
        lisa.addItem(new Item("Snakes on a plane", new RatingFeature(3.5)));
        lisa.addItem(new Item("Just My Luck", new RatingFeature(3.0)));
        lisa.addItem(new Item("Superman Returns", new RatingFeature(3.5)));
        lisa.addItem(new Item("You, Me and Dupree", new RatingFeature(2.5)));
        lisa.addItem(new Item("The Night Listener", new RatingFeature(3.0)));
        categories.add(lisa);

        Category gene = new Category("Gene Seymour");
        gene.addItem(new Item("Lady in the Water", new RatingFeature(3.0)));
        gene.addItem(new Item("Snakes on a plane", new RatingFeature(3.5)));
        gene.addItem(new Item("Just My Luck", new RatingFeature(1.5)));
        gene.addItem(new Item("Superman Returns", new RatingFeature(5.0)));
        gene.addItem(new Item("You, Me and Dupree", new RatingFeature(3.5)));
        gene.addItem(new Item("The Night Listener", new RatingFeature(3.0)));
        categories.add(gene);

        Category michael = new Category("Michael Phillips");
        michael.addItem(new Item("Lady in the Water", new RatingFeature(2.5)));
        michael.addItem(new Item("Snakes on a plane", new RatingFeature(3.0)));
        michael.addItem(new Item("Superman Returns", new RatingFeature(3.5)));
        michael.addItem(new Item("The Night Listener", new RatingFeature(4.0)));
        categories.add(michael);

        Category claudia = new Category("Claudia Puig");
        claudia.addItem(new Item("Snakes on a plane", new RatingFeature(3.5)));
        claudia.addItem(new Item("Just My Luck", new RatingFeature(3.0)));
        claudia.addItem(new Item("Superman Returns", new RatingFeature(4.0)));
        claudia.addItem(new Item("The Night Listener", new RatingFeature(4.5)));
        claudia.addItem(new Item("You, Me and Dupree", new RatingFeature(2.5)));
        categories.add(claudia);

        Category mick = new Category("Mick LaSalle");
        mick.addItem(new Item("Lady in the Water", new RatingFeature(3.0)));
        mick.addItem(new Item("Snakes on a plane", new RatingFeature(4.0)));
        mick.addItem(new Item("Just My Luck", new RatingFeature(2.0)));
        mick.addItem(new Item("Superman Returns", new RatingFeature(3.0)));
        mick.addItem(new Item("The Night Listener", new RatingFeature(3.0)));
        mick.addItem(new Item("You, Me and Dupree", new RatingFeature(2.0)));
        categories.add(mick);

        Category jack = new Category("Jack Matthews");
        jack.addItem(new Item("Lady in the Water", new RatingFeature(3.0)));
        jack.addItem(new Item("Snakes on a plane", new RatingFeature(4.0)));
        jack.addItem(new Item("Superman Returns", new RatingFeature(5.0)));
        jack.addItem(new Item("The Night Listener", new RatingFeature(3.0)));
        jack.addItem(new Item("You, Me and Dupree", new RatingFeature(3.5)));
        categories.add(jack);

        Category toby = new Category("Toby");
        toby.addItem(new Item("Snakes on a plane", new RatingFeature(4.5)));
        toby.addItem(new Item("Superman Returns", new RatingFeature(4.0)));
        toby.addItem(new Item("You, Me and Dupree", new RatingFeature(1.0)));
        categories.add(toby);

        return categories;
    }

    public static List<Item> buildColors() {
        try {
            Map<String, Color> singleColors = new HashMap<>();
            for (Field cf : Color.class.getDeclaredFields()) {
                int modifiers = cf.getModifiers();
                if (!Modifier.isPublic(modifiers)) continue;

                Color c = (Color)cf.get(null);
                if (!singleColors.values().contains(c))
                    singleColors.put(cf.getName(), c);
            }
            List<Item> items = new LinkedList<>();
            for (String k : singleColors.keySet()) {
                Item item = new Item(k);
                item.addFeature(new GeneralFeature("R", singleColors.get(k).getRed()));
                item.addFeature(new GeneralFeature("G", singleColors.get(k).getGreen()));
                item.addFeature(new GeneralFeature("B", singleColors.get(k).getBlue()));

                items.add(item);
            }
            return items;
        } catch(IllegalAccessException e) {
            System.err.println(e.getMessage());
            e.printStackTrace();
        }
        return Collections.emptyList();
    }

    // http://mnemstudio.org/clustering-k-means-example-1.htm
    public static List<Item> kMeansData() {
        return new LinkedList<>(Arrays.asList(
                new Item("1", Arrays.asList(
                        new GeneralFeature("A", 1.0),
                        new GeneralFeature("B", 1.0)
                )),
                new Item("2", Arrays.asList(
                        new GeneralFeature("A", 1.5),
                        new GeneralFeature("B", 2.0)
                )),
                new Item("3", Arrays.asList(
                        new GeneralFeature("A", 3.0),
                        new GeneralFeature("B", 4.0)
                )),
                new Item("4", Arrays.asList(
                        new GeneralFeature("A", 5.0),
                        new GeneralFeature("B", 7.0)
                )),
                new Item("5", Arrays.asList(
                        new GeneralFeature("A", 3.5),
                        new GeneralFeature("B", 5.0)
                )),
                new Item("6", Arrays.asList(
                        new GeneralFeature("A", 4.5),
                        new GeneralFeature("B", 5.0)
                )),
                new Item("7", Arrays.asList(
                        new GeneralFeature("A", 3.5),
                        new GeneralFeature("B", 4.5)
                ))
        ));
    }

}
