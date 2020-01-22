package com.ProjectZuul.GUI.Components;

import com.ProjectZuul.GUI.GameUI;
import com.ProjectZuul.GUI.Listeners.SetInactiveListener;
import com.ProjectZuul.Handlers.ActionHandler;
import com.ProjectZuul.Handlers.InventoryHandler;
import com.ProjectZuul.Models.Item;
import com.ProjectZuul.Models.Player;

import javax.swing.*;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import java.awt.*;

/**
 * The JPanel for Inventory. This class generates all components within the inventory
 *
 * @author Richard Ringia
 */
public class Inventory extends JPanel implements SetInactiveListener {

    /**
     * The position x in the JFrame
     */
    private int positionX = 600;
    /**
     * The position y in the JFrame
     */
    private int positionY = 400;
    /**
     * The width of the JPanel
     */
    private int width = 560;
    /**
     * The height of the JPanel
     */
    private int height = 150;

    /**
     * The inner JPanel where all the components will be stored
     */
    private JPanel innerPanel;

    /**
     * The grid rows for the innerPanel
     */
    private int gridRows = 3;
    /**
     * The grid cols for the innerPanel
     */
    private int gridCols = 5;

    /**
     * The player of the game
     */
    private Player player;

    /**
     * The label where the total weight will be displayed
     */
    private JLabel jLabelTotalWeight;

    /**
     * Instantiates a new Inventory.
     *
     * @param player the player of the game
     */
    public Inventory(Player player) {
        this.player = player;
        this.setBounds(this.positionX, this.positionY, this.width, this.height);
        this.init();
    }

    /**
     * Instantiates a new Inventory.
     * With this function, a custom Rectangle will be added
     *
     * @param player    the player of the game
     * @param rectangle custom rectangle for the inventory
     */
    public Inventory(Player player, Rectangle rectangle) {
        this.player = player;
        this.positionX = rectangle.x;
        this.positionY = rectangle.y;
        this.width = rectangle.width;
        this.height = rectangle.height;
        this.setBounds(rectangle);
        this.init();
    }

    /**
     * Initialise the inventory.
     * The layout of this will be set and the innerPanel will be created
     */
    private void init() {
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.setBackground(Color.BLACK);
        this.innerPanel = new JPanel(new GridLayout(this.gridRows, this.gridCols));
        this.innerPanel.setBackground(Color.BLACK);
        this.innerPanel.setPreferredSize(new Dimension(this.width, this.height - 25));
        this.innerPanel.setBorder(new CompoundBorder(BorderFactory.createLineBorder(Color.WHITE), new EmptyBorder(10, 10, 10, 10)));
        this.innerPanel.setLocation(50, 50);
        this.createInfoLabel();
        this.add(this.innerPanel);
    }

    /**
     *
     */
    private void createInfoLabel() {
        JPanel labelFrame = new JPanel();
        labelFrame.setBackground(Color.BLACK);
        labelFrame.setPreferredSize(new Dimension(this.width, 25));
        JLabel label = new JLabel("Inventory");
        label.setLocation(0, 0);
        label.setForeground(Color.WHITE);
        labelFrame.add(label);
        this.jLabelTotalWeight = new JLabel(this.getTotalWeightText());
        jLabelTotalWeight.setForeground(Color.GRAY);
        labelFrame.add(jLabelTotalWeight);
        this.add(labelFrame);
    }

    private String getTotalWeightText() {
        return this.player.getTotalWeight() + " of " + this.player.getMaxWeight() + " Kg is used";
    }

    private void updateTotalWeight() {
        this.jLabelTotalWeight.setText(this.getTotalWeightText());
    }

    /**
     * Add item.
     *
     * @param item the item
     */
    public void addItem(Item item) {
        MyButton jButton = new MyButton(item.getName(), Color.BLACK, Color.WHITE, new Dimension(140, 30));
        jButton.addActionListener(l -> {
            this.player.getActionHandler().createMenu(e -> {
                this.player.getInventoryHandler().dropItem(item);
                this.innerPanel.remove(jButton);
                this.updateTotalWeight();
                this.updateUI();
            }, "Drop");
        });
        this.updateTotalWeight();
        this.innerPanel.add(jButton);
        this.updateUI();
//        JLabel label = new JLabel(item.getName());
//        label.setForeground(Color.WHITE);
//        this.updateTotalWeight();
//        this.innerPanel.add(label);
//        this.updateUI();
    }

    @Override
    public void setMenuVisibility(boolean visibility) {
        this.setVisible(visibility);
    }
}
