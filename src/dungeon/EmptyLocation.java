package dungeon;

import general.Direction;
import general.MatrixPosition;
import general.Treasure;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;

/**
 * Singleton Pattern.
 */
class EmptyLocation implements LocationNode {

  private static final EmptyLocation instance = new EmptyLocation();

  private EmptyLocation() {
  }

  static EmptyLocation getInstance() {
    return instance;
  }

  @Override
  public boolean hasEmptyNodeAt(Direction direction)
      throws IllegalStateException {
    throw new IllegalStateException("This is itself the empty node!");
  }

  @Override
  public void setNeighbour(Direction direction, LocationNode location)
      throws IllegalStateException {
    throw new IllegalStateException("Can not add neighbour to the empty node.");
  }

  @Override
  public Direction getNeighbourDirection(LocationNode neighbour) throws IllegalStateException {
    throw new IllegalStateException("Empty node does not have neighbours.");
  }

  @Override
  public HashMap<Treasure, Integer> removeTreasure() throws IllegalStateException {
    throw new IllegalStateException("Can not remove treasure from the empty node.");
  }

  @Override
  public List<LocationNode> getRequiredNodesHelper(
      List<LocationNode> visited,
      List<LocationNode> queue,
      List<Integer> queueD,
      Predicate<Integer> distanceRequirement,
      Predicate<LocationNode> nodeRequirement
  ) {
    return new ArrayList<>();
  }

  @Override
  public List<LocationNode> getRequiredNodes(
      Predicate<Integer> distanceRequirement,
      Predicate<LocationNode> nodeRequirement
  ) {
    throw new IllegalStateException("Can not perform this operation from empty node.");
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
  public boolean isEmptyNode() {
    return true;
  }

  @Override
  public LocationNode getLocationAt(Direction direction)
      throws IllegalStateException {
    throw new IllegalStateException("Player is on a empty node, this shouldn't be possible!");
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
  public void setMonster(Monster monster) throws IllegalArgumentException {
    throw new IllegalStateException("Can not add monster to sentinel.");
  }

  @Override
  public boolean hasMonster() {
    return false;
  }

  @Override
  public String getType() {
    return "Empty node";
  }

  @Override
  public void addTreasure(Map<Treasure, Integer> treasure) throws IllegalStateException {
    throw  new IllegalStateException("can not add treasure to the empty node");
  }

  @Override
  public MatrixPosition getPosition()
      throws IllegalStateException {
    throw new IllegalStateException("Empty nodes do not have a position!");
  }

  @Override
  public boolean hasNeighbour(LocationNode that) {
    return false;
  }

  @Override
  public String toString() {
    return "Empty node";
  }
}
