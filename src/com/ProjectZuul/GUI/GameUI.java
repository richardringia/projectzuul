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
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Stack;

/**
 * The type Game ui.
 *
 * @author Anne Pier Merkus and Richard Ringia
 */
public class GameUI implements SetInactiveListener {
    /**
     * Instance of GameHandler which contains all rooms, items and the current location of the player.
     */
    private GameHandler gameHandler;

    /**
     * Instance of the com.ProjectZuul.GUI.GUI.
     */
    private GUI gui;

    /**
     * Timer to keep track of how long the game has been running for and when time is up and the player has lost.
     */
    private Timer timer;

    /**
     * startTime takes the current time difference between the current time and midnight January 1, 1970 UTC.
     * Comparison between the startTime and current time is used to keep track of the timer.
     */
    private long startTime = -1;

    /**
     * Duration of the game before the player dies.
     */
    private long duration = 500000;

    /**
     * Instance of the current Player.
     */
    private Player player;

    /**
     * The window of the application.
     */
    private MyFrame window;

    /**
     * Rectangle on 0, 0, 0, 0 to easily pass values on incase they should be 0.
     */
    private Rectangle positionzero;

    /**
     * Instance of the ActionMenu.
     */
    private ActionMenu actionMenu;

    /**
     * Container which gets the current opened command panel inside so we can deactivate it once another one is selected.
     */
    private Container currentSelectedCommandHolder;

    /**
     * Container which holds the current selected command button, so we can change the background color once another one is selected.
     */
    private Container CurrentSelectedCommand;

    /**
     * Panel which holds all command buttons at the top of the screen.
     */
    private MyPanel commandButtonsHolder;

    /**
     * Holder for the buttons with which the player can move, also holds the timer.
     */
    private MyPanel moveButtonHolder;

    /**
     * Holder for the quit buttons.
     */
    private MyPanel quitMenuHolder;

    /**
     * Holder for the items in the room when investigating.
     */
    private MyPanel investigateItemsHolder;

    /**
     * Holder for the text when no items are found in the room.
     */
    private MyPanel investigateNoItemsTextHolder;

    /**
     * Holder for the text when the player does not have a flashlight and tries to investigate a room.
     */
    private MyPanel investigateTooDarkTextHolder;

    /**
     * Label for the Time Left text to the left of the timer.
     */
    private MyLabel timeLabel;

    /**
     * Text that shows the time left.
     */
    private MyLabel timeLeftText;

    /**
     * Text which shows at the end of the game, can be set to victory too.
     */
    private MyLabel gameOver;

    /**
     * The panel that holds the map.
     */
    private JPanel map;

    /**
     * The Game finished panel.
     */
    FadePanel gameFinishedPanel;

    /**
     * The Action handler.
     */
    ActionHandler actionHandler;

    /**
     * Button to show move buttons and text of the current room..
     */
    private MyButton moveCommand;

    /**
     * Button to show all items in the current room.
     */
    private MyButton investigateCommand;

    /**
     * When pressed, opens a webpage so the player can go through the Quick-Start-Guide.
     */
    private MyButton helpCommand;

    /**
     * Button to show the quit buttons so the player can go back to the Main Menu or close the game entirely.
     */
    private MyButton quitCommand;

    /**
     * Move towards the room to the north when pressed, the button is disabled when there is no room in that direction.
     */
    private MyButton north;

    /**
     * Move towards the room to the south when pressed, the button is disabled when there is no room in that direction.
     */
    private MyButton south;

    /**
     * Move towards the room to the east when pressed, the button is disabled when there is no room in that direction.
     */
    private MyButton east;

    /**
     * Move towards the room to the west when pressed, the button is disabled when there is no room in that direction.
     */
    private MyButton west;

    /**
     * Go back to the room you came from, the button is disabled if this is the first room.
     */
    private MyButton back;

    /**
     * Quits the game and goes back to the main menu.
     */
    private MyButton quitToMenu;

    /**
     * Quits the game and closes the window.
     */
    private MyButton quitToDesktop;

    /**
     * When a door is locked, the button going to that direction will be saved in this variable.
     */
    private MyButton lockedRoomDirection;

    /**
     * Text for the room the player is currently in, updated every time the player changes to another room.
     */
    private MyTextArea currentRoomText;

    /**
     * Text shown when there are no items in the room.
     */
    private MyTextArea noItemsText;

    /**
     * Text shown when the player has no flashlight and is trying to investigate.
     */
    private MyTextArea tooDarkText;

    /**
     * Instance of the MapHandler.
     */
    private MapHandler mapHandler;

    /**
     * Instance of the InventoryHandler.
     */
    private InventoryHandler inventoryHandler;

    /**
     * Stack with rooms previously visited so we can click the back button and go back to the previous rooms.
     * Manually going back to a previous room also pops the stack.
     */
    private Stack<Room> previousRoom;

