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
import static junit.framework.Assert.assertSame;
import static junit.framework.Assert.assertTrue;

import dungeon.DungeonGame;
import dungeon.Game;
import dungeongeneral.LocationDesc;
import dungeongeneral.Odour;
import dungeongeneral.ShotResult;
import org.junit.Before;
import org.junit.Test;

/**
 * Game tests related to monsters.
 * Odour tests and player death tests.
 */
public class GameTestsMonsters {

  private Game sampleGame;
  private Game sampleGame2;

  /**
   * Setup for this test suite.
   */
  @Before
  public void setUp() {
    sampleGame = new DungeonGame(5, 4, 50, 5, false, 3,
        43,28,14,23,42,38,3,9,31,4,37,16,13,29,20,20,13,16,11,10,
        20,22,2,6,15,14,13,12,15,3,2,0,1,4,2,1,2,10,3,1,1,7,3,1,1,7,2,1,1,
        6,2,3,2,3,1,1,3,17,2,9,3,4,4,0,4,15,1,12,2,1,1,3,2,6,4,9,4,2,7,5,0,
        2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2
    );
    sampleGame2 = new DungeonGame(5, 4, 50, 4, true, 3,
        4,77,50,65,34,35,41,58,23,16,34,13,11,34,37,7,9,38,25,5,
        20,33,13,11,13,12,9,10,14,0,4,9,2,1,4,1,3,6,6,1,2,1,1,1,5,2,1,2,2,
        2,3,1,3,2,2,1,11,2,15,1,5,4,14,5,8,1,6,3,3,3,0,5,11,4,0,1,4,4,2,2,2,
            2,2,2,2,2,2,2,2,2,2,2,2,2,2,2
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
    game.move(NORTH);
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
    // Same games with different future.
    // Player dies in game 1, future given by varargs.
    game1.shoot(NORTH, 1); //to reduce monster's health.
    game1.move(NORTH);
    assertTrue(game1.isGameOver());
    assertFalse(game1.hasPlayerWon());
    assertTrue(
            game1.getLocationDesc().hasInjuredMonster()
    ); // Player description shows that there is an injured monster there.
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
    // Player survives in game 2, future given by varargs.
    game2.shoot(NORTH, 1); // to reduce monster's health.
    game2.move(NORTH);
    assertFalse(game2.isGameOver());
    assertTrue(
            game2.getLocationDesc().hasInjuredMonster()
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
    ShotResult res1 = game.shoot(EAST, 2);
    assertEquals(HIT, res1);
    ShotResult res2 = game.shoot(EAST, 2);
    assertEquals(KILL, res2);
    ShotResult res3 = game.shoot(EAST, 2);
    assertEquals(MISS, res3);
    assertEquals(
        "Player info:\n"
                + "Misses: 1\n"
                + "Hits: 2\n"
                + "Kills: 1\n"
                + "Treasure:\n"
                + "None\n"
                + "Items:\n"
                + "1 bow \n",
        game.getPlayerDesc().toString()
    );
    // We go to the location and check if it has a dead monster using the location description.
    game.move(EAST);
    game.move(EAST);
    game.move(SOUTH);
    game.move(SOUTH);
    game.move(WEST);
    game.move(WEST);
    assertTrue(
            game.getLocationDesc().hasDeadMonster()
    ); // Player description shows that there is a dead monster there.

  }

  /**
   * Test zero shoot distance.
   */
  @Test (expected = IllegalArgumentException.class)
  public void testShootDistanceZero() {
    Game game = new DungeonGame(5, 5, 50, 5, false, 3);
    game.shoot(NORTH, 0);
  }

  /**
   * Test negative shoot distance.
   */
  @Test (expected = IllegalArgumentException.class)
  public void testShootDistanceNegative() {
    Game game = new DungeonGame(5, 5, 50, 5, false, 3);
    game.shoot(NORTH, -5);
  }

  /**
   * Test null shoot direction.
   */
  @Test (expected = IllegalArgumentException.class)
  public void testShootDirectionNull() {
    Game game = new DungeonGame(5, 5, 50, 5, false, 3);
    game.shoot(null, 3);
  }

  /**
   * Test shot miss.
   * Non-wrapping.
   * Arrows stops at one point because of no opposite direction in some cave.
   */
  @Test
  public void shotMissLongDistanceNonWrapping() {
    Game game = new DungeonGame(5, 5, 50, 5, false, 3);
    // Very long distanced shot stops in some cave, hence misses. [in non wrapping dungeon]
    ShotResult res = game.shoot(NORTH, 200);
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
        2,8,2,3,1,5,2,1,1,7,3,2,3,5,4,7,1,2,3,0,2,11,3,14,4,9,4,6,3,14,2,4,3,0,1,12,2,2,3,7,4
    );
    // no monster at distance 1 to the south. [arrow distance]
    ShotResult res = game.shoot(SOUTH, 1);
    assertEquals(MISS, res);
    // lets go there and check if there is any monster.
    // d = 1
    assertEquals(
            "This is a cave\n"
                    + "Coordinates: (2, 4)\n"
                    + "Possible routes: S \n",
            game.getLocationDesc().toString()
    );
    game.move(SOUTH);
    // This is a tunnel
    // d = 1
    assertEquals(
            "This is a tunnel\n"
                    + "Coordinates: (3, 4)\n"
                    + "Possible routes: N W \n"
                    + "There are some items in this cave: 4 crooked arrows \n",
            game.getLocationDesc().toString()
    );
    game.move(WEST);
    // This is a cave
    // d = 0
    assertEquals(
            "This is a cave\n"
                    + "Coordinates: (3, 3)\n"
                    + "Possible routes: E S W \n",
            game.getLocationDesc().toString()
    );
    assertTrue(game.getLocationDesc().hasNoMonster());
    // This is no monster here.
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
    // monster at distance 1 to the south. [arrow distance]
    ShotResult res = game.shoot(SOUTH, 1);
    assertEquals(HIT, res);
    // lets go there and check if there is an injured monster.
    // d = 1
    assertEquals(
            "This is a cave\n"
            + "Coordinates: (2, 4)\n"
            + "Possible routes: S \n",
            game.getLocationDesc().toString()
    );
    game.move(SOUTH);
    // This is a tunnel
    // d = 1
    assertEquals(
            "This is a tunnel\n"
            + "Coordinates: (3, 4)\n"
            + "Possible routes: N W \n"
            + "There are some items in this cave: 4 crooked arrows \n",
            game.getLocationDesc().toString()
    );
    game.move(WEST);
    // This is a cave
    // d = 0
    assertEquals(
            "This is a cave\n"
                    + "Coordinates: (3, 3)\n"
                    + "Possible routes: E S W \n"
                    + "There is an injured monster here.\n",
            game.getLocationDesc().toString()
    );
    // This is the monster we injured.
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
    // monster at distance 1 to the south. [arrow distance]
    assertSame(game.smell(), LESS_PUNGENT);
    game.shoot(SOUTH, 1);
    ShotResult res = game.shoot(SOUTH, 1);
    assertEquals(KILL, res);
    game.move(SOUTH);
    // This is a tunnel
    // d = 1
    assertEquals(
            "This is a tunnel\n"
                    + "Coordinates: (3, 4)\n"
                    + "Possible routes: N W \n"
                    + "There are some items in this cave: 4 crooked arrows \n",
            game.getLocationDesc().toString()
    );
    game.move(WEST);
    // This is a cave
    // d = 0
    assertEquals(
            "This is a cave\n"
                    + "Coordinates: (3, 3)\n"
                    + "Possible routes: E S W \n"
                    + "There is a dead monster here.\n",
            game.getLocationDesc().toString()
    );
    // This is the monster we injured.
  }

  /**
   * Test shot hit for long distance on wrapping dungeon.
   * we move to the part of dungeon where the arrow can loop
   * and shoot in the looping direction. The arrow eventually
   * covers the given distance and stops at a cave. If the cave has
   * a monster it hits else it misses.
   * Arrows reaches specified distance.
   * Test that arrow injures monster
   */
  @Test
  public void shotHitLongDistanceWrapping() {
    Game game = new DungeonGame(5, 5, 50, 5, true, 15,
        43,9,26,41,15,26,73,75,65,0,72,15,28,33,22,4,23,48,27,25,34,28,41,40,26,4,31,10,
        4,19,28,32,11,30,6,12,14,26,23,34,26,4,8,24,20,7,21,13,13,6,11,8,7,13,16,3,10,13,8,10,5,
        2,0,0,6,4,5,1,1,0,0,4,3,1,1,18,3,1,3,19,2,1,3,13,3,1,2,16,2,1,1,7,3,2,3,11,1,2,3,13,1,2,
        3,13,2,2,2,12,3,3,2,9,1,3,3,9,3,6,4,13,5,12,3,16,3,17,1,3,1,2,3,8,5,7,3,14,4,4,3,0,8,8,1,
        2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2
    );
    game.move(SOUTH);
    game.move(WEST);
    game.move(SOUTH);
    // This pseudo random dungeon has a loop in the east west direction here.
    // Very long distanced shot in a wrapping dungeon
    // hits if monster exists at arrow's destination.
    ShotResult res = game.shoot(WEST, 52);
    assertEquals(HIT, res);
    // Since it is a hit, we can the player in the arrows expected path,
    // and check if the final destination contains a dead monster.
    // Player will not die because of the injured monster because the pseudo random
    // numbers keeps him alive, specifically, the 2s in the end.
    int d = 52;
    while (d != 0) {
      game.move(WEST);
      if (game.getLocationDesc().isCave()) {
        d--;
      }
    }
    // also tests that monster is half injured
    assertTrue(game.getLocationDesc().hasInjuredMonster());
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
        3,13,2,2,2,12,3,3,2,9,1,3,3,9,3,6,4,13,5,12,3,16,3,17,1,3,1,2,3,8,5,7,3,14,4,4,3,0,8,8,10,
        2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2
    );
    game.move(SOUTH);
    game.move(WEST);
    game.move(SOUTH);
    // Same dungeon as before but no monster in the same location.
    // This pseudo random dungeon has a loop in the east west direction here.
    // Very long distanced shot in a wrapping dungeon
    // hits if monster exists at arrow's destination.
    ShotResult res = game.shoot(WEST, 52);
    assertEquals(MISS, res);
    // monster does not exists at that location.
    // lets go there and check.
    int d = 52;
    while (d != 0) {
      game.move(WEST);
      if (game.getLocationDesc().isCave()) {
        d--;
      }
    }
    assertTrue(game.getLocationDesc().hasNoMonster());
    // No monster, hence a miss was expected.
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
    game.move(SOUTH);
    game.move(WEST);
    game.move(SOUTH);
    // This pseudo-random
    // Very long distanced shot in a wrapping dungeon
    // hits if monster exists at arrow's destination.
    // same monster can be hit with different input distances.
    ShotResult res1 = game.shoot(WEST, 52);
    ShotResult res2 = game.shoot(WEST, 37);
    assertEquals(HIT, res1);
    assertEquals(KILL, res2);
    int d = 52;
    while (d != 0) {
      game.move(WEST);
      if (game.getLocationDesc().isCave()) {
        d--;
      }
    }
    LocationDesc d52 = game.getLocationDesc();
    assertEquals(
            "This is a cave\n"
            + "Coordinates: (2, 1)\n"
            + "Possible routes: E S W \n"
            + "There are some items in this cave: 3 crooked arrows \n"
            + "There is a dead monster here.\n",
            d52.toString()
    );
    assertTrue(d52.hasDeadMonster());
    // Lets go back to where we came from.
    while (d != 52) {
      game.move(EAST);
      if (game.getLocationDesc().isCave()) {
        d++;
      }
    }
    // Now lets travel 37 arrow distance towards west and check
    d = 37;
    while (d != 0) {
      game.move(WEST);
      if (game.getLocationDesc().isCave()) {
        d--;
      }
    }
    LocationDesc d37 = game.getLocationDesc();
    assertEquals(
            "This is a cave\n"
                    + "Coordinates: (2, 1)\n"
                    + "Possible routes: E S W \n"
                    + "There are some items in this cave: 3 crooked arrows \n"
                    + "There is a dead monster here.\n",
            d37.toString()
    );
    // Tests that the arrow hits have killed the monster
    assertTrue(d37.hasDeadMonster());
    // in fact, d52 and d37 have the same coordinates
    assertEquals(d37.getCoordinates(), d52.getCoordinates());
  }

