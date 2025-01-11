/* Name: Fiona Dang
 * File: GameHistoryFrame.java
 * Purpose: This GameHistoryFrame class creates the popup window that displays previous Battleship games played
 * Date: June 9, 2024
 **/

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;
import javax.swing.border.MatteBorder;
import javax.imageio.ImageIO;
import javax.swing.JButton;

public class GameHistoryFrame {

	static String gameHistoryGUI = Main.gameHistoryGUI; //game history String to be printed out is taken from Main
	
	private JFrame frame; //create a frame
	
	/**
	 * Create the application.
	 */
	public GameHistoryFrame() { //initializes the frame
		initialize();
	}
	
	/**
	 * Sets frame visible
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
			image = ImageIO.read(new File("OIP.jpg")); //retrieve image 
		} catch (IOException e) { //catch errors and print it out
			e.printStackTrace();
		}
		frame = new JFrame(); //initializes frame
		frame.setSize(1000, 600); //set size of frame
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		frame.setContentPane(new ImagePanel(image)); //sets image 
		
		JLabel titleLabel = new JLabel("Previous Games"); //creates a label for instructions
		titleLabel.setHorizontalAlignment(SwingConstants.CENTER); //horizontal
		titleLabel.setBackground(new Color(128, 0, 64)); //sets colour of background
		titleLabel.setOpaque(true); //makes opaque
		titleLabel.setForeground(Color.WHITE);
		titleLabel.setFont(new Font("Sakura", Font.BOLD, 71)); //sets font
		titleLabel.setBounds(111, 25, 756, 69);
		frame.getContentPane().add(titleLabel); //add title label to frame
		
		
		JLabel previousGamesLabel = new JLabel("<html>"+gameHistoryGUI+"</html>"); //create a label with instructions
		previousGamesLabel.setFont(new Font("Monospaced",Font.BOLD,12)); //sets font of instructions label
		previousGamesLabel.setBackground(new Color(255, 202, 248));
		previousGamesLabel.setOpaque(true);
		previousGamesLabel.setBorder(new MatteBorder(5,5,5,5,(Color) Color.WHITE));
		previousGamesLabel.setBounds(82, 110, 840, 375);
		
		JScrollPane scrollPane = new JScrollPane(); //creates a scroll pane to place the label in
		scrollPane.setBackground(Color.WHITE); //sets settings of scroll pane
		scrollPane.setBorder(new MatteBorder(3, 3, 3, 3, (Color) Color.WHITE));
		scrollPane.getViewport().setBackground(new Color(255, 202, 248));
		scrollPane.getViewport().setOpaque(true);
		scrollPane.setBounds(225, 164, 500, 400);
		
		
		frame.getContentPane().add(scrollPane); //add scrollpane to frame
		
		scrollPane.setViewportView(previousGamesLabel); //puts the previous games label in the scroll pane
		
		frame.setVisible(true);
		
		JButton backButton = new JButton("Back"); //create a back button
		backButton.setFont(new Font("Sakura Blossom", Font.PLAIN, 25)); //set fonts and settings for back button
		backButton.setBorder(new MatteBorder(3,3,3,3,(Color) Color.WHITE));
		backButton.setBackground(new Color(255, 202, 248));
		backButton.setOpaque(true);
		backButton.setBounds(800, 500, 125, 44);
		frame.getContentPane().add(backButton); //add back button to frame
		backButton.addActionListener(new ActionListener() { //add action listener to back button
            public void actionPerformed(ActionEvent e)
            {
                frame.setVisible(false); //makes frame not visible if back button is pressed
            }
        });
	}
}
