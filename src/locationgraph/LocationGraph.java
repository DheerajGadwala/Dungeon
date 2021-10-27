package locationgraph;

import common.Direction;
import common.MatrixPosition;
import common.Treasure;
import randomizer.Randomizer;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * This is a collection of locations and their connections.
 */
public class LocationGraph implements LocationGraphAdt {

  private final List<LocationNode> locationNodes;
  private final List<Set<LocationNode>> connections;
  private final Randomizer randomizer;

  public LocationGraph(Randomizer randomizer) {
    this.locationNodes = new ArrayList<>();
    this.connections = new ArrayList<>();
    this.randomizer = randomizer;
  }

  private boolean containsPosition(MatrixPosition position) {
    for (LocationNode node: locationNodes) {
      if(node.getPosition().equals(position)) {
        return true;
      }
    }
    return false;
  }

  private boolean containsConnection(MatrixPosition position1, MatrixPosition position2) {
    for (Set<LocationNode> edge: connections) {
      List<LocationNode> nodes = new ArrayList<>(edge);
      MatrixPosition nodePos1 = nodes.get(0).getPosition();
      MatrixPosition nodePos2 = nodes.get(1).getPosition();
      if (
          (nodePos1.equals(position1) && nodePos2.equals(position2))
              ||
              (nodePos2.equals(position1) && nodePos1.equals(position2))
      ) {
        return true;
      }
    }
    return false;
  }

  private LocationNode getLocation(MatrixPosition position) {
    for (int i=0; i<locationNodes.size(); i++) {
      if(locationNodes.get(i).getPosition().equals(position)) {
      }

    }
    for (LocationNode node: locationNodes) {
      if(node.getPosition().equals(position)) {
        return node;
      }
    }
    throw new IllegalArgumentException("No location at the given position!");
  }

  @Override
  public void createLocationNode(MatrixPosition position)
      throws IllegalArgumentException, IllegalStateException {
    if (position == null) {
      throw new IllegalArgumentException("Position can not be null!");
    }
    if (containsPosition(position)) {
      throw new IllegalStateException("A location already exists at the given position!");
    }
    locationNodes.add(new Cave(position));
  }

  @Override
  public void addConnection(
      MatrixPosition position1,
      MatrixPosition position2,
      Direction direction
  )
      throws IllegalArgumentException, IllegalStateException {
    if (position1 == null || position2 == null || direction == null) {
      throw new IllegalArgumentException("Position/ direction can not be null!");
    }
    if (!containsPosition(position1) || !containsPosition(position2)) {
      throw new IllegalStateException("No location at one of the given positions.");
    }
    if (containsConnection(position1, position2)) {
      throw new IllegalStateException("Edge already exists between these locations");
    }
    LocationNode node1 = getLocation(position1);
    LocationNode node2 = getLocation(position2);

  }

  @Override
  public void removeConnection(MatrixPosition position1, MatrixPosition position2)
      throws IllegalArgumentException, IllegalStateException {

  }

  @Override
  public String getDescription(MatrixPosition position)
      throws IllegalArgumentException {
    return null;
  }

  @Override
  public boolean hasTreasure(MatrixPosition position)
      throws IllegalArgumentException {
    return false;
  }

  @Override
  public Treasure collectTreasure(MatrixPosition position)
      throws IllegalArgumentException, IllegalStateException {
    return null;
  }

  @Override
  public void addTreasure(MatrixPosition position, Treasure treasure)
      throws IllegalArgumentException, IllegalStateException {

  }

  @Override
  public boolean isTunnel(MatrixPosition position)
      throws IllegalArgumentException {
    return false;
  }

  @Override
  public boolean isCave(MatrixPosition position)
      throws IllegalArgumentException {
    return false;
  }

  @Override
  public boolean isWall(MatrixPosition position)
      throws IllegalArgumentException {
    return false;
  }
}
