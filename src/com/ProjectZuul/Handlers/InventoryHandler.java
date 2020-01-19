package com.ProjectZuul.Handlers;

import com.ProjectZuul.GUI.Components.Inventory;
import com.ProjectZuul.GUI.Listeners.SetInactiveListener;
import com.ProjectZuul.Models.Item;
import com.ProjectZuul.Models.Player;
import com.ProjectZuul.Models.Room;

import javax.swing.*;

public class InventoryHandler implements SetInactiveListener {

    private JFrame window;
    private Inventory inventory;
    private Player player;

    public InventoryHandler(JFrame window, Player player) {
        this.window = window;
        this.player = player;
        this.init();
    }

    private void init() {
        this.inventory = new Inventory(this.player);
        this.window.add(this.inventory);
    }


    public void addItem(Item item, Room room) {
        if (item.isCanPickup()) {
            if (this.player.addItem(item)) {
                this.inventory.addItem(item);
                room.removeItem(item);
                System.out.println(room.getName());
            }
        }
    }

    @Override
    public void setMenuVisibility(boolean visibility) {

    }
}
