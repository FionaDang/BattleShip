/* Name: Fiona Dang
 * File: ComputerBoard.java
 * Purpose: This ComputerBoard sub class performs tasks related to the computer's board
 * Date: June 3, 2024
 **/

import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.*;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.MatteBorder;
public class ComputerBoard extends Board { //ComputerBoard inherits Board class

	private ArrayList<Ship> ships; //declares an arraylist ships
	
	
	//Constructor
	public ComputerBoard(ArrayList<Ship> getShips) {
		ships = getShips; //ships = given ships
		numOfGuesses = 0; //no guesses at first
		numShipsLeft = ships.size(); //number of ships left is the size of the arraylist ships
		board = new String[10][10]; //10x10 board created
		won = false; //game has not been won
		for (int i = 0; i < 10; i++) { //fills in every index of the baord with a .
			for (int j = 0; j < 10; j++) {
				board[i][j] = ".";
			}
		}
		misses = 0; hits = 0;
	}
	
	/**
	 * Returns the number of ships alive
	 * 
	 * @return numShipsLeft
	 */
	public int getNumShipsAlive() {
		return numShipsLeft;
	}
	
	/**
	 * Returns the number of guesses made
	 * 
	 * @return numOfGuesses
	 */
	public int getNumOfGuesses() {
		return numOfGuesses;
	}
	
	/**
	 * Returns number of misses
	 * 
	 * @return misses
	 */
	public int getMisses() {
		return misses;
	}
	
	/**
	 * Returns number of hits
	 * 
	 * @return hits
	 */
	public int getNumHits() {
		return hits;
	}
	
	/**
	 * Checks whether the guessed position has been hit before
	 * 
	 * @param guess
	 * @return whether or not the guess has been hit
	 */
	public boolean checkGuess (String guess){
		int x = guess.charAt(0) - 'A'; //determines number corresponding to letter position
		int y = Integer.parseInt(guess.substring(1)) -1; //determines number position
		if (board[x][y] != ".") //if board position was shot at, then return false
			return false;
		return true; //otherwise return true
	}
	
	/**
	 * Displays information to user regarding their guess on computer's ships
	 * 
	 * @param pos
	 */
	public void guess(String pos) throws Exception{
		PrintWriter output = new PrintWriter(new FileWriter("gamehistory.txt", true));
		numOfGuesses++; //increases numOfGuesses by 1
		boolean hitted = false; //assume guess was not a hit
		output.println("User inputs '" + pos + "'");
		output.close();
		for (int i = 0; i<ships.size(); i++) { //for each ship
			hitted = ships.get(i).hit(pos); //checks if that ship was hit
			String name = ships.get(i).getName(); //gets name of the ship
			if (hitted) { //if ship was hit
				hits++;
				if (ships.get(i).getSunk()) { //if ship was sunk
					Main.gameFrame.setGuessStatus(name + " is sunk");
//					System.out.println(name+" is sunk"); //print out that the ship was sunk
					ships.remove(i); //removes sunk ship from arraylist
					numShipsLeft--; //subtracts 1 from remaining ships
					output.println("AI outputs 'SUNK, " + name + "'");
					output.close();
				}
				else {//otherwise, print out that the ship was hit at the position
					Main.gameFrame.setGuessStatus(name+" was hit at "+pos);
//					System.out.println(name+" was hit at "+pos);
					output.println("AI outputs 'HIT, " + name + "'");
					output.close();
				}
				if (name.equals("Battleship")) //if battleship was hit, mark battleship on board
					markPosition(pos, "B");
				else if (name.equals("Carrier")) //if carrier was hit, mark carrier on board
					markPosition(pos, "C");
				else if (name.equals("Cruiser")) //if cruiser was hit, mark cruiser on board
					markPosition(pos, "c");
				else if (name.equals("Destroyer")) //if destroyer was hit, mark destroyer on board
					markPosition(pos, "D");
				else //if submarine was hit, mark submarine on board
					markPosition(pos, "S");
				break;
			}
		}
		if (!hitted) { //if user's shot was a miss, mark the shot as a miss and inform the user
			misses++;
			markPosition(pos, "M");
			Main.gameFrame.setGuessStatus("Your guess was a miss");
//			System.out.println("Your guess was a miss");
			output.println("AI outputs 'MISS'");
			output.close();
		}
		Main.cBoard.printBoard(Main.getPanel1());
		Main.generateGuesses();
		if (numShipsLeft <= 0) { //if there are no remaining ships, game has been won
			won = true;
			endgame();
		}
	}
	
