package bcc.battleship;

public class Ship {

    // Constructor. Create a ship and set the length.
    public Ship(int length) {
    }

    // Has the location been initialized
    public boolean isLocationSet() {
        return false;
    }

    // Has the direction been initialized
    public boolean isDirectionSet() {
        return false;
    }

    // Set the location of the ship
    public void setLocation(int row, int col) {

    }

    // Set the direction of the ship
    public void setDirection(int direction) {

    }

    // Getter for the row value
    public int getRow() {
        return 0;
    }

    // Getter for the column value
    public int getCol() {
        return 0;
    }

    // Getter for the length of the ship
    public int getLength() {
        return 0;
    }

    // Getter for the direction
    public int getDirection() {
        return 0;
    }

    // Helper method to get a string value from the direction
    // Helper method to get a string value from the direction
    private String directionToString() {
        return "";
    }

    // Helper method to get a (row, col) string value from the location
    private String locationToString() {
        return "";
    }

    // toString value for this Ship
    @Override
    public String toString() {
        return "";
    }
}