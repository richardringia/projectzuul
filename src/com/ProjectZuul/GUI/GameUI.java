package com.ProjectZuul.GUI;


import com.ProjectZuul.Enums.GameMode;
import com.ProjectZuul.Enums.Language;
import com.ProjectZuul.GUI.Components.*;
import com.ProjectZuul.GUI.Fade.FadeController;
import com.ProjectZuul.GUI.Fade.FadePanel;
import com.ProjectZuul.GUI.Listeners.OnQuitListener;
import com.ProjectZuul.GUI.Listeners.SetInactiveListener;
import com.ProjectZuul.Handlers.*;
import com.ProjectZuul.Models.Item;
import com.ProjectZuul.Models.Player;
import com.ProjectZuul.Models.Room;
import com.ProjectZuul.Models.Vault;

import javax.sound.sampled.AudioSystem;
import javax.swing.*;
import javax.swing.plaf.basic.BasicComboBoxUI;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Stack;

public class GameUI implements SetInactiveListener {
    private GameHandler gameHandler;
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
    private MyPanel investigateItemsHolder, investigateNoItemsTextHolder, investigateTooDarkTextholder;

    private MyLabel timeLabel, timeLeftText, gameOver;
    private JPanel map;

    FadePanel gameFinishedPanel;

    ActionHandler actionHandler;
    private MyButton moveCommand, investigateCommand, helpCommand, quitCommand;
    private MyButton north, south, east, west, back;
    private MyButton quitToMenu, quitToDesktop;
    private MyButton lockedRoomDirection;

    private MyTextArea helpPageText;
    private MyTextArea currentRoomText;
    private MyTextArea noItemsText, tooDarkText;

    private MapHandler mapHandler;
    private InventoryHandler inventoryHandler;

    private Stack<Room> previousRoom;

    private GameMode gameMode;

    private LanguageHandler languageHandler;

    private Language language;

    private SoundHandler soundHandler;

    private boolean flashlightFound = false;

    private ArrayList<OnQuitListener> onQuitListeners;

    public GameUI(GUI gui, GameMode gameMode, Language language) {
        this.gui = gui;
        this.gameMode = gameMode;
        this.languageHandler = new LanguageHandler(language);

        this.language = language;

        onQuitListeners = new ArrayList<>();

        soundHandler = new SoundHandler(this);
        soundHandler.playBackgroundSound();

        window = gui.getWindow();

        gameFinishedPanel = new FadePanel(Color.WHITE, 0, 0, 1185, 560, window, 0, true);
        window.add(gameFinishedPanel);

        positionzero = new Rectangle();
        previousRoom = new Stack<>();
        player = new Player(this);

        createGame();
    }

    public void addOnQuitListener(OnQuitListener onQuitListener)
    {
        onQuitListeners.add(onQuitListener);
    }

    public void createGame() {
        gui.setMainMenuVisibility(false);

        gameHandler = new GameHandler(player);
        currentRoomText = new MyTextArea(gameHandler.getCurrentRoom().getLongDescription(), Color.BLACK, Color.WHITE, 100, 100, 400, 200, window);

        createCommandButtons();
        createDirectionButtons();
        createInvestigationView();
        createHelpPage();
        createQuitButtons();
        createMap();
        createInventory();
        createActionMenu();

        setDefaultGameValues();
        setInvestigationItems();

        startTimer();
    }

    private void setDefaultGameValues() {
        commandButtonsHolder.setVisible(true);
        setCurrentSelectedCommandHolder(moveButtonHolder);
        setCurrentSelectedCommand(moveCommand);
        currentRoomText.setVisible(true);
        setDifficultyValues(gameMode);

        window.repaint();
    }

    private void setDifficultyValues(GameMode gameMode)
    {
        switch (gameMode)
        {
            case EASY:
                inventoryHandler.createFlashLight();
                inventoryHandler.createMap();
                duration = 5000;
                break;
            case MEDIUM:
                inventoryHandler.createFlashLight();
                ItemHandler.setMapRoom(gameHandler.getRoomList(), this.languageHandler.get("GAME_ITEMS_MAP"));
                duration = 250000;
                break;
            case PRO:
                ItemHandler.setFlashlightRoom(gameHandler.getRoomList(), this.languageHandler.get("GAME_ITEMS_FLASHLIGHT"));
                ItemHandler.setMapRoom(gameHandler.getRoomList(), this.languageHandler.get("GAME_ITEMS_MAP"));
                duration = 50000;
        }
    }

