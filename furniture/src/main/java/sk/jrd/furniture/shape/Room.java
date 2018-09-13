package sk.jrd.furniture.shape;

import static com.google.common.base.Preconditions.*;

import java.util.BitSet;
import java.util.List;

import javax.annotation.Nonnull;

import com.google.common.base.Joiner;
import com.google.common.base.Splitter;
import com.google.common.collect.Lists;
import sk.jrd.furniture.shape.body.BodyBitSetBuilder;

public class Room extends AbstractShape {

    public Room(int width, int height, @Nonnull BitSet body) {
        super(width, height, body);
    }

    public static class Builder {
        /**
         * All values separator given for room definition.
         */
        public static final char DEFINITION_SEPARATOR = ' ';
        /**
         * Height and width separator
         */
        public static final char DIMENSION_SEPARATOR = ',';

        private static Splitter definitionSplitter = Splitter.on(DEFINITION_SEPARATOR).trimResults().omitEmptyStrings();
        private static Splitter dimensionsSplitter = Splitter.on(DIMENSION_SEPARATOR).trimResults().omitEmptyStrings();

        /**
         * Room definition.
         */
        private final String definition;

        /**
         * @param definition with accept of following definition:<br/>
         *                   -DroomDefinition="5,6 ..###. .####. ###### ###### ...###"
         */
        public Builder(@Nonnull String definition) {
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
            return definitions.subList(1, definitions.size() - 1);
        }


        private String getDimensions(@Nonnull List<String> definitions) {
            checkArgument(definitions.size() > 0, "Not valid room dimensions");
            return definitions.get(0);
        }

        private List<String> getDefinitions() {
            List<String> definitions = Lists.newArrayList(definitionSplitter.split(definition));
            checkArgument(definitions.isEmpty(), "Room definitions are empty");
            return definitions;
        }

        public Room build() {
            List<String> definitions = getDefinitions();
            String dimensions = getDimensions(definitions);
            List<String> roomDefinition = getRoomDefinition(definitions);

            // dimensions
            final int width = getWidth(dimensions);
            final int height = getHeight(dimensions);
            // body string
            final BitSet body = getBody(roomDefinition, width, height);

            return new Room(width, height, body);
        }
    }

}
