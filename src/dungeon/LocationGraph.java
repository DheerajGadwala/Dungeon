package dungeon;

import general.Direction;
import general.MatrixPosition;

import java.util.List;

/**
 * This represents a graph ADT of locations.
 * This can have more location nodes added to it.
 * This can have more connections added between its location.
 */
interface LocationGraph {

  /**
   * Returns location node at the position.
   * @param position position of the location to be returned.
   * @return location being requested.
   */
  LocationNode getLocation(MatrixPosition position);

  /**
   * create a Location Node in the graph with matrix position(id).
   * @param position position(id) of the node in a matrix.
   * @throws IllegalStateException when location with given position(id)
   *                                already exists in this graph.
   * @throws IllegalArgumentException when position is null.
   */
  void createLocationNode(MatrixPosition position)
      throws IllegalArgumentException, IllegalStateException;

  /**
   * add a connect from position1(id1) to position2(id2) in the given direction.
   * Bi-directionally links position2(id2) to position1(id1) in the opposite direction.
   * @param position1 position1(id1) of the connection
   * @param position2 position2(id2) of the connection
   * @param direction direction of the connection
   * @throws IllegalArgumentException when one or both positions are null or do not exist in graph
   * @throws IllegalStateException when a connection between locations of
   *                                given positions already exists.
   */
  void addConnection(MatrixPosition position1, MatrixPosition position2, Direction direction)
      throws IllegalArgumentException, IllegalStateException;

  /**
   * add a connect from position1(id1) to position2(id2) in the given direction.
   * Bi-directionally links position2(id2) to position1(id1) in the opposite direction.
   * @param position1 position1(id1) of the connection
   * @param position2 position2(id2) of the connection
   * @throws IllegalArgumentException when one or both positions are null or do not exist in graph
   * @throws IllegalStateException when a connection between locations of
   *                                given positions does not exist.
   */
  void removeConnection(MatrixPosition position1, MatrixPosition position2)
      throws IllegalArgumentException, IllegalStateException;

  /**
   * Returns an MST of this graph.
   */
  LocationGraph getMst();

  /**
   * Generates an MST and adds extra connections based on the given interconnectivity.
   * @param interconnectivity extra connections to be added.
   * @return a locations graph as described above.
   * @throws IllegalArgumentException when interconnectivity is invalid.
   */
  LocationGraph getMst(int interconnectivity) throws IllegalArgumentException;

  /**
   * Returns the number of location nodes in the graph.
   * @return number of location nodes.
   */
  int getNumberOfNodes();

  /**
   * Get all caves in this graph.
   * @return list of all locations that are caves.
   */
  List<LocationNode> getCaves();
}

