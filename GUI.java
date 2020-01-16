import Components.MyButton;
import Components.MyLabel;
import Components.MyPanel;
import Components.MyTextArea;
import Zuul.Game;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

public class GUI
{
    Game game;

    JFrame window;

    Container currentSelectedCommand;
    Container currentSelectedButton;

    MyPanel titleNamePanel;

    MyPanel commandButtonHolder;
    MyPanel directionButtonHolder, investigateItemsHolder, helpHolder, quitMenuHolder;

    MyLabel titleNameLabel;

    MyTextArea aboutPageText;
    MyTextArea helpPageText;
    MyTextArea gameCurrentRoom;

    // Buttons in Main Menu.
    MyButton startButton, quitButton, aboutButton;

    // Directions
    MyButton north, south, east, west, back;
    MyButton quitToMenu, quitToDesktop;
    MyButton move, investigate, help, quit;
    MyButton pickup, open, drop;

    // Back button on the About Page.
    MyButton aboutPageBack;

    Font titleFont = new Font("Arial", Font.PLAIN, 30);
    Font gameFont = new Font("Arial", Font.PLAIN, 15);

    boolean aboutPageCreated = false, gameScreenCreated = false;

    public static void main(String[] args) {
        new GUI();
    }

    public GUI()
    {
        createWindow();
        createMainMenu();
    }

    private void createWindow()
    {
        window = new JFrame();
        window.setSize(600, 600);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.getContentPane().setBackground(Color.black);
        window.setLayout(null);
        window.setTitle("Zuul");

        try
        {
            ArrayList<Image> icons = new ArrayList<>();

            //URL url = new URL("https://img.icons8.com/nolan/2x/ghost.png");
            URL url16 = new URL("https://img.icons8.com/doodle/16/000000/ghost--v1.png");
            URL url32 = new URL("https://img.icons8.com/nolan/32/ghost.png");

            icons.add(ImageIO.read(url16));
            icons.add(ImageIO.read(url32));

            window.setIconImages(icons);
        }
        catch(IOException IO)
        {
            //
        }
    }

    private void createMainMenu()
    {
        //titleNamePanel = createJPanel(Color.BLACK, 0, 0, 600, 100, window);
        titleNamePanel = new MyPanel(Color.BLACK, 0, 0, 600, 100, window);

        //titleNameLabel = createJLabel("PROJECT ZUUL", Color.WHITE, titleFont, titleNamePanel);
        titleNameLabel = new MyLabel("PROJECT ZUUL", Color.WHITE, titleFont, titleNamePanel);

        startButton = new MyButton("START", Color.BLACK, Color.WHITE,  250, 150, 100, 35, window);
        aboutButton = new MyButton("ABOUT", Color.BLACK, Color.WHITE, 250, 200, 100, 35, window);
        quitButton = new MyButton("QUIT", Color.BLACK, Color.WHITE, 250, 300, 100, 35, window);

        setMainMenuListeners();

        window.setVisible(true);
    }

    private void setMainMenuListeners()
    {
        startButton.addActionListener(e ->
        {
            createGame();
        });

        aboutButton.addActionListener(e -> createAboutPage());

        quitButton.addActionListener(e -> quitGame());
    }

    private void setMainMenuActive(boolean active)
    {
        titleNamePanel.setVisible(active);
        titleNameLabel.setVisible(active);
        startButton.setVisible(active);
        aboutButton.setVisible(active);
        quitButton.setVisible(active);
    }

    private void createGame()
    {
        setMainMenuActive(false);
        if (gameScreenCreated)
        {
            setDefaultGameValues();
            return;
        }

        game = new Game();
        gameCurrentRoom = new MyTextArea(game.getCurrentRoom().getLongDescription(),  Color.BLACK, Color.WHITE,  100, 100, 400, 200, window);
        gameCurrentRoom.setFont(gameFont);

        createCommandButtons();
        createDirectionButtons();
        createHelpPage();
        createQuitButtons();
        setCommandButtonListeners();

        setDefaultGameValues();
    }

    private void setDefaultGameValues()
    {
        gameScreenCreated = true;
        commandButtonHolder.setVisible(true);
        setCurrentSelectedCommand(directionButtonHolder);
        setCurrentSelectedButton(move);

        window.repaint();
    }

