package com.ProjectZuul.GUI;


import com.ProjectZuul.GUI.Components.*;
import com.ProjectZuul.GUI.Listeners.SetInactiveListener;
import com.ProjectZuul.Handlers.InventoryHandler;
import com.ProjectZuul.Handlers.MapHandler;
import com.ProjectZuul.Models.Item;
import com.ProjectZuul.Models.Player;
import com.ProjectZuul.Models.Room;
import com.ProjectZuul.Zuul.Game;

import javax.swing.*;
import java.awt.*;
import java.util.Stack;

public class GameUI implements SetInactiveListener {
    Game game;
    GUI gui;

    JFrame window;
    Rectangle positionzero;

    Container currentSelectedCommandHolder, CurrentSelectedCommand;

    MyPanel commandButtonsHolder, moveButtonHolder, helpTextHolder, quitMenuHolder;
    MyPanel investigateItemsHolder, investigateNoItemsTextHolder;
    JPanel map;

    MyButton moveCommand, investigateCommand, helpCommand, quitCommand;
    MyButton north, south, east, west, back;
    MyButton quitToMenu, quitToDesktop;

    MyTextArea helpPageText;
    MyTextArea currentRoomText;
    MyTextArea noItemsText;

    MapHandler mapHandler;
    InventoryHandler inventoryHandler;

    Stack<Room> previousRoom;

    Player player;

    boolean gameScreenCreated = false;

    public GameUI(GUI gui) {
        this.gui = gui;
        positionzero = new Rectangle();
        previousRoom = new Stack<>();
        player = new Player();
        createGame();
    }

    public void createGame() {
        window = gui.getWindow();
        gui.setMainMenuVisibility(false);

        if (gameScreenCreated) {
            //setDefaultGameValues();
            return;
        }

        game = new Game();
        currentRoomText = new MyTextArea(game.getCurrentRoom().getLongDescription(), Color.BLACK, Color.WHITE, 100, 100, 400, 200, window);

        createCommandButtons();
        createDirectionButtons();
        createInvestigationView();
        setInvestigationItems();
        createHelpPage();
        createQuitButtons();
        setDefaultGameValues();
        createMap();
        createInventory();
    }

    private void setDefaultGameValues() {
        gameScreenCreated = true;
        commandButtonsHolder.setVisible(true);
        setCurrentSelectedCommandHolder(moveButtonHolder);
        setCurrentSelectedCommand(moveCommand);

        window.repaint();
    }

    private void createCommandButtons() {
        commandButtonsHolder = new MyPanel(Color.BLACK, 20, 20, 500, 40, window);
        commandButtonsHolder.setLayout(new GridLayout(1, 10));
        ((GridLayout) commandButtonsHolder.getLayout()).setHgap(10);

        moveCommand = new MyButton("Move", Color.GRAY, Color.WHITE, positionzero, commandButtonsHolder);
        investigateCommand = new MyButton("Investigate", Color.BLACK, Color.WHITE, positionzero, commandButtonsHolder);
        helpCommand = new MyButton("Help", Color.BLACK, Color.WHITE, positionzero, commandButtonsHolder);
        quitCommand = new MyButton("Quit", Color.BLACK, Color.WHITE, positionzero, commandButtonsHolder);

        setCommandButtonListeners();
    }

    private void createDirectionButtons() {
        moveButtonHolder = new MyPanel(Color.BLACK, 75, 300, 300, 150, window);
        moveButtonHolder.setLayout(new GridLayout(4, 4));
        for (int y = 0; y < 4; y++) {
            for (int x = 0; x < 4; x++) {
                if (y == 0 && x == 2) {
                    north = new MyButton("North", Color.BLACK, Color.WHITE, positionzero);
                    moveButtonHolder.add(north);
                } else if (y == 1 && x == 1) {
                    west = new MyButton("West", Color.BLACK, Color.WHITE, positionzero);
                    moveButtonHolder.add(west);
                } else if (y == 1 && x == 3) {
                    east = new MyButton("East", Color.BLACK, Color.WHITE, positionzero);
                    moveButtonHolder.add(east);
                } else if (y == 2 && x == 2) {
                    south = new MyButton("South", Color.BLACK, Color.WHITE, positionzero);
                    moveButtonHolder.add(south);
                } else if (y == 3 && x == 0) {
                    back = new MyButton("Back", Color.BLACK, Color.WHITE, positionzero);
                    moveButtonHolder.add(back);
                } else {
                    moveButtonHolder.add(new EmptyComponent());
                }
            }
        }
        setDirectionButtonEnabled();
        setDirectionButtonListeners();
    }

