package ui;

import model.Item;
import model.MainCharacter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;


public class ShopPanel extends JPanel {

    private final GameGUI gameGUI;

    private List<Item> shopItems;
    private Item specialWeapon;
    private boolean specialAvailable;
    private boolean specialPurchased;

    private JComboBox<String> itemCombo;
    private JButton buyButton;
    private JTextArea textArea;

    public ShopPanel(GameGUI gui) {
        this.gameGUI = gui;
        this.shopItems = new ArrayList<>();
        setLayout(new BorderLayout());

        initShopItems();
        initComponents();
        syncWithHero();
    }

    // Effects: Initialize default shop items
    private void initShopItems() {
        shopItems.clear();

        shopItems.add(new Item("Iron Sword", "weapon", 50, 5));
        shopItems.add(new Item("Steel Sword", "weapon", 80, 8));
        shopItems.add(new Item("Knight Lance", "weapon", 100, 10));
        shopItems.add(new Item("War Axe", "weapon", 120, 12));
        shopItems.add(new Item("Royal Blade", "weapon", 150, 15));

        shopItems.add(new Item("Leather Armor", "armor", 40, 3));
        shopItems.add(new Item("Chainmail", "armor", 70, 6));
        shopItems.add(new Item("Knight Armor", "armor", 90, 9));
        shopItems.add(new Item("Tower Shield", "armor", 110, 11));
        shopItems.add(new Item("Royal Shield", "armor", 140, 14));

        shopItems.add(new Item("Healing Potion", "potion", 30, 0));

        specialWeapon = new Item("Holy Relic", "weapon", 200, 10);
        specialAvailable = false;
        specialPurchased = false;
    }

    private void initComponents() {
        textArea = new JTextArea();
        textArea.setEditable(false);

        itemCombo = new JComboBox<>();
        buyButton = new JButton("Buy");

        buyButton.addActionListener(this::buySelected);

        JPanel bottom = new JPanel(new FlowLayout());
        bottom.add(new JLabel("Choose item:"));
        bottom.add(itemCombo);
        bottom.add(buyButton);

        add(new JScrollPane(textArea), BorderLayout.CENTER);
        add(bottom, BorderLayout.SOUTH);
    }

    // Effects: Rebuild shop after load
    public void syncWithHero() {
        MainCharacter hero = gameGUI.getHero();

        shopItems.clear();
        specialAvailable = false;
        specialPurchased = false;
        initShopItems();

        for (Item owned : hero.getBackpack().getItems()) {
            if (owned.getName().equalsIgnoreCase(specialWeapon.getName())) {
                specialPurchased = true;
                break;
            }
        }

        for (Item owned : hero.getBackpack().getItems()) {
            shopItems.removeIf(s -> s.getName().equalsIgnoreCase(owned.getName()));
        }

        itemCombo.removeAllItems();
        for (Item it : shopItems) {
            itemCombo.addItem(it.getName());
        }

        if (shopItems.isEmpty() && !specialPurchased) {
            specialAvailable = true;
            itemCombo.addItem(specialWeapon.getName());
        }

        refreshText();
    }

    // Effects:Show shop list in text area
    private void refreshText() {
        textArea.setText("");

        textArea.append("=== Shop Items ===\n");
        for (Item it : shopItems) {
            textArea.append(describe(it) + "\n");
        }

        if (specialAvailable && !specialPurchased) {
            textArea.append("\n--- Special Weapon Unlocked ---\n");
            textArea.append(describe(specialWeapon) + "\n");
        }
    }

    private String describe(Item it) {
        return it.getName() + "  [type=" + it.getType()
                + ", cost=" + it.getCost()
                + ", bonus=" + it.getDamage() + "]";
    }

    // Effects: Buy item
    @SuppressWarnings("methodlength")
    private void buySelected(ActionEvent e) {
        String name = (String) itemCombo.getSelectedItem();
        if (name == null) {
            return;
        }

        Item item = findItem(name);

        if (item == null && specialAvailable && !specialPurchased
                && specialWeapon.getName().equalsIgnoreCase(name)) {
            item = specialWeapon;
        }
        if (item == null) {
            return;
        }

        MainCharacter hero = gameGUI.getHero();

        if (hero.getMoney() < item.getCost()) {
            JOptionPane.showMessageDialog(this, "Not enough gold!",
                    "Shop", JOptionPane.WARNING_MESSAGE);
            return;
        }

        hero.spendMoney(item.getCost());
        hero.addToBackpack(new Item(item.getName(), item.getType(),
                item.getCost(), item.getDamage()));

        itemCombo.removeItem(name);
        shopItems.remove(item);
        if (shopItems.isEmpty() && !specialAvailable && !specialPurchased) {
            specialAvailable = true;
            itemCombo.addItem(specialWeapon.getName());
        }
        if (item == specialWeapon) {
            specialPurchased = true;
        }

        refreshText();
        gameGUI.updateStatusLabels();
        gameGUI.getBackpackPanel().refreshAllItems();
        gameGUI.getStatsPanel().repaint();
    }

    private Item findItem(String name) {
        for (Item it : shopItems) {
            if (it.getName().equalsIgnoreCase(name)) {
                return it;
            }
        }
        return null;
    }
}