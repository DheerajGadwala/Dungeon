package model;

import static dungeongeneral.Direction.EAST;
import static dungeongeneral.Direction.NORTH;
import static dungeongeneral.Direction.SOUTH;
import static dungeongeneral.Direction.WEST;
import static dungeongeneral.Odour.LESS_PUNGENT;
import static dungeongeneral.Odour.MORE_PUNGENT;
import static dungeongeneral.Odour.ODOURLESS;
import static dungeongeneral.ShotResult.HIT;
import static dungeongeneral.ShotResult.KILL;
import static dungeongeneral.ShotResult.MISS;
import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;

import dungeon.DungeonGame;
import dungeon.Game;
import dungeongeneral.Odour;
import dungeongeneral.ShotResult;
import org.junit.Before;
import org.junit.Test;

/**
 * Game tests related to monsters.
 * Odour tests and player death tests.
 */
public class GameTestsMonsters {

  private Game nonWrappingSampleForSmell;
  private Game wrappingSampleForSmell;

  /**
   * Setup for this test suite.
   */
  @Before
  public void setUp() {
    nonWrappingSampleForSmell = new DungeonGame(5, 4, 50, 5, false, 3,
        43,28,14,23,42,38,3,9,31,4,37,16,13,29,20,20,13,16,11,10,
        20,22,2,6,15,14,13,12,15,3,2,0,1,4,2,1,2,10,3,1,1,7,3,1,1,7,2,1,1,
        6,2,3,2,3,1,1,3,17,2,9,3,4,4,0,4,15,1,12,2,1,1,3,2,6,4,9,4,2,7,5,0
    );
    wrappingSampleForSmell = new DungeonGame(5, 4, 50, 4, true, 3,
        4,77,50,65,34,35,41,58,23,16,34,13,11,34,37,7,9,38,25,5,
        20,33,13,11,13,12,9,10,14,0,4,9,2,1,4,1,3,6,6,1,2,1,1,1,5,2,1,2,2,
        2,3,1,3,2,2,1,11,2,15,1,5,4,14,5,8,1,6,3,3,3,0,5,11,4,0,1,4,4,2
    );
  }

  /**
   * Throws exception when monster count is too high.
   */
  @Test(expected = IllegalArgumentException.class)
  public void highMonsterCount() {
    new DungeonGame(5, 5, 50, 30, true, 3);
  }

  /**
   * Player dies when he moves into a monster with full health.
   * The below dungeon game has a monster to the north of start location.
   * When the game is over and the player has lost, it implies that the player has died.
   */
  @Test
  public void playerDiesFullHealthMonster() {
    //Monster to the north of start location.
    Game game = new DungeonGame(5, 5, 50, 5, false, 3,
        14,7,7,1,45,33,36,52,61,33,18,15,43,14,27,12,22,6,31,7,11,4,10,18,3,18,13,25,
        23,17,21,26,25,23,17,19,1,0,1,8,10,3,0,8,8,12,7,3,10,3,1,2,6,5,3,1,2,1,4,0,0,75,28,63,
        39,3,65,25,1,47,36,45,56,51,53,38,46,17,40,2,26,7,3,29,5,13,8,26,16,22,13,1,2,13,22,3,
        21,16,5,1,5,1,2,1,1,3,2,3,0,1,3,1,6,2,3,3,3,2,2,3,6,2,2,2,13,2,3,3,18,3,14,4,5,2,5,5,5,
        1,10,2,7,2,6,3,3,4,6,2,9,3,1,2
    );
    game.movePlayer(NORTH);
    assertTrue(game.isGameOver());
    assertFalse(game.hasPlayerWon());
  }

