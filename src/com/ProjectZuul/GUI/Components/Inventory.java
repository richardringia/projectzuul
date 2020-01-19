package com.ProjectZuul.GUI.Components;

import com.ProjectZuul.GUI.GUI;
import com.ProjectZuul.Models.Item;
import com.ProjectZuul.Models.Player;

import javax.swing.*;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.concurrent.Flow;

public class Inventory extends JPanel {

    private int positionX = 600;
    private int positionY = 400;
    private int width = 560;
    private int height = 150;

    private JPanel innerPanel;
    private int gridRows = 3;
    private int gridCols = 5;

    private Player player;
    private JLabel jLabelTotalWeight;

    public Inventory(Player player) {
        this.player = player;
        this.setBounds(this.positionX, this.positionY, this.width, this.height);
        this.init();
    }

    public Inventory(Player player, Rectangle rectangle) {
        this.player = player;
        this.positionX = rectangle.x;
        this.positionY = rectangle.y;
        this.width = rectangle.width;
        this.height = rectangle.height;
        this.setBounds(rectangle);
        this.init();
    }

    private void init() {
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.setBackground(Color.BLACK);
        this.innerPanel = new JPanel(new GridLayout(this.gridRows, this.gridCols));
        this.innerPanel.setBackground(Color.BLACK);
        this.innerPanel.setPreferredSize(new Dimension(this.width, this.height - 25));
        this.innerPanel.setBorder(new CompoundBorder(BorderFactory.createLineBorder(Color.WHITE), new EmptyBorder(10, 10, 10, 10)));
        this.innerPanel.setLocation(50, 50);
        this.createInfoLabel();
        this.add(this.innerPanel);
    }

    private void createInfoLabel() {
        JPanel labelFrame = new JPanel();
        labelFrame.setBackground(Color.BLACK);
        labelFrame.setPreferredSize(new Dimension(this.width, 25));
        JLabel label = new JLabel("Inventory");
        label.setLocation(0, 0);
        label.setForeground(Color.WHITE);
        labelFrame.add(label);
        this.jLabelTotalWeight = new JLabel(this.getTotalWeightText());
        jLabelTotalWeight.setForeground(Color.GRAY);
        labelFrame.add(jLabelTotalWeight);
        this.add(labelFrame);
    }

    private String getTotalWeightText() {
        return this.player.getTotalWeight() + " of " + this.player.getMaxWeight() + " Kg is used";
    }

    public void updateTotalWeight() {
        this.jLabelTotalWeight.setText(this.getTotalWeightText());
    }

    public void addItem(Item item) {
        JLabel label = new JLabel(item.getName());
        label.setForeground(Color.WHITE);
        this.updateTotalWeight();
        this.innerPanel.add(label);
        this.updateUI();
    }

}
