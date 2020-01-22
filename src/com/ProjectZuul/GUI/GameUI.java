package com.ProjectZuul.GUI;


import com.ProjectZuul.GUI.Components.*;
import com.ProjectZuul.GUI.Fade.FadeController;
import com.ProjectZuul.GUI.Fade.FadePanel;
import com.ProjectZuul.GUI.Listeners.SetInactiveListener;
import com.ProjectZuul.Handlers.ActionHandler;
import com.ProjectZuul.Handlers.InventoryHandler;
import com.ProjectZuul.Handlers.MapHandler;
import com.ProjectZuul.Models.Item;
import com.ProjectZuul.Models.Player;
import com.ProjectZuul.Models.Room;
import com.ProjectZuul.Models.Vault;
import com.ProjectZuul.Zuul.Game;

import javax.swing.*;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.Stack;

public class GameUI implements SetInactiveListener
{
    private Game game;
    private GUI gui;

    private Timer timer;
    private long startTime = -1;
    private long duration = 500000;

    private Player player;

    private JFrame window;
    private Rectangle positionzero;

    ActionMenu actionMenu;
    private Container currentSelectedCommandHolder, CurrentSelectedCommand;

    private MyPanel commandButtonsHolder, moveButtonHolder, helpTextHolder, quitMenuHolder;
    private MyPanel investigateItemsHolder, investigateNoItemsTextHolder;

    private MyLabel timeLabel, timeLeftText, gameOver;
    private JPanel map;

    FadePanel gameFinishedPanel;

    ActionHandler actionHandler;
    private MyButton moveCommand, investigateCommand, helpCommand, quitCommand;
    private MyButton north, south, east, west, back;
    private MyButton quitToMenu, quitToDesktop;

    private MyTextArea helpPageText;
    private MyTextArea currentRoomText;
    private MyTextArea noItemsText;

    private MapHandler mapHandler;
    private InventoryHandler inventoryHandler;

    private Stack<Room> previousRoom;

    public GameUI(GUI gui)
    {
        this.gui = gui;

        window = gui.getWindow();

        gameFinishedPanel = new FadePanel(Color.WHITE, 0, 0, 1185, 560, window, 0, true);
        window.add(gameFinishedPanel);

        positionzero = new Rectangle();
        previousRoom = new Stack<>();
        player = new Player();

        createGame();
    }

    public void createGame() {
        gui.setMainMenuVisibility(false);

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
        createActionMenu();
        startTimer();
    }

    private void setDefaultGameValues()
    {
        commandButtonsHolder.setVisible(true);
        setCurrentSelectedCommandHolder(moveButtonHolder);
        setCurrentSelectedCommand(moveCommand);
        currentRoomText.setVisible(true);

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

    private void createDirectionButtons()
    {
        moveButtonHolder = new MyPanel(Color.BLACK, 75, 300, 400, 200, window);
        moveButtonHolder.setLayout(new GridLayout(5, 5));
        for (int y = 0; y < 5; y++) {
            for (int x = 0; x < 5; x++) {
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
                }
                else if (y == 4 && x == 3)
                {
                    timeLeftText = new MyLabel("Time Left:", Color.WHITE, new Font("Arial", Font.PLAIN, 13), moveButtonHolder);
                    timeLeftText.setHorizontalAlignment(SwingConstants.CENTER);
                }
                else if (y == 4 && x == 4) {
                    timeLabel = new MyLabel("...", Color.WHITE, new Font("Arial", Font.PLAIN, 15), moveButtonHolder);
                    timeLabel.setHorizontalAlignment(SwingConstants.CENTER);
                }
                else {
                    moveButtonHolder.add(new EmptyComponent());
                }
            }
        }
        setDirectionButtonEnabled();
        setDirectionButtonListeners();
    }

    private void startTimer()
    {
        timer = new Timer(10, e -> {
            if (startTime < 0) {

                startTime = System.currentTimeMillis();
            }

            long now = System.currentTimeMillis();
            long clockTime = now - startTime;
            if (clockTime >= duration)
            {
                fadeGameFinishedScreen(false);
                clockTime = duration;
                timer.stop();
            }

            if (clockTime >= duration - 30000)
            {
                timeLeftText.setForeground(Color.RED);
                timeLabel.setForeground(Color.RED);
            }

            SimpleDateFormat df = new SimpleDateFormat("mm:ss");
            timeLabel.setText(df.format(duration - clockTime));
        });
        timer.setInitialDelay(0);
        if (!timer.isRunning()) {
            startTime = -1;
            timer.start();
        }
    }

    private void fadeGameFinishedScreen(boolean victory)
    {
        FadeController controller = new FadeController(2000);

        new java.util.Timer().schedule(
                new java.util.TimerTask() {
                    @Override
                    public void run() {
                        window.getContentPane().removeAll();
                        window.add(gameFinishedPanel);
                        window.repaint();
                    }
                },2000);


        gameOver = new MyLabel(victory ? "YOU WON" : "GAME OVER", Color.RED, new Font("Arial", Font.PLAIN, 30), gameFinishedPanel);
        gameOver.setBounds(0, 0, 1185, 560);
        gameOver.setHorizontalAlignment(SwingConstants.CENTER);
        gameOver.setVerticalAlignment(SwingConstants.CENTER);

        controller.add(gameFinishedPanel);
        controller.start();
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
                if (item.isCanPickup()) {
                    actionHandler.createMenuWithPickup(e2 -> {
                        inventoryHandler.addItem(item, game.getCurrentRoom());
                        investigateItemsHolder.remove(itemButton);
                        window.repaint();
                    }, this.player, item.getWeight());
                } else if (item instanceof Vault) {
                    Item key = player.getItem("Vault keys");
                    actionHandler.createMenuFromVault(e2 -> {
                        // YOU WIN
                        Vault vault = (Vault) item;
                        if (vault.openVault(key)) {
                            this.fadeGameFinishedScreen(true);
                        }
                    });
                } else {
                    actionHandler.createMenu();
                }
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
    public void setMenuVisibility(boolean visibility)
    {
        if (visibility)
        {
            setDefaultGameValues();
        }

        commandButtonsHolder.setVisible(visibility);
        currentSelectedCommandHolder.setVisible(visibility);
        mapHandler.setMenuVisibility(visibility);
        inventoryHandler.setMenuVisibility(visibility);
    }

    private void commands(Container holder, MyButton command, boolean selectedMove) {
        setCurrentSelectedCommandHolder(holder);
        setCurrentSelectedCommand(command);

        currentRoomText.setVisible(selectedMove);
        window.repaint();
    }

    private void setCurrentSelectedCommandHolder(Container holder)
    {
        if (currentSelectedCommandHolder != null) {
            currentSelectedCommandHolder.setVisible(false);
        }
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

    private void createActionMenu() {
        actionMenu = new ActionMenu();
        actionHandler = new ActionHandler(actionMenu);
        window.add(actionMenu);
    }
}
