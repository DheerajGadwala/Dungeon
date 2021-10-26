package LocationGraphADT;

import Common.Direction;
import Common.MatrixPosition;
import Common.Treasure;

class Cave extends AbstractLocationNode {

  @Override
  public boolean isCave() {
    return true;
  }

  @Override
  public boolean hasWallAt(Direction direction) {
    return false;
  }

  @Override
  public LocationNode getLocationAt(Direction direction) {
    return null;
  }

  @Override
  public String getDescription() {
    return null;
  }

  @Override
  public boolean hasTreasure() {
    return false;
  }

  @Override
  public void addTreasure(Treasure treasure) {
  }

  @Override
  public MatrixPosition getPosition() {
    return null;
  }
}
