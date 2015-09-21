package kenny.ml.decisiontree;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by kenny
 */
public class Tree {

    public final String label;

    public final String value;

    public final Map<String, Tree> children = new HashMap<>();

    public Tree(final String label, final String value) {
        this.label = label;
        this.value = value;
    }

    public boolean isLeaf() {
        return value != null;
    }

    public String walk(final Feature feature) {
       return walk(feature, children.get(feature.get(label)));
    }

    private static String walk(final Feature feature, final Tree tree) {
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