  /**
   * deterministic dungeon
   * Test Odour on non-wrapping.
   * No Smell
   */
  @Test
  public void testNoSmellNonWrapping() {
    Odour smell = sampleGame.smell();
    assertEquals(ODOURLESS, smell);
    LocationDesc des = sampleGame.getLocationDesc();
    assertEquals(
            "This is a cave\n"
                    + "Coordinates: (0, 0)\n"
                    + "Possible routes: S \n"
                    + "There are some items in this cave: 4 crooked arrows \n",
            des.toString()
    );
    // Only one possible route to south
    sampleGame.move(SOUTH);
    des = sampleGame.getLocationDesc();
    assertTrue(des.hasNoMonster());
    assertEquals(
            "This is a tunnel\n"
                    + "Coordinates: (1, 0)\n"
                    + "Possible routes: N S \n"
                    + "There are some items in this cave: 4 crooked arrows \n",
            des.toString()
    );
    // no monster here.
    // go further south. [we came from the north]
    sampleGame.move(SOUTH);
    des = sampleGame.getLocationDesc();
    assertTrue(des.hasNoMonster());
    assertEquals(
            "This is a cave\n"
                    + "Coordinates: (2, 0)\n"
                    + "Possible routes: N E S \n",
            des.toString()
    );
    // no monster here, hence ODOURLESS is correct at (0, 0)
  }

