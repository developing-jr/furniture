package sk.jrd.furniture.shape.body;

import org.junit.Test;

public class BodyBitSetBuilderTest {

    @Test(expected = NullPointerException.class)
    public void createWhenBodyIsNull() {
        new BodyBitSetBuilder(null);
    }

    // TODO JR follow implementation from here
}