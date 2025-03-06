# Setup Instructions
In order to run the GUI:
Go to the ports tab. Click "Add port" and type the number 6080.
Follow the link to your forwarded address. This is where your GUI should be available once you run your code. Note that the GUI won't work very well until you have finished most of the code - tests will be used to make sure the intermediate steps works well. 

To access the code, go to battleship > app > src > main > java


# Programming Instructions

General notes:
1. Don't change any method headers. These will be correct. 
2. For testing, double check that your terminal ends in something like this:![alt text](image.png). If it does not, run the following command(which stands for change directory). 
```
cd battleship
```
3. Say hello to your (maybe) first real code editor. Autocomplete, error highlighting, and other things are about to make your life so much easier. 
4. Apply for the student github developer pack!!


### Part 1 - Ship.java
Mr. Brown time estimate - 45mins

In this part we will write the code for ship.java. This class will represent a ship object in our battleship game. The Ship has several defining characteristics (think instance variables!) including a position, a length and a direction.

1. Create instance variables to store the integers row, col, length, and direction. NOTE - direction will store an int, but in our code we have named constants that store an int. For example, the 3 possible values are UNSET, HORIZONTAL, and VERTICAL, all defined in the Constants.java file. 
2. Fill in the code for the Ship Constuctor. Length should be set here, and all other values should be set to `Constants.UNSET`
3. Fill in code for setters and getters. 
4. Fill in the code for isLocationSet and isDirectionSet. This should return true if the corresponding values are set and false otherwise. 
5. Write the code for directionToString. Should return either "horizontal", "vertical", or "unset direction"
6. Write the code for locationToString. Should return either "([row], [col])" or "(unset location)"
7. Write the code for the toString method. Should return "[direction] ship of length [length] at [location]". Use the above methods to help!

To test this portion of the project, run the following command: 
```
gradle runShipTest
```

### Part 2 - Location.java
Mr. Brown time estimate - 30mins

The next class to write is the Location.java file. The Location class stores the information for one grid position. A location has two defining attributes:

1) Is there a ship at this location?

2) What is the status of this location?

The status of this would be whether this position is unguessed, we got a hit, or got a miss. All of these values are defined as constants in the Constants file. 

1. Add your instance variables. You should create a boolean to store whether or not this location contains a ship and an int status to contain UNGUESSED, HIT, or MISSED. 
2. Initialize the above instance variables within the constructor. 
3. Fill out the code for the methods checkHit(), checkMiss(), isUnguessed(), markHit(), markMiss(), hasShip(), setShip(), setStatus(), and getStatus(). Each of these can be completed with 1 line of code. 

To test this portion of the project, run the following command: 
```
gradle runLocationTest
```

### Part 3 - Grid.java
Mr. Brown time estimate - 90mins
Now that you have written the Ship class and the Location class, the next step is to write the Grid class. The Grid class represents a Battleship grid for each player. It will be used both to track where your ships are placed and to record the guesses made by your opponent.

The main state to track in the Grid is a 2-dimensional array of Location objects. The size of this grid can be found and modified within Constants.java

1. Create a single instance variable to store a 2D array of Location objects. 
2. Initialize the above instance variable within the constructor. 
3. Complete a few simple methods that make the underlying grid easier to work with - markHit, markMiss, setStatus, getStatus, alreadyGuessed, setShip, hasShip, get, numRows, and numCols
4. Complete the addShip method. This method is the most complex one so far! Given the ship object s, you need to mark all the Locations that the ship will occupy within the grid using setShip(true). HOWEVER, if the ship will at any point be out of bounds OR overlap another ship, this method should return false. If the ship can be successfully placed, return true. 
5. Complete the allShipsSank method. This should visit all locations in the grid and check if all locations that have a ship also are hit. If all ships are sank return true, otherwise false. 

```
gradle runGridTest
```

### Part 4 - Player.java
Mr. Brown time estimate - 30mins
Now that we have written Ship, Location, and Grid, it is time to write the Player class. Player will handle the logic for each player.

Each player should have an array of 5 ships. Each ship is of a set length. You have one ship of length 2, two ships of length 3, one ship of length 4 and one ship of length 5. These lengths can be found in Constants.java for your use. 

Then each Player also will have 2 Grids. One grid represents that player’s grid, and the other grid represents their opponents grid. On the player’s grid, they will mark the location of their ships, as well as record the guesses of their opponent. On the opponents grid, the player will record their own guesses of where they think the battleships are.

1. Declare the instance variables. You should have an array of ships, a grid for yourself, and a grid for your opponent. 
2. Write the constructor. Initialize the constructor. Using the ship lengths in Constants.java fill in the ship array with ships of the required lengths. 
3. Write chooseShipLocation. This should modify set the location and direction of the ship at index index, then attempt to add it to the grid. If successful return true, if unsuccessful, return false. 
4. Write recordOpponentGuess. If you have a ship at the location, use the markHit method on that locataion in your grid. If it is a miss, use the markMiss method on that location in your grid. 
5. Complete the getters at the bottom of the file. 

```
gradle runPlayerTest
```

# Submission
1. Testing everything. Run the command - 
``` 
gradle test
```

2. Submit on github classroom by running the following commands. This also saves your work permanently (unless you actively want to delete it). 

```
git add . 
git commit -m "submitting"
git push
```

3. Mark on Canvas that you have submitted here by typing the word 'github' and submitting. 