package GUI.Components;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

public class MyFrame extends JFrame
{
    public MyFrame()
    {
        this.setSize(600, 600);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.getContentPane().setBackground(Color.black);
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

            this.setIconImages(icons);
        }
        catch(IOException IO)
        {
            //
        }
    }
}
