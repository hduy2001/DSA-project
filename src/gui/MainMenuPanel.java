package gui;

import javax.swing.*;
import java.awt.*;

public class MainMenuPanel extends JPanel {
    // Private Component Declarations
    private JPanel headingPanel;
    private JPanel presetDifficultyPanel;
    private JLabel heading;
    private JLabel presetDifficultyLabel;

    // Public Component Declarations
    public static JButton easyButton, normalButton, hardButton;

    // Panel Variables
    private final int WIDTH = 600;
    private final int HEIGHT = 350;

    // Button Variables
    private final int BUTTON_WIDTH = 150;
    private final int BUTTON_HEIGHT = 40;

    public MainMenuPanel() {

        // Initiate all the components in the panel
        initComponents();

        // Panel Properties
        this.setPreferredSize(new Dimension(WIDTH, HEIGHT));  // Sets the preferred size of the JPanel
        this.setBackground(Color.GRAY);  // Sets the background color to gray
        this.setLayout(new BorderLayout());  // Sets the border layout for the panel

        // Add Components to Panel
        this.add(headingPanel, BorderLayout.NORTH);
        this.add(presetDifficultyPanel, BorderLayout.WEST);


    }

    /*
     * Creates all the components in the panel
     */
    public void initComponents() {
        // Panels
        headingPanel = new JPanel();
        headingPanel.setPreferredSize(new Dimension(WIDTH, HEIGHT / 5));
        headingPanel.setBackground(Color.GRAY);
        headingPanel.setLayout(new GridBagLayout()); // Layout that helps center the text

        presetDifficultyPanel = new JPanel();
        presetDifficultyPanel.setPreferredSize(new Dimension(WIDTH, HEIGHT / 5));
        presetDifficultyPanel.setBackground(Color.GRAY);
        presetDifficultyPanel.setLayout(new GridBagLayout());

        // Buttons
        easyButton = new JButton();
        easyButton.setText("Easy");
        easyButton.setPreferredSize(new Dimension(BUTTON_WIDTH, BUTTON_HEIGHT));
        easyButton.setBounds(new Rectangle(120, 80, BUTTON_WIDTH, BUTTON_HEIGHT));
        easyButton.setForeground(Color.GREEN);
        easyButton.setFocusable(false); // Makes sure the button can't be focused (very bad looking)
        easyButton.setFont(new Font(easyButton.getFont().getName(), Font.PLAIN, 20));

        normalButton = new JButton();
        normalButton.setText("Normal");
        normalButton.setPreferredSize(new Dimension(BUTTON_WIDTH, BUTTON_HEIGHT));
        normalButton.setBounds(new Rectangle(120, 140, BUTTON_WIDTH, BUTTON_HEIGHT));
        normalButton.setForeground(Color.BLUE);
        normalButton.setFocusable(false); // Makes sure the button can't be focused (very bad looking)
        normalButton.setFont(new Font(normalButton.getFont().getName(), Font.PLAIN, 20));

        hardButton = new JButton();
        hardButton.setText("Hard");
        hardButton.setPreferredSize(new Dimension(BUTTON_WIDTH, BUTTON_HEIGHT));
        hardButton.setBounds(new Rectangle(120, 200, BUTTON_WIDTH, BUTTON_HEIGHT));
        hardButton.setForeground(Color.RED);
        hardButton.setFocusable(false); // Makes sure the button can't be focused (very bad looking)
        hardButton.setFont(new Font(hardButton.getFont().getName(), Font.PLAIN, 20));

        // Labels
        heading = new JLabel();
        heading.setText("Minesweeper");
        heading.setFont(new Font(heading.getFont().getName(), Font.PLAIN, 50));
        heading.setForeground(Color.BLACK);

        presetDifficultyLabel = new JLabel();
        presetDifficultyLabel.setText("Select a Difficulty: ");
        presetDifficultyLabel.setFont(new Font(presetDifficultyLabel.getFont().getName(), Font.PLAIN, 20));
        presetDifficultyLabel.setForeground(Color.BLACK);
        presetDifficultyLabel.setBounds(120, 0, 200, 50);

        // Add Components
        headingPanel.add(heading);
        presetDifficultyPanel.add(presetDifficultyLabel);
        presetDifficultyPanel.add(easyButton);
        presetDifficultyPanel.add(normalButton);
        presetDifficultyPanel.add(hardButton);
    }



}

