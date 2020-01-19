package com.ProjectZuul.GUI;

import com.ProjectZuul.GUI.Components.MyButton;
import com.ProjectZuul.GUI.Components.MyLabel;
import com.ProjectZuul.GUI.Components.MyPanel;
import com.ProjectZuul.GUI.Components.MyTextArea;
import com.ProjectZuul.GUI.Listeners.SetInactiveListener;

import javax.swing.*;
import java.awt.*;

public class MainMenu implements SetInactiveListener
{
    GUI gui;
    JFrame window;

    MyPanel titleNamePanel;
    MyLabel titleNameLabel;

    MyButton startButton, quitButton, aboutButton;

    MyButton aboutPageBack;
    MyTextArea aboutPageText;

    boolean aboutPageCreated = false;
    boolean gameCreated = false;

    Font titleFont = new Font("Arial", Font.PLAIN, 30);

    public MainMenu(GUI gui)
    {
        this.gui = gui;
        createMainMenu();
    }

    private void createMainMenu()
    {
        window = gui.getWindow();

        //titleNamePanel = createJPanel(Color.BLACK, 0, 0, 600, 100, window);
        titleNamePanel = new MyPanel(Color.BLACK, 0, 0, 600, 100, window);

        //titleNameLabel = createJLabel("PROJECT ZUUL", Color.WHITE, titleFont, titleNamePanel);
        titleNameLabel = new MyLabel("PROJECT ZUUL", Color.WHITE, titleFont, titleNamePanel);

        startButton = new MyButton("START", Color.BLACK, Color.WHITE,  new Rectangle(250, 150, 100, 35), window);
        aboutButton = new MyButton("ABOUT", Color.BLACK, Color.WHITE, new Rectangle(250, 200, 100, 35), window);
        quitButton = new MyButton("QUIT", Color.BLACK, Color.WHITE, new Rectangle(250, 300, 100, 35), window);

        setMainMenuListeners();
    }

    private void setMainMenuListeners()
    {
        startButton.addActionListener(e ->
        {
            if (!gameCreated)
            {
                gui.createGame();
                gameCreated = true;
            }
            else
            {
                gui.setGameUIVisibility(true);
            }
        });

        aboutButton.addActionListener(e -> createAboutPage());

        quitButton.addActionListener(e -> gui.quitGame());
    }

    @Override
    public void setMenuVisibility(boolean visibility)
    {
        titleNamePanel.setVisible(visibility);
        titleNameLabel.setVisible(visibility);
        startButton.setVisible(visibility);
        aboutButton.setVisible(visibility);
        quitButton.setVisible(visibility);
    }

    private void createAboutPage()
    {
        gui.setMainMenuVisibility(false);
        if (!aboutPageCreated) {
            aboutPageBack = new MyButton("< BACK", Color.BLACK, Color.WHITE, new Rectangle(15, 15, 100, 35), window);
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

