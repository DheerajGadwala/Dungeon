package external;

import static general.Direction.EAST;
import static general.Direction.NORTH;
import static general.Direction.SOUTH;
import static general.Direction.WEST;

import dungeon.DungeonGame;
import dungeon.Game;
import general.MatrixPosition;
import junit.framework.Assert;
import org.junit.Before;
import org.junit.Test;
import randomizer.ActualRandomizer;
import randomizer.Randomizer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * This is the test suite for the dungeon game.
 */
public class DungeonGameTest {

  private Game sample1;
  private Game sample2;
  private Randomizer randomizer;

  /**
   * Setup for this test suite.
   */
  @Before
  public void setUp() {
    randomizer = new ActualRandomizer();
    sample1 = new DungeonGame(10, 10, false, 0);
    sample2 = new DungeonGame(6, 7, false, 0);
    sample2.createPlayer("Dheeraj");
  }

  /**
   * Test if exception is thrown when player name is null.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testCreatePlayer1() {
    sample1.createPlayer(null);
  }

  /**
   * Test if exception is thrown when player name is empty.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testCreatePlayer2() {
    sample1.createPlayer("");
  }

  /**
   * Test if exception is thrown when trying to add a player when another already exists.
   */
  @Test(expected = IllegalStateException.class)
  public void testCreatePlayer3() {
    sample2.createPlayer("Dheeraj2");
  }

  /**
   * Test if exception is thrown when movePlayer is called but player has not yet been created.
   */
  @Test(expected = IllegalStateException.class)
  public void testMovePlayerException() {
    Game game = new DungeonGame(10, 10, false, 0);
    game.movePlayer(NORTH);
  }

  /**
   * Test wrapping dungeon generation using pseudo random input.
   * Will create a wrapping dungeon when given a valid set of random numbers.
   * This also shows that the start-end distance is greater than 5 in this pseudo random dungeon.
   * This also shows that all nodes are connected in this pseudo random dungeon.
   */
  @Test
  public void testWrappingDungeon() {
    Game game = new DungeonGame(6, 7, true, 0,
        21,120,10,110,82,102,64,88,125,92,104,84,38,58,41,39,80,57,28,113,73,10,25,
        20,87,67,47,8,64,38,23,64,42,44,69,48,11,78,29,68,81,34,1,47,75,46,44,45,64,41,4
    );
    Assert.assertEquals(
        "[***][***][***][***][***][***][***][***][***]\n"
            + "[***]                 |         |       [***]\n"
            + "[***]--+----O----+    O    O----O----+--[***]\n"
            + "[***]       |    |              |       [***]\n"
            + "[***]       |    |              |       [***]\n"
            + "[***]--+----+    +----O----+    O    O--[***]\n"
            + "[***]                 |    |            [***]\n"
            + "[***]                 |    |            [***]\n"
            + "[***]--O    O----O----+    O    +----+--[***]\n"
            + "[***]            |              |       [***]\n"
            + "[***]            |              |       [***]\n"
            + "[***]--+    +----O----+----O----O----O--[***]\n"
            + "[***]  |    |              |         |  [***]\n"
            + "[***]  |    |              |         |  [***]\n"
            + "[***]  +    +    +----+    O    O----+  [***]\n"
            + "[***]  |    |    |    |                 [***]\n"
            + "[***]  |    |    |    |                 [***]\n"
            + "[***]  O    +----+    +    O----O----O  [***]\n"
            + "[***]                 |         |       [***]\n"
            + "[***][***][***][***][***][***][***][***][***]\n",
        game.displayStaticMap()
    );
  }

