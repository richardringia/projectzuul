package Components;

import javax.swing.*;
import java.awt.*;

public class MyButton extends JButton
{
    public MyButton(String text, Color background, Color foreground, int x, int y, int width, int height, Container parent)
    {
        this.setText(text);
        this.setBackground(background);
        this.setForeground(foreground);
        this.setBounds(x, y, width, height);
        this.setBorder(BorderFactory.createLineBorder(Color.WHITE));
        this.setFocusPainted(false);
        parent.add(this);
    }
}
