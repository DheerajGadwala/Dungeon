package dungeongeneral;

import java.util.Map;

/**
 * Descriptions of either a player or a location.
 */
public interface Description {

  Map<Treasure, Integer> getTreasure();

  Map<Item, Integer> getItems();
}
