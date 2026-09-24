package ui;

import model.Item;
import model.MainCharacter;
import model.Noble;
import persistence.JsonReader;
import persistence.JsonWriter;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;

import ca.ubc.cs.ExcludeFromJacocoGeneratedReport;

@ExcludeFromJacocoGeneratedReport
public class GameApp {
    private Scanner input;
    private MainCharacter hero;
    private static final String JSON_STORE = "./data/save.json";
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;

    // EFFECTS: runs the teller application
    public GameApp() {
        runGame();
    }

    // MODIFIES: this
    // EFFECTS: processes user input
    public void runGame() {
        init();
        boolean keepGoing = true;
        String command = null;

        while (keepGoing) {
            displayMenu();
            command = input.next().toLowerCase();
            input.nextLine();
            keepGoing = processMenuCommands(command);
        }

        System.out.println("Goodbye!");

    }

    // MODIFIES: this
    // EFFECTS: create a hero and a scanner
    private void init() {
        input = new Scanner(System.in);
        hero = new MainCharacter(10, 300);
        System.out.println("Welcome, knight");
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);
    }

    // EFFECTS: show main menu
    private void displayMenu() {
        System.out.println("-----Adventure Console-----");
        System.out.println("a: add item to backpack");
        System.out.println("b: view backpack");
        System.out.println("r: remove item from backpack");
        System.out.println("n: view known nobles");
        System.out.println("k: add a known noble");
        System.out.println("s: view stats");
        System.out.println("w: save game");
        System.out.println("l: load game");
        System.out.println("q: quit");
    }

    // EFFECTS: menu command; return false if user chose to quit
    @SuppressWarnings("methodlength")
    private boolean processMenuCommands(String input) {
        switch (input) {
            case "a":
                addItemToBackpack();
                return true;
            case "b":
                viewBackpack();
                return true;
            case "r":
                removeItemFromBackpack();
                return true;
            case "n":
                viewKnownNobles();
                return true;
            case "k":
                addKnownNoble();
                return true;
            case "s":
                viewStats();
                return true;
            case "w":
                saveGame();
                return true;
            case "l":
                loadGame();
                return true;
            case "q":
                return false;
            default:
                System.out.println("Invalid option inputted. Please try again.");
                return true;
        }
    }

    // MODIFIES: this
    // EFFECTS: build item information; spend money and add to backpack
    private void addItemToBackpack() {

        System.out.print("Item name: ");
        String name = input.nextLine();

        System.out.print("Item type: ");
        String type = input.nextLine();

        System.out.print("Item cost: ");
        Integer cost = input.nextInt();

        System.out.print("Item damage: ");
        Integer damage = input.nextInt();

        System.out.println("Item name:" + name + ", Item type:" + type
                + ", Item cost:" + cost + ", Item damage:" + damage);

        Item item = new Item(name, type, cost, damage);

        hero.spendMoney(cost);
        hero.addToBackpack(item);
        System.out.println("Added to backpack. Gold left: " + hero.getMoney());
    }

    // EFFECTS: view all ites in backpack
    private void viewBackpack() {
        List<Item> items = hero.getBackpack().getItems();
        if (items.isEmpty()) {
            System.out.println("Backpack is empty.");
            return;
        }

        System.out.println("Backpack");
        for (int i = 0; i < items.size(); i++) {
            Item item = items.get(i);
            String name = item.getName();
            String type = item.getType();
            int cost = item.getCost();
            int damage = item.getDamage();
            System.out.println("Item name:" + name + ", Item type:" + type
                    + ", Item cost:" + cost + ", Item damage:" + damage);

        }
    }

    // MODIFIES: this
    // EFFECTS: remove a item from backpack
    private void removeItemFromBackpack() {

        System.out.print("type name of item: ");
        String name = input.nextLine();
        hero.removeFromBackpack(name);
        System.out.println("Removed if exsist");
    }

    // MODIFIES: this
    // EFFECTS: add a noble into knownnoble list
    private void addKnownNoble() {

        System.out.print("type Noble name: ");
        String name = input.nextLine();
        if (name.isEmpty()) {
            return;
        }
        System.out.print("type Noble title: ");
        String title = input.nextLine();
        if (title.isEmpty()) {
            return;
        }
        System.out.print("type Noble Prestige: ");
        Integer prestige = input.nextInt();

        hero.addToKnownNobles(new Noble(name, title, prestige));
        System.out.println("Noble added.");
    }

    // EFFECTS: view all ites in KnownNobleslist
    private void viewKnownNobles() {
        List<Noble> nobles = hero.getKnownNobles().getNobles();
        if (nobles.isEmpty()) {
            System.out.println("No nobles yet.");
            return;
        }
        System.out.println("Known Nobles");
        for (int i = 0; i < nobles.size(); i++) {
            Noble n = nobles.get(i);
            String name = n.getName();
            System.out.println("Noble name:" + name);
        }
    }

    // EFFECTS: view stats
    private void viewStats() {
        System.out.println("Stats");
        System.out.println(hero.getStats());
    }

    // EFFECTS: saves the current hero to ./data/save.json
    private void saveGame() {
        try {
            jsonWriter.open();
            jsonWriter.write(hero);
            jsonWriter.close();
            System.out.println("Saved game to " + JSON_STORE);
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file: " + JSON_STORE);
        }
    }

    // MODIFIES: this
    // EFFECTS: loads hero from ./data/save.json and replaces current hero
    private void loadGame() {
        try {
            hero = jsonReader.read(); 
            System.out.println("Loaded game from " + JSON_STORE);
        } catch (IOException e) {
            System.out.println("Unable to read from file: " + JSON_STORE);
        }
    }
}