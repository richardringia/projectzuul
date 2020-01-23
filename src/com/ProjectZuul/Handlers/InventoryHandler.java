package com.ProjectZuul.Handlers;

import com.ProjectZuul.GUI.Components.Inventory;
import com.ProjectZuul.GUI.GameUI;
import com.ProjectZuul.GUI.Listeners.SetInactiveListener;
import com.ProjectZuul.Models.Item;
import com.ProjectZuul.Models.Player;
import com.ProjectZuul.Models.Room;

import javax.swing.*;

public class InventoryHandler implements SetInactiveListener {

    private JFrame window;
    private Inventory inventory;
    private Player player;
    private SetInactiveListener inventoryListener;
    private LanguageHandler languageHandler;

    public InventoryHandler(JFrame window, Player player) {
        this.window = window;
        this.player = player;
        this.languageHandler = player.getLanguageHandler();
        this.init();
    }

    private void init() {
        this.inventory = new Inventory(this.player);
        inventoryListener = inventory;
        this.window.add(this.inventory);
    }

    public void createFlashLight() {
        Item item = new Item(this.languageHandler.get("GAME_ITEMS_FLASHLIGHT"), 4, false);
        if (this.player.addItem(item)) {
            this.inventory.addItem(item);
        }
    }

    public void createMap()
    {
        Item item = new Item(this.languageHandler.get("GAME_ITEMS_MAP"), 4, false);
        if (this.player.addItem(item)) {
            this.inventory.addItem(item);
        }
    }

    public void addItem(Item item) {
        if (item.isCanPickup() && !this.player.isInventoryFull(item.getWeight())) {
            if (this.player.addItem(item)) {
                this.inventory.addItem(item);
                this.player.getCurrentRoom().removeItem(item);
            }
        }
    }

    public void dropItem(Item item) {
        if (item.isCanPickup() && item.isCanDrop()) {
            if (this.player.removeItem(item)) {
                this.player.addItemToRoom(item);
            }
        }
    }

    @Override
    public void setMenuVisibility(boolean visibility) {
        inventoryListener.setMenuVisibility(visibility);
    }
}
