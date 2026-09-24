package persistence;

import org.json.JSONObject;


public interface Writable {
    // EFFECTS: return a JSON representation of this object
    JSONObject toJson();
}