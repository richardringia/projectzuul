package com.ProjectZuul.GUI;

import com.ProjectZuul.Enums.GameMode;
import com.ProjectZuul.Enums.Language;
import com.ProjectZuul.GUI.Components.*;
import com.ProjectZuul.GUI.GameUI;
import com.ProjectZuul.GUI.Listeners.SetInactiveListener;

import javax.swing.*;
import java.awt.*;

/**
 * The entry point after Main, creates the window, Main Menu and the com.ProjectZuul.com.ProjectZuul.GUI.GUI.GameUI when called.
 *
 * @see com.ProjectZuul.Main#main(String[])
 * @author Anne Pier Merkus
 */
public class GUI
{
    /**
     * The window of the application.
     */
    MyFrame window;

    /**
     * Listener to set the main menu inactive.
     */
    SetInactiveListener mainMenuListener;
    /**
     * Listener to set the gameUI inactive.
     */
    SetInactiveListener gameUIListener;

    /**
     * The difficulty of the game.
     *
     * @see GameMode
     */
    GameMode gameMode;

    /**
     * The language the game will be played in.
     *
     * @see Language
     */
    Language language;

    /**
     * Creates a frame in which the game will be played and creates the MainMenu.
     */
    public GUI()
    {
        createWindow();
        mainMenuListener = new MainMenu(this, Language.EN);
        window.setVisible(true);
    }

    /**
     * Creates MyFrame which creates the window for this application.
     * Sets the UIManager to make the buttons look better.
     *
     * @see MyFrame#MyFrame()
     */
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
     * Gets the window of the application.
     *
     * @return The window of the application.
     */
    public MyFrame getWindow()
    {
        return window;
    }

    /**
     * Creates a new com.ProjectZuul.com.ProjectZuul.GUI.GUI.GameUI and sets gameUIListener so we can call it later if needed.
     */
    void createGame()
    {
        gameUIListener = new GameUI(this, gameMode, language);
    }

    /**
     * Sets com.ProjectZuul.com.ProjectZuul.GUI.GUI.GameUI visibility.
     *
     * @param visibility Whether the menu should be set visible or invisible.
     */
    void setGameUIVisibility(boolean visibility)
    {
        gameUIListener.setMenuVisibility(visibility);
    }

    /**
     * Sets Main Menu visibility.
     *
     * @param visibility Whether the menu should be set visible or invisible.
     */
    void setMainMenuVisibility(boolean visibility)
    {
        mainMenuListener.setMenuVisibility(visibility);
    }

    /**
     * Quits the game and calls Main Menu again.
     */
    void quitToMainMenu()
    {
        window.getContentPane().removeAll();
        mainMenuListener = new MainMenu(this, Language.EN);
        gameUIListener = null;
        window.repaint();
    }

    /**
     * Quit the game entirely by closing the window.
     */
    void quitGame()
    {
        window.removeAll();
        window.dispose();
        System.exit(0);
    }

    /**
     * Set the game mode to the given difficulty.
     *
     * @param gameMode The selected difficulty by the player in the Main Menu.
     */
    public void setGameMode(GameMode gameMode)
    {
        this.gameMode = gameMode;
    }

    /**
     * Set the language to the given language.
     *
     * @param language The selected language by the player in the Main Menu.
     */
    public void setLanguage(Language language)
    {
        this.language = language;
    }

}