    private void createCommandButtons()
    {
        commandButtonHolder = new MyPanel(Color.BLACK, 20, 20, 500, 40, window);
        commandButtonHolder.setLayout(new GridLayout(1, 10));
        ((GridLayout)commandButtonHolder.getLayout()).setHgap(10);

        move = new MyButton("Move", Color.GRAY, Color.WHITE, 0, 0, 0, 0, commandButtonHolder);
        investigate = new MyButton("Investigate", Color.BLACK, Color.WHITE, 0, 0, 0, 0, commandButtonHolder);
        help = new MyButton("Help", Color.BLACK, Color.WHITE, 0, 0, 0, 0, commandButtonHolder);
        quit = new MyButton("Quit", Color.BLACK, Color.WHITE, 0, 0, 0, 0, commandButtonHolder);
    }

    private void createDirectionButtons()
    {
        directionButtonHolder = new MyPanel(Color.BLACK, 225, 300, 150, 200, window);
        directionButtonHolder.setLayout(new GridLayout(5, 1));
        ((GridLayout)directionButtonHolder.getLayout()).setVgap(10);

        north = new MyButton("North", Color.BLACK, Color.WHITE, 0, 0, 0, 0, directionButtonHolder);
        east = new MyButton("East", Color.BLACK, Color.WHITE, 0, 0, 0, 0, directionButtonHolder);
        south = new MyButton("South", Color.BLACK, Color.WHITE, 0, 0, 0, 0, directionButtonHolder);
        west = new MyButton("West", Color.BLACK, Color.WHITE, 0, 0, 0, 0, directionButtonHolder);
        back = new MyButton("Back", Color.BLACK, Color.WHITE, 0, 0, 0, 0, directionButtonHolder);
    }

    private void createHelpPage()
    {
        helpHolder = new MyPanel(Color.BLACK, 100, 100, 400, 250, window);
        helpHolder.setLayout(new GridLayout(1, 1));
        helpHolder.setVisible(false);

        helpPageText = new MyTextArea("",  Color.BLACK, Color.WHITE,  0, 0, 0, 0, helpHolder);

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

        quitToMenu = new MyButton("Quit to Main Menu", Color.BLACK, Color.WHITE, 0, 0, 0, 0, quitMenuHolder);
        quitToDesktop = new MyButton("Quit to Desktop", Color.BLACK, Color.WHITE, 0, 0, 0, 0, quitMenuHolder);

        setQuitButtonListeners();
    }

    private void setCommandButtonListeners()
    {
        move.addActionListener(e ->
        {
            commands(directionButtonHolder, move, true);
        });

        help.addActionListener(e ->
        {
            commands(helpHolder, help, false);
        });

        quit.addActionListener(e ->
        {
            commands(quitMenuHolder, quit, false);
        });
    }

    private void commands(Container holder, Container button, boolean selectedMove)
    {
        setCurrentSelectedCommand(holder);
        setCurrentSelectedButton(button);

        gameCurrentRoom.setVisible(selectedMove);
    }

    private void setQuitButtonListeners()
    {
        quitToMenu.addActionListener(e ->
        {
            commandButtonHolder.setVisible(false);
            quitMenuHolder.setVisible(false);
            setMainMenuActive(true);
        });

        quitToDesktop.addActionListener(e -> quitGame());
    }

    private void createAboutPage()
    {
        setMainMenuActive(false);
        if (!aboutPageCreated) {
            aboutPageBack = new MyButton("< BACK", Color.BLACK, Color.WHITE, 15, 15, 100, 35, window);
            aboutPageText = new MyTextArea("",  Color.BLACK, Color.WHITE,  100, 100, 400, 250, window);

            aboutPageText.setText("DIT IS EEN ABOUT PAGINA, DEZE GAME IS GEWELDIG MANFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFF" +
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

            setAboutPageListeners();
        }
        else
        {
            setAboutPageActive(true);
        }
    }

    private void setAboutPageListeners()
    {
        aboutPageBack.addActionListener(e ->
        {
            setMainMenuActive(true);
            setAboutPageActive(false);
        });
    }

    private void setCurrentSelectedCommand(Container container)
    {
        if (currentSelectedCommand != null)
            currentSelectedCommand.setVisible(false);
        currentSelectedCommand = container;
        currentSelectedCommand.setVisible(true);
    }

    private void setCurrentSelectedButton(Container container)
    {
        if (currentSelectedButton != null)
            currentSelectedButton.setBackground(Color.BLACK);
        currentSelectedButton = container;
        currentSelectedButton.setBackground(Color.GRAY);
    }

    private void setAboutPageActive(boolean active)
    {
        aboutPageBack.setVisible(active);
        aboutPageText.setVisible(active);
    }

    private void quitGame()
    {
        window.dispose();
    }
}
