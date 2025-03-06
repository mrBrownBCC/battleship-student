package bcc.battleship;
import bcc.battleship.Constants;

public class Player {
  
    // Constructor: Initialize the grids and the ships.
    public Player() {

    }
    
    /**
     * This method is used for testing to set a ship's location.
     * It sets the ship's row, column, and direction, then adds it to the player's grid.
     *
     */
    
    public boolean chooseShipLocation(int index, int row, int col, int direction) {
        
        return false;
    }
   
    /**
     * Record a guess from the opponent.
     * This method checks the player's grid at (row, col). If there is a ship,
     * it marks a hit and returns true; otherwise, it marks a miss and returns false.
     *
     */
    public boolean recordOpponentGuess(int row, int col) {
        return false;
    }
    
    
    public Grid getMyGrid() {
        return null;
    }
    
    public Grid getOpponentGrid() {
        return null;
    }
}
