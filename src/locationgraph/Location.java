package locationgraph;

import static common.Direction.EAST;
import static common.Direction.NORTH;
import static common.Direction.SOUTH;
import static common.Direction.WEST;

import common.Direction;
import common.MatrixPosition;
import common.Treasure;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

class Location implements LocationNode {

  private final HashMap<Direction, LocationNode> neighbours;
  private final MatrixPosition position;
  private List<Treasure> treasures;

  public Location(
      MatrixPosition position,
      HashMap<Direction, LocationNode> neighbours
  ) {
    this.position = position;
    this.neighbours = neighbours;
  }

  public Location(
      MatrixPosition position
  ) {
    this.position = position;
    this.neighbours = new HashMap<>();
    neighbours.put(NORTH, Wall.getInstance());
    neighbours.put(EAST, Wall.getInstance());
    neighbours.put(SOUTH, Wall.getInstance());
    neighbours.put(WEST, Wall.getInstance());
  }

  private int getNeighbourCount() {
    int count = 0;
    if (!neighbours.get(NORTH).isWall()) {
      count++;
    }
    if (!neighbours.get(EAST).isWall()) {
      count++;
    }
    if (!neighbours.get(SOUTH).isWall()) {
      count++;
    }
    if (!neighbours.get(WEST).isWall()) {
      count++;
    }
    return count;
  }

  @Override
  public String getType() {
    return isCave() ? "Cave" : "Tunnel";
  }

  @Override
  public void addTreasure(Treasure treasure)
      throws IllegalArgumentException, IllegalStateException {
    if (treasure == null) {
      throw new IllegalArgumentException("Treasure can not be null.");
    }
    else if (isTunnel()) {
      throw new IllegalStateException("Can not add treasure to a tunnel");
    }
    this.treasures.add(treasure);
  }

  @Override
  public String getDescription() {
    String ret = String.format(
        "\n%s:\n Coordinates -> %s Neighbours {N, E, S, W}: -> {%s, %s, %s, %s}\n",
        getType(),
        position.toString(),
        neighbours.get(NORTH).getType(),
        neighbours.get(EAST).getType(),
        neighbours.get(SOUTH).getType(),
        neighbours.get(WEST).getType()
    );
    return ret;
  }

  @Override
  public boolean isTunnel() {
    return getNeighbourCount() == 2;
  }

  @Override
  public boolean isCave() {
    return getNeighbourCount() != 2;
  }

  @Override
  public boolean isWall() {
    return false;
  }

  @Override
  public boolean hasTreasure() {
    return treasures.size() != 0;
  }

  @Override
  public LocationNode getLocationAt(Direction direction) {
    return neighbours.get(direction);
  }

  @Override
  public boolean hasWallAt(Direction direction) {
    return neighbours.get(direction).isWall();
  }

  @Override
  public void setNeighbour(Direction direction, LocationNode location)
      throws IllegalArgumentException {
    if (direction == null || location == null) {
      throw new IllegalArgumentException("direction/ location can not be null");
    }
    neighbours.put(direction, location);
  }

  @Override
  public Direction getNeighbourDirection(LocationNode neighbour)
      throws IllegalArgumentException {
    for (Direction direction: Direction.values()) {
      if (neighbours.get(direction).equals(neighbour)) {
        return direction;
      }
    }
    throw new IllegalArgumentException("Given node is not a neighbour");
  }

  @Override
  public List<Treasure> removeTreasure() throws IllegalStateException {
    if (isTunnel()) {
      throw new IllegalStateException("Only caves have treasure.");
    }
    List<Treasure> temp = treasures;
    treasures = new ArrayList<>();
    return temp;
  }

  @Override
  public MatrixPosition getPosition() {
    return position;
  }

  @Override
  public boolean hasNeighbour(LocationNode that) {
    return that.equals(this.getLocationAt(NORTH))
        || that.equals(this.getLocationAt(EAST))
        || that.equals(this.getLocationAt(SOUTH))
        || that.equals(this.getLocationAt(WEST));
  }

  @Override
  public String toString() {
    return getDescription();
  }
}
