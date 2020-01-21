package com.ProjectZuul.GUI;

import com.ProjectZuul.GUI.Components.*;
import com.ProjectZuul.GUI.Listeners.SetInactiveListener;
import com.ProjectZuul.Zuul.Game;

import javax.swing.*;

public class GUI
{
    Game game;
    GameUI gameUI;

    JFrame window;

    SetInactiveListener mainMenuListener;
    SetInactiveListener gameUIListener;

    public static void main(String[] args) {
        new GUI();
    }

    public GUI()
    {
        createWindow();
        mainMenuListener = new MainMenu(this);
        window.setVisible(true);
    }

    private void createWindow()
    {
        window = new MyFrame();
        try {
            UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
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
        gameUIListener = new GameUI(this);
    }

    void setGameUIVisibility(boolean visibility)
    {
        gameUIListener.setMenuVisibility(visibility);
    }

    void setMainMenuVisibility(boolean visibility)
    {
        mainMenuListener.setMenuVisibility(visibility);
    }

    void quitGame()
    {
        window.dispose();
    }

}
