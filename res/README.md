# PDP_Project Dungeon

### 1. About
Wuse the principles of object oriented programming to implement a solution for the dungeon model adn its controller. 
I used the Kruskal's Union Find Algorithm to generate an MST and added more edges depending on the interconnectivity to generate the dungeon.
The Game type has a dungeon map generated at the start of the game. A player is created on command. The Game type can be used to move this player in the dungeon.
The difficult part of this project was testing the various classess ans methods. This was solved by the use of a wrapper class around Java.util.Random, 
along with another pseudoRandom class which takes integer varargs as parameters and allows simulation of random number generation, 
both these classes extended an interface and had the same methods. This helps with testing the various classes of this module.
Project 4 adds monsters to the dungeons. The player receives 2 new abilities: Smell and Shoot. There are items in the game now.
The player starts with the a 'Bow' item and 3 arrow items. Player can pick up arrows which can be found in dungeon locations. 
The controller is tested separtaely from the model using various mock models and check if it behaves as expected and does not hijack responsibility from the model.


### 2. List of features:
##### 1. Generate a non-deterministic dungeon on every run.
##### 2. Generate random treasure and items in a given percentage of caves.
##### 3. Create one player at the start.
##### 4. Generate the given number of monsters.
##### 5. Move Player in the dungeon using the controller.
##### 6. Make player collect treasure and items on command.
##### 7. Make the player shoot arrows at monsters. Kill the monsters.
##### 8. Game ends when player reaches the end location.
##### 9. Get player/ player location description used by the controller.
##### 10. Play as a console logged text based game.

### 3. How to run:
  * Method one:
    * To use the JAR file, we have to import it into intelliJ from where it can be run.
    * After importing the jar to Intellij, set Program arguments to "m n percentage difficulty enableWrap interconnectivity" in the run/debug configurations tab after clicking on edit configurations.
    * Sample arguments: '5 5 60 3 false 2'. This generates a 5*5 no-wrap dungeon with treasure in 60% of the caves, items in 60% of locations, 3 monsters and 2 interconnectivity.
  * Alternative method:
    * We can run the JAR file in the terminal/ cmd using the command 'java -jar PDP_Project-03.jar m n wrap interconnectivity percentage'.
    * Sample: 'java -jar Dungeon.jar 5 5 60 3 false 2'. This generates a 5*5 no-wrap dungeon with treasure in 60% of the caves, items in 60% of locations, 3 monsters and 2 interconnectivity. 
    * There is an image in the res showing a sample: sampleCommandLineArgsOnTerminal.png

### 4. How to use the program:
  * The program is interactive.
  * It was previously a map based game, but the projects 4 requirements specify text based, so, I've made it text based.
  * Once the game starts, we are shown the current location description and smell that the player senses.
  * We are also shown 4 options to pick from: Move, Pick up Treasure, Pick up Item and Shoot.
  * Once we read the description and grasp the situation, we pick a command.
  * Each command asks for further inputs, like direction, treasure, item, distance.
  * The game ends when the player is killed by a monster or if reaches the end alive.
  * The player wins if he is alive at the end. He loses if he is dead.
  * At the end we are shown the final player description.

### 5. Description of examples:
    Sample runs:
    (1) Display of Location Description
    (2) Display of smell sensed by the player
    (3) Show possible moves.
    (4) Request input move.
    (5) As inputs for the selected move.
    (6) Show result of the move.
    (7) Go to (1) if game is not over else (8)
    (8) Print message based on weather the player has won or lost.
    (9) Display final player description.
    
    List of sample runs:
    1. PlayerWins.txt: Player performs various actions namely, picking treasure, picking items, shooting and moving along with their various outcomes.Player wins in the end.
    2. PlayerLoses.txt: Player runs out of arrows and goes into a cave with an injured monster and dies.
    
### 6. Design changes:
###### Since my project 3 implementation was a map based interactive game, I removed a lot of public methods on the model interface which could defeat the purpose a text based game, for instance, the dump dungeon methods, which dumps the map on the screen.
###### Changed names of classes and methods for aesthetic purposes.
###### Added PlayerDescription and LocationDescription public types. These represent snapshots of the player's and his location's state at a particular point in the game.

### 7. Assumptions:
###### That the percentage of caves that have treasure should be around the given percentage and not extactly the given percentage.
###### Minimum size of the dungeon m\*n follows the condition m\*n-2>5

### 8. Limitations:
###### Removing the previously public methods from the model interface broke a lot of test cases, most of them are fixed though.
###### Did not test if connectivity of non-deterministically generated dungeon is as expected.

### 9. Citations:
###### [Effective Java](https://learning.oreilly.com/library/view/effective-java/9780134686097/)
###### [Youtube: Union Find Kruskal's Algorithm by William Fiset](https://www.youtube.com/watch?v=JZBQLXgSGfs)
