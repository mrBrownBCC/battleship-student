package bcc.battleship;

public class Grid {
    
    
    
    // Create a new Grid and initialize each Location.
    public Grid() {
        
    }
    
    // Mark a hit in the specified location.
    public void markHit(int row, int col) {
       
    }
    
    // Mark a miss in the specified location.
    public void markMiss(int row, int col) {

    }
    
    // Set the status of the Location at (row, col).
    public void setStatus(int row, int col, int status) {

    }
    
    // Get the status of the Location at (row, col).
    public int getStatus(int row, int col) {
        return 0;
    }
    
    // Return whether this Location has already been guessed.
    public boolean alreadyGuessed(int row, int col) {
        return false;
    }
    
    // Set whether there is a ship at this location.
    public void setShip(int row, int col, boolean val) {

    }
    
    // Return whether there is a ship at this location.
    public boolean hasShip(int row, int col) {
        return false;
    }
    
    // Get the Location object at this row and column.
    public Location get(int row, int col) {
        return null;
    }
    
    // Return the number of rows.
    public int numRows() {
        return 0;
    }
    
    // Return the number of columns.
    public int numCols() {
        return 0;
    }
    

    //maybe convert to boolean?
    public boolean addShip(Ship s) {
        return false;
    }

    public boolean allShipsSank(){
        return false;
    }
}
