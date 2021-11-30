package dungeon;

import dungeongeneral.Direction;
import dungeongeneral.Item;
import dungeongeneral.Treasure;

import java.util.List;
import java.util.Map;
import java.util.function.Predicate;

/**
 * Represents the union type for locations.
 * A locationNode can be a tunnel or a cave or the empty node.
 * A location might have treasure if it is a cave.
 * There is only one empty node. [Singleton]
 */
interface LocationNode extends ReadOnlyLocation {

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
   * Returns true if this location has any treasure.
   * @return true if this has treasure else false.
   */
  boolean hasTreasure();

  /**
   * Add treasure to this location.
   * @param treasure treasure to be added.
   * @throws IllegalStateException if treasure is being added to a Tunnel or Empty node.
   */
  void addTreasure(Map<Treasure, Integer> treasure) throws IllegalStateException;

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
   * removes one treasure of given type.
   * @param treasure Treasure whose count will be reduced by 1.
   * @throws IllegalStateException when trying to remove treasure from
   *                                Location nodes that are not caves or when
   *                                this the cave has no treasure at all.
   * @throws IllegalArgumentException when treasure of given type does not exists in this cave.
   */
  void decreaseTreasureCount(Treasure treasure)
      throws IllegalStateException, IllegalArgumentException;

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

  /**
   * sets a monster at this location.
   * @param monster monster to be set
   * @throws IllegalArgumentException if monster is null.
   */
  void setMonster(Entity monster) throws IllegalArgumentException;

  /**
   * sets the number of arrows at this location number of arrows.
   * @param n number of arrows
   * @throws IllegalArgumentException when n is less than or equal to 0.
   */
  void setItemCount(Item item, int n) throws IllegalArgumentException;

  /**
   * Decreases arrow count by one.
   * @throws IllegalArgumentException when this location has no arrows.
   * @throws IllegalStateException when this location has no items.
   */
  void decreaseItemCount(Item item) throws IllegalArgumentException, IllegalStateException;

  /**
   * returns the monster in this location.
   * @return the monster in this location.
   * @throws IllegalStateException when there is no monster in this location.
   */
  Entity getMonster() throws IllegalStateException;

  /**
   * Travel continues through tunnels regardless of directions.
   * Gets monster at the end of the path.
   * @param direction direction of travel.
   * @param distance distance of travel.
   * @return monster at the end of the path.
   * @throws IllegalArgumentException if direction is null or distance is negative.
   * @throws IllegalStateException if path is not completable, or monster does not
   *                                exists at the end of the path or if the monster
   *                                at the end is already dead.
   */
  Entity getMonsterAtEnd(Direction direction, int distance)
      throws IllegalArgumentException, IllegalStateException;
}