  /**
   * test that there is a 50% chance that the player dies when he he enters a cave
   * with an otyugh that has half health. The player dies if the last random number
   * passed is 1, he survives if it is 2. In both varargs all numbers except the last
   * are same.
   * Also tests game over when player dies.
   */
  @Test
  public void playerDeathChance50() {
    //Monster to the north of start location.
    int[] varargs1 = new int[]{
        14, 7, 7, 1, 45, 33, 36, 52, 61, 33, 18, 15, 43, 14, 27, 12, 22, 6, 31, 7, 11,
        4, 10, 18, 3, 18, 13, 25, 23, 17, 21, 26, 25, 23, 17, 19, 1, 0, 1, 8, 10, 3, 0,
        8, 8, 12, 7, 3, 10, 3, 1, 2, 6, 5, 3, 1, 2, 1, 4, 0, 0, 75, 28, 63, 39, 3, 65,
        25, 1, 47, 36, 45, 56, 51, 53, 38, 46, 17, 40, 2, 26, 7, 3, 29, 5, 13, 8, 26,
        16, 22, 13, 1, 2, 13, 22, 3, 21, 16, 5, 1, 5, 1, 2, 1, 1, 3, 2, 3, 0, 1, 3, 1,
        6, 2, 3, 3, 3, 2, 2, 3, 6, 2, 2, 2, 13, 2, 3, 3, 18, 3, 14, 4, 5, 2, 5, 5, 5,
        1, 10, 2, 7, 2, 6, 3, 3, 4, 6, 2, 9, 3, 1, 2, 1
    };
    Game game1 = new DungeonGame(5, 5, 50, 5, false, 3,
        varargs1
    );
    int[] varargs2 = new int[]{
        14, 7, 7, 1, 45, 33, 36, 52, 61, 33, 18, 15, 43, 14, 27, 12, 22, 6, 31, 7, 11,
        4, 10, 18, 3, 18, 13, 25, 23, 17, 21, 26, 25, 23, 17, 19, 1, 0, 1, 8, 10, 3, 0,
        8, 8, 12, 7, 3, 10, 3, 1, 2, 6, 5, 3, 1, 2, 1, 4, 0, 0, 75, 28, 63, 39, 3, 65,
        25, 1, 47, 36, 45, 56, 51, 53, 38, 46, 17, 40, 2, 26, 7, 3, 29, 5, 13, 8, 26,
        16, 22, 13, 1, 2, 13, 22, 3, 21, 16, 5, 1, 5, 1, 2, 1, 1, 3, 2, 3, 0, 1, 3, 1,
        6, 2, 3, 3, 3, 2, 2, 3, 6, 2, 2, 2, 13, 2, 3, 3, 18, 3, 14, 4, 5, 2, 5, 5, 5,
        1, 10, 2, 7, 2, 6, 3, 3, 4, 6, 2, 9, 3, 1, 2, 2
    };
    Game game2 = new DungeonGame(5, 5, 50, 5, false, 3,
        varargs2
    );
    // Same games with different future.
    assertEquals(game1.toString(), game2.toString());
    // Player dies in game 1, future given by varargs.
    game1.shootArrow(NORTH, 1); //to reduce monster's health.
    game1.movePlayer(NORTH);
    assertTrue(game1.isGameOver());
    assertFalse(game1.hasPlayerWon());
    assertTrue(
            game1.getLocationDescription().contains("There is an injured monster here.")
    ); // Player description shows that there is an injured monster there.
    // Player survives in game 2, future given by varargs.
    game2.shootArrow(NORTH, 1); //to reduce monster's health.
    game2.movePlayer(NORTH);
    assertFalse(game2.isGameOver());
    assertTrue(
            game2.getLocationDescription().contains("There is an injured monster here.")
    ); // Player description shows that there is an injured monster there.
  }

  /**
   * Test arrow hit through multiple turning tunnels and caves.
   * Arrow passes through 4 tunnels and 2 caves before hitting
   * the monster. Check in the below dungeon dump. First arrow hits,
   * Second arrow kills and third arrows misses.
   * Non-wrapping.
   */
  @Test
  public void testShootOverDistance() {
    Game game = new DungeonGame(5, 5, 50, 5, false, 3,
        17,27,17,42,54,65,48,59,35,44,3,31,10,34,12,14,11,25,40,18,32,22,9,6,22,16,
        3,31,13,21,19,7,21,0,6,6,7,5,15,5,7,12,11,9,1,5,2,3,3,3,1,2,1,2,8,2,2,2,10,1,3,3,2,
        1,2,3,1,3,1,2,4,2,2,1,5,3,16,3,15,1,16,4,13,3,6,5,3,3,3,4,6,3,11,4,9,2,13,4,8,7,7,6
    );
    ShotResult res1 = game.shootArrow(EAST, 2);
    assertEquals(HIT, res1);
    ShotResult res2 = game.shootArrow(EAST, 2);
    assertEquals(KILL, res2);
    ShotResult res3 = game.shootArrow(EAST, 2);
    assertEquals(MISS, res3);
    assertEquals(
        "Player info:\n"
            + "Misses: 1\n"
            + "Hits: 2\n"
            + "Kills: 1\n"
            + "Treasure:\n"
            + " diamond - 0\n"
            + " ruby - 0\n"
            + " sapphire - 0\n"
            + "Items:\n"
            + " bow - 1\n"
            + " crooked arrow - 0\n",
        game.getPlayerDescription());
    // We go to the location and check if it has a dead monster using the location description.
    game.movePlayer(EAST);
    game.movePlayer(EAST);
    game.movePlayer(SOUTH);
    game.movePlayer(SOUTH);
    game.movePlayer(WEST);
    game.movePlayer(WEST);
    assertTrue(
            game.getLocationDescription().contains("There is a dead monster here.")
    ); // Player description shows that there is a dead monster there.

  }

  /**
   * Test zero shoot distance.
   */
  @Test (expected = IllegalArgumentException.class)
  public void testShootDistanceZero() {
    Game game = new DungeonGame(5, 5, 50, 5, false, 3);
    game.shootArrow(NORTH, 0);
  }

