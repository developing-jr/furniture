package sk.jrd.furniture.shape;

import static com.google.common.base.Preconditions.*;

import java.util.BitSet;

import javax.annotation.Nonnull;

abstract class AbstractShape implements Shape {

    private int width;
    private int height;
    private BitSet body;

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
}
