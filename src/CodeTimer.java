/* Name: Fiona Dang
 * File: CodeTimer.java
 * Purpose: Timer for game
 * Date: June 2, 2024
 **/

import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JLabel;

public class CodeTimer {
	
	public static int secondsPassed = 0; //the number of seconds that have passed during the game
	public boolean stopTimer = false; //boolean to stop the timer once the game has been finished
	
	public static JLabel timeLabel = new JLabel("00:00:00", JLabel.CENTER); //label for game timer
	
	Timer timer = new Timer(); //creates timer object
	TimerTask task;
	
	/**
	 * Starts timer and increases timer after 1 second delay
	 */
	public void start() {
		timer.scheduleAtFixedRate(task, 1000, 1000);
	}
	
	public void start(boolean bool) {
		stopTimer = false;
		timer.cancel();
		timer = new Timer();
		timer.scheduleAtFixedRate(task, 1000, 1000);
	}
	
	public CodeTimer() {
		task = new TimerTask() {
			public void run() {
				if (stopTimer == false) { //if stopTimer is false (game has not finished)
					secondsPassed++; //increased seconds passed by 1
					stopTimer = Main.updateTimeLabel(secondsPassed); //update timer and check if game has ended
				}
				else {
					secondsPassed = 0;
					stopTimer = Main.updateTimeLabel(secondsPassed);
				}
			}
		};
	}
}