  /**
   * Test negative shoot distance.
   */
  @Test (expected = IllegalArgumentException.class)
  public void testShootDistanceNegative() {
    Game game = new DungeonGame(5, 5, 50, 5, false, 3);
    game.shootArrow(NORTH, -5);
  }

  /**
   * Test null shoot direction.
   */
  @Test (expected = IllegalArgumentException.class)
  public void testShootDirectionNull() {
    Game game = new DungeonGame(5, 5, 50, 5, false, 3);
    game.shootArrow(null, 3);
  }

  /**
   * Test shot miss.
   * Non-wrapping.
   */
  @Test
  public void shotMissLongDistanceNonWrapping() {
    Game game = new DungeonGame(5, 5, 50, 5, false, 3);
    // Very long distanced shot stops in some cave, hence misses. [in non wrapping dungeon]
    ShotResult res = game.shootArrow(NORTH, 200);
    assertEquals(MISS, res);
  }

  /**
   * Test shot miss to an empty cave.
   * Non-wrapping.
   */
  @Test
  public void shotMissEmptyCaveNonWrapping() {
    Game game = new DungeonGame(5, 5, 50, 5, false, 3,
        64,42,52,21,23,22,43,50,40,29,55,19,18,39,45,27,28,7,30,35,31,32,7,9,6,14,
        11,7,20,4,10,1,5,2,11,24,1,23,9,2,6,11,2,13,1,1,2,11,2,3,3,14,2,3,2,0,1,3,2,11,2,2,
        2,8,2,3,1,5,2,1,1,7,3,2,3,5,4,7,1,2,3,0,2,11,3,14,4,9,4,6,3,14,2,4,3,0,1,12,2,2,3,7,5
    );
    assertEquals(
        "                                                  \n"
            + "  ***Ic* -- ***It* -- ***It*    ****t* -- ****c*  \n"
            + "                        ||        ||              \n"
            + "                        ||        ||              \n"
            + "  ***IcT    ****cT    ***Ic* -- ***IcT -- *M**c*  \n"
            + "    ||        ||        ||        ||              \n"
            + "    ||        ||        ||        ||              \n"
            + "  EM*IcT -- ****cT -- ****c* -- ***It*    S*P*c*  \n"
            + "    ||        ||                            ||    \n"
            + "    ||        ||                            ||    \n"
            + "  ***IcT -- *M**c* -- ****c* -- *M**c* -- ***It*  \n"
            + "    ||        ||        ||        ||              \n"
            + "    ||        ||        ||        ||              \n"
            + "  ****t* -- ****t*    *M*IcT    ***It* -- ****cT  \n"
            + "                                                  \n",
        game.toString());
    // no monster at distance 2 to the south.
    ShotResult res = game.shootArrow(SOUTH, 2);
    assertEquals(MISS, res);
  }

  /**
   * Test shot hit.
   * Non-wrapping.
   */
  @Test
  public void shotHitNonWrapping() {
    Game game = new DungeonGame(5, 5, 50, 5, false, 3,
        64,42,52,21,23,22,43,50,40,29,55,19,18,39,45,27,28,7,30,35,31,32,7,9,6,14,
        11,7,20,4,10,1,5,2,11,24,1,23,9,2,6,11,2,13,1,1,2,11,2,3,3,14,2,3,2,0,1,3,2,11,2,2,
        2,8,2,3,1,5,2,1,1,7,3,2,3,5,4,7,1,2,3,0,2,11,3,14,4,9,4,6,3,14,2,4,3,0,1,12,2,2,3,7,5,2
    );
    assertEquals(
        "                                                  \n"
            + "  ***Ic* -- ***It* -- ***It*    ****t* -- ****c*  \n"
            + "                        ||        ||              \n"
            + "                        ||        ||              \n"
            + "  ***IcT    ****cT    ***Ic* -- ***IcT -- *M**c*  \n"
            + "    ||        ||        ||        ||              \n"
            + "    ||        ||        ||        ||              \n"
            + "  EM*IcT -- ****cT -- ****c* -- ***It*    S*P*c*  \n"
            + "    ||        ||                            ||    \n"
            + "    ||        ||                            ||    \n"
            + "  ***IcT -- *M**c* -- ****c* -- *M**c* -- ***It*  \n"
            + "    ||        ||        ||        ||              \n"
            + "    ||        ||        ||        ||              \n"
            + "  ****t* -- ****t*    *M*IcT    ***It* -- ****cT  \n"
            + "                                                  \n",
        game.toString());
    // monster at distance 1 to the south.
    ShotResult res = game.shootArrow(SOUTH, 1);
    assertEquals(HIT, res);
    // lets go there and check if there is an injured monster.
    int d = 1;
    assertEquals("", game.getLocationDescription());
    game.movePlayer(SOUTH);
    // This is a tunnel
    assertEquals("", game.getLocationDescription());
    game.movePlayer(WEST);
  }

