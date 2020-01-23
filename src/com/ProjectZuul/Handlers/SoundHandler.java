package com.ProjectZuul.Handlers;
import com.ProjectZuul.GUI.GameUI;
import com.ProjectZuul.GUI.Listeners.OnQuitListener;

import java.io.File;
import javax.media.Format;
import javax.media.Manager;
import javax.media.MediaLocator;
import javax.media.Player;
import javax.media.PlugInManager;
import javax.media.format.AudioFormat;

public class SoundHandler
{
    GameUI gameUI;
    public SoundHandler(GameUI gameUI) {
        this.gameUI = gameUI;
        Format input1 = new AudioFormat(AudioFormat.MPEGLAYER3);
        Format input2 = new AudioFormat(AudioFormat.MPEG);
        Format output = new AudioFormat(AudioFormat.LINEAR);
        PlugInManager.addPlugIn(
                "com.sun.media.codec.audio.mp3.JavaDecoder",
                new Format[]{input1, input2},
                new Format[]{output},
                PlugInManager.CODEC
        );
    }

    public void playSound()
    {
        try {
            Player player = Manager.createPlayer(new MediaLocator(new File("Sound/Opendoor.mp3").toURI().toURL()));
            player.start();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void playBackgroundSound()
    {
        try
        {
            Player player = Manager.createPlayer(new MediaLocator(new File("Sound/BackgroundMusicScary.mp3").toURI().toURL()));
            player.start();
            gameUI.addOnQuitListener(() -> {
                player.stop();
                player.close();
            });
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }
}