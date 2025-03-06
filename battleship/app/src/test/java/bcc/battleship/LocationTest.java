package bcc.battleship;

import org.junit.Test;
import static org.junit.Assert.*;

public class LocationTest {

    // Test 1: A new Location should have no ship and be unguessed.
    @Test
    public void testDefaultConstructor() {
        Location loc = new Location();
        assertFalse("New location should not have a ship. Try placing one later!", loc.hasShip());
        assertEquals("New location should be unguessed. Let's start fresh!", Constants.UNGUESSED, loc.getStatus());
    }

    // Test 2: markHit should mark the location as a hit.
    @Test
    public void testMarkHit() {
        Location loc = new Location();
        loc.markHit();
        assertTrue("After markHit, the location should be a hit. Great job!", loc.checkHit());
        assertEquals("Status should be HIT after marking a hit.", Constants.HIT, loc.getStatus());
    }

    // Test 3: markMiss should mark the location as a miss.
    @Test
    public void testMarkMiss() {
        Location loc = new Location();
        loc.markMiss();
        assertTrue("After markMiss, the location should be a miss. Keep trying!", loc.checkMiss());
        assertEquals("Status should be MISSED after marking a miss.", Constants.MISSED, loc.getStatus());
    }

    // Test 4: A new Location should be unguessed.
    @Test
    public void testIsUnguessed() {
        Location loc = new Location();
        assertTrue("A new location should be unguessed. Ready for action!", loc.isUnguessed());
    }

    // Test 5: setShip should correctly mark that a ship is present.
    @Test
    public void testSetShipTrue() {
        Location loc = new Location();
        loc.setShip(true);
        assertTrue("The location should have a ship after setting it to true.", loc.hasShip());
    }

    // Test 6: setShip should correctly mark that a ship is absent.
    @Test
    public void testSetShipFalse() {
        Location loc = new Location();
        loc.setShip(true);
        loc.setShip(false);
        assertFalse("The location should not have a ship after setting it to false.", loc.hasShip());
    }

    // Test 7: setStatus should update the status correctly.
    @Test
    public void testSetStatus() {
        Location loc = new Location();
        loc.setStatus(Constants.HIT);
        assertEquals("The status should be updated to HIT.", Constants.HIT, loc.getStatus());
        loc.setStatus(Constants.MISSED);
        assertEquals("The status should be updated to MISSED.", Constants.MISSED, loc.getStatus());
    }

    // Test 8: checkHit should return false if the location is not hit.
    @Test
    public void testCheckHitFalse() {
        Location loc = new Location();
        assertFalse("checkHit should be false on a new location.", loc.checkHit());
    }

    // Test 9: checkMiss should return false if the location is not miss.
    @Test
    public void testCheckMissFalse() {
        Location loc = new Location();
        assertFalse("checkMiss should be false on a new location.", loc.checkMiss());
    }

    // Test 10: Transition from unguessed to hit and then to miss.
    @Test
    public void testStatusTransition() {
        Location loc = new Location();
        loc.setShip(true); // Place a ship.
        loc.markHit();
        assertTrue("After marking hit, the location should register as hit.", loc.checkHit());
        loc.markMiss();
        assertTrue("After marking miss, the location should register as miss, even if a ship was there.", loc.checkMiss());
    }
}
