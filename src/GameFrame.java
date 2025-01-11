/* Name: Fiona Dang
 * File: InstructionFrame.java
 * Purpose: This InstructionFrame class creates the popup window that displays the instructions
 * Date: June 9, 2024
 **/

import javax.imageio.ImageIO;
import javax.swing.Box;
import javax.swing.JFrame;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JPanel;
import java.awt.GridLayout;
import java.awt.Image;

import javax.swing.JScrollPane;
import java.awt.Color;
import java.awt.Dimension;

import javax.swing.border.MatteBorder;
import javax.swing.SwingConstants;
import javax.swing.ImageIcon;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import java.awt.Choice;
import javax.swing.JPopupMenu;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.swing.JMenuItem;

public class GameFrame implements ActionListener {

	private JFrame frame;
	private JPopupMenu popupMenu;
	private JPanel panel;
	private JPanel panel_1;
	private JLabel message;
	private JLabel guessStatus;
	private Choice choice = new Choice();
	private JLabel timeLabel;
	
	public JLabel getTimeLabel() {
		return timeLabel;
	}
	/**
	 * Create the application.
	 */
	public GameFrame() {
		initialize();
	}
	
	/**
	 * Gets panel
	 * @return panel
	 */
	public JPanel getPanel() {
		return panel;
	}
	
	/**
	 * Gets panel1
	 * @return panel_1
	 */
	public JPanel getPanel1() {
		return panel_1;
	}
	
	/**
	 * Sets the frame visible or invisible
	 */
	public void setVisible() {
		if (frame.isVisible()) { //if frame is visible, make it not visible
			frame.setVisible(false);
		}
		else { //if frame is not visible, make it visible
			frame.setVisible(true);
		}
	}
	
	/**
	 * Sets the message to the given message
	 * @param m
	 */
	public void setMessage(String m) {
		message.setText(m);
	}
	
