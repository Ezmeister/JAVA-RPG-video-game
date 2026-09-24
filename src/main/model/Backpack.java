package model;

import java.util.ArrayList;
import java.util.List;

// Represents an backpack which contains all the items that Maincharacter has
public class Backpack {

    private List<Item> items = new ArrayList<>();
    private int capacity;

    // EFFECTS: constructs an empty backpack
    public Backpack() {
        capacity = 12;
    }

    // MODIFIES: this
    // EFFECTS: adds item
    public void addItem(Item item) {
        this.items.add(item);
        EventLog.getInstance().logEvent(
                new Event("Item added to backpack: " + item.getName()
                        + " (type = " + item.getType() + ", cost = "
                        + item.getCost() + ", damage = " + item.getDamage() + ")"));
    }

    // MODIFIES: this
    // EFFECTS: remove item by name
    public void removeItem(String name) {
        for (int i = 0; i < items.size(); i++) {
            if (items.get(i).getName().equalsIgnoreCase(name)) {
                items.remove(i);
            }
        }

    }

    public int getCapacity() {
        return capacity;
    }

    public List<Item> getItems() {
        return items;
    }

    public List<Item> viewItems() {
        EventLog.getInstance().logEvent(
                new Event("Viewed all items in backpack (" + items.size() + " items)."));
        return items;
    }

    // EFFECTS: returns list of items in backpack whose type equals given type
    public List<Item> getItemsByType(String type) {
        List<Item> result = new ArrayList<>();

        for (Item it : items) {
            if (it.getType().equalsIgnoreCase(type)) {
                result.add(it);
            }
        }

        EventLog.getInstance().logEvent(
                new Event("Viewed items in backpack filtered by type = "
                        + type + " (" + result.size() + " items)."));

        return result;
    }

}
