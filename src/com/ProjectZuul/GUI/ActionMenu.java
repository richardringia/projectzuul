package com.ProjectZuul.GUI;

import com.ProjectZuul.Handlers.LanguageHandler;
import com.ProjectZuul.Models.Player;

import javax.swing.*;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import java.awt.*;


/**
 * The type Action menu.
 */
public class ActionMenu extends JPanel {

    /**
     * The height of the panel
      */
    private int height = 200;

    /**
     * The width of the panel
     */
    private int width = 150;

    /**
     * The inner action panel where the action will be stored
     */
    private JPanel actionPanel;

    /**
     * Handles all the text in the application with the right language
     */
    private LanguageHandler languageHandler;

    /**
     * Instantiates a new Action menu.
     *
     * @param player the player of the game
     */
    public ActionMenu(Player player) {
        this.languageHandler = player.getLanguageHandler();
        this.setBounds(600, 20, 150, 200);
        this.setBackground(Color.BLACK);
        this.setLayout(null);
        this.setBorder(BorderFactory.createLineBorder(Color.WHITE));
        this.createTitleLabel();
        this.createActionMenuPanel();
    }


    /**
     * Create an title label for the action menu
     * Add the action menu to the super (JPanel)
     */
    private void createTitleLabel() {
        JLabel jLabel = new JLabel(languageHandler.get("GAME_ACTION_MENU_TITLE"));
        jLabel.setBounds(0, 0, 150, 30);
        jLabel.setForeground(Color.WHITE);
        jLabel.setHorizontalAlignment(SwingConstants.CENTER);
        jLabel.setVerticalAlignment(SwingConstants.CENTER);
        jLabel.setBorder(BorderFactory.createLineBorder(Color.WHITE));
        super.add(jLabel);
    }

    /**
     * Create the action panel where the buttons will be placed in.
     * Add the action panel to the super (JPanel)
     */
    private void createActionMenuPanel() {
        this.actionPanel = new JPanel();
        this.actionPanel.setBounds(0, 30, this.width, this.height - 30);
        this.actionPanel.setBorder(new CompoundBorder(BorderFactory.createLineBorder(Color.WHITE),new EmptyBorder(10, 5, 10, 5)));
        this.actionPanel.setBackground(Color.BLACK);
        this.actionPanel.setLayout(new GridLayout(3, 1, 10, 10));
        super.add(this.actionPanel);
    }

    /**
     * When a new component needs to be added to this panel. It need to be placed in the action panel.
     * Therefore a custom add function is created
     * @param component the component that needs to be added
     * @return the created component
     */
    public Component add(Component component) {
        return this.actionPanel.add(component);
    }

    /**
     * Reset the action menu, so remove all the buttons and update the ui
     */
    public void reset() {
        this.actionPanel.removeAll();
        this.actionPanel.updateUI();
    }
}
