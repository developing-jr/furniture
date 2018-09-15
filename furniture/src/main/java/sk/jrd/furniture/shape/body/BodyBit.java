package sk.jrd.furniture.shape.body;

import javax.annotation.Nonnull;
import java.util.Optional;

/**
 * Bit of shape, which represents either occupied or free place within.
 */
public enum BodyBit {
    True('#'),
    False('.');

    private final char value;

    BodyBit(char value) {
        this.value = value;
    }

    @Nonnull
    public static Optional<BodyBit> of(char value) {
        for (BodyBit bodyBit : values()) {
            if (bodyBit.value == value) {
                return Optional.of(bodyBit);
            }
        }
        return Optional.empty();
    }
}
