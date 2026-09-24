package model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ItemTest {

    private Item testSword;

    @BeforeEach
    void runBefore() {

        testSword = new Item("hammer", "weapon", 20, 20);

    }

    @Test
    void testConstructor() {
        assertEquals("hammer", testSword.getName());
        assertEquals("weapon", testSword.getType());
        assertEquals(20, testSword.getCost());
        assertEquals(20, testSword.getDamage());
    }

}
