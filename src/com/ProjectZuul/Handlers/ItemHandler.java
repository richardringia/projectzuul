package com.ProjectZuul.Handlers;

import java.lang.reflect.Array;
import java.util.List;
import java.util.ArrayList;
import java.util.*; // TODO: CHANGE

import com.ProjectZuul.Models.*;

public class ItemHandler {

    public static List<Item> createItems(List<Room> rooms, Room vaultRoom, Room mapRoom, Room keysRoom) {
        Item map, vaultKeys, flashlight, spiritVacuumCleaner, bookcase, vault, principlesKeys;

        map = new Item("Map", 2);
        vaultKeys = new Item("Vault keys", 2);
        principlesKeys = new Item("Principles keys", 2);
        flashlight = new Item("Flashlight", 3);
        spiritVacuumCleaner = new Item("Spirit vacuum cleaner", 8);
        bookcase = new Item("Bookcase");
        vault = new Vault(vaultKeys, vaultRoom);

//        map.setItemToRoom(mapRoom);
        ArrayList<Room> unavailableRooms = getLockedRooms(rooms);

        principlesKeys.setItemToRandomRoom(rooms, unavailableRooms);
        map.setItemToRoom(rooms.get(0));
        vaultKeys.setItemToRoom(keysRoom);
        flashlight.setItemToRandomRoom(rooms, null);
        spiritVacuumCleaner.setItemToRandomRoom(rooms, null);
        bookcase.setItemToRoom(mapRoom);

        return new ArrayList<>(Arrays.asList(map, vaultKeys, flashlight, spiritVacuumCleaner, bookcase, vault));
    }

    private static ArrayList<Room> getLockedRooms(List<Room> rooms)
    {
        ArrayList<Room> lockedRooms = new ArrayList<>(rooms);
        lockedRooms.removeIf(e -> !e.getDoorLocked());
        return lockedRooms;
    }
}