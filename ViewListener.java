/**
 * @File: ViewListener.java
 * @Description: An interface implemented by modules that expect to receive notifications from 
 * the UserInterace when user interacts with the UI components
 */

package src;

/**
 * @brief An interface for subscribing to a UserInterface as a listener
 * @details Provides methods triggered from the UserInterface when user interacts with the UI
 */
public interface ViewListener {

    /**
     * @brief  triggered every time the user interacts with the UI and intends to make a move in the game view
     * @param m - a move the user intends to make
     */
	public void onMoveOccured(MoveT move);
	
    /**
     * @brief triggered every time the user interacts with the UI and presses the "Back to Menu" button in the game view
     */
	public void onBackToMenuPressed();
	
    /**
     * @brief triggered every time the user interacts with the UI and presses the "Play" button in the menu
     */
	public void onPlayPressed();

}
