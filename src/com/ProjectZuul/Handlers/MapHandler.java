package com.ProjectZuul.Handlers;

import java.awt.*;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import javax.swing.*;

import com.ProjectZuul.GUI.Components.RoomPanel;
import com.ProjectZuul.Models.*;

public class MapHandler {

    private static final int FPS = 60;

    private List<Room> roomList;
    private JPanel frame;
    private int gridLength = 3;
    private int gridHeight = 5;
    private Room currentRoom;


    public MapHandler(List<Room> roomList) {
        this.roomList = roomList;
        currentRoom = roomList.get(0);
        this.draw();


        Component[] components = frame.getComponents();
        for (int i = 0; i < components.length; i++) {
            System.out.println("Componenet name - " + components[i].getName());
            System.out.println(Arrays.toString(frame.getComponents()));
            if (components[i] instanceof JPanel) {

                Component s[] = ((JPanel) components[i]).getComponents();
                for (int j = 0; j < s.length; j++) {
                    System.out.println("Sub Componenet name - " + s[j].getName());
                }
            }
        }
    }

    public JPanel getMap() {
        return this.frame;
    }

    private void draw() {
        this.frame = new JPanel();
        frame.setBackground(Color.BLACK);
        frame.setBounds(600, 0, 600, 600);
        frame.setLayout(new FlowLayout(FlowLayout.LEFT, 20, 20));

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new GridLayout(gridHeight, gridLength));
        mainPanel.setBackground(Color.BLACK);
        for (int y = 0; y < this.gridHeight; y++) {
            for (int x = 0; x < this.gridLength; x++) {
                RoomPanel panel = new RoomPanel(findRoom(x, y));
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

    private void update() {
//        System.out.println("Update");
    }

    public void updateRoom(Room room) {
        this.currentRoom = room;
    }
}