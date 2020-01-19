package com.ProjectZuul.GUI.Components;


import com.ProjectZuul.Models.Room;

import javax.swing.*;
import java.awt.*;

public class RoomPanel extends JPanel {

    private Room room;

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

    public Room getRoom() {
        return this.room;
    }

    public void setActive() {
        this.setBackground(Color.WHITE);
        this.getComponent(0).setForeground(Color.BLACK);
        this.setBorder(BorderFactory.createLineBorder(Color.BLACK));
    }

    public void setNotActive() {
        this.setBackground(Color.BLACK);
        this.getComponent(0).setForeground(Color.WHITE);
        this.setBorder(BorderFactory.createLineBorder(Color.WHITE));
    }
}
