package dungeonmodel;

import randomizer.Randomizer;

/**
 * This is an entity's strategy.
 */
public interface EntityStrategy {

  /**
   * makes the entity perform their next action.
   */
  void nextAction();

  /**
   * Sets a new randomizer for this entity's strategy.
   * @param randomizer a randomizer object.
   */
  void setRandomizer(Randomizer randomizer);
}
