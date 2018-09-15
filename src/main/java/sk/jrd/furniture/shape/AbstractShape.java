package sk.jrd.furniture.shape;

import javax.annotation.Nonnull;
import java.util.BitSet;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Abstract base implementation of shape.
 */
abstract class AbstractShape implements Shape {

    private final int width;
    private final int height;
    private final BitSet body;

    AbstractShape(int width, int height, @Nonnull BitSet body) {
        checkArgument(width > 0, "With of shape has not valid size, has to be greater than 0");
        checkArgument(height > 0, "Height of shape has not valid size, has to be greater than 0");
        this.width = width;
        this.height = height;

        this.body = checkNotNull(body, "Body of shape is NULL");
    }

    @Override
    public int getWidth() {
        return width;
    }

    @Override
    public int getHeight() {
        return height;
    }

    @Override
    public BitSet getBody() {
        return body;
    }

    @Override
    public String toString() {
        return "AbstractShape{" +
                "width=" + width +
                ", height=" + height +
                ", body=" + body +
                '}';
    }
}
