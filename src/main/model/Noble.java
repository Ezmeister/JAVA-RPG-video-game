package model;

import org.json.JSONObject;
import persistence.Writable;

// Represents an noble with name, title, and it's prestige
public class Noble implements Writable {

    private String name;
    private String title;
    private int prestige;

    // REQUIRES: prestige>=0
    // EFFECTS: constructs an noble
    public Noble(String name, String title, int prestige) {
        this.name = name;
        this.title = title;
        this.prestige = prestige;
    }

    public String getName() {
        return name;
    }

    public String getTitle() {
        return title;
    }

    public int getPrestige() {
        return prestige;
    }

    // MODIFIES: this
    // EFFECTS: return a JSON representation of this object
    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("name", name);
        json.put("title", title);
        json.put("prestige", prestige);
        return json;
    }

}
