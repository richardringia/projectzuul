package com.ProjectZuul.Handlers;

import com.ProjectZuul.GUI.GameUI;

import java.io.File;
import javax.media.*;
import javax.media.format.AudioFormat;

/**
 * Handler to process .mp3 files. Used to create sound effects and scary background music.
 *
 * @author Anne Pier Merkus and Richard Ringia
 */
public class SoundHandler {
    /**
     * Instance of the game used to add an OnQuitListener.
     */
    GameUI gameUI;

    /**
     * Check for if the goat sounds is playing
     */
    private boolean screamingGoatIsPlaying = false;

    /**
     * Check for if the last minute sounds is playing
     */
    private boolean lastMinuteIsPlaying = false;

    /**
     * Check for if the you won sounds is playing
     */
    private boolean youWonIsPlaying = false;

    /**
     * Check for if the you lost sounds is playing
     */
    private boolean loseIsPlaying = false;



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
    public void playSound() {
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
    public void playBackgroundSound() {
        try {
            Player player = Manager.createPlayer(new MediaLocator(new File("Sound/BackgroundMusicScary.mp3").toURI().toURL()));
            player.start();
            gameUI.addOnQuitListener(() -> {
                player.stop();
                player.close();
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void playScreamingGoatSound() {
        try {
            if (!this.screamingGoatIsPlaying) {
                Player player = Manager.createPlayer(new MediaLocator(new File("Sound/goat.mp3").toURI().toURL()));
                player.start();
                this.screamingGoatIsPlaying = true;
                gameUI.addOnQuitListener(() -> {
                    player.stop();
                    player.close();
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void playLastMinuteSound() {
        try {
            if (!this.lastMinuteIsPlaying) {
                Player player = Manager.createPlayer(new MediaLocator(new File("Sound/last_minute.mp3").toURI().toURL()));
                player.start();
                this.lastMinuteIsPlaying = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void playLoseSound()
    {
        try {
            if (!this.loseIsPlaying) {
                Player player = Manager.createPlayer(new MediaLocator(new File("Sound/lose_sound.mp3").toURI().toURL()));
                player.start();
                this.loseIsPlaying = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void playYouWonSound() {
        try {
            if (!this.youWonIsPlaying) {
                Player player = Manager.createPlayer(new MediaLocator(new File("Sound/youwon.mp3").toURI().toURL()));
                player.start();
                this.youWonIsPlaying = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}