package sk.jrd.furniture.shape;

import static com.google.common.base.Preconditions.*;

import java.util.BitSet;
import java.util.List;
import java.util.Optional;

import javax.annotation.Nonnull;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Joiner;
import com.google.common.base.Splitter;
import com.google.common.collect.Lists;
import sk.jrd.furniture.shape.body.BodyBitSetBuilder;

public class Room extends AbstractShape {

    private static final Logger LOGGER = LoggerFactory.getLogger(Room.class);

    Room(int width, int height, @Nonnull BitSet body) {
        super(width, height, body);
    }

    @Override
    public String toString() {
        return "Room{} " + super.toString();
    }

    private int getFromBitIndex(int positionX, int positionY) {
        return (positionY * getWidth()) // choose row
                + positionX; // choose column
    }

    @Nonnull
    Optional<BitSet> placeFurniture(int positionX, int positionY, @Nonnull Furniture furniture) {
        LOGGER.debug("Place on position X={} Y={} the furniture={} for body={}", positionX, positionY, furniture, getBody());

        checkArgument(positionX < getWidth(), "Position X overflows room width");
        checkArgument(positionY < getHeight(), "Position Y overflows room width");

        // set for return with placed furniture
        BitSet flipSet = (BitSet) getBody().clone();

        List<BitSet> furnitureRows = furniture.getRows();
        int rowIdx = 0;
        boolean breakAll = false;
        for (int i = getFromBitIndex(positionX, positionY);
             i < flipSet.length() && rowIdx < furnitureRows.size();
             i = i + getWidth()) { // through whole room
            BitSet furnitureRow = furnitureRows.get(rowIdx++);

            for (int j = 0; j < furniture.getWidth(); j++) { // bitwise operation
                boolean furnitureBit = furnitureRow.get(j);
                boolean roomBit = flipSet.get(i + j);

                // and + flip
                if (furnitureBit && roomBit) {
                    flipSet.flip(i + j); // occupied bit
                } else if ((!furnitureBit && !roomBit) || (!furnitureBit && roomBit)) {
                    continue; // free bit
                } else {
                    breakAll = true;
                    break;
                }
            }

            if (breakAll) break;
        }

        if (!breakAll) {
            return Optional.of(flipSet);
        } else {
            return Optional.empty();
        }
    }

    static class Builder {
        /**
         * All values separator given for room definition.
         */
        static final char DEFINITION_SEPARATOR = ' ';
        /**
         * Height and width separator
         */
        static final char DIMENSION_SEPARATOR = ',';

        private static final Splitter definitionSplitter = Splitter.on(DEFINITION_SEPARATOR).trimResults().omitEmptyStrings();
        private static final Splitter dimensionsSplitter = Splitter.on(DIMENSION_SEPARATOR).trimResults().omitEmptyStrings();

        /**
         * Room definition.
         */
        private final String definition;

        /**
         * @param definition with accept of following definition:<br/>
         *                   -DroomDefinition="5,6 ..###. .####. ###### ###### ...###"
         */
        Builder(@Nonnull String definition) {
            this.definition = checkNotNull(definition, "Definition of room is NULL");
        }

        /**
         * @param dimensions is e.g. "5,6"
         * @return with from dimension, it's second argument after the comma
         */
        private int getWidth(@Nonnull String dimensions) {
            List<String> dimensionList = Lists.newArrayList(dimensionsSplitter.split(dimensions));
            checkArgument(dimensionList.size() == 2, "Wrong dimensions of room {}", dimensions);
            return Integer.parseInt(dimensionList.get(1));
        }

        /**
         * @param dimensions is e.g. "5,6"
         * @return height from dimension, it's first argument after the comma
         */
        private int getHeight(@Nonnull String dimensions) {
            List<String> dimensionList = Lists.newArrayList(dimensionsSplitter.split(dimensions));
            checkArgument(dimensionList.size() == 2, "Wrong dimensions of room {}", dimensions);
            return Integer.parseInt(dimensionList.get(0));
        }

        private String getBodyString(@Nonnull List<String> roomDefinition) {
            return Joiner.on("").join(roomDefinition);
        }

        private BitSet getBody(@Nonnull List<String> roomDefinition, int width, int height) {
            checkArgument(roomDefinition.size() == height, "Wrong height room definition");
            String bodyString = getBodyString(roomDefinition);
            checkArgument((bodyString.length() % width) == 0, "Wrong width room definition");
            return new BodyBitSetBuilder(bodyString).build();
        }

        private List<String> getRoomDefinition(@Nonnull List<String> definitions) {
            checkArgument(definitions.size() > 1, "Not valid room definition");
            return definitions.subList(1, definitions.size());
        }


        private String getDimensions(@Nonnull List<String> definitions) {
            checkArgument(definitions.size() > 0, "Not valid room dimensions");
            return definitions.get(0);
        }

        private List<String> getDefinitions() {
            List<String> definitions = Lists.newArrayList(definitionSplitter.split(definition));
            checkArgument(!definitions.isEmpty(), "Room definitions are empty");
            return definitions;
        }

        @Nonnull
        Room build() {
            List<String> definitions = getDefinitions();

            // dimensions
            String dimensions = getDimensions(definitions);
            final int width = getWidth(dimensions);
            final int height = getHeight(dimensions);

            // body string
            List<String> roomDefinition = getRoomDefinition(definitions);
            final BitSet body = getBody(roomDefinition, width, height);

            return new Room(width, height, body);
        }
    }

    public static class Factory {

        @Nonnull
        public static Room create(@Nonnull String definition) {
            LOGGER.info("Create of room from definition: {}", definition);
            return new Room.Builder(definition).build();
        }
    }

}
