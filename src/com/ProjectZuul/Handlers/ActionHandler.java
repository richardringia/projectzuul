package com.ProjectZuul.Handlers;

import com.ProjectZuul.GUI.ActionMenu;
import com.ProjectZuul.GUI.Components.MyButton;

import java.awt.*;
import java.awt.event.ActionListener;

public class ActionHandler {

    ActionMenu actionMenu;

    public ActionHandler(ActionMenu actionMenu) {
        this.actionMenu = actionMenu;
    }


    public void createMenuWithPickup(ActionListener action) {
        this.actionMenu.reset();
        this.actionMenu.add(this.createPickupButton(action));
        this.actionMenu.add(this.createCancelButton());
        this.actionMenu.updateUI();
    }

    public void createMenu() {
        this.actionMenu.reset();
        this.actionMenu.add(this.createCancelButton());
        this.actionMenu.updateUI();
    }

    public void createMenuFromVault(ActionListener action) {
        this.actionMenu.reset();
        this.actionMenu.add(this.createOpenButton(action));
        this.actionMenu.add(this.createCancelButton());
        this.actionMenu.updateUI();
    }

    private MyButton createOpenButton(ActionListener action) {
        MyButton button = this.createButton("Open");
        button.addActionListener(action);
        button.addActionListener(e -> {
            this.actionMenu.reset();
        });
        return button;
    }

    private MyButton createPickupButton(ActionListener action) {
        MyButton button = this.createButton("Pick up");
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
