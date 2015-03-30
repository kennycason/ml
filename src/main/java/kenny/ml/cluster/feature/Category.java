package kenny.ml.cluster.feature;

import ch.lambdaj.Lambda;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

/**
 * Created by kenny on 2/16/14.
 */
public class Category implements Comparable<Category> {

    private final String name;

    private Map<String, Item> items;

    private double score = 0;

    public Category(String name) {
        this.name = name;
        items = new HashMap<>();
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

    public void addItem(Item item) {
        this.items.put(item.getName(), item);
    }

    public boolean containsItem(String item) {
        return items.containsKey(item);
    }

    public Item getItem(String item) {
        if(containsItem(item)) {
            return items.get(item);
        }
        return null;
    }

    public Set<String> getItemNames() {
        return items.keySet();
    }

    public Map<String, Item> getItems() {
        return items;
    }

    public Set<String> intersection(Category category) {
        Set<String> intersection = new TreeSet<>(getItemNames());
        intersection.retainAll(category.getItemNames());
        return intersection;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Category)) return false;

        Category category = (Category) o;

        if (!name.equals(category.name)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }

    @Override
    public int compareTo(Category category) {
        if(getScore() < category.getScore()) { return -1; }
        if(getScore() > category.getScore()) { return 1; }
        return 0;
    }

    @Override
    public String toString() {
        return "Category{" +
                "name='" + name + '\'' +
                ", score='" + score + '\'' +
                ", items=" + Lambda.join(items, "\n\t") +
                '}';
    }

}
