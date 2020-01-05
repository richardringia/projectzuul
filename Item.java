import java.util.List;
import java.util.Random;

public class Item {
    private String name;
    private boolean canPickup;
    private int weight;


    public Item(String name, boolean canPickup, int weight) {
        this.name = name;
        this.canPickup = canPickup;
        this.weight = weight;
    }

    public void setItemToRandomRoom(List<Room> availebleRooms) {
        this.setItemToRoom(availebleRooms.get(new Random().nextInt(availebleRooms.size())));
    }

    public void setItemToRoom(Room room) {
        room.addItem(this);
    }
    
    public int getWeight() {
        return this.weight;
    }

    public String getName() {
        return this.name;
    }

    public boolean isCanPickup() {
        return this.canPickup;
    }
}