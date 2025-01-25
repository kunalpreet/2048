/**
 * @File: GameController.java
 * @Description: controller module implementing ViewListener interface
 */

package src;

/**
 * @brief An abstract object that links the model and view modules.
 * @details Handles events triggered by the UI
 */
public class Controller implements ViewListener {
	
	// State Variables
	private BoardT model;
	private UserInterface view;
    private static Controller controller = null;

    /**
     * @brief constructor
     * @param model - model module (BoardT)
     * @param view - view module (UseInterface)
     */
    private Controller(BoardT model, UserInterface view){
        this.model = model;
        this.view = view;
    }

    /**
     * @brief public static method for obtaining a single instance
     * @param model - model module (BoardT)
     * @param view - view module (UseInterface)
     * @return the single GameController object
     */
    public static Controller getInstance(BoardT model, UserInterface view) 
    { 
        if (controller == null) 
            controller = new Controller(model, view); 
        return controller; 
    } 

    /**
     * @brief initializes the game
     */
    public void initializeGame(){
        model = new BoardT();
    }
    
    /**
     * @brief displays the menu
     */
    public void displayMenu() {
    	view.switchToMenu();
    }
    
    /**
     * @brief displays the game view
     */
    public void displayGame() {
    	view.switchToGame();
    }

    /**
     * @brief updates the status of the game
     */
    public void updateStatus() {
		if (model.isWinner()) {
			model.setStatus(false);
		}
		else if (model.isBoardFull() && !model.isAnyValidMove()) {
			model.setStatus(false);
		}
	}
    
    /**
     * @brief updates the grid displayed to the user
     */
    public void updateGrid() {
    	view.updateGrid(model.getBoard(), model.getScore());
    }
    
    /**
     * @brief updates the gameUI of the view module
     */
    public void updateGameUI() {
    	view.updateGameUI();
    }
    
    /**
     * @brief generates and populates a random tile at randomly selected unoccupied board cell
     */
    public void populateRandomCell() {
    	int[] coords = model.generateRandomFreeCellCoord();
    	int tile  = model.generateRandomTile();
    	model.setCell(coords[0], coords[1], tile);
    }
    
    /**
     * @brief updates the model and view when the user attempts to make a move (core routine of the controller)
     * @details triggered every time the user interacts with the UI and intends to make a move in game view
     * @param m - a move the user intends to make
     */
	@Override
	public void onMoveOccured(MoveT m) {
		if (!model.getStatus()) return;
		if (!model.isBoardChangedOnMove(m)) return;
		model.move(m);
		populateRandomCell();
		updateStatus();
		updateGrid();
		if (!model.getStatus() && view.isDisplaying()) {
			if (model.isWinner()) view.displayYouWonMessage();
			else view.displayYouLostMessage();
		}
	}
	
    /**
     * @brief initializes and starts the game
     * @details triggered every time the user interacts with the UI and presses the "Play" button in the menu
     */
	@Override
	public void onPlayPressed() {
		initializeGame();
		updateGameUI();
    	displayGame();
    	populateRandomCell();
    	populateRandomCell();
    	updateGrid();
	}
	
    /**
     * @brief switches the game view to the menu
     * @details triggered every time the user interacts with the UI and presses the "Back to Menu" button in the game view
     */
	@Override
	public void onBackToMenuPressed() {
		displayMenu();
	}
}
