package dungeongeneral;

import java.util.List;
import java.util.Map;

/**
 * Since a location node object is mutable, we use these
 * objects to safely transfer location data and description.
 * This is essentially a snapshot of the location's data at
 * a particular moment.
 */
public interface ReadOnlyLocation {

  /**
   * True if the location had treasure when this description was constructed.
   * @return true if the location has treasure when this description was constructed else false.
   */
  boolean hasTreasure() throws IllegalStateException;

  /**
   * returns a map of player treasure and treasure count.
   * @return player treasure.
   */
  Map<Treasure, Integer> getTreasure() throws IllegalStateException;

  /**
   * returns a map of player items and item count.
   * @return player items.
   */
  Map<Item, Integer> getItems() throws IllegalStateException;

  /**
   * Returns true if location is a cave.
   * @return returns true if location is a cave else false.
   */
  boolean isCave() throws IllegalStateException;

  /**
   * Returns true if location is a tunnel.
   * @return returns true if location is a tunnel else false.
   */
  boolean isTunnel() throws IllegalStateException;

  /**
   * Return true if location has a monster, dead or alive.
   * @return true if location has monster else false.
   */
  boolean hasMonster() throws IllegalStateException;

  /**
   * Returns true if location has no monster.
   * @return true if location has no monster else false.
   */
  boolean hasNoMonster() throws IllegalStateException;

  /**
   * Returns true if location has a dead monster.
   * @return true if location has a dead monster else false.
   */
  boolean hasDeadMonster() throws IllegalStateException;

  /**
   * Returns true if location has an injured monster.
   * @return true if location has an injured monster else false.
   */
  boolean hasInjuredMonster() throws IllegalStateException;

  /**
   * Returns true if location has a monster with full health.
   * @return true if location's monster has full health else false.
   */
  boolean hasHealthyMonster() throws IllegalStateException;

  /**
   * checks if location an alive monster in this location.
   * @return true if location has monster else false.
   */
  boolean hasAliveMonster() throws IllegalStateException;

  /**
   * Checks if there is at least one arrow in this location.
   * @return true there is at least one arrow in this location else false.
   */
  boolean hasItem(Item item) throws IllegalStateException;

  /**
   * returns true if this location has at least one item.
   * @return true if this location has at least one item else false.
   */
  boolean hasItems() throws IllegalStateException;

  /**
   * Returns position of the location.
   * @return position of the location.
   */
  Coordinate getCoordinates();

  /**
   * Returns list of directions which have routes to neighbouring locations.
   * @return list of directions which have routes to neighbouring locations.
   */
  List<Direction> getPossibleRoutes() throws IllegalStateException;

  /**
   * Returns odour at this location.
   * @return odour at this location.
   */
  Odour getOdour() throws IllegalStateException;

  /**
   * Returns true if location has a pit.
   * @return true if location has a pit else false.
   */
  boolean hasPit() throws IllegalStateException;

  /**
   * Returns true if there is a pit in a neighbouring location.
   * @return true if neighbour has a pit.
   */
  boolean hasSignsOfNearbyPit();

  /**
   * Returns true if discovered.
   * @return returns true if location is discovered by a player, else false.
   */
  boolean isDiscovered();
}
