import java.util.List;
import java.util.ArrayList;
import Models.*;
/**
 *  This class is the main class of the "World of Zuul" application. 
 *  "World of Zuul" is a very simple, text based adventure game.  Users 
 *  can walk around some scenery. That's all. It should really be extended 
 *  to make it more interesting!
 * 
 *  To play this game, create an instance of this class and call the "play"
 *  method.
 * 
 *  This main class creates and initialises all the others: it creates all
 *  rooms, creates the parser and starts the game.  It also evaluates and
 *  executes the commands that the parser returns.
 * 
 * @author  Michael Kölling and David J. Barnes
 * @version 2016.02.29
 */

public class Game 
{
    private Parser parser;
    private Room currentRoom;
    private List<Room> roomsList;
    private List<Item> itemsList;
    private Player player;
    private boolean isMenu = true;
    private ArrayList<Room> previousRooms = new ArrayList<>();
        
    public static void main(String[] args) {
        Game game = new Game();
        game.play();
    }
    
    /**
     * Create the game and initialise its internal map.
     */
    public Game() 
    {
        roomsList = new ArrayList<>();
        itemsList = new ArrayList<>();
        player = new Player();
        createRooms();
        createItems();
        parser = new Parser();
    }

    /**
     * Create all the rooms and link their exits together.
     */
    private void createRooms()
    {
        Room square, hallway, hallway2, hallway3, library, classRoom666, canteen, teachersRoom, principlesOffice, toilets, vaultRoom;

        // create the rooms
        square = new Room("You are outside of an abandoned school building, inside are some important documents which are very important. Retrieve the documents and get a big reward. " +
                "Don't get killed on the way, good luck!");

        hallway = new Room("You are in the hallway, from here you can move in different directions to try and find rooms which are important to your success.");
        hallway2 = new Room("You find yourself in another hallway.");
        hallway3 = new Room("You find yourself in the third hallway, seems like there are some important rooms nearby.");
        library = new Room("You are in the library, of course this means there are books around, maybe there is something important in there?");
        classRoom666 = new Room("You find yourself in the classroom 666, sounds like a classroom which could be haunted, what is that sound?");
        canteen = new Room("You are in a canteen, ghosts don't need food so they probably aren'there, or are they?");
        teachersRoom = new Room("Welcome to the teachers room, if any teachers died and came back as ghosts, they'll probably be here.");
        principlesOffice = new Room("The principles office always holds something important, try looking around to see what you can find.");
        toilets = new Room("You are at the toilets, ghosts often haunt a toilet because people are alone in here, then again this entire building is abandoned... ");
        vaultRoom = new Room("The vault room, could this be where you need to be, maybe investigate to see if can find something.");

        
        // initialise room exits
        square.setExit("north", hallway);

        hallway.setExit("north", hallway2);
        hallway.setExit("east", library);
        hallway.setExit("south", square);
        hallway.setExit("west", toilets);

        hallway2.setExit("north", hallway3);
        hallway2.setExit("east", classRoom666);
        hallway2.setExit("south", hallway);
        hallway2.setExit("west", canteen);

        hallway3.setExit("north", principlesOffice);
        hallway3.setExit("east", vaultRoom);
        hallway3.setExit("south", hallway2);
        hallway3.setExit("west", teachersRoom);

        library.setExit("west", hallway);
        toilets.setExit("east", hallway);

        classRoom666.setExit("west", hallway2);
        canteen.setExit("east", hallway2);

        vaultRoom.setExit("west", hallway3);
        teachersRoom.setExit("east", hallway3);
        principlesOffice.setExit("south", hallway3);

        this.roomsList.add(square);
        this.roomsList.add(hallway);
        this.roomsList.add(hallway2);
        this.roomsList.add(hallway3);
        this.roomsList.add(library);
        this.roomsList.add(classRoom666);
        this.roomsList.add(canteen);
        this.roomsList.add(teachersRoom);
        this.roomsList.add(principlesOffice);
        this.roomsList.add(toilets);
        this.roomsList.add(vaultRoom);

        currentRoom = square;  // start game outside
    }

    /**
     * Create all the items and add them to the rooms
     */
    private void createItems() {
        Item map, vaultKeys, flashlight, spiritVacuumCleaner, document, bookcase, vault;

        map = new Item("Map", true, 2);
        vaultKeys = new Item("Vault keys", true, 1);
        flashlight = new Item("Flashlight", true, 3);
        spiritVacuumCleaner = new Item("Spirit vacuum cleaner", true, 8);
        bookcase = new Item("Bookcase", false, 0);
        vault = new Vault(vaultKeys, this.roomsList.get(10));

        map.setItemToRoom(this.roomsList.get(4));
        vaultKeys.setItemToRoom(this.roomsList.get(8));
        flashlight.setItemToRandomRoom(this.roomsList);
        spiritVacuumCleaner.setItemToRandomRoom(this.roomsList);
        bookcase.setItemToRoom(this.roomsList.get(4));

        this.itemsList.add(map);
        this.itemsList.add(vaultKeys);
        this.itemsList.add(flashlight);
        this.itemsList.add(spiritVacuumCleaner);
        this.itemsList.add(bookcase);
        this.itemsList.add(vault);
    }
    
    /**
     *  Main play routine.  Loops until end of play.
     */
    public void play() 
    {            
        printMenu();

        // Enter the main command loop.  Here we repeatedly read commands and
        // execute them until the game is over.
                
        boolean finished = false;
        while (!finished) {
            Command command = parser.getCommand();
            finished = processCommand(command);
        }
        System.out.println("Thank you for playing.  Good bye.");
    }

