package com.ProjectZuul.GUI.Components;

import javax.swing.*;
import java.awt.*;

public class MyLabel extends JLabel
{
    public MyLabel(String text, Color foreground, Font font, Container parent)
    {
        this.setText(text);
        this.setForeground(foreground);
        this.setFont(font);
        parent.add(this);
    }
}
