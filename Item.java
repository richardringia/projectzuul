import java.util.List;
import java.util.Random;

public class Item {
    private String name;
    private boolean canPickup;


    public Item(String name, boolean canPickup) {
        this.name = name;
        this.canPickup = canPickup;
    }

    public void setItemToRandomRoom(List<Room> availebleRooms) {
        this.setItemToRoom(availebleRooms.get(new Random().nextInt(availebleRooms.size())));
    }

    public void setItemToRoom(Room room) {
        room.addItem(this);
    }
}