    /*private void setDirectionButtonsVisibility(MyButton direction, String directionString)
    {
        System.out.println(directionString);
        if (game.getCurrentRoom().getExit(directionString) == null)
        {
            moveButtonHolder.remove(direction);
        }
        else if (direction.getParent() == null)
        {
            moveButtonHolder.add(direction);
        }
        window.repaint();
    }*/

    private void setDirectionButtonEnabled() {
        north.setEnabled(game.getCurrentRoom().getExit("north") != null);
        east.setEnabled(game.getCurrentRoom().getExit("east") != null);
        south.setEnabled(game.getCurrentRoom().getExit("south") != null);
        west.setEnabled(game.getCurrentRoom().getExit("west") != null);

        back.setEnabled(previousRoom.size() != 0);
    }

    private void setDirectionButtonListeners() {
        north.addActionListener(e ->
        {
            goRoom("north");
        });
        east.addActionListener(e ->
        {
            goRoom("east");
        });
        south.addActionListener(e ->
        {
            goRoom("south");
        });
        west.addActionListener(e ->
        {
            goRoom("west");
        });
        back.addActionListener(e ->
        {
            goBackRoom();
        });
    }

    private void goRoom(String direction) {
        if (previousRoom.size() != 0 && game.getCurrentRoom().getExit(direction) == previousRoom.lastElement()) {
            goBackRoom();
            return;
        }

        previousRoom.push(game.getCurrentRoom());
        game.setCurrentRoom(game.getCurrentRoom().getExit(direction));
        currentRoomText.setText(game.getCurrentRoom().getLongDescription());
        mapHandler.updateRoom(game.getCurrentRoom());

        setDirectionButtonEnabled();
        setInvestigationItems();
    }

    private void goBackRoom() {
        game.setCurrentRoom(previousRoom.pop());
        currentRoomText.setText(game.getCurrentRoom().getLongDescription());
        mapHandler.updateRoom(game.getCurrentRoom());

        setDirectionButtonEnabled();
        setInvestigationItems();
    }

    private void createInvestigationView() {
        investigateNoItemsTextHolder = new MyPanel(Color.BLACK, 200, 100, 200, 20, window);
        investigateNoItemsTextHolder.setLayout(new GridLayout(1, 1));
        ((GridLayout) investigateNoItemsTextHolder.getLayout()).setVgap(10);
        investigateNoItemsTextHolder.setVisible(false);

        noItemsText = new MyTextArea("No items found in this location.", Color.BLACK, Color.WHITE, 0, 0, 20, 10, investigateNoItemsTextHolder);

        investigateItemsHolder = new MyPanel(Color.BLACK, 100, 200, 150, 200, window);
        investigateItemsHolder.setLayout(new GridLayout(1, 1));
        investigateItemsHolder.setVisible(false);
    }

    private void setInvestigationItems() {
        investigateItemsHolder.removeAll();
        if (game.getCurrentRoom().getItems().size() == 0) {
            return;
        }
        investigateItemsHolder.setLayout(new GridLayout(5, 1));
        ((GridLayout) investigateItemsHolder.getLayout()).setVgap(10);

        for (Item item : game.getCurrentRoom().getItems()) {
            MyButton itemButton = new MyButton(item.getName(), Color.BLACK, Color.WHITE, positionzero, investigateItemsHolder);
            itemButton.addActionListener(e -> {
                inventoryHandler.addItem(item, game.getCurrentRoom());
                investigateItemsHolder.remove(itemButton);
                window.repaint();
            });
        }
    }

