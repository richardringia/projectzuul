public class Vault extends Item {
    private Item key;
    private Item document;


    public Vault(Item key, Room room){
        super("Vault", false, 0);
        this.key = key;
        document = new Item("Document", true, 0);
        this.setItemToRoom(room);
    }


    public boolean openVault(Item item) {
        return item == key;
    }
 }