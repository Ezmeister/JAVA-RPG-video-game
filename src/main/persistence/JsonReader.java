// Referenced from the JsonSerialization Demo
// https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo

package persistence;

import model.Item;
import model.MainCharacter;
import model.Noble;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

import org.json.*;

// Represents a reader that reads workroom from JSON data stored in file
public class JsonReader {
    private String source;

    // MODIFIES: this
    // EFFECTS: constructs reader to read from source file
    public JsonReader(String source) {
        this.source = source;
    }

    // EFFECTS: reads MainCharacter from file and returns it;
    // throws IOException if an error occurs reading data from file
    public MainCharacter read() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseMainCharacter(jsonObject);
    }

    // EFFECTS: reads source file as string and returns it
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }

        return contentBuilder.toString();
    }

    // EFFECTS: parses MainCharacter from JSON object and returns it
    private MainCharacter parseMainCharacter(JSONObject jsonObject) {
        int strength = jsonObject.getInt("strength");
        int money = jsonObject.getInt("money");
        int moneySpent = jsonObject.optInt("moneySpent", 0);
        int peopleSaved = jsonObject.optInt("peopleSaved", 0);

        MainCharacter hero = new MainCharacter(strength, money + moneySpent);

        hero.spendMoney(moneySpent);

        hero.savePeople(peopleSaved);

        JSONObject backpackJson = jsonObject.getJSONObject("backpack");
        addItems(hero, backpackJson);

        JSONObject knownNoblesJson = jsonObject.getJSONObject("knownNobles");
        addNobles(hero, knownNoblesJson);

        return hero;
    }

    // MODIFIES: hero
    // EFFECTS: parses items from JSON object and adds them to hero's backpack
    private void addItems(MainCharacter hero, JSONObject backpackJson) {

        JSONArray jsonArray = backpackJson.getJSONArray("items");
        for (Object obj : jsonArray) {
            JSONObject next = (JSONObject) obj;
            addItem(hero, next);
        }
    }

    // MODIFIES: hero
    // EFFECTS: parses a single item from JSON object and adds it to hero's backpack
    private void addItem(MainCharacter hero, JSONObject json) {
        String name = json.getString("name");
        String type = json.getString("type");
        int cost = json.getInt("cost");
        int damage = json.getInt("damage");
        hero.addToBackpack(new Item(name, type, cost, damage));
    }

    // MODIFIES: hero
    // EFFECTS: parses nobles from JSON object and adds them to hero's known nobles
    private void addNobles(MainCharacter hero, JSONObject knownNoblesJson) {

        JSONArray jsonArray = knownNoblesJson.getJSONArray("nobles");
        for (Object obj : jsonArray) {
            JSONObject next = (JSONObject) obj;
            addNoble(hero, next);
        }
    }

    // MODIFIES: hero
    // EFFECTS: parses a single noble from JSON object and adds it to hero's known
    // nobles
    private void addNoble(MainCharacter hero, JSONObject json) {
        String name = json.getString("name");
        String title = json.getString("title");
        int prestige = json.getInt("prestige");
        hero.addToKnownNobles(new Noble(name, title, prestige));
    }
}
