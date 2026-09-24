// Referenced from the JsonSerialization Demo
// https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
package persistence;


import model.Item;
import model.MainCharacter;
import model.Noble;

import org.junit.jupiter.api.Test;

import java.io.IOException;


import static org.junit.jupiter.api.Assertions.*;
import ca.ubc.cs.ExcludeFromJacocoGeneratedReport;

@ExcludeFromJacocoGeneratedReport
public class TestJsonWriter extends TestJson {



    @Test
    void testWriterInvalidFile() {
        try {
            JsonWriter writer = new JsonWriter("./data/invalid*name.json");
            writer.open();
            fail("IOException was expected");
        } catch (IOException e) {
        }
    }

    @Test
    void testWriterEmptyHero() {
        try {
            MainCharacter hero = new MainCharacter(50, 300);

            JsonWriter writer = new JsonWriter("./data/testempty.json");
            writer.open();
            writer.write(hero);
            writer.close();

            JsonReader reader = new JsonReader("./data/testempty.json");
            MainCharacter loaded = reader.read();

            assertEquals(50, loaded.getStrength());
            assertEquals(300, loaded.getMoney());
            assertEquals(0, loaded.getMoneySpent());
            assertEquals(0, loaded.getPeopleSaved());
            assertEquals(0, loaded.getBackpack().getItems().size());
            assertEquals(0, loaded.getKnownNobles().getNobles().size());
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        } 
    }


    @Test
    void testWriterGeneralHero() {
        try {
            MainCharacter hero = new MainCharacter(80, 500);
            hero.savePeople(7);
            hero.spendMoney(120);
            hero.addToBackpack(new Item("Axe", "Weapon", 120, 55));
            hero.addToBackpack(new Item("Herb", "Consumable", 10, 0));
            hero.addToKnownNobles(new Noble("Elaine", "Countess", 18));

            JsonWriter writer = new JsonWriter("./data/testwriter.json");
            writer.open();
            writer.write(hero);
            writer.close();

            JsonReader reader = new JsonReader("./data/testwriter.json");
            MainCharacter loaded = reader.read();


            assertEquals(80, loaded.getStrength());
            assertEquals(380, loaded.getMoney());   
            assertEquals(120, loaded.getMoneySpent());
            assertEquals(7, loaded.getPeopleSaved());


            assertEquals(2, loaded.getBackpack().getItems().size());
            checkItem("Axe",  "Weapon",     120, 55, loaded.getBackpack().getItems().get(0));
            checkItem("Herb", "Consumable", 10,  0,  loaded.getBackpack().getItems().get(1));


            assertEquals(1, loaded.getKnownNobles().getNobles().size());
            checkNoble("Elaine", "Countess", 18, loaded.getKnownNobles().getNobles().get(0));
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        } 
    }


}