  /**
   * Test shot kill.
   * Non-wrapping.
   */
  @Test
  public void shotKill() {
    Game game = new DungeonGame(5, 5, 50, 5, false, 3,
        64,42,52,21,23,22,43,50,40,29,55,19,18,39,45,27,28,7,30,35,31,32,7,9,6,14,
        11,7,20,4,10,1,5,2,11,24,1,23,9,2,6,11,2,13,1,1,2,11,2,3,3,14,2,3,2,0,1,3,2,11,2,2,
        2,8,2,3,1,5,2,1,1,7,3,2,3,5,4,7,1,2,3,0,2,11,3,14,4,9,4,6,3,14,2,4,3,0,1,12,2,2,3,7,5
    );
    assertEquals(
        "                                                  \n"
            + "  ***Ic* -- ***It* -- ***It*    ****t* -- ****c*  \n"
            + "                        ||        ||              \n"
            + "                        ||        ||              \n"
            + "  ***IcT    ****cT    ***Ic* -- ***IcT -- *M**c*  \n"
            + "    ||        ||        ||        ||              \n"
            + "    ||        ||        ||        ||              \n"
            + "  EM*IcT -- ****cT -- ****c* -- ***It*    S*P*c*  \n"
            + "    ||        ||                            ||    \n"
            + "    ||        ||                            ||    \n"
            + "  ***IcT -- *M**c* -- ****c* -- *M**c* -- ***It*  \n"
            + "    ||        ||        ||        ||              \n"
            + "    ||        ||        ||        ||              \n"
            + "  ****t* -- ****t*    *M*IcT    ***It* -- ****cT  \n"
            + "                                                  \n",
        game.toString());
    // monster at distance 1 to the south.
    game.shootArrow(SOUTH, 1);
    ShotResult res = game.shootArrow(SOUTH, 1);
    assertEquals(KILL, res);
  }

  /**
   * Test shot hit for long distance on wrapping dungeon.
   * we move to the part of dungeon where the arrow can loop
   * and shoot in the looping direction. The arrow eventually
   * covers the given distance and stops at a cave. If the cave has
   * a monster it hits else it misses.
   */
  @Test
  public void shotHitLongDistanceWrapping() {
    Game game = new DungeonGame(5, 5, 50, 5, true, 15,
        43,9,26,41,15,26,73,75,65,0,72,15,28,33,22,4,23,48,27,25,34,28,41,40,26,4,31,10,
        4,19,28,32,11,30,6,12,14,26,23,34,26,4,8,24,20,7,21,13,13,6,11,8,7,13,16,3,10,13,8,10,5,
        2,0,0,6,4,5,1,1,0,0,4,3,1,1,18,3,1,3,19,2,1,3,13,3,1,2,16,2,1,1,7,3,2,3,11,1,2,3,13,1,2,
        3,13,2,2,2,12,3,3,2,9,1,3,3,9,3,6,4,13,5,12,3,16,3,17,1,3,1,2,3,8,5,7,3,14,4,4,3,0,8,8,1
    );
    assertEquals(
        "    ||                  ||        ||              \n"
            + "  *M**c* -- ****cT -- ***Ic* -- ***It*    S*P*cT  \n"
            + "    ||        ||        ||                  ||    \n"
            + "    ||        ||        ||                  ||    \n"
            + "  EM**c* -- *M*IcT -- *M*Ic*    ****t* -- ***It*  \n"
            + "    ||                  ||        ||              \n"
            + "    ||                  ||        ||              \n"
            + "- ****c* -- *M*Ic* -- ***Ic* -- ****c* -- ***IcT -\n"
            + "    ||        ||        ||                  ||    \n"
            + "    ||        ||        ||                  ||    \n"
            + "- ***IcT -- ****c*    ****cT -- ****cT -- ****cT -\n"
            + "    ||        ||        ||        ||        ||    \n"
            + "    ||        ||        ||        ||        ||    \n"
            + "- ***Ic* -- ****c* -- ***IcT -- ****cT -- ***IcT -\n"
            + "    ||                  ||        ||              \n",
        game.toString()
    );
    game.movePlayer(SOUTH);
    game.movePlayer(WEST);
    game.movePlayer(SOUTH);
    // Very long distanced shot in a wrapping dungeon
    // hits if monster exists at arrow's destination.
    ShotResult res = game.shootArrow(WEST, 52);
    assertEquals(HIT, res);
  }

