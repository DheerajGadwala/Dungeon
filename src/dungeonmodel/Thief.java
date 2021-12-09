package dungeonmodel;

import dungeongeneral.Coordinate;
import dungeongeneral.Direction;
import randomizer.Randomizer;

import java.util.List;

/**
 * Thief can not die.
 * Thief can rob the player's treasure.
 */
class Thief implements Entity {

  private LocationNode location;

  /**
   * Creates a thief object.
   * @param location location of the thief.
   * @throws IllegalArgumentException when location or randomizer are null.
   */
  public Thief(LocationNode location)
          throws IllegalArgumentException {
    if (location == null) {
      throw new IllegalArgumentException("Location can not be null.");
    }
    this.location = location;
  }

  @Override
  public boolean isAlive() {
    return true;
  }

  @Override
  public boolean isInjured() {
    return false;
  }

  @Override
  public int getHealth() {
    return 0;
  }

  /**
   * Player gets robbed.
   * @param player The player that this entity will try to harm.
   */
  @Override
  public void harm(Player player) {
    player.getRobbed();
  }

  @Override
  public void decreaseHealth(int damage) {
    throw new IllegalStateException("Thief can not be attacked, thieves are too stealthy.");
  }

  @Override
  public Coordinate getCoordinates() {
    return location.getCoordinates();
  }

  @Override
  public void move(Direction direction) throws IllegalArgumentException {
    if (direction == null) {
      throw new IllegalArgumentException("Direction can not be null.");
    }
    if (location.hasEmptyNodeAt(direction)) {
      throw new IllegalArgumentException("No path in the given direction.");
    }
    location = location.getLocationAt(direction);
  }

  @Override
  public LocationNode getLocation() {
    return location;
  }

  @Override
  public List<Direction> getPossibleRoutes() {
    return location.getPossibleRoutesHelper();
  }

  @Override
  public void setRandomizer(Randomizer randomizer) {
    throw new IllegalStateException("Thief has no randomizer");
  }

}
