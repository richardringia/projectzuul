package com.ProjectZuul.GUI;

import com.ProjectZuul.Enums.GameMode;
import com.ProjectZuul.Enums.Language;
import com.ProjectZuul.GUI.Components.*;
import com.ProjectZuul.GUI.Listeners.SetInactiveListener;

import javax.swing.*;
import java.awt.*;

public class GUI
{
    JFrame window;

    SetInactiveListener mainMenuListener;
    SetInactiveListener gameUIListener;

    GameMode gameMode;
    Language language;

    public static void main(String[] args) {
        new GUI();
    }

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

    public JFrame getWindow()
    {
        return window;
    }

    void createGame()
    {
        gameUIListener = new GameUI(this, gameMode, language);
    }

    void setGameUIVisibility(boolean visibility)
    {
        gameUIListener.setMenuVisibility(visibility);
    }

    void setMainMenuVisibility(boolean visibility)
    {
        mainMenuListener.setMenuVisibility(visibility);
    }

    void quitToMainMenu()
    {
        window.getContentPane().removeAll();
        mainMenuListener = new MainMenu(this, Language.EN);
        //new MainMenu(this);
        gameUIListener = null;
        window.repaint();
    }

    void quitGame()
    {
        window.removeAll();
        window.dispose();
        //System.exit(0);
    }

    public void setGameMode(GameMode gameMode)
    {
        this.gameMode = gameMode;
    }

    public void setLanguage(Language language)
    {
        this.language = language;
    }

}
