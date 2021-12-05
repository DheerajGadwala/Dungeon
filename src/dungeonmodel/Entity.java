package dungeonmodel;

import dungeongeneral.Direction;
import dungeongeneral.Coordinate;

import java.util.List;

/**
 * This represents an entity.
 * An entity is in a location.
 * An entity can attacks players in it's location.
 * Entity harm on player might result in the death of the player.
 * An entity can loose health.
 */
interface Entity {

  /**
   * Returns true if the entity is alive.
   * @return true if entity's health is not zero.
      */
  boolean isAlive();

  /**
   * Returns true if entity is injured.
   * @return true if entity is injured else false.
   */
  boolean isInjured();

  /**
   * Returns entity health.
   * @return health of the entity.
   */
  int getHealth();

  /**
   * This entity tries to harm the player.
   * @param player player this entity will try to harm.
   */
  void harm(Player player);

  /**
   * decreases this entity's health by given damage.
   * @param damage damage to this entity's health.
   */
  void decreaseHealth(int damage);

  /**
   * Gets position of this entity.
   * @return position of this entity.
   * @throws IllegalStateException when entity does not have a position. [Otyugh]
   */
  Coordinate getCoordinates() throws IllegalStateException;

  /**
   * The entity moves in the given direction.
   * @throws IllegalStateException when entity can not move. [Otyugh]
   * @throws IllegalArgumentException when move in the given direction is not possible.
   */
  void move(Direction direction) throws IllegalStateException;

  /**
   * Returns location of the entity.
   * @return Location of the entity.
   */
  LocationNode getLocation();

  /**
   * Returns a list of all possible directions in which the entity can move.
   * @return list of directions.
   */
  List<Direction> getPossibleRoutes();
}
