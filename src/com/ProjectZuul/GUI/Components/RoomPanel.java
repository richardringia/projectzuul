package com.ProjectZuul.GUI.Components;


import com.ProjectZuul.Models.Room;

import javax.swing.*;
import java.awt.*;

/**
 * The roompanel is used in the maphandler for showing the rooms
 */
public class RoomPanel extends JPanel {

    /**
     * The room for the room panel
     */
    private Room room;

    /**
     * Instantiates a new Room panel.
     * If there is no room then the room panel is no empty and has no text or border
     *
     * @param room      the room for the room panel
     * @param dimension the dimension of the panel
     */
    public RoomPanel(Room room, Dimension dimension) {
        super(new GridLayout());
        this.room = room;
        this.setPreferredSize(dimension);
        if (this.room != null) {
            JLabel label = new JLabel(room.getName(), SwingConstants.CENTER);
            label.setForeground(Color.WHITE);
            this.add(label, BorderLayout.CENTER);
            this.setBorder(BorderFactory.createLineBorder(Color.WHITE));
        }
        this.setBackground(Color.BLACK);
    }

    /**
     * Get the room of the room panel
     *
     * @return the room
     */
    public Room getRoom() {
        return this.room;
    }

    /**
     * Set the room panel to active.
     * This is used when a player enters the room
     */
    public void setActive() {
        this.setBackground(Color.WHITE);
        this.getComponent(0).setForeground(Color.BLACK);
        this.setBorder(BorderFactory.createLineBorder(Color.BLACK));
    }

    /**
     * Set the room panel not active
     * This is used when a player leaves the room
     */
    public void setNotActive() {
        this.setBackground(Color.BLACK);
        this.getComponent(0).setForeground(Color.WHITE);
        this.setBorder(BorderFactory.createLineBorder(Color.WHITE));
    }
}
