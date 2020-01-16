package Components;

import Models.Room;

import javax.swing.*;
import java.awt.*;

public class RoomPanel extends JPanel {

    private Room room;
    private boolean playerIsInRoom = false;

    public RoomPanel(Room room) {
        this.room = room;
        this.setPreferredSize(new Dimension(100, 100));
        if (this.room != null) {
            this.add(new JLabel(room.getName()));
            this.setBorder(BorderFactory.createLineBorder(Color.black));
            this.setBackground(Color.green);
        }
    }

    public void togglePlayerIsInRoom() {
        this.playerIsInRoom = !this.playerIsInRoom;
    }

    public Room getRoom() {
        return this.room;
    }

}
