/* Name: Fiona Dang
 * File: StatusFrame.java
 * Purpose: This StatusFrame class creates the popup window that displays the status
 * Date: June 9, 2024
 **/

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.Color;
import javax.swing.SwingConstants;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.swing.border.MatteBorder;
import javax.imageio.ImageIO;
import javax.swing.JButton;

public class StatusFrame {

	private JFrame frame;

	/**
	 * Create the application.
	 */
	public StatusFrame() {
		initialize();
	}
	
	/**
	 * Sets frame to be visible
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
		frame.setBounds(100, 100, 1000, 600);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		frame.setContentPane(new ImagePanel(image));
		
		JLabel titleLabel = new JLabel("Sakura Battleship Status"); //creates a title label and sets its settings
		titleLabel.setFont(new Font("Sakura", Font.BOLD, 71));
		titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
		titleLabel.setForeground(Color.WHITE);
		titleLabel.setBackground(new Color(128, 0, 64));
		titleLabel.setOpaque(true);
		titleLabel.setBounds(151, 33, 659, 63);
		frame.getContentPane().add(titleLabel);
		
		JPanel panel = new JPanel(); //creates a panel for the user's status and sets its settings
		panel.setBackground(new Color(255, 202, 248));
		panel.setBorder(new MatteBorder(5, 5, 5, 5, (Color) Color.WHITE));
		panel.setBounds(50, 125, 390, 298);
		frame.getContentPane().add(panel);
		panel.setLayout(null);
		
		JLabel subtitleLabel = new JLabel("Player Status"); //creates a subtitle label and sets its settings
		subtitleLabel.setHorizontalAlignment(SwingConstants.CENTER);
		subtitleLabel.setFont(new Font("Sakura Blossom", Font.BOLD, 30));
		subtitleLabel.setBounds(99, 21, 194, 54);
		panel.add(subtitleLabel);
		
		String text = " Number of Guesses: "+Main.cBoard.getNumOfGuesses()+"<br>"+ //displays number of guesses, hits, misses, and ships remaining in playerStatusLabel
						" Number of hits: "+Main.cBoard.getNumHits()+"<br>"+
						" Number of misses: "+Main.cBoard.getMisses()+"<br>"+
						" Number of ships remaining: "+Main.tBoard.getNumShipsAlive();
		JLabel playerStatusLabel = new JLabel("<html>"+text+"</html>");
		playerStatusLabel.setFont(new Font("Sakura Blossom", Font.PLAIN, 25)); //settings for label
		playerStatusLabel.setHorizontalAlignment(SwingConstants.LEFT);
		playerStatusLabel.setBounds(40, 64, 305, 195);
		panel.add(playerStatusLabel);
		
		JPanel panel_1 = new JPanel(); //creates another panel for the computer's status and sets its settings
		panel_1.setBorder(new MatteBorder(5, 5, 5, 5, (Color) Color.WHITE));
		panel_1.setBackground(new Color(255, 202, 248));
		panel_1.setBounds(531, 125, 390, 298);
		frame.getContentPane().add(panel_1);
		panel_1.setLayout(null);
		
		JLabel subtitle2Label = new JLabel("Computer Status"); //creates a subttile label and sets its settings
		subtitle2Label.setFont(new Font("Sakura Blossom", Font.BOLD, 30));
		subtitle2Label.setHorizontalAlignment(SwingConstants.CENTER);
		subtitle2Label.setBounds(111, 22, 182, 52);
		panel_1.add(subtitle2Label);
		
		text = " Number of Guesses: "+Main.tBoard.getNumOfGuesses()+"<br>"+ //displays number of guesses, hits, misses, and ships remaining in computerStatus Label
				" Number of hits: "+Main.tBoard.getNumHits()+"<br>"+
				" Number of misses: "+Main.tBoard.getMisses()+"<br>"+
				" Number of ships remaining: "+Main.cBoard.getNumShipsAlive();
		JLabel computerStatusLabel = new JLabel("<html>"+text+"</html>");
		computerStatusLabel.setFont(new Font("Sakura Blossom", Font.PLAIN, 25)); //settings for label
		computerStatusLabel.setHorizontalAlignment(SwingConstants.LEFT);
		computerStatusLabel.setBounds(44, 62, 301, 199);
		panel_1.add(computerStatusLabel);
		
		JButton backButton = new JButton("Back"); //creates a back button and sets its settings
		backButton.setForeground(Color.WHITE);
		backButton.setFont(new Font("Sakura Blossom", Font.BOLD, 30));
		backButton.setBackground(new Color(236, 0, 206));
		backButton.setOpaque(true);
		backButton.setBounds(345, 463, 317, 54);
		backButton.setBorder(new MatteBorder(3, 3, 3, 3, (Color) Color.WHITE));
		frame.getContentPane().add(backButton);
		backButton.addActionListener(new ActionListener() { //if back button is pressed, make frame not visible
            public void actionPerformed(ActionEvent e)
            {
                frame.setVisible(false);
            }
        });
		
	}

}
