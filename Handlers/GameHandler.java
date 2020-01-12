package Handlers;

import java.util.List;
import java.util.ArrayList;

import Models.*;

/**
 * Deze class voorzorgt het maken van het spel.
 * Dus alle kamers en items worden hier gegenereerd.
 */
public class GameHandler {

    // In deze list worden alle kamers gestored
    private List<Room> roomList;

    // In deze list worden alle items gestored
    private List<Item> itemList;

    public GameHandler() {
        RoomHandler roomHandler = new RoomHandler();
        roomList = roomHandler.getRoomList();

        itemList = ItemHandler.createItems(roomList, roomHandler.getVaultRoom(), roomHandler.getMapRoom(), roomHandler.getKeysRoom());
    }

    public List<Room> getRoomList()
    {
        return this.roomList;
    }

    public List<Item> getItemList()
    {
        return this.itemList;
    }
}