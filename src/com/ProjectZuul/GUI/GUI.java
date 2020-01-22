package com.ProjectZuul.GUI;

import com.ProjectZuul.GUI.Components.*;
import com.ProjectZuul.GUI.Listeners.SetInactiveListener;
import com.ProjectZuul.Zuul.Game;

import javax.swing.*;
import java.awt.*;

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
            //UIManager.put("nimbusBase", Color.BLACK);
            UIManager.put("nimbusBlueGrey", Color.BLACK);
            //UIManager.getLookAndFeelDefaults().put("Button[Disabled].backgroundPainter", new ButtonPainter(new Color(0, 0, 0, 255), new Color(0, 0, 50, 255)));

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
