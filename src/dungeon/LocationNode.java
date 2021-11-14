package dungeon;

import general.Direction;
import general.MatrixPosition;
import general.Treasure;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;

/**
 * Represents the union type for locations.
 * A locationNode can be a tunnel or a cave or the empty node.
 * A location might have treasure if it is a cave.
 * There is only one empty node. [Singleton]
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
   * Checks if location node is the empty node.
   * @return true if location node is the empty node.
   */
  boolean isEmptyNode();

  /**
   * Checks if there is the empty node in the given direction.
   * @param direction direction to be checked.
   * @return true if there is the empty node in the given direction.
   * @throws IllegalStateException when location node is the empty node.
   */
  boolean hasEmptyNodeAt(Direction direction) throws IllegalStateException;

  /**
   * Returns location in the given direction.
   * @param direction the required location node's direction from this location node.
   * @return Location node at the given direction.
   * @throws IllegalStateException when location node is the empty node.
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
   * type of this object as a string.
   * @return type of this object.
   */
  String getType();

  /**
   * Add treasure to this location.
   * @param treasure treasure to be added.
   * @throws IllegalStateException if treasure is being added to a Tunnel or Empty node.
   */
  void addTreasure(Map<Treasure, Integer> treasure) throws IllegalStateException;

  /**
   * get position of this location.
   * @return position of this location.
   */
  MatrixPosition getPosition() throws IllegalStateException;

  /**
   * return true if this location node has that as a neighbour.
   * Returns false if this is a empty node.
   * @param that node to be checked.
   * @return true if this and that are neighbours else false.
   */
  boolean hasNeighbour(LocationNode that);

  /**
   * Set neighbouring location in the given direction.
   * @param direction direction whose location is to be set.
   * @param location location to be set as the neighbour.
   * @throws IllegalArgumentException when location or direction is null.
   * @throws IllegalStateException when this location node is empty node.
   */
  void setNeighbour(Direction direction, LocationNode location)
      throws IllegalArgumentException, IllegalStateException;

  /**
   * Get direction of the neighbouring location.
   * @param neighbour location whose direction is being requested.
   * @return Direction of the given location.
   * @throws IllegalArgumentException if given location is not a neighbour
   */
  Direction getNeighbourDirection(LocationNode neighbour)
      throws IllegalArgumentException, IllegalStateException;

  /**
   * Removes and returns treasure from this location node.
   * @return Treasure from this location node.
   * @throws IllegalStateException when trying to remove treasure from
   *                                Location nodes that are not caves.
   */
  HashMap<Treasure, Integer> removeTreasure() throws IllegalStateException;

  /**
   * We perform BFS here.
   * Helper method for getRequiredNodes.
   * @param visited already visited nodes
   * @param queue queue of visiting
   * @param queueD queue of distance from initial node
   * @param distanceRequirement requirement of distance from.
   * @param nodeRequirement requirement from the node.
   * @return list of nodes at distance greater than or equal to d from this.
   */
  List<LocationNode> getRequiredNodesHelper(
      List<LocationNode> visited,
      List<LocationNode> queue,
      List<Integer> queueD,
      Predicate<Integer> distanceRequirement,
      Predicate<LocationNode> nodeRequirement
  );

  /**
   * Returns nodes which satisfy the given predicates.
   * @param distanceRequirement requirement of distance from this node.
   * @param nodeRequirement requirement from the node.
   * @return list of nodes that meet both requirements.
   */
  List<LocationNode> getRequiredNodes(
      Predicate<Integer> distanceRequirement,
      Predicate<LocationNode> nodeRequirement
  );
}
