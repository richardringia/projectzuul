package com.ProjectZuul.Models;

import com.ProjectZuul.GUI.GameUI;
import com.ProjectZuul.Handlers.ActionHandler;
import com.ProjectZuul.Handlers.GameHandler;
import com.ProjectZuul.Handlers.InventoryHandler;
import com.ProjectZuul.Handlers.LanguageHandler;

import javax.print.DocFlavor;
import java.util.List;
import java.util.ArrayList;

/**
 * In this class the player will be created.
 * With the player class items can be stored.
 *
 * @author Richard Ringia
 */
public class Player {
    /**
     * Maximum weight the player can carry in his inventory.
     */
    private int maxWeight = 10;

    /**
     * List of all items the player is carrying.
     */
    private List<Item> itemList;

    /**
     * Instance of the current GameUI.
     */
    private GameUI gameUI;

    /**
     * Instantiates a new Player.
     *
     * @param gameUI the game ui
     */

    /**
     * create the Player, set the gameUI value and initialize the itemList.
     *
     * @param gameUI Current instance of GameUI, value is saved in gameUI variable.
     */
    public Player(GameUI gameUI) {
        this.gameUI = gameUI;
        this.itemList = new ArrayList<>();
    }

    /**
     * If the player can pick up the item it is added to the list of items.
     * toggleMap is called to check if the player picked up the map, if so we make the map visible.
     *
     * @param item The item the player wants to pick up.
     * @return Whether the player can pick up the item or if it would be too much weight.
     */
    public boolean addItem(Item item) {
        if (this.getTotalWeight() <= this.maxWeight) {
            itemList.add(item);
            this.toggleMap(item, true);
            return true;
        } else {
            return false;
        }
    }

    /**
     * Gets the item corresponding to the name given, if the player doesn't have the item, return null.
     *
     * @param name The name of the item the player wants to access.
     * @return The item corresponding to the name.
     */
    public Item getItem(String name) {
        return this.itemList.stream()
                .filter(item -> name.equals(item.getName()))
                .findAny()
                .orElse(null);
    }

    /**
     * Loops through all the items and adds their name to an ArrayList.
     *
     * @return A list with names of all items.
     */
    public ArrayList<String> getItemNames() {
        ArrayList<String> itemNames = new ArrayList<>();
        for (Item item : itemList) {
            itemNames.add(item.getName());
        }

        return itemNames;
    }

    /**
     * Add the weight of all items together and return the result.
     *
     * @return The weight the player is currently carrying.
     */
    public int getTotalWeight() {
        int weight = 0;
        for (Item item : this.itemList) {
            weight += item.getWeight();
        }
        return weight;
    }

    /**
     * Gets the maxWeight of the player.
     *
     * @return the maximum weight the player can carry.
     */
    public int getMaxWeight() {
        return maxWeight;
    }

    /**
     * Calculates whether the player can pick up the item.
     *
     * @see #getTotalWeight()
     * @see #getMaxWeight()
     * @param itemWeight Weight of the item the player is trying to pick up
     * @return Whether the total weight and weight of the given item are bigger than the maximum weight.
     */
    public boolean isInventoryFull(int itemWeight) {
        return (this.getTotalWeight() + itemWeight) > this.maxWeight;
    }

    /**
     * Remove the given item from the itemList.
     *
     * @param item The item the player wants to drop.
     * @return Whether the item is removed.
     */
    public boolean removeItem(Item item) {
        this.toggleMap(item, false);
        return itemList.remove(item);
    }

    /**
     * Gets the current room from the instance of GameHandler.
     *
     * @see GameHandler#getCurrentRoom()
     * @return The room the player is currently in.
     */
    public Room getCurrentRoom() {
        return this.gameUI.getGameHandler().getCurrentRoom();
    }

    /**
     * Gets ActionHandler from GameUI.
     *
     * @return Instance of ActionHandler.
     */
    public ActionHandler getActionHandler() {
        return this.gameUI.getActionHandler();
    }

    /**
     * Gets the IventoryHandler from GameUI.
     *
     * @return Instance of InventoryHandler.
     */
    public InventoryHandler getInventoryHandler() {
        return this.gameUI.getInventoryHandler();
    }

    /**
     * Add the given item to the room the player is currently in and call setInvestigationItems to update the UI.
     *
     * @param item The item to be added to the room.
     */
    public void addItemToRoom(Item item) {
        this.getCurrentRoom().addItem(item);
        this.gameUI.setInvestigationItems();
    }

    /**
     * Check if the item is map, if it is, set toggle the map's visibility.
     * @param item The item to be checked whether it is the map.
     * @param b Whether to set the map active or inactive.
     */
    private void toggleMap(Item item, boolean b) {
        if (item.getName().equals(this.getLanguageHandler().get("GAME_ITEMS_MAP")))
            this.gameUI.getMapHandler().setMenuVisibility(b);
    }

    /**
     * Gets LanguageHandler so this class knows what language the player is playing in.
     *
     * @see LanguageHandler
     * @return Instance of LanguageHandler.
     */
    public LanguageHandler getLanguageHandler() {
        return this.gameUI.getLanguageHandler();
    }
}