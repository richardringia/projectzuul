package com.ProjectZuul.Models;

import java.util.Set;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.ArrayList;

/**
 * Class Room - a room in an adventure game.
 *
 * This class is part of the "World of Zuul" application. 
 * "World of Zuul" is a very simple, text based adventure game.  
 *
 * A "Room" represents one location in the scenery of the game.  It is 
 * connected to other rooms via exits.  For each existing exit, the room 
 * stores a reference to the neighboring room.
 * 
 * @author  Michael Kolling and David J. Barnes
 * @version 2016.02.29
 */

public class Room 
{
    private String name;
    private String description;
    private boolean doorLocked;
    private String unlockItem;
    private HashMap<String, Room> exits;        // stores exits of this room.
    private List<Item> itemList; // stores all the items in the room

    /**
     * Create a room described "description". Initially, it has
     * no exits. "description" is something like "a kitchen" or
     * "an open court yard".
     * @param description The room's description.
     */
    public Room(String name, String description)
    {
        this.name = name;
        this.description = description;
        exits = new HashMap<>();
        itemList = new ArrayList<>();
    }

    public Room(String name, String description, boolean doorLocked, String unlockItem)
    {
        this.name = name;
        this.description = description;
        this.unlockItem = unlockItem;
        this.doorLocked = doorLocked;
        exits = new HashMap<>();
        itemList = new ArrayList<>();
    }

    public boolean getDoorLocked()
    {
        return doorLocked;
    }

    public void setDoorLocked(boolean unlock)
    {
        doorLocked = unlock;
    }

    public String getUnlockItem()
    {
        return unlockItem;
    }

    /**
     * Define an exit from this room.
     * @param direction The direction of the exit.
     * @param neighbor  The room to which the exit leads.
     */
    public void setExit(String direction, Room neighbor) 
    {
        exits.put(direction, neighbor);
    }

    /**
     * @return The short description of the room
     * (the one that was defined in the constructor).
     */
    public String getShortDescription()
    {
        return description;
    }

    /**
     * Return a description of the room in the form:
     *     You are in the kitchen.
     *     Exits: north west
     * @return A long description of this room
     */
    public String getLongDescription()
    {
        return "\n" + description + ".\n\n\n" + "What would you like to do?";
    }

    /**
     * Return a string describing the room's exits, for example
     * "Exits: north west".
     * @return Details of the room's exits.
     */
    private String getExitString()
    {
        StringBuilder returnString = new StringBuilder("To which direction would you like to move? \n");
        Set<String> keys = exits.keySet();
        for(String exit : keys) {
            returnString.append(" \n").append(exit);
        }
        return returnString.toString();
    }

    /**
     * Return the room that is reached if we go from this room in direction
     * "direction". If there is no room in that direction, return null.
     * @param direction The exit's direction.
     * @return The room in the given direction.
     */
    public Room getExit(String direction) 
    {
        return exits.get(direction);
    }

    public void addItem(Item item) {
        this.itemList.add(item);
    }

    public List<Item> getItems() {
        return this.itemList;
    }


    public Item getItem(String name) {
        return this.itemList.stream()
                .filter(item -> name.equals(item.getName()))
                .findAny()
                .orElse(null);
    }

    public void removeItem(Item item) {
        this.itemList.remove(item);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

