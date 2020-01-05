import java.util.List;
import java.util.ArrayList;
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

    private ArrayList<Room> previousRooms = new ArrayList<>();
        
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
        Room square, hallway, hallway2, hallway3, library, classRoom666, canteen, theachersRoom, principlesOffice, toilets, vaultRoom;

        // create the rooms
        square = new Room("Outside the main entrace of the abandoned school");

        hallway = new Room("hallway");
        hallway2 = new Room("hallway2");
        hallway3 = new Room("hallway3");
        library = new Room("library");
        classRoom666 = new Room("classRoom666");
        canteen = new Room("canteen");
        theachersRoom = new Room("theachersRoom");
        principlesOffice = new Room("principlesOffice");
        toilets = new Room("toilets");
        vaultRoom = new Room("vaultRoom");

        
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
        hallway3.setExit("west", theachersRoom);

        library.setExit("west", hallway);
        toilets.setExit("east", hallway);

        classRoom666.setExit("west", hallway2);
        canteen.setExit("east", hallway2);

        vaultRoom.setExit("west", hallway3);
        theachersRoom.setExit("east", hallway3);
        principlesOffice.setExit("south", hallway3);

        this.roomsList.add(square);
        this.roomsList.add(hallway);
        this.roomsList.add(hallway2);
        this.roomsList.add(hallway3);
        this.roomsList.add(library);
        this.roomsList.add(classRoom666);
        this.roomsList.add(canteen);
        this.roomsList.add(theachersRoom);
        this.roomsList.add(principlesOffice);
        this.roomsList.add(toilets);
        this.roomsList.add(vaultRoom);

        currentRoom = square;  // start game outside
    }

    /**
     * Create all the items and add them to the rooms
     */
    private void createItems() {
        Item map, vaultKeys, flashlight, spiritVacuumCleaner, document, bookcase;

        map = new Item("Map", true, 2);
        vaultKeys = new Item("Vault keys", true, 1);
        flashlight = new Item("Flashlight", true, 3);
        spiritVacuumCleaner = new Item("Spirit vacuum cleaner", true, 8);
        document = new Item("Document", true, 0);
        bookcase = new Item("Bookcase", false, 0);

        map.setItemToRoom(this.roomsList.get(4));
        vaultKeys.setItemToRoom(this.roomsList.get(8));
        flashlight.setItemToRandomRoom(this.roomsList);
        spiritVacuumCleaner.setItemToRandomRoom(this.roomsList);
        document.setItemToRoom(this.roomsList.get(10));
        bookcase.setItemToRoom(this.roomsList.get(4));

        this.itemsList.add(map);
        this.itemsList.add(vaultKeys);
        this.itemsList.add(flashlight);
        this.itemsList.add(spiritVacuumCleaner);
        this.itemsList.add(document);
        this.itemsList.add(bookcase);
    }

    /**
     *  Main play routine.  Loops until end of play.
     */
    public void play() 
    {            
        printWelcome();

        // Enter the main command loop.  Here we repeatedly read commands and
        // execute them until the game is over.
                
        boolean finished = false;
        while (! finished) {
            Command command = parser.getCommand();
            finished = processCommand(command);
        }
        System.out.println("Thank you for playing.  Good bye.");
    }

    /**
     * Print out the opening message for the player.
     */
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
     * Given a command, process (that is: execute) the command.
     * @param command The command to be processed.
     * @return true If the command ends the game, false otherwise.
     */
    private boolean processCommand(Command command) 
    {
        boolean wantToQuit = false;

        CommandWord commandWord = command.getCommandWord();

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

            case QUIT:
                wantToQuit = quit(command);
                break;
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
        System.out.println(previousRooms.get(previousRooms.size() - 1).description);
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
            System.out.println(previousRooms.size() + "\n\n\n");
            for (int i = 0; i < previousRooms.size(); i++)
            {
                System.out.println(previousRooms.get(i).description);
            }
        }
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
