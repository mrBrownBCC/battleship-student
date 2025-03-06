package bcc.battleship;

import org.junit.Test;
import static org.junit.Assert.*;

public class PlayerTest {

    // Test 1: Verify that a new player's grids are properly initialized.
    @Test
    public void testGridsInitialization() {
        Player player = new Player();
        assertNotNull("Your own grid should be created when a player is initialized.", player.getMyGrid());
        assertNotNull("Your opponent grid should be created when a player is initialized.", player.getOpponentGrid());
        assertEquals("Your grid should have the correct number of rows.", Constants.NUM_ROWS, player.getMyGrid().numRows());
        assertEquals("Your grid should have the correct number of columns.", Constants.NUM_COLS, player.getMyGrid().numCols());
    }

    // Test 2: Valid ship placement using chooseShipLocation should succeed.
    @Test
    public void testValidChooseShipLocation() {
        Player player = new Player();
        // Place the first ship (index 0) horizontally at (0,0)
        boolean success = player.chooseShipLocation(0, 0, 0, Constants.HORIZONTAL);
        assertTrue("Placing the ship in a valid location should succeed.", success);
        // Verify that a ship is now present at (0,0).
        assertTrue("After placing, cell (0,0) should have a ship.", player.getMyGrid().hasShip(0, 0));
    }

    // Test 3: Invalid ship placement due to out-of-bound coordinates should fail.
    @Test
    public void testChooseShipLocationOutOfBounds() {
        Player player = new Player();
        // Place a ship near the right edge; if the ship's length is more than 1, this should fail.
        boolean success = player.chooseShipLocation(0, 0, Constants.NUM_COLS - 1, Constants.HORIZONTAL);
        if (Constants.SHIP_LENGTHS[0] > 1) {
            assertFalse("Placing a ship that goes out-of-bound should fail.", success);
        } else {
            assertTrue("Placing a ship of length 1 at the edge should succeed.", success);
        }
    }

    // Test 4: Invalid ship placement due to collision should fail.
    @Test
    public void testChooseShipLocationCollision() {
        Player player = new Player();
        // Place the first ship at (2,2) horizontally.
        boolean firstSuccess = player.chooseShipLocation(0, 2, 2, Constants.HORIZONTAL);
        assertTrue("The first ship placement should succeed.", firstSuccess);
        // Try to place a second ship overlapping with the first.
        boolean secondSuccess = player.chooseShipLocation(1, 2, 3, Constants.HORIZONTAL);
        assertFalse("Placing a ship that overlaps an existing ship should fail.", secondSuccess);
    }

    // Test 5: recordOpponentGuess should return true when a guess hits a ship.
    @Test
    public void testRecordOpponentGuessHit() {
        Player player = new Player();
        // Place a ship at (4,4) for testing.
        boolean placed = player.chooseShipLocation(0, 4, 4, Constants.HORIZONTAL);
        assertTrue("Placing a ship for the test should succeed.", placed);
        boolean hit = player.recordOpponentGuess(4, 4);
        assertTrue("A guess on a cell with a ship should be a hit.", hit);
    }

    // Test 6: recordOpponentGuess should return false when a guess misses.
    @Test
    public void testRecordOpponentGuessMiss() {
        Player player = new Player();
        // Without any ship placed at (5,5), a guess should miss.
        boolean hit = player.recordOpponentGuess(5, 5);
        assertFalse("A guess on a cell without a ship should be a miss.", hit);
    }

    // Test 7: After a hit guess, the cell's status should be marked as HIT.
    @Test
    public void testRecordOpponentGuessHitMarksCell() {
        Player player = new Player();
        player.chooseShipLocation(0, 3, 3, Constants.HORIZONTAL);
        player.recordOpponentGuess(3, 3);
        assertEquals("After a hit, the cell's status should be HIT.", Constants.HIT, player.getMyGrid().getStatus(3, 3));
    }

    // Test 8: After a miss guess, the cell's status should be marked as MISSED.
    @Test
    public void testRecordOpponentGuessMissMarksCell() {
        Player player = new Player();
        player.recordOpponentGuess(6, 6);
        assertEquals("After a miss, the cell's status should be MISSED.", Constants.MISSED, player.getMyGrid().getStatus(6, 6));
    }

    // Test 9: Verify that getMyGrid returns a grid that contains a ship after placement.
    @Test
    public void testGetMyGridShipPlacement() {
        Player player = new Player();
        player.chooseShipLocation(0, 0, 0, Constants.HORIZONTAL);
        boolean foundShip = false;
        for (int i = 0; i < Constants.NUM_ROWS; i++) {
            for (int j = 0; j < Constants.NUM_COLS; j++) {
                if (player.getMyGrid().hasShip(i, j)) {
                    foundShip = true;
                    break;
                }
            }
            if (foundShip) break;
        }
        assertTrue("After placing a ship, your grid should show a ship somewhere!", foundShip);
    }

    // Test 10: Check that all ships are initialized correctly by attempting to place each one.
    @Test
    public void testAllShipsInitialization() {
        Player player = new Player();
        int totalShips = Constants.SHIP_LENGTHS.length;
        int countPlaced = 0;
        // Place each ship in a different row to avoid collisions.
        for (int i = 0; i < totalShips; i++) {
            boolean placed = player.chooseShipLocation(i, i, 0, Constants.HORIZONTAL);
            if (placed) {
                countPlaced++;
            }
        }
        assertEquals("All ships should be placed if there's room on the grid.", totalShips, countPlaced);
    }
}