  /**
   * Test wrapping dungeon generation using pseudo random input.
   * Will create a non-wrapping dungeon when given a valid set of random numbers.
   * This also shows that the start-end distance is greater than 5 in this pseudo random dungeon.
   * This also shows that all nodes are connected in this pseudo random dungeon.
   */
  @Test
  public void testNonWrappingDungeon() {
    Game game = new DungeonGame(6, 7, false, 0,
        19,26,121,45,92,123,56,17,19,67,79,34,3,104,28,3,73,90,27,1,77,34,28,20,89,
        70,89,15,43,19,70,63,32,51,12,22,46,50,42,50,41,24,40,48,30,43,44,8,33,0,13,34,25,2
    );
    Assert.assertEquals(
        "[***][***][***][***][***][***][***][***][***]\n"
            + "[***]                                   [***]\n"
            + "[***]  +----+    O    O    O----+----+  [***]\n"
            + "[***]  |    |    |    |              |  [***]\n"
            + "[***]  |    |    |    |              |  [***]\n"
            + "[***]  O    O----O----+    +----+----+  [***]\n"
            + "[***]       |    |         |            [***]\n"
            + "[***]       |    |         |            [***]\n"
            + "[***]  O----+    +    O----O    O    O  [***]\n"
            + "[***]            |         |    |    |  [***]\n"
            + "[***]            |         |    |    |  [***]\n"
            + "[***]  O    O    +----+----O----O----O  [***]\n"
            + "[***]  |    |              |         |  [***]\n"
            + "[***]  |    |              |         |  [***]\n"
            + "[***]  O----O    +----+----O----+    O  [***]\n"
            + "[***]  |    |    |         |    |       [***]\n"
            + "[***]  |    |    |         |    |       [***]\n"
            + "[***]  O    +----O----O    O    +----O  [***]\n"
            + "[***]                                   [***]\n"
            + "[***][***][***][***][***][***][***][***][***]\n",
        game.displayStaticMap());
  }

  /**
   * Test showing map when interconnectivity is not zero.
   */
  @Test
  public void testInterConnectivityNonZero() {
    Game s = new DungeonGame(5, 7, true, 2,
        137,84,79,91,130,14,75,2,67,22,55,113,8,52,17,80,77,73,65,17,84,46,43,
        83,46,23,32,1,11,69,55,61,24,42,1,1,61,25,3,65,14,2,28,19,31,2,11,2,1,3,4,3,3,2,
        9,1,1,2,14,3,1,2,0,3,3,1,9,2,1,1,5,1,1,3,2,3,2,2,6,3,2,1,0,1,1,3,9,2,2,1,1,2,2,3
    );
    s.createPlayer("Dheeraj");
    s.generateTreasure(60);
    Assert.assertEquals(
        "[***][***][***][***][***][***][***][***][***]\n"
            + "[***]       |         |              |  [***]\n"
            + "[***]--O----+    +----T    O    +----T--[***]\n"
            + "[***]  |         |    |    |    |       [***]\n"
            + "[***]  |         |    |    |    |       [***]\n"
            + "[***]  T    O    E----T----+    O    O  [***]\n"
            + "[***]       |    |                   |  [***]\n"
            + "[***]       |    |                   |  [***]\n"
            + "[***]--O----T----T----+----+----T    +--[***]\n"
            + "[***]  |    |                           [***]\n"
            + "[***]  |    |                           [***]\n"
            + "[***]  T    +----+    T    +----+----O  [***]\n"
            + "[***]            |    |    |            [***]\n"
            + "[***]            |    |    |            [***]\n"
            + "[***]--O    +----+    T----T----+----P--[***]\n"
            + "[***]       |         |              |  [***]\n"
            + "[***][***][***][***][***][***][***][***][***]\n",
        s.displayMap());
  }

  /**
   * Testing showing map when interconnectivity is zero.
   */
  @Test
  public void testInterconnectivityZero() {
    Game s = new DungeonGame(5, 7, true, 0,
        33,41,126,65,66,103,127,119,77,16,55,100,49,100,16,38,41,31,69,94,44,27,90,
        91,85,33,34,58,40,56,71,24,21,38,67,41,21,0,2,17,5,17,1,31,0,5,12,12,2,1,1,0,3,2,1,
        1,3,3,3,7,2,1,3,2,1,1,3,12,3,2,3,0,1,3,1,7,1,3,2,11,2,3,2,2,1,1,3,6,3,3,2,4,1,1,3
    );
    s.createPlayer("Dheeraj");
    s.generateTreasure(60);
    Assert.assertEquals(
        "[***][***][***][***][***][***][***][***][***]\n"
            + "[***]                 |    |            [***]\n"
            + "[***]--+    O    T----T    T----+    P--[***]\n"
            + "[***]  |    |         |    |    |       [***]\n"
            + "[***]  |    |         |    |    |       [***]\n"
            + "[***]--O----O----+----+    +    O    T--[***]\n"
            + "[***]                      |            [***]\n"
            + "[***]                      |            [***]\n"
            + "[***]  +----+----O    E    +----+----T  [***]\n"
            + "[***]  |              |                 [***]\n"
            + "[***]  |              |                 [***]\n"
            + "[***]  T----+----O----O----+    T    T  [***]\n"
            + "[***]  |         |         |    |    |  [***]\n"
            + "[***]  |         |         |    |    |  [***]\n"
            + "[***]  T    T----+    +----O----T----+  [***]\n"
            + "[***]                 |    |            [***]\n"
            + "[***][***][***][***][***][***][***][***][***]\n",
        s.displayMap());
  }

