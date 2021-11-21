package dungeongeneral;

import java.util.Map;
import java.util.TreeMap;

/**
 * Description of a player.
 */
public class PlayerDescImpl implements PlayerDesc {

  private final int missCount;
  private final int hitCount;
  private final int killCount;
  private final Map<Treasure, Integer> treasures;
  private final Map<Item, Integer> items;

  /**
   * Creates a player description object.
   * @param items items of the player being described.
   * @param treasures treasures of the player being described.
   * @param missCount miss count of the player being described.
   * @param hitCount hit count of the player being described.
   * @param killCount kill count of the player being described.
   */
  public PlayerDescImpl(
          Map<Item, Integer> items, Map<Treasure, Integer> treasures,
          int missCount, int hitCount, int killCount) {
    if (treasures == null) {
      throw new IllegalArgumentException("treasures can not be null.");
    }
    if (items == null) {
      throw new IllegalArgumentException("items can not be null.");
    }
    this.treasures = copyTreasures(treasures);
    this.items = copyItems(items);
    this.missCount = missCount;
    this.hitCount = hitCount;
    this.killCount = killCount;
  }

  private Map<Item, Integer> copyItems(Map<Item, Integer> items) {
    Map<Item, Integer> copy = new TreeMap<>();
    for (Item item: items.keySet()) {
      copy.put(item, items.get(item));
    }
    return copy;
  }

  private Map<Treasure, Integer> copyTreasures(Map<Treasure, Integer> treasures) {
    Map<Treasure, Integer> copy = new TreeMap<>();
    for (Treasure treasure: treasures.keySet()) {
      copy.put(treasure, treasures.get(treasure));
    }
    return copy;
  }

  @Override
  public Map<Treasure, Integer> getTreasure() {
    return copyTreasures(treasures);
  }

  @Override
  public Map<Item, Integer> getItems() {
    return copyItems(items);
  }

  @Override
  public int getMissCount() {
    return missCount;
  }

  @Override
  public int getHitCount() {
    return hitCount;
  }

  @Override
  public int getKillCount() {
    return killCount;
  }

  @Override
  public String toString() {
    TreasureList tl = new TreasureList(treasures);
    ItemList il = new ItemList(items);
    return "Player info:\n"
            + "Misses: " + missCount + "\n"
            + "Hits: " + hitCount + "\n"
            + "Kills: " + killCount + "\n"
            + "Treasure:\n"
            + tl.toString() + "\n"
            + "Items:\n"
            + il.toString() + "\n";
  }
}