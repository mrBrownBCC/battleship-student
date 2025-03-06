package bcc.battleship;

import org.junit.Test;
import static org.junit.Assert.*;

public class GridTest {

    // Test 1: Verify that a new Grid initializes every cell correctly.
    @Test
    public void testGridInitialization() {
        Grid grid = new Grid();
        // Check a few representative cells.
        assertEquals("Cell (0,0) should be unguessed initially.", Constants.UNGUESSED, grid.getStatus(0, 0));
        assertFalse("Cell (0,0) should not have a ship at the start.", grid.hasShip(0, 0));
        
        int midRow = Constants.NUM_ROWS / 2;
        int midCol = Constants.NUM_COLS / 2;
        assertEquals("The middle cell should be unguessed when the game starts.", Constants.UNGUESSED, grid.getStatus(midRow, midCol));
        assertFalse("The middle cell should not have a ship initially.", grid.hasShip(midRow, midCol));
        
        assertEquals("The last cell should be unguessed when the grid is created.", Constants.UNGUESSED, grid.getStatus(Constants.NUM_ROWS - 1, Constants.NUM_COLS - 1));
        assertFalse("The last cell should not have a ship when the grid is created.", grid.hasShip(Constants.NUM_ROWS - 1, Constants.NUM_COLS - 1));
    }
    
    // Test 2: Mark a cell as hit and verify its status.
    @Test
    public void testMarkHit() {
        Grid grid = new Grid();
        grid.markHit(2, 3);
        assertEquals("After marking a hit, cell (2,3) should have status HIT.", Constants.HIT, grid.getStatus(2, 3));
        assertTrue("Cell (2,3) should be considered guessed after a hit.", grid.alreadyGuessed(2, 3));
    }
    
    // Test 3: Mark a cell as miss and verify its status.
    @Test
    public void testMarkMiss() {
        Grid grid = new Grid();
        grid.markMiss(4, 5);
        assertEquals("After marking a miss, cell (4,5) should have status MISSED.", Constants.MISSED, grid.getStatus(4, 5));
        assertTrue("Cell (4,5) should be considered guessed after a miss.", grid.alreadyGuessed(4, 5));
    }
    
    // Test 4: Set and then get the status, and check the alreadyGuessed behavior.
    @Test
    public void testSetStatusAndAlreadyGuessed() {
        Grid grid = new Grid();
        grid.setStatus(1, 1, Constants.HIT);
        assertEquals("Cell (1,1) status should be updated to HIT.", Constants.HIT, grid.getStatus(1, 1));
        assertTrue("Cell (1,1) should be considered guessed when its status is HIT.", grid.alreadyGuessed(1, 1));
        
        grid.setStatus(1, 1, Constants.UNGUESSED);
        assertEquals("After resetting, cell (1,1) status should be UNGUESSED.", Constants.UNGUESSED, grid.getStatus(1, 1));
        assertFalse("Cell (1,1) should not be considered guessed when its status is UNGUESSED.", grid.alreadyGuessed(1, 1));
    }
    
    // Test 5: Set a ship at a specific cell and verify that it is marked correctly.
    @Test
    public void testSetAndHasShip() {
        Grid grid = new Grid();
        grid.setShip(3, 3, true);
        assertTrue("After setting a ship, cell (3,3) should report that it has a ship.", grid.hasShip(3, 3));
        // Also check that get returns a valid Location object.
        Location loc = grid.get(3, 3);
        assertNotNull("get(3,3) should return a valid Location object.", loc);
    }
    
    // Test 6: Verify that the grid returns the correct number of rows and columns.
    @Test
    public void testNumRowsAndNumCols() {
        Grid grid = new Grid();
        assertEquals("The number of rows should be equal to NUM_ROWS.", Constants.NUM_ROWS, grid.numRows());
        assertEquals("The number of columns should be equal to NUM_COLS.", Constants.NUM_COLS, grid.numCols());
    }
    
    // Test 7: Add a ship with valid placement and verify it is added.
    @Test
    public void testAddShipSuccess() {
        Grid grid = new Grid();
        // Create a ship of length 3 to be placed horizontally starting at (0,0)
        Ship ship = new Ship(3);
        ship.setLocation(0, 0);
        ship.setDirection(Constants.HORIZONTAL);
        boolean added = grid.addShip(ship);
        assertTrue("Ship should be added successfully when placement is valid.", added);
        
        // Check that the cells for the ship have been marked with a ship.
        for (int i = 0; i < 3; i++) {
            assertTrue("Cell (0," + (0 + i) + ") should have a ship after placement.", grid.hasShip(0, 0 + i));
        }
    }
    
    // Test 8: Attempt to add a ship that goes out of bounds and expect failure.
    @Test
    public void testAddShipOutOfBoundsFailure() {
        Grid grid = new Grid();
        // Create a ship of length 4 placed horizontally near the right edge.
        Ship ship = new Ship(4);
        ship.setLocation(0, Constants.NUM_COLS - 2);  // This should go out of bounds.
        ship.setDirection(Constants.HORIZONTAL);
        boolean added = grid.addShip(ship);
        assertFalse("Ship should not be added if it goes out of bounds horizontally.", added);
    }
    
    // Test 9: Attempt to add a ship that collides with an existing ship and expect failure.
    @Test
    public void testAddShipCollisionFailure() {
        Grid grid = new Grid();
        // Add the first ship successfully.
        Ship ship1 = new Ship(3);
        ship1.setLocation(5, 5);
        ship1.setDirection(Constants.HORIZONTAL);
        boolean added1 = grid.addShip(ship1);
        assertTrue("The first ship should be added successfully.", added1);
        
        // Try to add a second ship that overlaps with the first ship.
        Ship ship2 = new Ship(3);
        ship2.setLocation(5, 6); // This overlaps with ship1.
        ship2.setDirection(Constants.HORIZONTAL);
        boolean added2 = grid.addShip(ship2);
        assertFalse("The second ship should not be added due to collision with the first ship.", added2);
    }
    
    // Test 10: Verify that allShipsSank correctly reports the state of the grid.
    @Test
    public void testAllShipsSank() {
        Grid grid = new Grid();
        // When no ships are placed, allShipsSank should return true.
        assertTrue("allShipsSank should return true when there are no ships placed.", grid.allShipsSank());
        
        // Place a ship of length 2 vertically at (7,7).
        Ship ship = new Ship(2);
        ship.setLocation(7, 7);
        ship.setDirection(Constants.VERTICAL);
        boolean added = grid.addShip(ship);
        assertTrue("The ship should be added successfully.", added);
        
        // At this point, not all ships are hit.
        assertFalse("allShipsSank should return false when a ship has not been hit.", grid.allShipsSank());
        
        // Mark both parts of the ship as hit.
        grid.markHit(7, 7);
        grid.markHit(8, 7);
        assertTrue("allShipsSank should return true after all ship parts have been hit.", grid.allShipsSank());
    }
}
