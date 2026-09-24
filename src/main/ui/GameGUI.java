package ui;

import model.Item;
import model.MainCharacter;
import model.Noble;
import persistence.JsonReader;
import persistence.JsonWriter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import model.Event;
import model.EventLog;

public class GameGUI extends JFrame {

    private static final String JSON_STORE = "./data/save.json";

    private MainCharacter hero;
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;

    private List<Noble> nobles;
    private List<Noble> defeatedNobles;
    private JLabel moneyLabel;
    private JLabel strengthLabel;
    private JLabel defeatedLabel;
    private JPanel cardPanel;
    private ShopPanel shopPanel;
    private BattlePanel battlePanel;
    private BackpackPanel backpackPanel;
    private StatsPanel statsPanel;

    public GameGUI() {
        initModel();
        initPersistence();
        initFrame();
        initStatusBar();
        initCards();
        initBottomButtons();

        updateStatusLabels();
        setVisible(true);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                printEventLog();
            }
        });
    }

    // EFFECTS: initialize hero and nobles
    private void initModel() {
        hero = new MainCharacter(10, 300);
        nobles = new ArrayList<>();
        defeatedNobles = new ArrayList<>();

        // preset 5 nobles from weak to strong
        nobles.add(new Noble("Lord A", "Baron", 5));
        nobles.add(new Noble("Lord B", "Viscount", 15));
        nobles.add(new Noble("Lord C", "Earl", 25));
        nobles.add(new Noble("Lord D", "Marquis", 40));
        nobles.add(new Noble("Lord E", "Duke", 60));
    }

    // EFFECTS: initialize JsonReader/Writer
    private void initPersistence() {
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);
    }

    // EFFECTS: sets up main frame properties
    private void initFrame() {
        setTitle("Knight Adventure");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        setSize(900, 600);
        setLocationRelativeTo(null); // center on screen
    }

    // EFFECTS: create top status bar
    private void initStatusBar() {
        JPanel top = new JPanel(new FlowLayout(FlowLayout.LEFT));
        moneyLabel = new JLabel();
        strengthLabel = new JLabel();
        defeatedLabel = new JLabel();

        top.add(moneyLabel);
        top.add(Box.createHorizontalStrut(20));
        top.add(strengthLabel);
        top.add(Box.createHorizontalStrut(20));
        top.add(defeatedLabel);

        add(top, BorderLayout.NORTH);
    }

    // EFFECTS: create center card panel and all sub-panels
    private void initCards() {
        cardPanel = new JPanel(new CardLayout());

        shopPanel = new ShopPanel(this);
        battlePanel = new BattlePanel(this);
        backpackPanel = new BackpackPanel(this);
        statsPanel = new StatsPanel(this);

        cardPanel.add(shopPanel, "SHOP");
        cardPanel.add(battlePanel, "BATTLE");
        cardPanel.add(backpackPanel, "BACKPACK");
        cardPanel.add(statsPanel, "STATS");

        add(cardPanel, BorderLayout.CENTER);
    }

    @SuppressWarnings("methodlength")
    // EFFECTS: create bottom navigation + save/load buttons
    private void initBottomButtons() {
        JPanel bottom = new JPanel(new FlowLayout());
        JButton shopButton = new JButton("Shop");
        JButton battleButton = new JButton("Battle");
        JButton backpackButton = new JButton("Backpack");
        JButton statsButton = new JButton("Stats");
        JButton saveButton = new JButton("Save");
        JButton loadButton = new JButton("Load");
        shopButton.addActionListener(e -> showCard("SHOP"));
        battleButton.addActionListener(e -> showCard("BATTLE"));
        backpackButton.addActionListener(e -> {
            backpackPanel.refreshAllItems();
            showCard("BACKPACK");
        });
        statsButton.addActionListener(e -> {
            statsPanel.repaint();
            showCard("STATS");
        });
        saveButton.addActionListener(e -> handleSave(e));
        loadButton.addActionListener(e -> handleLoad(e));
        bottom.add(shopButton);
        bottom.add(battleButton);
        bottom.add(backpackButton);
        bottom.add(statsButton);
        bottom.add(saveButton);
        bottom.add(loadButton);
        add(bottom, BorderLayout.SOUTH);
    }

    // EFFECTS: show panel with given name in card layout
    private void showCard(String name) {
        CardLayout cl = (CardLayout) cardPanel.getLayout();
        cl.show(cardPanel, name);
    }

    // EFFECTS: save hero to JSON_STORE, show dialog
    private void handleSave(ActionEvent e) {
        try {
            jsonWriter.open();
            jsonWriter.write(hero);
            jsonWriter.close();
            JOptionPane.showMessageDialog(this,
                    "Game saved to " + JSON_STORE,
                    "Save",
                    JOptionPane.INFORMATION_MESSAGE);
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this,
                    "Unable to write to file: " + JSON_STORE,
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    // MODIFIES: this
    // EFFECTS: load hero from JSON_STORE, refresh UI, show dialog
    private void handleLoad(ActionEvent e) {
        try {
            hero = jsonReader.read();
            defeatedNobles.clear();
            defeatedNobles.addAll(hero.getKnownNobles().getNobles());

            updateStatusLabels();
            backpackPanel.refreshAllItems();
            battlePanel.refreshNobleList();
            statsPanel.repaint();

            JOptionPane.showMessageDialog(this,
                    "Game loaded from " + JSON_STORE,
                    "Load",
                    JOptionPane.INFORMATION_MESSAGE);
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this,
                    "Unable to read from file: " + JSON_STORE,
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    // EFFECTS: compute total strength = base + sum of item damage
    public int calculateTotalStrength() {
        int base = hero.getStrength();
        int bonus = 0;
        for (Item i : hero.getBackpack().getItems()) {
            bonus += i.getDamage();
        }
        return base + bonus;
    }

    // EFFECTS: refresh top status labels
    public void updateStatusLabels() {
        moneyLabel.setText("Gold: " + hero.getMoney());
        strengthLabel.setText("Total Strength: " + calculateTotalStrength());
        defeatedLabel.setText("Defeated nobles: " + defeatedNobles.size());
    }

    public MainCharacter getHero() {
        return hero;
    }

    public List<Noble> getNobles() {
        return nobles;
    }

    public List<Noble> getDefeatedNobles() {
        return defeatedNobles;
    }

    public BattlePanel getBattlePanel() {
        return battlePanel;
    }

    public BackpackPanel getBackpackPanel() {
        return backpackPanel;
    }

    public StatsPanel getStatsPanel() {
        return statsPanel;
    }

    public static void main(String[] args) {
        new GameGUI();
    }

    // EFFECTS: print all events in the EventLog to console when quit
    private void printEventLog() {
        for (Event ev : EventLog.getInstance()) { 
            System.out.println(ev.toString()); 
        }
    }
}