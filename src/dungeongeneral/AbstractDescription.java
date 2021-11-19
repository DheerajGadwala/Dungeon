package dungeongeneral;

import java.util.Map;
import java.util.TreeMap;

abstract class AbstractDescription implements Description {

  protected final Map<Treasure, Integer> treasures;
  protected final Map<Item, Integer> items;

  protected AbstractDescription(Map<Treasure, Integer> treasures, Map<Item, Integer> items) {
    if (treasures == null) {
      throw new IllegalArgumentException("treasures can not be null.");
    }
    if (items == null) {
      throw new IllegalArgumentException("items can not be null.");
    }
    this.treasures = copyTreasures(treasures);
    this.items = copyItems(items);
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

  public Map<Treasure, Integer> getTreasure() {
    return copyTreasures(treasures);
  }

  public Map<Item, Integer> getItems() {
    return copyItems(items);
  }
}
