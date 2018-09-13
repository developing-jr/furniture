package sk.jrd.furniture;

import javafx.util.Pair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sk.jrd.furniture.shape.Furniture;
import sk.jrd.furniture.shape.Room;
import sk.jrd.furniture.shape.RoomWithFurniture;

import java.util.ArrayList;
import java.util.List;

class FurniturePlacer {

    private static final Logger LOGGER = LoggerFactory.getLogger(FurniturePlacer.class);

    public static void main(String[] args) {
        LOGGER.info("Start of Furniture Placer");

        Room room = Room.Factory.create(Properties.getRoomDefinition());
        List<Furniture> furnitures = Furniture.Factory.createAll(Properties.getFurnitureDefinitions());

        @SuppressWarnings("unused") List<Pair<Furniture, List<RoomWithFurniture>>> combinations = createCombinations(room, furnitures);
    }

    private static List<Pair<Furniture, List<RoomWithFurniture>>> createCombinations(Room room, List<Furniture> furnitures) {
        LOGGER.info("Create combinations for room={} and furniture={}", room, furnitures);
        final List<Pair<Furniture, List<RoomWithFurniture>>> combinations = new ArrayList<>();

        for (Furniture furniture : furnitures) {
            final Pair<Furniture, List<RoomWithFurniture>> pair = new Pair<>(furniture, new ArrayList<>());
            combinations.add(pair);

            for (int x = 0; x < room.getWidth() - furniture.getWidth(); x++) { // width
                for (int y = 0; y < room.getHeight() - furniture.getHeight(); y++) { // height
                    RoomWithFurniture.Factory.create(x, y, furniture, room)
                            .ifPresent(roomWithFurniture -> pair.getValue().add(roomWithFurniture));
                }
            }
        }

        return combinations;
    }
}
