/* Name: Fiona Dang
 * File: TrackBoard.java
 * Purpose: This TrackerBoard sub class performs tasks related to the user's board
 * Date: June 3, 2024
 **/

import java.awt.Font;
import java.awt.Image;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.*;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class TrackBoard extends Board { //TrackerBoard inherits Board class

	private String guess; //String guess from computer
	private String directionOfHit; //direction of hit to check
	private ArrayList<ArrayList<String>> lastHits; //arraylist to track computer's hits
	private int guessNumber = 0;
	private int lowEdgeY = 5;
	private int highEdgeY = 5;
	private String previousGuess;
	private String firstGuess;
	private boolean goingDown = false;
	private boolean continueRowLeft = false;
	private boolean continueRowRight = false;
	private ArrayList<String> sunken;
	
	//Constructor
	public TrackBoard(int numOfShips) {
		numOfGuesses = 0; //no guesses at first
		numShipsLeft = numOfShips; //num of ships left = num of ships
		board = new String[10][10]; //10x10 board created
		for (int i = 0; i < 10; i++) { //fills in every index of board with .
			for (int j = 0; j < 10; j++) 
				board[i][j] = ".";
		}
		lastHits = new ArrayList<>(); //empty arraylist
		for (int i = 0; i < 5; i++)
			lastHits.add(new ArrayList<>()); // Add new arraylist to each element of last hits to represent each ship
		directionOfHit = ""; //no direction yet
		won = false; //game has not been won
		sunken = new ArrayList<>();
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
	 * Checks if there are any hits on ships that have not been sunk
	 * 
	 * @return i
	 */
	private int checkEmpty(){
		for (int i = 0; i < lastHits.size(); i++){ //go through lastHits array
			if (!lastHits.get(i).isEmpty()) { // //if an index is not empty
				int size = lastHits.get(i).size(); 
				guess = lastHits.get(i).get(size - 1); // The last correct hit of the ship we are trying to guess
				return i;
			}
		}
		return -1;
	}
	
	/**
	 * Computer generates a guess based on level of difficulty
	 * 
	 * @param level
	 * @return guess
	 */
	public String generateGuess(String level, ArrayList<Ship> ships) throws Exception{
		PrintWriter output = new PrintWriter(new FileWriter("gamehistory.txt", true));
		int index = checkEmpty();
		if (index >= 0 && !level.equals("Legendary")) //if there was a previous hit, guess beside the previous hit
			guessBeside(index);
		else if (level.equals("Easy")) { //if easy difficulty, select a random x and y position and guess it
			int x = (int) (Math.random() * 10);
			int y = (int) (Math.random() * 10);
			while (board[x][y] !=".") { //while (if) the guess has already been guessed before, regenerate a new guess
				x = (int) (Math.random() * 10);
				y = (int) (Math.random() * 10);
			}
			guess = Character.toString((char)(x+'A')) + Integer.toString(y+1); //assigns the position of generated guess to guess
		}
		
		else if (level.equals("Hard")) { //if hard difficulty
			int minSize = 5;
			for (int i = 0; i < ships.size(); i++) { //find size of smallest ship on user's board
				if(ships.get(i).getSize() < minSize) {
					minSize = ships.get(i).getSize();
				}
			}
			if (guessNumber == 0) { //generates a random first guess in 6x6 grid in the center 
				int x = (int) (Math.random() * 6+2);
				int y = (int) (Math.random() * 6+2);
				guess = Character.toString((char)(x+'A')) + Integer.toString(y+1);
				previousGuess = guess;
				firstGuess = guess;
				guessNumber++;
			}
			else { //for all other guesses
				//System.out.println("here" + minSize);
				int x = previousGuess.charAt(0) - 'A'; //gets position of guess
				int y = Integer.parseInt(previousGuess.substring(1))-1;
				try { //check to see if guess to the left is valid
					int multiple = 1;
					while (board[x][y-(minSize*multiple)] !=".") { //while (if) the guess has already been guessed before, regenerate a new guess
						multiple++;
						guess = Character.toString((char)(x+'A')) + Integer.toString(y+1-(minSize*multiple));
					}
					continueRowLeft = true; //if valid, allow guess to go left
					
				}
				catch (ArrayIndexOutOfBoundsException e) { //otherwise, do not allow it to go left
					continueRowLeft = false;
				}
				try { //check to see if guess to the right is valid
					int multiple = 1;
					while (board[x][y+(minSize*multiple)] !=".") { //while (if) the guess has already been guessed before, regenerate a new guess
						multiple++;
						guess = Character.toString((char)(x+'A')) + Integer.toString(y+1+(minSize*multiple));
					}
					continueRowRight = true; //if valid, allow guess to go right
					
				}
				catch (ArrayIndexOutOfBoundsException e) { //otherwise, do not allow it to go right
					continueRowRight = false;
				}
				if (y-minSize >= 0 && !directionOfHit.equals("RIGHT") && lowEdgeY-minSize >=0 && continueRowLeft == true) { // if possible and valid (not already guessed and within board), guess spot to the left and determine next guess as left
					guess = Character.toString((char)(x+'A')) + Integer.toString(y-minSize+1); //make a guess minSize spots to the left
					int multiple = 1;
					while (board[x][y-(minSize*multiple)] !=".") { //while (if) the guess has already been guessed before, regenerate a new guess
						multiple++;
						guess = Character.toString((char)(x+'A')) + Integer.toString(y+1-(minSize*multiple));
					}
					previousGuess = guess; //make this new guess the previousGuess
					//System.out.println("hello" + (y-(minSize*(multiple+1))));
					if ((y-(minSize*(multiple+1))) < 0) { //if you cant go left anymore, switch direction to right and do not allow further left guesses on this row
						//System.out.println("help");
						lowEdgeY = Integer.parseInt(guess.substring(1))-1;
						directionOfHit = "RIGHT";
					} 
					else { //otherwise continue guessing left
						directionOfHit = "LEFT";
					}
				}
				else if (y+minSize < 10 && !directionOfHit.equals("LEFT") && highEdgeY + minSize <10 && continueRowRight == true) { //otherwise guess spot to the right and determine next guess as right
					guess = Character.toString((char)(x+'A')) + Integer.toString(y+1+minSize); //make a guess minSize spots to the right
					int multiple = 1;
					while (board[x][y+(minSize*multiple)] !=".") { //while (if) the guess has already been guessed before, regenerate a new guess
						multiple++;
						guess = Character.toString((char)(x+'A')) + Integer.toString(y+1+(minSize*multiple));
					}
					previousGuess = guess; //make this new guess the previousGuess
					//System.out.println("hi" + (y+(minSize*(multiple+1))));
					if ((y+(minSize*(multiple+1))) >= 10) { //if you cant go right anymore, switch direction to left and do not allow further right guesses on this row
						//System.out.println("help");
						directionOfHit = "LEFT";
						highEdgeY = Integer.parseInt(guess.substring(1))-1;
					}
					else { //otherwise continue guessing right
						directionOfHit = "RIGHT";
					}
				}
				else if ((x-1) >= 0 && (y-1) >= 0 && goingDown == false){ //diagonally left and up
					guess = Character.toString((char)((x-1)+'A')) + Integer.toString(y); //make a guess diagonally left and up one spot
					x = guess.charAt(0) - 'A'; //gets position of guess
					y = Integer.parseInt(guess.substring(1))-1;
					int subtract = 0;
					while (board[x][y-(subtract)] !=".") { //while (if) the guess has already been guessed before, regenerate a new guess to the left
						subtract++;
						guess = Character.toString((char)(x+'A')) + Integer.toString(y+1-(subtract)); //assigns the position of generated guess to guess
						//System.out.println("bonjour1");
					}
					previousGuess = guess; //make this new guess the previousGuess
					y = Integer.parseInt(guess.substring(1))-1;
					if((y + minSize) >= 10) { //if a guess to the right is needed in this row, make it so there will be no guesses to the right
						lowEdgeY = 5;
						highEdgeY = 100;
					}
					else if ((y-minSize) < 0) { //if a guess to the left is not needed in this row, make it so there will be no guesses to the right
						lowEdgeY = -1;
						highEdgeY = 5;
					}
					else { //otherwise, allow guesses to both the left and the right
						lowEdgeY = 5;
						highEdgeY = 5;
					}
				}
				else if ((x-1) >= 0 && (y+1) <10 && goingDown == false) { //diagonally right and up
					guess = Character.toString((char)((x-1)+'A')) + Integer.toString(y+2); //make a guess diagonally right and up one spot
					x = guess.charAt(0) - 'A'; //gets position of guess
					y = Integer.parseInt(guess.substring(1))-1;
					int subtract = 0;
					while (board[x][y-(subtract)] !=".") { //while (if) the guess has already been guessed before, regenerate a new guess
						subtract++;
						guess = Character.toString((char)(x+'A')) + Integer.toString(y+1-(subtract)); //assigns the position of generated guess to guess
						//System.out.println("bonjour2");
					}
					previousGuess = guess; //make this new guess the previousGuess
					y = Integer.parseInt(guess.substring(1))-1;
					if((y + minSize) >= 10) { //if a guess to the right is needed in this row, make it so there will be no guesses to the right
						lowEdgeY = 5;
						highEdgeY = 100;
					}
					else if ((y-minSize) < 0) { //if a guess to the left is not needed in this row, make it so there will be no guesses to the right
						lowEdgeY = -1;
						highEdgeY = 5;
					}
					else { //otherwise, allow guesses to both the left and the right
						lowEdgeY = 5;
						highEdgeY = 5;
					}
				} 
				else { //if you cant go up for guesses anymore
					if (guessNumber == 1) { //if this is the first guess that can not go up
						x = firstGuess.charAt(0) - 'A'; //gets position of the first guess
						y = Integer.parseInt(firstGuess.substring(1))-1;
						guess = Character.toString((char)((x+1)+'A')) + Integer.toString(y+2); //make a guess down and to the right
						previousGuess = guess; //make this new guess the previousGuess
						guessNumber++;
						goingDown = true; //make it so that there will be no more shots going up
						directionOfHit = ""; //reset direction of hit
						if((y + minSize) >= 10) { //if a guess to the right is needed in this row, make it so there will be no guesses to the right
							lowEdgeY = 5;
							highEdgeY = 100;
						}
						else if ((y-minSize) < 0) { //if a guess to the left is not needed in this row, make it so there will be no guesses to the right
							lowEdgeY = -1;
							highEdgeY = 5;
						}
						else { //otherwise, allow guesses to both the left and the right
							lowEdgeY = 5;
							highEdgeY = 5;
						}
					}
					else if ((x+1) < 10 && (y+1) <10) { //else if you can go right and down
						guess = Character.toString((char)((x+1)+'A')) + Integer.toString(y+2); //make guess diagonally right and down
						x = guess.charAt(0) - 'A'; //gets position of guess
						y = Integer.parseInt(guess.substring(1))-1;
						int subtract = 0;
						while (board[x][y-subtract] !=".") { //while (if) the guess has already been guessed before, regenerate a new guess
							subtract++;
							guess = Character.toString((char)(x+'A')) + Integer.toString(y+1-subtract); //assigns the position of generated guess to guess
						}
						previousGuess = guess; //make this new guess the previousGuess
						y = Integer.parseInt(guess.substring(1))-1;
						if((y + minSize) >= 10) { //if a guess to the right is needed in this row, make it so there will be no guesses to the right
							lowEdgeY = 5;
							highEdgeY = 100;
						}
						else if ((y-minSize) < 0) { //if a guess to the left is not needed in this row, make it so there will be no guesses to the right
							lowEdgeY = -1;
							highEdgeY = 5;
						}
						else { //otherwise, allow guesses to both the left and the right
							lowEdgeY = 5;
							highEdgeY = 5;
						}
					} 
					else if ((x+1) < 10 && (y-1) >=0) { //else if you can go left and down
						guess = Character.toString((char)((x+1)+'A')) + Integer.toString(y); //make guess diagonally left and down
						x = guess.charAt(0) - 'A'; //gets position of guess
						y = Integer.parseInt(guess.substring(1))-1;
						int subtract = 0;
						while (board[x][y-subtract] !=".") { //while (if) the guess has already been guessed before, regenerate a new guess
							subtract++;
							guess = Character.toString((char)(x+'A')) + Integer.toString(y+1-subtract); //assigns the position of generated guess to guess
						}
						previousGuess = guess;  //make this new guess the previousGuess
						y = Integer.parseInt(guess.substring(1))-1;
						if((y + minSize) >= 10) {  //if a guess to the right is needed in this row, make it so there will be no guesses to the right
							lowEdgeY = 5;
							highEdgeY = 100;
						}
						else if ((y-minSize) < 0) {  //if a guess to the left is not needed in this row, make it so there will be no guesses to the right
							lowEdgeY = -1;
							highEdgeY = 5;
						}
						else {  //otherwise, allow guesses to both the left and the right
							lowEdgeY = 5;
							highEdgeY = 5;
						}
					} 
					else { //otherwise, make random guesses 
						x = (int) (Math.random() * 10);
						y = (int) (Math.random() * 10);
						while (board[x][y] !=".") { //while (if) the guess has already been guessed before, regenerate a new guess
							x = (int) (Math.random() * 10);
							y = (int) (Math.random() * 10);
						}
						guess = Character.toString((char)(x+'A')) + Integer.toString(y+1); //assigns the position of generated guess to guess
					}
				}
			}
			// Guess horizontally before vertically
		}
		else { // Legendary Level - using probability heat map
			int[][] probabilityMap = new int[10][10]; //generate a probability map the size of the board
			for (Ship ship : ships) { //for each ship
				int size = ship.getSize()-1; //get the size of the ship
				for (int i = 0; i < probabilityMap.length; i++) { //for each index in the probability map
					for (int j = 0; j < probabilityMap[0].length; j++) {
						if (board[i][j].equals(".")) { //if the board has a .
							int[][][] endpoints = new int [4][2][2]; // stores endpoints, include direction, startrow, endrow, startcol, endcol
							//goes through every combination on the board where a ship of size x can fit and wherever it fits, add weight of 1 to the heat map
							if (i - size >= 0)
								endpoints[0] = new int[][]{{i - size, j}, {i+1, j+1}}; // Store values - use new int[][] to add {} for initialization (aka reinitialization of values)
							if (i + size < 10)
								endpoints[1] = new int[][] {{i, j}, {i+size+1, j+1}};
							if (j - size >= 0)
								endpoints[2] = new int[][] {{i, j-size}, {i+1, j+1}};
							if (j + size < 10)
								endpoints[3] = new int[][] {{i, j}, {i+1, j+size+1}};
							for (int [][] getValues : endpoints) {
								if (getValues != null) {
									int startRow = getValues[0][0], startCol = getValues[0][1], endRow = getValues[1][0], endCol = getValues[1][1];
									boolean valid = true;
									for (int x = startRow; x < endRow; x++) {
										for (int y = startCol; y < endCol; y++) {
											if (!board[x][y].equals(".")) {
												valid = false; break;
											}
										}
										if (!valid) break;
									}
									if (valid) {
										for (int x = startRow; x < endRow; x++) {
											for (int y = startCol; y < endCol; y++)
												probabilityMap[x][y]++;
										}
									}
								}
							}
						}
//						System.out.println("Mao: ");
//						for (int[] arr: probabilityMap) {
//							System.out.println(Arrays.toString(arr));
//						}
						if (board[i][j].equals("M")) { // Set weight to zero for misses
							probabilityMap[i][j] = 0;
						}
						else if (!board[i][j].equals(".") && !sunken.contains(board[i][j])) { // increase weighting after successful hits (not sunken)
							if (i + 1 < 10 && board[i+1][j].equals(".")) { //if the x position to the right is within the board and is a .
								if (i - 1 >= 0 && !board[i-1][j].equals(".") && !board[i-1][j].equals("M") && !sunken.contains(board[i-1][j])) //if the position to the left is within the board and has not been shot at
									probabilityMap[i+1][j] += 15; //increase probability of square to the right by 15
								else //otherwise increase probability of square to the right by 10
									probabilityMap[i+1][j] += 10;
							}
							if (i - 1 >= 0 && board[i-1][j].equals(".")) { //if the x position to the right is within the board and is a .
								if (i + 1 < 10 && !board[i+1][j].equals(".") && !board[i+1][j].equals("M") && !sunken.contains(board[i+1][j])) //if the position to the right is within the board and has not been shot at
									probabilityMap[i-1][j] += 15; //increase probability of square to the left by 15
								else //otherwise increase probability of square to the left by 10
									probabilityMap[i-1][j] += 10;
							}
							if (j + 1 < 10 && board[i][j+1].equals(".")) { //if the y position below is within the board and is a .
								if (j - 1 >= 0 && !board[i][j-1].equals(".") && !board[i][j-1].equals("M") && !sunken.contains(board[i][j-1])) //if the position above is within the board and has not been shot at
									probabilityMap[i][j+1] += 15;  //increase probability of square downward by 15
								else  //otherwise increase probability of square below by 10
									probabilityMap[i][j+1] += 10;
							}
							if (j - 1 >= 0 && board[i][j-1].equals(".")) { //if the y position above is within the board and is a .
								if (j + 1 < 10 && !board[i][j+1].equals(".") && !board[i][j+1].equals("M") && !sunken.contains(board[i][j+1])) //if the position below is within the board and has not been shot at
									probabilityMap[i][j-1] += 15; //increase probability of square above by 15
								else //otherwise increase probability of square below by 10
									probabilityMap[i][j-1] += 10;
							}
						}
					}
				}
			}
			if (ships.size() == 1 && ships.get(0).getName().equals("Destroyer")) {
				if (board[6][1].equals("."))
					guess = "G2";
				else
					guess = generateLegendaryGuess(probabilityMap);
			}
			else
				guess = generateLegendaryGuess(probabilityMap); //generate legendary guess
		}
		output.println("AI outputs '" + guess + "'");
		output.close();
		return guess; //return the guess
	}
	
	/**
	 * Generates a guess beside a previous hit; direction guessed is based on level of difficulty
	 * 
	 * @param level
	 */
	private void guessBeside(int index) {
		int x = guess.charAt(0) - 'A'; //gets position of guess
		int y = Integer.parseInt(guess.substring(1))-1;
		if (lastHits.get(index).size() == 1) { //if there was one previous hit
			if (x-1 >= 0 && board[x-1][y] == ".") { //if possible and valid (not already guessed and within board), guess spot above and determines next guess as up
				guess = Character.toString((char)((x-1)+'A')) + Integer.toString(y+1);
				directionOfHit = "UP";
			}
			else if (x+1 < 10 && board[x+1][y] == ".") { //else if possible and valid (not already guessed and within board), guess spot below and determine next guess as down
				guess = Character.toString((char)((x+1)+'A')) + Integer.toString(y+1);
				directionOfHit = "DOWN";
			}
			else if (y-1 >= 0 && board[x][y-1] == ".") { //else if possible and valid (not already guessed and within board), guess spot to the left and determine next guess as left
				guess = Character.toString((char)(x+'A')) + Integer.toString(y);
				directionOfHit = "LEFT";
			}
			else { //otherwise guess spot to the right and determine next guess as right
				guess = Character.toString((char)(x+'A')) + Integer.toString(y+2);
				directionOfHit = "RIGHT";
			}
		}
		else { //if there is more than one hit
			if (directionOfHit == "UP") { //if ship is going up
				if (x-1 < 0 || board[x-1][y] != ".") { //if next spot above is invalid (out of board or already guessed), change direction of shot to down
					directionOfHit = "DOWN";
					x = lastHits.get(index).get(0).charAt(0); //gets position of first hit on ship
					y = Integer.parseInt(lastHits.get(index).get(0).substring(1));
					guess = Character.toString((char)(x+1)) + y; //guess down one spot from first hit on that ship
				}
				else //otherwise if spot above is valid, guess it
					guess = Character.toString((char)((x-1)+'A')) + Integer.toString(y+1);
			}
			else if (directionOfHit == "DOWN") { //if ship is going down
				if (x+1 >= 10 || board[x+1][y] != ".") { //if next spot above is invalid (out of board or already guessed), change direction of guess to up
					directionOfHit = "UP";
					x = lastHits.get(index).get(0).charAt(0); //gets posiition of first hit on ship
					y = Integer.parseInt(lastHits.get(index).get(0).substring(1));
					guess = Character.toString((char)(x-1)) + y; //guess up one spot from first hit on that ship
				}
				else //otherwise if spot below is valid, guess it
					guess = Character.toString((char)((x+1)+'A')) + Integer.toString(y+1);
			}
			else if (directionOfHit == "LEFT") { //if ship is going left
				if (y-1 < 0 || board[x][y-1] != ".") { //if next spot above is invalid (out of board or already guessed), change direction of guess to right
					directionOfHit = "RIGHT";
					guess = Character.toString(lastHits.get(index).get(0).charAt(0)) + (Integer.parseInt(lastHits.get(index).get(0).substring(1))+1); //guess one spot to the right of first hit on ship
				}
				else //otherwise if spot to the left is valid, guess it
					guess = Character.toString((char)((x)+'A')) + Integer.toString(y);
			}
			else { //if ship is going right
				if (y+1 >= 10 || board[x][y+1] != ".") { //if next spot above is invalid (out of board or already guessed), change direction of guess to left
					directionOfHit = "LEFT";
					guess = Character.toString(lastHits.get(index).get(0).charAt(0)) + (Integer.parseInt(lastHits.get(index).get(0).substring(1))-1); //guess one spot to the left of first hit on ship
				}
				else //otherwise if spot to the right is valid, guess it
					guess = Character.toString((char)((x)+'A')) + Integer.toString(y+2);
			}
		}
	}
	
	/**
	 * Checks result of guesses
	 * 
	 * @param ans
	 * @param name
	 */
	
	public void guess(int ans, Ship ship, int shipSize, ArrayList<Ship> ships) throws Exception{
		PrintWriter output = new PrintWriter(new FileWriter("gamehistory.txt", true));
		numOfGuesses++; //increase numOfGuesses by 1
		String shortName = Character.toString(ship.getName().charAt(0));
		if (ship.getName().equals("Cruiser"))
			shortName = "c";
		markPosition(guess, shortName); //marks the position
		String[] names = {"C", "B", "c", "S", "D"};
		int index = -1;
		for (int i = 0; i < names.length; i++){
			if (names[i].equals(shortName)) {
			index = i; break;
			}
		}
		if (ans == 2) { // if ship was hit
			lastHits.get(index).add(guess); //add guess to lasthits arraylist
			hits++;
		}
		if (lastHits.get(index).size() == shipSize) {
			numShipsLeft--; //minus one ship alive
			sunken.add(shortName);
			lastHits.get(index).clear(); //clear the arraylist with previous hits
			directionOfHit = ""; //clears direction to guess
			Main.gameFrame.setGuessStatus("Your "+ship.getName()+" was sunk");
//			System.out.println("Your "+ship.getName() + " was sunk");
			output.println("User inputs 'HIT, SUNK " + ship.getName() + "'");
			ships.remove(ship);
			Main.gameFrame.changeChoice();
			output.close();
		}
		else {
			output.println("User inputs 'HIT " + ship.getName() + "'");
			output.close();
		}
		
		if (numShipsLeft <= 0) { //if there are no more ships left
			won = true; //game has been won
			endgame(); //print end of game message
		}
		printBoard(Main.getPanel());
	}
	
	/**
	 * Overloaded guess method to check results of guesses if shot was a miss
	 * 
	 * @param ans
	 * @param name
	 * @param shipSize
	 */
	public void guess(int ans, String name, int shipSize) throws Exception{
		PrintWriter output = new PrintWriter(new FileWriter("gamehistory.txt", true));
		output.println("User inputs 'MISS'");
		numOfGuesses++; //increase numOfGuesses by 1
		misses++;
		markPosition(guess, name); //marks the position
		int index = checkEmpty();
		if (ans == 1 && index >= 0) //if ship was a miss and there are still previous hits in arraylist lastHits
			guess = lastHits.get(index).get(lastHits.get(index).size()-1); //guess becomes the most recent hit
		if (numShipsLeft <= 0) { //if there are no more ships left
			won = true; //game has been won
			endgame(); //print end of game message
		}
		printBoard(Main.getPanel());
		output.close();
	}
	
	/**
	 * Generate a legendary level guess
	 * 
	 * @param probMap
	 * @return the guess
	 */
	private String generateLegendaryGuess(int[][] probMap) {
		int max = probMap[0][0]; // Find the largest value on the probability heat map - this represents the position with the greatest weighting (aka position that most likely contains ship)
		int letter = 0, num = 0; // The row and column that contains the max values
		for (int i = 0; i < probMap.length;i++) { //go through each index of the probMap and find the maximum value
			for (int j = 0; j < probMap[0].length; j++) {
				if (probMap[i][j] > max) { //replace max value if bigger value is found
					max = probMap[i][j];
					letter = i; //keep track of the position in the probMap
					num = j;
				}
			}
		}
		return Character.toString((char)(letter+'A')) + Integer.toString(num+1); //return the guess position
	}
	
	/**
	 * Print out message at the end of game
	 */
	public void endgame() throws Exception {
		Main.won = true;
		saveBoard();
		PrintWriter output = new PrintWriter(new FileWriter("gamehistory.txt", true));
		Main.cBoard.printFinalBoard(Main.gameFrame.getPanel1());
		if (!won) { //if user won, say how many moves user took to win
			Main.gameFrame.setMessage("You won the game in "+numOfGuesses +" moves");
//			System.out.println("You won the game in "+ numOfGuesses +" moves.");
			output.println("User won the game in " + numOfGuesses + " moves.");
		}
		else { //if user lost, wish them better luck next time
			Main.gameFrame.setMessage("The computer won the game in "+numOfGuesses +" moves");
			Main.gameFrame.setGuessStatus("Better luck next time!");
//			System.out.println("The computer won the game in "+numOfGuesses +" moves.");
//			System.out.println("Better luck next time!");
			output.println("Computer won the game in " + numOfGuesses + " moves.");
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
		int y = Integer.parseInt(pos.substring(1))-1; //determines number position
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
	 * Prints the board on the GUI
	 */
	public void printBoard(JPanel panel) {
		String letters = "ABCDEFGHIJ"; //letters/rows on the board
		panel.removeAll(); //removes everything from panel
		panel.revalidate();
		panel.repaint();
		panel.add(new JLabel());
		for (int i = 1; i < 11; i++) { 
			JLabel label = new JLabel(Integer.toString(i)); //label the columns
			label.setFont(new Font("Sakura Blossom", Font.BOLD, 20));
			label.setHorizontalAlignment(SwingConstants.CENTER);
			panel.add(label);
		}
		for (int i = 0; i < 10; i++) { //label the rows
			JLabel label = new JLabel(Character.toString(letters.charAt(i)));
			label.setFont(new Font("Sakura Blossom", Font.BOLD, 20));
			label.setHorizontalAlignment(SwingConstants.CENTER);
			panel.add(label);
			for (int j = 0; j < 10; j++) {
				if (board[i][j].equals(".")) { //if position was not hit, keep it blank
					label = new JLabel(board[i][j]);
					label.setFont(new Font("Sakura Blossom", Font.BOLD, 20));
					label.setHorizontalAlignment(SwingConstants.CENTER);
					panel.add(label);
				}
				else if (board[i][j].equals("C")){ //prints symbol for Carrier
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
				else if (board[i][j].equals("c")) {//prints symbol for cruiser
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
				else if (board[i][j].equals("M")) { //prints symbol for a miss
					label = new JLabel("");
					label.setIcon(getImageIcon("White Flower.png"));
					label.setHorizontalAlignment(SwingConstants.CENTER);
					panel.add(label);
				}
			}
		}
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
			output.println(letters.charAt(i) + Arrays.toString(board[i]));
			output2.println(letters.charAt(i) + Arrays.toString(board[i]));
		}
		output.close();
		output2.close();
	}
}