  /**
   * When interconnectivity is too high.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testInvalidInterconnectivity() {
    new DungeonGame(6, 8, true, 200);
  }

  /**
   * Test invalid dimensions in dungeon generation.
   * m<0.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testInvalidDungeonGameArguments1() {
    new DungeonGame(-5, 8, true, 200);
  }

  /**
   * Test invalid dimensions in dungeon generation.
   * n<0.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testInvalidDungeonGameArguments2() {
    new DungeonGame(6, -8, true, 200);
  }

  /**
   * Test invalid dimensions in dungeon generation.
   * m+n-2<=5.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testInvalidDungeonGameArguments3() {
    new DungeonGame(3, 3, true, 200);
  }

  /**
   * Test location of the player when he is created.
   */
  @Test
  public void testPlayerLocationAtStart() {
    Game game = new DungeonGame(6, 10, false, 0);
    game.createPlayer("Dheeraj");
    Assert.assertEquals(game.getStartPosition(), game.getPlayerPosition());
  }

  /**
   * Test that an end location is decided on dungeon
   * creation and it is possible for the player to reach it.
   */
  @Test
  public void testPlayerAtEndLocation() {
    Game game = new DungeonGame(
        5, 5, false, 2,
        40,4,17,20,59,29,45,53,12,56,53,2,48,50,
        10,11,9,10,40,39,20,29,27,25,27,21,6,16,22,2,5,1
    );
    game.createPlayer("Dheeraj");
    game.movePlayer(EAST);
    game.movePlayer(SOUTH);
    game.movePlayer(EAST);
    game.movePlayer(SOUTH);
    game.movePlayer(EAST);
    game.movePlayer(EAST);
    Assert.assertEquals(game.getPlayerPosition(), game.getEndPosition());
  }

  /**
   * Test if player description is as expected.
   */
  @Test
  public void testPlayerDescription() {
    Game game = new DungeonGame(
        5, 5, false, 2,
        7,11,9,68,9,63,16,29,56,52,29,54,31,46,33,18,11,32,31,5,22,24,19,4,3,4,
        20,9,18,8,5,14,9,18,0,1,4,1,2,1,1,1,3,3,7,1,3,2,6,2,2,0,5,3,1,3,1,0,1,2,2,1,0,1
    );
    game.generateTreasure(60);
    game.createPlayer("Dheeraj");
    game.cedeTreasure();
    game.movePlayer(EAST);
    game.cedeTreasure();
    game.movePlayer(SOUTH);
    game.cedeTreasure();
    game.movePlayer(EAST);
    game.cedeTreasure();
    game.movePlayer(SOUTH);
    game.cedeTreasure();
    Assert.assertEquals(
        "Name: Dheeraj\n"
            + "Tunnel:\n"
            + " Coordinates -> (1, 1) Neighbours {N, E, S, W}: -> "
            + "{Empty node, Cave, Empty node, Cave}\n"
            + "Player treasure: \n DIAMOND: 5 RUBY: 3 SAPPHIRE: 3 \n",
        game.getPlayerDescription()
    );
  }

  /**
   * Test if description of the player's location is as expected.
   * Player in a tunnel.
   */
  @Test
  public void testPlayerLocationDescription1() {
    Game game = new DungeonGame(
        5, 5, false, 2,
        7,11,9,68,9,63,16,29,56,52,29,54,31,46,33,18,11,32,31,5,22,24,19,4,3,4,
        20,9,18,8,5,14,9,18,0,1,4,1,2,1,1,1,3,3,7,1,3,2,6,2,2,0,5,3,1,3,1,0,1,2,2,1,0,1
    );
    game.generateTreasure(60);
    game.createPlayer("Dheeraj");
    game.cedeTreasure();
    game.movePlayer(EAST);
    game.cedeTreasure();
    game.movePlayer(SOUTH);
    game.cedeTreasure();
    game.movePlayer(EAST);
    Assert.assertEquals(
        "\nTunnel:\n"
            + " Coordinates -> (1, 1) Neighbours {N, E, S, W}: -> "
            + "{Empty node, Cave, Empty node, Cave}\n",
        game.getPlayerLocationDescription()
    );
  }

