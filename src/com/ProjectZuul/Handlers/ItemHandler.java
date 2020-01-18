package com.ProjectZuul.Handlers;

import java.util.List;
import java.util.ArrayList;
import java.util.*; // TODO: CHANGE

import com.ProjectZuul.Models.*;

public class ItemHandler {

    public static List<Item> createItems(List<Room> rooms, Room vaultRoom, Room mapRoom, Room keysRoom) {
        Item map, vaultKeys, flashlight, spiritVacuumCleaner, bookcase, vault;

        map = new Item("Map", 2);
        vaultKeys = new Item("Vault keys", 1);
        flashlight = new Item("Flashlight", 3);
        spiritVacuumCleaner = new Item("Spirit vacuum cleaner", 8);
        bookcase = new Item("Bookcase");
        vault = new Vault(vaultKeys, vaultRoom);

//        map.setItemToRoom(mapRoom);
        map.setItemToRoom(rooms.get(0));
        vaultKeys.setItemToRoom(keysRoom);
        flashlight.setItemToRandomRoom(rooms);
        spiritVacuumCleaner.setItemToRandomRoom(rooms);
        bookcase.setItemToRoom(mapRoom);

        return new ArrayList<>(Arrays.asList(map, vaultKeys, flashlight, spiritVacuumCleaner, bookcase, vault));
    }


}