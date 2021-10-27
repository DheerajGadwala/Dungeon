package locationgraph;

import common.Direction;
import common.MatrixPosition;
import common.Treasure;

import java.util.HashMap;
import java.util.List;

abstract class AbstractLocationNode implements LocationNode {

  protected final HashMap<Direction, LocationNode> neighbours;
  protected final MatrixPosition position;

  protected AbstractLocationNode(
      MatrixPosition position,
      HashMap<Direction, LocationNode> neighbours
  ) {
    this.position = position;
    this.neighbours = neighbours;
  }

  protected AbstractLocationNode(
      MatrixPosition position
  ) {
    this.position = position;
    this.neighbours = new HashMap<>();
    neighbours.put(Direction.NORTH, Wall.getInstance());
    neighbours.put(Direction.EAST, Wall.getInstance());
    neighbours.put(Direction.SOUTH, Wall.getInstance());
    neighbours.put(Direction.WEST, Wall.getInstance());
  }

  private int getNeighbourCount() {
    int count = 0;
    if (!neighbours.get(Direction.NORTH).isWall()) {
      count++;
    }
    if (!neighbours.get(Direction.EAST).isWall()) {
      count++;
    }
    if (!neighbours.get(Direction.SOUTH).isWall()) {
      count++;
    }
    if (!neighbours.get(Direction.WEST).isWall()) {
      count++;
    }
    return count;
  }

  @Override
  public void addTreasure(Treasure treasure) throws IllegalStateException {
    throw new IllegalStateException("Treasure can only be added to a Cave");
  }

  @Override
  public String getDescription() {
    return String.format(
        "%s:\n Coordinates -> (%d, %d)\n Neighbours {N, E, S, W}: -> {%s, %s, %s, %s}",
        this.toString(),
        neighbours.get(Direction.NORTH).toString(),
        neighbours.get(Direction.EAST).toString(),
        neighbours.get(Direction.SOUTH).toString(),
        neighbours.get(Direction.WEST).toString()
    );
  }

  @Override
  public boolean isTunnel() {
    return false;
  }

  @Override
  public boolean isCave() {
    return false;
  }

  @Override
  public boolean isWall() {
    return false;
  }

  @Override
  public boolean hasTreasure() {
    return false;
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
  public LocationNode setNeighbour(Direction direction, LocationNode location)
      throws IllegalArgumentException {
    if (direction == null || location == null) {
      throw new IllegalArgumentException("direction/ location can not be null");
    }
    neighbours.put(direction, location);
    if (this.getNeighbourCount() == 2) {
      return new Tunnel(position, neighbours);
    }
    else {
      return new Cave(position, neighbours);
    }
  }

  @Override
  public List<Treasure> removeTreasure() throws IllegalStateException {
    throw new IllegalStateException("Only caves have treasure.");
  }

  @Override
  public MatrixPosition getPosition() {
    return position;
  }
}
