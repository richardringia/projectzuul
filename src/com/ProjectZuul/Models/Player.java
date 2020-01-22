package com.ProjectZuul.Models;

import com.ProjectZuul.Handlers.InventoryHandler;

import javax.print.DocFlavor;
import java.util.List;
import java.util.ArrayList;

/**
 * In this class the player will be created.
 * With the player class items can be stored.
 */
public class Player {
    private int maxWeight = 10;
    private List<Item> itemList;

    public Player() {
        this.itemList = new ArrayList<>();
    }

    public boolean addItem(Item item) {
        if (this.getTotalWeight() <= this.maxWeight) {
            itemList.add(item);
            return true;
        } else {
            return false;
        }
    }

    public Item getItem(String name) {
        return this.itemList.stream()
                .filter(item -> name.equals(item.getName()))
                .findAny()
                .orElse(null);
    }

    public ArrayList<String> getItemNames()
    {
        ArrayList<String> itemNames = new ArrayList<>();
        for (Item item : itemList)
        {
            itemNames.add(item.getName());
        }

        return itemNames;
    }

    public int getTotalWeight() {
        int weight = 0;
        for (Item item: this.itemList) {
            weight += item.getWeight();
        }
        return weight;
    }

    public int getMaxWeight() {
        return maxWeight;
    }


    public boolean isInventoryFull(int itemWeight) {
        return (this.getTotalWeight() + itemWeight) > this.maxWeight;
    }
}