    /**
     * The difficulty selected by the player.
     */
    private GameMode gameMode;

    /**
     * Instance of LanguageHandler with the selected language so we can get the values in the correct language.
     */
    private LanguageHandler languageHandler;

    /**
     * The language selected by the player.
     */
    private Language language;

    /**
     * Instance of com.ProjectZuul.Handlers.SoundHandler so we can play start playing background music.
     */
    private SoundHandler soundHandler;

    /**
     * Whether the player has found the flashlight, can be in his inventory or on the floor.
     */
    private boolean flashlightFound = false;

    /**
     * Listeners for code that needs to be executed when quit is invoked.
     */
    private ArrayList<OnQuitListener> onQuitListeners;

    /**
     * Instantiates a new Game ui.
     *
     * @param gui      the gui
     * @param gameMode the game mode
     * @param language the language
     */
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

    /**
     * Add on quit listeners for processes to be ended when the game is finished.
     *
     * @param onQuitListener Code given to be executed when the player quits the game.
     */
    public void addOnQuitListener(OnQuitListener onQuitListener)
    {
        onQuitListeners.add(onQuitListener);
    }

    /**
     * Create the game by calling GameHandler and setting all values by calling other methods.
     */
    public void createGame() {
        gui.setMainMenuVisibility(false);

        gameHandler = new GameHandler(player);
        currentRoomText = new MyTextArea(gameHandler.getCurrentRoom().getLongDescription(), Color.BLACK, Color.WHITE, 100, 100, 400, 200, window);

        createCommandButtons();
        createDirectionButtons();
        createInvestigationView();
        createQuitButtons();
        createMap();
        createInventory();
        createActionMenu();

        setDefaultGameValues();
        setInvestigationItems();

        startTimer();
    }

    /**
     * Default values of the game, the selected command buttons and text of the current room.
     */
    private void setDefaultGameValues() {
        commandButtonsHolder.setVisible(true);
        setCurrentSelectedCommandHolder(moveButtonHolder);
        setCurrentSelectedCommand(moveCommand);
        currentRoomText.setVisible(true);
        setDifficultyValues(gameMode);

        window.repaint();
    }

    /**
     * Set players items and put items in rooms depending on the difficulty chosen.
     *
     * @param gameMode Difficulty selected by the player
     */
    private void setDifficultyValues(GameMode gameMode)
    {
        switch (gameMode)
        {
            case EASY:
                inventoryHandler.createFlashLight();
                inventoryHandler.createMap();
                duration = 300000;
                break;
            case MEDIUM:
                inventoryHandler.createFlashLight();
                ItemHandler.setMapRoom(gameHandler.getRoomList(), this.languageHandler.get("GAME_ITEMS_MAP"));
                duration = 240000;
                break;
            case PRO:
                ItemHandler.setFlashlightRoom(gameHandler.getRoomList(), this.languageHandler.get("GAME_ITEMS_FLASHLIGHT"));
                ItemHandler.setMapRoom(gameHandler.getRoomList(), this.languageHandler.get("GAME_ITEMS_MAP"));
                duration = 150000;
        }
    }

    /**
     * Create the command buttons at the top of the screen.
     */
    private void createCommandButtons() {
        commandButtonsHolder = new MyPanel(Color.BLACK, 20, 20, 500, 40, window);
        commandButtonsHolder.setLayout(new GridLayout(1, 10));
        ((GridLayout) commandButtonsHolder.getLayout()).setHgap(10);

        moveCommand = new MyButton(this.languageHandler.get("GAME_NAVIGATION_MOVE"), Color.GRAY, Color.WHITE, positionzero, commandButtonsHolder);
        investigateCommand = new MyButton(this.languageHandler.get("GAME_NAVIGATION_INVESTIGATE"), Color.BLACK, Color.WHITE, positionzero, commandButtonsHolder);
        if (Desktop.isDesktopSupported() && Desktop.getDesktop().isSupported(Desktop.Action.BROWSE)) {
            helpCommand = new MyButton(this.languageHandler.get("GAME_NAVIGATION_HELP"), Color.BLACK, Color.WHITE, positionzero, commandButtonsHolder);
        }
        quitCommand = new MyButton(this.languageHandler.get("GAME_NAVIGATION_QUIT"), Color.BLACK, Color.WHITE, positionzero, commandButtonsHolder);

        setCommandButtonListeners();
    }

    /**
     * Create buttons for the player to move around with.
     */
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