  /**
   * Test if description of the player's location is as expected.
   * Player in a cave with treasure.
   */
  @Test
  public void testPlayerLocationDescription2() {
    Game game = new DungeonGame(
        5, 5, false, 2,
        7,11,9,68,9,63,16,29,56,52,29,54,31,46,33,18,11,32,31,5,22,24,19,4,3,4,
        20,9,18,8,5,14,9,18,0,1,4,1,2,1,1,1,3,3,7,1,3,2,6,2,2,0,5,3,1,3,1,0,1,2,2,1,0,1
    );
    game.generateTreasure(60);
    game.createPlayer("Dheeraj");
    game.cedeTreasure();
    game.movePlayer(EAST);
    game.cedeTreasure();
    game.movePlayer(SOUTH);
    Assert.assertEquals(
        "\nCave:\n Coordinates -> (1, 0) Neighbours {N, E, S, W}: -> "
            + "{Cave, Tunnel, Tunnel, Empty node}\n"
            + " Treasure at this location-> DIAMOND: 3 RUBY: 1 SAPPHIRE: 3 \n",
        game.getPlayerLocationDescription()
    );
  }

  /**
   * Test if description of the player's location is as expected.
   * Player in a cave without treasure.
   */
  @Test
  public void testPlayerLocationDescription3() {
    Game game = new DungeonGame(
        5, 5, false, 2,
        7,11,9,68,9,63,16,29,56,52,29,54,31,46,33,18,11,32,31,5,22,24,19,4,3,4,
        20,9,18,8,5,14,9,18,0,1,4,1,2,1,1,1,3,3,7,1,3,2,6,2,2,0,5,3,1,3,1,0,1,2,2,1,0,1
    );
    game.generateTreasure(60);
    game.createPlayer("Dheeraj");
    game.cedeTreasure();
    game.movePlayer(EAST);
    game.cedeTreasure();
    game.movePlayer(SOUTH);
    game.cedeTreasure();
    Assert.assertEquals(
        "\nCave:\n Coordinates -> (1, 0) Neighbours {N, E, S, W}: -> "
            + "{Cave, Tunnel, Tunnel, Empty node}\n"
            + " Treasure at this location-> DIAMOND: 0 RUBY: 0 SAPPHIRE: 0 \n",
        game.getPlayerLocationDescription()
    );
  }

  /**
   * Test showing player collects treasure from his location.
   * First we assert the treasure the player has,
   * Then we assert the treasure at the location,
   * Then we make the player collect the treasure and assert his total treasure.
   */
  @Test
  public void testPlayerCollectingTreasure() {
    Game game = new DungeonGame(
        5, 5, false, 2,
        7,11,9,68,9,63,16,29,56,52,29,54,31,46,33,18,11,32,31,5,22,24,19,4,3,4,
        20,9,18,8,5,14,9,18,0,1,4,1,2,1,1,1,3,3,7,1,3,2,6,2,2,0,5,3,1,3,1,0,1,2,2,1,0,1
    );
    game.generateTreasure(60);
    game.createPlayer("Dheeraj");
    game.cedeTreasure();
    game.movePlayer(EAST);
    game.cedeTreasure();
    Assert.assertEquals(
        "Player treasure: \n"
            + " DIAMOND: 2 RUBY: 2 SAPPHIRE: 0 \n",
        game.getPlayerTreasureDescription()
    );
    game.movePlayer(SOUTH);
    Assert.assertEquals(
        "\nCave:\n Coordinates -> (1, 0) Neighbours {N, E, S, W}: -> "
            + "{Cave, Tunnel, Tunnel, Empty node}\n"
            + " Treasure at this location-> DIAMOND: 3 RUBY: 1 SAPPHIRE: 3 \n",
        game.getPlayerLocationDescription()
    );
    game.cedeTreasure();
    Assert.assertEquals(
        "Player treasure: \n"
            + " DIAMOND: 5 RUBY: 3 SAPPHIRE: 3 \n",
        game.getPlayerTreasureDescription()
    );
  }

