package modelwithobstacles;

import static dungeongeneral.Direction.EAST;
import static dungeongeneral.Direction.NORTH;
import static dungeongeneral.Direction.SOUTH;
import static dungeongeneral.Direction.WEST;
import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;

import dungeongeneral.Coordinate;
import dungeongeneral.ReadOnlyLocation;
import dungeongeneral.ReadOnlyPlayer;
import dungeonmodel.DungeonGameWithObstacles;
import dungeonmodel.GameWithObstacles;
import org.junit.Test;

import java.util.List;

/**
 * Test suite for Game with newly added obstacles.
 */
public class GameWithObstaclesTests {

  /**
   * Test if getter methods are working as expected.
   */
  @Test
  public void testGetters() {
    GameWithObstacles game = new DungeonGameWithObstacles(5, 4, 50, 2, true, 2);
    assertEquals(5, game.getRowCount());
    assertEquals(4, game.getColumnCount());
    assertEquals(50, game.getPercentage());
    assertEquals(2, game.getDifficulty());
    assertTrue(game.getEnableWrap());
    assertEquals(2, game.getInterconnectivity());
  }

  /**
   * Player moves to the location if there it is a neighbour.
   */
  @Test
  public void testMoveToLocation() {
    GameWithObstacles game = new DungeonGameWithObstacles(5, 4, 50, 2, true, 2,
            64,76,25,43,69,69,25,19,31,50,59,8,2,33,0,28,41,44,31,4,9,3,34,15,11,9,11,24,34,0,10,
            1,10,0,5,12,8,8,9,3,8,3,2,1,2,2,1,0,1,0,34,56,47,38,32,18,47,29,57,20,47,15,11,31,24,
            43,20,29,41,38,26,31,9,13,30,5,1,2,3,2,3,7,3,3,1,12,3,1,2,1,3,1,2,3,1,3,2,1,1,1,1,8,
            2,3,2,2,4,1,2,8,1,10,1,14,4,3,4,10,1,4,4,0,1,10,4,0,4,17,17,1,1
    );
    ReadOnlyPlayer player = game.getPlayerDesc();
    assertTrue(player.getPossibleRoutes().contains(SOUTH));
    assertEquals("(1, 1)", player.getCoordinates().toString());
    ReadOnlyLocation southToPlayer = game.getLocation(new Coordinate(2, 1));
    game.moveToLocation(southToPlayer);
    assertEquals("(2, 1)", player.getCoordinates().toString());
  }

  /**
   * An illegal argument error is thrown if given location is not a neighbour.
   */
  @Test (expected = IllegalArgumentException.class)
  public void testMoveToLocationIllegalArgs() {
    GameWithObstacles game = new DungeonGameWithObstacles(5, 4, 50, 2, true, 2,
            64,76,25,43,69,69,25,19,31,50,59,8,2,33,0,28,41,44,31,4,9,3,34,15,11,9,11,24,34,0,10,
            1,10,0,5,12,8,8,9,3,8,3,2,1,2,2,1,0,1,0,34,56,47,38,32,18,47,29,57,20,47,15,11,31,24,
            43,20,29,41,38,26,31,9,13,30,5,1,2,3,2,3,7,3,3,1,12,3,1,2,1,3,1,2,3,1,3,2,1,1,1,1,8,
            2,3,2,2,4,1,2,8,1,10,1,14,4,3,4,10,1,4,4,0,1,10,4,0,4,17,17
    );
    ReadOnlyPlayer player = game.getPlayerDesc();
    assertFalse(player.getPossibleRoutes().contains(NORTH));
    assertEquals("(1, 1)", player.getCoordinates().toString());
    ReadOnlyLocation northToPlayer = game.getLocation(new Coordinate(0, 1));
    game.moveToLocation(northToPlayer);
  }


