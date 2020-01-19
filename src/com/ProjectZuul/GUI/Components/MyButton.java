package com.ProjectZuul.GUI.Components;

import javax.swing.*;
import java.awt.*;

public class MyButton extends JButton {

    public MyButton(String text, Color background, Color foreground) {
        this.setText(text);
        this.setBackground(background);
        this.setForeground(Color.WHITE);
        this.setBorder(BorderFactory.createLineBorder(Color.WHITE));
        this.setFocusPainted(false);
        this.setOpaque(true);
    }

    public MyButton(String text, Color background, Color foreground, Dimension dimension) {
        this(text, background, foreground);
        this.setSize(dimension);
    }

    public MyButton(String text, Color background, Color foreground, Rectangle bounds) {
        this(text, background, foreground);
        this.setBounds(bounds);
    }

    public MyButton(String text, Color background, Color foreground, Rectangle bounds, Container parent) {
        this(text, background, foreground, bounds);
        parent.add(this);
    }
}
