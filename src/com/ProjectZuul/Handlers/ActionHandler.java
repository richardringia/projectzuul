package com.ProjectZuul.Handlers;

import com.ProjectZuul.GUI.ActionMenu;
import com.ProjectZuul.GUI.Components.MyButton;
import com.ProjectZuul.Models.Player;

import java.awt.*;
import java.awt.event.ActionListener;

/**
 * The type Action handler.
 */
public class ActionHandler {

    private ActionMenu actionMenu;

    private LanguageHandler languageHandler;

    /**
     * Instantiates a new Action handler.
     *
     * @param player     the player 
     * @param actionMenu the action menu
     */
    public ActionHandler(Player player, ActionMenu actionMenu) {
        this.languageHandler = player.getLanguageHandler();
        this.actionMenu = actionMenu;
    }

    private void before() {
        this.actionMenu.reset();
    }

    private void after() {
        this.actionMenu.add(this.createCancelButton());
        this.actionMenu.updateUI();
    }

    /**
     * Create menu.
     */
    public void createMenu() {
        this.before();
        this.after();
    }

    /**
     * Create menu.
     *
     * @param actionListener   the action listener
     * @param customButtonText the custom button text
     * @param canDrop          the can drop
     */
    public void createMenu(ActionListener actionListener, String customButtonText, boolean canDrop) {
        this.before();
        this.actionMenu.add(this.createButton(customButtonText, actionListener, !canDrop, this.languageHandler.get("GAME_ACTION_MENU_DROP_ERROR")));
        this.after();
    }

    /**
     * Create menu.
     *
     * @param actionListener the action listener
     * @param canOpenVault   the can open vault
     */
    public void createMenu(ActionListener actionListener, boolean canOpenVault) {
        this.before();
        this.actionMenu.add(this.createOpenButton(actionListener, !canOpenVault));
        this.after();
    }

    /**
     * Create menu.
     *
     * @param actionListener the action listener
     * @param player         the player
     * @param itemWeight     the item weight
     */
    public void createMenu(ActionListener actionListener, Player player, int itemWeight) {
        this.before();
        this.actionMenu.add(this.createPickupButton(actionListener, player.isInventoryFull(itemWeight)));
        this.after();
    }

    private MyButton createButton(String text) {
        MyButton myButton = new MyButton(text, Color.BLACK, Color.WHITE, new Dimension(140, 30));
        myButton.addActionListener(e -> {
            this.actionMenu.reset();
        });
        return myButton;
    }

    private MyButton createButton(String text, ActionListener actionListener) {
        MyButton myButton = this.createButton(text);
        myButton.addActionListener(actionListener);
        return myButton;
    }

    private MyButton createButton(String text, ActionListener actionListener, boolean disabled, String message) {
        MyButton myButton = this.createButton(text, actionListener);
        myButton.setEnabled(!disabled, message);
        return myButton;
    }

    private MyButton createOpenButton(ActionListener action, boolean disabled) {
        return this.createButton(this.languageHandler.get("GAME_ACTION_MENU_OPEN"), action, disabled, this.languageHandler.get("GAME_ACTION_MENU_OPEN_ERROR"));
    }

    private MyButton createPickupButton(ActionListener action, boolean disabled) {
        return this.createButton(this.languageHandler.get("GAME_ACTION_MENU_PICK_UP"), action, disabled, this.languageHandler.get("GAME_ACTION_MENU_PICK_UP_ERROR"));
    }

    private MyButton createCancelButton() {
        return this.createButton(this.languageHandler.get("GAME_CANCEL"));
    }


}
