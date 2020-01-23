package com.ProjectZuul.GUI;

import com.ProjectZuul.Enums.GameMode;
import com.ProjectZuul.Enums.Language;
import com.ProjectZuul.GUI.Components.*;
import com.ProjectZuul.GUI.Listeners.SetInactiveListener;

import javax.swing.*;
import java.awt.*;

/**
 * The type Gui.
 */
public class GUI
{
    /**
     * The Window.
     */
    MyFrame window;

    /**
     * The Main menu listener.
     */
    SetInactiveListener mainMenuListener;
    /**
     * The Game ui listener.
     */
    SetInactiveListener gameUIListener;

    /**
     * The Game mode.
     */
    GameMode gameMode;
    /**
     * The Language.
     */
    Language language;

    /**
     * The entry point of application.
     *
     * @param args the input arguments
     */
    public static void main(String[] args) {
        new GUI();
    }

    /**
     * Instantiates a new Gui.
     */
    public GUI()
    {
        createWindow();
        mainMenuListener = new MainMenu(this, Language.EN);
        window.setVisible(true);
    }

    private void createWindow()
    {
        window = new MyFrame();
        try {
            UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
            UIManager.put("nimbusBlueGrey", Color.BLACK);
            SwingUtilities.updateComponentTreeUI(window);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * Gets window.
     *
     * @return the window
     */
    public MyFrame getWindow()
    {
        return window;
    }

    /**
     * Create game.
     */
    void createGame()
    {
        gameUIListener = new GameUI(this, gameMode, language);
    }

    /**
     * Sets game ui visibility.
     *
     * @param visibility the visibility
     */
    void setGameUIVisibility(boolean visibility)
    {
        gameUIListener.setMenuVisibility(visibility);
    }

    /**
     * Sets main menu visibility.
     *
     * @param visibility the visibility
     */
    void setMainMenuVisibility(boolean visibility)
    {
        mainMenuListener.setMenuVisibility(visibility);
    }

    /**
     * Quit to main menu.
     */
    void quitToMainMenu()
    {
        window.getContentPane().removeAll();
        mainMenuListener = new MainMenu(this, Language.EN);
        //new MainMenu(this);
        gameUIListener = null;
        window.repaint();
    }

    /**
     * Quit game.
     */
    void quitGame()
    {
        window.removeAll();
        window.dispose();
        //System.exit(0);
    }

    /**
     * Sets game mode.
     *
     * @param gameMode the game mode
     */
    public void setGameMode(GameMode gameMode)
    {
        this.gameMode = gameMode;
    }

    /**
     * Sets language.
     *
     * @param language the language
     */
    public void setLanguage(Language language)
    {
        this.language = language;
    }

}