  /**
   * Test shot hit for long distance on wrapping dungeon.
   * we move to the part of dungeon where the arrow can loop
   * and shoot in the looping direction. The arrow eventually
   * covers the given distance and stops at a cave. If the cave has
   * a monster it hits else it misses.
   */
  @Test
  public void shotMissWrapping() {
    Game game = new DungeonGame(5, 5, 50, 5, true, 15,
        43,9,26,41,15,26,73,75,65,0,72,15,28,33,22,4,23,48,27,25,34,28,41,40,26,4,31,10,
        4,19,28,32,11,30,6,12,14,26,23,34,26,4,8,24,20,7,21,13,13,6,11,8,7,13,16,3,10,13,8,10,5,
        2,0,0,6,4,5,1,1,0,0,4,3,1,1,18,3,1,3,19,2,1,3,13,3,1,2,16,2,1,1,7,3,2,3,11,1,2,3,13,1,2,
        3,13,2,2,2,12,3,3,2,9,1,3,3,9,3,6,4,13,5,12,3,16,3,17,1,3,1,2,3,8,5,7,3,14,4,4,3,0,8,8,1
    );
    assertEquals(
        "    ||                  ||        ||              \n"
            + "  *M**c* -- ****cT -- ***Ic* -- ***It*    S*P*cT  \n"
            + "    ||        ||        ||                  ||    \n"
            + "    ||        ||        ||                  ||    \n"
            + "  EM**c* -- *M*IcT -- *M*Ic*    ****t* -- ***It*  \n"
            + "    ||                  ||        ||              \n"
            + "    ||                  ||        ||              \n"
            + "- ****c* -- *M*Ic* -- ***Ic* -- ****c* -- ***IcT -\n"
            + "    ||        ||        ||                  ||    \n"
            + "    ||        ||        ||                  ||    \n"
            + "- ***IcT -- ****c*    ****cT -- ****cT -- ****cT -\n"
            + "    ||        ||        ||        ||        ||    \n"
            + "    ||        ||        ||        ||        ||    \n"
            + "- ***Ic* -- ****c* -- ***IcT -- ****cT -- ***IcT -\n"
            + "    ||                  ||        ||              \n",
        game.toString()
    );
    game.movePlayer(SOUTH);
    game.movePlayer(WEST);
    game.movePlayer(SOUTH);
    // Very long distanced shot in a wrapping dungeon
    // hits if monster exists at arrow's destination.
    ShotResult res = game.shootArrow(WEST, 53);
    assertEquals(MISS, res);
  }

  /**
   * Test shot kill.
   * Wrapping.
   */
  @Test
  public void shotKillWrapping() {
    Game game = new DungeonGame(5, 5, 50, 5, true, 15,
        43,9,26,41,15,26,73,75,65,0,72,15,28,33,22,4,23,48,27,25,34,28,41,40,26,4,31,10,
        4,19,28,32,11,30,6,12,14,26,23,34,26,4,8,24,20,7,21,13,13,6,11,8,7,13,16,3,10,13,8,10,5,
        2,0,0,6,4,5,1,1,0,0,4,3,1,1,18,3,1,3,19,2,1,3,13,3,1,2,16,2,1,1,7,3,2,3,11,1,2,3,13,1,2,
        3,13,2,2,2,12,3,3,2,9,1,3,3,9,3,6,4,13,5,12,3,16,3,17,1,3,1,2,3,8,5,7,3,14,4,4,3,0,8,8,1
    );
    assertEquals(
        "    ||                  ||        ||              \n"
            + "  *M**c* -- ****cT -- ***Ic* -- ***It*    S*P*cT  \n"
            + "    ||        ||        ||                  ||    \n"
            + "    ||        ||        ||                  ||    \n"
            + "  EM**c* -- *M*IcT -- *M*Ic*    ****t* -- ***It*  \n"
            + "    ||                  ||        ||              \n"
            + "    ||                  ||        ||              \n"
            + "- ****c* -- *M*Ic* -- ***Ic* -- ****c* -- ***IcT -\n"
            + "    ||        ||        ||                  ||    \n"
            + "    ||        ||        ||                  ||    \n"
            + "- ***IcT -- ****c*    ****cT -- ****cT -- ****cT -\n"
            + "    ||        ||        ||        ||        ||    \n"
            + "    ||        ||        ||        ||        ||    \n"
            + "- ***Ic* -- ****c* -- ***IcT -- ****cT -- ***IcT -\n"
            + "    ||                  ||        ||              \n",
        game.toString()
    );
    game.movePlayer(SOUTH);
    game.movePlayer(WEST);
    game.movePlayer(SOUTH);
    // Very long distanced shot in a wrapping dungeon
    // hits if monster exists at arrow's destination.
    // same monster can be hit with different input distances.
    ShotResult res1 = game.shootArrow(WEST, 52);
    ShotResult res2 = game.shootArrow(WEST, 37);
    assertEquals(HIT, res1);
    assertEquals(KILL, res2);
  }

