/* Name: Fiona Dang
 * File: Ship.java
 * Purpose: Act as a template for all ships in Battleship game
 * Date: June 2, 2024
 **/

public class Ship {

	//Instance variables for Ship objects
	private String name;
	private int size; 
	private boolean sunk;  
	private String[] letterPos; 
	private int[] numPos;  
	
	//Constructor method
	public Ship(String name1, int size1) {
		name = name1; //assigns name of ship to given name
		size = size1; //assigns size of ship to given size
		sunk = false; //ship is initially not sunk
		letterPos = new String[size]; //prepares letter position array by assigning it the appropriate number of spots
		numPos = new int[size]; //prepares number position array by assigning it the appropriate number of spots
	}
	
	/**
	 * Returns name of ship - Accessor method (getter)
	 * 
	 * @return name
	 */

	public String getName() {
		return name;
	}
	
	/**
	 * Returns size of ship - Accessor method (getter)
	 * 
	 * @return size
	 */

	public int getSize() {
		return size;
	}
	
	/**
	 * Returns whether or not ship has been sunk - Accessor method (getter)
	 * 
	 * @return sunk
	 */

	public boolean getSunk() {
		return sunk;
	}
	
	/**
	 * Unsinks ship - Modifier method (setter)
	 */

	public void setSunk() {
		if (!sunk) //if ship is not sunk
			sunk = true; //sink ship
	}
	
	/**
	 * Returns a String with all spots occupied by a ship  - Modifier method (setter)
	 * 
	 * @return temp
	 */
	public String getPos() {
		String temp = ""; //declare empty String
		for (int i = 0; i < size; i++) {
			temp += letterPos[i] + numPos[i] + " "; //add each occupied spot in order and separates it with a space
		}
		return temp;
	}
	
	/**
	 * Sets the position of ship (all occupied spots) - Modifier method (setter)
	 * 
	 * @param nPos
	 * @param lPos
	 * @param direction
	 */

	public void setPos(int nPos, char lPos, String direction) {
		if (direction.equals("UP")) { //if ship position goes up
			for (int i = 0; i < size; i++) { //set positions until ship has gone through its whole length
				letterPos[i] = String.valueOf(lPos); //set letterPos
				numPos[i] = nPos; //set numPos
				lPos--; //goes up vertically
			}
		}
		else if (direction.equals("DOWN")) { //if ship position goes down
			for (int i = 0; i < size; i++) { //set positions until ship has gone through its whole length
				letterPos[i] = String.valueOf(lPos); //set letterPos
				numPos[i] = nPos; //set numPos
				lPos++; //goes down vertically
			}
		}
		else if (direction.equals("LEFT")) { //if ship position goes left
			for (int i = 0; i < size; i++) { //set positions until ship has gone through its whole length
				letterPos[i] = String.valueOf(lPos); //set letterPos
				numPos[i] = nPos; //set numPos
				nPos--; //goes left
			}
		}
		else { //if ship position goes right
			for (int i = 0; i < size; i++) { //set positions until ship has gone through its whole length
				letterPos[i] = String.valueOf(lPos); //set letterPos
				numPos[i] = nPos; //set numPos
				nPos++; //goes right
			}
		}
	}
	
	/**
	 * Informs user of whether or not a shot hit the ship
	 * 
	 * @param pos
	 * @return whether or not ship was it
	 */
	public boolean hit(String pos) {
		String positions[] = getPos().split(" "); //puts each occupied spot into an index of positions array
		for (int i = 0; i < positions.length; i++) { //goes through all elements of position array
			if (pos.equals(positions[i])) { //if position that was shot at is one of the ship's occupied spots
				letterPos[i] = "0"; //set that position to 0 to indicate it was hit
				numPos[i] = 0;
				checkSink(); //check if ship was sunk
				return true; //return that ship was hit
			}
		}
		return false; //if ship was not hit, return that it was not hit
	}
	
	/**
	 * Checks if boat is sunk
	 */
	public void checkSink() {
		String positions[] = getPos().split(" "); 
		for (int i = 0; i < positions.length; i++) {//checks if there are unhit spots on the ship
			if (!positions[i].equalsIgnoreCase("00")) {//if there is an unhit spot on the ship
				return; //continue
			}
		}
		setSunk(); //otherwise, set the ship as sunk
	}
}
