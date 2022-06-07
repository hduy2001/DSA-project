package logic;

import gui.Gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;

public class Board extends JPanel implements MouseListener {

    // Game Variables
    private final int TILE_SIZE = 30; //Holds the size of each tile in pixels
    private final int EMPTY_TILE = 0; // Represents the value
    private final int FLAG = 10; // Represents the value of a flagged tile
    private final int MINE = 9; // Represents the value of a mine
    private int flagsLeft; // Holds the amount of flags the user has available
    private int mouseX, mouseY; 
    // Arrays
    private int board[][]; // Contains the location of every mine and each tile's respective numeric value
    private boolean covered[][]; // Contains if a certain cell is covered or uncovered
    private boolean checked[][]; // Contains if a cell has been checked or not (used only for method: checkAdjacentCells)

    // Game Flags
    private boolean noMinesLeft = false; // Boolean flag that represents if no mines are remaining
    private boolean noCoveredTilesLeft = false; // Boolean flag that represents if there are no covered tiles left
    public boolean gameLost = false; // Boolean flag that triggers whenever a user hits a mine
    private boolean firstClick = false; // Boolean flag that triggers when the user makes a first click

    // Misc. Variables
    private final int NUM_IMAGES = 14; // Holds the amount of images
    private final ImageIcon[] images = initImages(); // An array that holds all the images in the image folder
    private final int BAR_HEIGHT = 40; // The height of the GUI bar at the top of the screen
    boolean repaint = true;  // Flags if the paint component should repaint itself

    // Private Component Declarations
    private JPanel topBar;
    private JPanel game;
    private JLabel flagLabel, timeLabel;
    private JLabel gameStatusLabel;

    // Public Component Declarations
    public static JButton returnButton;
    public static JButton levelButton;

    private java.util.Map<String, Image> image;
    /*
     * JPanel Constructor
     */
    public Board() {

        // Set the remaining amount of flags to the amount of mines
        flagsLeft = Gui.numMines;

        // Set the size of the arrays
        covered = new boolean[Gui.numRows][Gui.numColumns];
        board = new int[Gui.numRows][Gui.numColumns];
        checked = new boolean[Gui.numRows][Gui.numColumns];

        // Initiate the arrays
        for (int i = 0; i < Gui.numColumns; i++) {
            for (int j = 0; j < Gui.numRows; j++) { // Traverse through each cell

                // Set every tile covered
                covered[j][i] = true;
                // Set every tile's value to 0
                board[j][i] = 0;
                // Set every tile to unchecked
                checked[j][i] = false;

            }
        }

        // Initiate the components in the panel
        initComponents();

        // Panel properties
        this.setPreferredSize(new Dimension(Gui.numRows * TILE_SIZE, Gui.numColumns * TILE_SIZE + BAR_HEIGHT)); // Sets the preferred size of the panel
        this.setLayout(new BorderLayout()); // Sets the layout of the panel

        // Add the components to the panel
        this.add(topBar, BorderLayout.NORTH);
        this.add(game, BorderLayout.SOUTH);

        // Add a mouse listener to the game panel
        game.addMouseListener(this);

    }

