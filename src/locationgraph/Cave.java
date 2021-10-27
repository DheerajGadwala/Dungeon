package locationgraph;

import common.Direction;
import common.MatrixPosition;
import common.Treasure;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

class Cave extends AbstractLocationNode {

  private List<Treasure> treasures;

  /**
   * Creates a new cave object with the given position and neighbouring locations.
   * @param position position of the cave.
   * @param neighbours neighbours of a cave as a map of directions and neighbours.
   */
  public Cave(MatrixPosition position, HashMap<Direction, LocationNode> neighbours) {
    super(position, neighbours);
    treasures = new ArrayList<>();
  }

  public Cave(MatrixPosition position) {
    super(position);
    treasures = new ArrayList<>();
  }

  @Override
  public boolean isCave() {
    return true;
  }

  @Override
  public String getDescription() {
    String ret = super.getDescription();
    ret += String.format("");
    return ret;
  }

  @Override
  public boolean hasTreasure() {
    return treasures.size() != 0;
  }

  @Override
  public void addTreasure(Treasure treasure) throws IllegalArgumentException {
    if (treasure == null) {
      throw new IllegalArgumentException("Treasure can not be null.");
    }
    this.treasures.add(treasure);
  }

  @Override
  public List<Treasure> removeTreasure() throws IllegalStateException {
    List<Treasure> temp = treasures;
    treasures = new ArrayList<>();
    return temp;
  }

  @Override
  public String toString() {
    return "Cave";
  }
}
