package dungeon;

import dungeongeneral.Direction;
import dungeongeneral.Item;
import dungeongeneral.LocationDesc;
import dungeongeneral.MatrixPosition;
import dungeongeneral.Odour;
import dungeongeneral.PlayerDesc;
import dungeongeneral.ShotResult;
import dungeongeneral.Treasure;

import java.util.List;

/**
 * This is a player. A player has a name.
 * A player can look for and collect treasure.
 * A player has a location.
 * A player can look for and move to the neighbouring locations as well.
 */
interface Player {

  /**
   * Collect treasure from the player's current location.
   * @param treasure treasure to be collected.
   */
  void collectTreasure(Treasure treasure);

  /**
   * Picks given item from player's current location.
   * @param item type of item to be picked.
   */
  void pickItem(Item item);

  /**
   * get Description of the location of the player.
   * @return location description as a string.
   */
  LocationDesc getLocationDescription();

  /**
   * get neighbouring location directions from player's current location.
   * @return list of directions which lead to other locations.
   */
  List<Direction> getPossibleRoutes();

  /**
   * move player in the given direction.
   * @param direction direction in which the player is to be moved.
   * @throws  IllegalArgumentException if the given direction is null or if
   *                                  there is no neighbour in the given direction.
   */
  void movePlayer(Direction direction) throws IllegalArgumentException, IllegalStateException;

  /**
   * Get position of the player on the matrix.
   * @return a matrix position object representing the player's location.
   */
  MatrixPosition getPosition();

  /**
   * kills the player.
   */
  void die();

  /**
   * Returns true if the player is eaten.
   * @return true if player is eaten.
   */
  boolean isAlive();

  /**
   * Shoots a crooked arrow in the given direction and distance.
   * @param direction direction of the arrow.
   * @param distance distance of the arrow needs to cover.
   * @return Returns result of the shot.
   * @throws IllegalArgumentException when direction is null or distance is not positive.
   */
  ShotResult shoot(Direction direction, int distance)
      throws IllegalArgumentException;

  /**
   * Returns the odour the player smells at their location.
   * @return odour smelled by the player.
   */
  Odour smell();

  /**
   * Returns true if player has at least one arrow.
   * @return true if player has at least one arrow else false.
   */
  boolean hasArrow();

  /**
   * Returns current description object of the player.
   * @return description of the player.
   */
  PlayerDesc getDesc();
}
