package com.ProjectZuul.Handlers;

import java.lang.reflect.Array;
import java.util.List;
import java.util.ArrayList;
import java.util.*; // TODO: CHANGE

import com.ProjectZuul.Models.*;

/**
 * Creates all items and sets them to a room.
 *
 * @author Richard Ringia and Anne Pier Merkus
 */
public class ItemHandler {

    /**
     * Creates items and put them in a room, either random or a predetermined room. Item names correspond to the selected language.
     *
     * @param rooms     All rooms to which an item can be set.
     * @param vaultRoom The room where the vault is located.
     * @param mapRoom   The room where the map is located.
     * @param keysRoom  the room where the vault keys are located.
     * @param player    Instance of the current player
     * @return A list of all items.
     */
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

    /**
     * Takes all rooms in the game and checks whether they are locked and deletes them from the list if they are unlocked.
     * @param rooms A list of all rooms in the game.
     * @return A list of all rooms that are locked and still require a key to be opened.
     */
    private static ArrayList<Room> getLockedRooms(List<Room> rooms)
    {
        ArrayList<Room> lockedRooms = new ArrayList<>(rooms);
        lockedRooms.removeIf(e -> !e.getDoorLocked());
        return lockedRooms;
    }

    /**
     * If the difficulty is set to hard, the Flashlight will be put into a random (available) room.
     *
     * @param rooms A list of all rooms in the game.
     * @param name  The name of the item.
     */
    public static void setFlashlightRoom(List<Room> rooms, String name)
    {
        ArrayList<Room> unavailableRooms = getLockedRooms(rooms);
        Item flashlight;
        flashlight = new Item(name, 4);
        flashlight.setItemToRandomRoom(rooms, unavailableRooms);
    }

    /**
     * If the difficulty is set to Medium or Hard, the Map will be put into a random (available) room.
     *
     * @param rooms A list of all rooms in the game.
     * @param name  The name of the item.
     */
    public static void setMapRoom(List<Room> rooms, String name)
    {
        ArrayList<Room> unavailableRooms = getLockedRooms(rooms);
        Item map;
        map = new Item(name, 4);
        map.setItemToRandomRoom(rooms, unavailableRooms);
    }
}