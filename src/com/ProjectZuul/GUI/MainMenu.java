package com.ProjectZuul.GUI;

import com.ProjectZuul.Enums.GameMode;
import com.ProjectZuul.Enums.Language;
import com.ProjectZuul.GUI.Components.MyButton;
import com.ProjectZuul.GUI.Components.MyLabel;
import com.ProjectZuul.GUI.Components.MyPanel;
import com.ProjectZuul.GUI.Components.MyTextArea;
import com.ProjectZuul.GUI.Listeners.SetInactiveListener;
import com.ProjectZuul.Handlers.LanguageHandler;

import javax.swing.*;
import java.awt.*;

/**
 * The type Main menu.
 */
public class MainMenu implements SetInactiveListener
{
    /**
     * Instance of the GUI.
     */
    private GUI gui;

    /**
     * The window of the application.
     */
    private JFrame window;

    /**
     * Panel in which the language selection buttons are located.
     */
    private MyPanel languageButtonHolder;

    /**
     * Panel in which the difficulty selection buttons are located.
     */
    private MyPanel difficultyButtonHolder;

    /**
     * Language button for Dutch.
     */
    private MyButton dutchLanguageButton;

    /**
     * Language button for Dutch.
     */
    private MyButton englishLanguageButton;

    /**
     * Difficulty button for easy difficulty.
     */
    private MyButton easyDifficultyButton;

    /**
     * Difficulty medium for easy difficulty.
     */
    private MyButton mediumDifficultyButton;

    /**
     * Difficulty hard for easy difficulty.
     */
    private MyButton hardDifficultyButton;

    /**
     * A label for the title of the game.
     */
    private MyLabel titleNameLabel;

    /**
     * Button to start the game.
     */
    private MyButton startButton;

    /**
     * Button to quit the game.
     */
    private MyButton quitButton;

    /**
     * Button to show the about page.
     */
    private MyButton aboutButton;

    /**
     * Back button on the about page.
     */
    private MyButton aboutPageBack;

    /**
     * Back button on the start page.
     */
    private MyButton startPageBack;

    /**
     * Start page button to start the game after selecting settings.
     */
    private MyButton startPageStartButton;

    /**
     * Text area on the about page.
     */
    private MyTextArea aboutPageText;

    /**
     *
     */
    private MyTextArea startPageLanguageText;

    /**
     * Text Area above difficulty buttons.
     */
    private MyTextArea startPageDifficultyText;

    /**
     * The About page created.
     */
    boolean aboutPageCreated = false;
    /**
     *
     */
    boolean startPageCreated = false;

    /**
     * The Title font.
     */
    Font titleFont = new Font("Arial", Font.PLAIN, 30);

    private LanguageHandler languageHandler;

    /**
     * Instantiates a new Main menu.
     *
     * @param gui      the gui
     * @param language the language
     */
    public MainMenu(GUI gui, Language language)
    {
        this.gui = gui;
        this.languageHandler = new LanguageHandler(language);
        createMainMenu();
    }

    private void createMainMenu()
    {
        window = gui.getWindow();

        titleNameLabel = new MyLabel("PROJECT ZUUL", Color.WHITE, titleFont, window);
        titleNameLabel.setBounds(485, 0, 500, 100);

        startButton = new MyButton(languageHandler.get("MENU_START").toUpperCase(), Color.BLACK, Color.WHITE,  new Rectangle(550, 150, 100, 35), window);
        aboutButton = new MyButton(languageHandler.get("MENU_ABOUT").toUpperCase(), Color.BLACK, Color.WHITE, new Rectangle(550, 200, 100, 35), window);
        quitButton = new MyButton(languageHandler.get("MENU_QUIT").toUpperCase(), Color.BLACK, Color.WHITE, new Rectangle(550, 300, 100, 35), window);

        setMainMenuListeners();
    }

    private void setMainMenuListeners()
    {
        startButton.addActionListener(e ->
        {
                //gui.createGame();
            createStartPage();
        });

        aboutButton.addActionListener(e -> createAboutPage());

        quitButton.addActionListener(e -> gui.quitGame());
    }

    @Override
    public void setMenuVisibility(boolean visibility)
    {
        titleNameLabel.setVisible(visibility);
        startButton.setVisible(visibility);
        aboutButton.setVisible(visibility);
        quitButton.setVisible(visibility);
    }

    private void createStartPage()
    {
        gui.setMainMenuVisibility(false);
        if (!startPageCreated) {
            startPageCreated = true;
            startPageBack = new MyButton("< BACK", Color.BLACK, Color.WHITE, new Rectangle(15, 15, 100, 35), window);
            startPageStartButton = new MyButton("START >", Color.BLACK, Color.WHITE, new Rectangle(1075, 15, 100, 35), window);

            createLanguageButtons();
            createDifficultyButtons();
            window.repaint();
        }
        else
        {
            setStartPageActive(true);
        }

        setStartPageListeners();
    }

