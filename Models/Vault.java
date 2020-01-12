package Models;

public class Vault extends Item {
    private Item key;
    private Item document;


    public Vault(Item key, Room room){
        super("Vault");
        this.key = key;
        document = new Item("Document", 0);
        this.setItemToRoom(room);
    }


    public boolean openVault(Item item) {
        return item == key;
    }
 }