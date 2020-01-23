package com.ProjectZuul.GUI.Components;

import javax.swing.*;
import java.awt.*;

/**
 * MyLabel, extension of JLabel, used to create a constructor to set values to prevent duplicate code for every created label.
 *
 * @author Anne Pier Merkus
 */
public class MyLabel extends JLabel
{
    /**
     * Creates a new label and sets the given values.
     *
     * @param text       The text of the label.
     * @param foreground The color of the text.
     * @param font       The font the label will use.
     * @param parent     The container which will hold this label.
     */
    public MyLabel(String text, Color foreground, Font font, Container parent)
    {
        this.setText(text);
        this.setForeground(foreground);
        this.setFont(font);
        parent.add(this);
    }
}
