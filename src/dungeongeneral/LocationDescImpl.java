package dungeongeneral;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * This represents the description of a location at a particular point in the location's state.
 */
public class LocationDescImpl implements LocationDesc {

  private final boolean isCave;
  private final boolean hasMonster;
  private final int monsterHealth;
  private final MatrixPosition position;
  private final Map<Treasure, Integer> treasures;
  private final Map<Item, Integer> items;
  private final List<Direction> routes;

  /**
   * Creates a location description object.
   * @param items items of the location being described.
   * @param treasures treasures of the location being described.
   * @param isCave if location is a cave or not
   * @param hasMonster if location has a monster or not.
   * @param monsterHealth health of the monster. [0 if no monster]
   * @param position position of the location.
   * @param routes directions with lead to neighbouring locations.
   * @throws IllegalArgumentException if any reference type argument is null.
   */
  public LocationDescImpl(
          Map<Item, Integer> items, Map<Treasure, Integer> treasures,
          boolean isCave, boolean hasMonster, int monsterHealth,
          MatrixPosition position, List<Direction> routes
  ) throws IllegalArgumentException {
    if (treasures == null) {
      throw new IllegalArgumentException("treasures can not be null.");
    }
    if (items == null) {
      throw new IllegalArgumentException("items can not be null.");
    }
    if (position == null) {
      throw new IllegalArgumentException("position can not be null.");
    }
    if (routes == null) {
      throw new IllegalArgumentException("routes can not be null.");
    }
    this.treasures = copyTreasures(treasures);
    this.items = copyItems(items);
    this.isCave = isCave;
    this.hasMonster = hasMonster;
    this.monsterHealth = monsterHealth;
    this.position = position;
    this.routes = new ArrayList<>(routes);
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
  public boolean hasItems() {
    int sum = 0;
    for (Item i: Item.values()) {
      sum += items.get(i);
    }
    return sum > 0;
  }

  @Override
  public boolean hasTreasure() {
    int sum = 0;
    for (Treasure t: Treasure.values()) {
      sum += treasures.get(t);
    }
    return sum > 0;
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
  public boolean isCave() {
    return isCave;
  }

  @Override
  public boolean isTunnel() {
    return !isCave;
  }

  @Override
  public boolean hasNoMonster() {
    return !hasMonster;
  }

  @Override
  public boolean hasDeadMonster() {
    return hasMonster && monsterHealth == 0;
  }

  @Override
  public boolean hasInjuredMonster() {
    return monsterHealth == 1;
  }

  @Override
  public boolean hasHealthyMonster() {
    return monsterHealth == 2;
  }

  @Override
  public MatrixPosition getCoordinates() {
    return position;
  }

  @Override
  public List<Direction> getRoutes() {
    return new ArrayList<>(routes);
  }

  @Override
  public String toString() {
    String type = isCave ? "cave" : "tunnel";
    StringBuilder stb = new StringBuilder("This is a " + type + "\n");
    stb.append("Coordinates: ").append(position.toString()).append("\n");
    stb.append("Possible routes: ");
    for (Direction d: routes) {
      stb.append(d.toString()).append(" ");
    }
    stb.append("\n");
    if (hasTreasure()) {
      stb.append("There's some treasure in this cave: ");
      stb.append(new TreasureList(treasures).toString()).append("\n");
    }
    if (hasItems()) {
      stb.append("There are some items in this cave: ");
      stb.append(new ItemList(items).toString()).append("\n");
    }
    if (hasMonster) {
      if (monsterHealth == 0) {
        stb.append("There is a dead monster here.\n");
      }
      else if (monsterHealth == 1) {
        stb.append("There is an injured monster here.\n");
      }
      else {
        stb.append("There is an alive monster here.\n");
      }
    }
    return stb.toString();
  }
  
}