  /**
   * deterministic dungeon
   * Test Odour on non-wrapping.
   * Slightly pungent.
   */
  @Test
  public void testSlightlyPungentNonWrapping() {
    sampleGame.move(SOUTH);
    LocationDesc des = sampleGame.getLocationDesc();
    assertEquals(
            "This is a tunnel\n"
                    + "Coordinates: (1, 0)\n"
                    + "Possible routes: N S \n"
                    + "There are some items in this cave: 4 crooked arrows \n",
            des.toString()
    );
    assertTrue(des.hasNoMonster());
    sampleGame.move(SOUTH);
    des = sampleGame.getLocationDesc();
    assertEquals(
            "This is a cave\n"
            + "Coordinates: (2, 0)\n"
            + "Possible routes: N E S \n",
            des.toString()
    );
    assertTrue(des.hasNoMonster());
    Odour smell = sampleGame.smell();
    assertEquals(LESS_PUNGENT, smell);
    // less pungent smell here, indicates one monster in 2 distance from current location.
    // we covered the north, because we came from there.
    // lets look at east and south.
    sampleGame.move(EAST);
    des = sampleGame.getLocationDesc();
    assertEquals(
            "This is a tunnel\n"
            + "Coordinates: (2, 1)\n"
            + "Possible routes: E W \n"
            + "There are some items in this cave: 3 crooked arrows \n",
            des.toString()
    );
    assertTrue(des.hasNoMonster());
    sampleGame.move(EAST);
    des = sampleGame.getLocationDesc();
    assertEquals(
            "This is a tunnel\n"
            + "Coordinates: (2, 2)\n"
            + "Possible routes: E W \n",
            des.toString()
    );
    assertTrue(des.hasNoMonster());
    // lets go back and check the south.
    sampleGame.move(WEST);
    sampleGame.move(WEST);
    sampleGame.move(SOUTH);
    des = sampleGame.getLocationDesc();
    assertEquals(
            "This is a tunnel\n"
            + "Coordinates: (3, 0)\n"
            + "Possible routes: N E \n",
            des.toString()
    );
    assertTrue(des.hasNoMonster());
    sampleGame.move(EAST);
    des = sampleGame.getLocationDesc();
    assertTrue(des.hasHealthyMonster());
    assertEquals(
            "This is a cave\n"
            + "Coordinates: (3, 1)\n"
            + "Possible routes: E S W \n"
            + "There's some treasure in this cave: 1 diamond 1 ruby 3 sapphires \n"
            + "There is an alive monster here.\n",
            des.toString()
    );
    // this is the location with the monster, at a distance of 2
    // from the distance we smelled less pungent.
  }

