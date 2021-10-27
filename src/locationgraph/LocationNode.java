package locationgraph;

import common.Direction;
import common.MatrixPosition;
import common.Treasure;

import java.util.List;

/**
 * Represents the union type for locations.
 * A locationNode can be a tunnel or a cave or the wall.
 * A location might have treasure if it is a cave.
 * There is only one wall. [Singleton]
 */
interface LocationNode {

  /**
   * Checks if a location node is a tunnel.
   * @return true if location node is a tunnel.
   */
  boolean isTunnel();

  /**
   * Checks if location node is a cave.
   * @return true if location node is a cave.
   */
  boolean isCave();

  /**
   * Checks if location node is the wall.
   * @return true if location node is the wall.
   */
  boolean isWall();

  /**
   * Checks if there is the wall in the given direction.
   * @param direction direction to be checked.
   * @return true if there is the wall in the given direction.
   * @throws IllegalStateException when location node is the wall.
   */
  boolean hasWallAt(Direction direction) throws IllegalStateException;

  /**
   * Returns location in the given direction.
   * @param direction the required location node's direction from this location node.
   * @return Location node at the given direction.
   * @throws IllegalStateException when location node is the wall.
   */
  LocationNode getLocationAt(Direction direction) throws IllegalStateException;

  /**
   * Description of this location with the treasure it has.
   * @return description of this location as a string.
   */
  String getDescription();

  /**
   * Returns true if this location has treasure.
   * @return true if this has treasure else false.
   */
  boolean hasTreasure();

  /**
   * Add treasure to this location.
   * @param treasure treasure to be added.
   * @throws IllegalStateException if treasure is being added to Tunnel or Wall.
   */
  void addTreasure(Treasure treasure) throws IllegalStateException;

  /**
   * get position of this location.
   * @return position of this location.
   */
  MatrixPosition getPosition() throws IllegalStateException;

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

  /**
   * Removes and returns treasure from this location node.
   * @return Treasure from this location node.
   * @throws IllegalStateException when trying to remove treasure from
   *                                Location nodes that are not caves.
   */
  List<Treasure> removeTreasure() throws IllegalStateException;
}
