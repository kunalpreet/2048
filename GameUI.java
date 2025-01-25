package src;

import java.awt.*;
import javax.swing.*;

/**
 * @brief A class for visual representation of the 2048 game
 * @details Uses components provided by the Java Swing API
 */
public class GameUI extends JFrame {
    // State Variables
    private JButton backToMenuButton;
    private JLabel[][] grid;
    private JPanel gamePanel, topPanel;
    private JLabel scoreLabel;

    /**
     * @brief Constructor
     * @details Initializes the frame and its components, setting the size dynamically based on board size
     */
    public GameUI() {
        super("2048");
        int size = 4;
        setSize(size * 130, size * 150); // Dynamic sizing based on grid
        setLayout(new BorderLayout());
        setResizable(false);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        initializeTopPanel();
        initializeGamePanel(size);
        initializeBackButton();

        add(topPanel, BorderLayout.NORTH);
        add(gamePanel, BorderLayout.CENTER);
        add(backToMenuButton, BorderLayout.SOUTH);
    }

    /**
     * @brief Initializes the top panel (e.g., for score display)
     */
    private void initializeTopPanel() {
        topPanel = new JPanel();
        topPanel.setBackground(new Color(143, 122, 102));
        topPanel.setLayout(new BorderLayout());
        topPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        scoreLabel = new JLabel("Score: ", SwingConstants.CENTER);
        scoreLabel.setFont(new Font("Helvetica Neue", Font.BOLD, 30));
        scoreLabel.setForeground(Color.WHITE);

        topPanel.add(scoreLabel, BorderLayout.CENTER);
    }

    /**
     * @brief Initializes the game grid panel
     */
    private void initializeGamePanel(int size) {
        gamePanel = new JPanel(new GridLayout(size, size, 10, 10));
        gamePanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        gamePanel.setBackground(new Color(187, 173, 160));
        grid = new JLabel[size][size];

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                JLabel cell = new JLabel("", SwingConstants.CENTER);
                cell.setOpaque(true);
                cell.setBackground(tileColor(0));
                cell.setFont(new Font("Helvetica Neue", Font.BOLD, 55));
                cell.setForeground(Color.DARK_GRAY);
                cell.setBorder(BorderFactory.createLineBorder(new Color(187, 173, 160), 2));
                cell.setPreferredSize(new Dimension(80, 80));
                grid[i][j] = cell;
                gamePanel.add(cell);
            }
        }
    }

    /**
     * @brief Initializes the back-to-menu button
     */
    private void initializeBackButton() {
        backToMenuButton = new JButton("Menu");
        backToMenuButton.setFocusable(false);
        backToMenuButton.setFont(new Font("Helvetica Neue", Font.BOLD, 20));
        backToMenuButton.setBackground(new Color(143, 122, 102));
        backToMenuButton.setForeground(Color.black);
        backToMenuButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        backToMenuButton.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Add hover effect
        backToMenuButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                backToMenuButton.setBackground(new Color(168, 149, 132));
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                backToMenuButton.setBackground(new Color(143, 122, 102));
            }
        });
    }

    /**
     * @brief Updates the grid based on the current board state
     * @param board The current board state
     */
    public void updateGrid(int[][] board, int score) {
        int size = 4;
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                int value = board[i][j];
                JLabel cell = grid[i][j];

                if (value == 0) {
                    cell.setText("");
                } else {
                    cell.setText(String.valueOf(value));
                }

                cell.setBackground(tileColor(value));
                cell.setFont(tileFont(value));
                cell.setForeground(value > 4 ? Color.WHITE : Color.DARK_GRAY);
            }
        }
        scoreLabel.setText("Score: " + score);
    }

    /**
     * @brief Maps tile values to colors
     * @param tile The tile value
     * @return The color associated with the tile value
     */
    private Color tileColor(int tile) {
        return switch (tile) {
            case 0 -> new Color(204, 192, 179);
            case 2 -> new Color(238, 228, 218);
            case 4 -> new Color(237, 224, 200);
            case 8 -> new Color(242, 177, 121);
            case 16 -> new Color(245, 149, 99);
            case 32 -> new Color(246, 124, 95);
            case 64 -> new Color(246, 94, 59);
            case 128 -> new Color(237, 207, 114);
            case 256 -> new Color(237, 204, 97);
            case 512 -> new Color(237, 200, 80);
            case 1024 -> new Color(237, 197, 63);
            case 2048 -> new Color(237, 194, 46);
            default -> new Color(60, 58, 50);
        };
    }

    /**
     * @brief Maps tile values to fonts
     * @param tile The tile value
     * @return The font size associated with the tile value
     */
    private Font tileFont(int tile) {
        return new Font("Helvetica Neue", Font.BOLD, tile > 512 ? 40 : 50);
    }

    /**
     * @brief Gets the button for returning to the menu
     * @return The back-to-menu button
     */
    public JButton getBackToMenuButton() {
        return backToMenuButton;
    }
}
