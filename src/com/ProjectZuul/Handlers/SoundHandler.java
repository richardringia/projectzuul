package com.ProjectZuul.Handlers;

import com.ProjectZuul.GUI.GameUI;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import javax.media.*;
import javax.media.format.AudioFormat;

/**
 * Handler to process .mp3 files. Used to create sound effects and scary background music.
 * @author Anne Pier Merkus
 */
public class SoundHandler
{
    /**
     * Instance of the game used to add an OnQuitListener.
     */
    GameUI gameUI;

    /**
     * The background can be stored here.
     */
    Player backgroundPlayer;

    /**
     * Create new SoundHandler and register the PlugIn.
     *
     * @param gameUI instance of the current game.
     */
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

    /**
     * Create a new player to play a sound effect.
     */
    public void playSound()
    {
        try {
            Player player = Manager.createPlayer(new MediaLocator(new File("Sound/Opendoor.mp3").toURI().toURL()));
            player.start();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Play scary background music for 5 minutes.
     * OnQuitListener added so we can stop and close the player after quit is invoked, otherwise the music keeps playing.
     */
    public void playBackgroundSound()
    {
        try
        {
            backgroundPlayer = Manager.createPlayer(new MediaLocator(new File("Sound/BackgroundMusicScary.mp3").toURI().toURL()));
            backgroundPlayer.start();
            gameUI.addOnQuitListener(() -> {
                backgroundPlayer.stop();
                backgroundPlayer.close();
            });
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }

    public void playScreamingGoatSound()
    {
        try {
            Player player = Manager.createPlayer(new MediaLocator(new File("Sound/goat.mp3").toURI().toURL()));
            if (backgroundPlayer != null) {
                player.start();
            }

        }  catch(Exception e)
        {
            e.printStackTrace();
        }
    }
}