    /*
     * Paint method that constantly runs to update the panel Return type: returns
     * nothing (void)
     *
     * @param g: the graphics object
     */
    @Override
    public void paint(Graphics g) {

        // Prevents the graphics object from overwriting all the components in the panel
        super.paint(g);

        // Set the game flags to true
        noMinesLeft = true;
        noCoveredTilesLeft = true;
        // Traverse board array and update covered cells and flagged cells
        for (int i = 0; i < Gui.numColumns; i++) {
            for (int j = 0; j < Gui.numRows; j++) {

                // The current tile has a value greater than a flag's value and is covered
                if (board[j][i] >= FLAG && covered[j][i] == true) {

                    // Draw a flag
                    g.drawImage(images[11].getImage(), j * TILE_SIZE, i * TILE_SIZE + BAR_HEIGHT, null);

                }

                // The current tile has a less than a flag's value and is covered
                if (board[j][i] < FLAG && covered[j][i] == true) {

                    // Draw an empty tile
                    g.drawImage(images[10].getImage(), j * TILE_SIZE, i * TILE_SIZE + BAR_HEIGHT, null);

                }
                // The current tile is a mine and the noMinesLeft flag is true
                if (board[j][i] == MINE && noMinesLeft == true) {

                    // Set the noMinesLeft flag to false
                    noMinesLeft = false;

                }

            }
        }

        // Traverse covered array for covered/uncovered cells
        for (int i = 0; i < Gui.numColumns; i++) {
            for (int j = 0; j < Gui.numRows; j++) {

                // The current tile is uncovered
                if (covered[j][i] == false) {

                    // Draw a tile respective to the tile's value
                    g.drawImage(images[board[j][i]].getImage(), j * TILE_SIZE, i * TILE_SIZE + BAR_HEIGHT, null);

                }

                // The current tile is covered and isn't a flagged mine
                if (covered[j][i] == true && board[j][i] != MINE + FLAG && noCoveredTilesLeft == true) {

                    // Set the noCoveredTilesLeft flag to false
                    noCoveredTilesLeft = false;

                }

            }
        }

        // Runs if the user hit a mine and the game is over
        if (gameLost) {

            // Set the label visible
            gameStatusLabel.setVisible(true);

            // Set other mines and false flags visible
            for (int i = 0; i < Gui.numColumns; i++) {
                for (int j = 0; j < Gui.numRows; j++) {

                    // Current tile is a mine
                    if (board[j][i] == MINE) {
                        // Draw the mine
                        g.drawImage(images[9].getImage(), j * TILE_SIZE, i * TILE_SIZE + BAR_HEIGHT, null);
                    }

                    // Current tile is a false flagged tile
                    else if (board[j][i] >= FLAG && board[j][i] != MINE + FLAG) {
                        // Draw a false flag
                        g.drawImage(images[12].getImage(), j * TILE_SIZE, i * TILE_SIZE + BAR_HEIGHT, null);
                    }

                }
            }

            // Set return button visible and enabled
            repaint = false;
            returnButton.setVisible(true);
            returnButton.setEnabled(true);
            levelButton.setEnabled(false);

        }

        // User won the game!
        else if (noMinesLeft == true && noCoveredTilesLeft == true) {
            // Display a message to the user
            gameStatusLabel.setText("You win!");
            gameStatusLabel.setForeground(Color.GREEN);
            gameStatusLabel.setVisible(true);

            // Set return button visible and enabled
            repaint = false;
            returnButton.setVisible(true);
            returnButton.setEnabled(true);
            levelButton.setEnabled(false);

        }

        if (repaint) {
            // Repaint the GUI
            this.repaint();
        }

        // Update flagsLeft label
        flagLabel.setText(String.valueOf(flagsLeft));

    }

    /*
     * Runs whenever a mouse is clicked Return type: returns nothing (void)
     * Return type: Returns nothing (void)
     * @param e: the mouse event
     */
    @Override
    public void mousePressed(MouseEvent e) {

        // Get the current mouse coordinates respective to each tile
        mouseX = (int) (e.getX() / TILE_SIZE);
        mouseY = (int) (e.getY() / TILE_SIZE);

        // User right clicked
        if (e.getButton() == MouseEvent.BUTTON3 && !gameLost && covered[mouseX][mouseY] == true && firstClick == true) {

            // Remove exiting flag
            if (board[mouseX][mouseY] >= FLAG) {
                board[mouseX][mouseY] -= FLAG;
                flagsLeft++;
            }
            // Add a flag
            else if (board[mouseX][mouseY] < FLAG && flagsLeft > 0) {
                board[mouseX][mouseY] += FLAG;
                flagsLeft--;
            }

        }

        // User left clicked
        if (e.getButton() == MouseEvent.BUTTON1 && !gameLost) {

            // Checks that the user isn't clicking on a flagged tile or an uncovered tile
            if (covered[mouseX][mouseY] == true && board[mouseX][mouseY] < FLAG) {

                // If the user hit a mine, set the gameLost flag to true
                if (board[mouseX][mouseY] == MINE) {
                    gameLost = true;
                }

                // The user hit an empty tile
                else if (board[mouseX][mouseY] == EMPTY_TILE) {

                    // Runs if this is the user's first click
                    if (!firstClick) {

                        // Set the current tile to uncovered
                        covered[mouseX][mouseY] = false;
                        // Set the flag firstClick to true
                        firstClick = true;
                        // Initiate the board
                        board = initBoard(Gui.numRows, Gui.numColumns, Gui.numMines);

                    }

                    // search adjacent cells and uncover them
                    revealAdjacentCells(mouseX, mouseY);

                }

                // Runs if the user hit a numeric tile
                else {

                    // Uncover the tile
                    covered[mouseX][mouseY] = false;

                }

            }

        }

    }

    // Unused mouseEvent methods
    public void mouseReleased(MouseEvent e) {
        // TODO
    }

    public void mouseEntered(MouseEvent e) {
        // TODO
    }

    public void mouseExited(MouseEvent e) {
        // TODO
    }

    public void mouseClicked(MouseEvent e) {
        // TODO
    }

