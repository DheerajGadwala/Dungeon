package LocationGraphADT;

import Common.Direction;
import Common.MatrixPosition;
import Common.Treasure;

import java.util.HashMap;

class Tunnel extends AbstractLocationNode {

  public Tunnel(MatrixPosition position, HashMap<Direction, LocationNode> neighbours) {
    super(position, neighbours);
  }

  @Override
  public boolean isTunnel() {
    return false;
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
  public void addTreasure(Treasure treasure) {

  }

  @Override
  public MatrixPosition getPosition() {
    return null;
  }

}
