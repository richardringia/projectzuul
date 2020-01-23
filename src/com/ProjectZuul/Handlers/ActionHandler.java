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
    /**
     * This ActionMenu component is used for adding buttons or remove buttons on certain events/
     */
    private ActionMenu actionMenu;

    /**
     * Handles all the text in the application with the right language
     */
    private LanguageHandler languageHandler;

    /**
     * Instantiates a new Action handler.
     *
     * @param player     the player of the game
     * @param actionMenu the action menu component
     */
    public ActionHandler(Player player, ActionMenu actionMenu) {
        this.languageHandler = player.getLanguageHandler();
        this.actionMenu = actionMenu;
    }

    /**
     * This function needs to be called before adding new buttons to the action menu.
     * This is because the other buttons needs to be removed
     */
    private void before() {
        this.actionMenu.reset();
    }

    /**
     * This function needs to be called after adding new buttons to the action menu.
     * This is because a cancel button needs to be added en the ui needs an update.
     */
    private void after() {
        this.actionMenu.add(this.createCancelButton());
        this.actionMenu.updateUI();
    }

    /**
     * Create a simple menu
     */
    public void createMenu() {
        this.before();
        this.after();
    }

    /**
     * Create a menu where a button has a custom text and can drop is a boolean
     *
     * @param actionListener   the action listener is used for adding an action to a button
     * @param customButtonText the custom button text
     * @param canDrop          the can drop
     */
    public void createMenu(ActionListener actionListener, String customButtonText, boolean canDrop) {
        this.before();
        this.actionMenu.add(this.createButton(customButtonText, actionListener, !canDrop, this.languageHandler.get("GAME_ACTION_MENU_DROP_ERROR")));
        this.after();
    }

    /**
     * Create a menu for opening the vault
     *
     * @param actionListener  the action listener is used for adding an action to a button
     * @param canOpenVault   the can open vault
     */
    public void createMenu(ActionListener actionListener, boolean canOpenVault) {
        this.before();
        this.actionMenu.add(this.createOpenButton(actionListener, !canOpenVault));
        this.after();
    }

    /**
     * Create a menu for picking up items
     *
     * @param actionListener the action listener is used for adding an action to a button
     * @param player         the player
     * @param itemWeight     the item weight
     */
    public void createMenu(ActionListener actionListener, Player player, int itemWeight) {
        this.before();
        this.actionMenu.add(this.createPickupButton(actionListener, player.isInventoryFull(itemWeight)));
        this.after();
    }

    /**
     * Create a button for the menu
     *
     * @param text the name of the button
     * @return the created button with an action
     */
    private MyButton createButton(String text) {
        MyButton myButton = new MyButton(text, Color.BLACK, Color.WHITE, new Dimension(140, 30));
        myButton.addActionListener(e -> {
            this.actionMenu.reset();
        });
        return myButton;
    }

    /**
     * Create a button with an extra action
     *
     * @param text the name of the button
     * @param actionListener an extra action for the button
     * @return the created button with an extra action
     */
    private MyButton createButton(String text, ActionListener actionListener) {
        MyButton myButton = this.createButton(text);
        myButton.addActionListener(actionListener);
        return myButton;
    }

    /**
     * Create a button with disable function and an extra action
     *
     * @param text the name of the button
     * @param actionListener an extra action for the button
     * @param disabled is used for enabling of disabling the button
     * @param message the message when the button is disabled
     * @return the created button
     */
    private MyButton createButton(String text, ActionListener actionListener, boolean disabled, String message) {
        MyButton myButton = this.createButton(text, actionListener);
        myButton.setEnabled(!disabled, message);
        return myButton;
    }

    /**
     * Create a button for opening the vault
     *
     * @param action an extra action for the button
     * @param disabled when the vault key is not in the inventory. Then the button needs to be disabled. This is done by disabled.
     * @return the created button
     */
    private MyButton createOpenButton(ActionListener action, boolean disabled) {
        return this.createButton(this.languageHandler.get("GAME_ACTION_MENU_OPEN"), action, disabled, this.languageHandler.get("GAME_ACTION_MENU_OPEN_ERROR"));
    }

    /**
     * Create a button for picking up items
     *
     * @param action an extra action for the button
     * @param disabled when the inventory is full then the button needs to be disabled. This is done by disabled
     * @return the created button
     */
    private MyButton createPickupButton(ActionListener action, boolean disabled) {
        return this.createButton(this.languageHandler.get("GAME_ACTION_MENU_PICK_UP"), action, disabled, this.languageHandler.get("GAME_ACTION_MENU_PICK_UP_ERROR"));
    }

    /**
     * Create a simple cancel button for canceling the menu
     * @return the created button
     */
    private MyButton createCancelButton() {
        return this.createButton(this.languageHandler.get("GAME_CANCEL"));
    }


}