    /*
     * Initializes the board array Return type: Returns a 2D integer array (int[][])
     * @param rows: the amount of rows in the game
     * @param columns: the amount of columns in the game
     * @param mines: the amount of mines in the game
     */
    public int[][] initBoard(int rows, int columns, int mines) {

        // Create a new 2D integer array
        int[][] field = new int[rows][columns];
        // Create a 2D boolean array that contains spots where a mine won't be able to
        // generate
        boolean[][] noMine = new boolean[rows][columns];

        int minesLeft = mines;
        int randomX, randomY; // Contains the random coordinates for choosing mines

        // Random Object
        Random randObj = new Random();

        // Flag adjacent spots where no mines can generate
        for (int i = 0; i < columns; i++) {
            for (int j = 0; j < rows; j++) {

                // Runs if the current tile is uncovered
                if (covered[j][i] == false) {

                    // no mine can generate on the uncovered tile
                    noMine[j][i] = true;

                    if (j > 0) { // Left tile
                        noMine[j - 1][i] = true;
                    }
                    if (j < rows - 1) { // Right tile
                        noMine[j + 1][i] = true;
                    }
                    if (i > 0) { // Top tile
                        noMine[j][i - 1] = true;
                    }
                    if (i < columns - 1) { // Bottom tile
                        noMine[j][i + 1] = true;
                    }
                    if (i > 0 && j > 0) { // Top-left tile
                        noMine[j - 1][i - 1] = true;
                    }
                    if (i > 0 && j < rows - 1) { // Top-right tile
                        noMine[j + 1][i - 1] = true;
                    }
                    if (i < columns - 1 && j > 0) { // Bottom-left tile
                        noMine[j - 1][i + 1] = true;
                    }
                    if (i < columns - 1 && j < rows - 1) { // Bottom-right tile
                        noMine[j + 1][i + 1] = true;
                    }

                }

            }
        }

        // Fill random spots with mines until there are no mines left to be placed
        while (minesLeft > 0) {

            // Generate a new random coordinate
            randomX = randObj.nextInt(rows);
            randomY = randObj.nextInt(columns);

            // Runs if the current tile is not a mine and can be placed down
            if (field[randomX][randomY] != MINE && noMine[randomX][randomY] == false) {

                field[randomX][randomY] = MINE;
                minesLeft--;

            }

        }

        // Assign numbers to tiles based on the mines around them
        for (int i = 0; i < columns; i++) {
            for (int j = 0; j < rows; j++) {

                // Mine found
                if (field[j][i] == MINE) {

                    // Check adjacent cells and increase their value by one if they're not a mine
                    if (j > 0 && field[j - 1][i] != MINE) { // Left
                        field[j - 1][i]++;
                    }
                    if (j < rows - 1 && field[j + 1][i] != MINE) { // Right
                        field[j + 1][i]++;
                    }
                    if (i > 0 && field[j][i - 1] != MINE) { // Top
                        field[j][i - 1]++;
                    }
                    if (i < columns - 1 && field[j][i + 1] != MINE) { // Bottom
                        field[j][i + 1]++;
                    }
                    if (i > 0 && j > 0 && field[j - 1][i - 1] != MINE) { // Top-left
                        field[j - 1][i - 1]++;
                    }
                    if (i > 0 && j < rows - 1 && field[j + 1][i - 1] != MINE) { // Top-right
                        field[j + 1][i - 1]++;
                    }
                    if (i < columns - 1 && j > 0 && field[j - 1][i + 1] != MINE) { // Bottom-left
                        field[j - 1][i + 1]++;
                    }
                    if (i < columns - 1 && j < rows - 1 && field[j + 1][i + 1] != MINE) { // Bottom-right
                        field[j + 1][i + 1]++;
                    }

                }

            }
        }

        // Return the array containing all mines and numeric tiles
        return field;

    }

