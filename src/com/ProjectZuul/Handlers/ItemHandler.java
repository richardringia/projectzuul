package com.ProjectZuul.Handlers;

import java.lang.reflect.Array;
import java.util.List;
import java.util.ArrayList;
import java.util.*; // TODO: CHANGE

import com.ProjectZuul.Models.*;

public class ItemHandler {

    public static List<Item> createItems(List<Room> rooms, Room vaultRoom, Room mapRoom, Room keysRoom) {
        Item vaultKeys, spiritVacuumCleaner, bookcase, vault, principlesKeys;

        vaultKeys = new Item("Vault keys", 2);
        principlesKeys = new Item("Principles keys", 2);
        spiritVacuumCleaner = new Item("Spirit vacuum cleaner", 8);
        bookcase = new Item("Bookcase");
        vault = new Vault(vaultKeys, vaultRoom);

//        map.setItemToRoom(mapRoom);
        ArrayList<Room> unavailableRooms = getLockedRooms(rooms);

        principlesKeys.setItemToRandomRoom(rooms, unavailableRooms);
        vaultKeys.setItemToRoom(keysRoom);
        spiritVacuumCleaner.setItemToRandomRoom(rooms);
        bookcase.setItemToRoom(mapRoom);

        return new ArrayList<>(Arrays.asList(vaultKeys, spiritVacuumCleaner, bookcase, vault));
    }

    private static ArrayList<Room> getLockedRooms(List<Room> rooms)
    {
        ArrayList<Room> lockedRooms = new ArrayList<>(rooms);
        lockedRooms.removeIf(e -> !e.getDoorLocked());
        return lockedRooms;
    }

    public static void setFlashlightRoom(List<Room> rooms)
    {
        ArrayList<Room> unavailableRooms = getLockedRooms(rooms);
        Item flashlight;
        flashlight = new Item("Flashlight", 4);
        flashlight.setItemToRandomRoom(rooms, unavailableRooms);
    }

    public static void setMapRoom(List<Room> rooms)
    {
        ArrayList<Room> unavailableRooms = getLockedRooms(rooms);
        Item map;
        map = new Item("Map", 4);
        map.setItemToRandomRoom(rooms, unavailableRooms);
    }
}