/* Name: Fiona Dang
 * File: Main.java
 * Purpose: Main for Battleship game
 * Date: June 2, 2024
 **/

import java.util.*;
import java.io.*;
import javax.sound.sampled.AudioInputStream; //audio
import javax.sound.sampled.AudioSystem; 
import javax.sound.sampled.Clip;

import javax.imageio.ImageIO;
import javax.swing.*; //import libaries needed for GUI

import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;

public class Main extends JFrame implements ActionListener{
	private static final long serialVersionUID = 1L;
	static final int GRIDSIZE = 10;
	static int musicState = 0;
	static String filepath = "audio.wav";
	static long clipTimePosition = 0;
	static Clip clip;
	JButton musicButton = new JButton("");
	
	BufferedImage image = null;
	static int level = 0;
	static int order = 0;
	
	static CodeTimer codetimer;
	
	private static JLabel timeLabel;
	static boolean won = false;
	
	static String gameHistoryGUI;
	
	JPanel menuPanel = new JPanel(); //panels, labels, and buttons for main menu
	JLabel battleshipLabel = new JLabel(" Sakura Battleship ");
	JPanel battleshipLabelPanel = new JPanel();
	JButton startButton = new JButton("START");
	JPanel startButtonPanel = new JPanel();
	JButton instructionsButton = new JButton("INSTRUCTIONS");
	JPanel instructionsButtonPanel = new JPanel();
	JButton previousGamesButton = new JButton("PREVIOUS GAMES");
	JPanel previousGamesButtonPanel = new JPanel();
	static GameFrame gameFrame = new GameFrame();
	JPanel overallPanel = new JPanel();
	
	JPanel userBoard = new JPanel();
	JLabel[][] twoDUserBoard = new JLabel[GRIDSIZE][GRIDSIZE];
	final Font TITLE_FONT = new Font("Sakura", Font.BOLD, 100);
	final Font FONT = new Font("Sakura Blossom", Font.PLAIN, 50);
	
	private static boolean turn = false;
	static ComputerBoard cBoard; //creates a ComputerBoard object
	static TrackBoard tBoard; //creates a TrackBoard object
	
	static ArrayList<Ship> ships = new ArrayList<>(); //creates an ArrayList for ships
	static ArrayList<Ship> playerShips = new ArrayList<>(); //creates an ArrayList for player's ships
	
	private static JPanel panel = gameFrame.getPanel();
	private static JPanel panel_1 = gameFrame.getPanel1();
	
