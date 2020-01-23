package com.ProjectZuul.GUI.Fade;


import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

/**
 * Controller for fading a component. Holds a list of components that should go from visible to invisible or invisible to visible, by using transparency.
 *
 * @see FadePanel
 * @author Anne Pier Merkus
 */
public class FadeController {

    /**
     * List of components to be appearing or disappearing.
     */
    private List<FadePanel> animationPanels;

    /**
     * Timer in which the animationPanels will be updated.
     */
    private Timer timer;

    /**
     * startTime takes the current time difference between the current time and midnight January 1, 1970 UTC.
     * Comparison between the startTime and current time is used to keep track of the timer.
     */
    private Long startTime;

    /**
     * The time it should take to change the alpha of animationPanels.
     */
    private long runTime;

    /**
     * Creates a new FadeController that we can use to update UI.
     *
     * @param runTime The time it should take to change the alpha of the components in animationPanels, value given to runTime so we can use it in start.
     */
    public FadeController(int runTime) {
        this.runTime = runTime;
        animationPanels = new ArrayList<>();
    }

    /**
     * Add a component to animationPanels so we can use it and change the alpha value.
     *
     * @param panel the panel
     */
    public void add(FadePanel panel) {
        animationPanels.add(panel);
    }

    /**
     * Starts the timer and the process of turning components in animationPanels visible or invisible.
     */
    public void start()
    {
        timer = new Timer(40, e -> {
            if (startTime == null) {
                startTime = System.currentTimeMillis();
            }
            long duration = System.currentTimeMillis() - startTime;
            float progress = (float) duration / (float) runTime;
            if (progress > 1f) {
                progress = 1f;
                stop();
            }

            for (FadePanel panel : animationPanels) {
                panel.update(progress);
            }
        });
        timer.start();
    }

    /**
     * Stops the timer when the process is complete.
     */
    public void stop() {
        if (timer != null) {
            timer.stop();
        }
    }
}