package sk.jrd.furniture.shape.body;

import static com.google.common.truth.Truth.assertThat;

import java.util.Optional;

import org.junit.Test;

public class BodyBitTest {

    @Test
    public void createNotValidChar() {
        Optional<BodyBit> bodyBit = BodyBit.of('-');

        assertThat(bodyBit).isNotNull();
        assertThat(bodyBit.isPresent()).isFalse();
    }

    @Test
    public void createTrueBodyBit() {
        Optional<BodyBit> bodyBit = BodyBit.of('#');

        assertThat(bodyBit).isNotNull();
        assertThat(bodyBit.isPresent()).isTrue();
        assertThat(bodyBit.get()).isEqualTo(BodyBit.True);
    }

    @Test
    public void createFalseBodyBit() {
        Optional<BodyBit> bodyBit = BodyBit.of('.');

        assertThat(bodyBit).isNotNull();
        assertThat(bodyBit.isPresent()).isTrue();
        assertThat(bodyBit.get()).isEqualTo(BodyBit.False);
    }

}