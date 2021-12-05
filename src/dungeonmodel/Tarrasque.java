package dungeonmodel;

import dungeongeneral.Direction;
import dungeongeneral.Coordinate;
import randomizer.Randomizer;

import java.util.List;

class Tarrasque implements Entity {

  private LocationNode location;
  private int health;
  private final Randomizer randomizer;

  Tarrasque(LocationNode location, Randomizer randomizer) {
    if (location == null || location.isEmptyNode()) {
      throw new IllegalArgumentException("Location can not be null or empty node.");
    }
    if (randomizer == null) {
      throw new IllegalArgumentException("Randomizer can not be null");
    }
    this.location = location;
    this.randomizer = randomizer;
    this.health = 20;
  }

  @Override
  public boolean isAlive() {
    return this.health >= 0;
  }

  @Override
  public boolean isInjured() {
    return this.health != 20;
  }

  @Override
  public int getHealth() {
    return this.health;
  }

  /**
   * Harms the payer by decreasing his health.
   * @param player player this entity will try to harm.
   */
  @Override
  public void harm(Player player) {
    int damage = 5 + randomizer.getIntBetween(-2, 2);
    player.decreaseHealth(damage);
  }

  @Override
  public void decreaseHealth(int damage) {
    this.health -= damage;
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

}
