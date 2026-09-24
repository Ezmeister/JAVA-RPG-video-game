package ui;

import model.Noble;

import javax.swing.*;
import java.awt.*;
import java.util.List;


public class BattlePanel extends JPanel {

    private static final int HERO_MAX_HP = 200;

    private final GameGUI gameGUI;

    private DefaultListModel<String> nobleListModel;
    private JList<String> nobleList;

    private JLabel heroHpLabel;
    private JLabel nobleHpLabel;
    private JLabel lossLabel;
    private JTextArea logArea;
    private JTextArea defeatedArea;

    private JButton startButton;
    private JButton attackButton;
    private JButton defendButton;


    private Noble currentNoble;
    private int heroHp;
    private int nobleHp;
    private boolean defendAvailable;
    private boolean battleActive;


    private int lossCount;

    // EFFECTS: constructs battle panel
    public BattlePanel(GameGUI gui) {
        this.gameGUI = gui;
        setLayout(new BorderLayout());
        initComponents();
        refreshNobleList();
        updateDefeatedText();
        updateLossLabel();
        setButtonsForNoBattle();
    }

    @SuppressWarnings("methodlength")
    private void initComponents() {

        nobleListModel = new DefaultListModel<>();
        nobleList = new JList<>(nobleListModel);

        startButton = new JButton("Start Battle");
        startButton.addActionListener(e -> startBattle());

        attackButton = new JButton("Attack");
        attackButton.addActionListener(e -> doAttack());

        defendButton = new JButton("Defend (once)");
        defendButton.addActionListener(e -> doDefend());

        JPanel leftButtons = new JPanel(new GridLayout(3, 1, 5, 5));
        leftButtons.add(startButton);
        leftButtons.add(attackButton);
        leftButtons.add(defendButton);

        JPanel left = new JPanel(new BorderLayout());
        left.add(new JLabel("Available nobles:"), BorderLayout.NORTH);
        left.add(new JScrollPane(nobleList), BorderLayout.CENTER);
        left.add(leftButtons, BorderLayout.SOUTH);

       
        JPanel centerTop = new JPanel(new GridLayout(3, 1));
        heroHpLabel = new JLabel("Hero HP: -");
        nobleHpLabel = new JLabel("Noble HP: -");
        lossLabel = new JLabel("Losses: 0 / 3");
        centerTop.add(heroHpLabel);
        centerTop.add(nobleHpLabel);
        centerTop.add(lossLabel);

        logArea = new JTextArea(10, 25);
        logArea.setEditable(false);

        JPanel center = new JPanel(new BorderLayout());
        center.add(centerTop, BorderLayout.NORTH);
        center.add(new JScrollPane(logArea), BorderLayout.CENTER);

        
        defeatedArea = new JTextArea(10, 20);
        defeatedArea.setEditable(false);

        JPanel right = new JPanel(new BorderLayout());
        right.add(new JLabel("Defeated nobles:"), BorderLayout.NORTH);
        right.add(new JScrollPane(defeatedArea), BorderLayout.CENTER);

        add(left, BorderLayout.WEST);
        add(center, BorderLayout.CENTER);
        add(right, BorderLayout.EAST);
    }

    // MODIFIES: this
    // EFFECTS: repopulate list from nobles in GameGUI
    public void refreshNobleList() {
        nobleListModel.clear();
        List<Noble> nobles = gameGUI.getNobles();
        for (Noble n : nobles) {
            nobleListModel.addElement(
                    n.getTitle() + " " + n.getName()
                            + " (prestige=" + n.getPrestige() + ")"
            );
        }
    }

    // MODIFIES: this
    // EFFECTS: update text of defeated nobles
    private void updateDefeatedText() {
        StringBuilder sb = new StringBuilder();
        for (Noble n : gameGUI.getDefeatedNobles()) {
            sb.append(n.getTitle()).append(" ").append(n.getName()).append("\n");
        }
        defeatedArea.setText(sb.toString());
    }