  /**
   * An illegal state error is thrown if method is called after game is over.
   */
  @Test (expected = IllegalStateException.class)
  public void testMoveToLocationIllegalState() {
    GameWithObstacles game = new DungeonGameWithObstacles(5, 4, 50, 2, true, 2,
            64,76,25,43,69,69,25,19,31,50,59,8,2,33,0,28,41,44,31,4,9,3,34,15,11,9,11,24,34,0,10,
            1,10,0,5,12,8,8,9,3,8,3,2,1,2,2,1,0,1,0,34,56,47,38,32,18,47,29,57,20,47,15,11,31,24,
            43,20,29,41,38,26,31,9,13,30,5,1,2,3,2,3,7,3,3,1,12,3,1,2,1,3,1,2,3,1,3,2,1,1,1,1,8,
            2,3,2,2,4,1,2,8,1,10,1,14,4,3,4,10,1,4,4,0,1,10,4,0,4,17,17,1,1,1,1
    );
    ReadOnlyPlayer player = game.getPlayerDesc();
    game.move(SOUTH);
    game.move(WEST);
    ReadOnlyLocation location = game.getLocationDesc();
    // There is an otyugh here hence the player will die.
    assertTrue(game.isGameOver());
    assertTrue(location.hasAliveMonster());
    assertFalse(game.hasPlayerWon());
    // If we call the method now, we get an illegal state exception.
    ReadOnlyLocation anyLocation = game.getLocation(new Coordinate(0, 1));
    game.moveToLocation(anyLocation);
  }

  /**
   * Test that moving monster follows player until the player fights it.
   */
  @Test
  public void movingMonster() {
    GameWithObstacles game = new DungeonGameWithObstacles(4, 4, 50, 3, true, 2,
            61,57,37,47,20,23,50,47,18,27,37,28,3,18,19,12,10,23,14,26,6,9,10,11,4,4,3,6,2,2,4,1,
            0,4,2,3,3,1,2,2,3,1,3,2,2,2,3,1,3,14,4,14,1,9,5,5,3,11,2,4,1,9,2,2,5,3,4,6,2,7,2,3,
            1,3,1,0,0,1,1,1,2,1,3,0,2,2,3
    );
    game.move(SOUTH);
    game.move(EAST);
    assertTrue(game.movingMonsterAliveAtPlayerLocation());
    game.move(SOUTH);
    assertTrue(game.movingMonsterAliveAtPlayerLocation());
    game.move(NORTH);
    assertTrue(game.movingMonsterAliveAtPlayerLocation());
    game.move(SOUTH);
    assertTrue(game.movingMonsterAliveAtPlayerLocation());
    game.move(EAST);
    assertTrue(game.movingMonsterAliveAtPlayerLocation());
  }

  /**
   * Test that attacking the moving monster a bunch of times kills it.
   */
  @Test
  public void testAttack() {
    GameWithObstacles game = new DungeonGameWithObstacles(4, 4, 50, 3, true, 2,
            61,57,37,47,20,23,50,47,18,27,37,28,3,18,19,12,10,23,14,26,6,9,10,11,4,4,3,6,2,2,4,1,
            0,4,2,3,3,1,2,2,3,1,3,2,2,2,3,1,3,14,4,14,1,9,5,5,3,11,2,4,1,9,2,2,5,3,4,6,2,7,2,3,
            1,3,1,0,0,-1,1,1,2,1,3,0,2,2,3
    );
    game.move(SOUTH);
    game.move(EAST);
    assertTrue(game.movingMonsterAliveAtPlayerLocation());
    game.attack();
    assertTrue(game.movingMonsterAliveAtPlayerLocation());
    game.attack();
    assertTrue(game.movingMonsterAliveAtPlayerLocation());
    game.attack();
    assertTrue(game.movingMonsterAliveAtPlayerLocation());
    game.attack();
    assertFalse(game.movingMonsterAliveAtPlayerLocation());
  }

