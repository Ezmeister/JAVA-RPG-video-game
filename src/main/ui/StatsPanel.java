package ui;

import model.Item;

import javax.swing.*;
import java.awt.*;

// visual component
public class StatsPanel extends JPanel {

    private final GameGUI gameGUI;

    // EFFECTS: constructs stats panel
    public StatsPanel(GameGUI gui) {
        this.gameGUI = gui;
    }

    // EFFECTS: draws simple bar chart of stats
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        int baseStrength = gameGUI.getHero().getStrength();
        int bonusStrength = 0;
        for (Item i : gameGUI.getHero().getBackpack().getItems()) {
            bonusStrength += i.getDamage();
        }
        int defeatedCount = gameGUI.getDefeatedNobles().size();

        int max = Math.max(baseStrength, Math.max(bonusStrength, defeatedCount));
        if (max == 0) {
            max = 1;
        }

        int width = getWidth();
        int height = getHeight();
        int barWidth = width / 6;
        int x1 = width / 6;
        int x2 = width / 6 * 3;
        int x3 = width / 6 * 5;
        int bottom = height - 60;
        int maxBarHeight = bottom - 60;

        drawBar(g, x1, bottom, barWidth, baseStrength, maxBarHeight, max, "Base\nStrength");
        drawBar(g, x2, bottom, barWidth, bonusStrength, maxBarHeight, max, "Items\nBonus");
        drawBar(g, x3, bottom, barWidth, defeatedCount, maxBarHeight, max, "Defeated\nNobles");
    }

    private void drawBar(Graphics g, int centerX, int bottom, int barWidth,
                         int value, int maxBarHeight, int maxValue, String label) {
        int barHeight = (int) ((double) value / maxValue * maxBarHeight);
        int x = centerX - barWidth / 2;
        int y = bottom - barHeight;

        g.fillRect(x, y, barWidth, barHeight);
        g.drawString(String.valueOf(value), centerX - 5, y - 5);

        String[] lines = label.split("\n");
        int textY = bottom + 15;
        for (String line : lines) {
            g.drawString(line, centerX - 35, textY);
            textY += 15;
        }
    }
}