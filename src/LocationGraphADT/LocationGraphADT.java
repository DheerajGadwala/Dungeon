package LocationGraphADT;

import Common.Direction;
import Common.MatrixPosition;
import Common.Treasure;

/**
 * This represents a graph of locations.
 * This can have more location nodes added to it.
 * This can have more connections added between its location.
 */
public interface LocationGraphADT {
  /**
   * create a Location Node in the graph with matrix position(id).
   * @param position position(id) of the node in a matrix.
   * @throws IllegalStateException when location with given position(id) already exists in this graph.
   */
  void createLocationNode(MatrixPosition position)
      throws IllegalStateException;

  /**
   * add a connect from position1(id1) to position2(id2) in the given direction.
   * Bi-directionally links position2(id2) to position1(id1) in the opposite direction.
   * @param position1 position1(id1) of the connection
   * @param position2 position2(id2) of the connection
   * @param direction direction of the connection
   * @throws IllegalStateException when a connection between locations of given positions already exists.
   */
  void addConnection(MatrixPosition position1, MatrixPosition position2, Direction direction)
      throws IllegalStateException;

  /**
   * Description of a location at the given position(id).
   * Description involves details of treasures at the location.
   * @param position position(id) of the location node.
   * @return Description of the location as a String.
   * @throws IllegalArgumentException  when location of given position(id) is not in this graph.
   */
  String getDescription(MatrixPosition position)
      throws IllegalArgumentException;

  /**
   * Checks if the location at given position(id) in this graph has treasure.
   * @param position position(id) of the location node.
   * @return true if location has treasure else false.
   * @throws IllegalArgumentException  when location of given position(id) is not in this graph.
   */
  boolean hasTreasure(MatrixPosition position) 
      throws IllegalArgumentException;

  /**
   * Returns treasure at the given position's(id) location.
   * @param position position(id) of the location node.
   * @return treasure at the given location.
   * @throws IllegalArgumentException  when location of given position(id) is not in this graph.
   * @throws IllegalStateException when given location type of given position(id) can not have treasure.
   */
  Treasure collectTreasure(MatrixPosition position)
      throws IllegalArgumentException, IllegalStateException;

  /**
   * Adds the given treasure to the given position's(id) location.
   * @param position position(id) of the location node.
   * @param treasure treasure to be added.
   * @throws IllegalArgumentException when location of given position(id) is not in this graph.
   * @throws IllegalStateException when given location type of given position(id) can not have treasure.
   */
  void addTreasure(MatrixPosition position, Treasure treasure)
      throws IllegalArgumentException, IllegalStateException;

  /**
   * Checks if location of given position(id) is of type Tunnel.
   * @param position position(id) of the location node.
   * @return true if the location is of type tunnel else false.
   * @throws IllegalArgumentException when location of given position(id) is not in this graph.
   */
  boolean isTunnel(MatrixPosition position)
      throws IllegalArgumentException;

  /**
   * Checks if location of given position(id) is of type Cave.
   * @param position position(id) of the location node.
   * @return true if the location is of type tunnel else false.
   * @throws IllegalArgumentException when location of given position(id) is not in this graph.
   */
  boolean isCave(MatrixPosition position)
      throws IllegalArgumentException;

  /**
   * Checks if location of given position(id) is of type Wall.
   * @param position position(id) of the location node.
   * @return true if the location is of type tunnel else false.
   * @throws IllegalArgumentException when location of given position(id) is not in this graph.
   */
  boolean isWall(MatrixPosition position)
      throws IllegalArgumentException;
}

