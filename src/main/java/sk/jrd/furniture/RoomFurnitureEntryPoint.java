package sk.jrd.furniture;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sk.jrd.furniture.shape.Furniture;
import sk.jrd.furniture.shape.Room;

import java.util.List;

/**
 * Entry point class.
 */
class RoomFurnitureEntryPoint {

    private static final Logger LOGGER = LoggerFactory.getLogger(RoomFurnitureEntryPoint.class);

    // Sample data
    // -DroomDefinition="5,6 ..###. .####. ###### ###### ...###"
    // -DfurnitureDefinitions="A2#### B3.#.###.#."

    // furnitures:
    // A2 B3
    // ## .#.
    // ## ###

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

        new RoomFurniturePrinter(
                new RoomFurniturePlacer().place(room, furnitures).getCombinationTrees(),
                furnitures.size(),
                combination -> LOGGER.info(combination),
                rwf -> rwf.getFurniture().getType() + "(" + rwf.getPositionX() + "," + rwf.getPositionY() + ")")
                .print();
    }
}
