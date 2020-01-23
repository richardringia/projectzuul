package com.ProjectZuul.Handlers;

import com.ProjectZuul.GUI.Components.Inventory;
import com.ProjectZuul.GUI.Components.MyFrame;
import com.ProjectZuul.GUI.GameUI;
import com.ProjectZuul.GUI.Listeners.SetInactiveListener;
import com.ProjectZuul.Models.Item;
import com.ProjectZuul.Models.Player;
import com.ProjectZuul.Models.Room;

import javax.swing.*;

/**
 * Creates a new inventory view and handles pickup/drops.
 *
 * @author Richard Ringia and Anne Pier Merkus
 */
public class InventoryHandler implements SetInactiveListener {

    /**
     * The window of this application.
     */
    private MyFrame window;

    /**
     * Instance of the inventory view.
     */
    private Inventory inventory;

    /**
     * The player of the game.
     */
    private Player player;

    /**
     * Listener to set the inventory view visible or invisible.
     */
    private SetInactiveListener inventoryListener;

    /**
     * Handles all the text in the application with the right language
     */
    private LanguageHandler languageHandler;

    /**
     * Creates an InventoryHandler and sets variables with given values.
     *
     * @param window the window
     * @param player the player
     */
    public InventoryHandler(MyFrame window, Player player) {
        this.window = window;
        this.player = player;
        this.languageHandler = player.getLanguageHandler();
        this.init();
    }

    /**
     * Creates inventory view and adds it to the window.
     */
    private void init() {
        this.inventory = new Inventory(this.player);
        inventoryListener = inventory;
        this.window.add(this.inventory);
    }

    /**
     * Create a Flashlight item and add it to the players inventory.
     * Called when the difficulty is set to Medium or Easy
     */
    public void createFlashLight() {
        Item item = new Item(this.languageHandler.get("GAME_ITEMS_FLASHLIGHT"), 4, false);
        if (this.player.addItem(item)) {
            this.inventory.addItem(item);
        }
    }

    /**
     * Create a Map item and add it to the players inventory.
     * Called when the difficulty is set to easy.
     */
    public void createMap()
    {
        Item item = new Item(this.languageHandler.get("GAME_ITEMS_MAP"), 4, false);
        if (this.player.addItem(item)) {
            this.inventory.addItem(item);
        }
    }

    /**
     * Checks if the given item can be picked up and whether the player has a full inventory.
     * If the inventory isn't full, check if it will be able to add the item to the inventory without it becoming too heavy.
     * Adds item to inventory and removes it from the room so it can only be picked up once.
     *
     * @param item The item selected to be picked up.
     */
    public void addItem(Item item) {
        if (item.isCanPickup() && !this.player.isInventoryFull(item.getWeight())) {
            if (this.player.addItem(item)) {
                this.inventory.addItem(item);
                this.player.getCurrentRoom().removeItem(item);
            }
        }
    }

    /**
     * Check if the item can be dropped or if it's a must-keep item (fool proof on certain difficulties).
     * Removes the item from the player and adds it to the room so the player can pick it up again if they wish.
     *
     * @param item The item selected to be dropped.
     */
    public void dropItem(Item item) {
        if (item.isCanPickup() && item.isCanDrop()) {
            if (this.player.removeItem(item)) {
                this.player.addItemToRoom(item);
            }
        }
    }

    /**
     * Enable or Disable the inventory view.
     *
     * @param visibility Whether the menu should be set visible or invisible.
     */
    @Override
    public void setMenuVisibility(boolean visibility) {
        inventoryListener.setMenuVisibility(visibility);
    }
}
