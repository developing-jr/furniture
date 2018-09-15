package sk.jrd.furniture.shape.body;

import javax.annotation.Nonnull;
import java.util.BitSet;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Bitwise body builder of shape.
 *
 * @see BodyBit
 */
public class BodyBitSetBuilder {

    private final String body;

    public BodyBitSetBuilder(@Nonnull String body) {
        this.body = checkNotNull(body, "Body is NULL");
    }

    @Nonnull
    public BitSet build() {
        final BitSet result = new BitSet(body.length());

        for (int i = 0; i < body.length(); i++) {
            char c = body.charAt(i);
            if (BodyBit.of(c)
                    .orElseThrow(() -> new IllegalArgumentException("Wrong body definition " + body))
                    .equals(BodyBit.True)) {
                result.set(i);
            }
        }
        return result;
    }

}
