/* Name: Fiona Dang
 * File: StartFrame.java
 * Purpose: This StartFrame class creates the popup window that displays the start screen
 * Date: June 9, 2024
 **/

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.border.MatteBorder;
import javax.swing.JComboBox;
import javax.swing.JButton;

public class StartFrame {

	private JFrame frame;
	private GameFrame game;
	private CodeTimer ct;
	private boolean c;
	/**
	 * Create the application.
	 */
	public StartFrame(GameFrame gFrame, CodeTimer codetimer, boolean check) {
		game = gFrame;
		ct = codetimer;
		c = check;
		initialize();
	}
	
	/**
	 * Makes frame visible
	 */
	public void setVisible() {
		frame.setVisible(true);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		BufferedImage image = null;
		try {
			image = ImageIO.read(new File("OIP.jpg")); //gets background image
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		frame = new JFrame(); //creates frame and sets its settings
		frame.setSize(1000, 600);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		frame.setContentPane(new ImagePanel(image));
		
		JComboBox<String> orderBox = new JComboBox<>(); //creates a combo box to determine who goes first and sets its settings
		orderBox.setBackground(new Color(255, 202, 248));
		orderBox.setOpaque(true);
		orderBox.setFont(new Font("Sakura Blossom", Font.PLAIN, 20));
		orderBox.setBounds(638, 177, 274, 64);
		orderBox.addItem(" User");
		orderBox.addItem(" Computer");
		orderBox.addItem(" Random");
		frame.getContentPane().add(orderBox);
		
		JComboBox<String> modeBox = new JComboBox<String>(); //creates a combo box to determine the level of difficulty and sets its settings
		modeBox.setOpaque(true);
		modeBox.setBackground(new Color(255, 202, 248));
		modeBox.setFont(new Font("Sakura Blossom", Font.PLAIN, 20));
		modeBox.setBounds(638, 333, 274, 64);
		modeBox.addItem(" Easy");
		modeBox.addItem(" Hard");
		modeBox.addItem(" Legendary");
		frame.getContentPane().add(modeBox);
		
		JButton startGameButton = new JButton("Start Game"); //creates a button to start the game and sets its settings
		startGameButton.setBackground(new Color(255, 202, 248));
		startGameButton.setOpaque(true);
		startGameButton.setFont(new Font("Sakura Blossom", Font.PLAIN, 30));
		startGameButton.setBorder(new MatteBorder(3,3,3,3,(Color) Color.WHITE));
		startGameButton.setBounds(239, 475, 495, 64);
		frame.getContentPane().add(startGameButton);
		
		JLabel playerPrompt = new JLabel("Who goes first?"); //creates a label to prompt user to choose who goes first
		playerPrompt.setHorizontalAlignment(SwingConstants.CENTER); //label settings
		playerPrompt.setBackground(new Color(255, 202, 248));
		playerPrompt.setOpaque(true);
		playerPrompt.setFont(new Font("Sakura Blossom", Font.PLAIN, 30));
		playerPrompt.setBounds(73, 177, 495, 64);
		frame.getContentPane().add(playerPrompt);
		
		JLabel modePrompt = new JLabel("Select Mode");  //creates a label to prompt user to choose level of difficulty
		modePrompt.setHorizontalAlignment(SwingConstants.CENTER); //label settings
		modePrompt.setBackground(new Color(255, 202, 248));
		modePrompt.setOpaque(true);
		modePrompt.setFont(new Font("Sakura Blossom", Font.PLAIN, 30));
		modePrompt.setBounds(73, 333, 495, 64);
		frame.getContentPane().add(modePrompt);
		
		JLabel titleLabel = new JLabel("Sakura Battleship");  //creates a title label and sets its settings
		titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
		titleLabel.setBackground(new Color(128, 0, 64));
		titleLabel.setOpaque(true);
		titleLabel.setForeground(Color.WHITE);
		titleLabel.setFont(new Font("Sakura", Font.BOLD, 71));
		titleLabel.setBounds(312, 24, 484, 69);
		frame.getContentPane().add(titleLabel);
		
		startGameButton.addActionListener(new ActionListener() { //start button can perform actions
            public void actionPerformed(ActionEvent e)
            {
            	Main.won = false;
            	Main.level = modeBox.getSelectedIndex()+1; //sets level of difficulty in Main
            	Main.order = orderBox.getSelectedIndex()+1; //sets the order of moves in Main
                frame.setVisible(false); //makes frame invisible
                ct = new CodeTimer();
                if (c)
                	ct.start(true);
                else
                	ct.start();
				game.setVisible(); //makes game visible
				try {
					Main.game();
				} catch (IOException e1) {
					e1.printStackTrace();
				} catch (Exception e1) {
					e1.printStackTrace();
				}
            }
        });
	}
}
