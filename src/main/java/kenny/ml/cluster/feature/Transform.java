package kenny.ml.cluster.feature;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Created by kenny on 2/17/14.
 */
public class Transform {

    private Transform() {}

    /**
     * invert item and category information
     * @param categories
     * @return
     */
    public static List<Category> invert(List<Category> categories, String featureToInvert) {
        Map<Item, Category> transformed = new HashMap<>();
        for(Category category : categories) {
            for(Item item : category.getItems().values()) {
                if(!transformed.containsKey(item)) {
                    transformed.put(item, new Category(item.getName()));
                }
                GeneralFeature feature = new GeneralFeature(
                        item.getFeature(featureToInvert).getName(),
                        item.getFeature(featureToInvert).getValue()
                );
                Item newItem = new Item(category.getName());
                newItem.addFeature(feature);

                transformed.get(item).getItems().put(newItem.getName(), newItem);
            }
        }

        return new LinkedList(transformed.values());
    }

}
