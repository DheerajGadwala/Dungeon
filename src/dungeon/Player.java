package dungeon;

import general.Direction;
import general.MatrixPosition;

import java.util.List;

/**
 * This is a player. A player has a name.
 * A player can look for and collect treasure.
 * A player has a location.
 * A player can look for and move to the neighbouring locations as well.
 */
interface Player {

  /**
   * get name of the player.
   * @return name of the player
   */
  String getName();

  /**
   * Returns true if location has treasure else returns false.
   * @return true if location has treasure else false.
   */
  boolean scoutForTreasure();

  /**
   * Collect treasure from the player's current location.
   */
  void collectTreasure();

  /**
   * Checks if player is at the given position.
   * @return true if player's location has the given coordinates.
   * @throws IllegalArgumentException if position is null.
   */
  boolean atPosition(MatrixPosition position) throws IllegalArgumentException;

  /**
   * get the description of the treasure that the player has.
   * @return description of the treasure the player has.
   */
  String getTreasureDescription();

  /**
   * get Description of the location of the player.
   * @return location description as a string.
   */
  String getLocationDescription();

  /**
   * get neighbouring location directions from player's current location.
   * @return list of directions which lead to other locations.
   */
  List<Direction> getPossibleDirections();

  /**
   * move player in the given direction.
   * @param direction direction in which the player is to be moved.
   * @throws  IllegalArgumentException if the given direction is null.
   * @throws IllegalStateException if the current location has no neighbour in the given direction.
   */
  void movePlayer(Direction direction) throws IllegalArgumentException, IllegalStateException;

  /**
   * Get position of the player on the matrix.
   * @return a matrix position object representing the player's location.
   */
  MatrixPosition getPosition();

  /**
   * Returns description of the player.
   * Includes player name, player location details and player treasure details.
   * @return description of the player.
   */
  String getDescription();

  /**
   * kills the player.
   */
  void die();

  /**
   * Returns true if the player is eaten.
   * @return true if player is eaten.
   */
  boolean isAlive();
}
