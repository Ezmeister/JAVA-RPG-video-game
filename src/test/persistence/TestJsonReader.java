// Referenced from the JsonSerialization Demo
// https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
package persistence;

import model.MainCharacter;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

import ca.ubc.cs.ExcludeFromJacocoGeneratedReport;

@ExcludeFromJacocoGeneratedReport
class TestJsonReader extends TestJson {



    @Test
    void testReaderNonExistentFile() {
        JsonReader reader = new JsonReader("./data/noSuchFile.json");
        try {
            MainCharacter hero = reader.read();
            fail("IOException expected");
        } catch (IOException e) {

        }
    }

    @Test
    void testReaderEmptyHero() {
        persistence.JsonReader reader = new persistence.JsonReader("./data/testReaderEmpty.json");
        try {
            MainCharacter hero = reader.read();

            assertEquals(50, hero.getStrength());
            assertEquals(300, hero.getMoney());
            assertEquals(0, hero.getMoneySpent());
            assertEquals(0, hero.getPeopleSaved());

            assertEquals(0, hero.getBackpack().getItems().size());
            assertEquals(0, hero.getKnownNobles().getNobles().size());
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    @Test
    void testReaderGeneralHero() {
        persistence.JsonReader reader = new persistence.JsonReader("./data/testReader.json");
        try {
            MainCharacter hero = reader.read();

            assertEquals(50, hero.getStrength());

            assertEquals(101, hero.getMoneySpent());
            assertEquals(3, hero.getPeopleSaved());
            assertEquals(199, hero.getMoney());

            assertEquals(2, hero.getBackpack().getItems().size());
            checkItem("Sword", "Weapon", 81, 40, hero.getBackpack().getItems().get(0));
            checkItem("Potion", "Consumable", 20, 0, hero.getBackpack().getItems().get(1));

            assertEquals(1, hero.getKnownNobles().getNobles().size());
            checkNoble("Arthur", "Duke", 25, hero.getKnownNobles().getNobles().get(0));

        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

}
