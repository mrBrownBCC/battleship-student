package bcc.battleship;

public class Location {
    // Instance variables

    // Constructor. Initializes with no ship and status UNGUESSED.
    public Location() {

    }

    // Was this Location a hit?
    public boolean checkHit() {
        return false;
    }

    // Was this location a miss?
    public boolean checkMiss() {
        return false;
    }

    // Was this location unguessed?
    public boolean isUnguessed() {
        return false;
    }

    // Mark this location as a hit.
    public void markHit() {

    }

    // Mark this location as a miss.
    public void markMiss() {

    }

    // Return whether or not this location has a ship.
    public boolean hasShip() {
        return false;
    }

    // Set whether this location has a ship.
    public void setShip(boolean val) {

    }

    // Set the status of this Location.
    public void setStatus(int status) {

    }

    // Get the status of this Location.
    public int getStatus() {
        return 0;
    }
}
