package model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class BackpackTest {

    private Backpack testBackpack;
    private Item sword;

    @BeforeEach
    void runBefore() {

        testBackpack = new Backpack();
        sword = new Item("sword", "weapon", 0, 0);
    }

    @Test
    void testConstructor() {
        assertEquals(12, testBackpack.getCapacity());
        assertTrue(testBackpack.getItems().isEmpty());
    }

    @Test
    void testAddItem() {

        testBackpack.addItem(sword);
        assertEquals(1, testBackpack.getItems().size());
    }

    @Test
    void testRemoveItem() {

        testBackpack.addItem(sword);
        testBackpack.removeItem("sword");
        assertEquals(0, testBackpack.getItems().size());

        testBackpack.addItem(sword);
        testBackpack.removeItem("wor");
        assertEquals(1, testBackpack.getItems().size());
    }

}
