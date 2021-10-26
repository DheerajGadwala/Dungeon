package LocationGraphADT;

import Common.Direction;
import Common.Treasure;
import Common.MatrixPosition;

/**
 * Represents the union type for locations.
 * A locationNode can be a tunnel or a cave or a wall.
 * A location might have treasure if it is a cave.
 */
interface LocationNode {

  /**
   * Checks if a location node is a tunnel.
   * @return true if location node is a tunnel.
   */
  default boolean isTunnel() {
    return false;
  }

  /**
   * Checks if location node is a cave.
   * @return true if location node is a cave.
   */
  default boolean isCave() {
    return false;
  }

  /**
   * Checks if location node is a wall.
   * @return true if location node is a wall.
   */
  default boolean isWall() {
    return false;
  }

  /**
   * Checks if there is a wall in the given direction.
   * @param direction direction to be checked.
   * @return true if there is a wall in the given direction.
   */
  default boolean hasWallAt(Direction direction) {
    return true;
  }

  /**
   * Returns location in the given direction.
   * @param direction the required location node's direction from this location node.
   * @return Location node at the given direction.
   */
  LocationNode getLocationAt(Direction direction);

  /**
   * Description of this location with the treasure it has.
   * @return description of this location as a string.
   */
  String getDescription();

  /**
   * Returns true if this location has treasure.
   * @return true if this has treasure else false.
   */
  default boolean hasTreasure() {
    return false;
  }

  /**
   * Add treasure to this location.
   * @param treasure treasure to be added.
   */
  void addTreasure(Treasure treasure);

  /**
   * get position of this location.
   * @return position of this location.
   */
  MatrixPosition getPosition();

  /**
   * Set neighbouring location in the given direction.
   * @param direction direction whose location is to be set.
   * @param location location to be set as the neighbour.
   * @return new location node of the correct type, depending on the number of neighbours.
   * @throws IllegalArgumentException when location or direction is null.
   * @throws IllegalStateException when this location node is wall.
   */
  LocationNode setNeighbour(Direction direction, LocationNode location)
      throws IllegalArgumentException, IllegalStateException;
}
