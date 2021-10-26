package LocationGraphADT;

import Common.Direction;
import Common.MatrixPosition;
import Common.Treasure;

public class Wall extends AbstractLocationNode {
  @Override
  public boolean isWall() {
    return false;
  }

  @Override
  public LocationNode getLocationAt(Direction direction) {
    throw new IllegalStateException("Player is at a wall.");
  }

  @Override
  public String getDescription() {
    return "Wall";
  }

  @Override
  public void addTreasure(Treasure treasure) {
    throw new IllegalStateException("Walls can not have treasure");
  }

  @Override
  public MatrixPosition getPosition() {
    throw new IllegalStateException("Walls do not have a position");
  }
}
