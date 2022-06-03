package gui;

import java.awt.event.*;
import java.util.Random;
import javax.swing.*;

import logic.Board;

public class Gui extends JFrame implements ActionListener {
    private static final long serialVersionUID = -5479701518838741039L;
    public static int numRows, numColumns, numMines;

    public Gui() {
        this.setTitle("Minesweeper");  // Sets the title of the JFrame
        this.setResizable(false);  // Makes sure the user can't resize the JFrame
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setIconImage(new ImageIcon(getClass().getResource("/images/" + new Random().nextInt(13) + ".png")).getImage());
        this.add(new MainMenuPanel());  // Adds the panel containing the main menu to the JFrame
        this.pack();  // Sets every component to their preferred size
        this.setLocationRelativeTo(null);  // Put's the JFrame at the center of the user's screen

        // Adds Action Listeners to button components
        MainMenuPanel.easyButton.addActionListener(this);
        MainMenuPanel.normalButton.addActionListener(this);
        MainMenuPanel.hardButton.addActionListener(this);
        this.setVisible(true);  // Makes the JFrame visible to the user

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == Board.returnButton) {

            // Switch panels
            switchPanels(new MainMenuPanel());
            // Adds Action Listeners to button components
            MainMenuPanel.easyButton.addActionListener(this);
            MainMenuPanel.normalButton.addActionListener(this);
            MainMenuPanel.hardButton.addActionListener(this);

        }

        // User selected easy difficulty
        else if (e.getSource() == MainMenuPanel.easyButton) {

            // Disable the button
            MainMenuPanel.easyButton.setEnabled(false);

            // Set the Game variables
            numRows = 10;
            numColumns = 8;
            numMines = 10;

            // Switch the main menu panel to the game panel
            switchPanels(new Board());
            Board.returnButton.addActionListener(this);
            Board.levelButton.addActionListener(this);

        }

        // User selected normal difficulty
        else if (e.getSource() == MainMenuPanel.normalButton) {

            MainMenuPanel.normalButton.setEnabled(false);

            numRows = 18;
            numColumns = 14;
            numMines = 40;

            switchPanels(new Board());
            Board.returnButton.addActionListener(this);
            Board.levelButton.addActionListener(this);
        }

        // User selected hard difficulty
        else if (e.getSource() == MainMenuPanel.hardButton) {

            MainMenuPanel.hardButton.setEnabled(false);

            numRows = 24;
            numColumns = 20;
            numMines = 99;

            switchPanels(new Board());
            Board.returnButton.addActionListener(this);
            Board.levelButton.addActionListener(this);
        }

        if (e.getSource() == Board.levelButton) {
            int opt = JOptionPane.showConfirmDialog(null,"Are you sure?","Switch level",JOptionPane.YES_NO_OPTION);
            if (opt == 0){
                // Switch panels
                switchPanels(new MainMenuPanel());
                // Adds Action Listeners to button components
                MainMenuPanel.easyButton.addActionListener(this);
                MainMenuPanel.normalButton.addActionListener(this);
                MainMenuPanel.hardButton.addActionListener(this);
            }
        }
    }

    public void switchPanels(JPanel addPanel) {

        // Remove all components
        this.getContentPane().removeAll();

        // Add the new panel
        this.add(addPanel);

        // Pack the contents and repaint the GUI
        this.pack();
        this.repaint();

    }
}
