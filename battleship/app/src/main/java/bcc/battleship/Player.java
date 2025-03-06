package bcc.battleship;
import bcc.battleship.Constants;

public class Player {
    // These are the lengths of all of the ships.
    
    
    private Ship[] ships;         // Array to hold the 5 ships.
    private Grid myGrid;          // Grid for player's own ships and opponent guesses.
    private Grid opponentGrid;    // Grid for recording player's guesses.
    
    // Constructor: Initialize the grids and the ships.
    public Player() {
        myGrid = new Grid();
        opponentGrid = new Grid();
        
        // Initialize ships with the specified lengths.
        ships = new Ship[Constants.SHIP_LENGTHS.length];
        for (int i = 0; i < Constants.SHIP_LENGTHS.length; i++) {
            ships[i] = new Ship(Constants.SHIP_LENGTHS[i]);
        }
    }
    
    /**
     * This method is used for testing to set a ship's location.
     * It sets the ship's row, column, and direction, then adds it to the player's grid.
     *
     */
    
    public boolean chooseShipLocation(int index, int row, int col, int direction) {
        ships[index].setLocation(row, col);
        ships[index].setDirection(direction);
        return myGrid.addShip(ships[index]);
    }
   
    /**
     * Record a guess from the opponent.
     * This method checks the player's grid at (row, col). If there is a ship,
     * it marks a hit and returns true; otherwise, it marks a miss and returns false.
     *
     */
    public boolean recordOpponentGuess(int row, int col) {
        if (myGrid.hasShip(row, col)) {
            myGrid.markHit(row, col);
            return true;
        } else {
            myGrid.markMiss(row, col);
            return false;
        }
    }
    
    
    public Grid getMyGrid() {
        return myGrid;
    }
    
    public Grid getOpponentGrid() {
        return opponentGrid;
    }
}
