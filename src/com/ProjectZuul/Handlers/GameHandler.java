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

    private Room currentRoom;

    // In deze list worden alle kamers gestored
    private List<Room> roomList;

    // In deze list worden alle items gestored
    private List<Item> itemList;


    /**
     * Instantiates a new Game handler.
     *
     * @param player the player
     */
    public GameHandler(Player player) {
        RoomHandler roomHandler = new RoomHandler(player);
        roomList = roomHandler.getRoomList();
        itemList = ItemHandler.createItems(roomList, roomHandler.getVaultRoom(), roomHandler.getMapRoom(), roomHandler.getKeysRoom(), player);
        currentRoom = roomList.get(0);
    }

    /**
     * Gets room list.
     *
     * @return the room list
     */
    public List<Room> getRoomList()
    {
        return this.roomList;
    }

    /**
     * Gets item list.
     *
     * @return the item list
     */
    public List<Item> getItemList()
    {
        return this.itemList;
    }

    /**
     * Gets current room.
     *
     * @return the current room
     */
    public Room getCurrentRoom()
    {
        return currentRoom;
    }

    /**
     * Sets current room.
     *
     * @param currentRoom the current room
     */
    public void setCurrentRoom(Room currentRoom)
    {
        this.currentRoom = currentRoom;
    }
}