package com.ProjectZuul.Handlers;

import com.ProjectZuul.GUI.ActionMenu;
import com.ProjectZuul.GUI.Components.MyButton;
import com.ProjectZuul.Models.Player;

import java.awt.*;
import java.awt.event.ActionListener;

public class ActionHandler {

    private ActionMenu actionMenu;

    private LanguageHandler languageHandler;

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

    public void createMenu() {
        this.before();
        this.after();
    }

    public void createMenu(ActionListener actionListener, String customButtonText, boolean canDrop) {
        this.before();
        this.actionMenu.add(this.createButton(customButtonText, actionListener, !canDrop, "Item can not be dropped!"));
        this.after();
    }

    public void createMenu(ActionListener actionListener, boolean canOpenVault) {
        this.before();
        this.actionMenu.add(this.createOpenButton(actionListener, !canOpenVault));
        this.after();
    }

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
        return this.createButton(this.languageHandler.get("GAME_ACTION_MENU_OPEN"), action, disabled, "The vault can not be opened. Please find the 'Vault keys'!");
    }

    private MyButton createPickupButton(ActionListener action, boolean disabled) {
        return this.createButton(this.languageHandler.get("GAME_ACTION_MENU_PICK_UP"), action, disabled, "The inventory is full. Please drop some items!");
    }

    private MyButton createCancelButton() {
        return this.createButton(this.languageHandler.get("GAME_CANCEL"));
    }


}
