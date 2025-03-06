package bcc.battleship;

public class Location {
    // Instance variables
    private boolean ship; // indicates whether a ship is present
    private int status;   // status: UNGUESSED, HIT, or MISSED

    // Constructor. Initializes with no ship and status UNGUESSED.
    public Location() {
        this.ship = false;
        this.status = Constants.UNGUESSED;
    }

    // Was this Location a hit?
    public boolean checkHit() {
        return this.status == Constants.HIT;
    }

    // Was this location a miss?
    public boolean checkMiss() {
        return this.status == Constants.MISSED;
    }

    // Was this location unguessed?
    public boolean isUnguessed() {
        return this.status == Constants.UNGUESSED;
    }

    // Mark this location as a hit.
    public void markHit() {
        this.status = Constants.HIT;
    }

    // Mark this location as a miss.
    public void markMiss() {
        this.status = Constants.MISSED;
    }

    // Return whether or not this location has a ship.
    public boolean hasShip() {
        return this.ship;
    }

    // Set whether this location has a ship.
    public void setShip(boolean val) {
        this.ship = val;
    }

    // Set the status of this Location.
    public void setStatus(int status) {
        this.status = status;
    }

    // Get the status of this Location.
    public int getStatus() {
        return this.status;
    }
}
