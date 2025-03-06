package bcc.battleship;

public class Grid {
    private Location[][] grid;
    
    // Constants for grid dimensions.
    
    
    // Create a new Grid and initialize each Location.
    public Grid() {
        grid = new Location[Constants.NUM_ROWS][Constants.NUM_COLS];
        for (int row = 0; row < Constants.NUM_ROWS; row++) {
            for (int col = 0; col < Constants.NUM_COLS; col++) {
                grid[row][col] = new Location();
            }
        }
    }
    
    // Mark a hit in the specified location.
    public void markHit(int row, int col) {
        grid[row][col].markHit();
    }
    
    // Mark a miss in the specified location.
    public void markMiss(int row, int col) {
        grid[row][col].markMiss();
    }
    
    // Set the status of the Location at (row, col).
    public void setStatus(int row, int col, int status) {
        grid[row][col].setStatus(status);
    }
    
    // Get the status of the Location at (row, col).
    public int getStatus(int row, int col) {
        return grid[row][col].getStatus();
    }
    
    // Return whether this Location has already been guessed.
    public boolean alreadyGuessed(int row, int col) {
        return !grid[row][col].isUnguessed();
    }
    
    // Set whether there is a ship at this location.
    public void setShip(int row, int col, boolean val) {
        grid[row][col].setShip(val);
    }
    
    // Return whether there is a ship at this location.
    public boolean hasShip(int row, int col) {
        return grid[row][col].hasShip();
    }
    
    // Get the Location object at this row and column.
    public Location get(int row, int col) {
        return grid[row][col];
    }
    
    // Return the number of rows.
    public int numRows() {
        return Constants.NUM_ROWS;
    }
    
    // Return the number of columns.
    public int numCols() {
        return Constants.NUM_COLS;
    }
    

    //maybe convert to boolean?
    public boolean addShip(Ship s) {
        int startRow = s.getRow();
        int startCol = s.getCol();
        int length = s.getLength();
        int direction = s.getDirection();
        if (startRow == Constants.UNSET || startCol == Constants.UNSET) {
            return false;
        }
        
        // Place ship based on its direction.
        if (direction == Constants.HORIZONTAL) {
            for (int i = 0; i < length; i++) {
                if (startCol + i >= Constants.NUM_COLS) {
                   return false;
                }
                if (grid[startRow][startCol + i].hasShip()){
                    return false;
                }
                grid[startRow][startCol + i].setShip(true);
            }
        } else if (direction == Constants.VERTICAL) {
            for (int i = 0; i < length; i++) {
                if (startRow + i >= Constants.NUM_ROWS) {
                    return false;
                }
                if (grid[startRow + i][startCol].hasShip()){
                    return false;
                }
                grid[startRow + i][startCol].setShip(true);
            }
        } else {
            return false;
        }

        return true;
    }

    public boolean allShipsSank(){
        for (int i = 0; i < Constants.NUM_ROWS; i++) {
            for (int j = 0; j < Constants.NUM_COLS; j++) {
                if (hasShip(i, j) && getStatus(i, j) != Constants.HIT) {
                    return false;
                }
            }
        }
        return true;
    }
}
