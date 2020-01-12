package Handlers;

import java.util.List;
import java.util.ArrayList;

import Models.*;
import Handlers.*;

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
        roomList = new ArrayList<>();
        createRooms();
    }

    public List<Room> getRoomList()
    {
        return roomList;
    }

    private void createRooms() {
        roomList = RoomHandler.createRooms();
    }

    private void createItems() {

    }
}