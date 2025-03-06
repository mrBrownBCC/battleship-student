package bcc.battleship;

import static org.junit.Assert.*;
import org.junit.Test;

public class ShipTest {

    @Test
    public void testInitialLocationNotSet() {
        Ship ship = new Ship(3);
        assertFalse("Location should not be set for a newly created Ship", ship.isLocationSet());
    }

    @Test
    public void testInitialDirectionNotSet() {
        Ship ship = new Ship(3);
        assertFalse("Direction should not be set for a newly created Ship", ship.isDirectionSet());
    }

    @Test
    public void testInitialRowIsUnset() {
        Ship ship = new Ship(3);
        assertEquals("Row of newly created Ship should be the UNSET constant -1", Constants.UNSET, ship.getRow());
    }

    @Test
    public void testInitialColumnIsUnset() {
        Ship ship = new Ship(3);
        assertEquals("Column of newly created Ship should be the UNSET constant -1", Constants.UNSET, ship.getCol());
    }

    @Test
    public void testShipLength() {
        Ship ship = new Ship(3);
        assertEquals("Length of newly created Ship with length 3 should be 3", 3, ship.getLength());
    }

    @Test
    public void testInitialDirectionValue() {
        Ship ship = new Ship(3);
        assertEquals("Direction of newly created Ship should be the UNSET constant -1", Constants.UNSET, ship.getDirection());
    }

    @Test
    public void testToStringInitialShip() {
        Ship ship = new Ship(3);
        String expected = "unset direction ship of length 3 at (unset location)";
        assertEquals("Correct toString of a newly created Ship with length 3", expected, ship.toString());
    }

    @Test
    public void testSetLocationRow() {
        Ship ship = new Ship(3);
        ship.setLocation(1, 4);
        assertEquals("After setting the location of a ship to row 1 column 4; the ship's row should be 1", 1, ship.getRow());
    }

    @Test
    public void testSetLocationColumn() {
        Ship ship = new Ship(3);
        ship.setLocation(1, 4);
        assertEquals("After setting the location of a ship to row 1 column 4, the ship's column should be 4", 4, ship.getCol());
    }

    @Test
    public void testSetDirectionHorizontal() {
        Ship ship = new Ship(3);
        ship.setDirection(Constants.HORIZONTAL);
        assertEquals("After setting the direction of a ship to horizontal, the ship's direction should be the HORIZONTAL constant 0",
                     Constants.HORIZONTAL, ship.getDirection());
    }

    @Test
    public void testToStringHorizontalShip() {
        Ship ship = new Ship(3);
        ship.setLocation(1, 4);
        ship.setDirection(Constants.HORIZONTAL);
        String expected = "horizontal ship of length 3 at (1, 4)";
        assertEquals("Correct toString of a horizontal ship of length 3 positioned at row 1 and column 4", expected, ship.toString());
    }
}
