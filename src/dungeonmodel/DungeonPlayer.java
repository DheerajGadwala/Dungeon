package dungeonmodel;

import static dungeongeneral.Item.CROOKED_ARROW;
import static dungeongeneral.Item.POTION;
import static dungeongeneral.Sound.DYING_HOWL;
import static dungeongeneral.Sound.HISS;
import static dungeongeneral.Sound.HOWL;
import static dungeongeneral.Weapon.AXE;
import static dungeongeneral.Weapon.BOW;
import static dungeongeneral.Weapon.SWORD;

import dungeongeneral.Coordinate;
import dungeongeneral.Direction;
import dungeongeneral.Item;
import dungeongeneral.ItemList;
import dungeongeneral.ReadOnlyLocation;
import dungeongeneral.ReadOnlyPlayer;
import dungeongeneral.Sound;
import dungeongeneral.Treasure;
import dungeongeneral.TreasureList;
import dungeongeneral.Weapon;
import randomizer.Randomizer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * Represents a player at location inside the dungeon.
 */
class DungeonPlayer implements Player {

  private final Map<Treasure, Integer> treasures;
  private LocationNode location;
  private int health;
  private final Map<Item, Integer> items;
  private final List<Weapon> weapons;
  private Randomizer randomizer;
  private int missCount;
  private int hitCount;
  private int killCount;
  private Sound previousSound;

  /**
   * Creates an instance of the dungeon player.
   * @param location initial location of this player
   * @throws IllegalArgumentException when name or location are null or empty.
   */
  public DungeonPlayer(LocationNode location, Randomizer randomizer)
          throws IllegalArgumentException {
    if (location == null || location.isEmptyNode()) {
      throw new IllegalArgumentException("Location can not be null or empty node.");
    }
    if (randomizer == null) {
      throw new IllegalArgumentException("Randomizer can not be null.");
    }
    treasures = new HashMap<>();
    for (Treasure t: Treasure.values()) {
      treasures.put(t, 0);
    }
    items = new HashMap<>();
    items.put(CROOKED_ARROW, 3);
    items.put(POTION, 1);
    weapons = new ArrayList<>();
    weapons.add(BOW);
    weapons.add(SWORD);
    this.randomizer = randomizer;
    this.location = location;
    this.health = 30;
    this.missCount = 0;
    this.hitCount = 0;
    this.killCount = 0;
  }

  @Override
  public void collectTreasure(Treasure treasure) {
    location.decreaseTreasureCount(treasure);
    treasures.replace(treasure, treasures.get(treasure) + 1);
    previousSound = null;
  }

  @Override
  public void pickItem(Item item) {
    location.decreaseItemCount(item);
    items.replace(item, items.get(item) + 1);
    previousSound = null;
  }

  @Override
  public ReadOnlyLocation getLocationDescription() {
    return location.getDesc();
  }

  @Override
  public int getHealth() {
    return health;
  }

  @Override
  public Sound getPreviousShotResult() {
    return previousSound;
  }

  @Override
  public boolean isAt(ReadOnlyLocation location) {
    return getCoordinates().equals(location.getCoordinates());
  }

  @Override
  public List<Direction> getPossibleRoutes() {
    return location.getPossibleRoutes();
  }

  @Override
  public void move(Direction direction)
      throws IllegalArgumentException {
    if (direction == null) {
      throw new IllegalArgumentException("Direction can not be null.");
    }
    if (location.hasEmptyNodeAt(direction)) {
      throw new IllegalArgumentException("No path in the given direction.");
    }
    previousSound = null;
    location = location.getLocationAt(direction);
  }

  @Override
  public Coordinate getCoordinates() {
    return location.getCoordinates();
  }

  @Override
  public void die() {
    this.health = 0;
  }

  @Override
  public void getRobbed() {
    List<Treasure> treasure = new ArrayList<>();
    for (Treasure t: treasures.keySet()) {
      if (treasures.get(t) != 0) {
        treasure.add(t);
      }
    }
    if (treasure.size() != 0) {
      Treasure removed = treasure.remove(
              randomizer.getIntBetween(0, treasure.size() - 1)
      );
      treasures.put(removed, treasures.get(removed) - 1);
    }
  }

  @Override
  public void decreaseHealth(int damage) {
    this.health -= damage;
  }

  @Override
  public boolean isAlive() {
    return this.health > 0;
  }

  @Override
  public void attack(Entity monster) {
    int damage;
    if (weapons.contains(AXE)) {
      damage = AXE.getDamage() + randomizer.getIntBetween(-2, 2);
    }
    else {
      damage = SWORD.getDamage() + randomizer.getIntBetween(-2, 2);
    }
    previousSound = null;
    monster.decreaseHealth(damage);
  }

  @Override
  public Sound shoot(Direction direction, int distance)
      throws IllegalArgumentException, IllegalStateException {
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
    Entity monster;
    monster = location.getMonsterAtEnd(direction, distance, true);
    if (monster == null || !monster.isAlive()) {
      missCount++;
      previousSound = HISS;
      return HISS;
    }
    else {
      monster.decreaseHealth(BOW.getDamage());
      if (monster.isAlive()) {
        hitCount++;
        previousSound = HOWL;
        return HOWL;
      }
      else {
        hitCount++;
        killCount++;
        previousSound = DYING_HOWL;
        return DYING_HOWL;
      }
    }
  }

  @Override
  public LocationNode getLocation() {
    return location;
  }

  @Override
  public void setRandomizer(Randomizer randomizer) {
    if (randomizer == null) {
      throw new IllegalArgumentException("randomizer can not be null");
    }
    this.randomizer = randomizer;
  }

  @Override
  public boolean hasArrow() {
    return items.get(CROOKED_ARROW) > 0;
  }

  @Override
  public ReadOnlyPlayer getDesc() {
    return this;
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
