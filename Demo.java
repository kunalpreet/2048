/**
 * @File: Demo.java
 * @Description: Runs the game (client code)
 */

package src;

public class Demo {

   public static void main(String[] args) {
	      BoardT boardT = new BoardT();
	      UserInterface UI = UserInterface.getInstance();
	      Controller game = Controller.getInstance(boardT, UI);
	      UI.setViewListener(game);
	      game.displayMenu();
	  }

}