	/**
	 * Print out message at the end of game
	 */
	public void endgame() throws Exception{
		Main.won = true;
		PrintWriter output = new PrintWriter(new FileWriter("gamehistory.txt", true));
		printFinalBoard(Main.gameFrame.getPanel1());
		if (!won) { //if won is false, state number of moves it took computer to win
			Main.gameFrame.setMessage("The computer won the game in "+numOfGuesses +" moves");
//			System.out.println("The computer won the game in "+numOfGuesses +" moves.");
			output.println("Computer won the game in " + numOfGuesses + " moves.");
		}
		else { //if won is true, state number of moves it took user to win
			Main.gameFrame.setMessage("You won the game in "+numOfGuesses +" moves");
//			System.out.println("You won the game in "+numOfGuesses +" moves.");
			output.println("User won the game in " + numOfGuesses + " moves.");
		}
		output.close();
	}
	
	/**
	 * Marks the shot on the board
	 * 
	 * @param pos
	 * @param name
	 */
	public void markPosition(String pos, String name) {
		int x = pos.charAt(0) - 'A'; //determines number corresponding to letter position
		int y = Integer.parseInt(pos.substring(1)) -1; //determines number position
		board[x][y] = name; //marks the position with the name of the ship
	}
	
	/**
	 * Returns whether or not game has been won
	 * 
	 * @return won
	 */
	public boolean getWon() {
		return won;
	}
	
	/**
	 * Prints the board
	 */
	public void printBoard(JPanel panel) {
		panel.removeAll(); //removes everything from baord
		panel.revalidate();
		panel.repaint();
		String letters = "ABCDEFGHIJ"; //letters/rows on the board
		panel.add(new JLabel(""));
		for (int i = 1; i < 11; i++) { //prints out the columns
			JLabel label = new JLabel(Integer.toString(i));
			label.setFont(new Font("Sakura Blossom", Font.BOLD, 20));
			label.setHorizontalAlignment(SwingConstants.CENTER);
			panel.add(label);
		}
		for (int i = 0; i < 10; i++) { //prints out the rows (letters)
			JLabel label = new JLabel(Character.toString(letters.charAt(i)));
			label.setFont(new Font("Sakura Blossom", Font.BOLD, 20));
			label.setHorizontalAlignment(SwingConstants.CENTER);
			panel.add(label);
			int k = i;
			for (int j = 0; j < 10; j++) {
				int l = j;
				if (board[i][j].equals(".")) { //if board position is empty
					JButton button = new JButton(); //create a button there
					button.setBackground(new Color(255, 202, 248));
					button.setBorder(new MatteBorder(1, 1, 1, 1, (Color) Color.WHITE));
					panel.add(button);
					button.addActionListener(new ActionListener() {
			            public void actionPerformed(ActionEvent e)
			            {
			                if (!Main.getTurn()) { //if it is not the user's turn
			                	String g = Character.toString((char)(k+'A')) + Integer.toString(l+1); //gets position of guess
			                	try {
//			                		Main.setTurn(true); //try to set it to the user's turn
									Main.cBoard.guess(g); //guess the position
									if (!Main.won) Main.setTurn(true);
								} catch (Exception e1) {
									e1.printStackTrace();
								}
			                }
			            }
			        });
				}
				else if (board[i][j].equals("C")){  //prints symbol for carrier
					label = new JLabel("");
					label.setIcon(getImageIcon("PinkFlower.png"));
					label.setHorizontalAlignment(SwingConstants.CENTER);
					panel.add(label);
				}
				else if (board[i][j].equals("B")) {  //prints symbol for battleship
					label = new JLabel("");
					label.setIcon(getImageIcon("Purple Flower.png"));
					label.setHorizontalAlignment(SwingConstants.CENTER);
					panel.add(label);
				}
				else if (board[i][j].equals("c")) {  //prints symbol for cruiser
					label = new JLabel("");
					label.setIcon(getImageIcon("Yellow Flower.png"));
					label.setHorizontalAlignment(SwingConstants.CENTER);
					panel.add(label);
				}
				else if (board[i][j].equals("S")) {  //prints symbol for submarine
					label = new JLabel("");
					label.setIcon(getImageIcon("Blue Flower.png"));
					label.setHorizontalAlignment(SwingConstants.CENTER);
					panel.add(label);
				}
				else if (board[i][j].equals("D")) {  //prints symbol for destroyer
					label = new JLabel("");
					label.setIcon(getImageIcon("Green Flower.png"));
					label.setHorizontalAlignment(SwingConstants.CENTER);
					panel.add(label);
				}
				else if (board[i][j].equals("M")) {  //prints symbol for miss
					label = new JLabel("");
					label.setIcon(getImageIcon("White Flower.png"));
					label.setHorizontalAlignment(SwingConstants.CENTER);
					panel.add(label);
				}
			}
		}
//		for (int i = 0; i < 10; i++) //prints out position and the shots
//			System.out.println(letters.charAt(i) + Arrays.toString(board[i]));
	}
	
