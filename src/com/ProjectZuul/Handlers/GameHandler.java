package com.ProjectZuul.Handlers;

//package com.ProjectZuul.Handlers;


import com.ProjectZuul.Models.Item;
import com.ProjectZuul.Models.Player;
import com.ProjectZuul.Models.Room;

import java.util.List;
import java.util.ArrayList;

/**
 * All rooms and items are generated here.
 * They are also added to a list so we can access them later.
 *
 * @author Anne Pier Merkus
 */
public class GameHandler {

    /**
     * The room the player is currently in.
     */
    private Room currentRoom;

    /**
     * A list with all rooms in the game.
     */
    private List<Room> roomList;

    /**
     * A list of all items in the game.
     */
    private List<Item> itemList;


    /**
     * Creates a new GameHandler and initalizes everything in the game.
     *
     * @param player Instance of the player to get the language from.
     */
    public GameHandler(Player player) {
        RoomHandler roomHandler = new RoomHandler(player);
        roomList = roomHandler.getRoomList();
        itemList = ItemHandler.createItems(roomList, roomHandler.getVaultRoom(), roomHandler.getMapRoom(), roomHandler.getKeysRoom(), player);
        currentRoom = roomList.get(0);
    }

    /**
     * Gets all rooms in the game.
     *
     * @return A list of all rooms.
     */
    public List<Room> getRoomList()
    {
        return this.roomList;
    }

    /**
     * Gets all items in the game.
     *
     * @return A list of all items.
     */
    public List<Item> getItemList()
    {
        return this.itemList;
    }

    /**
     * Gets the room the player is currently in
     *
     * @return The room the player is currently in.
     */
    public Room getCurrentRoom()
    {
        return currentRoom;
    }

    /**
     * Change the current room to the room the player moves to.
     *
     * @param currentRoom The room the player wants to move to.
     */
    public void setCurrentRoom(Room currentRoom)
    {
        this.currentRoom = currentRoom;
    }
}