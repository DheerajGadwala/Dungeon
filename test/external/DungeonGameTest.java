package external;

import static general.Direction.*;
import static general.Item.CROOKED_ARROW;
import static general.Treasure.DIAMOND;
import static general.Treasure.RUBY;
import static general.Treasure.SAPPHIRE;
import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;
import static junit.framework.TestCase.assertFalse;

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

  private Game sampleGame1;
  private Game sampleGame3;
  private Randomizer randomizer;

  /**
   * Setup for this test suite.
   */
  @Before
  public void setUp() {
    randomizer = new ActualRandomizer();
    sampleGame1 = new DungeonGame(10, 10, 0, 1, false, 0);
    sampleGame3 = new DungeonGame(
        5, 5, 60, 1, false, 2,
        28,19,18,32,47,31,48,58,8,52,46,42,3,39,38,28,19,34,26,35,20,28,25,22,
        20,0,22,0,16,16,19,12,3,5,8,20,6,2,5,1,2,2,7,2,3,1,10,3,2,1,12,3,1,1,10,3,3,1,8,
        1,1,1,0,3,2,3,3,3,2,1,2,2,1,3,3,5,20,2,4,5,12,3,14,5,3,2,8,3,3,3,4,1,9,3,5,2,13,
        2,9,5,10,4,1,3
    );
    sampleGame3.createPlayer();
  }

  /**
   * Test if creating player more than once throws an exception.
   */
  @Test(expected = IllegalStateException.class)
  public void testCreatePlayer1() {
    try {
      sampleGame1.createPlayer();
    }
    catch (IllegalStateException ignored) {
    }
    sampleGame1.createPlayer();
  }

  /**
   * Test if exception is thrown when movePlayer is called but player has not yet been created.
   */
  @Test(expected = IllegalStateException.class)
  public void testMovePlayerException() {
    Game game = new DungeonGame(10, 10, 0, 1, false, 0);
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
    Game game = new DungeonGame(6, 7, 0, 4, true, 0,
        110,88,156,35,131,111,59,147,35,104,17,132,140,82,0,37,95,107,24,
        123,11,65,99,53,60,101,51,28,104,71,13,100,67,44,91,29,56,72,11,25,51,54,
        12,6,75,0,11,70,40,28,66,10,39,6,5,36,53,24,26,43,24,9,33,30,12,7,14,14,2
    );
    game.createPlayer();
    assertEquals(
        "[************][************][************][************][************][************][************][************][************]\n"
            + "[************]      ||                          ||                                        ||                    [************]\n"
            + "[************]      ||                          ||                                        ||                    [************]\n"
            + "[************]-- ~~~~~~~O ---- ~~~~~~~O      ~~~~~~~O      ~~~~~~~O      ~~~~~+~~ ---- ~~~~~+~~      E~M~~~~O --[************]\n"
            + "[************]      ||                                        ||            ||                                  [************]\n"
            + "[************]      ||                                        ||            ||                                  [************]\n"
            + "[************]      ||                                        ||            ||                                  [************]\n"
            + "[************]      ||                                        ||            ||                                  [************]\n"
            + "[************]   ~~~~~+~~ ---- ~~~~~~~O      ~~~~~+~~ ---- ~~~~~~~O      ~~M~~~~O ---- ~~~~~~~O      ~S~P~~~O   [************]\n"
            + "[************]                                  ||            ||            ||                          ||      [************]\n"
            + "[************]                                  ||            ||            ||                          ||      [************]\n"
            + "[************]                                  ||            ||            ||                          ||      [************]\n"
            + "[************]                                  ||            ||            ||                          ||      [************]\n"
            + "[************]   ~~~~~~~O      ~~~~~+~~ ---- ~~~~~+~~      ~~~~~+~~      ~~~~~+~~      ~~~~~~~O      ~~~~~+~~   [************]\n"
            + "[************]      ||            ||                          ||            ||            ||            ||      [************]\n"
            + "[************]      ||            ||                          ||            ||            ||            ||      [************]\n"
            + "[************]      ||            ||                          ||            ||            ||            ||      [************]\n"
            + "[************]      ||            ||                          ||            ||            ||            ||      [************]\n"
            + "[************]   ~~~~~+~~      ~~~~~+~~      ~~~~~+~~ ---- ~~~~~+~~      ~~~~~~~O      ~~~~~+~~ ---- ~~M~~~~O   [************]\n"
            + "[************]      ||            ||            ||                                                      ||      [************]\n"
            + "[************]      ||            ||            ||                                                      ||      [************]\n"
            + "[************]      ||            ||            ||                                                      ||      [************]\n"
            + "[************]      ||            ||            ||                                                      ||      [************]\n"
            + "[************]   ~~~~~~~O ---- ~~~~~+~~      ~~~~~+~~ ---- ~~~~~+~~      ~~~~~~~O ---- ~~~~~+~~      ~~~~~+~~   [************]\n"
            + "[************]      ||                                        ||                          ||            ||      [************]\n"
            + "[************]      ||                                        ||                          ||            ||      [************]\n"
            + "[************]      ||                                        ||                          ||            ||      [************]\n"
            + "[************]      ||                                        ||                          ||            ||      [************]\n"
            + "[************]-- ~~~~~~~O ---- ~~M~~~~O      ~~~~~+~~ ---- ~~~~~~~O ---- ~~~~~+~~ ---- ~~~~~~~O      ~~~~~+~~ --[************]\n"
            + "[************]      ||                          ||                                        ||                    [************]\n"
            + "[************]      ||                          ||                                        ||                    [************]\n"
            + "[************][************][************][************][************][************][************][************][************]\n",
        game.toString()
    );
  }

  /**
   * Test non-wrapping dungeon generation using pseudo random input.
   * Will create a non-wrapping dungeon when given a valid set of random numbers.
   * This also shows that the start-end distance is greater than 5 in this pseudo random dungeon.
   * This also shows that all nodes are connected in this pseudo random dungeon.
   */
  @Test
  public void testNonWrappingDungeon() {
    Game game = new DungeonGame(6, 7, 0, 1, false, 0,
        124,27,52,63,114,103,121,1,61,81,41,48,52,61,30,6,
        108,58,100,60,61,18,30,80,30,13,5,16,22,22,37,70,68,30,37,
        70,2,50,37,42,37,59,47,33,31,9,28,23,38,33,11,21,3,20
    );
    game.createPlayer();
    assertEquals(
        "[************][************][************][************][************][************][************][************][************]\n"
            + "[************]                                                                                                  [************]\n"
            + "[************]                                                                                                  [************]\n"
            + "[************]   ~~~~~+~~ ---- ~~~~~~~O ---- ~~~~~~~O      ~S~P~~~O ---- ~~~~~+~~ ---- ~~~~~+~~      ~~~~~~~O   [************]\n"
            + "[************]      ||            ||                                                      ||            ||      [************]\n"
            + "[************]      ||            ||                                                      ||            ||      [************]\n"
            + "[************]      ||            ||                                                      ||            ||      [************]\n"
            + "[************]      ||            ||                                                      ||            ||      [************]\n"
            + "[************]   ~~~~~+~~      ~~~~~+~~ ---- ~~~~~~~O ---- E~M~~~~O      ~~~~~~~O ---- ~~~~~~~O      ~~~~~+~~   [************]\n"
            + "[************]      ||                          ||                                        ||            ||      [************]\n"
            + "[************]      ||                          ||                                        ||            ||      [************]\n"
            + "[************]      ||                          ||                                        ||            ||      [************]\n"
            + "[************]      ||                          ||                                        ||            ||      [************]\n"
            + "[************]   ~~~~~~~O      ~~~~~+~~ ---- ~~~~~~~O      ~~~~~+~~ ---- ~~~~~~~O ---- ~~~~~~~O ---- ~~~~~+~~   [************]\n"
            + "[************]                    ||            ||            ||            ||            ||                    [************]\n"
            + "[************]                    ||            ||            ||            ||            ||                    [************]\n"
            + "[************]                    ||            ||            ||            ||            ||                    [************]\n"
            + "[************]                    ||            ||            ||            ||            ||                    [************]\n"
            + "[************]   ~~~~~+~~ ---- ~~~~~+~~      ~~~~~~~O ---- ~~~~~~~O      ~~~~~+~~      ~~~~~~~O ---- ~~~~~+~~   [************]\n"
            + "[************]      ||                          ||            ||            ||            ||            ||      [************]\n"
            + "[************]      ||                          ||            ||            ||            ||            ||      [************]\n"
            + "[************]      ||                          ||            ||            ||            ||            ||      [************]\n"
            + "[************]      ||                          ||            ||            ||            ||            ||      [************]\n"
            + "[************]   ~~~~~+~~      ~~~~~~~O      ~~~~~~~O      ~~~~~+~~      ~~~~~~~O      ~~~~~~~O      ~~~~~+~~   [************]\n"
            + "[************]      ||            ||                          ||                                        ||      [************]\n"
            + "[************]      ||            ||                          ||                                        ||      [************]\n"
            + "[************]      ||            ||                          ||                                        ||      [************]\n"
            + "[************]      ||            ||                          ||                                        ||      [************]\n"
            + "[************]   ~~~~~+~~ ---- ~~~~~+~~      ~~~~~~~O ---- ~~~~~+~~      ~~~~~~~O ---- ~~~~~+~~ ---- ~~~~~+~~   [************]\n"
            + "[************]                                                                                                  [************]\n"
            + "[************]                                                                                                  [************]\n"
            + "[************][************][************][************][************][************][************][************][************]\n",
        game.toString());
  }

  /**
   * Test showing map when interconnectivity is not zero.
   */
  @Test
  public void testInterConnectivityNonZero() {
    Game game = new DungeonGame(5, 7, 60, 4, true, 2,
        41,99,32,91,41,56,64,8,94,7,106,35,26,56,70,41,68,46,93,83,98,65,73,34,72,13,
        4,29,7,5,31,58,42,5,50,15,14,2,53,56,9,3,23,4,18,17,18,0,1,3,1,11,2,3,1,7,3,3,1,8,2,3,
        1,3,2,2,2,10,1,1,1,12,2,3,3,3,1,3,3,3,1,2,2,2,2,3,3,5,1,1,2,2,1,3,2,28,4,32,1,4,2,3,3,
        7,3,17,5,6,3,17,2,7,5,5,5,11,3,18,1,13,1,16,5,7,1,7,5,11,5,11,2,8,1,8,2,14,2,3,16,2
    );
    game.createPlayer();
    assertEquals(
        "[************][************][************][************][************][************][************][************][************]\n"
            + "[************]      ||                          ||                                                      ||      [************]\n"
            + "[************]      ||                          ||                                                      ||      [************]\n"
            + "[************]-- ~~~~~+~~      ~~M~~~TO      ~~~~~~TO ---- ~~~~I+~~ ---- ~~~~I+~~      ~~~~~~TO      ~~~~~+~~ --[************]\n"
            + "[************]                    ||            ||                          ||            ||                    [************]\n"
            + "[************]                    ||            ||                          ||            ||                    [************]\n"
            + "[************]                    ||            ||                          ||            ||                    [************]\n"
            + "[************]                    ||            ||                          ||            ||                    [************]\n"
            + "[************]-- ~~~~I~~O ---- ~~M~I~~O      ~~~~I~~O      ~~~~~+~~ ---- ~~~~I~TO      ~~~~~+~~ ---- ~~~~I~~O --[************]\n"
            + "[************]      ||            ||                          ||            ||                          ||      [************]\n"
            + "[************]      ||            ||                          ||            ||                          ||      [************]\n"
            + "[************]      ||            ||                          ||            ||                          ||      [************]\n"
            + "[************]      ||            ||                          ||            ||                          ||      [************]\n"
            + "[************]   ~~~~I~~O      ~~~~~+~~      ~~~~I~~O ---- ~~~~I+~~      ~~~~I+~~ ---- ~S~P~~TO      ~~~~I+~~   [************]\n"
            + "[************]                    ||                                                                    ||      [************]\n"
            + "[************]                    ||                                                                    ||      [************]\n"
            + "[************]                    ||                                                                    ||      [************]\n"
            + "[************]                    ||                                                                    ||      [************]\n"
            + "[************]   ~~~~I~TO ---- ~~~~I~TO ---- ~~~~I~TO ---- ~~~~I+~~ ---- ~~~~~+~~ ---- ~~~~I~TO ---- ~~~~I~~O   [************]\n"
            + "[************]                    ||            ||                                        ||            ||      [************]\n"
            + "[************]                    ||            ||                                        ||            ||      [************]\n"
            + "[************]                    ||            ||                                        ||            ||      [************]\n"
            + "[************]                    ||            ||                                        ||            ||      [************]\n"
            + "[************]-- ~~~~I+~~      ~~~~~~TO      ~~~~~~~O ---- ~~~~~+~~ ---- ~~~~~~TO      ~~M~I~~O      E~M~I~TO --[************]\n"
            + "[************]      ||                          ||                                                      ||      [************]\n"
            + "[************]      ||                          ||                                                      ||      [************]\n"
            + "[************][************][************][************][************][************][************][************][************]\n",
        game.toString());
  }

  /**
   * Testing showing map when interconnectivity is zero.
   */
  @Test
  public void testInterconnectivityZero() {
    Game game = new DungeonGame(5, 7, 60, 3, true, 0,
        102,15,133,43,127,16,61,26,62,91,8,102,72,87,37,46,17,81,5,57,57,71,28,66,47,
        8,84,73,1,73,22,72,64,29,21,59,43,48,59,8,30,13,11,11,17,9,8,8,9,26,26,12,2,3,1,10,2,
        2,3,2,1,2,3,8,2,2,1,8,1,1,1,11,2,3,2,5,1,2,1,6,3,3,2,5,1,3,3,2,2,2,2,5,4,22,5,32,2,29,
        4,28,1,10,2,14,4,11,2,8,4,12,4,24,5,18,2,0,1,4,4,16,5,9,3,2,1,5,3,15,4,10,3,12,3,8,11
    );
    game.createPlayer();
    assertEquals(
        "[************][************][************][************][************][************][************][************][************]\n"
            + "[************]      ||            ||            ||            ||                          ||                    [************]\n"
            + "[************]      ||            ||            ||            ||                          ||                    [************]\n"
            + "[************]   ~~~~I+~~ ---- ~~~~~~TO ---- ~~M~~~TO ---- ~~~~I+~~      ~~~~~~~O      ~~~~I~TO ---- ~~~~I~~O   [************]\n"
            + "[************]                                                              ||            ||                    [************]\n"
            + "[************]                                                              ||            ||                    [************]\n"
            + "[************]                                                              ||            ||                    [************]\n"
            + "[************]                                                              ||            ||                    [************]\n"
            + "[************]   ~~~~~+~~ ---- ~~~~~+~~ ---- ~~~~I+~~ ---- ~~~~I+~~      ~~~~I~~O ---- ~~~~~+~~      ~~~~I~TO   [************]\n"
            + "[************]      ||                                        ||            ||                          ||      [************]\n"
            + "[************]      ||                                        ||            ||                          ||      [************]\n"
            + "[************]      ||                                        ||            ||                          ||      [************]\n"
            + "[************]      ||                                        ||            ||                          ||      [************]\n"
            + "[************]   ~~~~~+~~      ~~~~I+~~ ---- ~~~~I~~O      E~M~I~~O      ~~~~~+~~ ---- ~~~~~~TO ---- ~~~~~+~~   [************]\n"
            + "[************]      ||            ||                                                      ||                    [************]\n"
            + "[************]      ||            ||                                                      ||                    [************]\n"
            + "[************]      ||            ||                                                      ||                    [************]\n"
            + "[************]      ||            ||                                                      ||                    [************]\n"
            + "[************]   ~~~~I+~~      ~~~~~+~~      ~~~~I~TO      ~~~~~~~O      ~~~~I+~~ ---- ~~~~I~TO ---- ~~~~I~TO   [************]\n"
            + "[************]      ||            ||            ||            ||            ||                                  [************]\n"
            + "[************]      ||            ||            ||            ||            ||                                  [************]\n"
            + "[************]      ||            ||            ||            ||            ||                                  [************]\n"
            + "[************]      ||            ||            ||            ||            ||                                  [************]\n"
            + "[************]-- ~~~~~~TO      ~~~~I+~~      ~~~~I+~~      ~~M~I~~O ---- ~~~~~+~~      ~S~PI~TO      ~~~~I~~O --[************]\n"
            + "[************]      ||            ||            ||            ||                          ||                    [************]\n"
            + "[************]      ||            ||            ||            ||                          ||                    [************]\n"
            + "[************][************][************][************][************][************][************][************][************]\n",
        game.toString());
  }

  /**
   * When interconnectivity is too high.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testInvalidInterconnectivity() {
    new DungeonGame(6, 8, 0, 1, true, 200);
  }

  /**
   * Test invalid dimensions in dungeon generation.
   * m<0.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testInvalidDungeonGameArguments1() {
    new DungeonGame(-5, 8, 0, 1, true, 200);
  }

  /**
   * Test invalid dimensions in dungeon generation.
   * n<0.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testInvalidDungeonGameArguments2() {
    new DungeonGame(6, -8, 0, 1, true, 200);
  }

  /**
   * Test invalid dimensions in dungeon generation.
   * m+n-2<=5.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testInvalidDungeonGameArguments3() {
    new DungeonGame(3, 3, 0, 1, true, 200);
  }

  /**
   * Test location of the player when he is created.
   * Asserting on the map, because S and P are on the same location.
   * The map also shows that there is no monster at the start location
   * represented by an S.
   * The map also shows that there is a monster at the end location
   * represented by an E.
   */
  @Test
  public void testPlayerLocationAtStart() {
    Game game = new DungeonGame(5, 5, 20, 1, false, 3,
        7,71,58,44,39,62,22,9,42,1,58,54,5,46,16,48,36,11,17,
        15,36,33,26,15,22,16,25,21,10,7,8,15,16,17,1,9,5,14,17,0,4,
        16,2,12,6,7,2,4,1,1,1,7,3,2,2,9,2,1,3,18,5,1,3,11,5,14,5,15,2
    );
    game.createPlayer();
    assertEquals(
        "[************][************][************][************][************][************][************]\n"
        + "[************]                                                                      [************]\n"
        + "[************]                                                                      [************]\n"
        + "[************]   ~~~~~~~O      ~~~~I+~~ ---- E~M~~~~O ---- ~~~~~~~O ---- ~~~~~~TO   [************]\n"
        + "[************]      ||            ||            ||            ||                    [************]\n"
        + "[************]      ||            ||            ||            ||                    [************]\n"
        + "[************]      ||            ||            ||            ||                    [************]\n"
        + "[************]      ||            ||            ||            ||                    [************]\n"
        + "[************]   ~~~~~+~~      ~~~~~+~~      ~~~~~+~~ ---- ~~~~~~~O ---- ~~~~~~TO   [************]\n"
        + "[************]      ||            ||                          ||                    [************]\n"
        + "[************]      ||            ||                          ||                    [************]\n"
        + "[************]      ||            ||                          ||                    [************]\n"
        + "[************]      ||            ||                          ||                    [************]\n"
        + "[************]   ~~~~~~~O ---- ~~~~~~~O ---- ~~~~I~~O      ~~~~~+~~      ~S~P~~~O   [************]\n"
        + "[************]      ||            ||                          ||            ||      [************]\n"
        + "[************]      ||            ||                          ||            ||      [************]\n"
        + "[************]      ||            ||                          ||            ||      [************]\n"
        + "[************]      ||            ||                          ||            ||      [************]\n"
        + "[************]   ~~~~~~~O ---- ~~~~I~~O      ~~~~~~TO      ~~~~I+~~ ---- ~~~~I~~O   [************]\n"
        + "[************]      ||            ||            ||                          ||      [************]\n"
        + "[************]      ||            ||            ||                          ||      [************]\n"
        + "[************]      ||            ||            ||                          ||      [************]\n"
        + "[************]      ||            ||            ||                          ||      [************]\n"
        + "[************]   ~~~~~~~O      ~~~~~+~~ ---- ~~~~~~~O ---- ~~~~~+~~ ---- ~~~~~+~~   [************]\n"
        + "[************]                                                                      [************]\n"
        + "[************]                                                                      [************]\n"
        + "[************][************][************][************][************][************][************]\n",
        game.toString()
    );
  }

  /**
   * Test that an end location is decided on dungeon
   * creation and it is possible for the player to reach it.
   * A wins the game when he reaches the end location and is
   * still alive/ not eaten by monsters. Refer to the map
   * to check the path.
   * Also tests that game is over when player reaches end.
   * Asserting that the game is not over until player reaches end.
   * Asserting that the player has not won until the game is over and
   * player has reached the end alive.
   */
  @Test
  public void testPlayerAtEndLocation() {
    Game game = new DungeonGame(
        5, 5, 0, 1, false, 2,
        40,4,17,20,59,29,45,53,12,56,53,2,48,50,
        10,11,9,10,40,39,20,29,27,25,27,21,6,16,22,2,5,1
    );
    game.createPlayer();
    assertEquals(
        "[************][************][************][************][************][************][************]\n"
            + "[************]                                                                      [************]\n"
            + "[************]                                                                      [************]\n"
            + "[************]   ~~~~~~~O ---- ~~~~~+~~      ~~~~~~~O ---- ~~~~~~~O ---- ~~~~~~~O   [************]\n"
            + "[************]                    ||                          ||                    [************]\n"
            + "[************]                    ||                          ||                    [************]\n"
            + "[************]                    ||                          ||                    [************]\n"
            + "[************]                    ||                          ||                    [************]\n"
            + "[************]   ~S~P~~~O ---- ~~~~~~~O ---- ~~~~~+~~ ---- ~~~~~+~~      ~~~~~~~O   [************]\n"
            + "[************]                    ||                                        ||      [************]\n"
            + "[************]                    ||                                        ||      [************]\n"
            + "[************]                    ||                                        ||      [************]\n"
            + "[************]                    ||                                        ||      [************]\n"
            + "[************]   ~~~~~+~~ ---- ~~~~~~~O ---- ~~~~~~~O ---- ~~~~~+~~ ---- ~~~~~+~~   [************]\n"
            + "[************]      ||                          ||                                  [************]\n"
            + "[************]      ||                          ||                                  [************]\n"
            + "[************]      ||                          ||                                  [************]\n"
            + "[************]      ||                          ||                                  [************]\n"
            + "[************]   ~~~~~~~O ---- ~~~~~+~~      ~~~~~~~O ---- ~~~~~+~~ ---- E~M~~~~O   [************]\n"
            + "[************]      ||            ||            ||                                  [************]\n"
            + "[************]      ||            ||            ||                                  [************]\n"
            + "[************]      ||            ||            ||                                  [************]\n"
            + "[************]      ||            ||            ||                                  [************]\n"
            + "[************]   ~~~~~+~~ ---- ~~~~~~~O ---- ~~~~~~~O ---- ~~~~~+~~ ---- ~~~~~~~O   [************]\n"
            + "[************]                                                                      [************]\n"
            + "[************]                                                                      [************]\n"
            + "[************][************][************][************][************][************][************]\n",
        game.toString());
    assertFalse(game.isGameOver());
    assertFalse(game.hasPlayerWon());
    game.movePlayer(EAST);
    assertFalse(game.isGameOver());
    assertFalse(game.hasPlayerWon());
    game.movePlayer(SOUTH);
    assertFalse(game.isGameOver());
    assertFalse(game.hasPlayerWon());
    game.movePlayer(EAST);
    assertFalse(game.isGameOver());
    assertFalse(game.hasPlayerWon());
    game.movePlayer(SOUTH);
    assertFalse(game.isGameOver());
    assertFalse(game.hasPlayerWon());
    game.movePlayer(EAST);
    assertFalse(game.isGameOver());
    assertFalse(game.hasPlayerWon());
    game.shootArrow(EAST, 1);
    assertFalse(game.isGameOver());
    assertFalse(game.hasPlayerWon());
    game.shootArrow(EAST, 1);
    assertFalse(game.isGameOver());
    assertFalse(game.hasPlayerWon());
    game.movePlayer(EAST);
    assertTrue(game.isGameOver());
    assertTrue(game.hasPlayerWon());
  }

  /**
   * Test isGameOver at every step until player dies.
   * game is over when player dies.
   * also asserting that player does not win when dies
   * even if the game is over.
   */
  @Test
  public void isGameOverOnPlayerDeath() {
    Game game = new DungeonGame(
        5, 4, 0, 4, false, 2,
        6,37,21,9,48,33,30,30,16,28,23,10,9,10,28,6,27,
        14,6,10,16,6,4,9,14,0,9,12,2,1,4,5,6,6,1,2,2,2,2,0,3,1,4
    );
    game.createPlayer();
    assertEquals(
        "[************][************][************][************][************][************]\n"
            + "[************]                                                        [************]\n"
            + "[************]                                                        [************]\n"
            + "[************]   ~~M~~~~O      ~~~~~~~O      ~~~~~~~O      ~~M~~~~O   [************]\n"
            + "[************]      ||            ||            ||            ||      [************]\n"
            + "[************]      ||            ||            ||            ||      [************]\n"
            + "[************]      ||            ||            ||            ||      [************]\n"
            + "[************]      ||            ||            ||            ||      [************]\n"
            + "[************]   ~~~~~~~O ---- ~~~~~~~O ---- ~~~~~~~O ---- ~~~~~+~~   [************]\n"
            + "[************]      ||                          ||                    [************]\n"
            + "[************]      ||                          ||                    [************]\n"
            + "[************]      ||                          ||                    [************]\n"
            + "[************]      ||                          ||                    [************]\n"
            + "[************]   ~~~~~+~~ ---- E~M~~~~O ---- ~~M~~~~O ---- ~~~~~+~~   [************]\n"
            + "[************]                    ||            ||            ||      [************]\n"
            + "[************]                    ||            ||            ||      [************]\n"
            + "[************]                    ||            ||            ||      [************]\n"
            + "[************]                    ||            ||            ||      [************]\n"
            + "[************]   ~~~~~+~~ ---- ~~~~~~~O ---- ~~~~~+~~      ~~~~~~~O   [************]\n"
            + "[************]      ||                                                [************]\n"
            + "[************]      ||                                                [************]\n"
            + "[************]      ||                                                [************]\n"
            + "[************]      ||                                                [************]\n"
            + "[************]   ~~~~~+~~ ---- ~~~~~+~~ ---- ~~~~~+~~ ---- ~S~P~~~O   [************]\n"
            + "[************]                                                        [************]\n"
            + "[************]                                                        [************]\n"
            + "[************][************][************][************][************][************]\n",
        game.toString()
    );
    assertFalse(game.isGameOver());
    assertFalse(game.hasPlayerWon());
    game.movePlayer(WEST);
    assertFalse(game.isGameOver());
    assertFalse(game.hasPlayerWon());
    game.movePlayer(WEST);
    assertFalse(game.isGameOver());
    assertFalse(game.hasPlayerWon());
    game.movePlayer(WEST);
    assertFalse(game.isGameOver());
    assertFalse(game.hasPlayerWon());
    game.movePlayer(NORTH);
    assertFalse(game.isGameOver());
    assertFalse(game.hasPlayerWon());
    game.movePlayer(EAST);
    assertFalse(game.isGameOver());
    assertFalse(game.hasPlayerWon());
    game.movePlayer(EAST);
    assertFalse(game.isGameOver());
    assertFalse(game.hasPlayerWon());
    game.movePlayer(NORTH);
    assertTrue(game.isGameOver());
    assertFalse(game.hasPlayerWon());
  }

  /**
   * Test if player description is as expected.
   */
  @Test
  public void testPlayerDescription() {
    sampleGame3.cedeItem(CROOKED_ARROW);
    sampleGame3.cedeItem(CROOKED_ARROW);
    sampleGame3.movePlayer(NORTH);
    sampleGame3.cedeTreasure(DIAMOND);
    sampleGame3.cedeTreasure(RUBY);
    sampleGame3.movePlayer(EAST);
    sampleGame3.movePlayer(SOUTH);
    sampleGame3.movePlayer(SOUTH);
    sampleGame3.cedeTreasure(DIAMOND);
    sampleGame3.cedeTreasure(SAPPHIRE);
    assertEquals(
        "Player description:\n"
            + "Treasure:\n"
            + " diamond - 2\n"
            + " ruby - 1\n"
            + " sapphire - 1\n"
            + "Items:\n"
            + " bow - 1\n"
            + " crooked arrow - 5\n",
        sampleGame3.getPlayerDescription()
    );
  }

  /**
   * Test if description of the player's location is as expected.
   * Player in a tunnel without items.
   */
  @Test
  public void testPlayerLocationDescription1() {
    sampleGame3.movePlayer(NORTH);
    sampleGame3.movePlayer(EAST);
    assertEquals(
        "This is a tunnel\n"
            + "You see paths in the following directions: S W \n",
        sampleGame3.getPlayerLocationDescription()
    );
  }

  /**
   * Test if description of the player's location is as expected.
   * Player in a tunnel with items.
   */
  @Test
  public void testPlayerLocationDescription2() {
    sampleGame3.movePlayer(NORTH);
    sampleGame3.movePlayer(WEST);
    sampleGame3.movePlayer(SOUTH);
    assertEquals(
        "This is a tunnel\n"
            + "You see paths in the following directions: N S \n"
            + "There are some items in this cave: 5 crooked arrows \n",
        sampleGame3.getPlayerLocationDescription()
    );
  }

  /**
   * Test if description of the player's location is as expected.
   * Player in a cave with treasure.
   */
  @Test
  public void testPlayerLocationDescription3() {
    sampleGame3.cedeItem(CROOKED_ARROW);
    sampleGame3.cedeItem(CROOKED_ARROW);
    sampleGame3.movePlayer(NORTH);
    sampleGame3.cedeTreasure(DIAMOND);
    sampleGame3.cedeTreasure(RUBY);
    sampleGame3.movePlayer(EAST);
    sampleGame3.movePlayer(SOUTH);
    sampleGame3.movePlayer(SOUTH);
    assertEquals(
        "This is a cave\n"
            + "You see paths in the following directions: N E W \n"
            + "Looks like there's some treasure in this cave: 2 diamonds 3 rubies 1 sapphire \n",
        sampleGame3.getPlayerLocationDescription()
    );
  }

  /**
   * Test if description of the player's location is as expected.
   * Player in a cave without treasure and items.
   */
  @Test
  public void testPlayerLocationDescription4() {
    sampleGame3.movePlayer(NORTH);
    sampleGame3.movePlayer(EAST);
    sampleGame3.movePlayer(SOUTH);
    assertEquals(
        "This is a cave\n"
            + "You see paths in the following directions: N E S \n",
        sampleGame3.getPlayerLocationDescription()
    );
  }

  /**
   * Test showing player collects treasure from his location.
   * First we assert the treasure the player has,
   * Then we assert the treasure at the location,
   * Then we make the player collect the treasure and assert his total treasure
   * and treasure at his location.
   */
  @Test
  public void testPlayerCollectingTreasure() {
    sampleGame3.movePlayer(NORTH);
    assertEquals(
        "Player description:\n"
        + "Treasure:\n"
        + " diamond - 0\n"
        + " ruby - 0\n"
        + " sapphire - 0\n"
        + "Items:\n"
        + " bow - 1\n"
        + " crooked arrow - 3\n",
        sampleGame3.getPlayerDescription()
    );
    assertEquals(
        "This is a cave\n"
        + "You see paths in the following directions: E S W \n"
        + "Looks like there's some treasure in this cave: 3 diamonds 2 rubies 3 sapphires \n"
        + "There are some items in this cave: 3 crooked arrows \n",
        sampleGame3.getPlayerLocationDescription()
    );
    sampleGame3.cedeTreasure(SAPPHIRE);
    sampleGame3.cedeTreasure(SAPPHIRE);
    sampleGame3.cedeTreasure(DIAMOND);
    assertEquals(
        "Player description:\n"
            + "Treasure:\n"
            + " diamond - 1\n"
            + " ruby - 0\n"
            + " sapphire - 2\n"
            + "Items:\n"
            + " bow - 1\n"
            + " crooked arrow - 3\n",
        sampleGame3.getPlayerDescription()
    );
    assertEquals(
        "This is a cave\n"
            + "You see paths in the following directions: E S W \n"
            + "Looks like there's some treasure in this cave: 2 diamonds 2 rubies 1 sapphire \n"
            + "There are some items in this cave: 3 crooked arrows \n",
        sampleGame3.getPlayerLocationDescription()
    );
  }

  /**
   * Test showing player collects items from his location.
   * First we assert the items the player has,
   * Then we assert the items at the location,
   * Then we make the player collect the items and assert his total items
   * and items at his location.
   */
  @Test
  public void testPlayerCollectingItems() {
    assertEquals(
        "This is a cave\n"
            + "You see paths in the following directions: N \n"
            + "There are some items in this cave: 3 crooked arrows \n",
        sampleGame3.getPlayerLocationDescription()
    );
    assertEquals(
        "Player description:\n"
            + "Treasure:\n"
            + " diamond - 0\n"
            + " ruby - 0\n"
            + " sapphire - 0\n"
            + "Items:\n"
            + " bow - 1\n"
            + " crooked arrow - 3\n",
        sampleGame3.getPlayerDescription()
    );
    sampleGame3.cedeItem(CROOKED_ARROW);
    sampleGame3.cedeItem(CROOKED_ARROW);
    assertEquals(
        "This is a cave\n"
            + "You see paths in the following directions: N \n"
            + "There are some items in this cave: 1 crooked arrow \n",
        sampleGame3.getPlayerLocationDescription()
    );
    assertEquals(
        "Player description:\n"
            + "Treasure:\n"
            + " diamond - 0\n"
            + " ruby - 0\n"
            + " sapphire - 0\n"
            + "Items:\n"
            + " bow - 1\n"
            + " crooked arrow - 5\n",
        sampleGame3.getPlayerDescription()
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
    Assert.assertEquals(
        (int) (countCaves * game.getPercentage() / 100.0),
        countTreasureCaves, 1);
  }

  /**
   * Test if negative percentage on generateTreasure method throws an exception as expected.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testAddTreasureException1() {
    new DungeonGame(10, 10, -20, 1, true, 3);
  }

  /**
   * Test if percentage greater than 100 on generateTreasure method throws an exception as expected.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testAddTreasureException2() {
    new DungeonGame(10, 10, 120, 1, true, 3);
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
    game.getPlayerDescription();
  }

  /**
   * Test that moving player in any direction changes his location.
   * P represents the player.
   * The dungeon state gets updated hence the map has the player's new location.
   * We move him in all 4 directions and check the dungeon map.
   */
  @Test
  public void testDirectionsOnPlayer() {
    assertEquals(
        "[************][************][************][************][************][************][************]\n"
            + "[************]                                                                      [************]\n"
            + "[************]                                                                      [************]\n"
            + "[************]   ~~~~~+~~ ---- ~~~~I~TO ---- ~~~~~+~~      ~~~~I~TO      ~~~~I~TO   [************]\n"
            + "[************]      ||            ||            ||            ||            ||      [************]\n"
            + "[************]      ||            ||            ||            ||            ||      [************]\n"
            + "[************]      ||            ||            ||            ||            ||      [************]\n"
            + "[************]      ||            ||            ||            ||            ||      [************]\n"
            + "[************]   ~~~~I+~~      ~S~PI~~O      ~~~~~~~O ---- ~~~~I~~O ---- ~~~~~+~~   [************]\n"
            + "[************]      ||                          ||                                  [************]\n"
            + "[************]      ||                          ||                                  [************]\n"
            + "[************]      ||                          ||                                  [************]\n"
            + "[************]      ||                          ||                                  [************]\n"
            + "[************]   ~~~~I+~~      ~~~~I+~~ ---- ~~~~~~TO ---- ~~~~~+~~      ~~~~I~TO   [************]\n"
            + "[************]      ||            ||                          ||            ||      [************]\n"
            + "[************]      ||            ||                          ||            ||      [************]\n"
            + "[************]      ||            ||                          ||            ||      [************]\n"
            + "[************]      ||            ||                          ||            ||      [************]\n"
            + "[************]   ~~~~~+~~ ---- E~M~I~TO ---- ~~~~I~TO ---- ~~~~~~~O ---- ~~~~I~~O   [************]\n"
            + "[************]                    ||            ||            ||            ||      [************]\n"
            + "[************]                    ||            ||            ||            ||      [************]\n"
            + "[************]                    ||            ||            ||            ||      [************]\n"
            + "[************]                    ||            ||            ||            ||      [************]\n"
            + "[************]   ~~~~~~~O ---- ~~~~I+~~      ~~~~I~~O      ~~~~~~TO      ~~~~I~TO   [************]\n"
            + "[************]                                                                      [************]\n"
            + "[************]                                                                      [************]\n"
            + "[************][************][************][************][************][************][************]\n",
        sampleGame3.toString()
    );
    sampleGame3.movePlayer(NORTH);
    assertEquals(
        "[************][************][************][************][************][************][************]\n"
            + "[************]                                                                      [************]\n"
            + "[************]                                                                      [************]\n"
            + "[************]   ~~~~~+~~ ---- ~~~PI~TO ---- ~~~~~+~~      ~~~~I~TO      ~~~~I~TO   [************]\n"
            + "[************]      ||            ||            ||            ||            ||      [************]\n"
            + "[************]      ||            ||            ||            ||            ||      [************]\n"
            + "[************]      ||            ||            ||            ||            ||      [************]\n"
            + "[************]      ||            ||            ||            ||            ||      [************]\n"
            + "[************]   ~~~~I+~~      ~S~~I~~O      ~~~~~~~O ---- ~~~~I~~O ---- ~~~~~+~~   [************]\n"
            + "[************]      ||                          ||                                  [************]\n"
            + "[************]      ||                          ||                                  [************]\n"
            + "[************]      ||                          ||                                  [************]\n"
            + "[************]      ||                          ||                                  [************]\n"
            + "[************]   ~~~~I+~~      ~~~~I+~~ ---- ~~~~~~TO ---- ~~~~~+~~      ~~~~I~TO   [************]\n"
            + "[************]      ||            ||                          ||            ||      [************]\n"
            + "[************]      ||            ||                          ||            ||      [************]\n"
            + "[************]      ||            ||                          ||            ||      [************]\n"
            + "[************]      ||            ||                          ||            ||      [************]\n"
            + "[************]   ~~~~~+~~ ---- E~M~I~TO ---- ~~~~I~TO ---- ~~~~~~~O ---- ~~~~I~~O   [************]\n"
            + "[************]                    ||            ||            ||            ||      [************]\n"
            + "[************]                    ||            ||            ||            ||      [************]\n"
            + "[************]                    ||            ||            ||            ||      [************]\n"
            + "[************]                    ||            ||            ||            ||      [************]\n"
            + "[************]   ~~~~~~~O ---- ~~~~I+~~      ~~~~I~~O      ~~~~~~TO      ~~~~I~TO   [************]\n"
            + "[************]                                                                      [************]\n"
            + "[************]                                                                      [************]\n"
            + "[************][************][************][************][************][************][************]\n",
        sampleGame3.toString()
    );
    sampleGame3.movePlayer(EAST);
    assertEquals(
        "[************][************][************][************][************][************][************]\n"
            + "[************]                                                                      [************]\n"
            + "[************]                                                                      [************]\n"
            + "[************]   ~~~~~+~~ ---- ~~~~I~TO ---- ~~~P~+~~      ~~~~I~TO      ~~~~I~TO   [************]\n"
            + "[************]      ||            ||            ||            ||            ||      [************]\n"
            + "[************]      ||            ||            ||            ||            ||      [************]\n"
            + "[************]      ||            ||            ||            ||            ||      [************]\n"
            + "[************]      ||            ||            ||            ||            ||      [************]\n"
            + "[************]   ~~~~I+~~      ~S~~I~~O      ~~~~~~~O ---- ~~~~I~~O ---- ~~~~~+~~   [************]\n"
            + "[************]      ||                          ||                                  [************]\n"
            + "[************]      ||                          ||                                  [************]\n"
            + "[************]      ||                          ||                                  [************]\n"
            + "[************]      ||                          ||                                  [************]\n"
            + "[************]   ~~~~I+~~      ~~~~I+~~ ---- ~~~~~~TO ---- ~~~~~+~~      ~~~~I~TO   [************]\n"
            + "[************]      ||            ||                          ||            ||      [************]\n"
            + "[************]      ||            ||                          ||            ||      [************]\n"
            + "[************]      ||            ||                          ||            ||      [************]\n"
            + "[************]      ||            ||                          ||            ||      [************]\n"
            + "[************]   ~~~~~+~~ ---- E~M~I~TO ---- ~~~~I~TO ---- ~~~~~~~O ---- ~~~~I~~O   [************]\n"
            + "[************]                    ||            ||            ||            ||      [************]\n"
            + "[************]                    ||            ||            ||            ||      [************]\n"
            + "[************]                    ||            ||            ||            ||      [************]\n"
            + "[************]                    ||            ||            ||            ||      [************]\n"
            + "[************]   ~~~~~~~O ---- ~~~~I+~~      ~~~~I~~O      ~~~~~~TO      ~~~~I~TO   [************]\n"
            + "[************]                                                                      [************]\n"
            + "[************]                                                                      [************]\n"
            + "[************][************][************][************][************][************][************]\n",
        sampleGame3.toString()
    );
    sampleGame3.movePlayer(SOUTH);
    assertEquals(
        "[************][************][************][************][************][************][************]\n"
            + "[************]                                                                      [************]\n"
            + "[************]                                                                      [************]\n"
            + "[************]   ~~~~~+~~ ---- ~~~~I~TO ---- ~~~~~+~~      ~~~~I~TO      ~~~~I~TO   [************]\n"
            + "[************]      ||            ||            ||            ||            ||      [************]\n"
            + "[************]      ||            ||            ||            ||            ||      [************]\n"
            + "[************]      ||            ||            ||            ||            ||      [************]\n"
            + "[************]      ||            ||            ||            ||            ||      [************]\n"
            + "[************]   ~~~~I+~~      ~S~~I~~O      ~~~P~~~O ---- ~~~~I~~O ---- ~~~~~+~~   [************]\n"
            + "[************]      ||                          ||                                  [************]\n"
            + "[************]      ||                          ||                                  [************]\n"
            + "[************]      ||                          ||                                  [************]\n"
            + "[************]      ||                          ||                                  [************]\n"
            + "[************]   ~~~~I+~~      ~~~~I+~~ ---- ~~~~~~TO ---- ~~~~~+~~      ~~~~I~TO   [************]\n"
            + "[************]      ||            ||                          ||            ||      [************]\n"
            + "[************]      ||            ||                          ||            ||      [************]\n"
            + "[************]      ||            ||                          ||            ||      [************]\n"
            + "[************]      ||            ||                          ||            ||      [************]\n"
            + "[************]   ~~~~~+~~ ---- E~M~I~TO ---- ~~~~I~TO ---- ~~~~~~~O ---- ~~~~I~~O   [************]\n"
            + "[************]                    ||            ||            ||            ||      [************]\n"
            + "[************]                    ||            ||            ||            ||      [************]\n"
            + "[************]                    ||            ||            ||            ||      [************]\n"
            + "[************]                    ||            ||            ||            ||      [************]\n"
            + "[************]   ~~~~~~~O ---- ~~~~I+~~      ~~~~I~~O      ~~~~~~TO      ~~~~I~TO   [************]\n"
            + "[************]                                                                      [************]\n"
            + "[************]                                                                      [************]\n"
            + "[************][************][************][************][************][************][************]\n",
        sampleGame3.toString()
    );
    sampleGame3.movePlayer(SOUTH);
    sampleGame3.movePlayer(WEST);
    assertEquals(
        "[************][************][************][************][************][************][************]\n"
            + "[************]                                                                      [************]\n"
            + "[************]                                                                      [************]\n"
            + "[************]   ~~~~~+~~ ---- ~~~~I~TO ---- ~~~~~+~~      ~~~~I~TO      ~~~~I~TO   [************]\n"
            + "[************]      ||            ||            ||            ||            ||      [************]\n"
            + "[************]      ||            ||            ||            ||            ||      [************]\n"
            + "[************]      ||            ||            ||            ||            ||      [************]\n"
            + "[************]      ||            ||            ||            ||            ||      [************]\n"
            + "[************]   ~~~~I+~~      ~S~~I~~O      ~~~~~~~O ---- ~~~~I~~O ---- ~~~~~+~~   [************]\n"
            + "[************]      ||                          ||                                  [************]\n"
            + "[************]      ||                          ||                                  [************]\n"
            + "[************]      ||                          ||                                  [************]\n"
            + "[************]      ||                          ||                                  [************]\n"
            + "[************]   ~~~~I+~~      ~~~PI+~~ ---- ~~~~~~TO ---- ~~~~~+~~      ~~~~I~TO   [************]\n"
            + "[************]      ||            ||                          ||            ||      [************]\n"
            + "[************]      ||            ||                          ||            ||      [************]\n"
            + "[************]      ||            ||                          ||            ||      [************]\n"
            + "[************]      ||            ||                          ||            ||      [************]\n"
            + "[************]   ~~~~~+~~ ---- E~M~I~TO ---- ~~~~I~TO ---- ~~~~~~~O ---- ~~~~I~~O   [************]\n"
            + "[************]                    ||            ||            ||            ||      [************]\n"
            + "[************]                    ||            ||            ||            ||      [************]\n"
            + "[************]                    ||            ||            ||            ||      [************]\n"
            + "[************]                    ||            ||            ||            ||      [************]\n"
            + "[************]   ~~~~~~~O ---- ~~~~I+~~      ~~~~I~~O      ~~~~~~TO      ~~~~I~TO   [************]\n"
            + "[************]                                                                      [************]\n"
            + "[************]                                                                      [************]\n"
            + "[************][************][************][************][************][************][************]\n",
        sampleGame3.toString()
    );
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
    int percentage = randomizer.getIntBetween(0, 100);
    return new DungeonGame(m, n, percentage, 1, wrap, interconnectivity);
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