    /*
     * Initates all components in the game panel Return type: Returns nothing (void)
     */
    public void initComponents() {

        // Panels
        topBar = new JPanel();
        topBar.setBackground(Color.GRAY);
        topBar.setPreferredSize(new Dimension(TILE_SIZE * Gui.numRows, BAR_HEIGHT));
        topBar.setLayout(new BorderLayout());

        game = new JPanel();
        game.setPreferredSize(new Dimension(TILE_SIZE * Gui.numRows, TILE_SIZE * Gui.numColumns));
        game.setLayout(new GridBagLayout());

        // Buttons
        returnButton = new JButton();
        returnButton.setText("Main Menu");
        returnButton.setForeground(Color.BLACK);
        returnButton.setFont(new Font(returnButton.getFont().getName(), Font.PLAIN, 20));
        returnButton.setPreferredSize(new Dimension(150, 40));
        returnButton.setFocusable(false);
        returnButton.setEnabled(false);
        returnButton.setVisible(false);

        // Labels
        flagLabel = new JLabel();
        flagLabel.setIcon(images[11]);
        flagLabel.setFont(new Font(flagLabel.getFont().getName(), Font.PLAIN, 20));
        flagLabel.setText(String.valueOf(flagsLeft));
        flagLabel.setForeground(Color.BLACK);

        levelButton = new JButton();
        levelButton.setText("Switch Level");
        levelButton.setForeground(Color.BLACK);
        levelButton.setFont(new Font(levelButton.getFont().getName(), Font.PLAIN, 20));
        levelButton.setPreferredSize(new Dimension(70, 40));
        levelButton.setVisible(true);

        gameStatusLabel = new JLabel();
        gameStatusLabel.setText("Game Over!");
        gameStatusLabel.setVisible(false);
        gameStatusLabel.setFont(new Font(gameStatusLabel.getFont().getName(), Font.PLAIN, 20));
        gameStatusLabel.setForeground(Color.RED);

        // Add components
        topBar.add(flagLabel, BorderLayout.WEST);
        topBar.add(levelButton, BorderLayout.CENTER);
        topBar.add(gameStatusLabel, BorderLayout.EAST);
        game.add(returnButton);

    }

    /*
     * Returns an ImageIcon array containing all the images in the image folder
     * Return type: Returns an ImageIcon array (ImageIcon[])
     */
    public ImageIcon[] initImages() {

        // Original images
        ImageIcon[] images = new ImageIcon[NUM_IMAGES];
        // Scaled images
        Image[] scaledImages = new Image[NUM_IMAGES];
        // Resized images
        ImageIcon[] resizedImages = new ImageIcon[NUM_IMAGES];

        for (int i = 0; i < NUM_IMAGES; i++) {

            images[i] = new ImageIcon(getClass().getResource("/images/" + i + ".png"));
            scaledImages[i] = images[i].getImage().getScaledInstance(TILE_SIZE, TILE_SIZE, java.awt.Image.SCALE_SMOOTH);
            resizedImages[i] = new ImageIcon(scaledImages[i]);

        }

        // Returns the resized images based on TILE_SIZE
        return resizedImages;

    }

    /*
     * A basic recursive method that reveals nearby tiles Return type: Returns nothing (void)
     * @param posX: the x-coordinate to reveal
     * @param posY: the y-coordinate to reveal
     */
    private void revealAdjacentCells(int posX, int posY) {

        // Current tile hasn't been checked yet
        if (!checked[posX][posY]) {

            // Uncover the current tile and mark it as checked
            covered[posX][posY] = false;
            checked[posX][posY] = true;

            // Runs if the current tile is an empty tile (Check adjacent cells)
            if (board[posX][posY] == 0) {

                // Checks adjacent cells and the method calls itself again
                if (posX > 0 && !checked[posX - 1][posY] && board[posX - 1][posY] < FLAG) { // Left
                    revealAdjacentCells(posX - 1, posY);
                }
                if (posX < Gui.numRows - 1 && !checked[posX + 1][posY] && board[posX + 1][posY] < FLAG) { // Right
                    revealAdjacentCells(posX + 1, posY);
                }
                if (posY > 0 && !checked[posX][posY - 1] && board[posX][posY - 1] < FLAG) { // Top
                    revealAdjacentCells(posX, posY - 1);
                }
                if (posY < Gui.numColumns - 1 && !checked[posX][posY + 1] && board[posX][posY + 1] < FLAG) { // Bottom
                    revealAdjacentCells(posX, posY + 1);
                }
                if (posY > 0 && posX > 0 && !checked[posX - 1][posY - 1] && board[posX - 1][posY - 1] < FLAG) { // Top-left
                    revealAdjacentCells(posX - 1, posY - 1);
                }
                if (posY > 0 && posX < Gui.numRows - 1 && !checked[posX + 1][posY - 1]
                        && board[posX + 1][posY - 1] < FLAG) { // Top-right
                    revealAdjacentCells(posX + 1, posY - 1);
                }
                if (posY < Gui.numColumns - 1 && posX > 0 && !checked[posX - 1][posY + 1]
                        && board[posX - 1][posY + 1] < FLAG) { // Bottom-left
                    revealAdjacentCells(posX - 1, posY + 1);
                }
                if (posY < Gui.numColumns - 1 && posX < Gui.numRows - 1 && !checked[posX + 1][posY + 1]
                        && board[posX + 1][posY + 1] < FLAG) { // Bottom-right
                    revealAdjacentCells(posX + 1, posY + 1);
                }

            }

        }

    }
}
