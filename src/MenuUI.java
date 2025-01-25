package src;

import java.awt.*;
import javax.swing.*;

/**
 * @brief An abstract object used for the visual representation of the menu
 * @details Uses components provided by the Java Swing API
 */
public class MenuUI extends JFrame {
    // Singleton instance
    private static MenuUI menu = null;

    // UI Components
    private JPanel menuPanel;
    private JButton playButton;
    private JLabel headingLabel, descriptionLabel;

    /**
     * @brief Constructor
     * @details Initializes the frame and its components
     */
    private MenuUI() {
        super("2048");
        initializeFrame();
        initializeComponents();
        layoutComponents();
    }

    /**
     * @brief Sets up the main JFrame properties
     */
    private void initializeFrame() {
        setSize(600, 400);
        setLocationRelativeTo(null); // Center on screen
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
    }

    /**
     * @brief Initializes UI components
     */
    private void initializeComponents() {
        // Main Panel
        menuPanel = new JPanel(new GridBagLayout());
        menuPanel.setBackground(new Color(240, 234, 214)); // Softer background
        menuPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(187, 173, 160), 3),
            BorderFactory.createEmptyBorder(20, 20, 20, 20)
        ));

        // Heading Label
        headingLabel = createLabel("2048", 60, new Color(119, 110, 101)); // Larger font for emphasis

        // Description Label
        descriptionLabel = createLabel(
            "Merge the tiles to reach 2048! Use the arrow keys to play.",
            16,
            new Color(119, 110, 101)
        );

        // Play Button
        playButton = createButton("Play Game", 24, new Color(238, 228, 218), new Color(119, 110, 101));

        // Add hover effects for buttons
        addHoverEffect(playButton,new Color(238, 228, 218));

        // Add scaling effect
        addScalingEffect(playButton);
    }

    /**
     * @brief Lays out components in the main panel
     */
    private void layoutComponents() {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(20, 15, 15, 15);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Heading Label
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        menuPanel.add(headingLabel, gbc);

        // Add a separator
        gbc.gridy = 1;
        menuPanel.add(new JSeparator(SwingConstants.HORIZONTAL), gbc);

        // Description Label
        gbc.gridy = 2;
        menuPanel.add(descriptionLabel, gbc);

        // Play Button
        gbc.gridy = 3;
        gbc.gridwidth = 0;
        gbc.anchor = GridBagConstraints.CENTER;
        menuPanel.add(playButton, gbc);

        add(menuPanel);
    }

    /**
     * @brief Utility method to create a JLabel
     */
    private JLabel createLabel(String text, int fontSize, Color color) {
        JLabel label = new JLabel(text, SwingConstants.CENTER);
        label.setFont(new Font("Helvetica Neue", Font.BOLD, fontSize));
        label.setForeground(color);
        return label;
    }

    /**
     * @brief Utility method to create a JButton
     */
    private JButton createButton(String text, int fontSize, Color bgColor, Color fgColor) {
        JButton button = new JButton(text);
        button.setFont(new Font("Helvetica Neue", Font.BOLD, fontSize));
        button.setBackground(bgColor);
        button.setForeground(fgColor);
        button.setFocusPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(187, 173, 160), 2),
            BorderFactory.createEmptyBorder(10, 20, 10, 20)
        ));
        button.setOpaque(true);
        return button;
    }

    /**
     * @brief Adds a hover effect to a JButton
     */
    private void addHoverEffect(JButton button, Color hoverColor) {
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(hoverColor);
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(new Color(238, 228, 218));
            }
        });
    }

    /**
     * @brief Adds a scaling effect to a JButton
     */
    private void addScalingEffect(JButton button) {
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setFont(button.getFont().deriveFont(26f));
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setFont(button.getFont().deriveFont(24f));
            }
        });
    }

    /**
     * @brief Public static method for obtaining a single instance
     * @return a MenuUI object
     */
    public static MenuUI getInstance() {
        if (menu == null) {
            menu = new MenuUI();
        }
        return menu;
    }

    public JButton getPlayButton() {
        return playButton;
    }
}
