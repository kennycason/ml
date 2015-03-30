package kenny.ml.cluster.feature;


import ch.lambdaj.Lambda;
import kenny.ml.cluster.SampleCorpus;
import org.junit.Test;

/**
 * Created by kenny on 2/17/14.
 */
public class TransformTest {

    @Test
    public void transform() {
        System.out.println(Lambda.join(Transform.invert(SampleCorpus.buildMoviedCritics(), "RATING"), "\n"));
    }
}
