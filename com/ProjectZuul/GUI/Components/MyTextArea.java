package com.ProjectZuul.GUI.Components;

import javax.swing.*;
import java.awt.*;

public class MyTextArea extends JTextArea
{
    Font gameFont = new Font("Arial", Font.PLAIN, 15);

    public MyTextArea(String text, Color background, Color foreground, int x, int y, int width, int height, Container parent)
    {
        this.setText(text);
        this.setBackground(background);
        this.setForeground(foreground);
        this.setBounds(x, y, width, height);
        this.setLineWrap(true);
        this.setWrapStyleWord(true);
        this.setFont(gameFont);

        parent.add(this);
    }
}
