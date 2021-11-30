package model;

import static dungeongeneral.Direction.EAST;
import static dungeongeneral.Direction.NORTH;
import static dungeongeneral.Direction.SOUTH;
import static dungeongeneral.Direction.WEST;
import static dungeongeneral.Item.CROOKED_ARROW;
import static dungeongeneral.Item.POTION;
import static dungeongeneral.Treasure.DIAMOND;
import static dungeongeneral.Treasure.RUBY;
import static dungeongeneral.Treasure.SAPPHIRE;
import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;
import static junit.framework.TestCase.assertFalse;

import dungeon.DungeonGame;
import dungeon.Game;
import dungeongeneral.ReadOnlyLocation;
import org.junit.Before;
import org.junit.Test;

/**
 * This is the test suite for the dungeon game.
 */
public class GameTests {

  private Game sampleGame3;
  private Game monsterAtNorthSample;

  /**
   * Setup for this test suite.
   */
  @Before
  public void setUp() {
    sampleGame3 = new DungeonGame(
        5, 5, 60, 1, false, 2,
        28,19,18,32,47,31,48,58,8,52,46,42,3,39,38,28,19,34,26,35,20,28,25,22,
        20,0,22,0,16,16,19,12,3,5,8,20,6,2,5,1,2,2,7,2,3,1,10,3,2,1,12,3,1,1,10,3,3,1,8,
        1,1,1,0,3,2,3,3,3,2,1,2,2,1,3,3,5,20,2,4,5,12,3,14,5,3,2,8,3,3,3,4,1,9,3,5,2,13,
        2,9,5,10,4,1,3
    );
    monsterAtNorthSample = new DungeonGame(5, 5, 50, 5, false, 3,
        14,7,7,1,45,33,36,52,61,33,18,15,43,14,27,12,22,6,31,7,11,4,10,18,3,18,13,25,
        23,17,21,26,25,23,17,19,1,0,1,8,10,3,0,8,8,12,7,3,10,3,1,2,6,5,3,1,2,1,4,0,0,75,28,63,
        39,3,65,25,1,47,36,45,56,51,53,38,46,17,40,2,26,7,3,29,5,13,8,26,16,22,13,1,2,13,22,3,
        21,16,5,1,5,1,2,1,1,3,2,3,0,1,3,1,6,2,3,3,3,2,2,3,6,2,2,2,13,2,3,3,18,3,14,4,5,2,5,5,5,
        1,10,2,7,2,6,3,3,4,6,2,9,3,1,2
    );
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
   * Test if negative percentage throws an exception as expected.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testAddTreasureException1() {
    new DungeonGame(10, 10, -20, 1, true, 3);
  }

  /**
   * Test if percentage greater than 100 on throws an exception as expected.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testAddTreasureException2() {
    new DungeonGame(10, 10, 120, 1, true, 3);
  }


  /**
   * Pass null to Move throws Illegal Argument exception.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testIllegalInputForMove() {
    sampleGame3.move(null);
  }

  /**
   * Pass direction with no neighbour to move throws exception.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testIllegalInputForMoveNonExistentNeighbour() {
    assertEquals(
            "This is a cave\n"
                    + "Coordinates: (1, 1)\n"
                    + "Possible routes: N \n"
                    + "There are some items in this cave: 3 crooked arrows \n",
            sampleGame3.getLocationDesc().toString()
    );
    sampleGame3.move(SOUTH);
  }

  /**
   * Passing null to cede item.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testIllegalInputForCedeItem() {
    sampleGame3.cedeItem(null);
  }

  /**
   * picking item which does not exist in the location.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testIllegalInputForCedeItemNonExistentItem() {
    assertEquals(
            "This is a cave\n"
                    + "Coordinates: (1, 1)\n"
                    + "Possible routes: N \n"
                    + "There are some items in this cave: 3 crooked arrows \n",
            sampleGame3.getLocationDesc().toString()
    );
    sampleGame3.cedeItem(POTION);
  }

  /**
   * When location has no items at all.
   */
  @Test(expected = IllegalStateException.class)
  public void testIllegalInputForCedeItemNonExistentItems() {
    try {
      sampleGame3.move(NORTH);
      sampleGame3.move(EAST);
    }
    catch (IllegalArgumentException ignored) {
    }
    assertEquals(
            "This is a tunnel\n"
                    + "Coordinates: (0, 2)\n"
                    + "Possible routes: S W \n",
            sampleGame3.getLocationDesc().toString()
    );
    sampleGame3.cedeItem(CROOKED_ARROW);
  }

  /**
   *  Passing null to cede treasure.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testIllegalInputForCedeTreasure() {
    sampleGame3.cedeTreasure(null);
  }

  /**
   *  When location has no treasure at all.
   */
  @Test(expected = IllegalStateException.class)
  public void testIllegalInputForCedeTreasureNoTreasure() {
    assertEquals(
            "This is a cave\n"
                    + "Coordinates: (1, 1)\n"
                    + "Possible routes: N \n"
                    + "There are some items in this cave: 3 crooked arrows \n",
            sampleGame3.getLocationDesc().toString()
    );
    sampleGame3.cedeTreasure(DIAMOND);
  }

  /**
   *  When location has no treasure of given type.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testIllegalInputForCedeTreasureNoTreasureOfThisType() {
    try {
      sampleGame3.move(NORTH);
      //collecting all 3 diamonds at player location.
      sampleGame3.cedeTreasure(DIAMOND);
      sampleGame3.cedeTreasure(DIAMOND);
      sampleGame3.cedeTreasure(DIAMOND);
      //player location still has other type of treasure.
    }
    catch (IllegalArgumentException ignored) {
    }
    assertEquals(
            "This is a cave\n"
                    + "Coordinates: (0, 1)\n"
                    + "Possible routes: E S W \n"
                    + "There's some treasure in this cave: 2 rubies 3 sapphires \n"
                    + "There are some items in this cave: 3 crooked arrows \n",
            sampleGame3.getLocationDesc().toString()
    );
    //This throws illegal argument exception.
    sampleGame3.cedeTreasure(DIAMOND);
  }

  /**
   * pass null as direction.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testShootIllegalInput() {
    sampleGame3.shoot(null, 1);
  }

  /**
   * pass negative distance.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testShootNegativeDistance() {
    assertEquals(
            "This is a cave\n"
                    + "Coordinates: (1, 1)\n"
                    + "Possible routes: N \n"
                    + "There are some items in this cave: 3 crooked arrows \n",
            sampleGame3.getLocationDesc().toString()
    );
    sampleGame3.shoot(NORTH, -3);
  }

  /**
   * pass 0 distance.
   */
  @Test(expected = IllegalArgumentException.class)

  public void testShootZeroDistance() {
    assertEquals(
            "This is a cave\n"
                    + "Coordinates: (1, 1)\n"
                    + "Possible routes: N \n"
                    + "There are some items in this cave: 3 crooked arrows \n",
            sampleGame3.getLocationDesc().toString()
    );
    sampleGame3.shoot(NORTH, 0);
  }

  /**
   * Test if exception is thrown when move is called after game is over.
   */
  @Test(expected = IllegalStateException.class)
  public void testMoveAfterGameOver() {
    assertFalse(monsterAtNorthSample.isGameOver());
    monsterAtNorthSample.move(NORTH);
    assertTrue(monsterAtNorthSample.isGameOver());
    monsterAtNorthSample.move(SOUTH); // This throws exception.
  }

  /**
   * Test if shoot throws an exception when called after game is over.
   */
  @Test(expected = IllegalStateException.class)
  public void testShootAfterGameOver() {
    assertFalse(monsterAtNorthSample.isGameOver());
    monsterAtNorthSample.move(NORTH);
    assertTrue(monsterAtNorthSample.isGameOver());
    monsterAtNorthSample.shoot(NORTH, 1); // This throws exception.
  }

  /**
   * Test cedeTreasure throws an exception when called after game is over.
   */
  @Test(expected = IllegalStateException.class)
  public void testPickUpTreasureAfterGameOver() {
    assertFalse(monsterAtNorthSample.isGameOver());
    monsterAtNorthSample.move(NORTH);
    assertTrue(monsterAtNorthSample.isGameOver());
    monsterAtNorthSample.cedeTreasure(RUBY);
  }

  /**
   * Test cedeItem throws an exception when called after game is over.
   */
  @Test(expected = IllegalStateException.class)
  public void testPickUpItemAfterGameOver() {
    assertFalse(monsterAtNorthSample.isGameOver());
    monsterAtNorthSample.move(NORTH);
    assertTrue(monsterAtNorthSample.isGameOver());
    monsterAtNorthSample.cedeItem(CROOKED_ARROW);
  }

  /**
   * Test wrapping dungeon generation using pseudo random input.
   * Will create a wrapping dungeon when given a valid set of random numbers.
   * We move through nodes with edges.
   */
  @Test
  public void testWrappingDungeon() {
    Game game = new DungeonGame(4, 4, 0, 1, true, 8,
            53,34,14,55,20,2,28,4,19,1,8,1,38,16,24,31,11,28,
            25,14,30,20,8,17,5,11,10,19,7,5,10,8,7,0,0,2,5,2,5,1,3,2,0
    );
    ReadOnlyLocation dec = game.getLocationDesc();
    assertEquals("This is a cave\n"
            + "Coordinates: (2, 3)\n"
            + "Possible routes: E \n", dec.toString());
    game.move(EAST);
    dec = game.getLocationDesc();
    // we can see that the coordinates have wrapped around
    assertEquals("This is a tunnel\n"
            + "Coordinates: (2, 0)\n"
            + "Possible routes: S W \n", dec.toString());
    game.move(SOUTH);
    dec = game.getLocationDesc();
    assertEquals("This is a tunnel\n"
            + "Coordinates: (3, 0)\n"
            + "Possible routes: N W \n", dec.toString());
    game.move(WEST);
    dec = game.getLocationDesc();
    // we can see that the coordinates have wrapped around
    assertEquals("This is a cave\n"
            + "Coordinates: (3, 3)\n"
            + "Possible routes: E S W \n", dec.toString());
    game.move(WEST);
    dec = game.getLocationDesc();
    assertEquals("This is a cave\n"
            + "Coordinates: (3, 2)\n"
            + "Possible routes: N E S W \n", dec.toString());
    int i = 0;
    // column size of dungeon is only 4 but we can loop through the dungeon.
    while (i < 100) {
      i++;
      game.move(NORTH);
    }
    assertEquals("This is a cave\n"
            + "Coordinates: (3, 2)\n"
            + "Possible routes: N E S W \n", game.getLocationDesc().toString());
  }

  /**
   * Test non-wrapping dungeon generation using pseudo random input.
   * Will create a non-wrapping dungeon when given a valid set of random numbers.
   * We walk through every corner node and check that there are no wrap around paths.
   */
  @Test
  public void testNonWrappingDungeon() {
    Game game = new DungeonGame(4, 4, 0, 1, false, 5,
            23,8,23,4,34,23,27,15,22,16,6,1,10,19,9,
            6,14,9,1,12,2,8,9,2,8,12,12,6,1,4,2,1,4,5,2,1,0,1);
    ReadOnlyLocation des = game.getLocationDesc();
    assertEquals("This is a cave\n"
                    + "Coordinates: (0, 0)\n"
                    + "Possible routes: E \n", des.toString());
    game.move(EAST);
    des = game.getLocationDesc();
    assertEquals("This is a tunnel\n"
                    + "Coordinates: (0, 1)\n"
                    + "Possible routes: E W \n", des.toString());
    game.move(EAST);
    des = game.getLocationDesc();
    assertEquals("This is a tunnel\n"
            + "Coordinates: (0, 2)\n"
            + "Possible routes: E W \n", des.toString());
    game.move(EAST);
    des = game.getLocationDesc();
    assertEquals("This is a tunnel\n"
            + "Coordinates: (0, 3)\n"
            + "Possible routes: S W \n", des.toString());
    game.move(SOUTH);
    des = game.getLocationDesc();
    assertEquals("This is a cave\n"
            + "Coordinates: (1, 3)\n"
            + "Possible routes: N S W \n"
            + "you sense a slightly pungent smell of otyughs.\n", des.toString());
    game.move(SOUTH);
    des = game.getLocationDesc();
    assertEquals("This is a cave\n"
            + "Coordinates: (2, 3)\n"
            + "Possible routes: N S W \n"
            + "you sense a very pungent smell of otyughs, be careful!\n", des.toString());
    game.move(SOUTH);
    des = game.getLocationDesc();
    assertEquals("This is a tunnel\n"
            + "Coordinates: (3, 3)\n"
            + "Possible routes: N W \n"
            + "you sense a slightly pungent smell of otyughs.\n", des.toString());
    game.move(WEST);
    des = game.getLocationDesc();
    assertEquals("This is a cave\n"
            + "Coordinates: (3, 2)\n"
            + "Possible routes: N E W \n"
            + "you sense a very pungent smell of otyughs, be careful!\n", des.toString());
    game.move(WEST);
    des = game.getLocationDesc();
    assertEquals("This is a cave\n"
            + "Coordinates: (3, 1)\n"
            + "Possible routes: N E W \n"
            + "you sense a slightly pungent smell of otyughs.\n", des.toString());
    game.move(WEST);
    des = game.getLocationDesc();
    assertEquals("This is a tunnel\n"
            + "Coordinates: (3, 0)\n"
            + "Possible routes: N E \n", des.toString());
    game.move(NORTH);
    des = game.getLocationDesc();
    assertEquals("This is a cave\n"
            + "Coordinates: (2, 0)\n"
            + "Possible routes: N E S \n", des.toString());
    game.move(NORTH);
    des = game.getLocationDesc();
    assertEquals("This is a tunnel\n"
            + "Coordinates: (1, 0)\n"
            + "Possible routes: E S \n", des.toString());

  }

  //  /**
  //   * Test showing map when interconnectivity is not zero.
  //   */
  //  @Test
  //  public void testInterConnectivityNonZero() {
  //    Game game = new DungeonGame(5, 7, 60, 4, true, 2,
  //        41,99,32,91,41,56,64,8,94,7,106,35,26,56,70,41,68,46,93,83,98,65,73,34,72,13,
  //        4,29,7,5,31,58,42,5,50,15,14,2,53,56,9,3,23,4,18,17,18,0,1,3,1,11,2,3,1,7,3,3,1,8,2,3,
  //        1,3,2,2,2,10,1,1,1,12,2,3,3,3,1,3,3,3,1,2,2,2,2,3,3,5,1,1,2,2,1,3,2,28,4,32,1,4,2,3,3,
  //        7,3,17,5,6,3,17,2,7,5,5,5,11,3,18,1,13,1,16,5,7,1,7,5,11,5,11,2,8,1,8,2,14,2,3,16,2
  //    );
  //    assertEquals(
  //        "    ||                  ||                                      ||    \n"
  //            + "- ****t*    *M**cT    ****cT -- ***It* -- ***It*    ****cT    ****t* -\n"
  //            + "              ||        ||                  ||        ||              \n"
  //            + "              ||        ||                  ||        ||              \n"
  //            + "- ***Ic* -- *M*Ic*    ***Ic*    ****t* -- ***IcT    ****t* -- ***Ic* -\n"
  //            + "    ||        ||                  ||        ||                  ||    \n"
  //            + "    ||        ||                  ||        ||                  ||    \n"
  //            + "  ***Ic*    ****t*    ***Ic* -- ***It*    ***It* -- S*P*cT    ***It*  \n"
  //            + "              ||                                                ||    \n"
  //            + "              ||                                                ||    \n"
  //            + "  ***IcT -- ***IcT -- ***IcT -- ***It* -- ****t* -- ***IcT -- ***Ic*  \n"
  //            + "              ||        ||                            ||        ||    \n"
  //            + "              ||        ||                            ||        ||    \n"
  //            + "- ***It*    ****cT    ****c* -- ****t* -- ****cT    *M*Ic*    EM*IcT -\n"
  //            + "    ||                  ||                                      ||    \n",
  //        game.toString());
  //  }
  //
  //  /**
  //   * Testing showing map when interconnectivity is zero.
  //   */
  //  @Test
  //  public void testInterconnectivityZero() {
  //    Game game = new DungeonGame(5, 7, 60, 3, true, 0,
  //        102,15,133,43,127,16,61,26,62,91,8,102,72,87,37,46,17,81,5,57,57,71,28,66,47,
  //        8,84,73,1,73,22,72,64,29,21,59,43,48,59,8,30,13,11,11,17,9,8,8,9,26,26,12,2,3,1,10,2,
  //        2,3,2,1,2,3,8,2,2,1,8,1,1,1,11,2,3,2,5,1,2,1,6,3,3,2,5,1,3,3,2,2,2,2,5,4,22,5,32,2,29,
  //        4,28,1,10,2,14,4,11,2,8,4,12,4,24,5,18,2,0,1,4,4,16,5,9,3,2,1,5,3,15,4,10,3,12,3,8,11
  //    );
  //    assertEquals(
  //        "    ||        ||        ||        ||                  ||              \n"
  //            + "  ***It* -- ****cT -- *M**cT -- ***It*    ****c*    ***IcT -- ***Ic*  \n"
  //            + "                                            ||        ||              \n"
  //            + "                                            ||        ||              \n"
  //            + "  ****t* -- ****t* -- ***It* -- ***It*    ***Ic* -- ****t*    ***IcT  \n"
  //            + "    ||                            ||        ||                  ||    \n"
  //            + "    ||                            ||        ||                  ||    \n"
  //            + "  ****t*    ***It* -- ***Ic*    EM*Ic*    ****t* -- ****cT -- ****t*  \n"
  //            + "    ||        ||                                      ||              \n"
  //            + "    ||        ||                                      ||              \n"
  //            + "  ***It*    ****t*    ***IcT    ****c*    ***It* -- ***IcT -- ***IcT  \n"
  //            + "    ||        ||        ||        ||        ||                        \n"
  //            + "    ||        ||        ||        ||        ||                        \n"
  //            + "- ****cT    ***It*    ***It*    *M*Ic* -- ****t*    S*PIcT    ***Ic* -\n"
  //            + "    ||        ||        ||        ||                  ||              \n",
  //        game.toString());
  //  }

  /**
   * Test location of the player when he is created.
   * The start location has no monster.
   */
  @Test
  public void testPlayerLocationAtStartHasNoMonster() {
    Game game = new DungeonGame(5, 5, 20, 1, false, 3);
    assertTrue(game.getLocationDesc().hasNoMonster()); // start location has no monster.
  }

  /**
   * Test location of the player when he is created.
   * Player has 3 arrows on game start.
   */
  @Test
  public void test3ArrowsOnGameStart() {
    Game game = new DungeonGame(5, 5, 20, 1, false, 3);
    assertEquals("Player info:\n"
                    + "Misses: 0\n"
                    + "Hits: 0\n"
                    + "Kills: 0\n"
                    + "Treasure:\n"
                    + "None\n"
                    + "Items:\n"
                    + "3 crooked arrows 1 potion \n",
            game.getPlayerDesc().toString()); // start location has no monster.
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
   * This also proves that there is a monster in the end cave.
   *
   */
  @Test
  public void testPlayerAtEndLocationAndMonsterAtEnd() {
    Game game = new DungeonGame(
        5, 5, 0, 1, false, 2,
        40,4,17,20,59,29,45,53,12,56,53,2,48,50,
        10,11,9,10,40,39,20,29,27,25,27,21,6,16,22,2,5,1
    );
    //    + "  ****c* -- ****t*    ****c* -- ****c* -- ****c*  \n"
    //    + "              ||                  ||              \n"
    //    + "              ||                  ||              \n"
    //    + "  S*P*c* -- ****c* -- ****t* -- ****t*    ****c*  \n"
    //    + "              ||                            ||    \n"
    //    + "              ||                            ||    \n"
    //    + "  ****t* -- ****c* -- ****c* -- ****t* -- ****t*  \n"
    //    + "    ||                  ||                        \n"
    //    + "    ||                  ||                        \n"
    //    + "  ****c* -- ****t*    ****c* -- ****t* -- EM**c*  \n"
    //    + "    ||        ||        ||                        \n"
    //    + "    ||        ||        ||                        \n"
    //    + "  ****t* -- ****c* -- ****c* -- ****t* -- ****c*  \n"
    assertFalse(game.isGameOver());
    assertFalse(game.hasPlayerWon());
    game.move(EAST);
    assertFalse(game.isGameOver());
    assertFalse(game.hasPlayerWon());
    game.move(SOUTH);
    assertFalse(game.isGameOver());
    assertFalse(game.hasPlayerWon());
    game.move(EAST);
    assertFalse(game.isGameOver());
    assertFalse(game.hasPlayerWon());
    game.move(SOUTH);
    assertFalse(game.isGameOver());
    assertFalse(game.hasPlayerWon());
    game.move(EAST);
    assertFalse(game.isGameOver());
    assertFalse(game.hasPlayerWon());
    game.shoot(EAST, 1); // hit end cave monster
    assertFalse(game.isGameOver());
    assertFalse(game.hasPlayerWon());
    game.shoot(EAST, 1); // Kill end cave monster
    assertFalse(game.isGameOver());
    assertFalse(game.hasPlayerWon());
    game.move(EAST); // Move to end cave
    assertTrue(game.isGameOver());  // game over
    assertTrue(game.hasPlayerWon()); // player won
    assertTrue(game.getLocationDesc().hasDeadMonster()); // End location has a dead monster
  }

  /**
   * Test that player does not win when at end but is dead.
   */
  @Test
  public void testPlayerDoesNotWinWhenInEndButIsDead() {
    Game game = new DungeonGame(
        5, 5, 0, 1, false, 2,
        40,4,17,20,59,29,45,53,12,56,53,2,48,50,
        10,11,9,10,40,39,20,29,27,25,27,21,6,16,22,2,5,1
    );
    //      "                                                  \n"
    //    + "  ****c* -- ****t*    ****c* -- ****c* -- ****c*  \n"
    //    + "              ||                  ||              \n"
    //    + "              ||                  ||              \n"
    //    + "  S*P*c* -- ****c* -- ****t* -- ****t*    ****c*  \n"
    //    + "              ||                            ||    \n"
    //    + "              ||                            ||    \n"
    //    + "  ****t* -- ****c* -- ****c* -- ****t* -- ****t*  \n"
    //    + "    ||                  ||                        \n"
    //    + "    ||                  ||                        \n"
    //    + "  ****c* -- ****t*    ****c* -- ****t* -- EM**c*  \n"
    //    + "    ||        ||        ||                        \n"
    //    + "    ||        ||        ||                        \n"
    //    + "  ****t* -- ****c* -- ****c* -- ****t* -- ****c*  \n"
    //    + "                                                  \n",
    // moving player to end location without killing the monster.
    game.move(EAST);
    game.move(SOUTH);
    game.move(EAST);
    game.move(SOUTH);
    game.move(EAST);
    game.move(EAST);
    assertTrue(game.isGameOver()); // game is over
    assertFalse(game.hasPlayerWon()); // but player has lost.
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
    //      "                                        \n"
    //    + "  *M**c*    ****c*    ****c*    *M**c*  \n"
    //    + "    ||        ||        ||        ||    \n"
    //    + "    ||        ||        ||        ||    \n"
    //    + "  ****c* -- ****c* -- ****c* -- ****t*  \n"
    //    + "    ||                  ||              \n"
    //    + "    ||                  ||              \n"
    //    + "  ****t* -- EM**c* -- *M**c* -- ****t*  \n"
    //    + "              ||        ||        ||    \n"
    //    + "              ||        ||        ||    \n"
    //    + "  ****t* -- ****c* -- ****t*    ****c*  \n"
    //    + "    ||                                  \n"
    //    + "    ||                                  \n"
    //    + "  ****t* -- ****t* -- ****t* -- S*P*c*  \n"
    //    + "                                        \n",
    assertFalse(game.isGameOver());
    assertFalse(game.hasPlayerWon());
    game.move(WEST);
    assertFalse(game.isGameOver());
    assertFalse(game.hasPlayerWon());
    game.move(WEST);
    assertFalse(game.isGameOver());
    assertFalse(game.hasPlayerWon());
    game.move(WEST);
    assertFalse(game.isGameOver());
    assertFalse(game.hasPlayerWon());
    game.move(NORTH);
    assertFalse(game.isGameOver());
    assertFalse(game.hasPlayerWon());
    game.move(EAST);
    assertFalse(game.isGameOver());
    assertFalse(game.hasPlayerWon());
    game.move(EAST);
    assertFalse(game.isGameOver());
    assertFalse(game.hasPlayerWon());
    game.move(NORTH);
    assertTrue(game.isGameOver()); // player dies hence game over
    assertFalse(game.hasPlayerWon()); // player dies hence lost
  }

  /**
   * Test if player description is as expected.
   */
  @Test
  public void testPlayerDescription() {
    // Exceptions are thrown if player any command fails.
    sampleGame3.cedeItem(CROOKED_ARROW);
    sampleGame3.cedeItem(CROOKED_ARROW);
    sampleGame3.move(NORTH);
    sampleGame3.cedeTreasure(DIAMOND);
    sampleGame3.cedeTreasure(RUBY);
    sampleGame3.move(EAST);
    sampleGame3.move(SOUTH);
    sampleGame3.move(SOUTH);
    sampleGame3.cedeTreasure(DIAMOND);
    sampleGame3.cedeTreasure(SAPPHIRE);
    sampleGame3.shoot(NORTH, 1);
    assertEquals(
        "Player info:\n"
                + "Misses: 1\n"
                + "Hits: 0\n"
                + "Kills: 0\n"
                + "Treasure:\n"
                + "2 diamonds 1 ruby 1 sapphire \n"
                + "Items:\n"
                + "4 crooked arrows 1 potion \n",
        sampleGame3.getPlayerDesc().toString()
    );
  }

  /**
   * Test if description of the player's location is as expected.
   * Player in a tunnel without items.
   */
  @Test
  public void testPlayerLocationDescription1() {
    sampleGame3.move(NORTH);
    sampleGame3.move(EAST);
    assertEquals(
        "This is a tunnel\n"
            + "Coordinates: (0, 2)\n"
            + "Possible routes: S W \n",
        sampleGame3.getLocationDesc().toString()
    );
  }

  /**
   * Test if description of the player's location is as expected.
   * Player in a tunnel with items.
   */
  @Test
  public void testPlayerLocationDescription2() {
    sampleGame3.move(NORTH);
    sampleGame3.move(WEST);
    sampleGame3.move(SOUTH);
    assertEquals(
        "This is a tunnel\n"
            + "Coordinates: (1, 0)\n"
            + "Possible routes: N S \n"
            + "There are some items in this cave: 5 crooked arrows \n",
        sampleGame3.getLocationDesc().toString()
    );
  }

  /**
   * Test if description of the player's location is as expected.
   * Player in a cave with treasure.
   */
  @Test
  public void testPlayerLocationDescription3() {
    sampleGame3.move(NORTH);
    sampleGame3.move(EAST);
    sampleGame3.move(SOUTH);
    sampleGame3.move(SOUTH);
    assertEquals(
        "This is a cave\n"
            + "Coordinates: (2, 2)\n"
            + "Possible routes: N E W \n"
            + "There's some treasure in this cave: 2 diamonds 3 rubies 1 sapphire \n"
                + "you sense a slightly pungent smell of otyughs.\n",
        sampleGame3.getLocationDesc().toString()
    );
  }

  /**
   * Test if description of the player's location is as expected.
   * Player in a cave without treasure and items.
   */
  @Test
  public void testPlayerLocationDescription4() {
    sampleGame3.move(NORTH);
    sampleGame3.move(EAST);
    sampleGame3.move(SOUTH);
    assertEquals(
        "This is a cave\n"
            + "Coordinates: (1, 2)\n"
            + "Possible routes: N E S \n",
        sampleGame3.getLocationDesc().toString()
    );
  }

  /**
   * Test location description with injured monster.
   */
  @Test
  public void testSmellWhenPlayerWithInjuredMonster() {
    //Monster to the north of start location.
    int[] varargs = new int[]{14, 7, 7, 1, 45, 33, 36, 52, 61, 33, 18, 15, 43, 14, 27, 12, 22, 6,
        31, 7, 11, 4, 10, 18, 3, 18, 13, 25, 23, 17, 21, 26, 25, 23, 17, 19, 1, 0, 1, 8, 10, 3, 0,
        8, 8, 12, 7, 3, 10, 3, 1, 2, 6, 5, 3, 1, 2, 1, 4, 0, 0, 75, 28, 63, 39, 3, 65, 25, 1, 47,
        36, 45, 56, 51, 53, 38, 46, 17, 40, 2, 26, 7, 3, 29, 5, 13, 8, 26, 16, 22, 13, 1, 2, 13,
        22, 3, 21, 16, 5, 1, 5, 1, 2, 1, 1, 3, 2, 3, 0, 1, 3, 1, 6, 2, 3, 3, 3, 2, 2, 3, 6, 2, 2,
        2, 13, 2, 3, 3, 18, 3, 14, 4, 5, 2, 5, 5, 5, 1, 10, 2, 7, 2, 6, 3, 3, 4, 6, 2, 9, 3, 1, 2,
        2};
    Game game = new DungeonGame(5, 5, 50, 5, false, 3,
        varargs
    );
    // Player survives in game, future given by varargs.
    game.shoot(NORTH, 1); //to reduce monster's health.
    game.move(NORTH);
    assertEquals(
        "This is a cave\n"
                + "Coordinates: (0, 1)\n"
                + "Possible routes: E S W \n"
                + "There is an injured monster here.\n"
                + "you sense a very pungent smell of otyughs, be careful!\n",
        game.getLocationDesc().toString()
    );
    assertEquals(
        "Player info:\n"
                + "Misses: 0\n"
                + "Hits: 1\n"
                + "Kills: 0\n"
                + "Treasure:\n"
                + "None\n"
                + "Items:\n"
                + "2 crooked arrows 1 potion \n",
        game.getPlayerDesc().toString()
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
    sampleGame3.move(NORTH);
    assertEquals(
        "Player info:\n"
                + "Misses: 0\n"
                + "Hits: 0\n"
                + "Kills: 0\n"
                + "Treasure:\n"
                + "None\n"
                + "Items:\n"
                + "3 crooked arrows 1 potion \n",
        sampleGame3.getPlayerDesc().toString()
    );
    assertEquals(
        "This is a cave\n"
            + "Coordinates: (0, 1)\n"
            + "Possible routes: E S W \n"
            + "There's some treasure in this cave: 3 diamonds 2 rubies 3 sapphires \n"
            + "There are some items in this cave: 3 crooked arrows \n",
        sampleGame3.getLocationDesc().toString()
    );
    sampleGame3.cedeTreasure(SAPPHIRE);
    sampleGame3.cedeTreasure(SAPPHIRE);
    sampleGame3.cedeTreasure(DIAMOND);
    assertEquals(
        "Player info:\n"
                + "Misses: 0\n"
                + "Hits: 0\n"
                + "Kills: 0\n"
                + "Treasure:\n"
                + "1 diamond 2 sapphires \n"
                + "Items:\n"
                + "3 crooked arrows 1 potion \n",
        sampleGame3.getPlayerDesc().toString()
    );
    assertEquals(
        "This is a cave\n"
            + "Coordinates: (0, 1)\n"
            + "Possible routes: E S W \n"
            + "There's some treasure in this cave: 2 diamonds 2 rubies 1 sapphire \n"
            + "There are some items in this cave: 3 crooked arrows \n",
        sampleGame3.getLocationDesc().toString()
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
            + "Coordinates: (1, 1)\n"
            + "Possible routes: N \n"
            + "There are some items in this cave: 3 crooked arrows \n",
        sampleGame3.getLocationDesc().toString()
    );
    assertEquals(
        "Player info:\n"
                + "Misses: 0\n"
                + "Hits: 0\n"
                + "Kills: 0\n"
                + "Treasure:\n"
                + "None\n"
                + "Items:\n"
                + "3 crooked arrows 1 potion \n",
        sampleGame3.getPlayerDesc().toString()
    );
    sampleGame3.cedeItem(CROOKED_ARROW);
    sampleGame3.cedeItem(CROOKED_ARROW);
    assertEquals(
        "This is a cave\n"
            + "Coordinates: (1, 1)\n"
            + "Possible routes: N \n"
            + "There are some items in this cave: 1 crooked arrow \n",
        sampleGame3.getLocationDesc().toString()
    );
    assertEquals(
        "Player info:\n"
                + "Misses: 0\n"
                + "Hits: 0\n"
                + "Kills: 0\n"
                + "Treasure:\n"
                + "None\n"
                + "Items:\n"
                + "5 crooked arrows 1 potion \n",
        sampleGame3.getPlayerDesc().toString()
    );
  }

  /**
   * Test that moving player in any direction changes his location.
   * P represents the player.
   * Invalid moves throws exceptions.
   * We move him in all 4 directions and check the dungeon map.
   * This also shows that arrows can be in both caves and tunnels.
   */
  @Test
  public void testDirectionsOnPlayerAndArrowsInCavesAndTunnels() {
    ReadOnlyLocation desc = sampleGame3.getLocationDesc();
    assertEquals("This is a cave\n"
            + "Coordinates: (1, 1)\n"
            + "Possible routes: N \n"
            + "There are some items in this cave: 3 crooked arrows \n",
            desc.toString());
    sampleGame3.move(NORTH);
    desc = sampleGame3.getLocationDesc();
    assertEquals("This is a cave\n"
            + "Coordinates: (0, 1)\n"
            + "Possible routes: E S W \n"
            + "There's some treasure in this cave: 3 diamonds 2 rubies 3 sapphires \n"
            + "There are some items in this cave: 3 crooked arrows \n",// Arrows in a cave
            desc.toString());
    sampleGame3.move(EAST);
    desc = sampleGame3.getLocationDesc();
    assertEquals("This is a tunnel\n"
            + "Coordinates: (0, 2)\n"
            + "Possible routes: S W \n",
            desc.toString());
    sampleGame3.move(SOUTH);
    desc = sampleGame3.getLocationDesc();
    assertEquals("This is a cave\n"
            + "Coordinates: (1, 2)\n"
            + "Possible routes: N E S \n",
            desc.toString());
    sampleGame3.move(SOUTH);
    desc = sampleGame3.getLocationDesc();
    assertEquals("This is a cave\n"
            + "Coordinates: (2, 2)\n"
            + "Possible routes: N E W \n"
            + "There's some treasure in this cave: 2 diamonds 3 rubies 1 sapphire \n"
            + "you sense a slightly pungent smell of otyughs.\n",
            desc.toString());
    sampleGame3.move(WEST);
    desc = sampleGame3.getLocationDesc();
    assertEquals("This is a tunnel\n"
            + "Coordinates: (2, 1)\n"
            + "Possible routes: E S \n"
            + "There are some items in this cave: 3 crooked arrows \n" // Arrows in a tunnel.
            + "you sense a very pungent smell of otyughs, be careful!\n",
            desc.toString()
    );
  }
}