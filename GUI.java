import Zuul.Game;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

public class GUI
{
    JFrame window;

    JPanel titleNamePanel;

    JLabel titleNameLabel;

    JTextArea aboutPageText;

    JButton startButton;
    JButton quitButton;
    JButton aboutButton;

    JButton back;

    Font titleFont = new Font("Arial", Font.PLAIN, 50);

    boolean aboutPageCreated = false;

    public static void main(String[] args) {
        new GUI();
    }

    public GUI()
    {
        createWindow();
        createMainMenu();
    }

    public void createWindow()
    {
        window = new JFrame();
        window.setSize(610, 610);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.getContentPane().setBackground(Color.black);
        window.setLayout(null);
        window.setTitle("Zuul");

        try
        {
            ArrayList<Image> icons = new ArrayList<>();

            //URL url = new URL("https://img.icons8.com/nolan/2x/ghost.png");
            URL url16 = new URL("https://img.icons8.com/doodle/16/000000/ghost--v1.png");
            URL url32 = new URL("https://img.icons8.com/nolan/32/ghost.png");

            icons.add(ImageIO.read(url16));
            icons.add(ImageIO.read(url32));

            window.setIconImages(icons);
        }
        catch(IOException IO)
        {
            //
        }
    }

    public void createMainMenu()
    {
        titleNamePanel = createJPanel(Color.BLACK, 0, 0, 600, 100, window);

        titleNameLabel = createJLabel("PROJECT ZUUL", Color.WHITE, titleFont, titleNamePanel);

        startButton = createJButton("START", Color.BLACK, Color.WHITE,  250, 150, 100, 35, window);
        aboutButton = createJButton("ABOUT", Color.BLACK, Color.WHITE, 250, 200, 100, 35, window);
        quitButton = createJButton("QUIT", Color.BLACK, Color.WHITE, 250, 300, 100, 35, window);

        setMainMenuListeners();

        window.setVisible(true);
    }

    public void setMainMenuListeners()
    {
        startButton.addActionListener(e ->
        {
            Game game = new Game();
            game.play();

        });

        aboutButton.addActionListener(e -> createAboutPage());

        quitButton.addActionListener(e -> window.dispose());
    }

    public void setMainMenuActive(boolean active)
    {
        titleNamePanel.setVisible(active);
        titleNameLabel.setVisible(active);
        startButton.setVisible(active);
        aboutButton.setVisible(active);
        quitButton.setVisible(active);
    }

    public void createAboutPage()
    {
        setMainMenuActive(false);
        if (!aboutPageCreated) {
            back = createJButton("< BACK", Color.BLACK, Color.WHITE, 15, 15, 100, 35, window);
            aboutPageText = new JTextArea();
            aboutPageText.setBounds(100, 100, 400, 250);
            aboutPageText.setBackground(Color.BLACK);
            aboutPageText.setForeground(Color.WHITE);
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
            aboutPageText.setLineWrap(true);

            window.add(aboutPageText);
            setAboutPageListeners();
        }
        else
        {
            setAboutPageActive(true);
        }
    }

    public void setAboutPageListeners()
    {
        back.addActionListener(e ->
        {
            setMainMenuActive(true);
            setAboutPageActive(false);
        });
    }

    public void setAboutPageActive(boolean active)
    {
        back.setVisible(active);
        aboutPageText.setVisible(active);
    }

    public JPanel createJPanel(Color background, int x, int y, int width, int height, Container parent)
    {
        JPanel panel = new JPanel();
        panel.setBackground(background);
        panel.setBounds(x, y, width, height);
        parent.add(panel);

        return panel;
    }

    public JLabel createJLabel(String text, Color foreground, Font font, Container parent)
    {
        JLabel label = new JLabel(text);
        label.setForeground(foreground);
        label.setFont(font);
        parent.add(label);

        return label;
    }

    public JButton createJButton(String text, Color background, Color foreground, int x, int y, int width, int height, Container parent)
    {
        JButton button = new JButton(text);
        button.setBackground(background);
        button.setForeground(foreground);
        button.setBounds(x, y, width, height);
        parent.add(button);

        return button;
    }
}