  /**
   * Test that only the given percentage of caves get treasure.
   * Total number of caves that get the treasure should be equal to
   * the total number of caves multiplied by the given percentage.
   */
  @Test
  public void testTreasurePercentage() {
    Game game = generateRandomDungeon();
    int percentage = randomizer.getIntBetween(0, 100);
    game.generateTreasure(percentage);
    List<MatrixPosition> nodes = game.getAllPositions();
    int countCaves = 0;
    int countTreasureCaves = 0;
    for (MatrixPosition node: nodes) {
      if (game.caveAtPosition(node)) {
        countCaves++;
      }
      if (game.treasureAtPosition(node)) {
        countTreasureCaves++;
      }
    }
    Assert.assertEquals((int) (countCaves * percentage / 100.0), countTreasureCaves, 1);
  }

  /**
   * Test if negative percentage on generateTreasure method throws an exception as expected.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testAddTreasureException1() {
    Game game = generateRandomDungeon();
    game.generateTreasure(-10);
  }

  /**
   * Test if percentage greater than 100 on generateTreasure method throws an exception as expected.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testAddTreasureException2() {
    Game game = generateRandomDungeon();
    game.generateTreasure(120);
  }

  /**
   * Test if error is thrown when getPlayerDescription is called before creating player.
   */
  @Test(expected = IllegalStateException.class)
  public void testPlayerDescriptionException() {
    Game game = generateRandomDungeon();
    game.getPlayerDescription();
  }

  /**
   * Test if error is thrown when getPlayerLocationDescription is called before creating player.
   */
  @Test(expected = IllegalStateException.class)
  public void testPlayerLocationDescriptionException() {
    Game game = generateRandomDungeon();
    game.getPlayerLocationDescription();
  }

  /**
   * Test if error is thrown when getPlayerTreasureDescription is called before creating player.
   */
  @Test(expected = IllegalStateException.class)
  public void testPlayerTreasureDescriptionException() {
    Game game = generateRandomDungeon();
    game.getPlayerTreasureDescription();
  }

