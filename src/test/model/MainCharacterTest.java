package model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class MainCharacterTest {
    private MainCharacter testMainCharacter;
    private Item knife;
    private Noble charles;

    @BeforeEach
    void runBefore() {

        testMainCharacter = new MainCharacter(20, 100);
        knife = new Item("knife", "weapon", 30, 15);
        charles = new Noble("Charles", "King", 1000);
    }

    @Test
    void testConstructor() {
        assertEquals(20, testMainCharacter.getStrength());
        assertEquals(100, testMainCharacter.getMoney());
        assertEquals(0, testMainCharacter.getPeopleSaved());
        assertEquals(0, testMainCharacter.getMoneySpent());
        assertTrue(testMainCharacter.getBackpack().getItems().isEmpty());
        assertTrue(testMainCharacter.getKnownNobles().getNobles().isEmpty());

    }

    @Test
    void testSavePeople() {

        testMainCharacter.savePeople(2);
        assertEquals(2, testMainCharacter.getPeopleSaved());
    }

    @Test
    void testSpendMoney() {

        testMainCharacter.spendMoney(40);
        assertEquals(40, testMainCharacter.getMoneySpent());
        assertEquals(60, testMainCharacter.getMoney());
    }

    @Test
    void testAddToKnownNobles() {

        testMainCharacter.addToKnownNobles(charles);
        assertEquals(1, testMainCharacter.getKnownNobles().getNobles().size());
    }

    @Test
    void testAddToBackpack() {

        testMainCharacter.addToBackpack(knife);
        assertEquals(1, testMainCharacter.getBackpack().getItems().size());
    }

    @Test
    void testRemoveFromBackpack() {

        testMainCharacter.addToBackpack(knife);
        assertEquals(1, testMainCharacter.getBackpack().getItems().size());
        testMainCharacter.removeFromBackpack("knife");
        assertEquals(0, testMainCharacter.getBackpack().getItems().size());
    }

    @Test
    void testGetStats() {
        testMainCharacter.spendMoney(40);
        testMainCharacter.savePeople(2);

        assertEquals("moneySpent:40, peopleSaved:2", testMainCharacter.getStats());
    }

}
