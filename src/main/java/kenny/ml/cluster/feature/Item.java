package kenny.ml.cluster.feature;

import ch.lambdaj.Lambda;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

/**
 * Created by kenny on 2/13/14.
 */
public class Item implements Comparable<Item>, Iterable<Feature> {

    private final String name;

    private double score = 0;

    private Map<String, Feature> features = new HashMap<>();

    public Item(String name) {
        this(name, Collections.emptyList());
    }

    public Item(String name, Feature... features) {
        this(name, Arrays.asList(features));
    }

    public Item(String name, Collection<? extends Feature> features) {
        this.name = name;
        for(Feature feature : features) {
            addFeature(feature);
        }
    }

    public boolean contains(String feature) {
        return features.containsKey(feature);
    }

    public Feature getFeature(String feature) {
        if(contains(feature)) {
            return features.get(feature);
        }
        return null;
    }

    public void addFeature(Feature feature) {
        features.put(feature.getName(), feature);
    }

    public Set<String> getFeatures() {
        return features.keySet();
    }

    public String getName() {
        return name;
    }

    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
    }

    @Override
    public Iterator<Feature> iterator() {
        return features.values().iterator();
    }

    public Set<String> intersection(Item item2) {
        Set<String> intersection = new TreeSet<>(getFeatures());
        intersection.retainAll(item2.getFeatures());
        return intersection;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Item)) return false;

        Item features = (Item) o;

        if (!name.equals(features.name)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }

    @Override
    public int compareTo(Item item) {
        if(getScore() < item.getScore()) { return -1; }
        if(getScore() > item.getScore()) { return 1; }
        return 0;
    }

    @Override
    public String toString() {
        return "Item{" +
                "name='" + name + '\'' +
                ", score='" + score + '\'' +
                ", features=" + Lambda.join(features) +
                '}';
    }

}
