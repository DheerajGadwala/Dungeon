package dungeongeneral;

import java.util.List;
import java.util.Map;

/**
 * Since a player object is mutable, we use these
 * objects to safely transfer player data and description
 * This is essentially a snapshot a player's state at a
 * particular point in time.
 */
public interface ReadOnlyPlayer {

  /**
   * returns a map of player treasure and treasure count.
   * @return player treasure.
   */
  Map<Treasure, Integer> getTreasure();

  /**
   * returns a map of player items and item count.
   * @return player items.
   */
  Map<Item, Integer> getItems();

  /**
   * Get position of the entity on the matrix.
   * @return a matrix position object representing the entity's location.
   */
  Coordinate getCoordinates();

  /**
   * get neighbouring location directions from entity's current location.
   * @return list of directions which lead to other locations.
   */
  List<Direction> getPossibleRoutes();

  /**
   * returns miss count of player.
   * @return arrow miss count.
   */
  int getMissCount();

  /**
   * returns hit count of player.
   * @return arrow hit count.
   */
  int getHitCount();

  /**
   * returns kill count of player.
   * @return arrow kill count.
   */
  int getKillCount();

  /**
   * Returns true if the entity is alive.
   * @return true if entity is alive.
   */
  boolean isAlive();

  /**
   * Returns true if entity has at least one arrow.
   * @return true if entity has at least one arrow else false.
   */
  boolean hasArrow();

  /**
   * Returns current description object of the entity.
   * @return description of the entity.
   */
  ReadOnlyPlayer getDesc();

  /**
   * get Description of the location of the entity.
   * @return location description as a string.
   */
  ReadOnlyLocation getLocationDescription();

  /**
   * Returns health of the player.
   * @return health of the player.
   */
  int getHealth();

  /**
   * Return the result of the previous shot.
   * Null if shoot was not the previous command.
   * @return result of the previous shot.
   */
  Sound getPreviousShotResult();

  /**
   * Returns true if player is at given location.
   * @param location location  to be checked.
   * @return true if player is at location else false.
   */
  boolean isAt(ReadOnlyLocation location);
}
