package com.ProjectZuul.Models;

import com.ProjectZuul.Enums.Language;
import com.ProjectZuul.Handlers.LanguageHandler;

import java.util.Set;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.ArrayList;

/**
 * Class Room - a room in an adventure game.
 * <p>
 * This class is part of the "World of Zuul" application.
 * "World of Zuul" is a very simple, text based adventure game.
 * <p>
 * A "Room" represents one location in the scenery of the game.  It is
 * connected to other rooms via exits.  For each existing exit, the room
 * stores a reference to the neighboring room.
 *
 * @see com.ProjectZuul.Handlers.RoomHandler
 * @author Anne Pier Merkus and Richard Ringia
 */
public class Room
{
    /**
     * Name of the room.
     */
    private String name;

    /**
     * Description of the room.
     */
    private String description;

    /**
     * Whether the door is locked or not.
     */
    private boolean doorLocked;

    /**
     * The name of the item needed to unlock the door if it is locked.
     */
    private String unlockItem;

    /**
     * List of possible exits in the current room.
     */
    private HashMap<String, Room> exits;

    /**
     * List of all items in the room.
     */
    private List<Item> itemList;

    /**
     * Instance of LanguageHandler.
     */
    private LanguageHandler languageHandler;

    /**
     * Create a new room, set variables with values given and initialize lists.
     *
     * @param name        The name given to this room.
     * @param description The room's description.
     * @param player      Instance of player
     */
    public Room(String name, String description, Player player)
    {
        this.name = name;
        this.description = description;
        this.languageHandler = player.getLanguageHandler();
        exits = new HashMap<>();
        itemList = new ArrayList<>();
    }

    /**
     * Calls another constructor and sets whether the door is locked.
     * Also gives corresponding item to unlocking the door if it is locked.
     *
     * @param name        the name
     * @param description the description
     * @param doorLocked  the door locked
     * @param unlockItem  the unlock item
     * @param player      the player
     */
    public Room(String name, String description, boolean doorLocked, String unlockItem, Player player)
    {
        this(name, description, player);
        this.unlockItem = unlockItem;
        this.doorLocked = doorLocked;
        exits = new HashMap<>();
        itemList = new ArrayList<>();
    }

    /**
     * Gets whether the door is locked or not.
     *
     * @return Whether the door is locked
     */
    public boolean getDoorLocked()
    {
        return doorLocked;
    }

    /**
     * Sets the state of doorlocked.
     *
     * @param unlock Whether the door becomes locked or not.
     */
    public void setDoorLocked(boolean unlock)
    {
        doorLocked = unlock;
    }

    /**
     * Gets the name of the item the door can be unlocked with.
     *
     * @return The name of the item the door can be unlocked with.
     */
    public String getUnlockItem()
    {
        return unlockItem;
    }

    /**
     * Define an exit from this room.
     *
     * @param direction The direction of the exit.
     * @param neighbor  The room to which the exit leads.
     */
    public void setExit(String direction, Room neighbor)
    {
        exits.put(direction, neighbor);
    }

    /**
     * Return a description of the room in the form:
     * You are in the kitchen.
     * Exits: north west
     *
     * @return A long description of this room
     */
    public String getLongDescription()
    {
        return "\n" + description + ".\n\n\n" + this.languageHandler.get("GAME_ROOM_LONG_DESC");
    }

    /**
     * Return the room that is reached if we go from this room in direction
     * "direction". If there is no room in that direction, return null.
     *
     * @param direction The exit's direction.
     * @return The room in the given direction.
     */
    public Room getExit(String direction)
    {
        return exits.get(direction);
    }

    /**
     * Add item too the room so the player can pick it up later.
     *
     * @param item The item to be added to the room.
     */
    public void addItem(Item item) {
        this.itemList.add(item);
    }

    /**
     * Gets all items in this room.
     *
     * @return A list with all items in this room.
     */
    public List<Item> getItems() {
        return this.itemList;
    }

    /**
     * Remove an item from the room so it can no longer be picked up later.
     *
     * @param item The item the player picked up.
     */
    public void removeItem(Item item) {
        this.itemList.remove(item);
    }

    /**
     * Gets the name of the room.
     *
     * @return The name of the room.
     */
    public String getName() {
        return name;
    }
}

