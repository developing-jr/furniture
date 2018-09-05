package sk.jrd.furniture;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by DEVELOPING-JR.
 */
public class FurniturePlacer {

    private static final Logger LOGGER = LoggerFactory.getLogger(FurniturePlacer.class);

    private static final String ROOM_DEFINITION = System.getProperty("roomDefinition");
    private static final String FURNITURE_DEFINITIONS = System.getProperty("furnitureDefinitions");

    public static void main(String[] args) {
        LOGGER.info("Start of Furniture Placer");
        LOGGER.info("Room definition: {}", ROOM_DEFINITION);
        LOGGER.info("Furniture definitions: {}", FURNITURE_DEFINITIONS);
    }
}
