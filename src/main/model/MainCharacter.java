package model;

import org.json.JSONArray;
import org.json.JSONObject;

import persistence.Writable;

// Represents the MainCharacter of the game
public class MainCharacter implements Writable {

    private int strength;
    private int money;
    private int moneySpent;
    private int peopleSaved;
    private Backpack backpack;
    private KnownNobles knownNobles;

    // REQUIRES: strength>=0 money>=0
    // EFFECTS: constructs an MainCharacter
    public MainCharacter(int strength, int money) {
        this.strength = strength;
        this.money = money;
        this.moneySpent = 0;
        this.peopleSaved = 0;
        this.backpack = new Backpack();
        this.knownNobles = new KnownNobles();
    }

    // REQUIRES: count > 0
    // MODIFIES: this
    // EFFECTS: increases peopleSaved by count then returns new total
    public void savePeople(int count) {
        peopleSaved = peopleSaved + count;
    }

    // REQUIRES: cost >= 0
    // MODIFIES: this
    // EFFECTS: reduces money by cost and increases moneySpent by cost; returns new
    // money
    public void spendMoney(int cost) {
        money = money - cost;
        moneySpent = moneySpent + cost;
    }

    // MODIFIES: this
    // EFFECTS: adds item to KnownNobles
    public void addToKnownNobles(Noble noble) {
        knownNobles.addNoble(noble);
    }

    // MODIFIES: this
    // EFFECTS: adds item to backpack
    public void addToBackpack(Item item) {
        backpack.addItem(item);
    }

    // MODIFIES: this
    // EFFECTS: removes an item by name from backpack
    public void removeFromBackpack(String name) {
        backpack.removeItem(name);
    }

    // EFFECTS: returns stats of main character
    public String getStats() {
        return "moneySpent:" + moneySpent + ", peopleSaved:" + peopleSaved;
    }

    public int getStrength() {
        return strength;
    }

    public int getMoney() {
        return money;
    }

    public int getMoneySpent() {
        return moneySpent;
    }

    public int getPeopleSaved() {
        return peopleSaved;
    }

    public Backpack getBackpack() {
        return backpack;
    }

    public KnownNobles getKnownNobles() {
        return knownNobles;
    }

    // MODIFIES: this
    // EFFECTS: return a JSON representation of this object
    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("strength", strength);
        json.put("money", money);
        json.put("moneySpent", moneySpent);
        json.put("peopleSaved", peopleSaved);

        JSONObject bp = new JSONObject();
        bp.put("capacity", backpack.getCapacity());
        bp.put("items", itemsToJson());
        json.put("backpack", bp);

        JSONObject kn = new JSONObject();
        kn.put("nobles", noblesToJson());
        json.put("knownNobles", kn);

        return json;
    }

    // MODIFIES: this
    // EFFECTS: returns items in backpack as a JSON array
    private JSONArray itemsToJson() {
        JSONArray arr = new JSONArray();
        for (Item it : backpack.getItems()) {
            arr.put(it.toJson());
        }
        return arr;
    }

    // MODIFIES: this
    // EFFECTS: returns nobles in knownNobles as a JSON array
    private JSONArray noblesToJson() {
        JSONArray arr = new JSONArray();
        for (Noble n : knownNobles.getNobles()) {
            arr.put(n.toJson());
        }
        return arr;
    }

}