  /**
   * Look at dump for clarity.
   * Test Odour on non-wrapping.
   * No Smell
   */
  @Test
  public void testNoSmellNonWrapping() {
    assertEquals(
        "                                        \n"
            + "  S*PIc*    ****t* -- ***It*    *M**c*  \n"
            + "    ||        ||        ||        ||    \n"
            + "    ||        ||        ||        ||    \n"
            + "  ***It*    ****t* -- *M*Ic* -- *M**cT  \n"
            + "    ||                            ||    \n"
            + "    ||                            ||    \n"
            + "  ****c* -- ***It* -- ****t* -- ***Ic*  \n"
            + "    ||                            ||    \n"
            + "    ||                            ||    \n"
            + "  ****t* -- *M**cT -- ****cT -- EM*Ic*  \n"
            + "              ||        ||        ||    \n"
            + "              ||        ||        ||    \n"
            + "  ***IcT -- ***IcT -- ****t*    ***IcT  \n"
            + "                                        \n",
        nonWrappingSampleForSmell.toString());
    Odour smell = nonWrappingSampleForSmell.getSmellAtPlayerLocation();
    assertEquals(ODOURLESS, smell);
  }

  /**
   * Look at dump for clarity.
   * Test Odour on non-wrapping.
   * Slightly pungent.
   */
  @Test
  public void testSlightlyPungentNonWrapping() {
    assertEquals(
        "                                        \n"
            + "  S*PIc*    ****t* -- ***It*    *M**c*  \n"
            + "    ||        ||        ||        ||    \n"
            + "    ||        ||        ||        ||    \n"
            + "  ***It*    ****t* -- *M*Ic* -- *M**cT  \n"
            + "    ||                            ||    \n"
            + "    ||                            ||    \n"
            + "  ****c* -- ***It* -- ****t* -- ***Ic*  \n"
            + "    ||                            ||    \n"
            + "    ||                            ||    \n"
            + "  ****t* -- *M**cT -- ****cT -- EM*Ic*  \n"
            + "              ||        ||        ||    \n"
            + "              ||        ||        ||    \n"
            + "  ***IcT -- ***IcT -- ****t*    ***IcT  \n"
            + "                                        \n",
        nonWrappingSampleForSmell.toString());
    nonWrappingSampleForSmell.movePlayer(SOUTH);
    nonWrappingSampleForSmell.movePlayer(SOUTH);
    Odour smell = nonWrappingSampleForSmell.getSmellAtPlayerLocation();
    assertEquals(LESS_PUNGENT, smell);
  }

  /**
   * Look at dump for clarity.
   * Test Odour on non-wrapping.
   * Very pungent.
   * 2 monsters 2 distance away.
   */
  @Test
  public void testVeryPungentNonWrapping2Monsters() {
    assertEquals(
        "                                        \n"
            + "  S*PIc*    ****t* -- ***It*    *M**c*  \n"
            + "    ||        ||        ||        ||    \n"
            + "    ||        ||        ||        ||    \n"
            + "  ***It*    ****t* -- *M*Ic* -- *M**cT  \n"
            + "    ||                            ||    \n"
            + "    ||                            ||    \n"
            + "  ****c* -- ***It* -- ****t* -- ***Ic*  \n"
            + "    ||                            ||    \n"
            + "    ||                            ||    \n"
            + "  ****t* -- *M**cT -- ****cT -- EM*Ic*  \n"
            + "              ||        ||        ||    \n"
            + "              ||        ||        ||    \n"
            + "  ***IcT -- ***IcT -- ****t*    ***IcT  \n"
            + "                                        \n",
        nonWrappingSampleForSmell.toString());
    nonWrappingSampleForSmell.movePlayer(SOUTH);
    nonWrappingSampleForSmell.movePlayer(SOUTH);
    nonWrappingSampleForSmell.movePlayer(EAST);
    nonWrappingSampleForSmell.movePlayer(EAST);
    Odour smell = nonWrappingSampleForSmell.getSmellAtPlayerLocation();
    assertEquals(MORE_PUNGENT, smell);
  }


  /**
   * Look at dump for clarity.
   * Test Odour on non-wrapping.
   * Very pungent.
   * 1 monsters 1 distance away.
   */
  @Test
  public void testVeryPungentNonWrapping1Monsters() {
    assertEquals(
        "                                        \n"
            + "  S*PIc*    ****t* -- ***It*    *M**c*  \n"
            + "    ||        ||        ||        ||    \n"
            + "    ||        ||        ||        ||    \n"
            + "  ***It*    ****t* -- *M*Ic* -- *M**cT  \n"
            + "    ||                            ||    \n"
            + "    ||                            ||    \n"
            + "  ****c* -- ***It* -- ****t* -- ***Ic*  \n"
            + "    ||                            ||    \n"
            + "    ||                            ||    \n"
            + "  ****t* -- *M**cT -- ****cT -- EM*Ic*  \n"
            + "              ||        ||        ||    \n"
            + "              ||        ||        ||    \n"
            + "  ***IcT -- ***IcT -- ****t*    ***IcT  \n"
            + "                                        \n",
        nonWrappingSampleForSmell.toString());
    nonWrappingSampleForSmell.movePlayer(SOUTH);
    nonWrappingSampleForSmell.movePlayer(SOUTH);
    nonWrappingSampleForSmell.movePlayer(SOUTH);
    Odour smell = nonWrappingSampleForSmell.getSmellAtPlayerLocation();
    assertEquals(MORE_PUNGENT, smell);
  }


