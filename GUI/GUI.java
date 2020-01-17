package GUI;

import GUI.Components.*;
import Zuul.Game;

import javax.swing.*;
import java.awt.*;

public class GUI
{
    Game game;
    GameUI gameUI;

    JFrame window;
    MainMenuListener mainMenuListener;

    public static void main(String[] args) {
        new GUI();
    }

    public GUI()
    {
        //SetMenuItemVisibility m = new MainMenu(this);
        createWindow();
        mainMenuListener = new MainMenu(this);

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
        new GameUI(this);
    }

    void setMainMenuVisibility(boolean visibility)
    {
        mainMenuListener.setMainMenuVisibility(visibility);
    }

    void quitGame()
    {
        window.dispose();
    }
}
