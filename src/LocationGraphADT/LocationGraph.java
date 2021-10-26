package LocationGraphADT;

import Common.Direction;
import Common.MatrixPosition;
import Common.Treasure;

/**
 * This is a collection of locations that might be connected to each other.
 */
public class LocationGraph implements LocationGraphADT {
  @Override
  public void createLocationNode(MatrixPosition position) throws IllegalStateException {

  }

  @Override
  public void addConnection(MatrixPosition position1, MatrixPosition position2, Direction direction) throws IllegalStateException {

  }

  @Override
  public String getDescription(MatrixPosition position) throws IllegalArgumentException {
    return null;
  }

  @Override
  public boolean hasTreasure(MatrixPosition position) throws IllegalArgumentException {
    return false;
  }

  @Override
  public Treasure collectTreasure(MatrixPosition position) throws IllegalArgumentException, IllegalStateException {
    return null;
  }

  @Override
  public void addTreasure(MatrixPosition position, Treasure treasure) throws IllegalArgumentException, IllegalStateException {

  }

  @Override
  public boolean isTunnel(MatrixPosition position) throws IllegalArgumentException {
    return false;
  }

  @Override
  public boolean isCave(MatrixPosition position) throws IllegalArgumentException {
    return false;
  }

  @Override
  public boolean isWall(MatrixPosition position) throws IllegalArgumentException {
    return false;
  }
}
