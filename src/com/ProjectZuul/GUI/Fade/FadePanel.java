package com.ProjectZuul.GUI.Fade;

import javax.swing.*;
import java.awt.*;

public class FadePanel extends JPanel
{
    private float alpha;
    private float from;
    private float to;

    public FadePanel(Color background, int x, int y, int width, int height, Container parent, float alpha, boolean fadeIn)
    {
        this.setBackground(background);
        this.setBounds(x, y, width, height);
        this.setOpaque(false);
        this.alpha = alpha;
        this.from = fadeIn ? 0f : 1f;
        this.to = fadeIn ? 1f : 0f;
        parent.add(this);
    }

    @Override
    public void paint(Graphics graphics) {
        Graphics2D graphics2D = (Graphics2D) graphics.create();
        graphics2D.setComposite(AlphaComposite.SrcOver.derive(alpha));
        super.paint(graphics2D);
        graphics2D.dispose();
    }

    @Override
    protected void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);
        graphics.setColor(getBackground());
        graphics.fillRect(0, 0, getWidth(), getHeight());
    }

    public void setAlpha(float value) {
        if (alpha != value) {
            this.alpha = Math.min(1f, Math.max(0, value));
            repaint();
        }
    }


    public float getValueBasedOnProgress(float progress)
    {
        float value = 0;
        float distance = to - from;
        value = (distance * progress);
        value += from;

        return value;
    }


    public void setFromTo(boolean fadeIn)
    {
        this.from = fadeIn ? 0f : 1f;
        this.to = fadeIn ? 1f : 0f;
    }

    public void update(float progress) {
        float alpha = getValueBasedOnProgress(progress);
        setAlpha(alpha);
    }

    public float getAlpha() {
        return alpha;
    }
}
