// Referenced from the JsonSerialization Demo
// https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo

package persistence;

import static org.junit.jupiter.api.Assertions.assertEquals;

import model.Item;
import model.Noble;
import ca.ubc.cs.ExcludeFromJacocoGeneratedReport;

@ExcludeFromJacocoGeneratedReport
public class TestJson {

    protected void checkItem(String name, String type, int cost, int damage, Item item) {
        assertEquals(name, item.getName());
        assertEquals(type, item.getType());
        assertEquals(cost, item.getCost());
        assertEquals(damage, item.getDamage());
    }

    protected void checkNoble(String name, String title, int prestige, Noble noble) {
        assertEquals(name, noble.getName());
        assertEquals(title, noble.getTitle());
        assertEquals(prestige, noble.getPrestige());
    }
}
