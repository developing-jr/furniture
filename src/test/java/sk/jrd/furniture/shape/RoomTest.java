package sk.jrd.furniture.shape;

import org.junit.Test;

import java.util.BitSet;
import java.util.Optional;

import static com.google.common.truth.Truth.assertThat;

public class RoomTest {

    @Test(expected = NullPointerException.class)
    public void createBodyNull() {
        new Room(3, 2, null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void createInvalidWith() {
        new Room(0, 2, new BitSet());
    }

    @Test(expected = IllegalArgumentException.class)
    public void createInvalidHeight() {
        new Room(3, 0, new BitSet());
    }

    @Test
    public void createSuccess() {
        new Room(3, 2, new BitSet());
    }

    @Test
    public void placeOfFurniture() {
        Furniture furniture = new Furniture.Builder("B3.#.###.#.").buildAll().iterator().next();
        // furniture
        ///.#.
        // ###
        // .#.

        // 012345 678901 234567 890123 456789
        // ..###. .####. ###### ###### ...###
        Room room = new Room.Builder("5,6 ..###. .####. ###### ###### ...###").build();
        // room:
        //..###.
        //.####.
        //######
        //######
        //...###

        assertEmptyRoomWithFurniture(0, 0, furniture, room);
        assertRoomWithFurniture(1, 0, furniture, room, new Room.Builder("5,6 ...##. ....#. ##.### ###### ...###").build().getBody());
        assertRoomWithFurniture(2, 0, furniture, room, new Room.Builder("5,6 ..#.#. .#.... ###.## ###### ...###").build().getBody());
        assertEmptyRoomWithFurniture(3, 0, furniture, room);

        assertRoomWithFurniture(0, 1, furniture, room, new Room.Builder("5,6 ..###. ..###. ...### #.#### ...###").build().getBody());
        assertRoomWithFurniture(1, 1, furniture, room, new Room.Builder("5,6 ..###. .#.##. #...## ##.### ...###").build().getBody());
        assertRoomWithFurniture(2, 1, furniture, room, new Room.Builder("5,6 ..###. .##.#. ##...# ###.## ...###").build().getBody());
        assertRoomWithFurniture(3, 1, furniture, room, new Room.Builder("5,6 ..###. .###.. ###... ####.# ...###").build().getBody());

        assertEmptyRoomWithFurniture(0, 2, furniture, room);
        assertEmptyRoomWithFurniture(1, 2, furniture, room);
        assertRoomWithFurniture(2, 2, furniture, room, new Room.Builder("5,6 ..###. .####. ###.## ##...# ....##").build().getBody());
        assertRoomWithFurniture(3, 2, furniture, room, new Room.Builder("5,6 ..###. .####. ####.# ###... ...#.#").build().getBody());
    }

    private void assertEmptyRoomWithFurniture(int positionX, int positionY, Furniture furniture, Room room) {
        Optional<BitSet> roomWithFurniture = room.placeFurniture(positionX, positionY, furniture);
        assertThat(roomWithFurniture).isNotNull();
        assertThat(roomWithFurniture.isPresent()).isFalse();
    }

    private void assertRoomWithFurniture(int positionX, int positionY, Furniture furniture, Room room, BitSet expected) {
        Optional<BitSet> roomWithFurniture = room.placeFurniture(positionX, positionY, furniture);
        assertThat(roomWithFurniture).isNotNull();
        assertThat(roomWithFurniture.isPresent()).isTrue();
        assertThat(roomWithFurniture.get()).isEqualTo(expected);
    }
}