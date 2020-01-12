package Handlers;

import java.util.List;
import java.util.ArrayList;
import java.util.*; // TODO: CHANGE

import Models.*;

/**
 *
 */
public class RoomHandler {
    private Room vaultRoom;
    private Room mapRoom;
    private Room keysRoom;
    private List<Room> roomList;

    public RoomHandler() {
        this.roomList = this.createRooms();
    }

    public Room getVaultRoom() {
        return this.vaultRoom;
    }

    public Room getMapRoom() {
        return this.mapRoom;
    }

    public Room getKeysRoom() {
        return this.keysRoom;
    }

    public List<Room> getRoomList() {
        return this.roomList;
    }

    private List<Room> createRooms() {
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

        List<Room> randomList = new ArrayList<>();
        randomList.add(library);
        randomList.add(classRoom666);
        randomList.add(canteen);
        randomList.add(teachersRoom);
        randomList.add(principlesOffice);
        randomList.add(toilets);
        randomList.add(vaultRoom);

        Collections.shuffle(randomList);
        // initialise room exits
        square.setExit("north", hallway);


        hallway.setExit("south", square);
        hallway.setExit("north", hallway2);
        hallway.setExit("east", randomList.get(0));
        hallway.setExit("west", randomList.get(1));

        hallway2.setExit("south", hallway);
        hallway2.setExit("north", hallway3);
        hallway2.setExit("east", randomList.get(2));
        hallway2.setExit("west", randomList.get(3));

        hallway3.setExit("south", hallway2);
        hallway3.setExit("north", randomList.get(4));
        hallway3.setExit("east", randomList.get(5));
        hallway3.setExit("west", randomList.get(6));

        randomList.get(0).setExit("west", hallway);
        randomList.get(1).setExit("east", hallway);

        randomList.get(2).setExit("west", hallway2);
        randomList.get(3).setExit("east", hallway2);

        randomList.get(4).setExit("west", hallway3);
        randomList.get(5).setExit("east", hallway3);
        randomList.get(6).setExit("south", hallway3);

        this.vaultRoom = vaultRoom;
        this.mapRoom = library;
        this.keysRoom = principlesOffice;

        return new ArrayList<>(Arrays.asList(square, hallway, hallway2, hallway3, library, classRoom666, canteen, teachersRoom, principlesOffice, toilets, vaultRoom));
    }


}