    private void createLanguageButtons()
    {
        languageButtonHolder = new MyPanel(Color.BLACK, 525, 175, 150, 80, window);
        languageButtonHolder.setLayout(new GridLayout(2, 1));
        ((GridLayout) languageButtonHolder.getLayout()).setVgap(10);

        startPageLanguageText = new MyTextArea("Select a language.",  Color.BLACK, Color.WHITE,  535, 125, 125, 50, window);

        dutchLanguageButton = new MyButton(this.languageHandler.get("Dutch"), Color.BLACK, Color.WHITE, new Rectangle(), languageButtonHolder);
        englishLanguageButton = new MyButton(this.languageHandler.get("English"), Color.BLACK, Color.WHITE, new Rectangle(), languageButtonHolder);

        dutchLanguageButton.setBackground(Color.GRAY);
        gui.setLanguage(Language.NL);

        setLanguageButtonsListeners();
    }

    private void createDifficultyButtons() {
        difficultyButtonHolder = new MyPanel(Color.BLACK, 525, 375, 150, 120, window);
        difficultyButtonHolder.setLayout(new GridLayout(3, 1));
        ((GridLayout) difficultyButtonHolder.getLayout()).setVgap(10);

        startPageDifficultyText = new MyTextArea("Select a difficulty.",  Color.BLACK, Color.WHITE,  540, 325, 120, 50, window);

        easyDifficultyButton = new MyButton(this.languageHandler.get("Easy"), Color.BLACK, Color.WHITE, new Rectangle(), difficultyButtonHolder);
        mediumDifficultyButton = new MyButton(this.languageHandler.get("Medium"), Color.BLACK, Color.WHITE, new Rectangle(), difficultyButtonHolder);
        hardDifficultyButton = new MyButton(this.languageHandler.get("Hard"), Color.BLACK, Color.WHITE, new Rectangle(), difficultyButtonHolder);

        easyDifficultyButton.setBackground(Color.GRAY);
        gui.setGameMode(GameMode.EASY);

        setDifficultyButtonsListeners();
    }

    private void setLanguageButtonsListeners()
    {
        dutchLanguageButton.addActionListener(e ->
        {
            englishLanguageButton.setBackground(Color.BLACK);
            dutchLanguageButton.setBackground(Color.GRAY);
            gui.setLanguage(Language.NL);
        });

        englishLanguageButton.addActionListener(e ->
        {
            dutchLanguageButton.setBackground(Color.BLACK);
            englishLanguageButton.setBackground(Color.GRAY);
            gui.setLanguage(Language.EN);
        });
    }

    private void setDifficultyButtonsListeners()
    {
        easyDifficultyButton.addActionListener(e ->
        {
            gui.setGameMode(GameMode.EASY);
            easyDifficultyButton.setBackground(Color.GRAY);
            hardDifficultyButton.setBackground(Color.BLACK);
            mediumDifficultyButton.setBackground(Color.BLACK);
        });

        mediumDifficultyButton.addActionListener(e->
        {
            gui.setGameMode(GameMode.MEDIUM);
            mediumDifficultyButton.setBackground(Color.GRAY);
            hardDifficultyButton.setBackground(Color.BLACK);
            easyDifficultyButton.setBackground(Color.BLACK);
        });
        hardDifficultyButton.addActionListener(e ->
        {
            gui.setGameMode(GameMode.PRO);
            hardDifficultyButton.setBackground(Color.GRAY);
            easyDifficultyButton.setBackground(Color.BLACK);
            mediumDifficultyButton.setBackground(Color.BLACK);
        });
    }

    private void setStartPageListeners()
    {
        startPageBack.addActionListener(e ->
        {
            gui.setMainMenuVisibility(true);
            setStartPageActive(false);
        });

        startPageStartButton.addActionListener(e ->
        {
            setStartPageActive(false);
            gui.createGame();
        });
    }

    private void setStartPageActive(boolean active)
    {
        startPageBack.setVisible(active);
        startPageStartButton.setVisible(active);
        difficultyButtonHolder.setVisible(active);
        startPageDifficultyText.setVisible(active);
        languageButtonHolder.setVisible(active);
        startPageLanguageText.setVisible(active);
    }

    private void createAboutPage()
    {
        gui.setMainMenuVisibility(false);
        if (!aboutPageCreated) {
            aboutPageBack = new MyButton("< " + languageHandler.get("ABOUT_BACK").toUpperCase(), Color.BLACK, Color.WHITE, new Rectangle(15, 15, 100, 35), window);
            aboutPageText = new MyTextArea("",  Color.BLACK, Color.WHITE,  400, 100, 400, 250, window);
            aboutPageText.setText(languageHandler.get("ABOUT_TEXT"));

            setAboutPageListeners();
            aboutPageCreated = true;
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
            gui.setMainMenuVisibility(true);
            setAboutPageActive(false);
        });
    }

    private void setAboutPageActive(boolean active)
    {
        aboutPageBack.setVisible(active);
        aboutPageText.setVisible(active);
    }
}

