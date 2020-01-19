package com.ProjectZuul.GUI.Components;

import com.ProjectZuul.Models.Item;

import javax.swing.*;
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

    public Inventory() {
        this.setBounds(this.positionX, this.positionY, this.width, this.height);
        this.init();
    }

    public Inventory(Rectangle rectangle) {
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
        this.innerPanel.setBorder(BorderFactory.createLineBorder(Color.WHITE));
        this.createInfoLabel();
        this.add(this.innerPanel);
    }

    private void createInfoLabel() {
        JLabel label = new JLabel("Inventory");
        label.setPreferredSize(new Dimension(this.width, 25));
        label.setForeground(Color.WHITE);
        this.add(label);
    }

    public void addItem(Item item) {
        System.out.println(item.getName());
        JLabel label = new JLabel(item.getName());
        label.setForeground(Color.WHITE);
        this.innerPanel.add(label);
    }
}
