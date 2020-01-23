package com.ProjectZuul.GUI.Fade;

import javax.swing.*;
import java.awt.*;

/**
 * An extension of JPanel, used to paint manually so we can make the panel slowly appear or disappear by using transparency.
 *
 * @author Anne Pier Merkus
 */
public class FadePanel extends JPanel
{
    /**
     * The alpha (transparency) of the panel, changes in update when called.
     *
     * @see FadeController
     */
    private float alpha;

    /**
     * Start value of alpha, can be 0 or 1.
     */
    private float from;

    /**
     * The value alpha must end on, can be 0 or 1.
     */
    private float to;

    /**
     * Creates a new panel and sets the given values.
     *
     * @param background The color of the background this panel will have.
     * @param x          The x location of the panel.
     * @param y          The y location of the panel.
     * @param width      The width of the panel.
     * @param height     The height of the panel.
     * @param parent     The container that will hold this panel.
     * @param alpha      Transparency of the panel, either 0 (invisible) or 1 (visible).
     * @param fadeIn     Whether the panel should fade in (invisible to visible), or fade out (visible to invisible).
     */
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

    /**
     * Paints a new background for this panel. Read super.paint for full documentation.
     * @see #paintComponent(Graphics)
     * @param graphics the Graphics context in which to paint
     */
    @Override
    public void paint(Graphics graphics) {
        Graphics2D graphics2D = (Graphics2D) graphics.create();
        graphics2D.setComposite(AlphaComposite.SrcOver.derive(alpha));
        super.paint(graphics2D);
        graphics2D.dispose();
    }

    /**
     * Sets the new background on the current graphics. Read 
     *
     * @param graphics the Graphics object to protect
     * @see #paint(Graphics) 
     */
    @Override
    protected void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);
        graphics.setColor(getBackground());
        graphics.fillRect(0, 0, getWidth(), getHeight());
    }

    /**
     * Gives a new value to alpha, using Math.min and Math.max to make sure the value stays within 0 and 1 and repaints the panel after setting the alpha.
     *
     * @param value The new, calculated, value of alpha.
     */
    public void setAlpha(float value) {
        if (alpha != value) {
            this.alpha = Math.min(1f, Math.max(0, value));
            repaint();
        }
    }

    /**
     * Calculates the value of alpha based on the progress on the timer and the values from and to.
     *
     * @param progress The progress on the timer determines what the alpha must be at.
     * @return The alpha value to be set after calculating it based on the progress.
     */
    public float getValueBasedOnProgress(float progress)
    {
        float value = 0;
        float distance = to - from;
        value = (distance * progress);
        value += from;

        return value;
    }

    /**
     * Change from and to value so a panel can go invisible again on the next controller.
     *
     * @param fadeIn Whether the panel should fade in (invisible to visible), or fade out (visible to invisible).
     */
    public void setFromTo(boolean fadeIn)
    {
        this.from = fadeIn ? 0f : 1f;
        this.to = fadeIn ? 1f : 0f;
    }

    /**
     * Update the current alpha of this panel.
     *
     * @see #getValueBasedOnProgress(float) 
     * @see #setAlpha(float)
     * @param progress The progress on the timer determines what the alpha must be at.
     */
    public void update(float progress) {
        float alpha = getValueBasedOnProgress(progress);
        setAlpha(alpha);
    }
}