  /**
   * Attacking when there is no monster throws an illegal state exception.
   */
  @Test (expected = IllegalStateException.class)
  public void testAttackIllegalState() {
    GameWithObstacles game = new DungeonGameWithObstacles(4, 4, 50, 3, true, 2,
            61,57,37,47,20,23,50,47,18,27,37,28,3,18,19,12,10,23,14,26,6,9,10,11,4,4,3,6,2,2,4,1,
            0,4,2,3,3,1,2,2,3,1,3,2,2,2,3,1,3,14,4,14,1,9,5,5,3,11,2,4,1,9,2,2,5,3,4,6,2,7,2,3,
            1,3,1,0,0,-1,1,1,2,1,3,0,2,2,3
    );
    game.attack();
  }

  /**
   * Test that generation sequences can be used to create copies of the same dungeon.
   * Same dungeons give the same generation sequence. These sequences can be used to create
   * copies of the dungeon at the start.
   */
  @Test
  public void testGenerationSequencesMatches() {
    GameWithObstacles game1 = new DungeonGameWithObstacles(4, 4, 50, 3, true, 2);
    List<Integer> sequence1 = game1.getGenerationSequence();
    GameWithObstacles game2 = new DungeonGameWithObstacles(4, 4, 50, 3, true, 2, sequence1);
    List<Integer> sequence2 = game2.getGenerationSequence();
    assertEquals(sequence1, sequence2);
  }

  /**
   * Player looses 2 health when he walks into an undiscovered location with a pit.
   */
  @Test
  public void testPitFallDamage() {
    GameWithObstacles game = new DungeonGameWithObstacles(4, 4, 50, 6, true, 2,
            20,27,24,46,46,41,4,46,25,25,7,41,26,6,35,1,10,32,2,12,14,2,7,6,1,2,7,6,1,
            6,3,0,3,1,2,1,0,7,59,13,37,13,31,3,28,6,42,25,1,7,18,33,5,23,18,5,3,4,2,10,9,0,2,8,
            3,1,2,4,2,6,3,2,2,4,1,2,3,5,2,2,1,2,3,1,2,8,4,10,1,5,5,6,5,3,1,3,2,8,4,8,5,5,1,1,1,
            0,12,1,0,5,4,1,0,2,2,1,2,0,2,2,0
    );
    game.move(WEST);
    game.move(SOUTH);
    assertTrue(game.getLocationDesc().hasSignsOfNearbyPit());
    assertEquals(30, game.getPlayerDesc().getHealth());
    game.move(WEST);
    assertTrue(game.getLocationDesc().hasPit());
    assertEquals(28, game.getPlayerDesc().getHealth());
  }

  /**
   * Player does not loose health once the pit is discovered.
   */
  @Test
  public void testPitFallDamageAfterDiscovery() {
    GameWithObstacles game = new DungeonGameWithObstacles(4, 4, 50, 6, true, 2,
            20,27,24,46,46,41,4,46,25,25,7,41,26,6,35,1,10,32,2,12,14,2,7,6,1,2,7,6,1,
            6,3,0,3,1,2,1,0,7,59,13,37,13,31,3,28,6,42,25,1,7,18,33,5,23,18,5,3,4,2,10,9,0,2,8,
            3,1,2,4,2,6,3,2,2,4,1,2,3,5,2,2,1,2,3,1,2,8,4,10,1,5,5,6,5,3,1,3,2,8,4,8,5,5,1,1,1,
            0,12,1,0,5,4,1,0,2,2,1,2,0,2,2,0
    );
    game.move(WEST);
    game.move(SOUTH);
    assertTrue(game.getLocationDesc().hasSignsOfNearbyPit());
    assertEquals(30, game.getPlayerDesc().getHealth());
    game.move(WEST);
    assertTrue(game.getLocationDesc().hasPit());
    assertEquals(28, game.getPlayerDesc().getHealth());
    game.move(EAST);
    game.move(WEST);
    // Comeback to the location again and check that health has not been reduced.
    assertEquals(28, game.getPlayerDesc().getHealth());
  }
}