package com.ProjectZuul.GUI.Components;


import com.ProjectZuul.Models.Room;

import javax.swing.*;
import java.awt.*;

public class RoomPanel extends JPanel {

    private Room room;
    private boolean playerIsInRoom = false;

    public RoomPanel(Room room) {
        this.room = room;
        this.setPreferredSize(new Dimension(100, 100));
        if (this.room != null) {
            JLabel label = new JLabel(room.getName());
            label.setForeground(Color.WHITE);
            this.add(label);
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
