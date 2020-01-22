package com.ProjectZuul.GUI.Components;

import javax.swing.*;
import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.awt.event.MouseEvent;

public class MyButton extends JButton {

    public MyButton(String text, Color background, Color foreground) {
        this.setText(text);
        this.setBackground(background);
        this.setForeground(Color.WHITE);
        this.setBorder(BorderFactory.createLineBorder(Color.WHITE));
        this.setFocusPainted(false);
        this.setOpaque(true);
        addPropertyChangeListener(e -> {
            try {
                if ((boolean) e.getNewValue()) {
                    setForeground(Color.WHITE);
                } else {
                    setForeground(Color.GRAY);
                }
            }
            catch (Exception ex)
            {
            }
        });
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

    public void setEnabled(boolean b, String message) {
        this.setEnabled(b);
        if (!this.isEnabled()) {
            ToolTipManager.sharedInstance().setInitialDelay(0);
            this.setToolTipText(message);
        }
        else
        {
            this.setToolTipText(null);
        }
    }
}
