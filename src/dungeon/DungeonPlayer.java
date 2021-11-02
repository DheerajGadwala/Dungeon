package dungeon;

import static general.Treasure.DIAMOND;
import static general.Treasure.RUBY;
import static general.Treasure.SAPPHIRE;

import general.Direction;
import general.MatrixPosition;
import general.Treasure;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Represents a player at location inside the dungeon.
 */
public class DungeonPlayer implements Player {

  private final String name;
  private final HashMap<Treasure, Integer> treasure;
  private LocationNode location;

  /**
   * Creates an instance of the dungeon player.
   * @param name name of this player
   * @param location initial location of this player
   * @throws IllegalArgumentException when name or location are null or empty.
   */
  public DungeonPlayer(String name, LocationNode location) throws IllegalArgumentException {
    if (name == null || name.isEmpty()) {
      throw new IllegalArgumentException("name can not be null or empty.");
    }
    if (location == null || location.isEmptyNode()) {
      throw new IllegalArgumentException("Location can not be null or empty node.");
    }
    this.name = name;
    treasure = new HashMap<>();
    treasure.put(DIAMOND, 0);
    treasure.put(SAPPHIRE, 0);
    treasure.put(RUBY, 0);
    this.location = location;
  }

  @Override
  public String getName() {
    return name;
  }

  @Override
  public boolean scoutForTreasure() {
    return location.hasTreasure();
  }

  @Override
  public void collectTreasure() {
    HashMap<Treasure, Integer> temp = location.removeTreasure();
    for (Treasure t: Treasure.values()) {
      treasure.replace(t, treasure.get(t) + temp.get(t));
    }
  }

  @Override
  public boolean atPosition(MatrixPosition position) throws IllegalArgumentException {
    if (position == null) {
      throw new IllegalArgumentException("Position can not be null.");
    }
    return location.getPosition().equals(position);
  }

  @Override
  public String getTreasureDescription() {
    StringBuilder ret = new StringBuilder();
    for (Treasure t: Treasure.values()) {
      ret.append(t.toString()).append(": ").append(treasure.get(t)).append("\n");
    }
    return ret.toString();
  }

  @Override
  public String getLocationDescription() {
    return location.getDescription();
  }

  @Override
  public List<Direction> getPossibleDirections() {
    List<Direction> possibilities = new ArrayList<>();
    for (Direction d: Direction.values()) {
      if (!location.hasEmptyNodeAt(d)) {
        possibilities.add(d);
      }
    }
    return possibilities;
  }

  @Override
  public void movePlayer(Direction direction)
      throws IllegalArgumentException, IllegalStateException {
    if (direction == null) {
      throw new IllegalArgumentException("Direction can not be null.");
    }
    if (location.hasEmptyNodeAt(direction)) {
      throw new IllegalStateException("No path in the given direction.");
    }
    location = location.getLocationAt(direction);
  }

  @Override
  public MatrixPosition getPosition() {
    return location.getPosition();
  }
}
