package com.ProjectZuul.GUI.Components;

import javax.swing.*;
import java.awt.*;

/**
 * MyPanel extension of JPanel, used to create a constructor to set values to prevent duplicate code for every created panel.
 *
 * @author Anne Pier Merkus
 */
public class MyPanel extends JPanel
{
    /**
     * Creates a new panel and sets the given values.
     *
     * @param background The color of the background this panel will have.
     * @param x          The x location of the panel.
     * @param y          The y location of the panel.
     * @param width      The width of the panel.
     * @param height     The height of the panel.
     * @param parent     The container that will hold this panel.
     */
    public MyPanel(Color background, int x, int y, int width, int height, Container parent)
    {
        this.setBackground(background);
        this.setBounds(x, y, width, height);
        parent.add(this);
    }
}
