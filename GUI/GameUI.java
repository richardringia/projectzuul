package GUI;

import GUI.Components.MyButton;
import GUI.Components.MyPanel;
import GUI.Components.MyTextArea;
import Zuul.Game;

import javax.swing.*;
import java.awt.*;

public class GameUI
{
    Game game;
    GUI gui;

    JFrame window;
    Rectangle positionzero;

    Container currentSelectedCommandHolder, CurrentSelectedCommand;

    MyPanel commandButtonsHolder, moveButtonHolder, investigateItemsHolder, helpTextHolder, quitMenuHolder;

    MyButton moveCommand, investigateCommand, helpCommand, quitCommand;
    MyButton north, south, east, west, back;
    MyButton quitToMenu, quitToDesktop;

    MyTextArea helpPageText;
    MyTextArea currentRoomText;

    boolean gameScreenCreated = false;

    Font gameFont = new Font("Arial", Font.PLAIN, 15);

    public GameUI(GUI gui)
    {
        this.gui = gui;
        positionzero = new Rectangle();
        createGame();
    }

    public void createGame()
    {
        window = gui.getWindow();
        gui.setMainMenuVisibility(false);

        if (gameScreenCreated)
        {
            //setDefaultGameValues();
            return;
        }

        game = new Game();
        currentRoomText = new MyTextArea(game.getCurrentRoom().getLongDescription(),  Color.BLACK, Color.WHITE,  100, 100, 400, 200, window);
        currentRoomText.setFont(gameFont);

        createCommandButtons();
        createDirectionButtons();
        createHelpPage();
        createQuitButtons();
        setDefaultGameValues();
    }

    private void setDefaultGameValues()
    {
        gameScreenCreated = true;
        commandButtonsHolder.setVisible(true);
        setCurrentSelectedCommandHolder(moveButtonHolder);
        setCurrentSelectedCommand(moveCommand);

        window.repaint();
    }

    private void createCommandButtons()
    {
        commandButtonsHolder = new MyPanel(Color.BLACK, 20, 20, 500, 40, window);
        commandButtonsHolder.setLayout(new GridLayout(1, 10));
        ((GridLayout)commandButtonsHolder.getLayout()).setHgap(10);

        moveCommand = new MyButton("Move", Color.GRAY, Color.WHITE, positionzero, commandButtonsHolder);
        investigateCommand = new MyButton("Investigate", Color.BLACK, Color.WHITE, positionzero, commandButtonsHolder);
        helpCommand = new MyButton("Help", Color.BLACK, Color.WHITE, positionzero, commandButtonsHolder);
        quitCommand = new MyButton("Quit", Color.BLACK, Color.WHITE, positionzero, commandButtonsHolder);

        setCommandButtonListeners();
    }

    private void createDirectionButtons()
    {
        moveButtonHolder = new MyPanel(Color.BLACK, 225, 300, 150, 200, window);
        moveButtonHolder.setLayout(new GridLayout(5, 1));
        ((GridLayout)moveButtonHolder.getLayout()).setVgap(10);

        north = new MyButton("North", Color.BLACK, Color.WHITE, positionzero, moveButtonHolder);
        east = new MyButton("East", Color.BLACK, Color.WHITE, positionzero, moveButtonHolder);
        south = new MyButton("South", Color.BLACK, Color.WHITE, positionzero, moveButtonHolder);
        west = new MyButton("West", Color.BLACK, Color.WHITE, positionzero, moveButtonHolder);
        back = new MyButton("Back", Color.BLACK, Color.WHITE, positionzero, moveButtonHolder);
    }

    private void createHelpPage()
    {
        helpTextHolder = new MyPanel(Color.BLACK, 100, 100, 400, 250, window);
        helpTextHolder.setLayout(new GridLayout(1, 1));
        helpTextHolder.setVisible(false);

        helpPageText = new MyTextArea("",  Color.BLACK, Color.WHITE,  0, 0, 0, 0, helpTextHolder);

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

    private void createQuitButtons()
    {
        quitMenuHolder = new MyPanel(Color.BLACK, 225, 200, 150, 80, window);
        quitMenuHolder.setLayout(new GridLayout(2, 1));
        ((GridLayout)quitMenuHolder.getLayout()).setVgap(10);
        quitMenuHolder.setVisible(false);

        quitToMenu = new MyButton("Quit to Main Menu", Color.BLACK, Color.WHITE, positionzero, quitMenuHolder);
        quitToDesktop = new MyButton("Quit to Desktop", Color.BLACK, Color.WHITE, positionzero, quitMenuHolder);

        setQuitButtonListeners();
    }

    private void setCommandButtonListeners()
    {
        moveCommand.addActionListener(e ->
        {
            commands(moveButtonHolder, moveCommand, true);
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

    private void setQuitButtonListeners()
    {
        quitToMenu.addActionListener(e ->
        {
            commandButtonsHolder.setVisible(false);
            quitMenuHolder.setVisible(false);
            gui.setMainMenuVisibility(true);
        });

        quitToDesktop.addActionListener(e -> gui.quitGame());
    }

    private void commands(Container holder, Container command, boolean selectedMove)
    {
        setCurrentSelectedCommandHolder(holder);
        setCurrentSelectedCommand(command);

        currentRoomText.setVisible(selectedMove);
    }

    private void setCurrentSelectedCommandHolder(Container holder)
    {
        if (currentSelectedCommandHolder != null)
            currentSelectedCommandHolder.setVisible(false);
        currentSelectedCommandHolder = holder;
        currentSelectedCommandHolder.setVisible(true);
    }

    private void setCurrentSelectedCommand(Container command)
    {
        if (CurrentSelectedCommand != null)
            CurrentSelectedCommand.setBackground(Color.BLACK);
        CurrentSelectedCommand = command;
        CurrentSelectedCommand.setBackground(Color.GRAY);
    }

}
