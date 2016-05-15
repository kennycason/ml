package kenny.ml.nlp.ner;

import kenny.ml.utils.ResourceLoader;
import opennlp.tools.namefind.NameFinderME;
import opennlp.tools.namefind.TokenNameFinderModel;
import opennlp.tools.util.Span;
import org.eclipse.collections.api.list.ListIterable;
import org.eclipse.collections.api.list.MutableList;
import org.eclipse.collections.impl.factory.Lists;

import java.io.IOException;

/**
 * Created by kenny on 5/9/16.
 */
public class NamedEntityRecognizer {

    private final NameFinderME personNer;
    private final NameFinderME locationNer;
    private final NameFinderME organizationNer;

    public NamedEntityRecognizer() {
        // Load the model file downloaded from OpenNLP
        // http://opennlp.sourceforge.net/models-1.5/en-ner-person.bin
        try {
            personNer = new NameFinderME(
                                new TokenNameFinderModel(
                                        ResourceLoader.toInputStream("kenny/ml/nlp/ner/en-ner-person.bin")));
            locationNer = new NameFinderME(
                    new TokenNameFinderModel(
                            ResourceLoader.toInputStream("kenny/ml/nlp/ner/en-ner-location.bin")));
            organizationNer = new NameFinderME(
                    new TokenNameFinderModel(
                            ResourceLoader.toInputStream("kenny/ml/nlp/ner/en-ner-organization.bin")));

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public ListIterable<Span> tokenize(final String[] tokens) {
        final MutableList<Span> spans = Lists.mutable.empty();
        for (final Span span : personNer.find(tokens)) {
            spans.add(span);
        }
        for (final Span span : locationNer.find(tokens)) {
            spans.add(span);
        }
        for (final Span span : organizationNer.find(tokens)) {
            spans.add(span);
        }
        return spans;
    }

}