  /**
   * deterministic dungeon
   * Test Odour on non-wrapping.
   * Very pungent.
   * 2 monsters 2 distance away.
   */
  @Test
  public void testVeryPungentNonWrapping2Monsters() {
    sampleGame.move(SOUTH);
    sampleGame.move(SOUTH);
    sampleGame.move(EAST);
    Odour smell = sampleGame.smell();
    assertEquals(ODOURLESS, smell);
    sampleGame.move(EAST);
    smell = sampleGame.smell();
    assertEquals(MORE_PUNGENT, smell);
    // Two locations from this position, there are 2 monsters, one in each location.
    // Lets go there and check. We injure the monsters and enter their locations.
    // Varargs prevent us from dying.
    sampleGame.move(EAST);
    assertEquals(MORE_PUNGENT, smell);
    assertEquals(
            "This is a cave\n"
                    + "Coordinates: (2, 3)\n"
                    + "Possible routes: N S W \n"
                    + "There are some items in this cave: 4 crooked arrows \n",
            sampleGame.getLocationDesc().toString()
    );
    // Since we came from the west, there's one monster in the north and one in the south.
    assertSame(HIT, sampleGame.shoot(NORTH, 1));
    assertSame(HIT, sampleGame.shoot(SOUTH, 1));
    sampleGame.move(NORTH);
    LocationDesc des = sampleGame.getLocationDesc();
    assertTrue(des.hasInjuredMonster());
    assertEquals(
            "This is a cave\n"
                    + "Coordinates: (1, 3)\n"
                    + "Possible routes: N S W \n"
                    + "There's some treasure in this cave: 3 diamonds 1 ruby 1 sapphire \n"
                    + "There is an injured monster here.\n",
            des.toString()
    );
    // we found the monster to the north, lets go south.
    sampleGame.move(SOUTH);
    sampleGame.move(SOUTH);
    des = sampleGame.getLocationDesc();
    assertTrue(des.hasInjuredMonster());
    assertEquals(
            "This is a cave\n"
                    + "Coordinates: (3, 3)\n"
                    + "Possible routes: N S W \n"
                    + "There are some items in this cave: 2 crooked arrows \n"
                    + "There is an injured monster here.\n",
            des.toString()
    );
    // We found the monster here as well
    // understandability, the smell was MORE_PUNGENT
  }