	//Constructor - Sets up GUI
	public Main() {
		try {
			image = ImageIO.read(new File("OIP.jpg")); //gets background image
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		ships.add(new Ship("Carrier", 5)); //adds the different ships with unique names and sizes
		ships.add(new Ship("Battleship", 4));
		ships.add(new Ship("Cruiser", 3));
		ships.add(new Ship("Submarine", 3));
		ships.add(new Ship("Destroyer", 2));
		
		playerShips.add(new Ship("Carrier", 5)); //adds the different ships with unique names and sizes
		playerShips.add(new Ship("Battleship", 4));
		playerShips.add(new Ship("Cruiser", 3));
		playerShips.add(new Ship("Submarine", 3));
		playerShips.add(new Ship("Destroyer", 2)); 
		
		setTitle("Battleship"); //title of window
		setSize(1000, 600);	 //size of window
		setContentPane(new ImagePanel(image));
		
		FlowLayout layout1 = new FlowLayout(); //sets layout for main menu
		GridLayout layout2 = new GridLayout(4, 1);
		layout2.setVgap(30);
		setLayout(layout1); //menu settings
		menuPanel.setOpaque(false);
		overallPanel.setOpaque(false);
		menuPanel.setLayout(layout2);
		
		musicButton.setBackground(new Color(236, 0, 206));
		musicButton.setOpaque(true);
		ImageIcon icon = new ImageIcon("Mute Icon.png");
		Image newimg = icon.getImage().getScaledInstance(30, 30,  java.awt.Image.SCALE_SMOOTH); // scale it the smooth way  
		icon = new ImageIcon(newimg);
		musicButton.setIcon(icon);
		
		startButton.addActionListener(this); //allows an action to happen when buttons pressed
		instructionsButton.addActionListener(this);
		previousGamesButton.addActionListener(this);
		musicButton.addActionListener(this);
		
		battleshipLabel.setFont(TITLE_FONT); //label settings
		battleshipLabel.setBackground(new Color(128, 0, 64));
		battleshipLabel.setOpaque(true);
		battleshipLabel.setForeground(Color.WHITE);
		battleshipLabelPanel.add(battleshipLabel);
		menuPanel.add(battleshipLabel);
		battleshipLabel.setHorizontalAlignment(SwingConstants.CENTER);

		startButton.setFont(FONT); //button settings
		startButton.setBackground(Color.PINK);
		startButton.setForeground(Color.WHITE);

		instructionsButton.setFont(FONT);
		instructionsButton.setBackground(Color.PINK);
		instructionsButton.setForeground(Color.WHITE);

		previousGamesButton.setFont(FONT);
		previousGamesButton.setBackground(Color.PINK);
		previousGamesButton.setForeground(Color.WHITE);

		
		startButtonPanel.add(startButton);
		menuPanel.add(startButton);
		startButton.setHorizontalAlignment(SwingConstants.CENTER);

		
		instructionsButtonPanel.add(instructionsButton);
		menuPanel.add(instructionsButton);
		instructionsButton.setHorizontalAlignment(SwingConstants.CENTER);
	
		
		previousGamesButtonPanel.add(previousGamesButton);
		menuPanel.add(previousGamesButton); //adds all menu GUI objects into menu panel
		previousGamesButton.setHorizontalAlignment(SwingConstants.CENTER);

		
		overallPanel.add(menuPanel);
		add(overallPanel);
		add(musicButton);
		
		setVisible(true);
	}
	
	/**
	 * Performs game functions, like creating ships, placing AI ships, etc.
	 * 
	 * @throws Exception
	 */
	public static void game() throws Exception {
		PrintWriter output = new PrintWriter(new FileWriter("gamehistory.txt", true));
		PrintWriter output2 = new PrintWriter(new FileWriter("gamehistorydisplay.txt", true));
		output.println("Game started");
		output2.println("New game");
		
		String[][] tempBoard = new String[10][10]; //create a board that is 10x10
		for (int i = 0; i < 10; i++) {
			for (int j = 0; j < 10; j++) {
				tempBoard[i][j] = "."; //set every spot in the board as a .
			}
		}
		
		if (order == 3) { //if coin toss, randomly decide who goes first
			order = (int) (Math.random() * 2+1);
		}
		
		for (int i = 0; i < ships.size(); i++) { //for each ship, generate a position based on level of difficulty chosen
			if (level == 1)
				generateEasyPosition(ships.get(i), tempBoard);
			else if (level == 2)
				generateShipPositions(ships.get(i), tempBoard);
			else
				generateHardPosition(ships.get(i), tempBoard);
		}
		output.println("Here are the AI's ship positions:");
		output2.println("Here are the AI's ship positions:");
		String letters = "ABCDEFGHIJ"; //letters/rows on the board
		output.println("  1  2  3  4  5  6  7  8  9  10"); //columns on board
		output2.println(" | 1| 2| 3| 4| 5| 6| 7| 8| 9| 10"); //columns on board
		for (int i = 0; i < 10; i++) {//prints out position of ships on AI's board in file
			output.println(letters.charAt(i) + Arrays.toString(tempBoard[i]));
			output2.println(letters.charAt(i) + Arrays.toString(tempBoard[i]));
		}
		output.close();
		output2.close();
		
		cBoard = new ComputerBoard(ships); //creates a ComputerBoard object
		tBoard = new TrackBoard(ships.size()); //creates a TrackBoard object
		
//		while(!cBoard.getWon() && !tBoard.getWon() && turn == false) { //while neither side has won
//			System.out.println("Your Board: "); //print out both boards
			tBoard.printBoard(panel);
//			System.out.println("Computer's Board: ");
			cBoard.printBoard(panel_1);
			if (order == 1) { //User goes first
				turn = false;
				gameFrame.setMessage("Your Turn");
//				System.out.println("Enter your guess in form Letter# (eg. A1): "); //prompt user to enter in their shot/ guess
//				boolean validGuess = false; //assume guess is invalid
//				while (validGuess == false) { //while guess is invalid, keep prompting user to enter a valid guess
//					try { //try to get the guess and display results of the guess
//						g = sc.next();
//						if (cBoard.checkGuess(g)){
//							cBoard.guess(g); //displays results of the guess
//							validGuess = true; //break out of the loop by validating guess
//						}
//						else
//							System.out.println("You already guessed this position. Guess again.");
//					}
//					catch(ArrayIndexOutOfBoundsException e) { //prompt user to enter new guess if guess is outside board
//						System.out.println("Please enter a valid guess.");
//					}
//					catch(NumberFormatException e) { //prompt user to enter a new guess if coordinates were not guessed
//						System.out.println("Please enter a valid guess.");
//					}
//					catch(InputMismatchException e) {
//						System.out.println("Please enter a valid guess.");
//					}
//				}
//				if (level == 1) //computer generates shot/ guess against user based on level of difficulty
//					g = tBoard.generateGuess("Easy", playerShips);
//				else if (level == 2)
//					g = tBoard.generateGuess("Hard", playerShips);
//				else
//					g = tBoard.generateGuess("Legendary", playerShips);
//				if (cBoard.getWon()|| tBoard.getWon())
//					break;
//				gameFrame.setMessage("The computer guessed position: "+g);
//				System.out.println("The computer guessed position: "+g); //print out computer's guess
//				System.out.println("Please choose one of the following \n 1) Miss \n 2) Hit"); //prompts user for results of computer's guess
//				int ans = sc.nextInt();
//				if (ans == 2) { //if shot was a hit or a sunk, prompt user to enter a number associated with the name of ship that was hit/ sunk
//					System.out.println("Pick the name of the ship: \n");
//					for (int i = 0; i < playerShips.size(); i++) { //print out list of ship names for user to pick
//						System.out.println((i+1)+") "+playerShips.get(i).getName());
//					}
//					int option = sc.nextInt();
//					tBoard.guess(ans, playerShips.get(option - 1), playerShips.get(option-1).getSize(), playerShips);
//				}
//				else //if computer's shot was a miss, state it on the board
//					tBoard.guess(ans, "M", -1);
			}
			else {
				turn = true;
				generateGuesses();
//				if (level == 1) //computer generates shot/ guess against user based on level of difficulty
//					g = tBoard.generateGuess("Easy", playerShips);
//				else if (level == 2)
//					g = tBoard.generateGuess("Hard", playerShips);
//				else
//					g = tBoard.generateGuess("Legendary", playerShips);
//				if (cBoard.getWon()|| tBoard.getWon())
//					break;
//				gameFrame.setMessage("The computer guessed position: "+g);
//				System.out.println("The computer guessed position: "+g); //print out computer's guess
//				System.out.println("Please choose one of the following \n 1) Miss \n 2) Hit"); //prompts user for results of computer's guess
//				int ans = sc.nextInt();
//				if (ans == 2) { //if shot was a hit or a sunk, prompt user to enter a number associated with the name of ship that was hit/ sunk
//					System.out.println("Pick the name of the ship: \n");
//					for (int i = 0; i < playerShips.size(); i++) { //print out list of ship names for user to pick
//						System.out.println((i+1)+") "+playerShips.get(i).getName());
//					}
//					int option = sc.nextInt();
//					tBoard.guess(ans, playerShips.get(option - 1), playerShips.get(option-1).getSize(), playerShips);
//				}
//				else //if computer's shot was a miss, state it on the board
//					tBoard.guess(ans, "M", -1);
//				System.out.println("Enter your guess in form Letter# (eg. A1): "); //prompt user to enter in their shot/ guess
//				boolean validGuess = false; //assume guess is invalid
//				while (validGuess == false) { //while guess is invalid, keep prompting user to enter a valid guess
//					try { //try to get the guess and display results of the guess
//						g = sc.next();
//						if (cBoard.checkGuess(g)){
//							cBoard.guess(g); //displays results of the guess
//							validGuess = true; //break out of the loop by validating guess
//						}
//						else
//							System.out.println("You already guessed this position. Guess again.");
//					}
//					catch(ArrayIndexOutOfBoundsException e) { //prompt user to enter new guess if guess is outside board
//						System.out.println("Please enter a valid guess.");
//					}
//					catch(NumberFormatException e) { //prompt user to enter a new guess if coordinates were not guessed
//						System.out.println("Please enter a valid guess.");
//					}
//				}
			}
//		}
		
//		cBoard.printBoard(panel_1); //print both boards
//		tBoard.printBoard(panel);
		
//		cBoard.saveBoard(); //saves boards to file
//		tBoard.saveBoard();
		
		output.println(""); //saves file
		output.close();
		
	}
	
	/**
	 * Generates guesses b
	 * ased on level of difficulty chosen
	 * 
	 * @throws Exception
	 */
	public static void generateGuesses() throws Exception {
		String g;
		if (level == 1) //computer generates shot/ guess against user based on level of difficulty
			g = tBoard.generateGuess("Easy", playerShips);
		else if (level == 2)
			g = tBoard.generateGuess("Hard", playerShips);
		else
			g = tBoard.generateGuess("Legendary", playerShips);
		gameFrame.setMessage("The computer guessed position: "+g);
	}
	
	/**
	 * Returns panel
	 * @return panel
	 */
	public static JPanel getPanel() {
		return panel;
	}
	
	/**
	 * Returns panel_1
	 * @return panel_1
	 */
	public static JPanel getPanel1() {
		return panel_1;
	}
	
	/**
	 * Returns turn
	 * @return turn
	 */
	public static boolean getTurn() {
		return turn;
	}
	
	/**
	 * Sets turn
	 */
	public static void setTurn(boolean change) {
		turn = change;
	}
	
	// ============================================================================================================
	  
		  // ACTION LISTENER - This method runs when an event occurs
		  // Code in here only runs when a user interacts with a component
		  // that has an action listener attached to it	
	public void actionPerformed(ActionEvent event){
		String command = event.getActionCommand();
		if(command.equals("START")) { //opens start frame if start button pressed
			StartFrame window = new StartFrame(gameFrame, codetimer, false);
			window.setVisible();
			timeLabel = gameFrame.getTimeLabel();
		}
		else if (command.equals("")) { //if sound button was pressed
			musicState++; //increase musicState counter by 1
			if (musicState%2 == 1){ //if musicState is odd, change button text to Sound: On
				ImageIcon icon = new ImageIcon("Sound Icon.png");
				Image newimg = icon.getImage().getScaledInstance(30, 30,  java.awt.Image.SCALE_SMOOTH); // scale it the smooth way  
				icon = new ImageIcon(newimg);
				musicButton.setIcon(icon);
			}
			else {
				ImageIcon icon = new ImageIcon("Mute Icon.png");
				Image newimg = icon.getImage().getScaledInstance(30, 30,  java.awt.Image.SCALE_SMOOTH); // scale it the smooth way  
				icon = new ImageIcon(newimg);
				musicButton.setIcon(icon); //if musicState is even, change button text to Sound: Off
			}
			PlayMusic(filepath); //play music function
		}
		else if (command.equals("INSTRUCTIONS")) { //opens instructions if instructions button pressed
			InstructionFrame instructionWindow = new InstructionFrame();
			instructionWindow.setVisible();
		}
		else if (command.equals("PREVIOUS GAMES")){ //opens game history if previous games button pressed
			GameHistoryFrame gameHistoryWindow = new GameHistoryFrame();
			gameHistoryWindow.setVisible();
		}
	}
	
	/**
	 * Turns music on or off
	 * 
	 * @param location
	 */
	public static void PlayMusic(String location) {
		try {
			File musicPath = new File(location);
			if(musicPath.exists()) { //if music path exists
				if (musicState == 1) { //if musicState is 1 / sound button was pressed for the first time
					AudioInputStream audioInput = AudioSystem.getAudioInputStream(musicPath); 
					clip = AudioSystem.getClip();
					clip.open(audioInput); //open the audio
					clip.start(); //start the audio
					clip.loop(Clip.LOOP_CONTINUOUSLY); //loop it continuously
				}
				if(musicState%2 == 1) { //otherwise if musicState is odd, start music
					clip.start();
					clip.loop(Clip.LOOP_CONTINUOUSLY); //loop it continuously
				}
				else { //otherwise, pause the music
					clip.stop();
				}
			}
			else { //otherwise report error
				System.out.println("Can't find file");
			}
		}
		catch (Exception e) {
			System.out.println(e);
		}
	}
	
	public static void main(String[] args) throws Exception {
		Main GUI = new Main(); //creates a Main object
		File file = new java.io.File("gamehistorydisplay.txt");
		Scanner input = new Scanner(file);
		gameHistoryGUI = "<html>";
		while(input.hasNext()) { //reads game history file
			gameHistoryGUI += input.nextLine() + "<br/>";
		}
		gameHistoryGUI += "</html>";
		input.close();
//		Scanner sc = new Scanner(System.in); //creates Scanner object
		
		// For console game - not GUI game
		/*int order = 0;
		do { 
			System.out.println("Who goes first? 1) Player 2) Computer 3) Toss Coin 4) See previous games"); //prompt user to pick who goes first
			order = sc.nextInt();
			if (order == 3) { //if coin toss, randomly decide who goes first
				order = (int) (Math.random() * 2+1);
			}
			if (order == 1) { //output who goes first
				System.out.println("You are going first.");
				output.println("User is going first.");
			}
			else if (order == 2) {
				System.out.println("Computer is going first.");
				output.println("Computer is going first.");
			}
			else { //prints out previous games
				String gameHistory = new String (Files.readAllBytes(Paths.get("gamehistorydisplay.txt")));
				System.out.println(gameHistory);
			}
		} while (order != 1 && order != 2); //while user has not decided who goes first, keep on prompting
		
		System.out.println("Pick a level: \n 1) Easy \n 2) Hard \n 3) Legendary"); //prompts user to pick a level of difficulty
		int level = sc.nextInt();
		*/
		
		
//		System.out.println("Legend: \n Battleship -> B \n Carrier -> C \n Cruiser -> c \n"
//				+ " Submarine -> S \n Destroyer -> D \n Miss -> M"); //prints out a legend for the console
	}
	
	/**
	 * Updates timer 
	 */
	public static boolean updateTimeLabel(int secondsPassed) {
		int hours = secondsPassed / 3600; //track number of hours
        int minutes = ((secondsPassed -(hours*3600))/60); //track number of minutes
        int seconds = (secondsPassed % 60); //tracks number of seconds
        String time = String.format("%02d:%02d:%02d", hours, minutes, seconds);
        timeLabel.setText(time); //sets timer in GUI
        return won; //returns whether or not game has been won
    }
	
	/**
	 * Generates easy positions for computer's ship
	 * 
	 * @param ship
	 * @param board
	 */
	public static void generateEasyPosition(Ship ship, String[][] board) {
		// Research shows that most common placements for ships are towards the center of the board
		// In addition, when ships are placed in closer vicinity to each other, they are more easily guessed
		// Here is an updated version for the generation of ship positions to allow for a more centralized and easily guessed version of the ships
		boolean valid = false;
		int letter, num, direction;
		while (!valid) { //while valid is false
			// Keep the constraints within a 6 by 6 board in the center instead of a 10 by 10 board
			letter = (int) (Math.random()*6)+2; //generates a random letter position in middle of board
			num = (int) (Math.random()*6)+2;  //generates a random number position in middle of board
			direction = (int) (Math.random()*4); //generates direction of placementn
			valid = true; //sets valid to true
			if (direction == 0) { //down
				int count = 0;
				for (int i = 0; i < ship.getSize(); i++) { 
					if (letter < 2 || letter >= 8) { //if letter position is outside 6x6 board, then it is invalid
						valid = false;
						break;
					}
					else if (board[letter][num] != ".") { // else if that board position is not previously empty, it is invalid
						valid = false;
						break;
					}
					board[letter][num] = "X"; //set the board position to X
					count++;
					letter--; //go down to check the next spot
				}
				if (valid) { //if valid, set the ships position there
					ship.setPos(num+1, (char)(letter + 'A'+1), "DOWN");
				}
				else { //otherwise, undo the marked Xs and replace them back with .s
					letter++;
					for (int i = 0; i < count; i++) {
						board[letter][num] = ".";
						letter++;
					}
				}
			}
			else if (direction == 1) { //up
				int count = 0;
				for (int i = 0; i < ship.getSize(); i++) { //for each of the ship's spots
					if (letter < 2 || letter >= 8) { //if letter position is outside 6x6 board, then it is invalid
						valid = false;
						break;
					}
					else if (board[letter][num] != ".") { //else if that board position is not previously empty, it is invalid
						valid = false;
						break;
					}
					board[letter][num] = "X"; //set the board position to X
					count++;
					letter++; //go up to check the next spot
				}
				if (valid) { //if valid, set the ships position there
					ship.setPos(num+1, (char)(letter + 'A'-1), "UP");
				}
				else { //otherwise, undo the marked Xs and replace them back with .s
					letter--;
					for (int i = 0; i < count; i++) {
						board[letter][num] = ".";
						letter--;
					}
				}
			}
			else if (direction == 2) {//RIGHT
				int count = 0;
				for (int i = 0; i < ship.getSize(); i++) { //for each of the ship's spots
					if (num < 2 || num >= 8) { //if number position is outside 6x6 board, then it is invalid
						valid = false;
						break;
					}
					else if (board[letter][num] != ".") { //else if that board position is not previously empty, it is invalid
						valid = false;
						break;
					}
					board[letter][num] = "X"; //set the board position to X
					count++;
					num--; //go right to check the next spot
				}
				if (valid) { //if valid, set the ships position there
					ship.setPos(num+2, (char)(letter + 'A'), "RIGHT");
				}
				else { //otherwise, undo the marked Xs and replace them back with .s
					num++;
					for (int i = 0; i < count; i++) {
						board[letter][num] = ".";
						num++;
					}
				}
			}
			else {//LEFT
				int count = 0;
				for (int i = 0; i < ship.getSize(); i++) { //for each of the ship's spots
					if (num < 2 || num >= 8) { //if number position is outside 6x6 board, then it is invalid
						valid = false;
						break;
					}
					else if (board[letter][num] != ".") { //else if that board position is not previously empty, it is invalid
						valid = false;
						break;
					}
					board[letter][num] = "X"; //set the board position to X
					count++;
					num++; //go left to check the next spot
				}
				if (valid) { //if valid, set the ships position there
					ship.setPos(num, (char)(letter + 'A'), "LEFT");
				}
				else { //otherwise, undo the marked Xs and replace them back with .s
					num--;
					for (int i = 0; i < count; i++) {
						board[letter][num] = ".";
						num--;
					}
				}
			}
		}
	}
	
	/**
	 * Checks if a spot adjacent to the current target spot is occupied by a ship
	 * 
	 * @param board
	 * @param letter
	 * @param num
	 * @param direction
	 * @return whether or not a spot adjacent to the current target spot is occupied by a ship
	 */
	public static boolean checkBeside(String[][] board, int letter, int num, int direction) {
		if (direction == 0||direction == 1) { //if going up or down
			if (num + 1 < 10 && board[letter][num+1] == "X") //if spot to the right is occupied, return true
				return true;
			else if (num - 1 >= 0 && board[letter][num-1] == "X") //if spot to the left is occupied, return true
				return true;
		}
		else {//if going right/ left
			if (letter + 1 < 10 && board[letter+1][num] == "X") //if spot to downward is occupied, return true
				return true;
			else if (letter - 1 >= 0 && board[letter-1][num] == "X") //if spot to upwards is occupied, return true
				return true;
		}
		return false;
	}
	
	/**
	 * Generate a hard to guess ship position
	 * 
	 * @param ship
	 * @param board
	 */
	public static void generateHardPosition(Ship ship, String[][] board) {
		/* Strategies to win at ship placement:
		 * 1) Use more vertical placed ships
		 * 2) Keep space between ships
		 */
		boolean valid = false;
		int letter, num, direction;
		while (!valid) {
			direction = (int) (Math.random()*4); //randomly generate a direction (chances of right/left doubled)
			if (direction == 2 || direction == 3) {
				letter = (int) (Math.random()*4); //randomly generate a letter position anywhere on board
				if (letter == 0) { // increase chances for letter positions near edges
					letter = 0;
				}
				else if (letter == 1) {
					letter = 1;
				}
				else if (letter == 2) {
					letter = 8;
				}
				else if (letter == 3) {
					letter = 9;
				}
				num = (int) (Math.random()*10); //randomly generate a number position anywhere on board
			}
			else {
				num = (int) (Math.random()*4);
				if (num == 0) {
					num = 0;
				}
				else if (num == 1) {
					num = 1;
				}
				else if (num == 2) {
					num = 8;
				}
				else if (num == 3) {
					num = 9;
				}
				letter = (int) (Math.random()*10);
			}

			
			
			valid = true;
			if (direction == 0) { //down
				int count = 0;
				for (int i = 0; i < ship.getSize(); i++) { //for each of the ship's spots
					if (letter < 0 || letter >= 10) { //if letter is outside board, invalid
						valid = false;
						break;
					}
					else if (board[letter][num] != ".") { //else if spot is already occupied, it is invalid
						valid = false;
						break;
					}
					else if (checkBeside(board, letter, num, 0)) {
						valid = false;
						break;
					}
					board[letter][num] = "X";
					count++;
					letter--; //go down to the next spot
				}
				if (valid) { //if valid, set ship's position there
					ship.setPos(num+1, (char)(letter + 'A'+1), "DOWN");
				}
				else { //otherwise, undo the marked Xs and replace them back with .s
					letter++;
					for (int i = 0; i < count; i++) {
						board[letter][num] = ".";
						letter++;
					}
				}
			}
			else if (direction == 1) { //up
				int count = 0;
				for (int i = 0; i < ship.getSize(); i++) { //for each of the ship's spots
					if (letter < 0 || letter >= 10) { //if letter is outside board, invalid
						valid = false;
						break;
					}
					else if (board[letter][num] != ".") { //else if spot is already occupied, it is invalid
						valid = false;
						break;
					}
					else if (checkBeside(board, letter, num, 1)) { //if there is an occupied spot beside the ship, it is invalid
						valid = false; break;
					}
					board[letter][num] = "X"; //mark the ship with an X on the board
					count++;
					letter++; //go up to the next spot
				}
				if (valid) { //if valid, set ship's position there
					ship.setPos(num+1, (char)(letter + 'A'-1), "UP");
				}
				else { //otherwise, undo the marked Xs and replace them back with .s
					letter--;
					for (int i = 0; i < count; i++) {
						board[letter][num] = ".";
						letter--;
					}
				}
			}
			else if (direction == 2) {//right
				int count = 0;
				for (int i = 0; i < ship.getSize(); i++) { //for each of the ship's spots
					if (num < 0 || num >= 10) { //if number is outside board, invalid
						valid = false;
						break;
					}
					else if (board[letter][num] != ".") { //else if spot is already occupied, it is invalid
						valid = false;
						break;
					}
					else if (checkBeside(board, letter, num, 2)) { //if there is an occupied spot beside the ship, it is invalid
						valid = false; break;
					}
					board[letter][num] = "X"; //mark the ship with an X on the board
					count++;
					num--; //go right to the next spot
				}
				if (valid) { //if valid, set ship's position there
					ship.setPos(num+2, (char)(letter + 'A'), "RIGHT");
				}
				else { //otherwise, undo the marked Xs and replace them back with .s
					num++;
					for (int i = 0; i < count; i++) {
						board[letter][num] = ".";
						num++;
					}
				}
			}
			else {//left
				int count = 0;
				for (int i = 0; i < ship.getSize(); i++) { //for each of the ship's spots
					if (num < 0 || num >= 10) { //if number is outside board, invalid
						valid = false;
						break;
					}
					else if (board[letter][num] != ".") { //else if spot is already occupied, it is invalid
						valid = false;
						break;
					}
					else if (checkBeside(board, letter, num, 3)) { //if there is an occupied spot beside the ship, it is invalid
						valid = false; break;
					}
					board[letter][num] = "X"; //mark the ship with an X on the board
					count++;
					num++; //go left to the next spot
				}
				if (valid) { //if valid, set ship's position there
					ship.setPos(num, (char)(letter + 'A'), "LEFT");
				}
				else { //otherwise, undo the marked Xs and replace them back with .s
					num--;
					for (int i = 0; i < count; i++) {
						board[letter][num] = ".";
						num--;
					}
				}
			}
		}
	}
	/**
	 * Generate a harder to guess ship position (random position anywhere in the board)
	 * 
	 * @param ship
	 * @param board
	 */
	public static void generateShipPositions(Ship ship, String[][] board) {
		boolean valid = false;
		int letter, num, direction;
		while (!valid) {
			letter = (int) (Math.random()*10); //generate a random letter position in the grid
			num = (int) (Math.random()*10);  //generate a random number position in the grid
			direction = (int) (Math.random()*4); //generate a direction (each direction has equal chances)
			valid = true;
			if (direction == 0) { //down
				int count = 0;
				for (int i = 0; i < ship.getSize(); i++) { //for each of the ship's spots
					if (letter < 0 || letter >= 10) { //if letter is outside board, invalid
						valid = false;
						break;
					}
					else if (board[letter][num] != ".") { //else if spot is already occupied, it is invalid
						valid = false;
						break;
					}
					board[letter][num] = "X"; //mark the ship with an X on the board
					count++;
					letter--; //go down to the next spot
				}
				if (valid) { //if valid, set ship's position there
					ship.setPos(num+1, (char)(letter + 'A'+1), "DOWN");
				}
				else { //otherwise, undo the marked Xs and replace them back with .s
					letter++;
					for (int i = 0; i < count; i++) {
						board[letter][num] = ".";
						letter++;
					}
				}
			}
			else if (direction == 1) { //up
				int count = 0;
				for (int i = 0; i < ship.getSize(); i++) { //for each of the ship's spots
					if (letter < 0 || letter >= 10) { //if letter is outside board, invalid
						valid = false;
						break;
					}
					else if (board[letter][num] != ".") { //else if spot is already occupied, it is invalid
						valid = false;
						break;
					}
					board[letter][num] = "X"; //mark the ship with an X on the board
					count++;
					letter++; //go up to the next spot
				}
				if (valid) { //if valid, set ship's position there
					ship.setPos(num+1, (char)(letter + 'A'-1), "UP");
				}
				else { //otherwise, undo the marked Xs and replace them back with .s
					letter--;
					for (int i = 0; i < count; i++) {
						board[letter][num] = ".";
						letter--;
					}
				}
			}
			else if (direction == 2) {//right
				int count = 0;
				for (int i = 0; i < ship.getSize(); i++) { //for each of the ship's spots
					if (num < 0 || num >= 10) { //if number is outside board, invalid
						valid = false;
						break;
					}
					else if (board[letter][num] != ".") { //else if spot is already occupied, it is invalid
						valid = false;
						break;
					}
					board[letter][num] = "X"; //mark the ship with an X on the board
					count++;
					num--; //go right to the next spot
				}
				if (valid) { //if valid, set ship's position there
					ship.setPos(num+2, (char)(letter + 'A'), "RIGHT");
				}
				else { //otherwise, undo the marked Xs and replace them back with .s
					num++;
					for (int i = 0; i < count; i++) {
						board[letter][num] = ".";
						num++;
					}
				}
			}
			else {//left
				int count = 0;
				for (int i = 0; i < ship.getSize(); i++) { //for each of the ship's spots
					if (num < 0 || num >= 10) { //if number is outside board, invalid
						valid = false;
						break;
					}
					else if (board[letter][num] != ".") { //else if spot is already occupied, it is invalid
						valid = false;
						break;
					}
					board[letter][num] = "X"; //mark the ship with an X on the board
					count++;
					num++; //go left to the next spot
				}
				if (valid) { //if valid, set ship's position there
					ship.setPos(num, (char)(letter + 'A'), "LEFT");
				}
				else { //otherwise, undo the marked Xs and replace them back with .s
					num--;
					for (int i = 0; i < count; i++) {
						board[letter][num] = ".";
						num--;
					}
				}
			}
		}
	}
}
