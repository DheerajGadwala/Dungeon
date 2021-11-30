package dungeon;

import dungeongeneral.*;

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
  boolean hasTreasure();

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
   * Returns true if location is a cave.
   * @return returns true if location is a cave else false.
   */
  boolean isCave();

  /**
   * Returns true if location is a tunnel.
   * @return returns true if location is a tunnel else false.
   */
  boolean isTunnel();

  /**
   * Return true if location has a monster, dead or alive.
   * @return true if location has monster else false.
   */
  boolean hasMonster();

  /**
   * Returns true if location has no monster.
   * @return true if location has no monster else false.
   */
  boolean hasNoMonster();

  /**
   * Returns true if location has a dead monster.
   * @return true if location has a dead monster else false.
   */
  boolean hasDeadMonster();

  /**
   * Returns true if location has an injured monster.
   * @return true if location has an injured monster else false.
   */
  boolean hasInjuredMonster();

  /**
   * Returns true if location has a monster with full health.
   * @return true if location's monster has full health else false.
   */
  boolean hasHealthyMonster();

  /**
   * checks if location an alive monster in this location.
   * @return true if location has monster else false.
   */
  boolean hasAliveMonster();

  /**
   * Checks if there is at least one arrow in this location.
   * @return true there is at least one arrow in this location else false.
   */
  boolean hasItem(Item item);

  /**
   * returns true if this location has at least one item.
   * @return true if this location has at least one item else false.
   */
  boolean hasItems();

  /**
   * Returns position of the location.
   * @return position of the location.
   */
  Coordinate getCoordinates();

  /**
   * Returns list of directions which have routes to neighbouring locations.
   * @return list of directions which have routes to neighbouring locations.
   */
  List<Direction> getPossibleRoutes();

  /**
   * Returns odour at this location.
   * @return odour at this location.
   */
  Odour getOdour();

  /**
   * Returns description object of this location.
   * @return description object of this location.
   */
  ReadOnlyLocation getDesc();
}
