package com.ProjectZuul.Models;

import com.ProjectZuul.Handlers.LanguageHandler;

/**
 * Vault is the item where the player needs to go to win the game.
 * Vault is an item with a few different aspects to it so Item is extended.
 *
 * @author Richard Ringia
 */
public class Vault extends Item {
    /**
     * Item required to open the vault.
     */
    private Item key;

    /**
     * Create the vault and set the key required to unlock and the room it will be placed in.
     *
     * @param key    Key required to open the vault
     * @param room   Room where the vault is located.
     * @param player Instance of the player to retrieve the language settings.
     */
    public Vault(Item key, Room room, Player player){
        super(player.getLanguageHandler().get("GAME_ITEMS_VAULT"));
        this.key = key;
        this.setItemToRoom(room);
    }


    /**
     * Checks if the player has the key to the vault.
     * If he does, he can open the vault and wins the game.
     *
     * @param item Item given to compare it to the key.
     * @return Whether the item given is the key required.
     */
    public boolean canOpenVault(Item item) {
        return item == key;
    }
 }