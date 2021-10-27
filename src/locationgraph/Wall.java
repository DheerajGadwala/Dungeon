package locationgraph;

import common.Direction;
import common.MatrixPosition;

/**
 * Singleton Pattern.
 */
class Wall extends AbstractLocationNode {

  private static Wall instance = new Wall();

  private Wall() {
    super(null, null);
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
  public LocationNode setNeighbour(Direction direction, LocationNode location)
      throws IllegalStateException {
    throw new IllegalStateException("Can not add neighbour to the wall.");
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
  public MatrixPosition getPosition()
      throws IllegalStateException {
    throw new IllegalStateException("Walls do not have a position!");
  }

  @Override
  public String toString() {
    return "Wall";
  }
}
