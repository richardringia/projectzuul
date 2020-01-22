package com.ProjectZuul.GUI;

import com.ProjectZuul.Enums.GameMode;
import com.ProjectZuul.GUI.Components.*;
import com.ProjectZuul.GUI.Listeners.SetInactiveListener;
import com.ProjectZuul.Zuul.Game;

import javax.swing.*;
import java.awt.*;

public class GUI
{
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
        gameUIListener = new GameUI(this, GameMode.EASY);
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
        mainMenuListener = new MainMenu(this);
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

}
