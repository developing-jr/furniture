package sk.jrd.furniture.shape;

import static com.google.common.truth.Truth.assertThat;

import java.util.List;

import org.junit.Test;

public class FurnitureBuilderTest {

    @Test(expected = NullPointerException.class)
    public void createNull() {
        new Furniture.Builder(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void buildWithEmptyDefinition() {
        new Furniture.Builder("").buildAll();
    }

    @Test(expected = IllegalArgumentException.class)
    public void buildWithSmallDefinition() {
        new Furniture.Builder("12").buildAll();
    }

    @Test(expected = IllegalArgumentException.class)
    public void buildWithInvalidFurnitureType() {
        new Furniture.Builder("12#").buildAll();
    }

    @Test(expected = IllegalArgumentException.class)
    public void buildWithInvalidFurnitureWidth() {
        new Furniture.Builder("AA#").buildAll();
    }

    @Test(expected = IllegalArgumentException.class)
    public void buildWithInvalidFurnitureHeight() {
        new Furniture.Builder("A2.##").buildAll();
    }

    @Test
    public void buildOneFurnitureWithSuccess() {
        List<Furniture> furnitures = new Furniture.Builder("A2.##.").buildAll();

        assertThat(furnitures).isNotNull();
        assertThat(furnitures.size()).isEqualTo(1);

        assertFirstFurniture(furnitures);
    }

    private void assertFirstFurniture(List<Furniture> furnitures) {
        Furniture first = furnitures.get(0);
        assertThat(first).isNotNull();
        assertThat(first.getType()).isEqualTo('A');
        assertThat(first.getWidth()).isEqualTo(2);
        assertThat(first.getHeight()).isEqualTo(2);
        assertThat(first.getBody()).isNotNull();
        assertThat(first.getBody().length()).isEqualTo(3);
        assertThat(first.getBody().cardinality()).isEqualTo(2);
    }

    @Test
    public void buildMoreFurnitureWithSuccess() {
        List<Furniture> furnitures = new Furniture.Builder("A2.##. B3##.#.#").buildAll();

        assertThat(furnitures).isNotNull();
        assertThat(furnitures.size()).isEqualTo(2);

        assertFirstFurniture(furnitures);
        assertSecondFurniture(furnitures);
    }

    private void assertSecondFurniture(List<Furniture> furnitures) {
        Furniture second = furnitures.get(1);
        assertThat(second).isNotNull();
        assertThat(second.getType()).isEqualTo('B');
        assertThat(second.getWidth()).isEqualTo(3);
        assertThat(second.getHeight()).isEqualTo(2);
        assertThat(second.getBody()).isNotNull();
        assertThat(second.getBody().length()).isEqualTo(6);
        assertThat(second.getBody().cardinality()).isEqualTo(4);
    }

}