  /**
   * Look at dump for clarity.
   * Test Odour on wrapping.
   * Odourless.
   * 2 monsters 1 distance away.
   */
  @Test
  public void testNoSmellWrapping() {
    wrappingSampleForSmell.movePlayer(WEST);
    wrappingSampleForSmell.shootArrow(WEST, 1);
    wrappingSampleForSmell.shootArrow(WEST, 1);
    wrappingSampleForSmell.movePlayer(WEST);
    wrappingSampleForSmell.movePlayer(WEST);
    assertEquals(
        "              ||        ||              \n"
            + "- ***Ic*    ***It*    *M**cT -- *M*Ic* -\n"
            + "              ||        ||        ||    \n"
            + "              ||        ||        ||    \n"
            + "- ****t*    ***It*    ****cT    ***It* -\n"
            + "    ||        ||                        \n"
            + "    ||        ||                        \n"
            + "- ****t*    ***It* -- ****c* -- EM*Ic* -\n"
            + "                        ||        ||    \n"
            + "                        ||        ||    \n"
            + "- ****t*    ****t* -- ****t*    ****t* -\n"
            + "    ||        ||                        \n"
            + "    ||        ||                        \n"
            + "  **PIt* -- ***Ic* -- ****cT -- S**IcT  \n"
            + "              ||        ||              \n",
        wrappingSampleForSmell.toString()
    );
    Odour smell = wrappingSampleForSmell.getSmellAtPlayerLocation();
    assertEquals(ODOURLESS, smell);
  }

  /**
   * Look at dump for clarity.
   * Test Odour on wrapping.
   * Less_pungent.
   * 2 monsters 1 distance away.
   */
  @Test
  public void testSlightlyPungentWrapping() {
    wrappingSampleForSmell.movePlayer(WEST);
    wrappingSampleForSmell.shootArrow(WEST, 1);
    wrappingSampleForSmell.shootArrow(WEST, 1);
    wrappingSampleForSmell.movePlayer(WEST);
    wrappingSampleForSmell.movePlayer(WEST);
    wrappingSampleForSmell.movePlayer(NORTH);
    assertEquals(
        "              ||        ||              \n"
            + "- ***Ic*    ***It*    *M**cT -- *M*Ic* -\n"
            + "              ||        ||        ||    \n"
            + "              ||        ||        ||    \n"
            + "- ****t*    ***It*    ****cT    ***It* -\n"
            + "    ||        ||                        \n"
            + "    ||        ||                        \n"
            + "- ****t*    ***It* -- ****c* -- EM*Ic* -\n"
            + "                        ||        ||    \n"
            + "                        ||        ||    \n"
            + "- **P*t*    ****t* -- ****t*    ****t* -\n"
            + "    ||        ||                        \n"
            + "    ||        ||                        \n"
            + "  ***It* -- ***Ic* -- ****cT -- S**IcT  \n"
            + "              ||        ||              \n",
        wrappingSampleForSmell.toString()
    );
    Odour smell = wrappingSampleForSmell.getSmellAtPlayerLocation();
    assertEquals(LESS_PUNGENT, smell);
  }

  /**
   * Look at dump for clarity.
   * Test Odour on wrapping.
   * Very pungent.
   * 2 monsters 2 distance away.
   */
  @Test
  public void testVeryPungentWrapping2Monsters() {

    assertEquals(
        "              ||        ||              \n"
            + "- ***Ic*    ***It*    *M**cT -- *M*Ic* -\n"
            + "              ||        ||        ||    \n"
            + "              ||        ||        ||    \n"
            + "- ****t*    ***It*    ****cT    ***It* -\n"
            + "    ||        ||                        \n"
            + "    ||        ||                        \n"
            + "- ****t*    ***It* -- ****c* -- EM*Ic* -\n"
            + "                        ||        ||    \n"
            + "                        ||        ||    \n"
            + "- ****t*    ****t* -- ****t*    ****t* -\n"
            + "    ||        ||                        \n"
            + "    ||        ||                        \n"
            + "  ***It* -- *M*Ic* -- ****cT -- S*PIcT  \n"
            + "              ||        ||              \n",
        wrappingSampleForSmell.toString()
    );
    Odour smell = wrappingSampleForSmell.getSmellAtPlayerLocation();
    assertEquals(MORE_PUNGENT, smell);
  }

