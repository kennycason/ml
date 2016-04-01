package kenny.ml.data.imdb;

import org.eclipse.collections.api.list.MutableList;
import org.eclipse.collections.impl.list.mutable.FastList;

/**
 * Created by kenny on 3/28/16.
 */
public class ImdbData {
    public final Data train = new Data();
    public final Data test = new Data();

    public static class Data {
        public final MutableList<String> positive = new FastList<>();
        public final MutableList<String> negative = new FastList<>();
    }
}
