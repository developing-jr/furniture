package sk.jrd.furniture.shape;

import com.google.common.base.Splitter;
import com.google.common.collect.Lists;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sk.jrd.furniture.shape.body.BodyBitSetBuilder;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.BitSet;
import java.util.List;
import java.util.stream.Collectors;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Represents furniture in room.
 */
public class Furniture extends AbstractShape {

    private static final Logger LOGGER = LoggerFactory.getLogger(Room.class);

    private final char type;

    Furniture(char type, int width, int height, @Nonnull BitSet body) {
        super(width, height, body);
        this.type = type;
    }

    /**
     * @return type of furniture in one letter format from first position of definition, e.g. definition - B3.#.###.#.
     */
    public char getType() {
        return type;
    }

    /**
     * @return rows of furniture in bitwise body
     */
    List<BitSet> getRows() {
        List<BitSet> result = new ArrayList<>();
        for (int i = 0; i < getBody().length(); i = i + getWidth()) {
            result.add(getBody().get(i, i + getWidth()));
        }
        return result;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Furniture furniture = (Furniture) o;

        return type == furniture.type;
    }

    @Override
    public int hashCode() {
        return (int) type;
    }

    @Override
    public String toString() {
        return "Furniture{" +
                "type=" + type +
                "} " + super.toString();
    }

    /**
     * Builder of all furniture from given definitions.
     */
    static class Builder {
        /**
         * Values separator for given furniture definitions.
         */
        static final char DEFINITION_SEPARATOR = ' ';

        private static final Splitter allDefinitionsSplitter = Splitter.on(DEFINITION_SEPARATOR).trimResults().omitEmptyStrings();

        /**
         * All furniture definitions
         */
        private final String allDefinitions;

        /**
         * @param allDefinitions with accept of following definition:<br/>
         *                       "A2#### B3.#.###.#."
         * @throws NullPointerException when allDefinitions is NUll
         */
        Builder(@Nonnull String allDefinitions) {
            this.allDefinitions = checkNotNull(allDefinitions, "All furniture definitions are NULL");
        }

        private char getType(@Nonnull String definition) {
            char result = definition.charAt(0);
            checkArgument(Character.toString(result).matches("[A-Z]"), "Not valid type of furniture");
            return result;
        }

        private int getWidth(@Nonnull String definition) {
            return Character.getNumericValue(definition.charAt(1));
        }

        private String getBodyString(@Nonnull String definition) {
            return definition.substring(2);
        }

        private int getHeight(@Nonnull String definition, int width) {
            String body = getBodyString(definition);
            checkArgument((body.length() % width) == 0, "Height of furniture couldn't be computed case of wrong furniture definition {}", definition);

            return body.length() / width;
        }

        private BitSet getBody(@Nonnull String definition) {
            return new BodyBitSetBuilder(getBodyString(definition)).build();
        }

        /**
         * @param definition definition of one furniture
         * @return furniture from given definition
         * @throws NullPointerException     if definition is NULL
         * @throws IllegalArgumentException if definition has not valid expression
         */
        private Furniture buildOne(@Nonnull String definition) {
            checkNotNull(definition);
            checkArgument(definition.length() > 2);

            // type
            final char type = getType(definition);
            // dimensions
            final int width = getWidth(definition);
            final int height = getHeight(definition, width);
            // body
            final BitSet body = getBody(definition);

            return new Furniture(type, width, height, body);
        }

        private List<String> getAllDefinitions() {
            List<String> result = Lists.newArrayList(allDefinitionsSplitter.split(allDefinitions));
            checkArgument(!result.isEmpty(), "Furniture definitions are empty");
            return result;
        }

        /**
         * @return all furniture from given string definition
         * @throws NullPointerException     if definition is NULL
         * @throws IllegalArgumentException if definition has not valid expression
         */
        @Nonnull
        List<Furniture> buildAll() {
            return getAllDefinitions().stream()
                    .map(this::buildOne)
                    .collect(Collectors.toList());
        }
    }

    public static class Factory {

        /**
         * @param allDefinitions definitions of furniture
         * @return furniture represented in given definition
         */
        @Nonnull
        public static List<Furniture> createAll(@Nonnull String allDefinitions) {
            LOGGER.info("Create of furniture from all definitions: {}", allDefinitions);
            return new Furniture.Builder(allDefinitions).buildAll();
        }
    }

}
