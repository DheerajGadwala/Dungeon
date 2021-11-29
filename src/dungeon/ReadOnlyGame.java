package dungeon;

import dungeongeneral.LocationDesc;
import dungeongeneral.Odour;
import dungeongeneral.PlayerDesc;

/**
 * This is a ready only version of the game that can be used by a view.
 */
public interface ReadOnlyGame {

   /**
   * Returns the odour at player location.
   * @return odour as perceived by the player.
   */
  Odour smell();

  /**
   * Returns description of the player, which
   * includes: game stats, player's treasure and
   * items.
   * @return description of the player.
   */
  PlayerDesc getPlayerDesc();

  /**
   * Returns description of the player's location.
   * It includes the type of of location, treasure if any,
   * items if any and details about the monster, if it exists
   * in the location.
   * @return description of the player's location.
   */
  LocationDesc getLocationDesc();

  /**
   * Returns true if the player has at least one arrow.
   * @return true if player has at least one arrow else false.
   */
  boolean hasArrow();

  /**
   * Returns true if player has reached end.
   * Disables further moves.
   * @return true is game is over.
   */
  boolean isGameOver();

  /**
   * returns true if the player has won the game else false.
   * @return true if player has won else false.
   */
  boolean hasPlayerWon();
}
