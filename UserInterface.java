/**
 * @File: UserInterface.java
 * @Description: A view module implementing KeyListener and ActionListener interfaces.
 */

 package src;

 // Import necessary Java libraries
 import static javax.swing.JOptionPane.showMessageDialog;
 
 import java.awt.event.ActionEvent;
 import java.awt.event.ActionListener;
 import java.awt.event.KeyEvent;
 import java.awt.event.KeyListener;
 
 /**
  * A class that handles the user interface for the application.
  * Manages both the game view and the menu, responding to user interactions.
  */
 public class UserInterface implements KeyListener, ActionListener {
 
     // Instance variables
     private ViewListener listener;
     private GameUI game;
     private MenuUI menu;
     private static UserInterface instance = null;
 
     /**
      * Private constructor to initialize the interface.
      * Sets up event listeners for the menu and game components.
      */
     private UserInterface() {
         game = new GameUI();
         menu = MenuUI.getInstance();
 
         game.addKeyListener(this);
         game.getBackToMenuButton().addActionListener(this);
 
         menu.getPlayButton().addActionListener(this);
     }
 
     /**
      * Switches the display to the menu view.
      */
     public void switchToMenu() {
         game.setVisible(false);
         menu.setVisible(true);
     }
 
     /**
      * Switches the display to the game view.
      */
     public void switchToGame() {
         game.setVisible(true);
         menu.setVisible(false);
     }
 
     /**
      * Checks if either the game view or menu is currently visible.
      * 
      * @return true if either view is visible, false otherwise.
      */
     public boolean isDisplaying() {
         return game.isVisible() || menu.isVisible();
     }
 
     /**
      * Assigns a listener to handle user interactions.
      * 
      * @param listener The listener to be notified of UI events.
      */
     public void setViewListener(ViewListener listener) {
         this.listener = listener;
     }
 
     /**
      * Retrieves the singleton instance of UserInterface.
      * 
      * @return The UserInterface instance.
      */
     public static UserInterface getInstance() {
         if (instance == null) {
             instance = new UserInterface();
         }
         return instance;
     }
 
     /**
      * Updates the game UI components and reassigns event listeners.
      */
     public void updateGameUI() {
         game.removeKeyListener(this);
         game.getBackToMenuButton().removeActionListener(this);
 
         game = new GameUI();
         game.addKeyListener(this);
         game.getBackToMenuButton().addActionListener(this);
     }
 
     /**
      * Updates the game grid display with the latest board state and score.
      * 
      * @param board The current state of the board.
      * @param score The current game score.
      */
     public void updateGrid(int[][] board, int score) {
         game.updateGrid(board, score);
     }
 
     /**
      * Displays a message indicating the player has won.
      */
     public void displayYouWonMessage() {
         showMessageDialog(null, "Congratulations, you win!");
     }
 
     /**
      * Displays a message indicating the player has lost.
      */
     public void displayYouLostMessage() {
         showMessageDialog(null, "Game Over! Try again.");
     }
 
     /**
      * Handles key press events to capture user moves.
      * 
      * @param e The key event.
      */
     @Override
     public void keyPressed(KeyEvent e) {
         if (listener == null) {
             return;
         }
         if (e.getKeyCode() == KeyEvent.VK_UP) {
            listener.onMoveOccured(MoveT.up);
         }
         else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
            listener.onMoveOccured(MoveT.down);
         }
         else if (e.getKeyCode() == KeyEvent.VK_LEFT) {
            listener.onMoveOccured(MoveT.left);
         }
         else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
            listener.onMoveOccured(MoveT.right);
         }
     }
 
     /**
      * Handles button click events for menu interactions.
      * 
      * @param e The action event.
      */
     @Override
     public void actionPerformed(ActionEvent e) {
         if (listener == null) {
             return;
         }
         if (e.getSource() == game.getBackToMenuButton()) {
             listener.onBackToMenuPressed();
         } else if (e.getSource() == menu.getPlayButton()) {
             listener.onPlayPressed();
         }
     }
 
     // Unused inherited methods
     @Override
     public void keyReleased(KeyEvent e) {
     }
 
     @Override
     public void keyTyped(KeyEvent e) {
     }
 }
 