    private void createCommandButtons() {
        commandButtonsHolder = new MyPanel(Color.BLACK, 20, 20, 500, 40, window);
        commandButtonsHolder.setLayout(new GridLayout(1, 10));
        ((GridLayout) commandButtonsHolder.getLayout()).setHgap(10);

        moveCommand = new MyButton(this.languageHandler.get("GAME_NAVIGATION_MOVE"), Color.GRAY, Color.WHITE, positionzero, commandButtonsHolder);
        investigateCommand = new MyButton(this.languageHandler.get("GAME_NAVIGATION_INVESTIGATE"), Color.BLACK, Color.WHITE, positionzero, commandButtonsHolder);
        helpCommand = new MyButton(this.languageHandler.get("GAME_NAVIGATION_HELP"), Color.BLACK, Color.WHITE, positionzero, commandButtonsHolder);
        quitCommand = new MyButton(this.languageHandler.get("GAME_NAVIGATION_QUIT"), Color.BLACK, Color.WHITE, positionzero, commandButtonsHolder);

        setCommandButtonListeners();
    }

    private void createDirectionButtons() {
        moveButtonHolder = new MyPanel(Color.BLACK, 75, 300, 400, 200, window);
        moveButtonHolder.setLayout(new GridLayout(5, 5));
        for (int y = 0; y < 5; y++) {
            for (int x = 0; x < 5; x++) {
                if (y == 0 && x == 2) {
                    north = new MyButton(this.languageHandler.get("GAME_NAVIGATION_NORTH"), Color.BLACK, Color.WHITE, positionzero);
                    moveButtonHolder.add(north);
                } else if (y == 1 && x == 1) {
                    west = new MyButton(this.languageHandler.get("GAME_NAVIGATION_WEST"), Color.BLACK, Color.WHITE, positionzero);
                    moveButtonHolder.add(west);
                } else if (y == 1 && x == 3) {
                    east = new MyButton(this.languageHandler.get("GAME_NAVIGATION_EAST"), Color.BLACK, Color.WHITE, positionzero);
                    moveButtonHolder.add(east);
                } else if (y == 2 && x == 2) {
                    south = new MyButton(this.languageHandler.get("GAME_NAVIGATION_SOUTH"), Color.BLACK, Color.WHITE, positionzero);
                    moveButtonHolder.add(south);
                } else if (y == 3 && x == 0) {
                    back = new MyButton(this.languageHandler.get("GAME_NAVIGATION_BACK"), Color.BLACK, Color.WHITE, positionzero);
                    moveButtonHolder.add(back);
                } else if (y == 4 && x == 3) {
                    timeLeftText = new MyLabel(this.languageHandler.get("GAME_TIMER_TIME_LEFT") + ":", Color.WHITE, new Font("Arial", Font.PLAIN, 13), moveButtonHolder);
                    timeLeftText.setHorizontalAlignment(SwingConstants.CENTER);
                } else if (y == 4 && x == 4) {
                    timeLabel = new MyLabel("...", Color.WHITE, new Font("Arial", Font.PLAIN, 15), moveButtonHolder);
                    timeLabel.setHorizontalAlignment(SwingConstants.CENTER);
                } else {
                    moveButtonHolder.add(new EmptyComponent());
                }
            }
        }
        setDirectionButtonEnabled();
        setDirectionButtonListeners();
    }

