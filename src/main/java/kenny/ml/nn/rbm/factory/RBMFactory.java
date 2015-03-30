package kenny.ml.nn.rbm.factory;


import kenny.ml.nn.rbm.RBM;

/**
 * Created by kenny on 5/16/14.
 */
public interface RBMFactory {
    RBM build(final int numVisibleNodes, final int numHiddenNodes);
}
