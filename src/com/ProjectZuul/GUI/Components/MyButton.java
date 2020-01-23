package com.ProjectZuul.GUI.Components;

import javax.swing.*;
import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.awt.event.MouseEvent;

/**
 * MyButton, extension of JButton, used to create a constructor with default values to prevent duplicate code for every created button.
 *
 * @author Anne Pier Merkus and Richard Ringia
 */
public class MyButton extends JButton {

    /**
     * Creates a new button and sets the given values
     * Adds propertyChangeListener to change the color of text when the button gets disabled.
     *
     * @param text       The text of the button.
     * @param background The color of the background this button will have.
     * @param foreground The color of the text.
     */
    public MyButton(String text, Color background, Color foreground) {
        this.setText(text);
        this.setBackground(background);
        this.setForeground(Color.WHITE);
        this.setBorder(BorderFactory.createLineBorder(Color.WHITE));
        this.setFocusPainted(false);
        this.setOpaque(true);
        addPropertyChangeListener(evt ->  {
            if ((boolean) evt.getNewValue()) {
                setForeground(Color.WHITE);
            } else {
                setForeground(Color.GRAY);
            }
        });
    }

    /**
     * Calls another constructor to set all default values and sets the size.
     *
     * @param text       The text of the button.
     * @param background The color of the background this button will have.
     * @param foreground The color of the text.
     * @param dimension  The size given to this button.
     */
    public MyButton(String text, Color background, Color foreground, Dimension dimension) {
        this(text, background, foreground);
        this.setSize(dimension);
    }

    /**
     * Calls another constructor to set all default values and sets the bounds.
     *
     * @param text       The text of the button.
     * @param background The color of the background this button will have.
     * @param foreground The color of the text.
     * @param bounds     The size and location of this button.
     */
    public MyButton(String text, Color background, Color foreground, Rectangle bounds) {
        this(text, background, foreground);
        this.setBounds(bounds);
    }

    /**
     * Calls another constructor to set all default values and gives a container which will be this buttons parent.
     *
     * @param text       The text of the button.
     * @param background The color of the background this button will have.
     * @param foreground The color of the text.
     * @param bounds     The size and location of this button.
     * @param parent     The container which will hold this button.
     */
    public MyButton(String text, Color background, Color foreground, Rectangle bounds, Container parent) {
        this(text, background, foreground, bounds);
        parent.add(this);
    }

    /**
     * Disable or Enable the button, if the button is enabled the tooltip will be removed, if the button is disabled a tooltip will be added.
     *
     * @param b       Whether the button will be clickable or not.
     * @param message The message shown in the tooltip when hovered over the button.
     */
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
