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

    MyLabel titleNameLabel;

    MyButton startButton, quitButton, aboutButton;

    MyButton aboutPageBack;
    MyTextArea aboutPageText;

    boolean aboutPageCreated = false;

    Font titleFont = new Font("Arial", Font.PLAIN, 30);

    public MainMenu(GUI gui)
    {
        this.gui = gui;
        createMainMenu();
    }

    private void createMainMenu()
    {
        window = gui.getWindow();

        titleNameLabel = new MyLabel("PROJECT ZUUL", Color.WHITE, titleFont, window);
        titleNameLabel.setBounds(485, 0, 500, 100);

        startButton = new MyButton("START", Color.BLACK, Color.WHITE,  new Rectangle(550, 150, 100, 35), window);
        aboutButton = new MyButton("ABOUT", Color.BLACK, Color.WHITE, new Rectangle(550, 200, 100, 35), window);
        quitButton = new MyButton("QUIT", Color.BLACK, Color.WHITE, new Rectangle(550, 300, 100, 35), window);

        setMainMenuListeners();
    }

    private void setMainMenuListeners()
    {
        startButton.addActionListener(e ->
        {
           // if (!gameCreated)
            //{
                gui.createGame();
               // gameCreated = true;
            //}
            //else
            //{
            //    gui.setMainMenuVisibility(false);
            //    gui.setGameUIVisibility(true);
           // }
        });

        aboutButton.addActionListener(e -> createAboutPage());

        quitButton.addActionListener(e -> gui.quitGame());
    }

    @Override
    public void setMenuVisibility(boolean visibility)
    {
        System.out.println(visibility);
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

