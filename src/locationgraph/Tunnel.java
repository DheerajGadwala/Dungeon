package locationgraph;

import common.Direction;
import common.MatrixPosition;

import java.util.HashMap;

class Tunnel extends AbstractLocationNode {

  /**
   * Creates a new tunnel object with the given position and neighbouring locations.
   * @param position position of the cave.
   * @param neighbours neighbours of a cave as a map of directions and neighbours.
   */
  public Tunnel(MatrixPosition position, HashMap<Direction, LocationNode> neighbours) throws IllegalStateException {
    super(position, neighbours);
  }

  @Override
  public boolean isTunnel() {
    return true;
  }

  @Override
  public String toString() {
    return "Tunnel";
  }
}
