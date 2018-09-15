package sk.jrd.furniture;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sk.jrd.furniture.shape.Furniture;
import sk.jrd.furniture.shape.Room;
import sk.jrd.furniture.shape.RoomWithFurnitureNode;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Represents placer of furniture in room.
 */
public class RoomFurniturePlacer {

    private static final Logger LOGGER = LoggerFactory.getLogger(RoomFurniturePlacer.class);

    private final List<RoomWithFurnitureNode> combinationTrees = new ArrayList<>();

    /**
     * @return all combination trees of place furniture in room
     */
    @Nonnull
    public List<RoomWithFurnitureNode> getCombinationTrees() {
        return combinationTrees;
    }

    public RoomFurniturePlacer place(@Nonnull final Room room, final @Nonnull List<Furniture> furnitures) {
        LOGGER.debug("Place furniture with count={} for room={}", furnitures.size(), room);
        for (Furniture furniture : furnitures) { // all other furniture to check

            for (int y = 0; y <= room.getHeight() - furniture.getHeight(); y++) { // height
                for (int x = 0; x <= room.getWidth() - furniture.getWidth(); x++) { // width

                    // create tree node for room with placed furniture
                    RoomWithFurnitureNode.Factory.create(x, y, furniture, room)
                            .ifPresent(roomWithFurniture -> {

                                if (room instanceof RoomWithFurnitureNode) { // add child
                                    RoomWithFurnitureNode rwf = (RoomWithFurnitureNode) room;
                                    roomWithFurniture.setParent(rwf);
                                    rwf.addChild(roomWithFurniture);
                                } else {
                                    combinationTrees.add(roomWithFurniture); // add root
                                }

                                if (hasOtherFurnitures(furnitures)) { // to check recursion
                                    place(roomWithFurniture, createAndGetOtherFurnitures(furnitures, furniture));
                                } else {
                                    LOGGER.debug("Furniture placed in room {}", roomWithFurniture);
                                }
                            });
                }
            }
        }
        return this;
    }

    /**
     * @param furnitures to check
     * @return {@code true} if collection has other furniture to process
     */
    private boolean hasOtherFurnitures(@Nonnull final List<Furniture> furnitures) {
        return furnitures.size() > 1;
    }

    /**
     * @param furnitures whole collection
     * @param furniture  to omit
     * @return new furniture collect without given furniture to omit
     */
    private List<Furniture> createAndGetOtherFurnitures(@Nonnull final List<Furniture> furnitures, final Furniture furniture) {
        return furnitures.stream()
                .filter(other -> !other.equals(furniture))
                .collect(Collectors.toList());
    }

}
