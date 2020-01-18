package Models;

import java.util.List;
import java.util.Random;

/**
 * This is the main Item class.
 * An item can be pickup and has a weight.
 */
public class Item {
    private String name; // The name of the item
    private boolean canPickup; // This bool is used for checking if the item can be picked up
    private int weight; // The weight of the item

    /**
     * @param name
     *
     * With this constructor an item that not can be pickup can be created.
     */
    public Item(String name) {
        this.name = name;
        this.weight = 0;
        this.canPickup = false;
    }

    /**
     * @param name
     * @param weight
     *
     * With this constructor an item with canpickup can be created.
     */
    public Item(String name, int weight) {
        this.name = name;
        this.weight = weight;
        this.canPickup = true;
    }

    /**
     * @param availebleRooms
     *
     * Put an item in a random room
     */
    public void setItemToRandomRoom(List<Room> availebleRooms) {
        // Get a random room from the given rooms.
        this.setItemToRoom(availebleRooms.get(new Random().nextInt(availebleRooms.size())));
    }

    /**
     * @param room
     *
     * Put an item in a room
     */
    public void setItemToRoom(Room room) {
        room.addItem(this);
    }

    /**
     * @return int
     *
     * Get the item his weight
     */
    public int getWeight() {
        return this.weight;
    }

    /**
     * @return String
     *
     * Get the item his name
     */
    public String getName() {
        return this.name;
    }

    /**
     * @return boolean
     *
     * Check if the item can be picked up
     */
    public boolean isCanPickup() {
        return this.canPickup;
    }
}