    private void printWelcome()
    {
        System.out.println();
        System.out.println("Welcome to the World of Zuul!");
        System.out.println("World of Zuul is a new, incredibly boring adventure game.");
        System.out.println("Type '" + CommandWord.HELP + "' if you need help.");
        System.out.println();
        System.out.println(currentRoom.getLongDescription());
    }
    
    /**
     * Print out the opening message for the player.
     */
    private void printMenu()
    {
        System.out.println();
        System.out.println("Welcome to the World of Zuul!");
        System.out.println("World of Zuul is a new, incredibly boring adventure game.");
        System.out.println("You are currently in the main menu, your options are play, about and quit. Select what you want to do by typing the word.");
    }

    /**
     * Given a command, process (that is: execute) the command.
     * @param command The command to be processed.
     * @return true If the command ends the game, false otherwise.
     */
    private boolean processCommand(Command command) 
    {
        boolean wantToQuit = false;

        CommandWord commandWord = command.getCommandWord();
        if (isMenu)
        {
            switch (commandWord)
            {
                 case UNKNOWN:
                    System.out.println("I don't know what you mean...");
                    break;
    
                case ABOUT:
                    System.out.println("Wij zijn 2 student van de Hanzehogeschool Groningen.");
                    break;
                    
                case PLAY:
                    printWelcome();
                    isMenu = false;
                    break;
                    
                case QUIT:
                    wantToQuit = quit(command);
                    break;   
            }
        }
        else
        {
            switch (commandWord) {
                case UNKNOWN:
                    System.out.println("I don't know what you mean...");
                    break;
                case BACK:
                    goBack();
                    break;  
                case HELP:
                    printHelp();
                    break;
   
                case GO:
                    goRoom(command);
                    break;
                case INVESTIGATE:
                    goInvestigate();
                    break;
                case PICKUP:
                    goPickup(command);
                    break;
                case OPEN:
                    wantToQuit = goOpen(command);
                    break;
                case QUIT:
                    wantToQuit = quit(command);
                    break;
            }
        }
        return wantToQuit;
    }

    // implementations of user commands:

    /**
     * Print out some help information.
     * Here we print some stupid, cryptic message and a list of the 
     * command words.
     */
    private void printHelp() 
    {
        System.out.println("You are lost. You are alone. You wander");
        System.out.println("around at the university.");
        System.out.println();
        System.out.println("Your command words are:");
        parser.showCommands();
    }

    private void goBack()
    {
        if (previousRooms.size() == 0)
        {            
            System.out.println("There is no room to go back to!");
            return;
        }
        currentRoom = previousRooms.get(previousRooms.size() - 1);

        previousRooms.remove(previousRooms.size() - 1);
        System.out.println(currentRoom.getLongDescription());
    }
    /** 
     * Try to go in one direction. If there is an exit, enter the new
     * room, otherwise print an error message.
     */
    private void goRoom(Command command) 
    {
        if(!command.hasSecondWord()) {
            // if there is no second word, we don't know where to go...
            System.out.println("Go where?");
            return;
        }

        String direction = command.getSecondWord();

        // Try to leave current room.
        Room nextRoom = currentRoom.getExit(direction);

        if (nextRoom == null) {
            System.out.println("There is no door!");
        }
        else {
            previousRooms.add(currentRoom);
            currentRoom = nextRoom;
            System.out.println(currentRoom.getLongDescription());
        }
    }

    private void goInvestigate() {
        if (currentRoom.getItems().size() > 0) {
            System.out.println("Items in this room:");
            for(Item item: currentRoom.getItems()) {
                System.out.println(item.getName());
            }
        } else {
            System.out.println("There are no items in this room!");
        }

    }

    private void goPickup(Command command) {
        if(!command.hasSecondWord()) {
            System.out.println("What do you want to pick up?");
            return;
        }

        String itemString = command.getSecondWord();

        Item item = currentRoom.getItem(itemString);

        if (item != null) {
            if (item.isCanPickup()) {
                if (player.addItem(item)) {
                    System.out.println("Item add to the bag!");
                    currentRoom.removeItem(item);
                } else {
                    System.out.println("Item not added to bag, bag is full!");
                }
            } else {
                System.out.println("Cannot pickup this item!");
            }
        } else {
            System.out.println("Item not find. To find out which items are in the room. Run command: investigate");
        }
    }

    private boolean goOpen(Command command) {
        if(!command.hasSecondWord()) {
            System.out.println("What do you want to open?");
            return false;
        }

        String itemString = command.getSecondWord();

        Item item = currentRoom.getItem(itemString);

        switch (itemString) {
            case "Vault":
                return openVault();
            default:
                System.out.println("Nothing to open here!");
                break;
        }
        return false;
    }

    private boolean openVault() {
        Item key = player.getItem("Vault keys");
        if (key != null) {
            Item item = currentRoom.getItem("Vault");
            if (item != null) {
                Vault vault = (Vault)item;
                if (vault.openVault(key)) {
                    System.out.println("You won the game!");
                    return true;
                }
            } else {
                System.out.println("No vault in this room!");
            }
        } else {
            System.out.println("You haven't find the vault keys yet. Please search!");
        }
        return false;
    }

    /** 
     * "Quit" was entered. Check the rest of the command to see
     * whether we really quit the game.
     * @return true, if this command quits the game, false otherwise.
     */
    private boolean quit(Command command) 
    {
        if(command.hasSecondWord()) {
            System.out.println("Quit what?");
            return false;
        }
        else {
            return true;  // signal that we want to quit
        }
    }
}
