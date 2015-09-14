package kenny.ml.decisiontree;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by kenny
 */
public class Tree {

    public String label;

    public String value;

    public Map<String, Tree> children = new HashMap<>();

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
