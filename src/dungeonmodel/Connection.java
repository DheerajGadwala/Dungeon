package dungeonmodel;

import dungeongeneral.Direction;
import dungeongeneral.Coordinate;

/**
 * Represents an edge in the Location graph.
 */
class Connection {
  private final LocationNode A;
  private final LocationNode B;

  Connection(LocationNode a, LocationNode b) {
    A = a;
    B = b;
  }

  LocationNode getVertexA() {
    return this.A;
  }

  LocationNode getVertexB() {
    return this.B;
  }

  Coordinate getCoordinateA() {
    return getVertexA().getCoordinates();
  }

  Coordinate getCoordinateB() {
    return getVertexB().getCoordinates();
  }

  Direction getDirection() {
    return A.getNeighbourDirection(B);
  }

  boolean isConnectedToA(Connection that) {
    return this.A.hasNeighbour(that.A)
        || this.A.hasNeighbour(that.B);
  }

  boolean isConnectedToB(Connection that) {
    return this.B.hasNeighbour(that.A)
        || this.B.hasNeighbour(that.B);
  }

  boolean isConnected(Connection that) {
    return isConnectedToA(that) || isConnectedToB(that);
  }

  boolean hasVertexAt(Coordinate a) {
    return A.getCoordinates().equals(a) || B.getCoordinates().equals(a);
  }

  @Override
  public String toString() {
    return "A: " + A.getCoordinates() + "B: " + B.getCoordinates();
  }
}
