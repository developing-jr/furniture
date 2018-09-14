package sk.jrd.furniture;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nonnull;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import sk.jrd.furniture.shape.Furniture;
import sk.jrd.furniture.shape.Room;
import sk.jrd.furniture.shape.RoomWithFurniture;

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

    // A2 x2 y0
    // ....#.
    // .#..#.
    // ######
    // ######
    // ...###

    // B3 x1 y0
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
    }

    // TODO JR replace with some design pattern and collect parent result in room with furniture
    private static void place(@Nonnull Room room, @Nonnull List<Furniture> furnitures) {
        LOGGER.info("Place furniture with count={} for room={}", furnitures.size(), room);

        for (Furniture furniture : furnitures) {
            for (int y = 0; y <= room.getHeight() - furniture.getHeight(); y++) { // height
                for (int x = 0; x <= room.getWidth() - furniture.getWidth(); x++) { // width
                    RoomWithFurniture.Factory.create(x, y, furniture, room)
                            .ifPresent(roomWithFurniture -> {
                                if (furnitures.size() > 1) {
                                    place(roomWithFurniture, createNewExcept(furnitures, furniture));
                                } else {
                                    LOGGER.info("Room with furniture {}", roomWithFurniture);
                                }
                            });
                }
            }
        }
    }

    private static List<Furniture> createNewExcept(@Nonnull List<Furniture> furnitures, @Nonnull Furniture except) {
        List<Furniture> result = new ArrayList<>();
        for (Furniture f : furnitures) {
            if (!f.equals(except)) {
                result.add(f);
            }
        }
        return result;
    }

}
