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

    public void togglePlayerIsInRoom() {
        this.playerIsInRoom = !this.playerIsInRoom;
    }

    public Room getRoom() {
        return this.room;
    }

}
