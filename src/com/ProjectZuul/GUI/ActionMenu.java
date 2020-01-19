package com.ProjectZuul.GUI;

import javax.swing.*;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class ActionMenu extends JPanel {
    private int height = 200;
    private int width = 150;

    private JPanel actionPanel;

    public ActionMenu() {
        this.setBounds(600, 20, 150, 200);
        this.setBackground(Color.BLACK);
        this.setLayout(null);
        this.setBorder(BorderFactory.createLineBorder(Color.WHITE));
        this.createTitleLabel();
        this.createActionMenuPanel();
    }


    private void createTitleLabel() {
        JLabel jLabel = new JLabel("Action menu");
        jLabel.setBounds(0, 0, 150, 30);
        jLabel.setForeground(Color.WHITE);
        jLabel.setHorizontalAlignment(SwingConstants.CENTER);
        jLabel.setVerticalAlignment(SwingConstants.CENTER);
        jLabel.setBorder(BorderFactory.createLineBorder(Color.WHITE));
        super.add(jLabel);
    }

    private void createActionMenuPanel() {
        this.actionPanel = new JPanel();
        this.actionPanel.setBounds(0, 30, this.width, this.height - 30);
        this.actionPanel.setBorder(new CompoundBorder(BorderFactory.createLineBorder(Color.WHITE),new EmptyBorder(10, 5, 10, 5)));
        this.actionPanel.setBackground(Color.BLACK);
        this.actionPanel.setLayout(new GridLayout(3, 1, 10, 10));
        super.add(this.actionPanel);
    }

    public Component add(Component component) {
        return this.actionPanel.add(component);
    }

    public void reset() {
        this.actionPanel.removeAll();
        this.actionPanel.updateUI();
    }
}
