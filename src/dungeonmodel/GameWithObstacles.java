package dungeonmodel;

import dungeongeneral.ReadOnlyGameWithObstacles;

/**
 * This is a game with additional obstacles.
 * This game has a moving monster called a Tarrasque.
 * This game has a thief who steals from the player.
 * The player has to fight the Tarrasque hand to hand if they are at the same location.
 * The locations might have pits.
 */
public interface GameWithObstacles extends Game, ReadOnlyGameWithObstacles {

  /**
   * Attack a Tarrasque in the same location.
   */
  void attack();

  /**
   * Returns a new game that has same state as this at the start of this game.
   * @return a new game object.
   */
  GameWithObstacles restart();
}
