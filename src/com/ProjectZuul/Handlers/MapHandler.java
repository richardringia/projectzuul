package com.ProjectZuul.Handlers;

import java.awt.*;
import java.util.List;
import javax.swing.*;

import com.ProjectZuul.GUI.Components.RoomPanel;
import com.ProjectZuul.GUI.Listeners.SetInactiveListener;
import com.ProjectZuul.Models.*;

public class MapHandler implements SetInactiveListener
{

    private static final int FPS = 60;

    private List<Room> roomList;
    private JPanel frame;
    private int gridLength = 3;
    private int gridHeight = 5;
    private Room currentRoom;
    private int height = 300;
    private int width = 300;


    public MapHandler(List<Room> roomList) {
        this.roomList = roomList;
        currentRoom = roomList.get(0);
        this.draw();
    }

    public JPanel getMap() {
        return this.frame;
    }

    private void draw() {
        this.frame = new JPanel();
        frame.setBackground(Color.BLACK);
        frame.setBounds(850, 0, this.width + 10, this.height + 10);
        frame.setLayout(new FlowLayout(FlowLayout.LEFT));

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new GridLayout(gridHeight, gridLength));
        mainPanel.setBackground(Color.BLACK);
        for (int y = 0; y < this.gridHeight; y++) {
            for (int x = 0; x < this.gridLength; x++) {
                RoomPanel panel = new RoomPanel(findRoom(x, y), new Dimension(this.width / this.gridLength, this.height / this.gridHeight));
                mainPanel.add(panel);
            }
        }
        frame.add(mainPanel);
        frame.setVisible(true);
    }

    private Room findRoom(int x, int y) {
        int startX = 1;
        int startY = 4;

        int xCalc = -(startX - x);
        int yCalc = -(startY - y);

        Room currentRoom = this.roomList.get(0);

        for (int b = yCalc; (yCalc < 0) ? b < 0 : b > 0; ) {
            // zuiden bij een + getal
            // noorden bij een - getal
            currentRoom = b < 0 ? currentRoom.getExit("north") : currentRoom.getExit("south");
            if (yCalc < 0)
                b++;
            else
                b--;
        }

        for (int a = xCalc; (xCalc < 0) ? a < 0 : a > 0; ) {
            // oost bij een + getal
            // west bij een - getal
            currentRoom = a < 0 ? currentRoom.getExit("west") : currentRoom.getExit("east");

            if (xCalc < 0)
                a++;
            else
                a--;
        }

        return currentRoom;

    }

    public void updateRoom(Room _room) {
        this.currentRoom = _room;
        Component[] components = frame.getComponents();
        for (Component component : components) {
            if (component instanceof JPanel) {
                Component[] innerComponents = ((JPanel) component).getComponents();
                for (Object object : innerComponents) {
                    if (object instanceof RoomPanel) {
                        RoomPanel roomPanel = ((RoomPanel) object);
                        Room room = roomPanel.getRoom();
                        if (room != null) {
                            if (room == _room) {
                                roomPanel.setActive();
                            } else {
                                roomPanel.setNotActive();
                            }
                        }
                    }
                }
            }
        }
    }

    @Override
    public void setMenuVisibility(boolean visibility) {

    }
}