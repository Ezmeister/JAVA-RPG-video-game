package ui;

import model.Item;

import javax.swing.*;
import java.awt.*;

// Represents the backpack screen for viewing items
public class BackpackPanel extends JPanel {

    private final GameGUI gameGUI;

    private DefaultListModel<String> listModel;
    private JList<String> itemList;
    private JButton allButton;
    private JButton weaponsButton;
    private JButton armorButton;

    // EFFECTS: constructs backpack panel
    public BackpackPanel(GameGUI gui) {
        this.gameGUI = gui;
        setLayout(new BorderLayout());
        initComponents();
        refreshAllItems();
    }

    private void initComponents() {
        listModel = new DefaultListModel<>();
        itemList = new JList<>(listModel);

        allButton = new JButton("All items");
        weaponsButton = new JButton("Weapons");
        armorButton = new JButton("Armors");

        allButton.addActionListener(e -> refreshAllItems());
        weaponsButton.addActionListener(e -> refreshFiltered("weapon"));
        armorButton.addActionListener(e -> refreshFiltered("armor"));

        JPanel top = new JPanel(new FlowLayout());
        top.add(allButton);
        top.add(weaponsButton);
        top.add(armorButton);

        add(top, BorderLayout.NORTH);
        add(new JScrollPane(itemList), BorderLayout.CENTER);
    }

    // MODIFIES: this
    // EFFECTS: show all items in backpack
    public void refreshAllItems() {
        listModel.clear();
        for (Item i : gameGUI.getHero().getBackpack().viewItems()) {
            listModel.addElement(describeItem(i));
        }
    }

    // MODIFIES: this
    // EFFECTS: show only items of given type
    private void refreshFiltered(String type) {
        listModel.clear();
        for (Item i : gameGUI.getHero().getBackpack().getItemsByType(type)) {
            listModel.addElement(describeItem(i));
        }
    }

    private String describeItem(Item i) {
        return i.getName()
                + " (" + i.getType() + ")"
                + " cost=" + i.getCost()
                + " bonus=" + i.getDamage();
    }
}