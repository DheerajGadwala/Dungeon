package dungeonmodel;

import dungeongeneral.ReadOnlyGameWithObstacles;
import dungeongeneral.ReadOnlyLocation;

import java.util.List;

/**
 * This is a game with additional obstacles.
 * This game has a moving monster called a Tarrasque.
 * This game has a thief who steals from the player.
 * The player has to fight the Tarrasque hand to hand if they are at the same location.
 * The locations might have pits.
 */
public interface GameWithObstacles extends Game, ReadOnlyGameWithObstacles {
  /**
   * Returns the sequence required to re generate this dungeon at its start state.
   * @return list of integers required to regenerate this dungeon.
   */
  List<Integer> getGenerationSequence();

  /**
   * Attack a Tarrasque in the same location.
   */
  void attack();

  /**
   * Moves player to the given location.
   * @param location location to which the player is to be moved.
   * @throws IllegalArgumentException if the given location is null or it
   *                                is not possible to move to that location.
   * @throws IllegalStateException If the game is over.
   */
  void moveToLocation(ReadOnlyLocation location)
          throws IllegalArgumentException, IllegalStateException;
}
