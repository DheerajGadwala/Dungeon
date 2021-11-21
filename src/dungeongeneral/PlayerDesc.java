package dungeongeneral;

import java.util.Map;

/**
 * Since a player object is mutable, we use these
 * objects to safely transfer player data and description
 * This is essentially a snapshot a player's state at a
 * particular point in time.
 */
public interface PlayerDesc {

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
}