	public Choice getChoice() {
		return choice;
	}
	
	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		BufferedImage image = null;
		try {
			image = ImageIO.read(new File("OIP.jpg")); //gets backgroudn iamge
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		frame = new JFrame(); //creates frame and sets its settings
		frame.setSize(1000, 600);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		frame.setContentPane(new ImagePanel(image));
		
		popupMenu = new JPopupMenu("Menu"); //creates a popup menu and sets its settings
		popupMenu.setPopupSize(new Dimension(200, 400));
		popupMenu.setFont(new Font("Sakura Blossom", Font.PLAIN, 30));
		popupMenu.setBackground(new Color(255, 202, 248));
		popupMenu.setBounds(0, 0, 65, 30);
		addPopup(frame.getContentPane(), popupMenu);
		
		JButton openMenu = new JButton("Menu"); //creates a button to open menu and sets its settings
		openMenu.setForeground(Color.WHITE);
		openMenu.setFont(new Font("Sakura Blossom", Font.PLAIN, 30));
		openMenu.setBackground(new Color(236, 0, 206));
		openMenu.setBorder(new MatteBorder(3, 3, 3, 3, (Color) Color.WHITE));
		openMenu.setBounds(0, 0, 131, 51);
		frame.getContentPane().add(openMenu);
		openMenu.addActionListener(this);
		openMenu.setComponentPopupMenu(popupMenu);
		
		JMenuItem homeOption = new JMenuItem("Home"); //creates a menu item to go back to home and sets it settings
		homeOption.setForeground(Color.WHITE);
		homeOption.setBackground(new Color(236, 0, 206));
		homeOption.setBorder(new MatteBorder(3,3,3,3, (Color) Color.WHITE));
		homeOption.setHorizontalAlignment(SwingConstants.LEFT);
		homeOption.setFont(new Font("Sakura Blossom", Font.BOLD, 25));
		popupMenu.add(homeOption);
		homeOption.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) //if this button is pressed, make the frame and the popup menu invisible
            {
                frame.setVisible(false);
                popupMenu.setVisible(false);
            }
        });
		
		JMenuItem instructionMenu = new JMenuItem("Instructions"); //creates a instructions menu item  and sets its settings
		instructionMenu.setForeground(Color.WHITE);
		instructionMenu.setBackground(new Color(236, 0, 206));
		instructionMenu.setBorder(new MatteBorder(0,3,3,3, (Color) Color.WHITE));
		instructionMenu.setHorizontalAlignment(SwingConstants.LEFT);
		instructionMenu.setFont(new Font("Sakura Blossom", Font.BOLD, 25));
		popupMenu.add(instructionMenu);
		instructionMenu.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) //if it is pressed, show the instructions and make popup menu invisible
            {
            	popupMenu.setVisible(false);
                InstructionFrame instructWin = new InstructionFrame();
                instructWin.setVisible();
            }
        });
		
		JMenuItem statusMenu = new JMenuItem("Show status"); //creates a status menu item and sets its settings
		statusMenu.setForeground(Color.WHITE);
		statusMenu.setBackground(new Color(236, 0, 206));
		statusMenu.setBorder(new MatteBorder(0,3,3,3, (Color) Color.WHITE));
		statusMenu.setHorizontalAlignment(SwingConstants.LEFT);
		statusMenu.setFont(new Font("Sakura Blossom", Font.BOLD, 25));
		popupMenu.add(statusMenu);
		statusMenu.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) //if its is pressed, make the status visible and make the popup menu invisible
            {
            	popupMenu.setVisible(false);
                StatusFrame statusWin = new StatusFrame();
                statusWin.setVisible();
            }
        });
		
		JMenuItem restartMenu = new JMenuItem("Restart Game"); //creates a restart game menu item and sets its settings
		restartMenu.setForeground(Color.WHITE);
		restartMenu.setBackground(new Color(236, 0, 206));
		restartMenu.setBorder(new MatteBorder(0,3,3,3, (Color) Color.WHITE));
		restartMenu.setHorizontalAlignment(SwingConstants.LEFT);
		restartMenu.setFont(new Font("Sakura Blossom", Font.BOLD, 25));
		popupMenu.add(restartMenu);
		restartMenu.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) //if its is pressed, make the start game frame visible and make the popup menu invisible
            {
            	popupMenu.setVisible(false);
            	Main.gameFrame.setVisible();
            	timeLabel = new JLabel("00:00:00", JLabel.CENTER);
            	
            	Main.ships.clear();
            	Main.ships.add(new Ship("Carrier", 5)); //adds the different ships with unique names and sizes
        		Main.ships.add(new Ship("Battleship", 4));
        		Main.ships.add(new Ship("Cruiser", 3));
        		Main.ships.add(new Ship("Submarine", 3));
        		Main.ships.add(new Ship("Destroyer", 2));
        		
        		Main.playerShips.clear();
        		Main.playerShips.add(new Ship("Carrier", 5)); //adds the different ships with unique names and sizes
        		Main.playerShips.add(new Ship("Battleship", 4));
        		Main.playerShips.add(new Ship("Cruiser", 3));
        		Main.playerShips.add(new Ship("Submarine", 3));
        		Main.playerShips.add(new Ship("Destroyer", 2)); 
        		
        		Main.won = true;
        		changeChoice();
        		
            	StartFrame window = new StartFrame(Main.gameFrame, Main.codetimer, false);
    			window.setVisible();
            }
        });
		
		JMenuItem toggleMusic = new JMenuItem("Music");
		toggleMusic.setForeground(Color.WHITE);
		toggleMusic.setBackground(new Color(236, 0, 206));
		toggleMusic.setBorder(new MatteBorder(0,3,3,3, (Color) Color.WHITE));
		toggleMusic.setHorizontalAlignment(SwingConstants.LEFT);
		toggleMusic.setFont(new Font("Sakura Blossom", Font.BOLD, 25));
		popupMenu.add(toggleMusic);
		toggleMusic.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) //if it is pressed, make the turn music on/off invisible
            {
                popupMenu.setVisible(false);
                Main.musicState++; //increase musicState counter by 1
    			Main.PlayMusic(Main.filepath); //play music function
            }
        });
		
		JMenuItem closeMenu = new JMenuItem("Close Menu"); //creates a close menu item and sets its settings
		closeMenu.setForeground(Color.WHITE);
		closeMenu.setBackground(new Color(236, 0, 206));
		closeMenu.setBorder(new MatteBorder(0,3,3,3, (Color) Color.WHITE));
		closeMenu.setHorizontalAlignment(SwingConstants.LEFT);
		closeMenu.setFont(new Font("Sakura Blossom", Font.BOLD, 25));
		popupMenu.add(closeMenu);
		closeMenu.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) //if it is pressed, make the popup menu invisible
            {
                popupMenu.setVisible(false);
            }
        });
		
		JMenuItem exit = new JMenuItem("Exit Game"); //creates an exit menu item and sets its settings
		exit.setForeground(Color.WHITE);
		exit.setBackground(new Color(236, 0, 206));
		exit.setBorder(new MatteBorder(0,3,3,3, (Color) Color.WHITE));
		exit.setHorizontalAlignment(SwingConstants.LEFT);
		exit.setFont(new Font("Sakura Blossom", Font.BOLD, 25));
		popupMenu.add(exit);
		exit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) //if it is pressed, close the program
            {
                System.exit(0);
            }
        });
		
		JLabel titleLabel = new JLabel("Sakura Battleship"); //creates a title label and sets its settings
		titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
		titleLabel.setBackground(new Color(128, 0, 64));
		titleLabel.setOpaque(true);
		titleLabel.setForeground(Color.WHITE);
		titleLabel.setFont(new Font("Sakura", Font.BOLD, 71));
		titleLabel.setBounds(312, 24, 484, 69);
		frame.getContentPane().add(titleLabel);
		
		panel = new JPanel(); //creates a panel and sets its settings
		panel.setBorder(new MatteBorder(5, 5, 5, 5, (Color) new Color(255, 255, 255)));
		panel.setBackground(new Color(255, 202, 248));
		panel.setBounds(25, 165, 365, 365);
		frame.getContentPane().add(panel);
		panel.setLayout(new GridLayout(11, 11, 0, 0));
		
		panel_1 = new JPanel(); //creates another panel and sets its settings
		panel_1.setBorder(new MatteBorder(5, 5, 5, 5, (Color) Color.WHITE));
		panel_1.setBackground(new Color(255, 202, 248));
		panel_1.setBounds(404, 165, 365, 365);
		frame.getContentPane().add(panel_1);
		panel_1.setLayout(new GridLayout(11, 11, 0, 0));
		
		JScrollPane scrollPane = new JScrollPane(); //creates a scrollpane and sets its settings
		scrollPane.setBackground(Color.WHITE);
		scrollPane.setBorder(new MatteBorder(3, 3, 3, 3, (Color) Color.WHITE));
		scrollPane.getViewport().setBackground(new Color(255, 202, 248));
		scrollPane.getViewport().setOpaque(true);
		scrollPane.setBounds(784, 164, 168, 183);
		frame.getContentPane().add(scrollPane);
		
		JLabel legendLabel = new JLabel("Legend"); //create a legend label and sets its settings
		legendLabel.setForeground(new Color(236, 0, 206));
		legendLabel.setOpaque(true);
		legendLabel.setBackground(Color.WHITE);
		legendLabel.setHorizontalAlignment(SwingConstants.CENTER);
		legendLabel.setFont(new Font("Sakura Blossom", Font.BOLD, 20));
		scrollPane.setColumnHeaderView(legendLabel);
		
		JPanel panel_2 = new JPanel(); //create another panel
		panel_2.setBackground(new Color(255, 202, 248));
		scrollPane.setViewportView(panel_2);
		
		panel_2.add(Box.createRigidArea(new Dimension(0, 10))); //adds a box in panel 2 
		JLabel carrierLabel = new JLabel("Carrier"); //creates a carrier label
		ImageIcon icon = new ImageIcon("PinkFlower.png"); //adds the carrier symbol
		Image newimg = icon.getImage().getScaledInstance(30, 30,  java.awt.Image.SCALE_SMOOTH); // scale it the smooth way  
		icon = new ImageIcon(newimg);
		panel_2.setLayout(new BoxLayout(panel_2, BoxLayout.Y_AXIS));
		carrierLabel.setIcon(icon);
		carrierLabel.setFont(new Font("Sakura Blossom", Font.PLAIN, 20));
		panel_2.add(carrierLabel);
		panel_2.add(Box.createRigidArea(new Dimension(0, 10)));
		JLabel battleLabel = new JLabel("Battleship"); //creates a battleship label
		ImageIcon icon2 = new ImageIcon("Purple Flower.png"); //adds the battleship symbol
		newimg = icon2.getImage().getScaledInstance(30, 30,  java.awt.Image.SCALE_SMOOTH); // scale it the smooth way  
		icon2 = new ImageIcon(newimg);
		battleLabel.setIcon(icon2);
		battleLabel.setFont(new Font("Sakura Blossom", Font.PLAIN, 20));
		panel_2.add(battleLabel);
		panel_2.add(Box.createRigidArea(new Dimension(0, 10)));
		JLabel cruiserLabel = new JLabel("Cruiser"); //creates a cruiser label
		ImageIcon icon3 = new ImageIcon("Yellow Flower.png"); //adds the cruiser symbol
		newimg = icon3.getImage().getScaledInstance(30, 30,  java.awt.Image.SCALE_SMOOTH); // scale it the smooth way  
		icon3 = new ImageIcon(newimg);
		cruiserLabel.setIcon(icon3);
		cruiserLabel.setFont(new Font("Sakura Blossom", Font.PLAIN, 20));
		panel_2.add(cruiserLabel);
		panel_2.add(Box.createRigidArea(new Dimension(0, 10)));
		JLabel subLabel = new JLabel("Submarine"); //creates a submarine label
		ImageIcon icon4 = new ImageIcon("Blue Flower.png"); //adds the submarine symbol
		newimg = icon4.getImage().getScaledInstance(30, 30,  java.awt.Image.SCALE_SMOOTH); // scale it the smooth way  
		icon4 = new ImageIcon(newimg);
		subLabel.setIcon(icon4);
		subLabel.setFont(new Font("Sakura Blossom", Font.PLAIN, 20));
		panel_2.add(subLabel);
		panel_2.add(Box.createRigidArea(new Dimension(0, 10)));
		JLabel DestroyerLabel = new JLabel("Destroyer"); //creates a destroyer label
		ImageIcon icon5 = new ImageIcon("Green Flower.png"); //adds the destroyer symbol
		newimg = icon5.getImage().getScaledInstance(30, 30,  java.awt.Image.SCALE_SMOOTH); // scale it the smooth way  
		icon5 = new ImageIcon(newimg);
		DestroyerLabel.setIcon(icon5);
		DestroyerLabel.setFont(new Font("Sakura Blossom", Font.PLAIN, 20));
		panel_2.add(DestroyerLabel);
		panel_2.add(Box.createRigidArea(new Dimension(0, 10)));
		JLabel missLabel = new JLabel("Miss"); //creates a miss label
		ImageIcon icon6 = new ImageIcon("White Flower.png"); //adds the miss symbol
		newimg = icon6.getImage().getScaledInstance(30, 30,  java.awt.Image.SCALE_SMOOTH); // scale it the smooth way  
		icon6 = new ImageIcon(newimg);
		missLabel.setIcon(icon6);
		missLabel.setFont(new Font("Sakura Blossom", Font.PLAIN, 20));
		panel_2.add(missLabel);
		panel_2.add(Box.createRigidArea(new Dimension(0, 10)));
		
		JLabel ourBoardLabel = new JLabel("Our Board"); //create a label to indicate the user's board and sets its settings
		ourBoardLabel.setHorizontalAlignment(SwingConstants.CENTER);
		ourBoardLabel.setBackground(new Color(128, 0, 64));
		ourBoardLabel.setOpaque(true);
		ourBoardLabel.setForeground(Color.WHITE);
		ourBoardLabel.setFont(new Font("Sakura Blossom", Font.BOLD, 15));
		ourBoardLabel.setBounds(25, 138, 96, 21);
		frame.getContentPane().add(ourBoardLabel);
		
		JLabel guessBoardLabel = new JLabel("Guessing Board"); //create a label to indicate the computer's board and sets its settings
		guessBoardLabel.setHorizontalAlignment(SwingConstants.CENTER);
		guessBoardLabel.setBackground(new Color(128, 0, 64));
		guessBoardLabel.setOpaque(true);
		guessBoardLabel.setForeground(Color.WHITE);
		guessBoardLabel.setFont(new Font("Sakura Blossom", Font.BOLD, 15));
		guessBoardLabel.setBounds(404, 138, 110, 17);
		frame.getContentPane().add(guessBoardLabel);
		
		JButton missButton = new JButton("Miss"); //create a miss button and sets its settings
		missButton.setFont(new Font("Sakura Blossom", Font.PLAIN, 30));
		missButton.setBackground(new Color(255, 202, 248));
		missButton.setBorder(new MatteBorder(3, 3, 3, 3, (Color) Color.WHITE));
		missButton.setBounds(784, 370, 168, 51);
		frame.getContentPane().add(missButton);
		missButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e)
            {
            	if (Main.getTurn()) { //if it is the computer's turn
	            	try {
						Main.tBoard.guess(1, "M", -1); //inform AI that the shot was a miss
						Main.setTurn(false); //make it not the computer's turn
						if (!Main.won)
							Main.gameFrame.setMessage("Your Turn"); //state that it is the user's turn
					} catch (Exception e1) {
						e1.printStackTrace();
					}
            	}
            }
        });
		
		//creates a choice drop down menu that allows the user to select the ship that was hit
		choice.setBackground(new Color(255, 202, 248));
		choice.setFont(new Font("DialogInput", Font.BOLD, 25));
		choice.setBounds(784, 430, 168, 25);
		choice.add("CARRIER"); //add all the different ships
		choice.add("BATTLESHIP");
		choice.add("CRUISER");
		choice.add("SUBMARINE");
		choice.add("DESTROYER");
		frame.getContentPane().add(choice);
		
		JButton hitButton = new JButton("Hit"); //adds a hit button and sets its settings
		hitButton.setFont(new Font("Sakura Blossom", Font.PLAIN, 30));
		hitButton.setBackground(new Color(255, 202, 248));
		hitButton.setBorder(new MatteBorder(3, 3, 3, 3, (Color) Color.WHITE));
		hitButton.setBounds(784, 479, 168, 51);
		frame.getContentPane().add(hitButton);
		hitButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) //if it is pressed
            {
            	if (Main.getTurn()) { //if it is the computer's turn
	            	try {
	            		int option = choice.getSelectedIndex(); //get the ship that the user stated was hit
	            		Main.tBoard.guess(2, Main.playerShips.get(option), Main.playerShips.get(option).getSize(), Main.playerShips); //displays results of the guess
	            		Main.setTurn(false); //sets it to the user's turn
	            		if (!Main.won)
	            			Main.gameFrame.setMessage("Your Turn");
					} catch (Exception e1) {
						e1.printStackTrace();
					}
            	}
            }
        });
		
		timeLabel = new JLabel("00:00:00", JLabel.CENTER);
		timeLabel.setFont(new Font("Sakura Blossom", Font.BOLD, 20));
		timeLabel.setBackground(new Color(236, 0, 206));
		timeLabel.setOpaque(true);
		timeLabel.setForeground(Color.WHITE);
		timeLabel.setBorder(new MatteBorder(3,3,3,3,(Color) Color.WHITE));
		timeLabel.setHorizontalAlignment(SwingConstants.CENTER);
		timeLabel.setBounds(843, 0, 143, 51);
		frame.getContentPane().add(timeLabel);
		
		message = new JLabel("Your Turn"); //creates a label that says it is the user's turn; sets its settings
		message.setBackground(new Color(128, 0, 64));
		message.setOpaque(true);
		message.setFont(new Font("Sakura Blossom", Font.BOLD, 28));
		message.setForeground(Color.WHITE);
		message.setHorizontalAlignment(SwingConstants.CENTER);
		message.setBounds(28, 103, 924, 25);
		frame.getContentPane().add(message);
		
		guessStatus = new JLabel(""); //creates a label for the guess status; sets its settings
		guessStatus.setFont(new Font("Sakura Blossom", Font.BOLD, 15));
		guessStatus.setHorizontalAlignment(SwingConstants.CENTER);
		guessStatus.setForeground(Color.WHITE);
		guessStatus.setBackground(new Color(128, 0, 64));
		guessStatus.setOpaque(true);
		guessStatus.setBorder(new MatteBorder(3,3,3,3, (Color) Color.WHITE));
		guessStatus.setBounds(784, 134, 168, 25);
		frame.getContentPane().add(guessStatus);
		
	}
	public void actionPerformed(ActionEvent e) { //if menu button is pressed, make popup menu visible
		String command = e.getActionCommand();
		if (command.equals("Menu")) {
			popupMenu.setVisible(true);
		}
	}
	
	public void changeChoice() {
		frame.remove(choice);
		frame.repaint();
		frame.revalidate();
		choice = new Choice();
		choice.setBackground(new Color(255, 202, 248));
		choice.setFont(new Font("DialogInput", Font.BOLD, 25));
		choice.setBounds(784, 430, 168, 25);
		for (int i = 0; i < Main.playerShips.size(); i++) {
			choice.add(Main.playerShips.get(i).getName().toUpperCase());
		}
		frame.getContentPane().add(choice);
		frame.repaint();
		frame.revalidate();
	}
	
	/**
	 * Set guess status to the given string
	 * 
	 * @param s
	 */
	public void setGuessStatus(String s) {
		guessStatus.setText(s);
	}
	
	/**
	 * Adds the popup
	 * 
	 * @param component
	 * @param popup
	 */
	private static void addPopup(Component component, final JPopupMenu popup) {
		component.addMouseListener(new MouseAdapter() { //allows tracking of mouse movements
			public void mousePressed(MouseEvent e) {
				if (e.isPopupTrigger()) { //if mouse was pressed, show menu
					showMenu(e);
				}
			}
			public void mouseReleased(MouseEvent e) { //if mouse was released, show menu
				if (e.isPopupTrigger()) {
					showMenu(e);
				}
			}
			private void showMenu(MouseEvent e) { //make popup menu show
				popup.show(e.getComponent(), e.getX(), e.getY());
			}
		});
	}
}
