package dungeongeneral;

import java.util.Map;
import java.util.TreeMap;

/**
 * Takes in a hashmap and returns a beautified toString on call toString.
 */
public class TreasureList {

  private final Map<Treasure, Integer> map;

  /**
   * creates an object of this type.
   * @param map map of items and integers.
   */
  public TreasureList(Map<Treasure, Integer> map) {
    if (map == null) {
      throw new IllegalArgumentException("map can not be null");
    }
    this.map = new TreeMap<>();
    for (Treasure t: map.keySet()) {
      this.map.put(t, map.get(t));
    }
  }

  @Override
  public String toString() {
    StringBuilder ret = new StringBuilder();
    for (Treasure t: map.keySet()) {
      if (map.get(t) == 1) {
        ret.append(map.get(t)).append(" ").append(t.getSingular()).append(" ");
      }
      else if (map.get(t) > 1) {
        ret.append(map.get(t)).append(" ").append(t.getPlural()).append(" ");
      }
    }
    return ret.toString().equals("") ? "None" : ret.toString();
  }

}
