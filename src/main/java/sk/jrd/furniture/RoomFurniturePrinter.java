package sk.jrd.furniture;

import sk.jrd.furniture.shape.RoomWithFurniture;
import sk.jrd.furniture.shape.RoomWithFurnitureNode;

import javax.annotation.Nonnull;
import java.util.*;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Collectors;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Represents print interpreter for all computed rooms with place furniture.
 */
public class RoomFurniturePrinter {

    /**
     * All combination trees
     */
    private final List<RoomWithFurnitureNode> combinationTrees;
    /**
     * The last nodes in combination trees.
     */
    private final List<RoomWithFurnitureNode> lastCombinationNodesOfTrees;
    /**
     * All combination in list form.
     */
    private final Combinations combinations;

    /**
     * @param combinationTrees all combination trees
     * @param furnitureSize    size of furniture
     * @param printer          to use
     * @param formatter        to use by printing
     */
    public RoomFurniturePrinter(@Nonnull List<RoomWithFurnitureNode> combinationTrees,
                                int furnitureSize,
                                @Nonnull Consumer<String> printer,
                                @Nonnull Function<RoomWithFurniture, String> formatter) {
        this.combinationTrees = checkNotNull(combinationTrees, "Room with furniture combination trees are NULL");

        lastCombinationNodesOfTrees = new ArrayList<>();
        combinations = new Combinations(
                checkNotNull(printer, "Room furniture combination printer is NULL"),
                formatter, furnitureSize);
    }

    /**
     * Prints all combinations.
     */
    public void print() {
        collectLastNodesInTrees();
        convertCombinationNodeTreesIntoList();
        printCombinations();
    }

    /**
     * Collects all last nodes in trees to convert combination tree into list of combinations.
     */
    private void collectLastNodesInTrees() {
        combinationTrees.forEach(node -> collectLastNode(node));
    }

    private void collectLastNode(RoomWithFurnitureNode node) {
        if (node.hasChildren()) {
            for (RoomWithFurnitureNode n : node.getChildren()) {
                collectLastNode(n);
            }
        } else {
            lastCombinationNodesOfTrees.add(node);
        }
    }

    /**
     * Converts from combination node trees into list of combinations.
     */
    private void convertCombinationNodeTreesIntoList() {
        lastCombinationNodesOfTrees.forEach(node -> collectCombinationsFromLastNode(node,
                combinations.addAndGet(new Combination())));
    }

    private void collectCombinationsFromLastNode(RoomWithFurnitureNode node, Combination combination) {
        if (node.hasParent()) {
            collectCombinationsFromLastNode(node.getParent(), combination);
        }
        combination.add(node);
    }

    /**
     * Prints all combinations.
     */
    private void printCombinations() {
        combinations
                .sort().removeDuplicities() // sort before remove of duplicities, cause of hashCode usage by HashSet
                .sort().print(); // sort before print, after remove of duplicities by HashSet
    }

    /**
     * Wrapper class for one combination represented in list.
     */
    class Combination {
        private final List<RoomWithFurniture> combination = new ArrayList<>();

        void add(RoomWithFurniture rwf) {
            combination.add(rwf);
        }

        String print(final Function<RoomWithFurniture, String> formatter) {
            return combination.stream()
                    .map(rfw -> rfw.print(formatter))
                    .collect(Collectors.joining(" "));
        }

        int size() {
            return combination.size();
        }

        void sort() {
            combination.sort(Comparator.comparingInt(o -> o.getFurniture().getType()));
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            Combination that = (Combination) o;

            return combination.equals(that.combination);
        }

        @Override
        public int hashCode() {
            return combination.hashCode();
        }

        @Override
        public String toString() {
            return "Combination{" +
                    "combination=" + combination +
                    '}';
        }
    }

    /**
     * Represents all combinations in list.
     */
    class Combinations {
        private final List<Combination> combinations = new ArrayList<>();
        private final Consumer<String> printer;
        private final Function<RoomWithFurniture, String> formatter;
        private final int furnitureSize;

        Combinations(Consumer<String> printer, Function<RoomWithFurniture, String> formatter, int furnitureSize) {
            this.printer = printer;
            this.formatter = formatter;
            this.furnitureSize = furnitureSize;
        }

        Combination addAndGet(Combination combination) {
            add(combination);
            return combination;
        }

        Combinations add(Combination combination) {
            combinations.add(combination);
            return this;
        }

        Combinations addAll(Collection<Combination> combinations) {
            this.combinations.addAll(combinations);
            return this;
        }

        Combinations sort() {
            combinations.forEach(combination -> combination.sort());
            combinations.sort(Comparator.comparing(combination -> combination.print(formatter)));
            return this;
        }

        Combinations removeDuplicities() {
            Combinations copy = new Combinations(printer, formatter, furnitureSize);
            // array list to remove of duplicity to use equals instead of hashcode
            Set<Combination> removeDuplicitySet = new LinkedHashSet<>(this.combinations);
            // add all to copy
            copy.addAll(removeDuplicitySet);
            return copy;
        }

        void print() {
            combinations.stream()
                    .filter(combination -> combination.size() == furnitureSize)
                    .forEach(combination -> printer.accept(combination.print(formatter)));
        }

        @Override
        public String toString() {
            return "Combinations{" +
                    "combinations=" + combinations +
                    '}';
        }
    }


}
