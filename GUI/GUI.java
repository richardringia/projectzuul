package GUI;

import GUI.Components.*;
import Zuul.Game;

import javax.swing.*;
import java.awt.*;

public class GUI
{
    Game game;
    MainMenu mainMenu;
    GameUI gameUI;

    JFrame window;

    public static void main(String[] args) {
        new GUI();
    }

    public GUI()
    {
        createWindow();
        mainMenu = new MainMenu(this);

        window.setVisible(true);
    }

    private void createWindow()
    {
        window = new MyFrame();
    }

    public JFrame getWindow()
    {
        return window;
    }

    void createGame()
    {
        gameUI = new GameUI(this);
    }

    void setMainMenuVisibility(boolean visibility)
    {
        mainMenu.setMainMenuVisibility(visibility);
    }
    void quitGame()
    {
        window.dispose();
    }
}
