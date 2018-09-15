package sk.jrd.furniture.shape;

import java.util.BitSet;

/**
 * Definition of shape.
 */
interface Shape {

    /**
     * @return width of shape
     */
    int getWidth();

    /**
     * @return height of shape
     */
    int getHeight();

    /**
     * @return bitwise representation of shape by {@link sk.jrd.furniture.shape.body.BodyBit}
     */
    BitSet getBody();

}
