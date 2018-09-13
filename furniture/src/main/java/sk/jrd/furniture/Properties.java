package sk.jrd.furniture;

import static com.google.common.base.Preconditions.checkNotNull;

class Properties {

    private static final String ROOM_DEFINITION = System.getProperty("roomDefinition");
    private static final String FURNITURE_DEFINITIONS = System.getProperty("furnitureDefinitions");

    static String getRoomDefinition() {
        return checkNotNull(ROOM_DEFINITION, "Room definition is NULL");
    }

    static String getFurnitureDefinitions() {
        return checkNotNull(FURNITURE_DEFINITIONS, "Furniture definitions are NULL");
    }

}
