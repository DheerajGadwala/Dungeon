package dungeon;

/**
 * This represents a monster.
 * A monster is in a location.
 * A monster can attacks players in it's location.
 * Monster attack on player might result in the death of the player.
 * A monster can loose health.
 */
interface Monster {

  /**
   * Returns true if the monster is alive.
   * @return true if monster's health is not zero.
   */
  boolean isAlive();

  /**
   * This monster tries to kill the player.
   * @param player player this monster will try to kill.
   */
  void attack(Player player);

  /**
   * Decreases this monster's health by 1.
   */
  void decreaseHealth();
}
