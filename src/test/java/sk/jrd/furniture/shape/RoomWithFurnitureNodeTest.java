package sk.jrd.furniture.shape;

import org.junit.Before;
import org.junit.Test;

import java.util.BitSet;

import static com.google.common.truth.Truth.assertThat;

public class RoomWithFurnitureNodeTest {

    private static final int ROOM_WIDTH = 6;
    private static final int ROOM_HEIGHT = 5;

    private RoomWithFurnitureNode node;

    @Before
    public void setUp() throws Exception {
        node = new RoomWithFurnitureNode(3, 2,
                new Furniture('A', 2, 3, new BitSet()),
                ROOM_WIDTH, ROOM_HEIGHT, new BitSet());
    }

    @Test
    public void addChild() {
        node.addChild(new RoomWithFurnitureNode(0, 0,
                new Furniture('A', 2, 3, new BitSet()),
                ROOM_WIDTH, ROOM_HEIGHT, new BitSet()));
        node.addChild(new RoomWithFurnitureNode(1, 1,
                new Furniture('B', 2, 2, new BitSet()),
                ROOM_WIDTH, ROOM_HEIGHT, new BitSet()));

        assertThat(node.hasChildren());
        assertThat(node.getChildren().size()).isEqualTo(2);

        assertNode(node.getChildren().get(0),
                0, 0,
                new Furniture('A', 2, 3, new BitSet()),
                ROOM_WIDTH, ROOM_HEIGHT, new BitSet());
        assertNode(node.getChildren().get(1),
                1, 1,
                new Furniture('B', 2, 3, new BitSet()),
                ROOM_WIDTH, ROOM_HEIGHT, new BitSet());
    }

    private void assertNode(RoomWithFurnitureNode node,
                            int positionX, int positionY,
                            Furniture furniture,
                            int width, int height, BitSet body) {
        assertThat(node).isNotNull();
        assertThat(node.getPositionX()).isEqualTo(positionX);
        assertThat(node.getPositionY()).isEqualTo(positionY);
        assertThat(node.getFurniture()).isEqualTo(furniture);
        assertThat(node.getWidth()).isEqualTo(width);
        assertThat(node.getHeight()).isEqualTo(height);
        assertThat(node.getBody()).isEqualTo(body);
    }

    @Test
    public void setParent() throws Exception {
        node.setParent(new RoomWithFurnitureNode(0, 0,
                new Furniture('A', 2, 3, new BitSet()),
                ROOM_WIDTH, ROOM_HEIGHT, new BitSet()));

        assertThat(node.hasParent()).isTrue();
        assertNode(node.getParent(),
                0, 0,
                new Furniture('A', 2, 3, new BitSet()),
                ROOM_WIDTH, ROOM_HEIGHT, new BitSet());
    }
}