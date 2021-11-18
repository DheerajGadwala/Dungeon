package dungeon;

import static general.Item.BOW;
import static general.Item.CROOKED_ARROW;
import static general.ShotResult.HIT;
import static general.ShotResult.KILL;
import static general.ShotResult.MISS;

import general.Direction;
import general.Item;
import general.MatrixPosition;
import general.Odour;
import general.ShotResult;
import general.Treasure;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Represents a player at location inside the dungeon.
 */
class DungeonPlayer implements Player {

  private final HashMap<Treasure, Integer> treasures;
  private LocationNode location;
  private boolean isAlive;
  private final Map<Item, Integer> items;

  /**
   * Creates an instance of the dungeon player.
   * @param location initial location of this player
   * @throws IllegalArgumentException when name or location are null or empty.
   */
  public DungeonPlayer(LocationNode location) throws IllegalArgumentException {
    if (location == null || location.isEmptyNode()) {
      throw new IllegalArgumentException("Location can not be null or empty node.");
    }
    treasures = new HashMap<>();
    for (Treasure t: Treasure.values()) {
      treasures.put(t, 0);
    }
    items = new HashMap<>();
    items.put(BOW, 1);
    items.put(CROOKED_ARROW, 3);
    this.location = location;
    this.isAlive = true;
  }

  @Override
  public Map<Treasure, Integer> getTreasures() {
    Map<Treasure, Integer> map = new HashMap<>();
    for (Treasure t: Treasure.values()) {
      if (treasures.get(t) > 0) {
        map.put(t, treasures.get(t));
      }
    }
    return map;
  }

  @Override
  public Map<Item, Integer> getItems() {
    Map<Item, Integer> map = new HashMap<>();
    for (Item i: Item.values()) {
      if (items.get(i) > 0) {
        map.put(i, items.get(i));
      }
    }
    return map;
  }

  @Override
  public void collectTreasure(Treasure t) {
    location.decreaseTreasureCount(t);
    treasures.replace(t, treasures.get(t) + 1);
  }

  @Override
  public void pickItem(Item i) {
    location.decreaseItemCount(i);
    items.replace(i, items.get(i) + 1);
  }

  @Override
  public String getLocationDescription() {
    return location.toString();
  }

  @Override
  public List<Direction> getPossibleRoutes() {
    return location.getPossibleRoutes();
  }

  @Override
  public void movePlayer(Direction direction)
      throws IllegalArgumentException {
    if (direction == null) {
      throw new IllegalArgumentException("Direction can not be null.");
    }
    if (location.hasEmptyNodeAt(direction)) {
      throw new IllegalArgumentException("No path in the given direction.");
    }
    location = location.getLocationAt(direction);
  }

  @Override
  public MatrixPosition getPosition() {
    return location.getPosition();
  }

  @Override
  public void die() {
    this.isAlive = false;
  }

  @Override
  public boolean isAlive() {
    return isAlive;
  }

  @Override
  public ShotResult shoot(Direction direction, int distance)
      throws IllegalArgumentException {
    if (direction == null) {
      throw new IllegalArgumentException("Direction can not be null.");
    }
    else if (items.get(CROOKED_ARROW) == 0) {
      throw new IllegalStateException("Not enough arrows.");
    }
    else if (distance < 1) {
      throw new IllegalArgumentException("Distance needs to be at least one.");
    }
    items.replace(CROOKED_ARROW, items.get(CROOKED_ARROW) - 1);
    Monster monster;
    monster = location.getMonsterAtEnd(direction, distance);
    if (monster == null || !monster.isAlive()) {
      return MISS;
    }
    else {
      monster.decreaseHealth();
      if (monster.isAlive()) {
        return HIT;
      }
      else {
        return KILL;
      }
    }
  }

  @Override
  public Odour smell() {
    return location.getOdour();
  }

  @Override
  public boolean hasArrow() {
    return items.get(CROOKED_ARROW) > 0;
  }

  @Override
  public String toString() {
    StringBuilder stb = new StringBuilder();
    stb.append("Player description:\n");
    stb.append("Treasure:\n");
    for (Treasure t: Treasure.values()) {
      stb.append(String.format(" %s - %d\n", t.toString(), treasures.get(t)));
    }
    stb.append("Items:\n");
    for (Item i: Item.values()) {
      stb.append(String.format(" %s - %d\n", i.toString(), items.get(i)));
    }
    return stb.toString();
  }
}
