package sk.jrd.furniture.shape;

import javax.annotation.Nonnull;
import java.util.BitSet;
import java.util.Optional;

public class RoomWithFurniture extends Furniture {

    private final int positionX;
    private final int positionY;

    private RoomWithFurniture(int positionX, int positionY, char type, int width, int height, @Nonnull BitSet body) {
        super(type, width, height, body);

        this.positionX = positionX;
        this.positionY = positionY;
    }

    @SuppressWarnings("unused")
    public int getPositionX() {
        return positionX;
    }

    @SuppressWarnings("unused")
    public int getPositionY() {
        return positionY;
    }

    @Override
    public String toString() {
        return "RoomWithFurniture{" +
                "positionX=" + positionX +
                ", positionY=" + positionY +
                "} " + super.toString();
    }

    public static class Factory {

        public static Optional<RoomWithFurniture> create(int positionX, int positionY, Furniture furniture, Room room) {
            Optional<BitSet> optBody = room.placeFurniture(positionX, positionY, furniture);
            if (optBody.isPresent()) {
                return optBody.map(body -> new RoomWithFurniture(positionX, positionY,
                        furniture.getType(),
                        room.getWidth(), room.getHeight(), body));
            }
            return Optional.empty();
        }
    }
}