    // MODIFIES: this
    // EFFECTS: start a new battle with selected noble
    private void startBattle() {
      
        if (lossCount >= 3) {
            return;
        }

        int idx = nobleList.getSelectedIndex();
        List<Noble> nobles = gameGUI.getNobles();

        if (idx < 0 || idx >= nobles.size()) {
            return;
        }

        currentNoble = nobles.get(idx);
        heroHp = HERO_MAX_HP;
        nobleHp = currentNoble.getPrestige() * 10;
        defendAvailable = true;
        battleActive = true;

        logArea.setText("");
        appendLog("Battle started against "
                + currentNoble.getTitle() + " " + currentNoble.getName()
                + " (prestige " + currentNoble.getPrestige() + ")");
        appendLog("Hero attack = " + gameGUI.calculateTotalStrength()
                + ", Noble attack = " + currentNoble.getPrestige());
        updateHpLabels();
        setButtonsForBattle();
    }

    // MODIFIES: this
    // EFFECTS: hero attacks, then noble attacks (if still alive)
    private void doAttack() {
        if (!battleActive || currentNoble == null) {
            return;
        }

        int heroAttack = gameGUI.calculateTotalStrength();
        int nobleAttack = currentNoble.getPrestige();

        
        nobleHp -= heroAttack;
        appendLog("Hero attacks for " + heroAttack + " damage.");
        if (nobleHp <= 0) {
            nobleHp = 0;
            updateHpLabels();
            winBattle();
            return;
        }

        
        heroHp -= nobleAttack;
        appendLog(currentNoble.getName() + " attacks for " + nobleAttack + " damage.");
        if (heroHp <= 0) {
            heroHp = 0;
            updateHpLabels();
            loseBattle();
            return;
        }

        updateHpLabels();
    }

    // MODIFIES: this
    // EFFECTS: hero restores HP to full once per battle
    private void doDefend() {
        if (!battleActive || currentNoble == null || !defendAvailable) {
            return;
        }

        defendAvailable = false;
        heroHp = HERO_MAX_HP;
        appendLog("Hero uses defend and restores HP to full (" + HERO_MAX_HP + ").");
        updateHpLabels();
        defendButton.setEnabled(false);
    }

    // MODIFIES: this, hero, nobles, defeated list
    // EFFECTS: handle victory, give reward gold, remove noble from list
    private void winBattle() {
        battleActive = false;
        appendLog("You WIN!");

        int prestige = currentNoble.getPrestige();
        int reward = prestige * 2;

        
        gameGUI.getHero().spendMoney(-reward);
        appendLog("You gain " + reward + " gold.");

       
        gameGUI.getDefeatedNobles().add(currentNoble);
        gameGUI.getNobles().remove(currentNoble);

        refreshNobleList();
        updateDefeatedText();
        gameGUI.updateStatusLabels();
        gameGUI.getStatsPanel().repaint();
        setButtonsForNoBattle();
        
    }

    // MODIFIES: this
    // EFFECTS: handle defeat; after 3 losses, game over
    private void loseBattle() {
        battleActive = false;
        appendLog("You lost the battle...");
        lossCount++;
        updateLossLabel();

        if (lossCount >= 3) {
            appendLog("You have lost 3 times. Game over!");
            setButtonsForGameOver();
            JOptionPane.showMessageDialog(this,
                    "You have lost 3 times. Game over!",
                    "Game Over",
                    JOptionPane.INFORMATION_MESSAGE);
        } else {
            setButtonsForNoBattle();
        }

        gameGUI.updateStatusLabels();
        gameGUI.getStatsPanel().repaint();
    }

    private void updateHpLabels() {
        heroHpLabel.setText("Hero HP: " + heroHp + "/" + HERO_MAX_HP);
        if (currentNoble != null) {
            nobleHpLabel.setText("Noble HP: " + nobleHp + " / "
                    + (currentNoble.getPrestige() * 10));
        } else {
            nobleHpLabel.setText("Noble HP: -");
        }
    }

    private void updateLossLabel() {
        lossLabel.setText("Losses: " + lossCount + " / 3");
    }

    private void appendLog(String msg) {
        logArea.append(msg + "\n");
    }

    private void setButtonsForBattle() {
        startButton.setEnabled(true);
        attackButton.setEnabled(true);
        defendButton.setEnabled(defendAvailable);
    }

    private void setButtonsForNoBattle() {
        attackButton.setEnabled(false);
        defendButton.setEnabled(false);
        
        if (lossCount < 3) {
            startButton.setEnabled(true);
        }
    }

    // MODIFIES: this
    // EFFECTS: disable all battle buttons after game over
    private void setButtonsForGameOver() {
        startButton.setEnabled(false);
        attackButton.setEnabled(false);
        defendButton.setEnabled(false);
    }
}