package sk.jrd.furniture;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sk.jrd.furniture.shape.Furniture;
import sk.jrd.furniture.shape.Room;
import sk.jrd.furniture.shape.RoomWithFurniture;

import javax.annotation.Nonnull;
import java.util.*;
import java.util.function.Consumer;
import java.util.stream.Collectors;

class FurniturePlacer {

    private static final Logger LOGGER = LoggerFactory.getLogger(FurniturePlacer.class);

    // Sample data
    // -DroomDefinition=5,6 ..###. .####. ###### ###### ...###" "-DfurnitureDefinitions=A2#### B3.#.###.#."

    // furnitures:
    // A2
    // ##
    // ##

    // B3
    // .#.
    // ###
    // .#.

    // room:
    // ..###.
    // .####.
    // ######
    // ######
    // ...###

    // A2 furniture placed in room on x2 y0
    // ....#.
    // .#..#.
    // ######
    // ######
    // ...###

    // B3 furniture placed in room on x1 y0
    // ...##.
    // ....#.
    // ##.###
    // ######
    // ...###

    public static void main(String[] args) {
        LOGGER.info("Start of Furniture Placer");

        Room room = Room.Factory.create(Properties.getRoomDefinition());
        List<Furniture> furnitures = Furniture.Factory.createAll(Properties.getFurnitureDefinitions());

        place(room, furnitures);
        print(furnitures.size());
    }

    private static final List<RoomWithFurniture> allCombinationNodes = new ArrayList<>();

    private static void place(@Nonnull Room room, @Nonnull List<Furniture> furnitures) {
        LOGGER.info("Place furniture with count={} for room={}", furnitures.size(), room);
        for (Furniture furniture : furnitures) {

            for (int y = 0; y <= room.getHeight() - furniture.getHeight(); y++) { // height
                for (int x = 0; x <= room.getWidth() - furniture.getWidth(); x++) { // width

                    RoomWithFurniture.Factory.create(x, y, furniture, room)
                            .ifPresent(roomWithFurniture -> {

                                if (room instanceof RoomWithFurniture) {
                                    RoomWithFurniture rwf = (RoomWithFurniture) room;
                                    roomWithFurniture.setParent(rwf);
                                    rwf.addChild(roomWithFurniture);
                                } else {
                                    allCombinationNodes.add(roomWithFurniture);
                                }

                                if (hasOtherFurnitures(furnitures)) {
                                    place(roomWithFurniture, createAndGetOtherFurnitures(furnitures, furniture));
                                } else {
                                    LOGGER.info("Furniture placed in room {}", roomWithFurniture);
                                }
                            });
                }
            }
        }
    }

    private static boolean hasOtherFurnitures(final List<Furniture> furnitures) {
        return furnitures.size() > 1;
    }

    private static List<Furniture> createAndGetOtherFurnitures(final List<Furniture> furnitures, final Furniture furniture) {
        return furnitures.stream()
                .filter(other -> !other.equals(furniture))
                .collect(Collectors.toList());
    }


    private static final List<RoomWithFurniture> lastCombinationNodes = new ArrayList<>();
    private static final RoomWithFurnitureCombinations combinations = new RoomWithFurnitureCombinations(LOGGER::info);

    private static void print(int furnitureSize) {
        // collect last nodes
        allCombinationNodes.forEach(FurniturePlacer::collectLastNode);

        // collect all combinations into one item
        lastCombinationNodes.forEach(rwf -> collectCombinationsFromLastNode(rwf, combinations.addAndGet(new RoomWithFurnitureCombination())));

        // print all combinations
        combinations
                .sort().removeDuplicities() // sort before remove of duplicities, cause to use hashCode by HashSet
                .sort().print(furnitureSize); // sort before print, after remove of duplicities by HashSet
    }

    private static void collectLastNode(RoomWithFurniture rwf) {
        if (rwf.hasChildren()) {
            for (RoomWithFurniture r : rwf.getChildren()) {
                collectLastNode(r);
            }
        } else {
            lastCombinationNodes.add(rwf);
        }
    }

    private static void collectCombinationsFromLastNode(RoomWithFurniture rwf, RoomWithFurnitureCombination combination) {
        if (rwf.hasParent()) {
            collectCombinationsFromLastNode(rwf.getParent(), combination);
        }
        combination.add(rwf);
    }

    static class RoomWithFurnitureCombination {
        private final List<RoomWithFurniture> roomWithFurnitures = new ArrayList<>();

        void add(RoomWithFurniture roomWithFurniture) {
            roomWithFurnitures.add(roomWithFurniture);
        }

        String print() {
            return roomWithFurnitures.stream().map(RoomWithFurniture::print).collect(Collectors.joining(" "));
        }

        int size() {
            return roomWithFurnitures.size();
        }

        void sort() {
            roomWithFurnitures.sort(Comparator.comparingInt(o -> o.getFurniture().getType()));
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            RoomWithFurnitureCombination that = (RoomWithFurnitureCombination) o;

            return roomWithFurnitures.equals(that.roomWithFurnitures);
        }

        @Override
        public int hashCode() {
            return roomWithFurnitures.hashCode();
        }

        @Override
        public String toString() {
            return "RoomWithFurnitureCombination{" +
                    "roomWithFurnitures=" + roomWithFurnitures +
                    '}';
        }
    }

    static class RoomWithFurnitureCombinations {
        private final List<RoomWithFurnitureCombination> combinations = new ArrayList<>();

        private final Consumer<String> printer;

        RoomWithFurnitureCombinations(Consumer<String> printer) {
            this.printer = printer;
        }

        RoomWithFurnitureCombination addAndGet(RoomWithFurnitureCombination combination) {
            combinations.add(combination);
            return combination;
        }

        RoomWithFurnitureCombinations sort() {
            for (RoomWithFurnitureCombination c : combinations) {
                c.sort();
            }

            combinations.sort(Comparator.comparing(RoomWithFurnitureCombination::print));
            return this;
        }

        RoomWithFurnitureCombinations removeDuplicities() {
            RoomWithFurnitureCombinations copy = new RoomWithFurnitureCombinations(printer);

            // array list to remove of duplicity to use equals instead of hashcode
            Set<RoomWithFurnitureCombination> removeDuplicitySet = new LinkedHashSet<>();
            removeDuplicitySet.addAll(this.combinations);

            for (RoomWithFurnitureCombination c : removeDuplicitySet) {
                copy.addAndGet(c);
            }

            return copy;
        }

        void print(int combinationSize) {
            combinations.stream()
                    .filter(combination -> combination.size() == combinationSize)
                    .forEach(combination -> printer.accept(combination.print()));
        }

        @Override
        public String toString() {
            return "RoomWithFurnitureCombinations{" +
                    "combinations=" + combinations +
                    '}';
        }
    }

}