  /**
   * Deterministic dungeon.
   * Test Odour on non-wrapping.
   * Very pungent.
   * 1 monsters 1 distance away.
   */
  @Test
  public void testVeryPungentNonWrapping1Monsters() {
    sampleGame.move(SOUTH);
    sampleGame.move(SOUTH);
    sampleGame.move(SOUTH);
    Odour smell = sampleGame.smell();
    assertEquals(MORE_PUNGENT, smell);
    // There is a monster in one of the neighbours. [pseudo random dungeon]
    LocationDesc des = sampleGame.getLocationDesc();
    assertEquals(
            "This is a tunnel\n"
            + "Coordinates: (3, 0)\n"
            + "Possible routes: N E \n",
            des.toString()
    );
    sampleGame.move(EAST);
    des = sampleGame.getLocationDesc();
    assertEquals(
            "This is a cave\n"
            + "Coordinates: (3, 1)\n"
            + "Possible routes: E S W \n"
            + "There's some treasure in this cave: 1 diamond 1 ruby 3 sapphires \n"
            + "There is an alive monster here.\n",
            des.toString()
    );

  }

  /**
   * deterministic dungeon
   * Test Odour on wrapping.
   * Odourless.
   * 2 monsters 1 distance away.
   */
  @Test
  public void testNoSmellWrapping() {
    sampleGame2.move(WEST);
    sampleGame2.shoot(WEST, 1);
    sampleGame2.shoot(WEST, 1);
    sampleGame2.move(WEST);
    sampleGame2.move(WEST);

    // Deterministic dungeon
    // This location has no odour.

    Odour smell = sampleGame2.smell();
    assertEquals(ODOURLESS, smell);
    // This location has no odour, we go to all neighbours and neighbours' neighbours and
    // assert that there are no monsters/ only dead monsters.
    LocationDesc des = sampleGame2.getLocationDesc();
    assertEquals(
            "This is a tunnel\n"
                    + "Coordinates: (4, 0)\n"
                    + "Possible routes: N E \n"
                    + "There are some items in this cave: 1 crooked arrow \n",
            des.toString());
    // Covering east and its neighbours first.
    sampleGame2.move(EAST);
    des = sampleGame2.getLocationDesc();
    assertEquals(
            "This is a cave\n"
                    + "Coordinates: (4, 1)\n"
                    + "Possible routes: N E S W \n"
                    + "There are some items in this cave: 5 crooked arrows \n"
                    + "There is a dead monster here.\n",
            des.toString());
    sampleGame2.move(SOUTH);
    des = sampleGame2.getLocationDesc();
    assertEquals(
            "This is a tunnel\n"
                    + "Coordinates: (0, 1)\n"
                    + "Possible routes: N S \n"
                    + "There are some items in this cave: 1 crooked arrow \n",
            des.toString());
    sampleGame2.move(NORTH);
    sampleGame2.move(NORTH);
    des = sampleGame2.getLocationDesc();
    assertEquals(
            "This is a tunnel\n"
                    + "Coordinates: (3, 1)\n"
                    + "Possible routes: E S \n",
            des.toString());
    sampleGame2.move(SOUTH);
    sampleGame2.move(EAST);
    des = sampleGame2.getLocationDesc();
    assertEquals(
            "This is a cave\n"
                    + "Coordinates: (4, 2)\n"
                    + "Possible routes: E S W \n"
                    + "There's some treasure in this cave: 2 diamonds 3 rubies 1 sapphire \n",
            des.toString());
    sampleGame2.move(WEST);
    sampleGame2.move(WEST);
    // Now cover north and its neighbours
    sampleGame2.move(NORTH);
    des = sampleGame2.getLocationDesc();
    assertEquals(
            "This is a tunnel\n"
                    + "Coordinates: (3, 0)\n"
                    + "Possible routes: S W \n",
            des.toString());
    sampleGame2.move(WEST);
    des = sampleGame2.getLocationDesc();
    assertEquals(
            "This is a tunnel\n"
                    + "Coordinates: (3, 3)\n"
                    + "Possible routes: N E \n",
            des.toString());
  }

