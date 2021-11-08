# PDP_Project-03Dungeon

### 1. About
In this project we use the principles of object oriented programming to implement a solution for the dungeon model. 
I used the Kruskal's Union Find Algorithm to generate an MST and added more edges depending on the interconnectivity to generate the dungeon.
The Game type has a dungeon map generated at the start of the game. A player is created on command. The Game type can be used to move this player in the dungeon.
The difficult part of this project was testing the various classess ans methods. This was solved by the use of a wrapper class around Java.util.Random, 
along with another pseudoRandom class which takes integer varargs as parameters and allows simulation of random number generation, 
both these classes extended an interface and had the same methods. This helps with testing the various classes of this module.


### 2. List of features:
##### 1. Generate a non-deterministic dungeon on every run.
##### 2. Generate random treasure in a given percentage of caves.
##### 3. Create one player at the start on method call.
##### 4. Move Player in the dungeon using the game interface.
##### 5. Make player collect treasure on command.
##### 6. Game ends when player reaches the end location.
##### 7. Get player/ player location description on command.
##### 8. Display map of the dungeon on method call.

### 3. How to run:
  * Method one:
    * To use the JAR file, we have to import it into intelliJ from where it can be run.
    * After importing the jar to Intellij, set Program arguments to "m n wrap interconnectivity percentage" in the run/debug configurations tab after clicking on edit configurations.
    * Sample arguments: '5 5 false 2 60'. This generates a 5*5 no-wrap dungeon with treasure in 60% of the caves.
  * Alternative method:
    * We can run the JAR file in the terminal/ cmd using the command 'java -jar PDP_Project-03.jar m n wrap interconnectivity percentage'.
    * Sample: 'java -jar PDP_Project-03.jar 5 5 false 2 60'. This generates a 5*5 no-wrap dungeon with treasure in 60% of the caves. 
    * There is an image in the res showing a sample: sampleCommandLineArgsOnTerminal.png

### 4. How to use the program:
  * The program is interactive.
  * Once it is starts, we see a map of the dungeon, with the player. 
  * You are also shown all your possible moves.
  * You make a move by giving single character inputs.
  * We can collect treasure, get description, etc, this way as well.
  * When an illegal move is attempted, a simple message is returned stating that the move is not possible.
  * When you the end, the game is over and we get the final player description.

### 5. Description of examples:
    Sample runs:
    (1) Display map.
    (2) Show possible moves.
    (3) Request input move.
    (4) Make move.
    (5) go to (1) if player reached end else (6).
    (6) Game over.
    
    List of sample runs:
    1. PlayerVisitsAllLocations.txt: We go through all nodes before going to the end.
    2. PlayerMovesFromStartToEnd.txt: We make moves to go from start to end.
    3. PlayerCollectingTreasure.txt: 
      . We go to a location with tresure. 
      . Show the location description.
      . Show player description.
      . Make the player collect treasure.
      . Show location description again, which now has zero treasure.
      . Show player description, which has an increased amount of treasure now.
    4. PlayerAndLocationDescriptionAtEveryStep.txt:
      . This run is from driver 3.
      . We make moves, but this time, descriptions are printed by the driver automatically and not on request.
    
    Driver test runs:
    When a winner exists:
    (1) Welcome Message
    (2) Base stats of the players.
    (3) NEW MATCH indication
    (4) Player descriptions with newly added gear and weapon
    (5) Decision of who attacks first
    (6) Round 1: message showing who attacks whom
    (7) Shows if there was any damage or the attack was evaded.
    (8) Shows new healths
    (9) This information is displayed until some wins.
    (10) When someone wins, he is declared as the winner.
    (11) Driver asks if a rematch is wanted.
    (12) Starts again from (3)
    
    When there is a draw on one of the rematches:
    (3) NEW MATCH indication
    (4) Player descriptions with newly added gear and weapon
    (5) If internal calculations indicate that a draw is inevitable, a message is displayed and the match is ended.
    (6) Driver then asks if a rematch is wanted.
    (7) Start again from (3)
    
    Causes for rematch:
    (1) Both players successfully evade each other every single time. [This usually happens when both players have low effective strength and high effective dexterity]
    (2) Both players are not able to break the other's armour/ constitution. [This usually happens when both players have low effective strength and high effective constitution]
    
### 6. Design changes:
###### Removed abstract class: AbstractLocation and classes: Tunnel, Cave and Wall. Have two classes instead: Location, and EmptyLocation; because all those classes had a lot of common functionalities. Differentiated between tunnel and cave from inside the Location.
###### Added package private Connection class, which is used to hold uni directional edges inside LocationGraphs.
###### Changed names of classes for aesthetic purposes.

### 7. Assumptions:
###### That the percentage of caves that have treasure should be around the given percentage and not extactly the given percentage.
###### Minimum size of the dungeon m\*n follows the condition m\*n-2>5

### 8. Limitations:
###### Did not test if connectivity of non-deterministically generated dungeon is as expected.

### 9. Citations:
###### [Effective Java](https://learning.oreilly.com/library/view/effective-java/9780134686097/)
###### [Youtube: Union Find Kruskal's Algorithm by William Fiset](https://www.youtube.com/watch?v=JZBQLXgSGfs)
