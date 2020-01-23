package com.ProjectZuul.Handlers;

//package com.ProjectZuul.Handlers;


import com.ProjectZuul.Models.Item;
import com.ProjectZuul.Models.Player;
import com.ProjectZuul.Models.Room;

import java.util.List;
import java.util.ArrayList;

/**
 * Deze class voorzorgt het maken van het spel.
 * Dus alle kamers en items worden hier gegenereerd.
 */
public class GameHandler {

    private Room currentRoom;

    // In deze list worden alle kamers gestored
    private List<Room> roomList;

    // In deze list worden alle items gestored
    private List<Item> itemList;


    public GameHandler(Player player) {
        RoomHandler roomHandler = new RoomHandler(player);
        roomList = roomHandler.getRoomList();
        itemList = ItemHandler.createItems(roomList, roomHandler.getVaultRoom(), roomHandler.getMapRoom(), roomHandler.getKeysRoom(), player);
        currentRoom = roomList.get(0);
    }

    public List<Room> getRoomList()
    {
        return this.roomList;
    }

    public List<Item> getItemList()
    {
        return this.itemList;
    }

    public Room getCurrentRoom()
    {
        return currentRoom;
    }

    public void setCurrentRoom(Room currentRoom)
    {
        this.currentRoom = currentRoom;
    }
}