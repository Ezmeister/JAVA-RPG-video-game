package model;

import java.util.ArrayList;
import java.util.List;

// Represents all nobles that maincharacter knows
public class KnownNobles {

    private List<Noble> nobles = new ArrayList<>();

    // EFFECTS: constructs an empty list which indicates known nobles
    public KnownNobles() {

    }

    // MODIFIES: this
    // EFFECTS: adds noble into list
    public void addNoble(Noble noble) {
        this.nobles.add(noble);
    }

    public List<Noble> getNobles() {
        return nobles;
    }

}
