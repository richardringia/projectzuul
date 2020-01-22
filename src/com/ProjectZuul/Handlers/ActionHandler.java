package com.ProjectZuul.Handlers;

import com.ProjectZuul.GUI.ActionMenu;
import com.ProjectZuul.GUI.Components.MyButton;
import com.ProjectZuul.Models.Item;
import com.ProjectZuul.Models.Player;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class ActionHandler {

    ActionMenu actionMenu;

    public ActionHandler(ActionMenu actionMenu) {
        this.actionMenu = actionMenu;
    }


    public void createMenuWithPickup(ActionListener action, Player player, int itemWeight) {
        this.actionMenu.reset();
        this.actionMenu.add(this.createPickupButton(action, player.isInventoryFull(itemWeight)));
        this.actionMenu.add(this.createCancelButton());
        this.actionMenu.updateUI();
    }

    public void createMenu() {
        this.actionMenu.reset();
        this.actionMenu.add(this.createCancelButton());
        this.actionMenu.updateUI();
    }

    public void createMenuFromVault(ActionListener action, boolean canOpenVault) {
        this.actionMenu.reset();
        this.actionMenu.add(this.createOpenButton(action, !canOpenVault));
        this.actionMenu.add(this.createCancelButton());
        this.actionMenu.updateUI();
    }

    private MyButton createOpenButton(ActionListener action, boolean disabled) {
        MyButton button = this.createButton("Open");
        button.setEnabled(!disabled, "The vault can not be opened. Please find the 'Vault keys'!");
        button.addActionListener(action);
        button.addActionListener(e -> {
            this.actionMenu.reset();
        });
        return button;
    }

    private MyButton createPickupButton(ActionListener action, boolean disabled) {
        MyButton button = this.createButton("Pick up");
        button.setEnabled(!disabled, "The inventory is full. Please drop some items!");
        button.addActionListener(action);
        button.addActionListener(e -> {
            this.actionMenu.reset();
        });
        return button;
    }

    private MyButton createCancelButton() {
        MyButton button = new MyButton("Cancel", Color.BLACK, Color.WHITE, new Dimension(140, 30));
        button.addActionListener(e -> this.actionMenu.reset());
        return button;
    }

    private MyButton createButton(String text) {
        return new MyButton(text, Color.BLACK, Color.WHITE, new Dimension(140, 30));
    }

}
