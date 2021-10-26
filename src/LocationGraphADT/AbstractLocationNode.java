package LocationGraphADT;

import Common.Direction;
import Common.MatrixPosition;

import java.util.List;
import java.util.HashMap;
import java.util.stream.Collectors;

abstract class AbstractLocationNode implements LocationNode {

  private final HashMap<Direction, LocationNode> neighbours;
  private final MatrixPosition position;

  protected AbstractLocationNode(MatrixPosition position, HashMap<Direction, LocationNode> neighbours) {
    this.position = position;
    this.neighbours = neighbours;
  }

  private int getNeighbourCount() {
    int count=0;
    if (neighbours.get(Direction.NORTH).isWall()) {
      count++;
    }
    if (neighbours.get(Direction.EAST).isWall()) {
      count++;
    }
    if (neighbours.get(Direction.SOUTH).isWall()) {
      count++;
    }
    if (neighbours.get(Direction.WEST).isWall()) {
      count++;
    }
    return count;
  }

  @Override
  public LocationNode setNeighbour(Direction direction, LocationNode location) throws IllegalArgumentException, IllegalStateException {
    if(neighbours.get(direction).isWall()) {
      HashMap<Direction, LocationNode> mapCopy = neighbours.entrySet().stream()
          .collect(Collectors.toMap(e -> e.getKey(), e -> List.copyOf(e.getValue())))
      HashMap<Direction, LocationNode> newNeighbours = neighbours.clone();
      if(this.isTunnel()) {
        return new Cave(position, );
      }
    }
  }
}
