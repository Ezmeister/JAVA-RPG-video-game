package model;

import org.json.JSONObject;

import persistence.Writable;

// Represents an item with its name, type, cost and damage.(damage can be 0)
public class Item implements Writable {
    private String name;
    private String type;
    private int cost;
    private int damage;

    // REQUIRES: cost > 0, damage >= 0
    // EFFECTS: constructs an item
    public Item(String name, String type, int cost, int damage) {
        this.name = name;
        this.type = type;
        this.cost = cost;
        this.damage = damage;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public int getCost() {
        return cost;
    }

    public int getDamage() {
        return damage;
    }

    // MODIFIES: this
    // EFFECTS: return a JSON representation of this object
    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("name", name);
        json.put("type", type);
        json.put("cost", cost);
        json.put("damage", damage);
        return json;
    }

}
