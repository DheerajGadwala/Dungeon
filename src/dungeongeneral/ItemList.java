package dungeongeneral;

import java.util.Map;
import java.util.TreeMap;

/**
 * Takes in a hashmap and returns a beautified toString on call to toString.
 */
class ItemList {

  private final Map<Item, Integer> map;

  /**
   * creates an object of this type.
   * @param map map of items and integers.
   */
  ItemList(Map<Item, Integer> map) {
    if (map == null) {
      throw new IllegalArgumentException("map can not be null");
    }
    this.map = new TreeMap<>();
    for (Item t: map.keySet()) {
      this.map.put(t, map.get(t));
    }
  }

  @Override
  public String toString() {
    StringBuilder ret = new StringBuilder();
    for (Item i: map.keySet()) {
      if (map.get(i) == 1) {
        ret.append(map.get(i)).append(" ").append(i.getSingular()).append(" ");
      }
      else if (map.get(i) > 1) {
        ret.append(map.get(i)).append(" ").append(i.getPlural()).append(" ");
      }
    }
    return ret.toString().equals("") ? "None" : ret.toString();
  }

}
