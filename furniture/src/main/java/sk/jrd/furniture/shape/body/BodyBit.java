package sk.jrd.furniture.shape.body;

import java.util.Optional;

import com.sun.istack.internal.NotNull;

public enum BodyBit {
    True('#'),
    False('.');

    private final char value;

    BodyBit(char value) {
        this.value = value;
    }

    @NotNull
    public static Optional<BodyBit> of(char value) {
        for (BodyBit bodyBit : values()) {
            if (bodyBit.value == value) {
                return Optional.of(bodyBit);
            }
        }
        return Optional.empty();
    }
}
