# Sakura Battleship

**Author:** Fiona Dang  
**Date:** June 2024  
**Language:** Java (Swing)

## Overview
Sakura Battleship is a Java-based GUI implementation of the classic naval combat board game. It features a unique "Sakura" (Cherry Blossom) aesthetic, background music, and a smart AI opponent with multiple difficulty levels. The game allows a single player to play against the computer, tracking hits, misses, and game history.

## Object-Oriented Design
This project utilizes core Object-Oriented Programming principles to maintain a modular and scalable codebase:

* **Abstraction:**
    * The `Board` class acts as an abstract template, defining the essential structure for any game board (e.g., `board` grid, `numShipsLeft`, `hits`, `misses`) without implementing specific logic.
    * It declares abstract methods such as `markPosition()`, `printBoard()`, and `endgame()`, forcing subclasses to provide their own specific implementations.

* **Inheritance:**
    * The `ComputerBoard` and `TrackBoard` classes inherit from the abstract `Board` class.
    * This allows both boards to share common attributes (like the 2D string array for the grid) while separating the logic for the AI's internal board (`ComputerBoard`) from the player's guessing board (`TrackBoard`).

* **Encapsulation:**
    * The `Ship` class encapsulates data related to specific ships, including `name`, `size`, and `sunk` status, using private instance variables.
    * Access to these variables is controlled through public getter and setter methods like `getName()`, `getSize()`, and `hit()`, ensuring the internal state of a ship is managed securely.

* **Polymorphism:**
    * Polymorphism is demonstrated through the `printBoard()` method. While defined abstractly in `Board`, it is implemented differently in the subclasses: `ComputerBoard` handles the display of the AI's ships and game-over reveal, while `TrackBoard` handles the player's view of hits and misses.

* **Composition:**
    * The `ComputerBoard` class uses composition by maintaining an `ArrayList<Ship>`. This "has-a" relationship allows the board to manage multiple ship objects as part of its state.

## Features
* **Three Difficulty Levels:**
    * **Easy:** The AI makes random guesses within the grid.
    * **Hard:** The AI uses strategic guessing algorithms, targeting adjacent squares (up, down, left, right) after a hit.
    * **Legendary:** The AI utilizes a probability heat map to determine the most likely ship positions based on remaining board space.
* **Interactive GUI:** Built using Java Swing, the interface features custom flower-themed icons for ships (Carrier, Battleship, Cruiser, Submarine, Destroyer) and a pink/purple color palette.
* **Game History:** Tracks and logs move history and previous game outcomes in `gamehistory.txt` and `gamehistorydisplay.txt`.
* **Status Tracking:** A real-time status dashboard shows the number of guesses, hits, misses, and ships remaining for both the player and the computer.
* **Audio:** Includes background music (`audio.wav`) and sound effects, which can be toggled via the menu.
* **Timer:** Tracks the duration of the gameplay using a dedicated `CodeTimer` class.

## Getting Started

### Prerequisites
* Java Development Kit (JDK) installed.
* Java Runtime Environment (JRE).

### Installation & Execution
1.  **Compile the Source Code:**
    Navigate to the `src` directory and compile the java files.
    ```bash
    javac *.java
    ```

2.  **Run the Game:**
    Run the `Main` class to start the application.
    ```bash
    java Main
    ```

*Note: Ensure that the required asset files (images like `OIP.jpg`, `PinkFlower.png`, etc., and audio `audio.wav`) are in the correct directory relative to the compiled classes as referenced in the code.*

## How to Play
1.  **Main Menu:** Select your difficulty level (Easy, Hard, Legendary) and who goes first (User, Computer, or Random Coin Toss) from the dropdowns, then click **START**.
2.  **Setup:** The game automatically generates ship positions for both the player and the AI based on the chosen difficulty logic.
3.  **Gameplay:**
    * **Attacking:** Click on a coordinate on the **Guessing Board** to fire at the enemy. You will be informed if it is a "Hit" or "Miss".
    * **Defending:** When the AI fires, its move is processed automatically. You must use the dropdown menu and buttons on the right to confirm if the AI hit specific ships (Carrier, Battleship, etc.) or missed, updating the game state.
4.  **Winning:** The first side to sink all 5 of the opponent's ships wins the game.

## Project Structure
* **src/**
    * `Main.java`: The entry point of the application; handles the main game loop and menu.
    * `Board.java`: Abstract class defining the template for game boards.
    * `ComputerBoard.java`: Handles the AI's board state and logic.
    * `TrackBoard.java`: Handles the user's tracking board and AI guessing algorithms (Easy/Hard/Legendary logic).
    * `Ship.java`: Defines ship properties (size, name, sunk status).
    * `GameFrame.java`: The main game window GUI.
    * `StartFrame.java`, `InstructionFrame.java`, `StatusFrame.java`, `GameHistoryFrame.java`: Helper GUI classes for different screens.
    * `CodeTimer.java`: Handles the game timer.
    * `ImagePanel.java`: Utility for rendering background images.
* **Logs**
    * `gamehistory.txt`: Detailed log of moves.
    * `gamehistorydisplay.txt`: Visual text representation of the board states.
