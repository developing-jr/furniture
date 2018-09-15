package sk.jrd.furniture.shape;

import static com.google.common.truth.Truth.assertThat;

import java.util.BitSet;

import org.junit.Test;

public class RoomBuilderTest {

    @Test(expected = NullPointerException.class)
    public void createNull() {
        new Room.Builder(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void buildWithEmptyDefinition() {
        new Room.Builder("").build();
    }

    @Test(expected = IllegalArgumentException.class)
    public void buildWithNotValidDimensions() {
        new Room.Builder("5,").build();
    }

    @Test(expected = IllegalArgumentException.class)
    public void buildWithNotValidValueDimensions() {
        new Room.Builder("5,A").build();
    }

    @Test(expected = IllegalArgumentException.class)
    public void buildWithNotValidHeightOfRoomDefinition() {
        new Room.Builder("5,6 ..###. .####. ###### ######").build();
    }

    @Test(expected = IllegalArgumentException.class)
    public void buildWithNotValidWidthOfRoomDefinition() {
        new Room.Builder("5,6 ..###. .####. ###### ###### ...##").build();
    }

    @Test
    public void buildSuccess() {
        Room room = new Room.Builder("5,6 ..###. .####. ###### ###### ...###").build();

        assertThat(room).isNotNull();
        assertThat(room.getHeight()).isEqualTo(5);
        assertThat(room.getWidth()).isEqualTo(6);

        //5,6 ..###. .####. ###### ###### ...###
        //5,6 012345 678901 234567 890123 456789
        BitSet expected = new BitSet(30);
        expected.set(2, 5);
        expected.set(7, 11);
        expected.set(12, 18);
        expected.set(18, 24);
        expected.set(27, 30);
        assertThat(room.getBody()).isEqualTo(expected);
    }

}