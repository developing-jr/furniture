package sk.jrd.furniture.shape;

import java.util.BitSet;
import java.util.Optional;

import javax.annotation.Nonnull;

public class RoomWithFurniture extends Room {

    private final char furnitureType;
    private final int positionX;
    private final int positionY;

    private RoomWithFurniture(int positionX, int positionY, char furnitureType, int width, int height, @Nonnull BitSet body) {
        super(width, height, body);

        this.positionX = positionX;
        this.positionY = positionY;
        this.furnitureType = furnitureType;
    }

    @SuppressWarnings("unused")
    public int getPositionX() {
        return positionX;
    }

    @SuppressWarnings("unused")
    public int getPositionY() {
        return positionY;
    }

    @SuppressWarnings("unused")
    public char getFurnitureType() {
        return furnitureType;
    }

    @Override
    public String toString() {
        return "RoomWithFurniture{" +
                "furnitureType=" + furnitureType +
                ", positionX=" + positionX +
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