  /**
   * Test that moving player in any direction changes his location.
   * P represents the player.
   * The dungeon state gets updated hence the map has the player's new location.
   * We move him in all 4 directions and check the dungeon map.
   */
  @Test
  public void testDirectionsOnPlayer() {
    Game game = new DungeonGame(
        6, 7, false, 2,
        98,52,78,128,21,87,50,74,71,78,71,72,94,16,54,81,86,52,
        50,11,25,98,2,52,7,37,17,73,22,13,42,13,34,29,38,64,69,53,23,61,42,
        43,0,42,32,7,39,39,23,8,16,36,53,5,25,1,31,38,30,11,19,32,33,0,1
    );
    game.createPlayer("Dheeraj");
    Assert.assertEquals(
        "[***][***][***][***][***][***][***][***][***]\n"
            + "[***]                                   [***]\n"
            + "[***]  P----+----O----+    +----O    O  [***]\n"
            + "[***]            |    |    |         |  [***]\n"
            + "[***]            |    |    |         |  [***]\n"
            + "[***]  O    +----O    O    +    O----O  [***]\n"
            + "[***]  |    |    |         |         |  [***]\n"
            + "[***]  |    |    |         |         |  [***]\n"
            + "[***]  +----O    O----+    O----O----+  [***]\n"
            + "[***]       |    |    |    |    |       [***]\n"
            + "[***]       |    |    |    |    |       [***]\n"
            + "[***]  O----O----O    +----O    +    O  [***]\n"
            + "[***]       |    |         |    |    |  [***]\n"
            + "[***]       |    |         |    |    |  [***]\n"
            + "[***]  O----+    E----O    +    O----O  [***]\n"
            + "[***]            |         |    |    |  [***]\n"
            + "[***]            |         |    |    |  [***]\n"
            + "[***]  O----+----+    O----+    +----+  [***]\n"
            + "[***]                                   [***]\n"
            + "[***][***][***][***][***][***][***][***][***]\n",
        game.displayMap());
    game.movePlayer(EAST);
    Assert.assertEquals(
        "[***][***][***][***][***][***][***][***][***]\n"
            + "[***]                                   [***]\n"
            + "[***]  S----P----O----+    +----O    O  [***]\n"
            + "[***]            |    |    |         |  [***]\n"
            + "[***]            |    |    |         |  [***]\n"
            + "[***]  O    +----O    O    +    O----O  [***]\n"
            + "[***]  |    |    |         |         |  [***]\n"
            + "[***]  |    |    |         |         |  [***]\n"
            + "[***]  +----O    O----+    O----O----+  [***]\n"
            + "[***]       |    |    |    |    |       [***]\n"
            + "[***]       |    |    |    |    |       [***]\n"
            + "[***]  O----O----O    +----O    +    O  [***]\n"
            + "[***]       |    |         |    |    |  [***]\n"
            + "[***]       |    |         |    |    |  [***]\n"
            + "[***]  O----+    E----O    +    O----O  [***]\n"
            + "[***]            |         |    |    |  [***]\n"
            + "[***]            |         |    |    |  [***]\n"
            + "[***]  O----+----+    O----+    +----+  [***]\n"
            + "[***]                                   [***]\n"
            + "[***][***][***][***][***][***][***][***][***]\n",
        game.displayMap());
    game.movePlayer(EAST);
    game.movePlayer(SOUTH);
    Assert.assertEquals(
        "[***][***][***][***][***][***][***][***][***]\n"
            + "[***]                                   [***]\n"
            + "[***]  S----+----O----+    +----O    O  [***]\n"
            + "[***]            |    |    |         |  [***]\n"
            + "[***]            |    |    |         |  [***]\n"
            + "[***]  O    +----P    O    +    O----O  [***]\n"
            + "[***]  |    |    |         |         |  [***]\n"
            + "[***]  |    |    |         |         |  [***]\n"
            + "[***]  +----O    O----+    O----O----+  [***]\n"
            + "[***]       |    |    |    |    |       [***]\n"
            + "[***]       |    |    |    |    |       [***]\n"
            + "[***]  O----O----O    +----O    +    O  [***]\n"
            + "[***]       |    |         |    |    |  [***]\n"
            + "[***]       |    |         |    |    |  [***]\n"
            + "[***]  O----+    E----O    +    O----O  [***]\n"
            + "[***]            |         |    |    |  [***]\n"
            + "[***]            |         |    |    |  [***]\n"
            + "[***]  O----+----+    O----+    +----+  [***]\n"
            + "[***]                                   [***]\n"
            + "[***][***][***][***][***][***][***][***][***]\n",
        game.displayMap());
    game.movePlayer(WEST);
    Assert.assertEquals(
        "[***][***][***][***][***][***][***][***][***]\n"
            + "[***]                                   [***]\n"
            + "[***]  S----+----O----+    +----O    O  [***]\n"
            + "[***]            |    |    |         |  [***]\n"
            + "[***]            |    |    |         |  [***]\n"
            + "[***]  O    P----O    O    +    O----O  [***]\n"
            + "[***]  |    |    |         |         |  [***]\n"
            + "[***]  |    |    |         |         |  [***]\n"
            + "[***]  +----O    O----+    O----O----+  [***]\n"
            + "[***]       |    |    |    |    |       [***]\n"
            + "[***]       |    |    |    |    |       [***]\n"
            + "[***]  O----O----O    +----O    +    O  [***]\n"
            + "[***]       |    |         |    |    |  [***]\n"
            + "[***]       |    |         |    |    |  [***]\n"
            + "[***]  O----+    E----O    +    O----O  [***]\n"
            + "[***]            |         |    |    |  [***]\n"
            + "[***]            |         |    |    |  [***]\n"
            + "[***]  O----+----+    O----+    +----+  [***]\n"
            + "[***]                                   [***]\n"
            + "[***][***][***][***][***][***][***][***][***]\n",
        game.displayMap());
    game.movePlayer(SOUTH);
    game.movePlayer(WEST);
    game.movePlayer(NORTH);
    Assert.assertEquals(
        "[***][***][***][***][***][***][***][***][***]\n"
            + "[***]                                   [***]\n"
            + "[***]  S----+----O----+    +----O    O  [***]\n"
            + "[***]            |    |    |         |  [***]\n"
            + "[***]            |    |    |         |  [***]\n"
            + "[***]  P    +----O    O    +    O----O  [***]\n"
            + "[***]  |    |    |         |         |  [***]\n"
            + "[***]  |    |    |         |         |  [***]\n"
            + "[***]  +----O    O----+    O----O----+  [***]\n"
            + "[***]       |    |    |    |    |       [***]\n"
            + "[***]       |    |    |    |    |       [***]\n"
            + "[***]  O----O----O    +----O    +    O  [***]\n"
            + "[***]       |    |         |    |    |  [***]\n"
            + "[***]       |    |         |    |    |  [***]\n"
            + "[***]  O----+    E----O    +    O----O  [***]\n"
            + "[***]            |         |    |    |  [***]\n"
            + "[***]            |         |    |    |  [***]\n"
            + "[***]  O----+----+    O----+    +----+  [***]\n"
            + "[***]                                   [***]\n"
            + "[***][***][***][***][***][***][***][***][***]\n",
        game.displayMap());
  }

