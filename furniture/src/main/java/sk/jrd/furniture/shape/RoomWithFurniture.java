package sk.jrd.furniture.shape;

import javax.annotation.Nonnull;
import java.util.BitSet;
import java.util.function.Function;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Represents room with placed furniture in, where body is in bitwise form.
 */
public class RoomWithFurniture extends Room {

    private final Furniture furniture;
    private final int positionX;
    private final int positionY;

    protected RoomWithFurniture(int positionX, int positionY, @Nonnull Furniture furniture, int width, int height, @Nonnull BitSet body) {
        super(width, height, body);

        this.positionX = positionX;
        this.positionY = positionY;
        this.furniture = checkNotNull(furniture);
    }

    /**
     * @return X position computed from top, left position
     */
    @SuppressWarnings("unused")
    public int getPositionX() {
        return positionX;
    }

    /**
     * @return Y position computed from top, left position
     */
    @SuppressWarnings("unused")
    public int getPositionY() {
        return positionY;
    }

    /**
     * @return placed furniture in room
     */
    public Furniture getFurniture() {
        return furniture;
    }

    /**
     * @return printed furniture in room in string format
     */
    public String print(final Function<RoomWithFurniture, String> formatter) {
        return formatter.apply(this);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        RoomWithFurniture that = (RoomWithFurniture) o;

        if (positionX != that.positionX) return false;
        //noinspection SimplifiableIfStatement
        if (positionY != that.positionY) return false;
        return furniture.equals(that.furniture);
    }

    @Override
    public int hashCode() {
        int result = furniture.hashCode();
        result = 31 * result + positionX;
        result = 31 * result + positionY;
        return result;
    }

    @Override
    public String toString() {
        return "RoomWithFurniture{" +
                "positionX=" + positionX +
                ", positionY=" + positionY +
                ", furniture=" + furniture +
                "} " + super.toString();
    }

}