  /**
   * deterministic dungeon
   * Test Odour on wrapping.
   * Less_pungent.
   * 2 monsters 1 distance away.
   */
  @Test
  public void testSlightlyPungentWrapping() {
    sampleGame2.move(WEST);
    sampleGame2.shoot(WEST, 1);
    sampleGame2.shoot(WEST, 1);
    sampleGame2.move(WEST);
    sampleGame2.move(WEST);
    sampleGame2.move(NORTH);


    Odour smell = sampleGame2.smell();
    assertEquals(LESS_PUNGENT, smell);
    // There is one monster 2 distance away.
    // Let us check our neighbours and our neighbours' neighbours.
    LocationDesc des = sampleGame2.getLocationDesc();
    assertEquals(
            "This is a tunnel\n"
                    + "Coordinates: (3, 0)\n"
                    + "Possible routes: S W \n",
            des.toString());
    sampleGame2.move(SOUTH);
    des = sampleGame2.getLocationDesc();
    assertEquals(
            "This is a tunnel\n"
                    + "Coordinates: (4, 0)\n"
                    + "Possible routes: N E \n"
                    + "There are some items in this cave: 1 crooked arrow \n",
            des.toString());
    sampleGame2.move(EAST);
    des = sampleGame2.getLocationDesc();
    assertEquals(
            "This is a cave\n"
                    + "Coordinates: (4, 1)\n"
                    + "Possible routes: N E S W \n"
                    + "There are some items in this cave: 5 crooked arrows \n"
                    + "There is a dead monster here.\n",
            des.toString());
    sampleGame2.move(WEST);
    des = sampleGame2.getLocationDesc();
    assertEquals(
            "This is a tunnel\n"
                    + "Coordinates: (4, 0)\n"
                    + "Possible routes: N E \n"
                    + "There are some items in this cave: 1 crooked arrow \n",
            des.toString());
    sampleGame2.move(NORTH);
    des = sampleGame2.getLocationDesc();
    assertEquals(
            "This is a tunnel\n"
                    + "Coordinates: (3, 0)\n"
                    + "Possible routes: S W \n",
            des.toString());
    sampleGame2.move(WEST);
    des = sampleGame2.getLocationDesc();
    assertEquals(
            "This is a tunnel\n"
                    + "Coordinates: (3, 3)\n"
                    + "Possible routes: N E \n",
            des.toString());
    sampleGame2.move(NORTH);
    des = sampleGame2.getLocationDesc();
    assertEquals(
            "This is a cave\n"
                    + "Coordinates: (2, 3)\n"
                    + "Possible routes: E S W \n"
                    + "There are some items in this cave: 2 crooked arrows \n"
                    + "There is an alive monster here.\n",
            des.toString());
    // This is the monster that was causing the less pungent smell 2 distance away.
  }

