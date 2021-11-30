package dungeon;

import dungeongeneral.Direction;
import dungeongeneral.Item;
import dungeongeneral.ShotResult;
import dungeongeneral.Treasure;

import java.util.List;

/**
 * This is A Player.
 * A Player can look for and collect treasure.
 * A Player has a location.
 * A Player can look for and move to the neighbouring locations as well.
 */
interface Player extends ReadOnlyPlayer{

  /**
   * Collect treasure from the entity's current location.
   * @param treasure treasure to be collected.
   */
  void collectTreasure(Treasure treasure);

  /**
   * Picks given item from entity's current location.
   * @param item type of item to be picked.
   */
  void pickItem(Item item);

  /**
   * move entity in the given direction.
   * @param direction direction in which the entity is to be moved.
   * @throws  IllegalArgumentException if the given direction is null or if
   *                                  there is no neighbour in the given direction.
   */
  void move(Direction direction) throws IllegalArgumentException, IllegalStateException;

  /**
   * kills the entity.
   */
  void die();

  /**
   * Player is mugged of a random treasure.
   * @return treasure lost by the player.
   */
  Treasure getRobbed();

  /**
   * Decreases the player's health.
   * @param damage damage to the player's health.
   */
  void decreaseHealth(int damage);

  /**
   * Consumes a potion.
   * @throws IllegalStateException when entity has no potions.
   */
  void consumePotion() throws IllegalStateException;

  /**
   * Attacks other entity at the same location.
   */
  void attack(Entity monster);

  /**
   * Shoots a crooked arrow in the given direction and distance.
   * @param direction direction of the arrow.
   * @param distance distance of the arrow needs to cover.
   * @return Returns result of the shot.
   * @throws IllegalArgumentException when direction is null or distance is not positive.
   */
  ShotResult shoot(Direction direction, int distance)
      throws IllegalArgumentException;

}
