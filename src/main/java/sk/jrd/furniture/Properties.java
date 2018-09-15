package sk.jrd.furniture;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Utility to get system properties.
 */
class Properties {

    private static final String ROOM_DEFINITION = System.getProperty("roomDefinition");
    private static final String FURNITURE_DEFINITIONS = System.getProperty("furnitureDefinitions");

    /**
     * @return room definition from system property {@link #ROOM_DEFINITION}
     */
    static String getRoomDefinition() {
        return checkNotNull(ROOM_DEFINITION, "Room definition is NULL");
    }

    /**
     * @return furniture definitions from system property {@link #FURNITURE_DEFINITIONS}
     */
    static String getFurnitureDefinitions() {
        return checkNotNull(FURNITURE_DEFINITIONS, "Furniture definitions are NULL");
    }

}
