package com.ProjectZuul.GUI.Fade;


import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class FadeController {

    private List<FadePanel> animationPanels;
    private Timer timer;
    private Long startTime;
    private long runTime;

    public FadeController(int runTime) {
        this.runTime = runTime;
        animationPanels = new ArrayList<>();
    }

    public void add(FadePanel panel) {
        animationPanels.add(panel);
    }

    public void start()
    {
        timer = new Timer(40, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
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
            }
        });
        timer.start();

    }

    public void stop() {
        if (timer != null) {
            timer.stop();
        }
    }
}