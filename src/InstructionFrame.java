/* Name: Fiona Dang
 * File: InstructionFrame.java
 * Purpose: This InstructionFrame class creates the popup window that displays the instructions
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
import javax.swing.SwingConstants;
import javax.swing.border.MatteBorder;
import javax.imageio.ImageIO;
import javax.swing.JButton;

public class InstructionFrame {

	private JFrame frame;
	/**
	 * Create the application.
	 */
	public InstructionFrame() {
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
			image = ImageIO.read(new File("OIP.jpg"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		frame = new JFrame(); //sets settings of the frame
		frame.setSize(1000, 600);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		frame.setContentPane(new ImagePanel(image));
		
		JLabel titleLabel = new JLabel("Sakura Battleship Instructions"); //creates title label and sets its settings
		titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
		titleLabel.setBackground(new Color(128, 0, 64));
		titleLabel.setOpaque(true);
		titleLabel.setForeground(Color.WHITE);
		titleLabel.setFont(new Font("Sakura", Font.BOLD, 71));
		titleLabel.setBounds(111, 25, 756, 69);
		frame.getContentPane().add(titleLabel);
		
		String text = " 1) Choose the level of difficulty you wish to play against and click the �Start� button in the main menu.<br>"
				+ " 2) You will place your ships horizontally or vertically on your paper grid. Your ships must not overlap. You have a carrier (5 spots), a battleship (4 spots), a cruiser (3 spots), a submarine (3 spots), and a destroyer (2 spots). The AI has the same ships and will do the same. Neither you nor the AI will reveal their ship�s locations at any point during the game.<br>"
				+ " 3) You and the AI will take turns shooting at each other�s ships. You will shoot by clicking on the Guessing Board. You will then be informed of the results (miss, hit, sunk) of your shot.<br>"
				+ " 4) The AI will shoot and you will click the appropriate buttons in the bottom right to inform the AI of the results of its shots. If the AI hit your ship, please select the appropriate ship in the dropdown menu.<br>"
				+ " 5) All shots will be recorded down on the computer screen.<br>"
				+ " 6) These steps will be repeated until one side has sunk all of their opponent�s ships. The side that sinks all their opponent�s ships first will be the winner.";
		JLabel instructionsLabel = new JLabel("<html>"+text+"</html>"); //creates instructions label and sets its settings
		instructionsLabel.setFont(new Font("Sakura Blossom", Font.PLAIN, 20));
		instructionsLabel.setBackground(new Color(255, 202, 248));
		instructionsLabel.setOpaque(true);
		instructionsLabel.setBorder(new MatteBorder(5,5,5,5,(Color) Color.WHITE));
		instructionsLabel.setBounds(82, 110, 840, 375);
		frame.getContentPane().add(instructionsLabel);
		
		JButton backButton = new JButton("Back"); //creates a back button and sets its settings
		backButton.setFont(new Font("Sakura Blossom", Font.PLAIN, 25));
		backButton.setBorder(new MatteBorder(3,3,3,3,(Color) Color.WHITE));
		backButton.setBackground(new Color(255, 202, 248));
		backButton.setOpaque(true);
		backButton.setBounds(450, 500, 125, 44);
		frame.getContentPane().add(backButton);
		backButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e)
            {
                frame.setVisible(false); //if back button is pressed, make frame not visible
            }
        });
	}

}
