package Components;

import javax.swing.*;
import java.awt.*;

public class MyTextArea extends JTextArea
{
    public MyTextArea(String text, Color background, Color foreground, int x, int y, int width, int height, Container parent)
    {
        this.setText(text);
        this.setBackground(background);
        this.setForeground(foreground);
        this.setBounds(x, y, width, height);
        this.setLineWrap(true);
        this.setWrapStyleWord(true);

        parent.add(this);
    }
}
