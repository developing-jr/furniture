package sk.jrd.furniture.shape.body;

import static com.google.common.truth.Truth.assertThat;

import java.util.BitSet;

import org.junit.Test;

public class BodyBitSetBuilderTest {

    @Test(expected = NullPointerException.class)
    public void createWhenBodyIsNull() {
        new BodyBitSetBuilder(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void buildWithInvalidData() {
        new BodyBitSetBuilder("---").build();
    }

    @Test
    public void buildWithValid() {
        BitSet body = new BodyBitSetBuilder(".##.").build();

        assertThat(body).isNotNull();
        assertThat(body.length()).isEqualTo(3); // end of the last bit
        assertThat(body.cardinality()).isEqualTo(2);
        assertThat(body.get(0)).isFalse();
        assertThat(body.get(1)).isTrue();
        assertThat(body.get(2)).isTrue();
        assertThat(body.get(3)).isFalse();
    }

}