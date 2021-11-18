package dungeon;

import randomizer.Randomizer;

/**
 * Otyugh is a type of monster.
 */
class Otyugh implements Monster {

  private int health;
  private final Randomizer randomizer;
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
  public void attack(Player player) {
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
  public void decreaseHealth() {
    if (!this.isAlive()) {
      throw new IllegalStateException("This otyugh is already dead.");
    }
    this.health--;
  }
}
