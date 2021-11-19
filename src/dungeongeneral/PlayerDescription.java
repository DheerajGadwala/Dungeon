package dungeongeneral;

import java.util.Map;

/**
 * Description of a player.
 */
public class PlayerDescription extends AbstractDescription{

  private final int missCount;
  private final int hitCount;
  private final int killCount;

  /**
   * Creates a player description object.
   * @param items items of the player being described.
   * @param treasures treasures of the player being described.
   * @param missCount miss count of the player being described.
   * @param hitCount hit count of the player being described.
   * @param killCount kill count of the player being described.
   */
  public PlayerDescription(
          Map<Item, Integer> items, Map<Treasure, Integer> treasures,
          int missCount, int hitCount, int killCount) {
    super(treasures, items);
    this.missCount = missCount;
    this.hitCount = hitCount;
    this.killCount = killCount;
  }

  public int getMissCount() {
    return missCount;
  }

  public int getHitCount() {
    return hitCount;
  }

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
