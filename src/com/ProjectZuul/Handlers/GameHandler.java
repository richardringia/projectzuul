package com.ProjectZuul.Handlers;

//package com.ProjectZuul.Handlers;


import com.ProjectZuul.Models.Item;
import com.ProjectZuul.Models.Room;
import com.ProjectZuul.Zuul.Game;

import java.util.List;
import java.util.ArrayList;

/**
 * Deze class voorzorgt het maken van het spel.
 * Dus alle kamers en items worden hier gegenereerd.
 */
public class GameHandler {

    Game gameInstance;
    // In deze list worden alle kamers gestored
    private List<Room> roomList;

    // In deze list worden alle items gestored
    private List<Item> itemList;


    public GameHandler() {
        RoomHandler roomHandler = new RoomHandler();
        roomList = roomHandler.getRoomList();
        RoomHandler room = new RoomHandler();
        itemList = ItemHandler.createItems(roomList, roomHandler.getVaultRoom(), roomHandler.getMapRoom(), roomHandler.getKeysRoom());
    }
    
    public void startTimer()
    {
        System.out.println("TIMER RUNNING");
        new java.util.Timer().schedule( 
        new java.util.TimerTask() {
            @Override
            public void run() {
                 gameInstance.setBecomePossessed(true);
            }
        }, 
        5000 
);
    }
    public List<Room> getRoomList()
    {
        return this.roomList;
    }

    public List<Item> getItemList()
    {
        return this.itemList;
    }
    
    public void setGameInstance(Game gameInstance)
    {
        this.gameInstance = gameInstance;
    }
}