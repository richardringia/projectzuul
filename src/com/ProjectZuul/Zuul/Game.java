package com.ProjectZuul.Zuul;

import com.ProjectZuul.Models.*;
import com.ProjectZuul.Handlers.*;

import java.util.List;
import java.util.ArrayList;
import java.util.*;
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
    private boolean becomePossessed = false;
    private ArrayList<Room> previousRooms = new ArrayList<>();
    private GameHandler main;

    /**
     * Create the game and initialise its internal map.
     */
    public Game()
    {
        System.out.println("dddddddd");
        main = new GameHandler();
        main.setGameInstance(this);
        roomsList = main.getRoomList();
        currentRoom = roomsList.get(0); 

        itemsList = new ArrayList<>();
        player = new Player();
        itemsList = main.getItemList();
        parser = new Parser();
    }

    /**
     *  com.ProjectZuul.Main play routine.  Loops until end of play.
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
        main.startTimer();
        System.out.println("\n----------------------------------");
        System.out.println(currentRoom.getLongDescription());
        System.out.println("\n----------------------------------\n");
    }

    /**
     * Print out the opening message for the player.
     */
    private void printMenu()
    {
        System.out.println("\n----------------------------------\n");
        System.out.println("Welcome to the World of Zuul!");
        System.out.println("World of Zuul is a new, incredibly boring adventure game.");
        System.out.println("You are currently in the main menu, your options are\n\nplay\nabout\nquit\n");
        System.out.println("----------------------------------\n");
    }

    public void setBecomePossessed(boolean becomePossessed)
    {
        this.becomePossessed = becomePossessed;
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
        becamePossessed();
        
        previousRooms.remove(previousRooms.size() - 1);
        System.out.println("----------------------------------\n");
        System.out.println(currentRoom.getLongDescription());
        System.out.println("\n----------------------------------\n");
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
            becamePossessed();
        
            System.out.println("\n----------------------------------");
            System.out.println(currentRoom.getLongDescription());
            System.out.println("\n----------------------------------\n");
        }
    }

    private void becamePossessed()
    {           
        if (becomePossessed)
        {   
            System.out.println("\n**********************************\n");  
            System.out.println("The ghosts are onto you, a ghost just went inside your body and you feel weird. You will have to hurry up or you will lose control and die!"); 
            System.out.println("\n**********************************\n");
            becomePossessed = false;
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

    public Room getCurrentRoom()
    {
        return currentRoom;
    }

    public void setCurrentRoom(Room currentRoom)
    {
        this.currentRoom = currentRoom;
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

    public List<Room> getRoomsList() {
        return this.roomsList;
    }
}