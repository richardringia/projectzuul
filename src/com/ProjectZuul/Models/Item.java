package com.ProjectZuul.Models;


import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * This is the main Item class.
 * An item can be picked up and has a weight.
 *
 * @see com.ProjectZuul.Handlers.ItemHandler
 * @author Richard Ringia
 */
public class Item {
    /**
     * The name of the item.
     */
    private String name;

    /**
     * The weight of the item.
     */
    private int weight;

    /**
     * Whether the item can be picked up or not.
     */
    private boolean canPickup;

    /**
     * Whether the item can be dropped or not.
     */
    private boolean canDrop;

    /**
     * @param name
     *
     * With this constructor an item that not can be pickup can be created.
     */
    public Item(String name) {
        this.name = name;
        this.weight = 0;
        this.canPickup = false;
        this.canDrop = false;
    }

    /**
     * Create items and set variables to the given values.
     *
     * @param name   Name of the created item.
     * @param weight Weight of the created item.
     */
    public Item(String name, int weight) {
        this.name = name;
        this.weight = weight;
        this.canPickup = true;
        this.canDrop = true;
    }

    /**
     * Calls another constructor of item and makes it impossible to drop the item.
     * This is to prevent people from dropping items given to them at the start of the game.
     *
     * @param name    Name of the created item.
     * @param weight  Weight of the created item.
     * @param canDrop Whether the item can be dropped or not.
     */
    public Item(String name, int weight, boolean canDrop) {
        this(name, weight);
        this.canDrop = canDrop;
    }

    /**
     *  Put an item in a random room after removing the unavailable rooms from the available rooms.
     *
     * @param availableRooms   A list of all rooms in the game.
     * @param unavailableRooms Rooms that are unavailable due to being locked.
     */
    public void setItemToRandomRoom(List<Room> availableRooms, List<Room> unavailableRooms) {
        // Get a random room from the given rooms.
        ArrayList<Room> listCopy = new ArrayList<>(availableRooms);

        if (unavailableRooms != null)
        {
            for (Room room : unavailableRooms)
            {
                listCopy.remove(room);
            }
        }

        this.setItemToRoom(listCopy.get(new Random().nextInt(listCopy.size())));
    }

    /**
     * Place the item in a random room, it may also be placed in rooms that are locked.
     *
     * @param availableRooms A list of all rooms in the game.
     */
    public void setItemToRandomRoom(List<Room> availableRooms) {
        // Get a random room from the given rooms.
        this.setItemToRoom(availableRooms.get(new Random().nextInt(availableRooms.size())));
    }

    /**
     * Add the item to the given room.
     *
     *  @param room Room given to add the item to.
     */
    public void setItemToRoom(Room room) {
        room.addItem(this);
    }

    /**
     * Get the item's weight.
     *
     * @return The weight of the item
     */
    public int getWeight() {
        return this.weight;
    }

    /**
     * Get the item's name.
     *
     * @return The name of the item.
     */
    public String getName() {
        return this.name;
    }

    /**
     * Check if the item can be picked up or not.
     *
     * @return Whether the item can be picked up.
     */
    public boolean isCanPickup() {
        return this.canPickup;
    }

    /**
     * Check if the item can be dropped or not.
     *
     * @return Whether the item can be dropped.
     */
    public boolean isCanDrop() {
        return canDrop;
    }
}