/**
 * @File: BoardT.java
 * @Description: a model module for storing the state and status of the game
**/

package src;

// Import java libraries
import java.util.Arrays;
import java.util.Random;

/**
 * @brief An ADT representing the board of the game 2048
 * @details Stores the state and the status of the game
 */
public class BoardT {
	
	// State Variables
	private int[][] board;
	private boolean status;
	private int score; // New variable to track the score

    /**
     * @brief constructor
     * @details generates an empty board
     */
	public BoardT() {
		int size = 4;
		status = true;
		score = 0; // Initialize score to 0
		board = new int[size][size];
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				board[i][j] = 0;
			}
		}
	}

    /**
     * @brief gets the status of the game
     * @return status (true -> game in progress; false -> game is finished)
     */
	public boolean getStatus() {
		return status;
	}

    /**
     * @brief gets the game board
     * @return board
     */
	public int[][] getBoard() {
		return board;
	}

    /**
     * @brief gets the current score
     * @return the current score
     */
	public int getScore() {
		return score;
	}

    /**
     * @brief sets the cell at given x and y with given tile value
     * @param x - row number
     * @param y - column number
     * @param tile - tile value
     * @throws IndexOutOfBoundsException - if entered coordinate is located outside of the board
     */
	public void setCell(int x, int y, int tile) {
		int size = 4;
		if (x < 0 || x > size - 1 || y < 0 || y > size - 1)
			throw new IndexOutOfBoundsException("Trying to set a cell beyond the board boundaries.");
		board[x][y] = tile;
	}

    /**
     * @brief sets the game status
     * @param status - true for game in progress; false for game finished
     */
	public void setStatus(boolean status) {
		this.status = status;
	}

    /**
     * @brief determines whether the game is won 
     * @return true if the board contains value 2048; false otherwise
     */
	public boolean isWinner() {
		int size = 4;
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				if (board[i][j] == 2048) {
					return true;
				}
			}
		}
		return false;
	}

    /**
     * @brief determines whether the board is fully occupied 
     * @return true if the board doesn't contain a tile with value 0; false otherwise
     */
	public boolean isBoardFull() {
		int size = 4;
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				if (board[i][j] == 0) {
					return false;
				}
			}
		}
		return true;
	}

    /**
     * @brief determines whether there exist a valid move
     * @return true if there exist a valid move; false otherwise
     */
	public boolean isAnyValidMove() {
		int size = 4;
		for (int i = 0; i < size - 1; i++) {
			for (int j = 0; j < size - 1; j++) {
				if (board[i][j] == board[i + 1][j] || board[i][j] == board[i][j + 1]) {
					return true;
				}
			}
		}
		for (int i = 0; i < size - 1; i++) {
			if (board[i][size - 1] == board[i + 1][size - 1]) {
				return true;
			}
		}
		for (int j = 0; j < size - 1; j++) {
			if (board[size - 1][j] == board[size - 1][j + 1]) {
				return true;
			}
		}
		return false;
	}

    /**
     * @brief generates a random tile value based on the size of the board
     * @return a random tile value - 2,4, or 8
     */
	public int generateRandomTile() {
		int size = 4;
		Random rand = new Random();
		int rndInt = rand.nextInt(size - 1);
		if (rndInt == 0 || rndInt == 1)
			return 2;
		else if (rndInt == 2 || rndInt == 3)
			return 4;
		return 8;
	}

    /**
     * @brief generates random board coordinates of a cell that is not occupied
     * @throws UnsupportedOperationException - if the board if fully occupied
     * @return randomly generated coordinates of an unoccupied cell
     */
	public int[] generateRandomFreeCellCoord() {
		if (isBoardFull())
			throw new UnsupportedOperationException("Random free cell cannot be generated since the board is full.");
		int size = 4;
		int x, y;
		Random rand = new Random();
		do {
			x = rand.nextInt(size);
			y = rand.nextInt(size);
		} while (board[x][y] != 0);
		return new int[] { x, y };
	}

    /**
     * @brief checks whether performing a given potential move would change the state of the board
     * @param move - a move to be checked
     * @return true if performing a given move would have changed the state of the board; false otherwise 
     */
	public boolean isBoardChangedOnMove(MoveT move) {
		int[][] copyBoard = clone2dArray(board);
		if (move == MoveT.left) {
			compressLeft(copyBoard);
			merge(copyBoard);
			compressLeft(copyBoard);
		} else if (move == MoveT.right) {
			reverse(copyBoard);
			compressLeft(copyBoard);
			merge(copyBoard);
			compressLeft(copyBoard);
			reverse(copyBoard);
		} else if (move == MoveT.up) {
			transpose(copyBoard);
			compressLeft(copyBoard);
			merge(copyBoard);
			compressLeft(copyBoard);
			transpose(copyBoard);
		} else {
			transpose(copyBoard);
			reverse(copyBoard);
			compressLeft(copyBoard);
			merge(copyBoard);
			compressLeft(copyBoard);
			reverse(copyBoard);
			transpose(copyBoard);
		}
		return !Arrays.deepEquals(copyBoard, board);
	}

    /**
     * @brief performs a given move and updates the state of the board accordingly
     * @param move - a move to be performed
     */
	public void move(MoveT move) {
		if (move == MoveT.left) {
			compressLeft(board);
			merge(board);
			compressLeft(board);
		} else if (move == MoveT.right) {
			reverse(board);
			compressLeft(board);
			merge(board);
			compressLeft(board);
			reverse(board);
		} else if (move == MoveT.up) {
			transpose(board);
			compressLeft(board);
			merge(board);
			compressLeft(board);
			transpose(board);
		} else {
			transpose(board);
			reverse(board);
			compressLeft(board);
			merge(board);
			compressLeft(board);
			reverse(board);
			transpose(board);
		}
	}

    /**
     * @brief creates a copy(clone) of a given 2d array
     * @param arr - an array to be cloned
     * @return a copy(clone) of a given array
     */
	private int[][] clone2dArray(int[][] arr) {
		int size = 4;
		int[][] newArr = new int[size][size];
		for (int i = 0; i < arr.length; i++)
			for (int j = 0; j < arr[i].length; j++)
				newArr[i][j] = arr[i][j];
		return newArr;
	}

    /**
     * @brief reverses the values in a given 2d array
     * @param arr - an array to be modified
     */
	private void reverse(int[][] arr) {
		int size = arr.length;
		int[][] reverse = new int[size][size];
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				reverse[i][j] = arr[i][size - 1 - j];
			}
		}

		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				arr[i][j] = reverse[i][j];
			}
		}
	}

    /**
     * @brief transposes the values in a given 2d array
     * @param arr - an array to be modified
     */
	private void transpose(int[][] arr) {
		int size = arr.length;
		int[][] transpose = new int[size][size];
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				transpose[i][j] = arr[j][i];
			}
		}

		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				arr[i][j] = transpose[i][j];
			}
		}
	}

    /**
     * @brief compresses the tiles in a given array to the left
     * @param arr - an array to be modified
     */
	private void compressLeft(int[][] arr) {
		int size = arr.length;
		int position;
		for (int i = 0; i < size; i++) {
			position = 0;
			for (int j = 0; j < size; j++) {
				if (arr[i][j] != 0) {
					arr[i][position] = arr[i][j];
					if (j != position) {
						arr[i][j] = 0;
					}
					position++;
				}
			}
		}
	}

    /**
     * @brief merges adjacent tiles with the same values in a given array
     * @details merges adjacent tiles with the same values into 
     * one tile of twice the value for each row starting from the leftmost tile.
     * The tile merged becomes an empty tile. Updates the score accordingly.
     * @param arr - an array to be modified
     */
	private void merge(int[][] arr) {
		int size = arr.length;
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size - 1; j++) {
				if (arr[i][j] == arr[i][j + 1] && arr[i][j] != 0) {
					arr[i][j] *= 2;
					score += arr[i][j]; // Update score with the value of the merged tile
					//System.out.println(String.valueOf(score));
					arr[i][j + 1] = 0;
				}
			}
		}
	}
}
