package com.ProjectZuul.GUI.Components;

import javax.swing.*;
import java.awt.*;

public class MyButton extends JButton
{
    public MyButton(String text, Color background, Color foreground, Rectangle bounds)
    {
        this.setText(text);
        this.setBackground(background);
        this.setForeground(Color.WHITE);
        this.setBounds(bounds);
        this.setBorder(BorderFactory.createLineBorder(Color.WHITE));
        this.setFocusPainted(false);
        this.setOpaque(true);
    }

    public MyButton(String text, Color background, Color foreground, Rectangle bounds, Container parent)
    {
        this.setText(text);
        this.setBackground(background);
        this.setForeground(Color.WHITE);
        this.setBounds(bounds);
        this.setBorder(BorderFactory.createLineBorder(Color.WHITE));
        this.setFocusPainted(false);
        this.setOpaque(true);
        parent.add(this);
    }
}