  /**
   * Any randomly generated dungeon will pass this test.
   * Every node can be visited from every other node.
   * Since this is a bi directional graph, if we visit all nodes
   * in a single bfs, our dungeon graph is strongly connected.
   * We start the bfs with a random node.
   */
  @Test
  public void testAllLocationsReachable() {
    Game game = generateRandomDungeon();
    List<MatrixPosition> nodes = game.getAllPositions();
    Map<MatrixPosition, List<MatrixPosition>> adjacencyList = generateAdjacencyList(
        nodes,
        game.getAllConnections()
    );
    Map<MatrixPosition, Boolean> visited = new HashMap<>();
    for (MatrixPosition node: nodes) {
      visited.put(node, false);
    }
    List<MatrixPosition> queue = new ArrayList<>();
    queue.add(nodes.get(randomizer.getIntBetween(0, nodes.size() - 1)));
    while (queue.size() > 0) {
      MatrixPosition curr = queue.remove(0);
      if (visited.get(curr)) {
        continue;
      }
      visited.replace(curr, true);
      queue.addAll(adjacencyList.get(curr));
    }
    boolean allVisited = true;
    for (MatrixPosition node: nodes) {
      allVisited &= visited.get(node);
    }
    Assert.assertTrue(allVisited);
  }

  /**
   * Any randomly generated dungeon will pass this test.
   * We find the shortest distance from the start node
   * to the other nodes using a bfs.
   * We assert that the distance to the end node is greater than 5.
   */
  @Test
  public void startEndDistance() {
    Game game = generateRandomDungeon();
    List<MatrixPosition> nodes = game.getAllPositions();
    Map<MatrixPosition, List<MatrixPosition>> adjacencyList = generateAdjacencyList(
        nodes,
        game.getAllConnections()
    );
    Map<MatrixPosition, Boolean> visited = new HashMap<>();
    for (MatrixPosition node: nodes) {
      visited.put(node, false);
    }
    Map<MatrixPosition, Integer> distance = new HashMap<>();
    for (MatrixPosition node: nodes) {
      distance.put(node, 0);
    }
    List<MatrixPosition> queue = new ArrayList<>();
    queue.add(game.getStartPosition());
    while (queue.size() > 0) {
      MatrixPosition curr = queue.remove(0);
      if (visited.get(curr)) {
        continue;
      }
      visited.replace(curr, true);
      for (MatrixPosition neighbour : adjacencyList.get(curr)) {
        queue.add(neighbour);
        distance.replace(neighbour, distance.get(curr) + 1);
      }
    }
    Assert.assertTrue(distance.get(game.getEndPosition()) > 5);
  }

  private Game generateRandomDungeon() {
    int m = randomizer.getIntBetween(4, 30);
    int n = randomizer.getIntBetween(4, 30);
    boolean wrap = randomizer.getIntBetween(1, 2) == 1;
    int interconnectivity = randomizer.getIntBetween(0, m * n / 2 - 1);
    return new DungeonGame(m, n, wrap, interconnectivity);
  }

  private Map<MatrixPosition, List<MatrixPosition>> generateAdjacencyList(
      List<MatrixPosition> nodes, List<List<MatrixPosition>> connections
  ) {
    Map<MatrixPosition, List<MatrixPosition>> adjacencyList = new HashMap<>();
    for (MatrixPosition node: nodes) {
      adjacencyList.put(node, new ArrayList<>());
    }
    for (List<MatrixPosition> edge: connections) {
      adjacencyList.get(edge.get(0)).add(edge.get(1));
    }
    return adjacencyList;
  }
}