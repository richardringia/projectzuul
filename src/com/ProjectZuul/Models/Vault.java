package com.ProjectZuul.Models;

import com.ProjectZuul.Handlers.LanguageHandler;

public class Vault extends Item {
    private Item key;
    private Item document;

    public Vault(Item key, Room room, Player player){
        super(player.getLanguageHandler().get("GAME_ITEMS_VAULT"));
        this.key = key;
        document = new Item(player.getLanguageHandler().get("GAME_ITEMS_VAULT_DOCUMENT"), 0);
        this.setItemToRoom(room);
    }


    public boolean canOpenVault(Item item) {
        return item == key;
    }
 }