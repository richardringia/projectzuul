package com.ProjectZuul.Handlers;

import java.lang.reflect.Array;
import java.util.List;
import java.util.ArrayList;
import java.util.*; // TODO: CHANGE

import com.ProjectZuul.Models.*;

public class ItemHandler {

    public static List<Item> createItems(List<Room> rooms, Room vaultRoom, Room mapRoom, Room keysRoom, Player player) {
        Item vaultKeys, spiritVacuumCleaner, bookcase, vault, principlesKeys;
        LanguageHandler languageHandler = player.getLanguageHandler();

        vaultKeys = new Item(languageHandler.get("GAME_ITEMS_VAULT_KEYS"), 2);
        principlesKeys = new Item(languageHandler.get("GAME_ITEMS_PRINCIPLES_KEYS"), 2);
        spiritVacuumCleaner = new Item(languageHandler.get("GAME_ITEMS_SPIRIT_VACUUM_CLEANER"), 8);
        bookcase = new Item(languageHandler.get("GAME_ITEMS_BOOKCASE"));
        vault = new Vault(vaultKeys, vaultRoom, player);

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

    public static void setFlashlightRoom(List<Room> rooms, String name)
    {
        ArrayList<Room> unavailableRooms = getLockedRooms(rooms);
        Item flashlight;
        flashlight = new Item(name, 4);
        flashlight.setItemToRandomRoom(rooms, unavailableRooms);
    }

    public static void setMapRoom(List<Room> rooms, String name)
    {
        ArrayList<Room> unavailableRooms = getLockedRooms(rooms);
        Item map;
        map = new Item(name, 4);
        map.setItemToRandomRoom(rooms, unavailableRooms);
    }
}