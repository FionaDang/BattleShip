/* Name: Fiona Dang
 * File: Board.java
 * Purpose: Act as an abstract template for boards
 * Date: June 3, 2024
 **/
import javax.swing.JPanel;
public abstract class Board { //abstract Board class

	protected String[][] board; //2d grid for board
	protected int numShipsLeft; //number of ships left
	protected int numOfGuesses; //number of guesses
	protected boolean won; //whether or not game has been won
	protected int misses;
	protected int hits;


/**
	 * Partial implementation of markPosition method
	 * 
	 * @param pos
	 * @param name
	 */
	protected abstract void markPosition(String pos, String name);
	
	/**
	 * Partial implementation of printBoard method
	 */
	public abstract void printBoard(JPanel panel);
	
	/**
	 * Partial implementation of endgame method
	 */
	public abstract void endgame() throws Exception;
	
	/**
	 * Partial implementation of getWon method
	 * @return whether it won
	 */
	public abstract boolean getWon();
}
