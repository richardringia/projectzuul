package com.ProjectZuul.GUI.Components;

import javax.swing.*;
import java.awt.*;

/**
 * MyPanel extension of JTextArea, used to create a constructor to set values to prevent duplicate code for every created text area.
 *
 * @author Anne Pier Merkus
 */
public class MyTextArea extends JTextArea
{
    /**
     * The font used for every text area.
     */
    Font gameFont = new Font("Arial", Font.PLAIN, 15);

    /**
     * Creates a new text area and sets the given values.
     *
     * @param text       The text of the text area.
     * @param background The color of the background this text area will have.
     * @param foreground The color of the text.
     * @param x          The x location of the text area.
     * @param y          The y location of the text area.
     * @param width      The width of the text area.
     * @param height     The height of the text area.
     * @param parent     The container that will hold this text area.
     */
    public MyTextArea(String text, Color background, Color foreground, int x, int y, int width, int height, Container parent)
    {
        this.setText(text);
        this.setBackground(background);
        this.setForeground(foreground);
        this.setBounds(x, y, width, height);
        this.setLineWrap(true);
        this.setWrapStyleWord(true);
        this.setFont(gameFont);
        this.setEditable(false);
        this.setBorder(BorderFactory.createLineBorder(Color.BLACK));

        parent.add(this);
    }
}
