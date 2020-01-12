package Models;

import java.util.List;
import java.util.ArrayList;

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

    private int getTotalWeight() {
        int weight = 0;
        for (Item item: this.itemList) {
            weight += item.getWeight();
        }
        return weight;
    }

}