package sk.jrd.furniture.shape;

import static com.google.common.base.Preconditions.*;

import java.util.BitSet;
import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.Nonnull;

import com.google.common.base.Splitter;
import com.google.common.collect.Lists;
import sk.jrd.furniture.shape.body.BodyBitSetBuilder;

public class Furniture extends AbstractShape {

    private final char type;

    public Furniture(char type, int width, int height, @Nonnull BitSet body) {
        super(width, height, body);
        this.type = type;
    }

    public char getType() {
        return type;
    }

    /**
     * Builder of all furniture definition from given argument.
     */
    public static class Builder {
        /**
         * Values separator for given furniture definitions.
         */
        public static final char DEFINITION_SEPARATOR = ' ';

        private static Splitter allDefinitionsSplitter = Splitter.on(DEFINITION_SEPARATOR).trimResults().omitEmptyStrings();

        /**
         * Furniture definitions
         */
        private final String allDefinitions;

        /**
         * @param allDefinitions with accept of following definition:<br/>
         *                       -DfurnitureDefinitions="A2#### B3.#.###.#."
         */
        public Builder(@Nonnull String allDefinitions) {
            this.allDefinitions = checkNotNull(allDefinitions, "All furniture definitions are NULL");
        }

        private char getType(@Nonnull String definition) {
            return definition.charAt(0);
        }

        private int getWidth(@Nonnull String definition) {
            return Character.getNumericValue(definition.charAt(1));
        }

        private String getBodyString(@Nonnull String definition) {
            return definition.substring(3);
        }

        private int getHeight(@Nonnull String definition, int width) {
            String body = getBodyString(definition);
            checkArgument((body.length() % width) == 0, "Height of furniture couldn't be computed case of wrong furniture definition {}", definition);

            return body.length() / width;
        }

        private BitSet getBody(@Nonnull String definition) {
            return new BodyBitSetBuilder(getBodyString(definition)).build();
        }

        private Furniture buildOne(@Nonnull String definition) {
            checkNotNull(definition);
            checkArgument(definition.length() > 3);

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

        @Nonnull
        public List<Furniture> buildAll() {
            return getAllDefinitions().stream()
                    .map(definition -> buildOne(definition))
                    .collect(Collectors.toList());
        }
    }
}
