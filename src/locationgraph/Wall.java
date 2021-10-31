package locationgraph;

import common.Direction;
import common.MatrixPosition;
import common.Treasure;

import java.util.List;

/**
 * Singleton Pattern.
 */
class Wall implements LocationNode {

  private static final Wall instance = new Wall();

  private Wall() {
  }

  static Wall getInstance() {
    return instance;
  }

  @Override
  public boolean hasWallAt(Direction direction)
      throws IllegalStateException {
    throw new IllegalStateException("This is itself the wall!");
  }

  @Override
  public void setNeighbour(Direction direction, LocationNode location)
      throws IllegalStateException {
    throw new IllegalStateException("Can not add neighbour to the wall.");
  }

  @Override
  public Direction getNeighbourDirection(LocationNode neighbour) throws IllegalStateException {
    throw new IllegalStateException("Wall does not have neighbours.");
  }

  @Override
  public List<Treasure> removeTreasure() throws IllegalStateException {
    throw new IllegalStateException("Can not remove treasure from the wall.");
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
    return true;
  }

  @Override
  public LocationNode getLocationAt(Direction direction)
      throws IllegalStateException {
    throw new IllegalStateException("Player is on a wall, this shouldn't be possible!");
  }

  @Override
  public String getDescription() {
    return toString();
  }

  @Override
  public boolean hasTreasure() {
    return false;
  }

  @Override
  public String getType() {
    return "Wall";
  }

  @Override
  public void addTreasure(Treasure treasure) throws IllegalStateException {
    throw  new IllegalStateException("can not add treasure to the wall");
  }

  @Override
  public MatrixPosition getPosition()
      throws IllegalStateException {
    throw new IllegalStateException("Walls do not have a position!");
  }

  @Override
  public boolean hasNeighbour(LocationNode that) {
    return false;
  }

  @Override
  public String toString() {
    return "Wall";
  }
}