    private void createHelpPage() {
        helpTextHolder = new MyPanel(Color.BLACK, 100, 100, 400, 250, window);
        helpTextHolder.setLayout(new GridLayout(1, 1));
        helpTextHolder.setVisible(false);

        helpPageText = new MyTextArea("", Color.BLACK, Color.WHITE, 0, 0, 0, 0, helpTextHolder);

        helpPageText.setText("DIT IS EEN HELP PAGINA, DEZE GAME IS GEWELDIG MANFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFF" +
                "FFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFF" +
                "FFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFF" +
                "FFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFF" +
                "FFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFF" +
                "FFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFF" +
                "FFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFF" +
                "FFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFF" +
                "FFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFF" +
                "FFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFF" +
                "FFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFF" +
                "FFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFF" +
                "FFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFF" +
                "FFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFF");
    }

    private void createQuitButtons() {
        quitMenuHolder = new MyPanel(Color.BLACK, 225, 200, 150, 80, window);
        quitMenuHolder.setLayout(new GridLayout(2, 1));
        ((GridLayout) quitMenuHolder.getLayout()).setVgap(10);
        quitMenuHolder.setVisible(false);

        quitToMenu = new MyButton("Main Menu", Color.BLACK, Color.WHITE, positionzero, quitMenuHolder);
        quitToDesktop = new MyButton("Quit to Desktop", Color.BLACK, Color.WHITE, positionzero, quitMenuHolder);

        setQuitButtonListeners();
    }

    private void setCommandButtonListeners() {
        moveCommand.addActionListener(e ->
        {
            commands(moveButtonHolder, moveCommand, true);
        });

        investigateCommand.addActionListener(e ->
        {
            commands(game.getCurrentRoom().getItems().size() == 0 ? investigateNoItemsTextHolder : investigateItemsHolder, investigateCommand, false);
        });

        helpCommand.addActionListener(e ->
        {
            commands(helpTextHolder, helpCommand, false);
        });

        quitCommand.addActionListener(e ->
        {
            commands(quitMenuHolder, quitCommand, false);
        });
    }

    private void setQuitButtonListeners() {
        quitToMenu.addActionListener(e ->
        {
            gui.setGameUIVisibility(false);
            gui.setMainMenuVisibility(true);
        });

        quitToDesktop.addActionListener(e -> gui.quitGame());
    }

    @Override
    public void setMenuVisibility(boolean visibility) {
        commandButtonsHolder.setVisible(visibility);
        quitMenuHolder.setVisible(visibility);
        mapHandler.setMenuVisibility(visibility);
        inventoryHandler.setMenuVisibility(visibility);
    }

    private void commands(Container holder, MyButton command, boolean selectedMove) {
        setCurrentSelectedCommandHolder(holder);
        setCurrentSelectedCommand(command);

        currentRoomText.setVisible(selectedMove);
    }

    private void setCurrentSelectedCommandHolder(Container holder) {
        if (currentSelectedCommandHolder != null)
            currentSelectedCommandHolder.setVisible(false);
        currentSelectedCommandHolder = holder;
        currentSelectedCommandHolder.setVisible(true);
    }

    private void setCurrentSelectedCommand(MyButton command) {
//        command.setBackground();
        if (CurrentSelectedCommand != null)
            CurrentSelectedCommand.setBackground(Color.BLACK);
        CurrentSelectedCommand = command;
        CurrentSelectedCommand.setBackground(Color.GRAY);
    }

    private void createMap() {
        mapHandler = new MapHandler(game.getRoomsList());
        map = mapHandler.getMap();
        mapHandler.updateRoom(game.getCurrentRoom());
        window.add(map);
    }

    private void createInventory() {
        inventoryHandler = new InventoryHandler(this.window, this.player);
    }
}
