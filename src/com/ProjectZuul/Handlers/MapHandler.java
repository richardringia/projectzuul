package com.ProjectZuul.Handlers;

import java.awt.*;
import java.util.List;
import javax.swing.*;

import com.ProjectZuul.GUI.Components.RoomPanel;
import com.ProjectZuul.GUI.Listeners.SetInactiveListener;
import com.ProjectZuul.Models.*;

/**
 * Handler to create the map used in this application.
 *
 * @see RoomPanel
 * @author Richard Ringia
 */
public class MapHandler implements SetInactiveListener
{
    /**
     * A list of all rooms in the game.
     */
    private List<Room> roomList;

    /**
     * The Component that will hold our map.
     */
    private JPanel frame;

    /**
     * The amount of columns in the GridLayout.
     */
    private int gridLength = 3;

    /**
     * The amount of rows in the GridLayout.
     */
    private int gridHeight = 5;

    /**
     * The height of the map.
     */
    private int height = 300;

    /**
     * The width of the map.
     */
    private int width = 300;


    /**
     * Sets roomList variable to the given value, calls draw to create the map and sets the map to invisible.
     *
     * @param roomList A list of all rooms in the game.
     */
    public MapHandler(List<Room> roomList) {
        this.roomList = roomList;
        this.draw();
        this.setMenuVisibility(false);
    }

    /**
     * Gets the Component that holds our map.
     *
     * @return the Component that holds our map.
     */
    public JPanel getMap() {
        return this.frame;
    }

    /**
     * Creates the map by adding a RoomPanel instance in each position of the grid.
     *
     * @see RoomPanel
     */
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

    /**
     * Creates a room if there is a room in the given location of the grid, if not, return null.
     *
     * @param x The x location of the grid.
     * @param y The Y location of the grid.
     * @return The room that is positioned in the current x and y position of the grid.
     */
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

    /**
     * Update the current room on the map by giving it a color.
     * This way the player knows where he is by looking at the map.
     *
     * @param _room The room to which the player moved.
     */
    public void updateRoom(Room _room) {
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

    /**
     *  Sets map visibility.
     *
     * @param visibility Whether the menu should be set visible or invisible.
     */
    @Override
    public void setMenuVisibility(boolean visibility) {
        frame.setVisible(visibility);
    }
}