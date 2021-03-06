package com.ProjectZuul.GUI.Components;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

/**
 * MyFrame extension of JFrame, creates the window of this application.
 *
 * @author Anne Pier Merkus and Richard Ringia.
 */
public class MyFrame extends JFrame
{
    /**
     * Creates the window used for this application. Sets the title and image of the window.
     */
    public MyFrame()
    {
        this.setResizable(false);
        this.setSize(1200, 600);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.getContentPane().setBackground(Color.BLACK);
        this.setLayout(null);
        this.setTitle("Zuul");

        try
        {
            ArrayList<Image> icons = new ArrayList<>();

            //URL url = new URL("https://img.icons8.com/nolan/2x/ghost.png");
            URL url16 = new URL("https://img.icons8.com/doodle/16/000000/ghost--v1.png");
            URL url32 = new URL("https://img.icons8.com/nolan/32/ghost.png");

            icons.add(ImageIO.read(url16));
            icons.add(ImageIO.read(url32));

            if (System.getProperty("os.name").startsWith("Mac OS X")) {
                Taskbar.getTaskbar().setIconImage(ImageIO.read(url32));

            } else {
                this.setIconImages(icons);
            }
        }
        catch(IOException IO)
        {
            //
        }
    }
}
