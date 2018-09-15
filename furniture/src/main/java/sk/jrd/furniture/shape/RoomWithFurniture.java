package sk.jrd.furniture.shape;

import com.google.common.collect.ImmutableList;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.BitSet;
import java.util.List;
import java.util.Optional;

import static com.google.common.base.Preconditions.checkNotNull;

// TODO JR create RoomWithFurnitureNode for children and parent
public class RoomWithFurniture extends Room {

    private final Furniture furniture;
    private final int positionX;
    private final int positionY;

    private RoomWithFurniture parent;
    private final List<RoomWithFurniture> children;

    private RoomWithFurniture(int positionX, int positionY, @Nonnull Furniture furniture, int width, int height, @Nonnull BitSet body) {
        super(width, height, body);

        this.positionX = positionX;
        this.positionY = positionY;
        this.furniture = checkNotNull(furniture);

        parent = null;
        children = new ArrayList<>();
    }

    @SuppressWarnings("unused")
    public int getPositionX() {
        return positionX;
    }

    @SuppressWarnings("unused")
    public int getPositionY() {
        return positionY;
    }

    public Furniture getFurniture() {
        return furniture;
    }

    public RoomWithFurniture getParent() {
        return parent;
    }

    public void setParent(RoomWithFurniture parent) {
        this.parent = parent;
    }

    public boolean hasParent() {
        return parent != null;
    }

    public boolean hasChildren() {
        return children.size() > 0;
    }

    public ImmutableList<RoomWithFurniture> getChildren() {
        return ImmutableList.copyOf(children);
    }

    public void addChild(RoomWithFurniture child) {
        if (child != null) {
            children.add(child);
        }
    }

    public String print() {
        return furniture.getType() + "(" + positionX + "," + positionY + ")";
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

    public static class Factory {

        public static Optional<RoomWithFurniture> create(int positionX, int positionY, Furniture furniture, Room room) {
            Optional<BitSet> bodyWithPlacedFurniture = room.placeFurniture(positionX, positionY, furniture);
            if (bodyWithPlacedFurniture.isPresent()) {
                return bodyWithPlacedFurniture.map(body -> new RoomWithFurniture(
                        positionX, positionY,
                        furniture,
                        room.getWidth(), room.getHeight(), body));
            }
            return Optional.empty();
        }
    }
}