	/**
	 * Prints final board of game
	 * 
	 * @param panel
	 * @throws Exception 
	 */
	public void printFinalBoard(JPanel panel) throws Exception {
		Main.setTurn(false); //it is not the user's turn
		Main.gameFrame.setMessage("The computer won the game in "+numOfGuesses +" moves"); //displays how many moves it took the computer to win
		panel.removeAll(); //remove everything
		panel.revalidate();
		panel.repaint();
		String letters = "ABCDEFGHIJ"; //letters/rows on the board
		panel.add(new JLabel(""));
		for (int i = 1; i < 11; i++) { //prints out columns
			JLabel label = new JLabel(Integer.toString(i));
			label.setFont(new Font("Sakura Blossom", Font.BOLD, 20));
			label.setHorizontalAlignment(SwingConstants.CENTER);
			panel.add(label);
		}
		for (int i = 0; i < 10; i++) { //prints out rows
			JLabel label = new JLabel(Character.toString(letters.charAt(i)));
			label.setFont(new Font("Sakura Blossom", Font.BOLD, 20));
			label.setHorizontalAlignment(SwingConstants.CENTER);
			panel.add(label);
			for (int j = 0; j < 10; j++) {
				if (board[i][j].equals(".")) { //if board position was not shot at, do not display any symbol
					label = new JLabel("");
					label.setBorder(new MatteBorder(1, 1, 1, 1, (Color) Color.WHITE));
					panel.add(label);
				}
				else if (board[i][j].equals("C")){ //prints symbol for carrier
					label = new JLabel("");
					label.setIcon(getImageIcon("PinkFlower.png"));
					label.setHorizontalAlignment(SwingConstants.CENTER);
					panel.add(label);
				}
				else if (board[i][j].equals("B")) { //prints symbol for battleship
					label = new JLabel("");
					label.setIcon(getImageIcon("Purple Flower.png"));
					label.setHorizontalAlignment(SwingConstants.CENTER);
					panel.add(label);
				}
				else if (board[i][j].equals("c")) { //prints symbol for cruiser
					label = new JLabel("");
					label.setIcon(getImageIcon("Yellow Flower.png"));
					label.setHorizontalAlignment(SwingConstants.CENTER);
					panel.add(label);
				}
				else if (board[i][j].equals("S")) { //prints symbol for submarine
					label = new JLabel("");
					label.setIcon(getImageIcon("Blue Flower.png"));
					label.setHorizontalAlignment(SwingConstants.CENTER);
					panel.add(label);
				}
				else if (board[i][j].equals("D")) { //prints symbol for destroyer
					label = new JLabel("");
					label.setIcon(getImageIcon("Green Flower.png"));
					label.setHorizontalAlignment(SwingConstants.CENTER);
					panel.add(label);
				}
				else if (board[i][j].equals("M")) { //prints symbol for miss
					label = new JLabel("");
					label.setIcon(getImageIcon("White Flower.png"));
					label.setHorizontalAlignment(SwingConstants.CENTER);
					panel.add(label);
				}
			}
		}
		saveBoard();
	}
	
	private ImageIcon getImageIcon(String filePath) {
		ImageIcon icon = new ImageIcon(filePath);
		Image newimg = icon.getImage().getScaledInstance(30, 30,  java.awt.Image.SCALE_SMOOTH); // scale it the smooth way  
		icon = new ImageIcon(newimg);
		return icon;
	}
	
	/**
	 * Saves the final board to the file
	 * 
	 * @throws Exception
	 */
	public void saveBoard() throws Exception{
		PrintWriter output = new PrintWriter(new FileWriter("gamehistory.txt", true));
		PrintWriter output2 = new PrintWriter(new FileWriter("gamehistorydisplay.txt", true));
		output.println("This is the AI's board displaying the user's shots.");
		output2.println("This is the AI's board displaying the user's shots.");
		String letters = "ABCDEFGHIJ"; //letters/rows on the board
		output.println("  1  2  3  4  5  6  7  8  9  10"); //columns on board
		output2.println(" | 1| 2| 3| 4| 5| 6| 7| 8| 9| 10"); //columns on board
		for (int i = 0; i < 10; i++) {//prints out position and the shots
//			System.out.println(Arrays.toString(board[i]));
			output.println(letters.charAt(i) + Arrays.toString(board[i]));
			output2.println(letters.charAt(i) + Arrays.toString(board[i]));
		}
		output.close();
		output2.close();
	}
	
}
