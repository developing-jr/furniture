package sk.jrd.furniture.shape;

import com.google.common.collect.ImmutableList;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.BitSet;
import java.util.List;
import java.util.Optional;

/**
 * Represents room with placed furniture for tree node.
 */
public class RoomWithFurnitureNode extends RoomWithFurniture {

    private RoomWithFurnitureNode parent;
    private final List<RoomWithFurnitureNode> children;

    RoomWithFurnitureNode(int positionX, int positionY, @Nonnull Furniture furniture, int width, int height, @Nonnull BitSet body) {
        super(positionX, positionY, furniture, width, height, body);

        parent = null;
        children = new ArrayList<>();
    }

    /**
     * @return parent of room with furniture in tree
     */
    public RoomWithFurnitureNode getParent() {
        return parent;
    }

    /**
     * Sets parent
     *
     * @param parent parent of room with furniture in tree to set
     */
    public void setParent(RoomWithFurnitureNode parent) {
        this.parent = parent;
    }

    /**
     * @return {@code true} if room with furniture has parent in tree
     */
    public boolean hasParent() {
        return parent != null;
    }

    /**
     * @return {@code true} if room with furniture has children in tree
     */
    public boolean hasChildren() {
        return children.size() > 0;
    }

    public ImmutableList<RoomWithFurnitureNode> getChildren() {
        return ImmutableList.copyOf(children);
    }

    /**
     * Adds child of room with furniture in tree
     *
     * @param child to set
     */
    public void addChild(RoomWithFurnitureNode child) {
        if (child != null) {
            children.add(child);
        }
    }

    public static class Factory {

        /**
         * Creates room with placed furniture for node tree.
         *
         * @param positionX X position of furniture in bitwise body and computed from top, left position
         * @param positionY Y position of furniture in bitwise body and computed from top, left position
         * @param furniture to place in room
         * @param room      to place furniture in
         * @return room with furniture object if furniture was successfully placed in room on given X, Y position, otherwise return empty optional
         */
        public static Optional<RoomWithFurnitureNode> create(int positionX, int positionY, Furniture furniture, Room room) {
            Optional<BitSet> bodyWithPlacedFurniture = room.placeFurniture(positionX, positionY, furniture);
            if (bodyWithPlacedFurniture.isPresent()) {
                return bodyWithPlacedFurniture.map(body -> new RoomWithFurnitureNode(
                        positionX, positionY,
                        furniture,
                        room.getWidth(), room.getHeight(), body));
            }
            return Optional.empty();
        }
    }
}
