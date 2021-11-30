package dungeon;

/**
 * This is a game with additional obstacles.
 * This game has a moving monster called a Tarrasque.
 * This game has a thief who steals from the player.
 * The player has to fight the Tarrasque hand to hand if they are at the same location.
 * The locations might have pits.
 */
public interface GameWithObstacles extends Game{

  /**
   * Attack a Tarrasque in the same location.
   */
  void attack();
}
