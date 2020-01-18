package GUI.Components;

import javax.swing.*;
import java.awt.*;

public class MyPanel extends JPanel
{
    public MyPanel(Color background, int x, int y, int width, int height, Container parent)
    {
        this.setBackground(background);
        this.setBounds(x, y, width, height);
        parent.add(this);
    }
}