    /**
     * Start the timer in which the player has to complete the game before they lose.
     */
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
                soundHandler.playScreamingGoatSound();
            }

            if (clockTime >= duration  && gameMode == GameMode.EASY) {
                soundHandler.playLoseSound();
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

    /**
     * Called when the game is either won or lost, end screen becomes visible and the player can go back to the Main Menu or quit the game.
     *
     * @see FadeController#start()
     * @param victory Whether the player won the game or lost the game.
     */
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
                        activateOnQuitListeners();
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

    /**
     * Disable direction buttons if there is no room in the direction of that button.
     */
    private void setDirectionButtonEnabled() {
        north.setEnabled(gameHandler.getCurrentRoom().getExit("north") != null, this.languageHandler.get("GAME_NO_ROOM"));
        east.setEnabled(gameHandler.getCurrentRoom().getExit("east") != null, this.languageHandler.get("GAME_NO_ROOM"));
        south.setEnabled(gameHandler.getCurrentRoom().getExit("south") != null, this.languageHandler.get("GAME_NO_ROOM"));
        west.setEnabled(gameHandler.getCurrentRoom().getExit("west") != null, this.languageHandler.get("GAME_NO_ROOM"));

        back.setEnabled(previousRoom.size() != 0);
    }

    /**
     * Listeners for all direction buttons.
     */
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

    /**
     * Move player to the next room, check if the door is not locked, if it is, tell them to find a key to get in.
     *
     * @param direction Direction to move to.
     */
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

    /**
     * Back button to go back to the previous room.
     */
    private void goBackRoom() {
        gameHandler.setCurrentRoom(previousRoom.pop());
        currentRoomText.setText(gameHandler.getCurrentRoom().getLongDescription());
        mapHandler.updateRoom(gameHandler.getCurrentRoom());

        setDirectionButtonEnabled();
        setInvestigationItems();
    }

    /**
     * Create the view so the player can investigate the room for items.
     */
    private void createInvestigationView() {
        investigateNoItemsTextHolder = new MyPanel(Color.BLACK, 200, 100, 210, 25, window);
        investigateNoItemsTextHolder.setLayout(new GridLayout(1, 1));
        investigateNoItemsTextHolder.setVisible(false);

        noItemsText = new MyTextArea(this.languageHandler.get("GAME_INVESTIGATE_NO_ITEMS"), Color.BLACK, Color.WHITE, 0, 0, 0, 0, investigateNoItemsTextHolder);

        investigateTooDarkTextHolder = new MyPanel(Color.BLACK, 100, 100, 400, 100, window);
        investigateTooDarkTextHolder.setLayout(new GridLayout(1, 1));
        investigateTooDarkTextHolder.setVisible(false);
        tooDarkText = new MyTextArea(this.languageHandler.get("GAME_ROOM_DARK"), Color.BLACK, Color.WHITE, 0, 0, 0, 0, investigateTooDarkTextHolder);

        investigateItemsHolder = new MyPanel(Color.BLACK, 225, 200, 150, 215, window);
        investigateItemsHolder.setLayout(new GridLayout(2, 1));
        ((GridLayout) investigateItemsHolder.getLayout()).setVgap(10);
        investigateItemsHolder.setVisible(false);
    }

    /**
     * Add items to the investigation view so the player can see what items are in the room and can pick them up.
     */
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
                            this.soundHandler.playYouWon();
                        }
                    }, vault.canOpenVault(key));
                } else {
                    actionHandler.createMenu();
                }
            });
        }
    }


    /**
     * Create buttons to quit the game to either the Main Menu or to Desktop.
     */
    private void createQuitButtons() {
        quitMenuHolder = new MyPanel(Color.BLACK, 225, 200, 150, 80, window);
        quitMenuHolder.setLayout(new GridLayout(2, 1));
        ((GridLayout) quitMenuHolder.getLayout()).setVgap(10);
        quitMenuHolder.setVisible(false);

        quitToMenu = new MyButton(this.languageHandler.get("GAME_MENU_MAIN_MENU"), Color.BLACK, Color.WHITE, positionzero, quitMenuHolder);
        quitToDesktop = new MyButton(this.languageHandler.get("GAME_MENU_QUIT_TO_DESKTOP"), Color.BLACK, Color.WHITE, positionzero, quitMenuHolder);

        setQuitButtonListeners();
    }

    /**
     * Set listeners for all command buttons.
     */
    private void setCommandButtonListeners() {
        moveCommand.addActionListener(e ->
        {
            commands(moveButtonHolder, moveCommand, true);
        });

        investigateCommand.addActionListener(e ->
        {
            if (!flashlightFound)
            {
                commands(investigateTooDarkTextHolder, investigateCommand, false);
            }
            else {
                commands(gameHandler.getCurrentRoom().getItems().size() == 0 ? investigateNoItemsTextHolder : investigateItemsHolder, investigateCommand, false);
            }
        });

        helpCommand.addActionListener(e ->
        {
            if (Desktop.isDesktopSupported() && Desktop.getDesktop().isSupported(Desktop.Action.BROWSE)) {
                try {
                    Desktop.getDesktop().browse(new URI(this.gameMode == GameMode.EASY ? this.languageHandler.get("GUIDE_URL") : this.gameMode == GameMode.MEDIUM ? this.languageHandler.get("GUIDE_URL_MEDIUM") : this.languageHandler.get("GUIDE_URL_HARD")));
                } catch (IOException | URISyntaxException ex) {
                    ex.printStackTrace();
                }
            }
//            commands(helpTextHolder, helpCommand, false);
        });

        quitCommand.addActionListener(e ->
        {
            commands(quitMenuHolder, quitCommand, false);
        });
    }

    /**
     * Set listeners for quit buttons.
     */
    private void setQuitButtonListeners() {
        quitToMenu.addActionListener(e ->
        {
            //gui.setGameUIVisibility(false);
            //gui.setMainMenuVisibility(true);
            activateOnQuitListeners();

            timer.stop();
            gui.quitToMainMenu();
        });

        quitToDesktop.addActionListener(e ->
        {
            activateOnQuitListeners();

            timer.stop();
            gui.quitGame();
        });
    }

    /**
     * Call all OnQuitListeners in the list.
     */
    public void activateOnQuitListeners()
    {
        for (OnQuitListener onQuitListener : onQuitListeners)
        {
            onQuitListener.quit();
        }
    }

    /**
     * Sets com.ProjectZuul.com.ProjectZuul.GUI.GUI.GameUI visibility.
     *
     * @param visibility Whether the menu should be set visible or invisible.
     */
    @Override
    public void setMenuVisibility(boolean visibility) {
        if (visibility) {
            setDefaultGameValues();
        }

        commandButtonsHolder.setVisible(visibility);
        currentSelectedCommandHolder.setVisible(visibility);
        inventoryHandler.setMenuVisibility(visibility);
    }

    /**
     *
     * @param holder Holder corresponding to the command button currently selected.
     * @param command Command button currently selected.
     * @param selectedMove Whether the play is in the move screen or not.
     */
    private void commands(Container holder, MyButton command, boolean selectedMove) {
        setCurrentSelectedCommandHolder(holder);
        setCurrentSelectedCommand(command);

        currentRoomText.setVisible(selectedMove);
        window.repaint();
    }

    /**
     * Set previous selected command holder inactive.
     *
     * @param holder Current selected holder.
     */
    private void setCurrentSelectedCommandHolder(Container holder) {
        if (currentSelectedCommandHolder != null) {
            currentSelectedCommandHolder.setVisible(false);
        }
        currentSelectedCommandHolder = holder;
        currentSelectedCommandHolder.setVisible(true);
    }

    /**
     * Set previous selected command color back to black and change this one to gray to indicate which button is selected.
     *
     * @param command Current selected command
     */
    private void setCurrentSelectedCommand(MyButton command) {
//        command.setBackground();
        if (CurrentSelectedCommand != null)
            CurrentSelectedCommand.setBackground(Color.BLACK);
        CurrentSelectedCommand = command;
        CurrentSelectedCommand.setBackground(Color.GRAY);
    }

    /**
     * Create the map with the current location of the player highlighted.
     */
    private void createMap() {
        mapHandler = new MapHandler(gameHandler.getRoomList());
        map = mapHandler.getMap();
        mapHandler.updateRoom(gameHandler.getCurrentRoom());
        window.add(map);
    }

    /**
     * Create the inventory with the items the player currently possesses.
     */
    private void createInventory() {
        inventoryHandler = new InventoryHandler(this.window, this.player);
    }

    /**
     * Create the action menu to add buttons to when the player wants to pick something up or drop something.
     */
    private void createActionMenu() {
        actionMenu = new ActionMenu(this.player);
        actionHandler = new ActionHandler(this.player, actionMenu);
        window.add(actionMenu);
    }

    /**
     * Gets instance of GameHandler.
     *
     * @return Instance of GameHandler.
     */
    public GameHandler getGameHandler() {
        return this.gameHandler;
    }

    /**
     * Gets instance of ActionHandler
     *
     * @return Instance of ActionHandler.
     */
    public ActionHandler getActionHandler() {
        return this.actionHandler;
    }

    /**
     * Gets instance of InventoryHandler.
     *
     * @return Instance of InventoryHandler
     */
    public InventoryHandler getInventoryHandler() {
        return this.inventoryHandler;
    }

    /**
     * Gets instance of MapHandler.
     *
     * @return Instance of MapHandler.
     */
    public MapHandler getMapHandler() {
        return mapHandler;
    }

    /**
     * Gets instance of GameMode.
     *
     * @return Instance of GameMode.
     */
    public GameMode getGameMode() {
        return gameMode;
    }

    /**
     * Gets instance of LanguageHandler.
     *
     * @return Instance of LanguageHandler.
     */
    public LanguageHandler getLanguageHandler() {
        return languageHandler;
    }
}
