# PDP_Project Dungeon

### 1. About
#####Project 3
We use the principles of object oriented programming to implementing the Dungeon Game using the MVC architecture. 
I used the Kruskal's Union Find Algorithm to generate an MST and added more edges depending on the interconnectivity to generate the dungeon map.
The Game type has a dungeon map generated at the start of the game. The Game type can be used to move this player in the dungeon.
The difficult part of this project was testing the various classes and methods. This was solved by the use of a wrapper class around Java.util.Random, 
along with another pseudoRandom class which takes integer varargs as parameters and allows simulation of random number generation, 
both these classes extended an interface and had the same methods. This helps with testing the various classes of this module.

#####Project 4 
adds monsters to the dungeons. The player receives 2 new abilities: Smell and Shoot. There are items in the game now.
The player starts with the a 'Bow' weapon and 3 'arrow' items. Player can pick up arrows which can be found in dungeon map locations. 
The controller testing has been completely separated from the model testing using various mock models. We check if it behaves as expected and does not hijack responsibility from the model.

#####Project 5 
adds a View which is built using Swing. A new Controller has also been added specifically for the View. A moving monster now roams around in the dungeon looking for players. A thief has also been added, he steals from the player's treasure when they cross paths. Pits have been added to locations along with a way to detect them. When a player falls into a pit he loses health. The Strategy Pattern has been used to dictate commands to the thief and the moving monster.


### 2. List of features:
####GUI Game:
##### 1. Move the player in the game.
##### 2. Shoot arrows at otyughs and look for visual cues of sound.
##### 3. Attack the moving monster and get attacked.
##### 4. Get robbed by the thief.
##### 5. Pick up treasure: Rubies, Sapphires and Diamonds.
##### 6. Pick up items: Crooked arrows.
##### 7. Zoom in and Zoom out of the map on the GUI.
##### 8. Home page, Settings Page and Game page.
##### 9. Reset the game to the start state.
##### 10. Restart the game with same dungeon attributes.
##### 11. Start a new game.
##### 12. Quit the GUI.
####Text Based Game:
##### 1. Generate a non-deterministic dungeon game on every run.
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
    * After importing the jar to Intellij, we have 2 options:
        * Text Based Game: set Program arguments to "m n percentage difficulty enableWrap interconnectivity" in the run/debug configurations tab after clicking on edit configurations. Sample arguments: '5 5 60 3 false 2'. This generates a 5*5 no-wrap dungeonmodel with treasure in 60% of the caves, items in 60% of locations, 3 monsters and 2 interconnectivity.
        * GUI Game: do not set any program arguments and just run the jar.
  * Alternative method:
    * Text Based Game:
        * We can run the JAR file in the terminal/ cmd using the command 'java -jar Dungeon.jar m n wrap interconnectivity percentage'.
        * Sample: 'java -jar Dungeon.jar 5 5 60 3 false 2'. This generates a 5*5 no-wrap dungeon with treasure in 60% of the caves, items in 60% of locations, 3 monsters and 2 interconnectivity. 
        * There is an image in the res showing a sample: sampleCommandLineArgsOnTerminal.png
    * GUI Game:
        *  We can run the JAR file in the terminal/ cmd using the command 'java -jar Dungeon.jar'
        * This will open the GUI.

### 4. How to use the program:
  * Text Based Game: 
      * The program is interactive.
      * It was previously a map based game, but the projects 4 requirements specify text based, so, I've made it text based.
      * Once the game starts, we are shown the current location description and smell that the player senses.
      * We are also shown 4 options to pick from: Move, Pick up Treasure, Pick up Item and Shoot.
      * Once we read the description and grasp the situation, we pick a command.
      * Each command asks for further inputs, like direction, treasure, item, distance.
      * The game ends when the player is killed by a monster or if reaches the end alive.
      * The player wins if he is alive at the end. He loses if he is dead.
      * At the end we are shown the final player description.
  * GUI Game:
      * When the GUI opens, we see the home page, which clearly shows intuitive options. We can start a new game here, reset the game, restart the game or quit the GUI.
      * We also have a JMenu which was a hard requirement with the same options.
      * On starting a new game, we can use the following to interact with the game:
        * Move: Arrow Keys/ Clicks on the map on neighbouring locations.
        * Pick Arrow: c
        * Pick Ruby: r
        * Pick Diamond: d
        * Pick Sapphire: s
        * Attack Moving Monster: a
        * Shoot sequence: x followed by arrow key for direction. This is followed by a pop up that takes distance as an input.
      * The game can be reset to the initial start, or a randomizer start using the the menu options or by going back to the home page.
      * The Zoom In and Zoom out buttons on the game page can be used to perform the same actions on the map.
      * The command information [key bindings] can be viewed in game in the info menu by clicking on the commands option.
      
 
   

### 5. Description of examples:
#####Text Based Game:
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
#####GUI Based Game:
    Images of the game in res folder:
    1. after fight with monster
    2. commands
    3. dead otyugh
    4. dead: after player has died
    5. dungeon info: dialog box
    6. dying howl: image on map
    7. howl: image on map
    8. escaped with moving monster
    9. escaped
    10. home
    11. info menu
    12. menu
    13. moving monster
    14. pit and sign
    15. settings
    16. smell
    17. start
    18. thief
    19. treasure and arrows
    20. player with treasure
### 6. Design changes:
Project 4:
###### Since my project 3 implementation was a map based interactive game, I removed a lot of public methods on the model interface which could defeat the purpose a text based game, for instance, the dump dungeonmodel methods, which dumps the map on the screen.
###### Changed names of classes and methods for aesthetic purposes.
###### Added PlayerDescription and LocationDescription public types. These represent snapshots of the player's and his location's state at a particular point in the game.
Project 5:
###### PlayerDesc removed and ReadOnlyPlayer added. Since read only player is a reference of the actual player, we need not continuously fetch PlayerDesc objects.
###### LocationDesc and ReadOnlyLocation added.
###### ReadOnlyGame added.
###### Square pattern used to add new features instead of changing the model.
###### Renamed objects for aesthetic purposes.
 
### 7. Assumptions:
###### That the percentage of caves that have treasure should be around the given percentage and not extactly the given percentage.
###### Minimum size of the dungeon m\*n follows the condition m\*n-2>5
###### Player wins if he is alive and at end location, even if there is an otyugh/ moving monster at the end.
###### Number of pits is the equal to difficulty/ 2.

### 8. Limitations:
Project 3
###### Did not test if connectivity of a non-deterministically generated dungeon is as expected.
Project 4
###### Removing the previously public methods from the model interface broke a lot of test cases, most of them are fixed though.
Project 5
###### Did not test thief because of time constraints.

### 9. Citations:
###### [Effective Java](https://learning.oreilly.com/library/view/effective-java/9780134686097/)
###### [Youtube: Union Find Kruskal's Algorithm by William Fiset](https://www.youtube.com/watch?v=JZBQLXgSGfs)
###### [https://stackoverflow.com/questions/19504745/java-images-not-appearing-in-jar-file](https://stackoverflow.com/questions/19504745/java-images-not-appearing-in-jar-file)
###### [https://docs.oracle.com/en/java/javase/11/docs/api/java.desktop/javax/swing/JMenu.html](https://docs.oracle.com/en/java/javase/11/docs/api/java.desktop/javax/swing/JMenu.html)
###### [Youtube: Swing Tutorial by BroCode](https://www.youtube.com/watch?v=Kmgo00avvEw)
