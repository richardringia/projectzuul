package com.ProjectZuul.Handlers;


import java.util.List;
import java.util.ArrayList;
import java.util.*; // TODO: CHANGE

import com.ProjectZuul.Enums.Language;
import com.ProjectZuul.Models.*;

/**
 *
 */
public class RoomHandler {
    private Room vaultRoom;
    private Room mapRoom;
    private Room keysRoom;
    private List<Room> roomList;
    private LanguageHandler languageHandler;
    private Player player;

    public RoomHandler(Player player) {
        this.languageHandler = player.getLanguageHandler();
        this.player = player;
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
        square = new Room(this.languageHandler.get("GAME_ROOMS_SQUARE"), this.languageHandler.get("GAME_ROOMS_SQUARE"), this.player);
        hallway = new Room(this.languageHandler.get("GAME_ROOMS_HALLWAY"), this.languageHandler.get("GAME_ROOMS_HALLWAY_DESC_1"), this.player);
        hallway2 = new Room(this.languageHandler.get("GAME_ROOMS_HALLWAY"), this.languageHandler.get("GAME_ROOMS_HALLWAY_DESC_2"), this.player);
        hallway3 = new Room(this.languageHandler.get("GAME_ROOMS_HALLWAY"), this.languageHandler.get("GAME_ROOMS_HALLWAY_DESC_3"), this.player);
        library = new Room(this.languageHandler.get("GAME_ROOMS_LIBRARY"), this.languageHandler.get("GAME_ROOMS_LIBRARY_DESC"), this.player);
        classRoom666 = new Room(this.languageHandler.get("GAME_ROOMS_CLASS_ROOM_666"), this.languageHandler.get("GAME_ROOMS_CLASS_ROOM_666_DESC"), this.player);
        canteen = new Room(this.languageHandler.get("GAME_ROOMS_CANTEEN"), this.languageHandler.get("GAME_ROOMS_CANTEEN_DESC"), this.player);
        teachersRoom = new Room(this.languageHandler.get("GAME_ROOMS_TEACHERS_ROOM"), this.languageHandler.get("GAME_ROOMS_TEACHERS_ROOM_DESC"), this.player);
        principlesOffice = new Room(this.languageHandler.get("GAME_ROOMS_PRINCIPLES_OFFICE"), this.languageHandler.get("GAME_ROOMS_PRINCIPLES_OFFICE_DESC"), true, this.languageHandler.get("GAME_ITEMS_PRINCIPLES_KEYS"), this.player);
        toilets = new Room(this.languageHandler.get("GAME_ROOMS_TOILETS"), this.languageHandler.get("GAME_ROOMS_TOILETS_DESC"), this.player);
        vaultRoom = new Room(this.languageHandler.get("GAME_ROOMS_VAULT_ROOM"), this.languageHandler.get("GAME_ROOMS_VAULT_ROOM_DESC"), this.player);

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
        hallway3.setExit("north", randomList.get(6));
        hallway3.setExit("east", randomList.get(4));
        hallway3.setExit("west", randomList.get(5));

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