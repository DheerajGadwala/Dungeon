package dungeonmodel;

import dungeongeneral.Coordinate;
import dungeongeneral.Direction;
import randomizer.Randomizer;

import java.util.ArrayList;
import java.util.List;

/**
 * Otyugh is a type of monster.
 * They spend their entire life in a single cave.
 * They wait for beings to enter their cave so that they can eat them.
 * Otyughs can only be killed from a distance.
 * It is not possible to win against them in hand to hand combat.
 */
class Otyugh implements Entity {

  private int health;
  private Randomizer randomizer;
  private static final int DEFAULT_HEALTH = 2;

  /**
   * Otyugh constructor.
   * @param randomizer randomizer object to determine attacks by otyughs.
   * @throws IllegalArgumentException when randomizer is null.
   */
  Otyugh(Randomizer randomizer) throws IllegalArgumentException {
    if (randomizer == null) {
      throw new IllegalArgumentException("randomizer can not be null");
    }
    this.randomizer = randomizer;
    this.health = DEFAULT_HEALTH;
  }

  @Override
  public boolean isAlive() {
    return this.health > 0;
  }

  @Override
  public boolean isInjured() {
    return health == 1;
  }

  @Override
  public int getHealth() {
    return this.health;
  }

  /**
   * Kills the player.
   * @param player player this entity will try to harm.
   */
  @Override
  public void harm(Player player) {
    if (!player.isAlive()) {
      throw new IllegalArgumentException("Player is already dead");
    }
    else if (!this.isAlive()) {
      throw new IllegalStateException("Otyugh is dead.");
    }
    else if (this.health == 2 || randomizer.getIntBetween(1, 2) == 1) {
      player.die();
    }
  }

  @Override
  public void decreaseHealth(int damage) {
    if (!this.isAlive()) {
      throw new IllegalStateException("This otyugh is already dead.");
    }
    this.health -= damage;
  }

  /**
   * Otyughs have no position.
   * @return Position of entity.
   * @throws IllegalStateException Since this is an otyugh, we throw an exception.
   */
  @Override
  public Coordinate getCoordinates() throws IllegalStateException {
    throw new IllegalStateException("Otyughs have no clue about their position.");
  }

  /**
   * Otyugh can not move.
   * @param direction direction in which entity is to be moved.
   * @throws IllegalStateException Since this is an otyugh, we throw an exception.
   */
  @Override
  public void move(Direction direction) throws IllegalStateException {
    throw new IllegalStateException("Otyughs can not move.");
  }

  /**
   * Location information is not stored here.
   * @return Location.
   * @throws IllegalStateException Since this is an otyugh, we throw an exception.
   */
  @Override
  public LocationNode getLocation() throws IllegalStateException {
    throw new IllegalStateException("Otyughs have no clue about their location.");
  }

  @Override
  public List<Direction> getPossibleRoutes() {
    return new ArrayList<>();
  }

  @Override
  public void setRandomizer(Randomizer randomizer) {
    if (randomizer == null) {
      throw new IllegalArgumentException("randomizer can not be null");
    }
    this.randomizer = randomizer;
  }
}