  /**
   * Look at dump for clarity.
   * Test Odour on wrapping.
   * Very pungent.
   * 2 monsters 1 distance away.
   */
  @Test
  public void testVeryPungentWrapping1Monster() {
    wrappingSampleForSmell.movePlayer(WEST);
    wrappingSampleForSmell.shootArrow(WEST, 1);
    wrappingSampleForSmell.shootArrow(WEST, 1);
    assertEquals(
        "              ||        ||              \n"
            + "- ***Ic*    ***It*    *M**cT -- *M*Ic* -\n"
            + "              ||        ||        ||    \n"
            + "              ||        ||        ||    \n"
            + "- ****t*    ***It*    ****cT    ***It* -\n"
            + "    ||        ||                        \n"
            + "    ||        ||                        \n"
            + "- ****t*    ***It* -- ****c* -- EM*Ic* -\n"
            + "                        ||        ||    \n"
            + "                        ||        ||    \n"
            + "- ****t*    ****t* -- ****t*    ****t* -\n"
            + "    ||        ||                        \n"
            + "    ||        ||                        \n"
            + "  ***It* -- ***Ic* -- **P*cT -- S**IcT  \n"
            + "              ||        ||              \n",
        wrappingSampleForSmell.toString()
    );
    Odour smell = wrappingSampleForSmell.getSmellAtPlayerLocation();
    assertEquals(MORE_PUNGENT, smell);
  }

  /**
   * Check that player smells more pungent when with an injured monster.
   */
  @Test
  public void testSmellWhenPlayerWithInjuredMonster() {
    //Monster to the north of start location.
    int[] varargs = new int[]{
        14, 7, 7, 1, 45, 33, 36, 52, 61, 33, 18, 15, 43, 14, 27, 12, 22, 6, 31, 7, 11,
        4, 10, 18, 3, 18, 13, 25, 23, 17, 21, 26, 25, 23, 17, 19, 1, 0, 1, 8, 10, 3, 0,
        8, 8, 12, 7, 3, 10, 3, 1, 2, 6, 5, 3, 1, 2, 1, 4, 0, 0, 75, 28, 63, 39, 3, 65,
        25, 1, 47, 36, 45, 56, 51, 53, 38, 46, 17, 40, 2, 26, 7, 3, 29, 5, 13, 8, 26,
        16, 22, 13, 1, 2, 13, 22, 3, 21, 16, 5, 1, 5, 1, 2, 1, 1, 3, 2, 3, 0, 1, 3, 1,
        6, 2, 3, 3, 3, 2, 2, 3, 6, 2, 2, 2, 13, 2, 3, 3, 18, 3, 14, 4, 5, 2, 5, 5, 5,
        1, 10, 2, 7, 2, 6, 3, 3, 4, 6, 2, 9, 3, 1, 2, 2
    };
    Game game = new DungeonGame(5, 5, 50, 5, false, 3,
        varargs
    );
    // // Player survives in game, future given by varargs.
    game.shootArrow(NORTH, 1); //to reduce monster's health.
    game.movePlayer(NORTH);
    Odour smell = game.getSmellAtPlayerLocation();
    assertEquals(MORE_PUNGENT, smell);
  }

  /**
   * Test that there are n monsters as expected.
   * We assert that the map of the dungeons is as expected.
   * The map will have n 'M's which represent monsters.
   */
  @Test
  public void nMonsters() {
    Game game = new DungeonGame(5, 5, 50, 7, false, 3,
        55,39,49,38,30,20,3,38,37,16,24,29,51,21,34,18,12,17,41,15,
        29,27,9,8,11,9,11,2,13,16,19,17,9,15,21,5,14,16,22,0,0,18,11,3,1,7,
        4,8,4,10,1,5,1,3,3,1,1,1,1,7,1,2,3,3,2,3,2,1,1,2,2,6,3,3,1,8,3,3,2,4,
        1,2,1,6,2,20,2,2,4,20,2,5,5,1,5,13,4,0,2,13,5,6,4,4,5,4,4,1,0,1,5,5,0
        );
    assertEquals("Dungeon map not as expected! It should have 7 'M's",
        "                                                  \n"
            + "  ***Ic*    *M*Ic*    *M*IcT    ****t* -- ****t*  \n"
            + "    ||        ||        ||        ||        ||    \n"
            + "    ||        ||        ||        ||        ||    \n"
            + "  ****c* -- ***IcT -- ***IcT -- EM**c* -- ***It*  \n"
            + "    ||                            ||              \n"
            + "    ||                            ||              \n"
            + "  ***It*    *M*Ic*    ****t* -- *M**cT -- ****cT  \n"
            + "    ||        ||        ||        ||              \n"
            + "    ||        ||        ||        ||              \n"
            + "  *M**cT -- *M**cT -- ***It*    ****c* -- ***It*  \n"
            + "    ||                            ||        ||    \n"
            + "    ||                            ||        ||    \n"
            + "  S*P*c*    ***IcT -- ****t* -- ***Ic* -- ****t*  \n"
            + "                                                  \n", game.toString());
  }
}