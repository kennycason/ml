package kenny.ml.decisiontree;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by kenny
 */
public class Tree {

    public String label;

    public Enum value;

    public Map<Enum, Tree> children = new HashMap<>();

    public boolean isLeaf() {
        return value != null;
    }

    public Enum walk(final Feature feature) {
       return walk(feature, children.get(feature.get(label)));
    }

    private static Enum walk(final Feature feature, final Tree tree) {
        if(tree == null) { return null; }
        if(tree.value != null) { return tree.value; }

        return walk(feature, tree.children.get(feature.get(tree.label)));
    }

    @Override
    public String toString() {
        return "Tree{" +
                "label='" + label + '\'' +
                ", value=" + value +
                ", children=" + children +
                '}';
    }
}
