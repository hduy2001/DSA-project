package gui;

import java.awt.event.*;
import java.util.Random;
import javax.swing.*;

import logic.Board;

public class Gui extends JFrame implements ActionListener {
    private static final long serialVersionUID = -5479701518838741039L;
    public static int numRows, numColumns, numMines;

    public Gui() {
        this.setTitle("Minesweeper");
        this.setResizable(false);  
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setIconImage(new ImageIcon(getClass().getResource("/images/" + new Random().nextInt(13) + ".png")).getImage());
        this.add(new MainMenuPanel());  
        this.pack();  
        this.setLocationRelativeTo(null);  

        // Adds Action Listeners to button components
        MainMenuPanel.easyButton.addActionListener(this);
        MainMenuPanel.normalButton.addActionListener(this);
        MainMenuPanel.hardButton.addActionListener(this);
        this.setVisible(true);

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
                MainMenuPanel.easyButton.addActionListener(this);
                MainMenuPanel.normalButton.addActionListener(this);
                MainMenuPanel.hardButton.addActionListener(this);
            }
        }
    }

    public void switchPanels(JPanel addPanel) {
        this.getContentPane().removeAll();
        this.add(addPanel);
        this.pack();
        this.repaint();
    }
}
