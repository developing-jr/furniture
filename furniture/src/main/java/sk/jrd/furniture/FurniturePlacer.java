package sk.jrd.furniture;

import java.util.BitSet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FurniturePlacer {

    private static final Logger LOGGER = LoggerFactory.getLogger(FurniturePlacer.class);

    private static final String ROOM_DEFINITION = System.getProperty("roomDefinition");
    private static final String FURNITURE_DEFINITIONS = System.getProperty("furnitureDefinitions");

    public static void main(String[] args) {
        LOGGER.info("Start of Furniture Placer");
        LOGGER.info("Room definition: {}", ROOM_DEFINITION);
        LOGGER.info("Furniture definitions: {}", FURNITURE_DEFINITIONS);

        // BitSet example
        BitSet bits1 = new BitSet(16);
        BitSet bits2 = new BitSet(16);

        // set some bits
        for (int i = 0; i < 16; i++) {
            if ((i % 2) == 0) bits1.set(i);
            if ((i % 5) != 0) bits2.set(i);
        }

        System.out.println("Initial pattern in bits1: ");
        System.out.println(bits1);
        System.out.println("\nInitial pattern in bits2: ");
        System.out.println(bits2);

        // AND bits
        bits2.and(bits1);
        System.out.println("\nbits2 AND bits1: ");
        System.out.println(bits2);

        // OR bits
        bits2.or(bits1);
        System.out.println("\nbits2 OR bits1: ");
        System.out.println(bits2);

        // XOR bits
        bits2.xor(bits1);
        System.out.println("\nbits2 XOR bits1: ");
        System.out.println(bits2);
    }
}