  /**
   * deterministic dungeon
   * Test Odour on wrapping.
   * Very pungent.
   * 2 monsters 2 distance away.
   */
  @Test
  public void testVeryPungentWrapping2Monsters() {
    Odour smell = sampleGame2.smell();
    assertEquals(MORE_PUNGENT, smell);

    // This deterministic dungeon has a monster to current location -> west -> west
    // and another at current location -> west -> south.

    LocationDesc dec = sampleGame2.getLocationDesc();
    assertEquals(
            "This is a cave\n"
                    + "Coordinates: (4, 3)\n"
                    + "Possible routes: W \n"
                    + "There's some treasure in this cave: 1 diamond 1 ruby 1 sapphire \n"
                    + "There are some items in this cave: 4 crooked arrows \n",
            dec.toString());
    sampleGame2.move(WEST);
    dec = sampleGame2.getLocationDesc();
    assertEquals(
            "This is a cave\n"
                    + "Coordinates: (4, 2)\n"
                    + "Possible routes: E S W \n"
                    + "There's some treasure in this cave: 2 diamonds 3 rubies 1 sapphire \n",
            dec.toString());
    // Lets shoot both the monsters once so that we do not die.
    sampleGame2.shoot(WEST, 1);
    sampleGame2.shoot(SOUTH, 1);
    sampleGame2.move(WEST);
    dec = sampleGame2.getLocationDesc();
    assertEquals(
            "This is a cave\n"
                    + "Coordinates: (4, 1)\n"
                    + "Possible routes: N E S W \n"
                    + "There are some items in this cave: 5 crooked arrows \n"
                    + "There is an injured monster here.\n",
            dec.toString());
    sampleGame2.move(EAST);
    sampleGame2.move(SOUTH);
    dec = sampleGame2.getLocationDesc();
    assertEquals(
            "This is a cave\n"
                    + "Coordinates: (0, 2)\n"
                    + "Possible routes: N E S \n"
                    + "There's some treasure in this cave: 2 diamonds 1 ruby 2 sapphires \n"
                    + "There is an injured monster here.\n",
            dec.toString());
    // These were the 2 monsters that were causing the MORE_PUNGENT smell

  }

  /**
   * deterministic dungeon
   * Test Odour on wrapping.
   * Very pungent.
   * 2 monsters 1 distance away.
   */
  @Test
  public void testVeryPungentWrapping1Monster() {
    // Kill the monster to the west.
    sampleGame2.move(WEST);
    sampleGame2.shoot(WEST, 1);
    sampleGame2.shoot(WEST, 1);

    // We killed the monster to the west and came from east so,
    // there is only one monster to the immediate south. We know this,
    // because of the more_pungent smell.
    Odour smell = sampleGame2.smell();
    assertEquals(MORE_PUNGENT, smell);
    LocationDesc dec = sampleGame2.getLocationDesc();
    assertEquals(
            "This is a cave\n"
                    + "Coordinates: (4, 2)\n"
                    + "Possible routes: E S W \n"
                    + "There's some treasure in this cave: 2 diamonds 3 rubies 1 sapphire \n",
            dec.toString());

    sampleGame2.move(SOUTH);
    dec = sampleGame2.getLocationDesc();
    assertEquals(
            "This is a cave\n"
                    + "Coordinates: (0, 2)\n"
                    + "Possible routes: N E S \n"
                    + "There's some treasure in this cave: 2 diamonds 1 ruby 2 sapphires \n"
                    + "There is an alive monster here.\n",
            dec.toString());
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
        1, 10, 2, 7, 2, 6, 3, 3, 4, 6, 2, 9, 3, 7, 2, 2
    };
    Game game = new DungeonGame(5, 5, 50, 5, false, 3,
        varargs
    );
    // Player survives in game, future given by varargs.
    game.shoot(NORTH, 1); //to reduce monster's health.
    game.move(NORTH);
    // Go there and check for smell.
    assertEquals(MORE_PUNGENT, game.smell());
    assertTrue(game.getLocationDesc().hasInjuredMonster());
    // Lets kill the monster, come back and check for the smell again to make
    // sure that this monster is the origin of the smell.
    game.move(SOUTH);
    game.shoot(NORTH, 1); // kill
    assertTrue(game.getLocationDesc().hasNoMonster()); // assert no monster
    assertEquals(ODOURLESS, game.smell());
  }
}