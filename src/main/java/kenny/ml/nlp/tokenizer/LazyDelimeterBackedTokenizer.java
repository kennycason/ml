package kenny.ml.nlp.tokenizer;

import org.eclipse.collections.api.RichIterable;
import org.eclipse.collections.api.list.MutableList;
import org.eclipse.collections.impl.factory.Lists;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Iterator;

/**
 * Created by kenny on 3/27/16.
 *
 * lazily tokenize an input stream until a delimiting token is consumed.
 */
public class LazyDelimeterBackedTokenizer implements Iterable<String> {
    private final char delimiter;

    private final BufferedReader bufferedReader;

    private boolean endOfStream;

    public LazyDelimeterBackedTokenizer(final char delimeter, final InputStream inputStream) {
        this.delimiter = delimeter;
        this.bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
    }

    // defeats the purpose of "lazy"
    public RichIterable<String> tokenize() {
        final MutableList<String> tokens = Lists.mutable.empty();
        for(;;) {
            final String next = next();
            if (next == null) { break; }
            tokens.add(next);
        }
        return tokens;
    }

    public String next() {
        if (endOfStream) { return null; }

        final StringBuilder token = new StringBuilder();
        for (;;) {
            final char next = readNextCharacter();
            if (next == delimiter) { break; }
            token.append(next);
        }
        return token.toString();
    }

    private char readNextCharacter() {
        try {
            int next = bufferedReader.read();
            if (next == -1) {
                endOfStream = true;
                return delimiter;
            }
            return (char) next;

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Iterator<String> iterator() {
        final LazyDelimeterBackedTokenizer that = this;
        return new Iterator<String>() {
            private String last = "start";

            @Override
            public boolean hasNext() {
                return last != null;
            }

            @Override
            public String next() {
                last = that.next();
                return last;
            }
        };
    }
}