    private void startTimer() {
        timer = new Timer(10, e -> {
            if (startTime < 0) {

                startTime = System.currentTimeMillis();
            }

            long now = System.currentTimeMillis();
            long clockTime = now - startTime;
            if (clockTime >= duration) {
                fadeGameFinishedScreen(false);
                clockTime = duration;
                timer.stop();
            }

            if (clockTime >= duration - 30000) {
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

    private void fadeGameFinishedScreen(boolean victory) {
        FadeController controller = new FadeController(2000);

        timer.stop();
        new java.util.Timer().schedule(
                new java.util.TimerTask() {
                    @Override
                    public void run() {
                        window.getContentPane().removeAll();
                        quitMenuHolder.setVisible(true);
                        quitMenuHolder.setBounds(525, 400, 150, 80);
                        quitMenuHolder.setBackground(Color.WHITE);
                        window.add(quitMenuHolder);
                        window.add(gameFinishedPanel);

                        window.repaint();
                    }
                }, 2000);


        gameOver = new MyLabel(victory ? this.languageHandler.get("GAME_WIN").toUpperCase() : this.languageHandler.get("GAME_LOST").toUpperCase(), Color.RED, new Font("Arial", Font.PLAIN, 30), gameFinishedPanel);
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
        north.setEnabled(gameHandler.getCurrentRoom().getExit("north") != null, this.languageHandler.get("GAME_NO_ROOM"));
        east.setEnabled(gameHandler.getCurrentRoom().getExit("east") != null, this.languageHandler.get("GAME_NO_ROOM"));
        south.setEnabled(gameHandler.getCurrentRoom().getExit("south") != null, this.languageHandler.get("GAME_NO_ROOM"));
        west.setEnabled(gameHandler.getCurrentRoom().getExit("west") != null, this.languageHandler.get("GAME_NO_ROOM"));

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
        Room nextRoom = gameHandler.getCurrentRoom().getExit(direction);

        if (nextRoom.getDoorLocked()) {
            lockedRoomDirection = direction.equals("north") ? north : direction.equals("west") ? west : east;
            lockedRoomDirection.setEnabled(false, this.languageHandler.get("GAME_ROOM_DOOR_LOCK"));

            if (player.getItemNames().contains(nextRoom.getUnlockItem())) {
                nextRoom.setDoorLocked(false);
            } else {
                currentRoomText.setText("\n" + this.languageHandler.get("GAME_ROOM_DOOR_LOCK_1") + " " + nextRoom.getName() + this.languageHandler.get("GAME_ROOM_DOOR_LOCK_2") + "\n\n" +
                        this.languageHandler.get("GAME_ROOM_DOOR_LOCK_3"));
                return;
            }
        }
        if (previousRoom.size() != 0 && gameHandler.getCurrentRoom().getExit(direction) == previousRoom.lastElement()) {
            goBackRoom();
            return;
        }

        previousRoom.push(gameHandler.getCurrentRoom());
        gameHandler.setCurrentRoom(nextRoom);
        currentRoomText.setText(nextRoom.getLongDescription());
        mapHandler.updateRoom(nextRoom);

        setDirectionButtonEnabled();
        setInvestigationItems();
    }

    private void goBackRoom() {
        gameHandler.setCurrentRoom(previousRoom.pop());
        currentRoomText.setText(gameHandler.getCurrentRoom().getLongDescription());
        mapHandler.updateRoom(gameHandler.getCurrentRoom());

        setDirectionButtonEnabled();
        setInvestigationItems();
    }

    private void createInvestigationView() {
        investigateNoItemsTextHolder = new MyPanel(Color.BLACK, 200, 100, 210, 25, window);
        investigateNoItemsTextHolder.setLayout(new GridLayout(1, 1));
        investigateNoItemsTextHolder.setVisible(false);

        noItemsText = new MyTextArea(this.languageHandler.get("GAME_INVESTIGATE_NO_ITEMS"), Color.BLACK, Color.WHITE, 0, 0, 0, 0, investigateNoItemsTextHolder);

        investigateTooDarkTextholder = new MyPanel(Color.BLACK, 100, 100, 400, 100, window);
        investigateTooDarkTextholder.setLayout(new GridLayout(1, 1));
        investigateTooDarkTextholder.setVisible(false);
        tooDarkText = new MyTextArea(this.languageHandler.get("GAME_ROOM_DARK"), Color.BLACK, Color.WHITE, 0, 0, 0, 0, investigateTooDarkTextholder);

        investigateItemsHolder = new MyPanel(Color.BLACK, 225, 200, 150, 215, window);
        investigateItemsHolder.setLayout(new GridLayout(2, 1));
        ((GridLayout) investigateItemsHolder.getLayout()).setVgap(10);
        investigateItemsHolder.setVisible(false);
    }

    public void setInvestigationItems() {
        investigateItemsHolder.removeAll();

        boolean hasFlashlight;
        if (player.getItem(this.languageHandler.get("GAME_ITEMS_FLASHLIGHT")) == null)
        {
            flashlightFound = false;
            hasFlashlight = false;
        }
        else
        {
            hasFlashlight = true;
            flashlightFound = true;
        }

        if (gameHandler.getCurrentRoom().getItems().size() == 0) {
            return;
        }
        investigateItemsHolder.setLayout(new GridLayout(5, 1));
        ((GridLayout) investigateItemsHolder.getLayout()).setVgap(10);

        for (Item item : gameHandler.getCurrentRoom().getItems()) {

            if (!hasFlashlight && !item.getName().equals(this.languageHandler.get("GAME_ITEMS_FLASHLIGHT")))
            {
                System.out.println("PEPEPE");
                continue;
            }
            if (item.getName().equals(this.languageHandler.get("GAME_ITEMS_FLASHLIGHT")))
            {
                flashlightFound = true;
            }

            MyButton itemButton = new MyButton(item.getName(), Color.BLACK, Color.WHITE, positionzero, investigateItemsHolder);
            itemButton.addActionListener(e -> {
                if (item.isCanPickup()) {
                    actionHandler.createMenu(e2 -> {
                        inventoryHandler.addItem(item);
                        investigateItemsHolder.remove(itemButton);
                        if (item.getName().equals(this.languageHandler.get("GAME_ITEMS_FLASHLIGHT")))
                        {
                            setInvestigationItems();
                        }
                        window.repaint();
                    }, this.player, item.getWeight());
                } else if (item instanceof Vault) {
                    Item key = player.getItem(this.languageHandler.get("GAME_ITEMS_VAULT_KEYS"));
                    Vault vault = (Vault) item;
                    actionHandler.createMenu(e2 -> {
                        if (vault.canOpenVault(key)) {
                            this.fadeGameFinishedScreen(true);
                        }
                    }, vault.canOpenVault(key));
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

        helpPageText.setText(this.languageHandler.get("ABOUT_TEXT"));
    }

    private void createQuitButtons() {
        quitMenuHolder = new MyPanel(Color.BLACK, 225, 200, 150, 80, window);
        quitMenuHolder.setLayout(new GridLayout(2, 1));
        ((GridLayout) quitMenuHolder.getLayout()).setVgap(10);
        quitMenuHolder.setVisible(false);

        quitToMenu = new MyButton(this.languageHandler.get("GAME_MENU_MAIN_MENU"), Color.BLACK, Color.WHITE, positionzero, quitMenuHolder);
        quitToDesktop = new MyButton(this.languageHandler.get("GAME_MENU_QUIT_TO_DESKTOP"), Color.BLACK, Color.WHITE, positionzero, quitMenuHolder);

        setQuitButtonListeners();
    }

    private void setCommandButtonListeners() {
        moveCommand.addActionListener(e ->
        {
            commands(moveButtonHolder, moveCommand, true);
        });

        investigateCommand.addActionListener(e ->
        {
            if (!flashlightFound)
            {
                commands(investigateTooDarkTextholder, investigateCommand, false);
            }
            else {
                commands(gameHandler.getCurrentRoom().getItems().size() == 0 ? investigateNoItemsTextHolder : investigateItemsHolder, investigateCommand, false);
            }
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
            //gui.setGameUIVisibility(false);
            //gui.setMainMenuVisibility(true);
            for (OnQuitListener onQuitListener : onQuitListeners)
            {
                onQuitListener.quit();
            }
            timer.stop();
            gui.quitToMainMenu();
        });

        quitToDesktop.addActionListener(e ->
        {
            for (OnQuitListener onQuitListener : onQuitListeners)
            {
                onQuitListener.quit();
            }
            timer.stop();
            gui.quitGame();
        });
    }

    @Override
    public void setMenuVisibility(boolean visibility) {
        if (visibility) {
            setDefaultGameValues();
        }

        commandButtonsHolder.setVisible(visibility);
        currentSelectedCommandHolder.setVisible(visibility);
        inventoryHandler.setMenuVisibility(visibility);
    }

    private void commands(Container holder, MyButton command, boolean selectedMove) {
        setCurrentSelectedCommandHolder(holder);
        setCurrentSelectedCommand(command);

        currentRoomText.setVisible(selectedMove);
        window.repaint();
    }

    private void setCurrentSelectedCommandHolder(Container holder) {
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
        mapHandler = new MapHandler(gameHandler.getRoomList());
        map = mapHandler.getMap();
        mapHandler.updateRoom(gameHandler.getCurrentRoom());
        window.add(map);
    }

    private void createInventory() {
        inventoryHandler = new InventoryHandler(this.window, this.player);
    }

    private void createActionMenu() {
        actionMenu = new ActionMenu(this.player);
        actionHandler = new ActionHandler(this.player, actionMenu);
        window.add(actionMenu);
    }

    public GameHandler getGameHandler() {
        return this.gameHandler;
    }

    public ActionHandler getActionHandler() {
        return this.actionHandler;
    }

    public InventoryHandler getInventoryHandler() {
        return this.inventoryHandler;
    }

    public MyPanel getInvestigateItemsHolder() {
        return this.investigateItemsHolder;
    }

    public MapHandler getMapHandler() {
        return mapHandler;
    }

    public GameMode getGameMode() {
        return gameMode;
    }

    public LanguageHandler getLanguageHandler() {
        return languageHandler;
    }
}
