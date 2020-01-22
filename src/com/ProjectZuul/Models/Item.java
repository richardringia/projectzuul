package com.ProjectZuul.Models;

import java.lang.reflect.Array;
import java.util.ArrayList;
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
     * @param name
     * @param weight
     *
     * With this constructor an item with canpickup can be created.
     */
    public Item(String name, int weight) {
        this.name = name;
        this.weight = weight;
        this.canPickup = true;
        this.canDrop = true;
    }

    public Item(String name, int weight, boolean canDrop) {
        this(name, weight);
        this.canDrop = canDrop;
    }

    /**
     * @param availableRooms
     *
     * Put an item in a random room
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
        for (Room room : listCopy)
        {
            System.out.println(room.getName());
        }
        this.setItemToRoom(listCopy.get(new Random().nextInt(listCopy.size())));
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

    public boolean isCanDrop() {
        return canDrop;
    }
}