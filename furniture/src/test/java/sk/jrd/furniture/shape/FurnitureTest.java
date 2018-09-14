package sk.jrd.furniture.shape;

import static com.google.common.truth.Truth.assertThat;

import java.util.BitSet;
import java.util.List;

import org.junit.Test;

public class FurnitureTest {

    @Test(expected = NullPointerException.class)
    public void createBodyNull() {
        new Furniture('A', 3, 2, null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void createInvalidWith() {
        new Furniture('A', 0, 2, new BitSet());
    }

    @Test(expected = IllegalArgumentException.class)
    public void createInvalidHeight() {
        new Furniture('A', 3, 0, new BitSet());
    }

    @Test
    public void createSuccess() {
        new Furniture('A', 3, 2, new BitSet());
    }

    @Test
    public void getRows() {
        List<Furniture> furnitures = new Furniture.Builder("A2.##. B3##.#.#..#").buildAll();

        assertFirstRow(furnitures);
        assertSecondRow(furnitures);
    }

    private void assertFirstRow(List<Furniture> furnitures) {
        Furniture first = furnitures.get(0);
        List<BitSet> rows = first.getRows();

        assertThat(rows).isNotNull();
        assertThat(rows.size()).isEqualTo(2);

        BitSet expFirstRow = new BitSet(2);
        expFirstRow.set(1);
        assertThat(rows.get(0)).isEqualTo(expFirstRow);

        BitSet expSecondRow = new BitSet(2);
        expSecondRow.set(0);
        assertThat(rows.get(1)).isEqualTo(expSecondRow);
    }

    private void assertSecondRow(List<Furniture> furnitures) {
        Furniture second = furnitures.get(1);
        List<BitSet> rows = second.getRows();

        assertThat(rows).isNotNull();
        assertThat(rows.size()).isEqualTo(3);

        BitSet expFirstRow = new BitSet(3);
        expFirstRow.set(0);
        expFirstRow.set(1);
        assertThat(rows.get(0)).isEqualTo(expFirstRow);

        BitSet expSecondRow = new BitSet(3);
        expSecondRow.set(0);
        expSecondRow.set(2);
        assertThat(rows.get(1)).isEqualTo(expSecondRow);

        BitSet expThirdRow = new BitSet(3);
        expThirdRow.set(2);
        assertThat(rows.get(2)).isEqualTo(expThirdRow);
    }
}